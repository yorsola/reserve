package com.ac.reserve.common.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * mybatis plus 代码自动生成工具类
 * </p>
 *
 * @author buguangyi
 * @version 0.1
 * @date 2018-5-8
 */
public class CodeGenerator {

    // 数据库配置
    private static String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/reserve?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull&characterSetResults=UTF-8";
    private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static String JDBC_USERNAME = "root";
    private static String JDBC_PASSWORD = "root";
    // 是否继承BaseEntity
    private static boolean BASE_ENTITY = false;
    // 是否继承BaseController
    private static boolean BASE_CONTROLLER = false;
    // 生成的包路径
    private static String PACKAGEDIR = "com.ac.reserve.web";
    // xml 生成路径
    private static String MYBATIS_XML = System.getProperty("user.dir") + "/src/main/resources/mapper/";
    // entity 生成路径
    private static String ENTITY_DIR = System.getProperty("user.dir") + "/src/main/java/com/ac/reserve/web/";

    /**
     * <p>
     * 跟据数据库表自动生成java代码和mapper文件
     * </p>
     */
    public static void main(String[] args) {
        // 生成test表
        String[] tabArr = new String[]{"round"};
        // 模块名称
        String moduleName = "api";
        // 是否继承BaseEntity
        BASE_ENTITY = false;
        // 是否继承BaseController
        BASE_CONTROLLER = false;
        execute(moduleName, tabArr);
    }

    /**
     * 执行
     *
     * @param moduleName 模块名
     * @param tabArr     生成的表
     */
    private static void execute(String moduleName, String[] tabArr) {
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(System.getProperty("user.dir") + "/src/main/java");
        gc.setActiveRecord(false);
        gc.setEnableCache(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setOpen(false);
        gc.setAuthor("author");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(globalConfig, fieldType);
            }
        });
//        dsc.setTypeConvert(new MySqlTypeConvert(){
//
//        });
        dsc.setDriverName(JDBC_DRIVER);
        dsc.setUsername(JDBC_USERNAME);
        dsc.setPassword(JDBC_PASSWORD);
        dsc.setUrl(JDBC_URL);
        mpg.setDataSource(dsc);
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
//        strategy.setTablePrefix(new String[]{"cr_", "sys_"});// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(tabArr);
//        strategy.setLogicDeleteFieldName("del_flag");
        // 自定义实体父类
        if (BASE_ENTITY) {
            strategy.setSuperEntityClass("com.cr.idoctor.common.base.BaseEntity");
            // 自定义实体，公共字段
//            strategy.setSuperEntityColumns(new String[]{"create_by", "create_time", "update_by", "update_time", "del_flag", "remark"});
        }
        if (BASE_CONTROLLER) {
            strategy.setSuperControllerClass("com.cr.idoctor.common.base.BaseController");
        }
        // 【实体】是否为lombok模型（默认 false）
        strategy.setEntityLombokModel(true);
        mpg.setStrategy(strategy);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(PACKAGEDIR);
        pc.setModuleName(moduleName);
        mpg.setPackageInfo(pc);

        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                this.setMap(map);
            }
        };
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig("/template/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return MYBATIS_XML + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        focList.add(new FileOutConfig("/template/entity.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return ENTITY_DIR + moduleName + "/entity/" + tableInfo.getEntityName() + "Entity.java";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        TemplateConfig tc = new TemplateConfig();
        tc.setController("/template/controller.java.vm");
        tc.setEntity(null);
        tc.setMapper("/template/mapper.java.vm");
        tc.setXml(null);
        tc.setService("/template/service.java.vm");
        tc.setServiceImpl("/template/serviceImpl.java.vm");
        mpg.setTemplate(tc);
        // 执行生成
        mpg.execute();

    }

}