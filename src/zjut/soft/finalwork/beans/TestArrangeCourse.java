/**
  * @Author Benson
  * @Time 2013-11-28
  */
package zjut.soft.finalwork.beans;

/**
 * øº ‘∞≤≈≈øŒ≥Ã
 * @author tsw
 *
 */
public class TestArrangeCourse {

	private String className;
	
	private String teacherName;
	
	private String courseName;
	
	private String testDate;
	
	private String dayPeriod;
	
	private String classroomName;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public String getDayPeriod() {
		return dayPeriod;
	}

	public void setDayPeriod(String dayPeriod) {
		this.dayPeriod = dayPeriod;
	}

	public String getClassroomName() {
		return classroomName;
	}

	public void setClassroomName(String classroomName) {
		this.classroomName = classroomName;
	}

	@Override
	public String toString() {
		return "TestArrangeCourse [className=" + className + ", teacherName="
				+ teacherName + ", courseName=" + courseName + ", testDate="
				+ testDate + ", dayPeriod=" + dayPeriod + ", classroomName="
				+ classroomName + "]";
	}
	
}
