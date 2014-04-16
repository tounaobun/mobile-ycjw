/**
  * @Author Benson
  * @Time 2013-11-28
  */
package zjut.soft.finalwork.query;

import java.util.List;

import zjut.soft.finalwork.beans.TestArrangeCourse;
import android.content.Context;

public interface TestArrangementQuery {

	
	/**
	 * 根据学期查找排考结果
	 * @param context 上下文
	 * @param semester 学期
	 * @return 排考结果列表
	 * @throws Exception 
	 */
	List<TestArrangeCourse> getTestArrangementBySemester(Context context,String semester) throws Exception;
	
	/**
	 * 第一次返回的排考结果
	 * @param context 上下文
	 * @return 排考结果列表
	 * @throws Exception
	 */
	public List<TestArrangeCourse> getTestArrangementFirstTime(Context context) throws Exception;
	
	/**
	 * 返回学期列表
	 * @return
	 */
	public List<String> getSemesters(Context context) throws Exception;
}
