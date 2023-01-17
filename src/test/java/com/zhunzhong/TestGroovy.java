package com.zhunzhong;

import com.google.common.collect.ImmutableMap;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.transform.ThreadInterrupt;
import groovy.transform.TimedInterrupt;
import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.WhileStatement;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer;
import org.codehaus.groovy.control.customizers.SecureASTCustomizer;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.syntax.Types;
import org.junit.Test;
import org.kohsuke.groovy.sandbox.GroovyInterceptor;
import org.kohsuke.groovy.sandbox.SandboxTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: zhunzhong
 * @date: 2023-01-17 16:42
 * @description: todo
 */
public class TestGroovy {


    @Test
    public void testAST() {
        final String script = "import com.alibaba.fastjson.JSONObject;JSONObject object = new JSONObject()";

        // 创建SecureASTCustomizer
        final SecureASTCustomizer secure = new SecureASTCustomizer();
        // 禁止使用闭包
        secure.setClosuresAllowed(true);
        List<Integer> tokensBlacklist = new ArrayList<>();
        // 添加关键字黑名单 while和goto
        tokensBlacklist.add(Types.KEYWORD_WHILE);
        tokensBlacklist.add(Types.KEYWORD_GOTO);
        secure.setDisallowedTokens(tokensBlacklist);
        // 设置直接导入检查
        secure.setIndirectImportCheckEnabled(true);
        // 添加导入黑名单，用户不能导入JSONObject
        List<String> list = new ArrayList<>();
        list.add("com.alibaba.fastjson.JSONObject");
        secure.setDisallowedImports(list);

        // statement 黑名单，不能使用while循环块
        List<Class<? extends Statement>> statementBlacklist = new ArrayList<>();
        statementBlacklist.add(WhileStatement.class);
        secure.setDisallowedStatements(statementBlacklist);

        // 自定义CompilerConfiguration，设置AST
        final CompilerConfiguration config = new CompilerConfiguration();
        config.addCompilationCustomizers(secure);
        Binding intBinding = new Binding();
        GroovyShell shell = new GroovyShell(intBinding, config);

        final Object eval = shell.evaluate(script);
        System.out.println(eval);
    }


    @Test
    public void testGroovySandbox() {
        // 自定义配置
        CompilerConfiguration config = new CompilerConfiguration();

        // 添加线程中断拦截器，可拦截循环体（for,while）、方法和闭包的首指令
        config.addCompilationCustomizers(new ASTTransformationCustomizer(ThreadInterrupt.class));

        // 添加线程中断拦截器，可中断超时线程，当前定义超时时间为3s
        Map<String, Object> timeoutArgs = ImmutableMap.of("value", 3);
        config.addCompilationCustomizers(new ASTTransformationCustomizer(timeoutArgs, TimedInterrupt.class));

        // 沙盒环境
        config.addCompilationCustomizers(new SandboxTransformer());
        GroovyShell sh = new GroovyShell(config);
        // 注册至当前线程
        new NoSystemExitSandbox().register();
        new NoRunTimeSandbox().register();

        // 确保在每次更新缓存Class<Script>对象时候，采用不同的groovyClassLoader
        // Script groovyScript = sh.parse("System.exit(1)");
        // Script groovyScript = sh.parse("Runtime.getRuntime().availableProcessors()");
        Script groovyScript = sh.parse("System.exit(1)");

        Object run = groovyScript.run();
        System.out.println(run);
    }

    static class NoSystemExitSandbox extends GroovyInterceptor {
        @Override
        public Object onStaticCall(GroovyInterceptor.Invoker invoker, Class receiver, String method, Object... args) throws Throwable {
            if (receiver == System.class && method.equals("exit")) {
                throw new SecurityException("No call on System.exit() please");
            }
            return super.onStaticCall(invoker, receiver, method, args);
        }
    }

    static class NoRunTimeSandbox extends GroovyInterceptor {
        @Override
        public Object onStaticCall(GroovyInterceptor.Invoker invoker, Class receiver, String method, Object... args) throws Throwable {
            if (receiver == Runtime.class) {
                throw new SecurityException("No call on RunTime please");
            }
            return super.onStaticCall(invoker, receiver, method, args);
        }
    }


    private static GroovyShell groovyShell = new GroovyShell();


    private static Map<String, Script> scriptCache = new ConcurrentHashMap<>();

    private static <T> T invoke(String scriptText, String function, Object... objects) throws Exception {
        Script script;

        String cacheKey = DigestUtils.md5Hex(scriptText);
        if (scriptCache.containsKey(cacheKey)) {
            script = scriptCache.get(cacheKey);
        } else {
            script = groovyShell.parse(scriptText);
            scriptCache.put(cacheKey, script);
        }
        return (T) InvokerHelper.invokeMethod(script, function, objects);

    }
}
