/**
  * @Author Benson
  * @Time 2013-11-22
  */
package zjut.soft.finalwork.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import zjut.soft.finalwork.R;
import zjut.soft.finalwork.core.YCApplication;
import zjut.soft.finalwork.core.YCStudentManager;
import zjut.soft.finalwork.util.Constant;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditPassUI extends Activity {

	private EditText usernameET,oldPassET,newPassET,confPassET;
	private Button sureBtn,backBtn;
	private YCApplication app;
	private Handler handler;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == 1) {

			} else if(msg.what == 0) {
				Toast.makeText(EditPassUI.this, "�����޸�ʧ��!", Toast.LENGTH_SHORT).show();
				sureBtn.setEnabled(true);
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit_pass_ui);
		
		initWidgets();
	}

	private void initWidgets() {
		handler = new Handler();
		app = (YCApplication) getApplication();
		usernameET = (EditText) findViewById(R.id.edit_pass_username);
		oldPassET = (EditText) findViewById(R.id.edit_pass_old_pass);
		newPassET = (EditText) findViewById(R.id.edit_pass_new_pass);
		confPassET = (EditText) findViewById(R.id.edit_pass_confirm_pass);
		sureBtn = (Button) findViewById(R.id.edit_pass_sure);
		backBtn = (Button) findViewById(R.id.edit_pass_back);
		
		usernameET.setText(app.get("username").toString());
		
		sureBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tryToModifyPass();
			}
		});
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditPassUI.this.finish();
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			}
		});
	}

	private void tryToModifyPass() {
		final String oldPass = oldPassET.getText().toString();
		final String newPass = newPassET.getText().toString();
		final String confPass = confPassET.getText().toString();
		
		// ��֤
		if(!newPass.equals(confPass)) {
			Toast.makeText(this, "�����������벻һ��!", Toast.LENGTH_SHORT).show();
			return;
		}
		if(!oldPass.equals(app.get("password"))) {
			Toast.makeText(this, "���������벻��ȷ!", Toast.LENGTH_SHORT).show();
			return;
		}
		// λ��������֤��1λ��Ҳ��
		sureBtn.setCompoundDrawablesWithIntrinsicBounds(0,
				R.drawable.login_progress_anim, 0, 0);
		sureBtn.setText("�޸���...");
		AnimationDrawable mFrameAnimation = (AnimationDrawable) sureBtn.getCompoundDrawables()[1];
		mFrameAnimation.start();
		sureBtn.setEnabled(false);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					// ����POST����
					List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
					urlParameters.add(new BasicNameValuePair("Edt_OldUser", app.get("username").toString()));
					urlParameters.add(new BasicNameValuePair("Edt_OldPassWord", oldPass));
					urlParameters.add(new BasicNameValuePair("Edt_NewPassWord", newPass));
					urlParameters.add(new BasicNameValuePair("Edt_QRPassWord", confPass));				
					urlParameters.add(new BasicNameValuePair("ImageButton1.x", "0"));
					urlParameters.add(new BasicNameValuePair("ImageButton1.y", "0"));
					
					String loginUrl = ((YCApplication)getApplication()).get("selectedIp") + Constant.modifyPassContext;
					
					Elements elements = Jsoup.connect(loginUrl).get().select("input");
					for(Element element : elements ) {
						if("__VIEWSTATE".equals(element.attr("name"))) {
							urlParameters.add(new BasicNameValuePair("__VIEWSTATE", element.val()));
						}
					}
					
					// ������Ϣ
					for(NameValuePair pair : urlParameters) {
						Log.i(YCStudentManager.class.getName(),pair.getName() + ":" + pair.getValue());
					}
					// POST�����������
					
					HttpPost post = new HttpPost(loginUrl);
					post.setEntity(new UrlEncodedFormEntity(urlParameters,Constant.ENCODING));

					HttpResponse response =  app.getClient().execute(post);
			 
					BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),Constant.ENCODING),8192);
					String line = null;
					while ((line = rd.readLine()) != null) {
						if(line.contains("�����޸ĳɹ�")) {
							handler.post(new Runnable() {
								@Override
								public void run() {
									// �޸Ļ�������ֵ
									app.put("password", newPass);	// ����ԭ���ľ�����
									getSharedPreferences(Constant.PREFERENCES_NAME, Context.MODE_PRIVATE)
									.edit().putString("password", newPass).commit(); // ���Ǳ����ļ�����
									Toast.makeText(EditPassUI.this, "�����޸ĳɹ�!", Toast.LENGTH_SHORT).show();
									sureBtn.setText("�޸ĳɹ�");
									EditPassUI.this.finish();
									overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
								}
							});

							break;
						} else {
							// ò�Ʋ�����else�ˡ�
						}
					}
				}catch(Exception e) {
					e.printStackTrace();
					mHandler.sendEmptyMessage(0);
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							Toast.makeText(EditPassUI.this, "�����޸�ʧ��!", Toast.LENGTH_SHORT).show();
							sureBtn.setEnabled(true);
						}
					});
				}	
			} 
		}).start();
	}

	
	
}
