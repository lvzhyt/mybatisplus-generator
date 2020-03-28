##mybatis-plus 代码生成器
基于 mybatis-plus generator

模板工具 velocity
修改配置文件 generator.properties

修改templates下的模板 生成想要的文件 只保留了vm

详细 参见官方 

https://mp.baomidou.com/guide/generator.html#%E4%BD%BF%E7%94%A8%E6%95%99%E7%A8%8B

可配置的参数
参见 git 
```html
https://gitee.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-generator/src/main/java/com/baomidou/mybatisplus/generator/engine/AbstractTemplateEngine.java
```
部分源代码
```java
class AbstractTemplateEngine {
 /**
      * 渲染对象 MAP 信息
      *
      * @param tableInfo 表信息对象
      * @return ignore
      */
     public Map<String, Object> getObjectMap(TableInfo tableInfo) {
         Map<String, Object> objectMap = new HashMap<>(30);
         ConfigBuilder config = getConfigBuilder();
         if (config.getStrategyConfig().isControllerMappingHyphenStyle()) {
             objectMap.put("controllerMappingHyphenStyle", config.getStrategyConfig().isControllerMappingHyphenStyle());
             objectMap.put("controllerMappingHyphen", StringUtils.camelToHyphen(tableInfo.getEntityPath()));
         }
         objectMap.put("restControllerStyle", config.getStrategyConfig().isRestControllerStyle());
         objectMap.put("config", config);
         objectMap.put("package", config.getPackageInfo());
         GlobalConfig globalConfig = config.getGlobalConfig();
         objectMap.put("author", globalConfig.getAuthor());
         objectMap.put("idType", globalConfig.getIdType() == null ? null : globalConfig.getIdType().toString());
         objectMap.put("logicDeleteFieldName", config.getStrategyConfig().getLogicDeleteFieldName());
         objectMap.put("versionFieldName", config.getStrategyConfig().getVersionFieldName());
         objectMap.put("activeRecord", globalConfig.isActiveRecord());
         objectMap.put("kotlin", globalConfig.isKotlin());
         objectMap.put("swagger2", globalConfig.isSwagger2());
         objectMap.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
         objectMap.put("table", tableInfo);
         objectMap.put("enableCache", globalConfig.isEnableCache());
         objectMap.put("baseResultMap", globalConfig.isBaseResultMap());
         objectMap.put("baseColumnList", globalConfig.isBaseColumnList());
         objectMap.put("entity", tableInfo.getEntityName());
         objectMap.put("entitySerialVersionUID", config.getStrategyConfig().isEntitySerialVersionUID());
         objectMap.put("entityColumnConstant", config.getStrategyConfig().isEntityColumnConstant());
         objectMap.put("entityBuilderModel", config.getStrategyConfig().isEntityBuilderModel());
         objectMap.put("entityLombokModel", config.getStrategyConfig().isEntityLombokModel());
         objectMap.put("entityBooleanColumnRemoveIsPrefix", config.getStrategyConfig().isEntityBooleanColumnRemoveIsPrefix());
         objectMap.put("superEntityClass", getSuperClassName(config.getSuperEntityClass()));
         objectMap.put("superMapperClassPackage", config.getSuperMapperClass());
         objectMap.put("superMapperClass", getSuperClassName(config.getSuperMapperClass()));
         objectMap.put("superServiceClassPackage", config.getSuperServiceClass());
         objectMap.put("superServiceClass", getSuperClassName(config.getSuperServiceClass()));
         objectMap.put("superServiceImplClassPackage", config.getSuperServiceImplClass());
         objectMap.put("superServiceImplClass", getSuperClassName(config.getSuperServiceImplClass()));
         objectMap.put("superControllerClassPackage", verifyClassPacket(config.getSuperControllerClass()));
         objectMap.put("superControllerClass", getSuperClassName(config.getSuperControllerClass()));
         return Objects.isNull(config.getInjectionConfig()) ? objectMap : config.getInjectionConfig().prepareObjectMap(objectMap);
     }
 }
```