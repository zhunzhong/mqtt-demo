package com.zhunzhong.demo.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @author zhunzhong
 * @datetime 2021/12/25 15:42
 * @description
 */
public class MybatisTool {

    public static void ds() {
        String url = "jdbc:mysql://127.0.0.1:3306/ds1?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "root";
        autoGenerator(url, username, password, "D:/temp/ds");
    }


    public static void autoGenerator(String url, String username, String password, String baseDir) {
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("zhunzhong") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(baseDir); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.iflytek.zhunzhong.demo") // 设置父包名
                            .moduleName("pojo") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, baseDir + "/mapperXml/")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder()
                            .enableTableFieldAnnotation()
                            //.enableActiveRecord()
                            .columnNaming(NamingStrategy.no_change)
                            .mapperBuilder().enableBaseResultMap()
                            .enableMapperAnnotation()
                            .enableBaseColumnList()
                            //.enableSkipView()
                            .build();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }


    public static void main(String[] args) {
        ds();
    }
}
