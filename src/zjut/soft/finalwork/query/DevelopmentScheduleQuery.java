/**
  * @Author Benson
  * @Time 2013-11-28
  */
package zjut.soft.finalwork.query;

import android.content.Context;

/**
 * 个人培养计划查询
 * @author tsw
 *
 */
public interface DevelopmentScheduleQuery {

	/**
	 * 返回个人培养计划查询HTML
	 * @param context
	 * @return
	 */
	String getDevelopmentScheduleQueryInfo(Context context)  throws Exception;
}
