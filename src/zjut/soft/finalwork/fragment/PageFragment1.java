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
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import zjut.soft.finalwork.R;
import zjut.soft.finalwork.beans.LevelTest;
import zjut.soft.finalwork.core.YCApplication;
import zjut.soft.finalwork.util.Constant;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PageFragment1 extends Fragment {

	private TextView tv;
	private List<LevelTest> levelTest;
	private Handler mHandler;
	private StringBuilder info;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.page1, null);
		tv = (TextView) view.findViewById(R.id.page1_textview);
		mHandler = new Handler();
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	public void showLevelResult() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					 HttpGet get = new HttpGet(((YCApplication)getActivity().getApplication()).get("selectedIp") + Constant.levelQuery);
				        
				     YCApplication app = (YCApplication)getActivity().getApplicationContext();
				     HttpResponse response = app.getClient().execute(get);  
				     HttpEntity entity = response.getEntity();  
				     BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(),Constant.ENCODING));
				     StringBuilder sb = new StringBuilder();
				     String temp = null;
				     while((temp = br.readLine()) != null) {
				    	 sb.append(temp);
				     }
				            
					Document doc = Jsoup.parse(sb.toString());
					
					Elements tables = doc.select("#DJKCJ");
					if(tables.size() > 0) {
						Element table = tables.get(0);
						Elements trs = table.select("tr");
						levelTest = new ArrayList<LevelTest>();
						info = new StringBuilder();
						if(trs.size() > 1) {
							for(int i = 1; i < trs.size(); i++) {
								LevelTest test = new LevelTest();
								Element tr = trs.get(i);
								Elements tds = tr.select("td");
								String name = tds.get(0).select("span").get(0).html();
								String grade = tds.get(1).select("span").get(0).html();
								String date = tds.get(2).select("span").get(0).html();
								System.out.println(name + "," + grade + "," + date);
								info.append(name + "," + grade + "," + date + "\n");
								test.setName(name);
								test.setGrade(grade);
								test.setDate(date);
								levelTest.add(test);
								
							}
						}
					}
					mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							tv.setText(info.toString());
						}
					});
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
}

