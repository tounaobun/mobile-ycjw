/*
 * Copyright (C) 2012 yueyueniao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package zjut.soft.finalwork.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;

import zjut.soft.finalwork.R;
import zjut.soft.finalwork.core.YCApplication;
import zjut.soft.finalwork.core.YCStudentManager;
import zjut.soft.finalwork.fragment.LeftFragment;
import zjut.soft.finalwork.fragment.LeftFragment.ItemClickListener;
import zjut.soft.finalwork.fragment.PayQueryFragment;
import zjut.soft.finalwork.fragment.QuerySystemFragment;
import zjut.soft.finalwork.fragment.RightFragment;
import zjut.soft.finalwork.fragment.UserConfFragment;
import zjut.soft.finalwork.fragment.ViewPageFragment;
import zjut.soft.finalwork.util.Constant;
import zjut.soft.finalwork.view.SlidingMenu;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Window;

public class SlidingActivity extends FragmentActivity implements ItemClickListener{
	SlidingMenu mSlidingMenu;
	LeftFragment leftFragment;
	RightFragment rightFragment;
	ViewPageFragment viewPageFragment;
	private Timer timer;
	private YCApplication app;
	private SharedPreferences sp;
	private Handler mHandler;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		init();
//		initListener();
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				showLeft();	//  转向导航菜单
				updateClassName();  // 更新班级
			}
		}, 600	);
	}

	@Override
	public void onResume() {
		super.onResume();
	}
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(timer != null) {
			timer.cancel();	// 关闭定时任务(当前执行任务不受影响)
		}
		((YCApplication)getApplication()).clearAllCachedData();	// 清除缓存
	}
	
	/**
	 * 抓取班级名称
	 */
	private void updateClassName() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					HttpGet get = new HttpGet(
							((YCApplication) getApplication())
									.get("selectedIp") + Constant.gradeQuery);
					final YCApplication app = (YCApplication) getApplication();
					HttpResponse response = app.getClient().execute(get);
					HttpEntity entity = response.getEntity();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(entity.getContent(),
									Constant.ENCODING));
					String temp = null;
					String keyWord = "lblBjmc";
					while ((temp = br.readLine()) != null) {
						if(temp.contains(keyWord)) {
							break;
						}
					}
					final String value = Jsoup.parse(temp).select("#" + keyWord).html().substring(3);
					
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							app.put("className", value);
							leftFragment.setClassName(value);
						}
					});
				} catch (final Exception e) {
					e.printStackTrace();
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							leftFragment.setClassName(e.getMessage());							
						}
					});
				}
			}
		}).start();
	}
	
	private void init() {
		sp = getSharedPreferences(Constant.PREFERENCES_NAME, Context.MODE_PRIVATE);
		mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
		mHandler = new Handler();
//		mSlidingMenu.setCanSliding(true,false);	// 可左
//		mSlidingMenu.setCanSliding(false, true);	// 可右
//		mSlidingMenu.setCanSliding(true, true); // 可左可右
		mSlidingMenu.setLeftView(getLayoutInflater().inflate(
				R.layout.left_frame, null));
		mSlidingMenu.setRightView(getLayoutInflater().inflate(
				R.layout.right_frame, null));
		mSlidingMenu.setCenterView(getLayoutInflater().inflate(
				R.layout.center_frame, null));

		FragmentTransaction t = this.getSupportFragmentManager()
				.beginTransaction();
		leftFragment = new LeftFragment();
		t.replace(R.id.left_frame, leftFragment);

		rightFragment = new RightFragment();
		t.replace(R.id.right_frame, rightFragment);

//		viewPageFragment = new ViewPageFragment();
		UserConfFragment userConfFrag = new UserConfFragment();
		t.replace(R.id.center_frame, userConfFrag);
		t.commit();
		
		app = (YCApplication) getApplication();
		timeSchedule();
	}

	public void showLeft() {
		mSlidingMenu.showLeftView();
	}

	public void showRight() {
		mSlidingMenu.showRightView();
	}

	@Override
	public void onItemClick(int rank) {
		switch(rank) {
			case R.id.sliding_activity_user_manage:
				// 用户管理
				showLeft();
				UserConfFragment userConfFrag = new UserConfFragment();
				this.getSupportFragmentManager()
				.beginTransaction().replace(R.id.center_frame, userConfFrag)
				.commit();
				break;
			case R.id.sliding_activity_pay_query:
				// 缴费查询
				showLeft();
				PayQueryFragment payQueryFrag = new PayQueryFragment();
				this.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.center_frame, payQueryFrag)
				.commit();
				break;
			case R.id.sliding_activity_query_system:
				// 缴费查询
				showLeft();
				QuerySystemFragment querySystemFrag = new QuerySystemFragment();
				this.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.center_frame, querySystemFrag)
				.commit();
				break;
			case R.id.sliding_activity_about_us:
				//  关于我们
				showLeft();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						showRight();
					}
				}, 500);
				break;
			case R.id.sliding_activity_unregister_user:
				// 注销系统
				new AlertDialog.Builder(this).setTitle("注销系统").setMessage("您确定要注销吗?")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sp.edit().putString("username", null).putString("password", null)
						.putString("url", null).commit();
						Intent i = new Intent();
						i.setClass(SlidingActivity.this, LoginUI.class);
						startActivity(i);
						SlidingActivity.this.finish();
						overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
					}
				}).setNegativeButton("取消", null)
				.create().show();
				break;
		}
	}


    private void timeSchedule() {
		// 定时任务
		// 第一次隔10分钟，以后每隔15分钟重新登录一次
    	
    	// 备注 Asp.net session 超时默认为 20分钟
    	timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("尝试重连");
				String result = new YCStudentManager().login(getApplicationContext(), app.get("selectedIp").toString(),app.get("username").toString(), app.get("password").toString());
				System.out.println(result);
			}
		}, 10 * 60 * 1000, 15 * 60 * 1000);
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		System.out.println("right:" + mSlidingMenu.hasClickRight());
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			
			if(!mSlidingMenu.hasClickLeft() &&  !mSlidingMenu.hasClickRight()) {
				mSlidingMenu.showLeftView();
			} else if (mSlidingMenu.hasClickRight()) {
				mSlidingMenu.showRightView();
			}
				else {
				new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("您确实要退出吗?")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						DefaultHttpClient client = ((YCApplication)getApplication()).getClient();
						client = null;
						Runtime.getRuntime().gc();
						SlidingActivity.this.finish();
						overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
					}
				}).setNegativeButton("取消", null).create().show();
			}
		}
		return true;
	}

	private void setBothSliding() {
		if(mSlidingMenu.hasClickLeft()) {
			mSlidingMenu.setCanSliding(true, false);
		} else if(mSlidingMenu.hasClickRight()) {
			mSlidingMenu.setCanSliding(false, true);
		} else {
			mSlidingMenu.setCanSliding(false,false);
		}
	}
//	
//	private void setBothSlidingForRight() {
//		if(mSlidingMenu.hasClickRight()) {
//			mSlidingMenu.setCanSliding(false, true);
//		} else if(mSlidingMenu.hasClickLeft()) {
//			mSlidingMenu.setCanSliding(true, false);
//		} else {
//			mSlidingMenu.setCanSliding(false,false);
//		}
//	}
	
//	private void initListener() {
//	viewPageFragment.setMyPageChangeListener(new MyPageChangeListener() {
//		
//		@Override
//		public void onPageSelected(int position) {
//			if(viewPageFragment.isFirst()){
//				mSlidingMenu.setCanSliding(true,false);
//			}else if(viewPageFragment.isEnd()){
//				mSlidingMenu.setCanSliding(false,true);
//			}else{
//				mSlidingMenu.setCanSliding(false,false);
//			}
//		}
//	});
//}
}
