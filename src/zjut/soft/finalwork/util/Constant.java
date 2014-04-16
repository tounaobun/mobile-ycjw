/**
 * @Author Benson
 * @Time 2013-11-22
 */
package zjut.soft.finalwork.util;

public class Constant {
	// ////////////
	// web host
	public static final String PROTOCOL = "http://";
	public static final String YC_OUTER_HOST = "www.ycjw.zjut.edu.cn"; // ԭ������[������IP��ַ��������û�û�е�¼���������]
	public static final String YC_INNER_HOST_1 = "172.16.7.86"; // ԭ������1
	public static final String YC_INNER_HOST_2 = "172.16.7.83"; // ԭ������2
	public static final String PORT = ":80";

	public static final String OUTER_URL = PROTOCOL + YC_OUTER_HOST + PORT;
	public static final String INNER_URL_1 = PROTOCOL + YC_INNER_HOST_1 + PORT;
	public static final String INNER_URL_2 = PROTOCOL + YC_INNER_HOST_2 + PORT;

	public static final String loginContext = "/logon.aspx"; // ��¼

	// ͳһ�ַ�����
	public static final String ENCODING = "GBK";

	// ///////////
	// context
	public static final String portraitContext = "/stdgl/ksbm/Web_std_djk_tp.aspx"; // ͷ��

	public static final String modifyPassContext = "/stdgl/Usergl/std_XGMM.aspx"; // �޸�����

	public static final String levelQuery = "/stdgl/ksbm/Web_DJKCJ.aspx"; // �ȼ���ѯ

	public static final String gradeQuery = "/stdgl/cxxt/cjcx/Cjcx_Xsgrcj.aspx"; // �ɼ���ѯ

	public static final String payQuery = "/stdgl/cxxt/websfjf.aspx"; // �ɷѲ�ѯ

	public static final String gpaQuery = "/stdgl/cxxt/byshmx.aspx"; // �����ѯ

	public static final String testArrangeQuery = "/stdgl/cxxt/webpkjgcx.aspx"; // �ſ������ѯ

	public static final String developScheduleQuery = "/stdgl/cxxt/grpyjhcx.aspx"; //���������ƻ���ѯ
	
	public static final String courseTableQuery = "/stdgl/cxxt/Web_Std_XQKB.aspx"; // �α��ѯ
	
	public static final String PREFERENCES_NAME = "yc_jw"; // ���ݿ�����
}
