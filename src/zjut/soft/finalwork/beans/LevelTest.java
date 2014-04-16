/**
  * @Author Benson
  * @Time 2013-11-22
  */
package zjut.soft.finalwork.beans;

/**
 * 等级考试查询
 * @author tsw
 *
 */
public class LevelTest {

	/**
	 * 等级名称
	 */
	private String name;
	
	/**
	 * 成绩
	 */
	private String grade;
	
	/**
	 * 考试时间
	 */
	private String date;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "LevelTest [name=" + name + ", grade=" + grade + ", date="
				+ date + "]";
	}
	
}
