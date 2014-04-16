/**
 * @Author Benson
 * @Time 2013-11-26
 */
package zjut.soft.finalwork.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import zjut.soft.finalwork.R;
import zjut.soft.finalwork.core.YCApplication;
import zjut.soft.finalwork.util.Constant;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GraduationGPAQueryUI extends Activity {

	private TextView gpaTV;
	private Button backBtn;
	private Handler mHandler;
	private Animation anim;
	private ImageView refreshIV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.graduation_gpa_query_ui);

		initComponents();
		
		getGraduationGPA();
	}

	private void initComponents() {
		mHandler = new Handler();
		gpaTV = (TextView) findViewById(R.id.graduation_gpa_query_ui_tv);
		backBtn = (Button) findViewById(R.id.graduation_gpa_query_ui_btn);
		refreshIV = (ImageView) findViewById(R.id.gpa_refresh);
		refreshIV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				getGraduationGPA();
			}
		});
		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				GraduationGPAQueryUI.this.finish();
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			}
		});
	}

	private void animateScale() {
		anim = new AlphaAnimation(1.0f,0.0f);
		anim.setDuration(1000);//���ö�������ʱ�� 
		/** ���÷��� */ 
		anim.setRepeatCount(3);//�����ظ����� 
		//animation.setFillAfter(boolean);//����ִ������Ƿ�ͣ����ִ�����״̬ 
//		anim.setStartOffset(150);//ִ��ǰ�ĵȴ�ʱ�� 
		
		gpaTV.setAnimation(anim);
		anim.startNow();
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
	}

	private void getGraduationGPA() {
		gpaTV.setTextColor(Color.GRAY);
		gpaTV.setText("��ѯ��...");
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					HttpGet get = new HttpGet(
							((YCApplication) getApplication())
									.get("selectedIp") + Constant.gpaQuery);
					YCApplication app = (YCApplication) getApplication();
					HttpResponse response = app.getClient().execute(get);
					HttpEntity entity = response.getEntity();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(entity.getContent(),
									Constant.ENCODING));
					String temp = null;
					while ((temp = br.readLine()) != null) {
						if (temp.contains("lb_jd")) {
							break;
						}
					}
					if (temp != null) {
						// <span
						// id="lb_jd">3.119</span>�������㽭��ҵ��ѧ����ѧʿѧλ�����а취������ѧȫ���γ�ƽ������С��1.5�ߣ���������ѧʿѧλ��
						Document doc = Jsoup.parse(temp);
						final String gpa = doc.select("#lb_jd").html();
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								BigDecimal gpaV = new BigDecimal(gpa.trim());
								BigDecimal minV = new BigDecimal("1.5");
								if(gpaV.compareTo(minV) == 1 || gpaV.compareTo(minV) == 0){
									gpaTV.setTextColor(Color.GREEN);
									gpaTV.setText(gpaV.toString());
									animateScale();
								} else if(gpaV.compareTo(minV) == -1) {
									gpaTV.setTextColor(Color.RED);
									gpaTV.setText(gpaV.toString());
									animateScale();
								}
							}

						});
					} else {
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								gpaTV.setTextColor(Color.RED);
								gpaTV.setText("�޼�����Ϣ");
							}
						});
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}
}
