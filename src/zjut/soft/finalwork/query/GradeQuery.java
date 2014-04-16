/**
  * @Author Benson
  * @Time 2013-11-21
  */
package zjut.soft.finalwork.query;

import java.util.List;

import zjut.soft.finalwork.beans.CourseInfo;

/**
 * 学生成绩查询
 * @author tsw
 *
 */
public interface GradeQuery {

	/**
	 * 按学期查询普通成绩
	 * @param time 学期
	 * @return 所修课程信息列表
	 */
	List<CourseInfo> gradeQueryBySemester(String time);
	
	/**
	 * 按学年查询普通成绩
	 * @param time 学年
	 * @return 所修课程信息列表
	 */
	List<CourseInfo> gradeQueryByAcademic(String time);	
}
