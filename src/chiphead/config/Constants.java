package chiphead.config;

public class Constants {
	public static final String PROJTABLENAME		= "零售收单室项目情况表";
	public static final String PROJSCHDTABLENAME	= "零售收单室项目排期表";
	public static final String SCHDTABLENAME		= "零售收单室工作排期表";
	public static final String STATABLENAME		= "零售收单室工作情况表";
	public static final String OPERTABLENAME		= "零售收单室运维登记表";
	public static final String INNOTABLENAME		= "零售收单室创新登记表";
	public static final String EMERTABLENAME		= "零售收单室特急登记表";
	
	public static final String MYBATISCONFIG		= "chiphead/config/mybatis-config.xml";
	
	public static final int jichuTeam = 1;
	public static final int yewuTeam = 2;
	public static final int yingyongTeam = 3;
	public static final int otherTeam = 4;//4、5暂时不用，合并显示
	public static final int allTeam = 5;
	
	public static final String jichuSheetName		= "收单基础组";
	public static final String yewuSheetName		= "收单业务组";
	public static final String yingyongSheetName	= "收单应用组";
	public static final String otherSheetName		= "其他";
	
	public static final boolean debugMode = false;
	
	//暂未使用
	public static final String schdTableFillInstruction = 
			"填表说明：\n" + 
			"1、任务负责人、项目经理、技术经理对应的“类型、项目名称”选项用加深字符；\n" +
			"2、排期表中需求相关工作简写为“需求”、设计相关工作简写为“设计”、程序实现和编码相关工作简写为“开发”、自测和联调相关工作简写为“UT”、ST测试相关工作简写为“ST”、UAT测试相关工作简写为“UAT”、上线投产相关工作简写为“上线”、现场值班等相关工作简写为“运维”、代码检视、方案评审等评审类工作简写为“评审”\n" +
			"3、周计划工作量、周实际完成工作量超过7小时的填充底色“黄色”；\n" + 
			"4、上周实际工作量以“/小时数”的形式表示，如“/5.5”表示上周此项工作实际用5.5工时；\n" +
			"5、项目名称中“（辅办）”前缀表示作为项目成员加入到其他部室的项目之中，“（支持）”前缀表示仅提供支持服务没有开发任务。";
	
	
	
}
