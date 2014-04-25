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
package zjut.soft.finalwork.fragment;

import java.io.BufferedReader;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import zjut.soft.finalwork.R;
import zjut.soft.finalwork.core.YCApplication;
import zjut.soft.finalwork.ui.BasicInfoUI;
import zjut.soft.finalwork.util.Constant;
import android.R.raw;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class LeftFragment extends Fragment implements View.OnClickListener {

	private TextView userConfTV;	// �û�����
	private TextView payQueryTV;    // �ɷѲ�ѯ
	private TextView querySystemTV;	// ��ѯϵͳ
	private TextView aboutUsTV;	    // ��������
	private TextView unRegisterTV;	// ע��ϵͳ
	
	// newly added
	private TextView pickCourseTV;  // ѡ��ϵͳ
	private TextView RatingTV;      // ѧ������
	private TextView registTV;		// ����ϵͳ
	
	private TextView classNameTV;   // �༶����
	private ImageView headTV;
	private ImageView iv1,iv2,iv3,iv4,iv5;	// ѡ�м�ͷ
	
	private Bitmap portraitBitmap;
	private ItemClickListener instance;
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			headTV.setImageBitmap((Bitmap)msg.obj);
			((YCApplication)getActivity().getApplication()).put("portrait",portraitBitmap);
		}
	};
	
	public interface ItemClickListener {
		void onItemClick(int rank);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		if(activity instanceof ItemClickListener) {
			instance = (ItemClickListener)activity;
		} else {
			throw new RuntimeException("Can't cast to ItemClickListener!");
		}
	}

	
	@Override
	public void onResume() {
		super.onResume();
		unRegisterTV.setOnClickListener(this);
		userConfTV.setOnClickListener(this);
		payQueryTV.setOnClickListener(this);
		querySystemTV.setOnClickListener(this);
		aboutUsTV.setOnClickListener(this);
		
		registTV.setOnClickListener(this);
		pickCourseTV.setOnClickListener(this);
		RatingTV.setOnClickListener(this);
	}

	public void setClassName(String name) {
		this.classNameTV.setText(name);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	  View view = inflater.inflate(R.layout.left, null);
	  
	  iv1 = (ImageView) view.findViewById(R.id.left_iv1);
	  iv2 = (ImageView) view.findViewById(R.id.left_iv2);
	  iv3 = (ImageView) view.findViewById(R.id.left_iv3);
	  iv4 = (ImageView) view.findViewById(R.id.left_iv4);
	  iv5 = (ImageView) view.findViewById(R.id.left_iv5);
	  headTV = (ImageView) view.findViewById(R.id.sliding_activity_head_view);
	  headTV.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(getActivity(),BasicInfoUI.class);
			i.putExtra("myportrait", portraitBitmap);
			getActivity().startActivity(i);
			getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		}
	});
      TextView tv = (TextView) view.findViewById(R.id.sliding_activity_name_tv);
      YCApplication app = (YCApplication)this.getActivity().getApplication();
      tv.setText(app.get("name").toString());	// ��������
//      portrait = (ImageView) view.findViewById(R.id.sliding_activity_head_view);
      
      
      final String url = ((YCApplication)getActivity().getApplication()).get("selectedIp") + Constant.portraitContext;

      new Thread(new Runnable() {
		
		@Override
		public void run() {
			HttpGet get = new HttpGet(url);

			try {
				DefaultHttpClient client = ((YCApplication) getActivity()
						.getApplicationContext()).getClient();
				synchronized (client) {
					HttpResponse response = client.execute(get);
					HttpEntity entity = response.getEntity();
					InputStream is = entity.getContent();
					
					portraitBitmap = BitmapFactory.decodeStream(is); // �ؼ���������
					is.close();
					Message msg = mHandler.obtainMessage();
					msg.obj = portraitBitmap;
					mHandler.sendMessage(msg);
				}
				

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}).start();
      

      TextView username = (TextView) view.findViewById(R.id.sliding_activity_username_textview);
      username.setText(app.get("username").toString());	// ����ѧ��
      userConfTV = (TextView) view.findViewById(R.id.sliding_activity_user_manage);	// �û�����
      payQueryTV = (TextView) view.findViewById(R.id.sliding_activity_pay_query);	// �ɷѲ�ѯ
      querySystemTV = (TextView) view.findViewById(R.id.sliding_activity_query_system);	// ��ѯϵͳ
      aboutUsTV = (TextView) view.findViewById(R.id.sliding_activity_about_us);	// ��������
      unRegisterTV = (TextView) view.findViewById(R.id.sliding_activity_unregister_user);	// ע��ϵͳ
      classNameTV =  (TextView) view.findViewById(R.id.sliding_activity_class_tv);	// �༶����
      
      registTV = (TextView) view.findViewById(R.id.sliding_activity_regist_system); // ����ϵͳ
      pickCourseTV = (TextView) view.findViewById(R.id.sliding_activity_pick_course_system); // ѡ��ϵͳ
      RatingTV = (TextView) view.findViewById(R.id.sliding_activity_student_rating);	// ѧ������
	  return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}


	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.sliding_activity_user_manage:
		case R.id.sliding_activity_pay_query:
		case R.id.sliding_activity_query_system:
		case R.id.sliding_activity_about_us:
		case R.id.sliding_activity_unregister_user:
		case R.id.sliding_activity_regist_system:
		case R.id.sliding_activity_pick_course_system:
		case R.id.sliding_activity_student_rating:
			selectedItemWithArrow(v.getId());
			instance.onItemClick(v.getId());
			break;
		}
	}

	private void selectedItemWithArrow(int id) {
		ImageView[] ivs = new ImageView[]{iv1,iv2,iv3,iv4,iv5};
		// ���в��ɼ�
		for(ImageView iv : ivs) {
			iv.setVisibility(View.INVISIBLE);
		}
		
		switch(id) {
		case R.id.sliding_activity_user_manage:
			iv1.setVisibility(View.VISIBLE);
			break;
		case R.id.sliding_activity_query_system:
			iv2.setVisibility(View.VISIBLE);
			break;
		case R.id.sliding_activity_pay_query:
			iv3.setVisibility(View.VISIBLE);
			break;
		case R.id.sliding_activity_unregister_user:
			iv5.setVisibility(View.VISIBLE);
			break;
		case R.id.sliding_activity_about_us:
			iv4.setVisibility(View.VISIBLE);
			break;
		}
	}

}
