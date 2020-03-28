package mybatis.generator;


import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
//    public static String scanner(String tip) {
//        Scanner scanner = new Scanner(System.in);
//        StringBuilder help = new StringBuilder();
//        help.append("请输入" + tip + "：");
//        System.out.println(help.toString());
//        if (scanner.hasNext()) {
//            String ipt = scanner.next();
//            if (StringUtils.isNotEmpty(ipt)) {
//                return ipt;
//            }
//        }
//        throw new MybatisPlusException("请输入正确的" + tip + "！");
//    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        Configuration config = getConfig();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        String outputDir = config.getString("output-dir");
        if(isRelativePath(outputDir)){
            outputDir = projectPath+File.separator+outputDir;
        }
        gc.setOutputDir(outputDir);
        String author = config.getString("author");
        if(StringUtils.isNotBlank(author)){
            gc.setAuthor(author);
        }
        gc.setOpen(false);
        // 实体属性 Swagger2 注解
        boolean swaggerEnable = config.getBoolean("swagger2");
        gc.setSwagger2(swaggerEnable);

        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        String datasourceUrl = config.getString("datasource.url");
        dsc.setUrl(datasourceUrl);
        // dsc.setSchemaName("public");
        dsc.setDriverName(config.getString("datasource.driver-class-name"));
        dsc.setUsername(config.getString("datasource.username"));
        dsc.setPassword(config.getString("datasource.password"));
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(config.getString("module"));
        pc.setParent(config.getString("package"));
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
//         如果模板引擎是 velocity
         String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父实体类
        String superEntityClass = config.getString("super-entity-class");
        if(StringUtils.isNotBlank(superEntityClass)){
            strategy.setSuperEntityClass(superEntityClass);
        }
        // 公共父mapper
        String superMapperClass = config.getString("super-mapper-class");
        if(StringUtils.isNotBlank(superMapperClass)){
            strategy.setSuperMapperClass(superMapperClass);
        }
        // 公共父mapper
        String superServiceClass = config.getString("super-service-class");
        if(StringUtils.isNotBlank(superServiceClass)){
            strategy.setSuperServiceClass(superServiceClass);
        }
        // 公共父类
        String superControllerClass = config.getString("super-controller-class");
        if(StringUtils.isNotBlank(superControllerClass)){
            strategy.setSuperControllerClass(superControllerClass);
        }

        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
        strategy.setInclude(config.getString("table-names").split(","));
//        strategy.setControllerMappingHyphenStyle(true);
        strategy.setCapitalMode(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new RuntimeException("获取配置文件失败，", e);
        }
    }

    /**
     * 相对路径
     * @param path
     * @return
     */
    public static boolean isRelativePath(String path){
        if(StringUtils.isBlank(path)){
            return true;
        }
        if(path.contains(":\\")||path.contains(":/")){
            return false;
        }
        // linux / 开始为根目录
        if(path.startsWith("/") && "/".equals(File.separator)){
            return false;
        }

        return true;
    }
}