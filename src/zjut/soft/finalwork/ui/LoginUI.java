/**
 * @Author Benson
 * @Time 2013-11-22
 */
package zjut.soft.finalwork.ui;

import zjut.soft.finalwork.R;
import zjut.soft.finalwork.core.StudentManager;
import zjut.soft.finalwork.core.YCApplication;
import zjut.soft.finalwork.core.YCStudentManager;
import zjut.soft.finalwork.dbs.DBManager;
import zjut.soft.finalwork.util.Constant;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class LoginUI extends Activity {

	private EditText passwordET;
	private AutoCompleteTextView userNameET;
	private Button loginBtn;
	private Spinner spinner;
	private AnimationDrawable mFrameAnimation;
	private static final String[] ips = { Constant.OUTER_URL,
			Constant.INNER_URL_1, Constant.INNER_URL_2 };
	private static final String[] ipNames = { "ԭ����������", "ԭ����������1", "ԭ����������2" };
	private Handler mHandler;
	private SharedPreferences sp;
	private YCApplication app;
	private DBManager dbManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_ui);
		initWidgets();
	}

	@Override
	public void onResume() {
		super.onResume();
		tryToAutoLogin();
	}
	
	private void tryToAutoLogin() {
		String username = sp.getString("username", null);
		String password = sp.getString("password",null);
		String url = sp.getString("url",null);
		System.out.println("username:" + username + "password:" + password);
		if(url != null && username != null && password != null) {
			tryToLogin(url,username,password);
		}
	}
	
	private void initWidgets() {
		dbManager = new DBManager(this);
		mHandler = new Handler();
		sp = getSharedPreferences(Constant.PREFERENCES_NAME, Context.MODE_PRIVATE);
		app = (YCApplication) getApplication();
		userNameET = (AutoCompleteTextView) findViewById(R.id.username_et);
		
		ArrayAdapter<String> unadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dbManager.query());
		userNameET.setAdapter(unadapter);
		
		passwordET = (EditText) findViewById(R.id.password_et);
		loginBtn = (Button) findViewById(R.id.login_btn);
		spinner = (Spinner) findViewById(R.id.login_spinner);

		loginBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				tryToLogin();
			}
		});

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.spinner_item, ipNames);

		// ���������б�ķ��
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// ��adapter ��ӵ�spinner��
		spinner.setAdapter(adapter);

		// Ĭ������
		spinner.setSelection(0);
		// ����¼�Spinner�¼�����
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String selectedIp = ips[arg2];
				((YCApplication) getApplication())
						.put("selectedIp", selectedIp);
				System.out.println(selectedIp);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	private void tryToLogin() {

		// Ĭ��
		((YCApplication) getApplication())
		.put("selectedIp", ips[spinner.getSelectedItemPosition()]);

		final String username = userNameET.getText().toString();
		final String password = passwordET.getText().toString();

		// ��Ч����֤
		if (username == null || username.equals("") || username.length() != 12) {
			Toast.makeText(this, "�û�����������,����������!", Toast.LENGTH_SHORT).show();
			return;
		}
		if (password == null || password.equals("")) {
			Toast.makeText(this, "������������,����������!", Toast.LENGTH_SHORT).show();
			return;
		}

		loginBtn.setEnabled(false);
		// ��¼���档ԲȦת��ת...
		loginBtn.setCompoundDrawablesWithIntrinsicBounds(0,
				R.drawable.login_progress_anim, 0, 0);
		loginBtn.setText("���ڵ�¼...");
		mFrameAnimation = (AnimationDrawable) loginBtn.getCompoundDrawables()[1];
		mFrameAnimation.start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				StudentManager manager = new YCStudentManager();
				final String url = ips[spinner.getSelectedItemPosition()];
				final String result = manager.login(LoginUI.this,url,username,
						password);
				if (result.equals("��¼�ɹ�")) {
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							mFrameAnimation.stop();
							
							// �洢�û���������
							sp.edit().putString("username", username)
							.putString("password", password)
							.putString("url", url)
							.commit();
							// �洢ѧ�ŵ����ݿ�
							dbManager.addUsername(username);
							
							loginBtn.setText("��¼�ɹ�");
							Intent i = new Intent();
							i.setClass(LoginUI.this, SlidingActivity.class);
							LoginUI.this.startActivity(i);
							LoginUI.this.finish();
						}
					});
				} else {
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							mFrameAnimation.stop();
							loginBtn.setCompoundDrawablesWithIntrinsicBounds(0,
									0, 0, 0);
							loginBtn.setText("��¼");
							loginBtn.setEnabled(true);
							Toast.makeText(LoginUI.this, result,
									Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
		}).start();
	}

	
	private void tryToLogin(final String url,final String username,final String password) {

		userNameET.setText(username);
		passwordET.setText(password);
		
		for(int i=0;i<ips.length;i++) {
			if(ips[i].equals(url)) {
				spinner.setSelection(i);
			}
		}
		userNameET.setEnabled(false);
		passwordET.setEnabled(false);
		spinner.setEnabled(false);
		loginBtn.setEnabled(false);
		// ��¼���档ԲȦת��ת...
		loginBtn.setCompoundDrawablesWithIntrinsicBounds(0,
				R.drawable.login_progress_anim, 0, 0);
		loginBtn.setText("���ڵ�¼...");
		mFrameAnimation = (AnimationDrawable) loginBtn.getCompoundDrawables()[1];
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mFrameAnimation.start();	// trick to turn it work
			}
		}, 100);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				StudentManager manager = new YCStudentManager();
				final String result = manager.login(LoginUI.this,url, username,
						password);
				if (result.equals("��¼�ɹ�")) {
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							mFrameAnimation.stop();
							
							// �洢�û���������
							sp.edit().putString("username", username)
							.putString("password", password)
							.putString("url", url)
							.commit();
							
							loginBtn.setText("��¼�ɹ�");
							Intent i = new Intent();
							i.setClass(LoginUI.this, SlidingActivity.class);
							LoginUI.this.startActivity(i);
							LoginUI.this.finish();
							overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
						}
					});
				} else {
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							mFrameAnimation.stop();
							loginBtn.setCompoundDrawablesWithIntrinsicBounds(0,
									0, 0, 0);
							loginBtn.setText("��¼");
							loginBtn.setEnabled(true);
							userNameET.setEnabled(true);
							passwordET.setEnabled(true);
							spinner.setEnabled(true);
							// �����������
							sp.edit().putString("username", null)
							.putString("password", null)
							.putString("url", null)
							.commit();
							Toast.makeText(LoginUI.this, result,
									Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
		}).start();
	
	}
	
}
