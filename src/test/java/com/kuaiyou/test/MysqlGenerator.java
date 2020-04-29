package com.kuaiyou.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class MysqlGenerator {
	public static String parent = "com.kuaiyou";
	public static String moduleName = "lucky";
	public static String superMapperClass = "com.kuaiyou.lucky.common.SuperMapper";
	public static String tablePrefix = "t_luck_";
	public static String projectPath = "D:/luckminipro/";

	/**
	 * <p>
	 * MySQL 生成演示
	 * </p>
	 */
	public static void main(String[] args) {
		// 自定义需要填充的字段s
		List<TableFill> tableFillList = new ArrayList<>();
		tableFillList.add(new TableFill("ASDD_SS", FieldFill.INSERT_UPDATE));
		// String[] excludeTableNames = {};
		// String[] includeTableNames = { "t_luck_activity", "t_luck_business",
		// "t_luck_draw", "t_luck_prize",
		// "t_luck_prizesetting", "t_luck_report", "t_luck_userdev",
		// "t_luck_userdraw", "t_luck_wxuser" };
		String[] includeTableNames = { "t_luck_adviser" };
		// 代码生成器
		GlobalConfig globalConfig = new GlobalConfig().setOutputDir(projectPath + "src/main/java")
				// 输出目录
				.setFileOverride(true)
				// 是否覆盖文件
				.setActiveRecord(false)
				// 开启 activeRecord 模式
				.setEnableCache(false)
				// XML 二级缓存
				.setBaseResultMap(false)
				// XML ResultMap
				.setBaseColumnList(true)
				// XML columList
				.setAuthor("yardney")
				// 自定义文件命名，注意 %s 会自动填充表实体属性
				.setMapperName("%sMapper")
				// .setXmlName("%sMapper")
				.setServiceName("%sService").setServiceImplName("%sServiceImpl").setActiveRecord(true)
		// .setControllerName("%sGenApi")
		;

		DataSourceConfig dataSource = new DataSourceConfig().setDbType(DbType.MYSQL)
				// 数据库类型
				.setTypeConvert(new MySqlTypeConvert() {
					// 自定义数据库表字段类型转换【可选】
					@Override
					public DbColumnType processTypeConvert(String fieldType) {
						System.out.println("转换类型：" + fieldType);
						// if ( fieldType.toLowerCase().contains(
						// "tinyint" ) ) {
						// return DbColumnType.BOOLEAN;
						// }
						return super.processTypeConvert(fieldType);
					}
				}).setDriverName("com.mysql.jdbc.Driver").setUsername("kuaiyou").setPassword("kuaiyou")
				.setUrl("jdbc:mysql://192.168.2.43:3306/kylucky?useUnicode=true&characterEncoding=UTF-8&useSSL=false&zeroDateTimeBehavior=convertToNull");
		StrategyConfig strategyConfig = new StrategyConfig()
				// .setCapitalMode(true)// 全局大写命名
				// .setDbColumnUnderline(true)//全局下划线命名
				.setTablePrefix(new String[] { "sys_", tablePrefix })// 此处可以修改为您的表前缀
				.setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
				// .setInclude(new String[] { "user" }) //
				// 需要生成的表
				// .setExclude(new String[]{"test"}) // 排除生成的表
				// 自定义实体父类
				// .setSuperEntityClass("com.baomidou.demo.TestEntity")
				// 自定义实体，公共字段
				// .setSuperEntityColumns(new String[] { "test_id"
				// }).setTableFillList(tableFillList)
				// 自定义 mapper 父类
				// .setSuperMapperClass("com.baomidou.demo.TestMapper")
				// 自定义 service 父类
				// .setSuperServiceClass("com.baomidou.demo.TestService")
				// 自定义 service 实现类父类
				// .setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl")
				// 自定义 controller 父类
				// .setSuperControllerClass("org.redin.controller.BaseController")
				// 自定义 公共字段
				// 自定义 Enity 父类
				.setSuperMapperClass(superMapperClass)
				// .setSuperEntityClass("com.baomidou.mybatisplus.activerecord.Model")
				// 【实体】是否生成字段常量（默认 false）
				.setEntityColumnConstant(true)
				// 【实体】是否为构建者模型（默认 false）
				// .setEntityBuilderModel(true)
				// 【实体】是否为lombok模型（默认 false）<a
				// href="https://projectlombok.org/">document</a>
				.setEntityLombokModel(true)
				// Boolean类型字段是否移除is前缀处理
				// .setEntityBooleanColumnRemoveIsPrefix(true)
				// .setRestControllerStyle(true)
				// .setControllerMappingHyphenStyle(true)
				.setLogicDeleteFieldName("deleted")
				// .setExclude(excludeTableNames)
				.setInclude(includeTableNames).setRestControllerStyle(true);
		PackageConfig packageConfig = new PackageConfig().setModuleName(moduleName)
				// 自定义包路径
				.setParent(parent)
				// 这里是控制器包名，默认 web
				// .setController("genapi")
				// 实体包名
				.setService("service").setServiceImpl("service.impl").setEntity("entity");
		AutoGenerator mpg = new AutoGenerator().setGlobalConfig(globalConfig).setDataSource(dataSource)
				.setStrategy(strategyConfig).setPackageInfo(packageConfig)
				.setCfg(
						// 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
						new InjectionConfig() {
							@Override
							public void initMap() {
								Map<String, Object> map = new HashMap<>();
								// map.put("abc",
								// this.getConfig().getGlobalConfig().getAuthor()
								// +
								// "-mp");
								this.setMap(map);
							}
						}.setFileOutConfigList(
								Collections.<FileOutConfig>singletonList(new FileOutConfig("/templates/mapper.xml.vm") {
									// 自定义输出文件目录
									@Override
									public String outputFile(TableInfo tableInfo) {
										return projectPath + "src/main/resources/mappers/" + tableInfo.getEntityName()
												+ "Mapper.xml";
									}
								})))
				.setTemplate(
						// 关闭默认 xml 生成，调整生成 至 根目录
						new TemplateConfig().setXml(null)
		// 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用
		// copy
		// 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
		// .setController("...");
		// .setEntity("...");
		// .setMapper("...");
		// .setXml("...");
		// .setService("...");
		// .setServiceImpl("...");
		);
		// 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
		// 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
		// .setController("...");
		// .setEntity("...");
		// .setMapper("...");
		// .setXml("...");
		// .setService("...");
		// .setServiceImpl("...");

		// InjectionConfig cfg = new InjectionConfig() {
		// @Override
		// public void initMap() {
		//
		// }
		// };
		// List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
		//
		// // 调整 xml 生成目录演示
		// focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
		// @Override
		// public String outputFile(TableInfo tableInfo) {
		// return "/develop/code/xml/" + tableInfo.getEntityName() + ".xml";
		// }
		// });
		// cfg.setFileOutConfigList(focList);

		// 执行生成
		mpg.execute();

		// 打印注入设置，这里演示模板里面怎么获取注入内容【可无】
		// System.err.println(mpg.getCfg().getMap().get("abc"));
	}

}