/**
  * @Author Benson
  * @Time 2013-11-22
  */
package zjut.soft.finalwork.query;

import java.util.List;

import zjut.soft.finalwork.beans.CourseInfo;

public class StudentGradeQuery implements GradeQuery {

	StudentGradeQuery() {
		
	}
	
	@Override
	public List<CourseInfo> gradeQueryBySemester(String time) {
		
		return null;
	}

	@Override
	public List<CourseInfo> gradeQueryByAcademic(String time) {
		return null;
	}

}
