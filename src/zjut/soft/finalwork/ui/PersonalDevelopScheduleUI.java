/**
  * @Author Benson
  * @Time 2013-11-28
  */
package zjut.soft.finalwork.ui;

import zjut.soft.finalwork.R;
import zjut.soft.finalwork.query.StudentQueryManager;
import zjut.soft.finalwork.util.Constant;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class PersonalDevelopScheduleUI extends Activity {

	private WebView wv;
	private String htmlString;
	private Handler mHandler;
	private Button backBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personal_develop_schedule_ui);
		
		init();
	}

	private void init() {
		backBtn = (Button) findViewById(R.id.personal_develop_schedule_ui_back_btn);
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PersonalDevelopScheduleUI.this.finish();
			}
		});
		wv = (WebView) findViewById(R.id.personal_develop_schedule_ui_webview);
		wv.getSettings().setJavaScriptEnabled(true);
		mHandler = new Handler();
		
		Toast.makeText(this, "正在加载数据,请稍等...",Toast.LENGTH_SHORT).show();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					htmlString = new StudentQueryManager().getDevelopmentScheduleQuery().getDevelopmentScheduleQueryInfo(PersonalDevelopScheduleUI.this);
					
					wv.loadDataWithBaseURL(null, htmlString, "text/html", "utf-8", null);
				} catch (final Exception e) {
					e.printStackTrace();
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(PersonalDevelopScheduleUI.this, e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
		}).start();
		
	}
}
