/**
 * @Author Benson
 * @Time 2013-11-22
 */
package zjut.soft.finalwork.util;

public class Constant {
	// ////////////
	// web host
	public static final String PROTOCOL = "http://";
	public static final String YC_OUTER_HOST = "www.ycjw.zjut.edu.cn"; // 原创外网[不能用IP地址，会造成用户没有登录的奇怪现象]
	public static final String YC_INNER_HOST_1 = "172.16.7.86"; // 原创内网1
	public static final String YC_INNER_HOST_2 = "172.16.7.83"; // 原创内网2
	public static final String PORT = ":80";

	public static final String OUTER_URL = PROTOCOL + YC_OUTER_HOST + PORT;
	public static final String INNER_URL_1 = PROTOCOL + YC_INNER_HOST_1 + PORT;
	public static final String INNER_URL_2 = PROTOCOL + YC_INNER_HOST_2 + PORT;

	public static final String loginContext = "/logon.aspx"; // 登录

	// 统一字符编码
	public static final String ENCODING = "GBK";

	// ///////////
	// context
	public static final String portraitContext = "/stdgl/ksbm/Web_std_djk_tp.aspx"; // 头像

	public static final String modifyPassContext = "/stdgl/Usergl/std_XGMM.aspx"; // 修改密码

	public static final String levelQuery = "/stdgl/ksbm/Web_DJKCJ.aspx"; // 等级查询

	public static final String gradeQuery = "/stdgl/cxxt/cjcx/Cjcx_Xsgrcj.aspx"; // 成绩查询

	public static final String payQuery = "/stdgl/cxxt/websfjf.aspx"; // 缴费查询

	public static final String gpaQuery = "/stdgl/cxxt/byshmx.aspx"; // 绩点查询

	public static final String testArrangeQuery = "/stdgl/cxxt/webpkjgcx.aspx"; // 排考结果查询

	public static final String developScheduleQuery = "/stdgl/cxxt/grpyjhcx.aspx"; //个人培养计划查询
	
	public static final String courseTableQuery = "/stdgl/cxxt/Web_Std_XQKB.aspx"; // 课表查询
	
	public static final String PREFERENCES_NAME = "yc_jw"; // 数据库名称
}
