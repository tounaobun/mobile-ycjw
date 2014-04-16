/**
  * @Author Benson
  * @Time 2013-11-28
  */
package zjut.soft.finalwork.ui;

import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.ListWheelAdapter;
import kankan.wheel.widget.WheelAdapter;
import kankan.wheel.widget.WheelView;
import zjut.soft.finalwork.R;
import zjut.soft.finalwork.core.YCApplication;
import zjut.soft.finalwork.query.StudentQueryManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class ChooseSemesterUI extends Activity {

	private Button backBtn,nextBtn;
	private WheelView semesterWV;
	private List<String> semesterItems;
	private Handler mHandler;
	private WheelAdapter adapter;
	private YCApplication app;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_semester_ui);
		
		initComponents();
		
		loadSemesterItems();
	}

	private void loadSemesterItems() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					List<String> items = new StudentQueryManager().getTestArrangementQuery().getSemesters(ChooseSemesterUI.this);
					if(semesterItems != null) {
						semesterItems.clear();
						semesterItems.addAll(items);
					}
					mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							adapter = new ListWheelAdapter<String>(semesterItems);
							semesterWV.setAdapter(adapter);
							semesterWV.setCurrentItem((Integer)app.get("selected_semester_index"));
							nextBtn.setText("下一步");
							nextBtn.setEnabled(true);
						}
					});
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	private void initComponents() {
		mHandler = new Handler();
		app = (YCApplication) getApplication();
		backBtn = (Button) findViewById(R.id.choose_semester_ui_back_btn);
		nextBtn = (Button) findViewById(R.id.choose_semester_next_step);
		semesterWV = (WheelView) findViewById(R.id.semester_wv);
		
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ChooseSemesterUI.this.finish();
			}
		});
		
		nextBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ChooseSemesterUI.this,TestArrangementUI.class);
				i.putExtra("semester", semesterItems.get(semesterWV.getCurrentItem()));
				startActivity(i);
			}
		});
		
		semesterItems = new ArrayList<String>();
		semesterItems.add("正在加载中...");
		adapter = new ListWheelAdapter<String>(semesterItems);
		semesterWV.setAdapter(adapter);
		
		nextBtn.setEnabled(false);	// 加载完数据前不可点击
		nextBtn.setText("请稍等...");
	}
}
