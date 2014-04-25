/**
  * @Author Benson
  * @Time 2013-11-28
  */
package zjut.soft.finalwork.ui;

import zjut.soft.finalwork.R;
import zjut.soft.finalwork.query.StudentQueryManager;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StudentCourseTableQueryUI extends Activity {

	private WebView wv;
	private TextView title;
	private Handler mHandler;
	private MyHandler mmHandler;
	private String htmlString;
	private Button backBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.student_course_table_query);
		
		init();
	}

	private void init() {
		mHandler = new Handler();
		mmHandler = new MyHandler();
		wv = (WebView) findViewById(R.id.student_course_table_query_ui_webview);
		title = (TextView) findViewById(R.id.student_course_table_query_ui_title);
		title.setText(getIntent().getStringExtra("semester"));
		backBtn = (Button) findViewById(R.id.student_course_table_query_ui_back_btn);
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				StudentCourseTableQueryUI.this.finish();
			}
		});
		Toast.makeText(this, "正在加载数据,请稍等...",Toast.LENGTH_SHORT).show();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					
					htmlString = new StudentQueryManager().getStudentCourseTableQuery().getCourseTableBySemester(StudentCourseTableQueryUI.this, getIntent().getStringExtra("semester"));
					mmHandler.sendEmptyMessage(0);
				} catch (final Exception e) {
					e.printStackTrace();
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(StudentCourseTableQueryUI.this, e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					});
				}
				
			}
		}).start();
	}
	class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == 0)
				wv.loadDataWithBaseURL(null, htmlString, "text/html", "utf-8", null);
		}
	}
}
