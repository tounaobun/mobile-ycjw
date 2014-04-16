/**
 * @Author Benson
 * @Time 2013-11-28
 */
package zjut.soft.finalwork.ui;

import java.util.List;

import zjut.soft.finalwork.R;
import zjut.soft.finalwork.beans.TestArrangeCourse;
import zjut.soft.finalwork.core.YCApplication;
import zjut.soft.finalwork.query.StudentQueryManager;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class TestArrangementUI extends Activity {

	private TableLayout tl;
	private ProgressBar pb;
	private Button backBtn;
	private List<TestArrangeCourse> courses;
	private Handler mHandler;
	private TextView title,subTitle,englishTV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.test_arrangement_ui);

		init();
	}
	
	private void init() {
		mHandler = new Handler();
		tl = (TableLayout) findViewById(R.id.test_arrangement_ui_tl);
		tl.setStretchAllColumns(true);
		englishTV = (TextView) findViewById(R.id.english);
		englishTV.setText(Html.fromHtml("<a href='http://www.jwc.zjut.edu.cn/kscx.asp'>教务处网站考试安排</a>"));
		englishTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.jwc.zjut.edu.cn/kscx.asp"));
				startActivity(i);	
			}
		});
		pb = (ProgressBar) findViewById(R.id.test_arrangement_ui_pb);
		title = (TextView) findViewById(R.id.test_arrangement_ui_title);
		title.setText(getIntent().getStringExtra("semester"));
		subTitle = (TextView) findViewById(R.id.test_arrangement_ui_sub_title);
		subTitle.setText("学生:" + ((YCApplication)getApplication()).get("name") + "的考试安排信息");
		backBtn = (Button) findViewById(R.id.test_arrangement_ui_btn);
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				TestArrangementUI.this.finish();
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			}
		});
		
		loadTestArrangementCourses();
	}
	
	private void loadTestArrangementCourses() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					courses = new StudentQueryManager()
					.getTestArrangementQuery()
					.getTestArrangementBySemester(TestArrangementUI.this, getIntent().getStringExtra("semester"));
					mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							fillTableWithTestArrangementCourses();
						}
					});
				} catch (final Exception e) {
					e.printStackTrace();
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(TestArrangementUI.this, e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
		}).start();
	}

	private void fillTableWithTestArrangementCourses() {
		if (courses.size() == 0) {
			Toast.makeText(this, "没有排考信息!", Toast.LENGTH_SHORT).show();
			if (pb != null && pb.getVisibility() == View.VISIBLE) {
				pb.setVisibility(View.GONE);
			}
			return;
		}
		for (TestArrangeCourse course : courses) {
			// 表行
			TableRow tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			// 表列
			// 班级名称
			TextView tv1 = new TextView(this);
			tv1.setBackgroundResource(R.drawable.cell_shape2);
			tv1.setText(course.getClassName());
			// 教师
			TextView tv2 = new TextView(this);
			tv2.setBackgroundResource(R.drawable.cell_shape2);
			tv2.setText(course.getTeacherName());
			// 执行课程名称
			TextView tv3 = new TextView(this);
			tv3.setBackgroundResource(R.drawable.cell_shape2);
			tv3.setText(course.getCourseName());
			// 考试日期
			TextView tv4 = new TextView(this);
			tv4.setBackgroundResource(R.drawable.cell_shape2);
			tv4.setText(course.getTestDate());
			// 考试时段
			TextView tv5 = new TextView(this);
			tv5.setBackgroundResource(R.drawable.cell_shape2);
			tv5.setText(course.getDayPeriod());
			// 教室名称
			TextView tv6 = new TextView(this);
			tv6.setBackgroundResource(R.drawable.cell_shape2);
			tv6.setText(course.getClassroomName());

			tr.addView(tv1);
			tr.addView(tv2);
			tr.addView(tv3);
			tr.addView(tv4);
			tr.addView(tv5);
			tr.addView(tv6);

			tl.addView(tr, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

		}
		pb.setVisibility(View.GONE);

	}

	

}
