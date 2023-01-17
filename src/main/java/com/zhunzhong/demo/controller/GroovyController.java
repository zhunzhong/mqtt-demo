package com.zhunzhong.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.zhunzhong.demo.api.Result;
import groovy.lang.GroovyClassLoader;
import liquibase.pro.packaged.S;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.SecureASTCustomizer;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author: zhunzhong
 * @date: 2023-01-17 15:32
 * @description: todo
 */
@Slf4j
@RestController
@RequestMapping("/groovy")
public class GroovyController {

    @Value("${groovy.classPath}")
    private String baseClassPath;

    private GroovyClassLoader classLoader;


    @GetMapping("/load")
    public Result<Void> load() {
        log.info("BaseClassPath: {}", baseClassPath);
        final SecureASTCustomizer groovyStandardSecureASTCustomizer = new SecureASTCustomizer();
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        //自定义CompilerConfiguration，设置groovy 编译选项，
        // 比如设置基类，设置默认导包，安全校验AST等等等，其他自定义操作
        compilerConfiguration.addCompilationCustomizers(groovyStandardSecureASTCustomizer);
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        classLoader = new GroovyClassLoader();

        List<File> fileList = FileUtil.loopFiles(new File(baseClassPath),
                pathname -> StrUtil.endWith(pathname.getName(), ".jar"));
        fileList.forEach((file -> {
            try {
                log.info("addClassPath:{}", file.getCanonicalPath());
                // classLoader.addClasspath(file.getCanonicalPath());
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }));
        return Result.success();
    }

    @GetMapping("/run")
    public Result<Void> run() throws IOException {
        String groovyFilePath = "D:\\codes\\open-source\\zhunzhong\\mqtt-demo\\src\\main\\groovy\\Test.groovy";
        File groovyFile = new File(groovyFilePath);
        Class<?> clazz = classLoader.parseClass(groovyFile);
        try {
            Method run = clazz.getMethod("run", String.class);
            Object obj = clazz.getDeclaredConstructor().newInstance();
            Object res = run.invoke(obj, "xxx");
            System.out.println(res);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 InstantiationException e) {
            log.error(e.getMessage(), e);
        }
        // Object res=InvokerHelper.invokeMethod(clazz,"run","xxx");

        return Result.success();
    }

}
