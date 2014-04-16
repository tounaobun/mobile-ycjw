/**
  * @Author Benson
  * @Time 2013-11-22
  */
package zjut.soft.finalwork.query;

/**
 * 学生成绩查询管理器
 * @author tsw
 *
 */
public interface QueryManager {

	GradeQuery getGradeQuery();
	
	TestArrangementQuery getTestArrangementQuery();
	
	DevelopmentScheduleQuery getDevelopmentScheduleQuery();
	
	CourseTableQuery getStudentCourseTableQuery();
}
