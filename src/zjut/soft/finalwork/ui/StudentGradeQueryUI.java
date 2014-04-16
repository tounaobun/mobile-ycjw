/**
  * @Author Benson
  * @Time 2013-11-24
  */
package zjut.soft.finalwork.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import zjut.soft.finalwork.R;
import zjut.soft.finalwork.beans.CourseInfo;
import zjut.soft.finalwork.core.YCApplication;
import zjut.soft.finalwork.core.YCStudentManager;
import zjut.soft.finalwork.util.Constant;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class StudentGradeQueryUI extends Activity {

	private Handler mHandler;
	private TextView titleTV;
	private ListView mListView;
	private int queryType,timeType;
	private String gradeDate;
	private ProgressBar pb;
	private Button backBtn;
	private ImageView refreshIV;
	private ArrayAdapter<CourseInfo> adapter;
	private List<CourseInfo> courseInfos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.student_grade_query_ui);
		
		mHandler = new Handler();
		
		mListView = (ListView) findViewById(R.id.student_grade_query_ui_lv);
		
		YCApplication app = (YCApplication)getApplication();
		
		queryType = (Integer) app.get("query_type");
		timeType = (Integer) app.get("time_type");
		
		gradeDate = (String) app.get("grade_date");
		
		titleTV = (TextView) findViewById(R.id.student_query_ui_frag_title);
		
		pb = (ProgressBar) findViewById(R.id.student_grade_query_progressbar);
		
		backBtn = (Button) findViewById(R.id.student_grade_query_ui_btn);
		refreshIV = (ImageView) findViewById(R.id.student_grade_refresh);
		
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				StudentGradeQueryUI.this.finish();
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			}
		});
		
		refreshIV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				queryResultInBackground();
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		createTitle(queryType,timeType,gradeDate);
		queryResultInBackground();
	}
	
	private void queryResultInBackground() {
		
		// 清除之前的数据
		if(courseInfos != null && courseInfos.size() > 0) {
			courseInfos.clear();
			adapter.notifyDataSetChanged();
			mListView.setVisibility(View.GONE);
			pb.setVisibility(View.VISIBLE);
		}
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
				// 学期还是学年
				if(timeType == 0) {
					urlParameters.add(new BasicNameValuePair("1", "rbtnXq"));	
					urlParameters.add(new BasicNameValuePair("ddlXq", gradeDate));
				} else if (timeType == 1){
					urlParameters.add(new BasicNameValuePair("1", "rbtnXn"));	
					urlParameters.add(new BasicNameValuePair("ddlXn", gradeDate));
				}
				// 查询所有课程
				urlParameters.add(new BasicNameValuePair("ddlKc", "＝所有课程＝"));
				
				if(queryType == 0) {
					urlParameters.add(new BasicNameValuePair("Button1", "普通考试成绩查询"));
				} else if(queryType == 1) {
					urlParameters.add(new BasicNameValuePair("Button2", "普通考试不合成绩格查询"));
				} else if (queryType == 2) {
					urlParameters.add(new BasicNameValuePair("Button3", "补考课程及成绩查询"));
				} else if (queryType == 3) {
					urlParameters.add(new BasicNameValuePair("Button4", "重修课程及成绩查询"));
				}
				
				YCApplication app = (YCApplication)getApplication();
				HttpClient client = app.getClient();
				String url = app.get("selectedIp") + Constant.gradeQuery;
				HttpGet request = new HttpGet(url);
				try {
					HttpResponse response = client.execute(request);
					InputStream is = response.getEntity().getContent();
					BufferedReader br = new BufferedReader(new InputStreamReader(is,Constant.ENCODING));
					StringBuilder sb = new StringBuilder();
					String temp = null;
					while((temp = br.readLine()) != null) {
						if(temp.contains("<script language")) {
							break;	// 节约时间
						}
						sb.append(temp);
					}
					
					Document doc = Jsoup.parse(sb.toString());
					Elements elements = doc.select("input");
					for(Element element : elements ) {
						if("__EVENTTARGET".equals(element.attr("name"))) {
							urlParameters.add(new BasicNameValuePair("__EVENTTARGET", element.val()));
						} else if("__EVENTARGUMENT".equals(element.attr("name"))) {
							urlParameters.add(new BasicNameValuePair("__EVENTARGUMENT", element.val()));
						} else if("__VIEWSTATE".equals(element.attr("name"))) {
							urlParameters.add(new BasicNameValuePair("__VIEWSTATE", element.val()));
						}
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// 调试信息
				for(NameValuePair pair : urlParameters) {
					Log.i(YCStudentManager.class.getName(),pair.getName() + ":" + pair.getValue());
				}
				HttpPost post = new HttpPost(url);
				try {
					post.setEntity(new UrlEncodedFormEntity(urlParameters,Constant.ENCODING));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				HttpResponse response = null;
				try {
					response = app.getClient().execute(post);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				BufferedReader rd = null;
				try {
					rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),Constant.ENCODING),8192);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				String temp = null;
				StringBuilder sb = new StringBuilder();
				try {
					while((temp = rd.readLine()) != null) {
						sb.append(temp);
						System.out.println(temp);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Document doc = Jsoup.parse(sb.toString());

				Elements elements = doc.select("#DataGrid1");
				
				Elements e = elements.select("tr");
				
				courseInfos = new ArrayList<CourseInfo>();
				for(int i=1;i<e.size(); i++) {
					CourseInfo info = new CourseInfo();
					Elements tds = e.get(i).select("td");
					info.setSemester(" 实际学期:" + tds.get(0).select("font").html());
					info.setCourseName(" 课程名称:" + tds.get(1).select("font").html());
					info.setType(" 考试性质:" + tds.get(2).select("font").html());
					info.setGrade(" 成绩:" + tds.get(3).select("font").html());
					String classes = tds.get(4).select("font").html();
					if(classes.equals("&nbsp;")) {
						info.setClasses(" 学时:无");
					} else {
						info.setClasses(" 学时:" + tds.get(4).select("font").html());
					}
					String credit = tds.get(5).select("font").html();
					if(credit.equals("&nbsp;")) {
						info.setCredit(" 学分:无");
					} else {
						info.setCredit(" 学分:" + tds.get(5).select("font").html());
					}					
					courseInfos.add(info);
				}
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						pb.setVisibility(View.GONE);
						mListView.setVisibility(View.VISIBLE);
						if(courseInfos.size() == 0) {
							mListView.setVisibility(View.GONE);
							((TextView)findViewById(R.id.student_grade_hint)).setVisibility(View.VISIBLE);
						} else {
							adapter = new CourseInfoAdapter(StudentGradeQueryUI.this, R.layout.query_system_listview_grade_result, courseInfos);
							mListView.setAdapter(adapter);
						}
					}
				});
			}
		}).start();
	}
	
	private void createTitle(int queryType,int timeType,String date) {

		String queryName = null;
		if(queryType == 0) {
			queryName = "普通考试成绩查询";
		} else if(queryType == 1) {
			queryName = "普通考试不合格成绩查询";
		} else if(queryType == 2) {
			queryName = "补考课程及成绩查询";
		} else if(queryType == 3) {
			queryName = "重修课程及成绩查询";
		}
		titleTV.setText(queryName + " | " + date);
	}

	static class CourseInfoAdapter extends ArrayAdapter<CourseInfo> {

		private int resourceId;
		
		public CourseInfoAdapter(Context context, int textViewResourceId,
				List<CourseInfo> objects) {
			super(context, textViewResourceId, objects);
			this.resourceId = textViewResourceId;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			CourseInfo courseInfo = getItem(position);
			if(convertView == null) {
		        LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);   
		        convertView = vi.inflate(resourceId, null);
		        holder = new ViewHolder();
		        holder.actualSemester = (TextView)convertView.findViewById(R.id.query_system_listview_grade_result_actual_semesiter);
		        holder.courseName = (TextView)convertView.findViewById(R.id.query_system_listview_grade_result_course_name);
		        holder.property = (TextView)convertView.findViewById(R.id.query_system_listview_grade_result_property);
		        holder.grade = (TextView)convertView.findViewById(R.id.query_system_listview_grade_result_grade);
		        holder.hour = (TextView)convertView.findViewById(R.id.query_system_listview_grade_result_actual_hour);
		        holder.credit = (TextView)convertView.findViewById(R.id.query_system_listview_grade_result_actual_credit);
		        convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			int[] bgs = new int[] {
					R.drawable.ic_course_bg_fen,
					R.drawable.ic_course_bg_huang,
					R.drawable.ic_course_bg_lan,
					R.drawable.ic_course_bg_lv,
					R.drawable.ic_course_bg_pulan,
					R.drawable.ic_course_bg_qing,
					R.drawable.ic_course_bg_tao,
					R.drawable.ic_course_bg_zi
			};
			
			convertView.setBackgroundResource(bgs[position%bgs.length]);
	        holder.actualSemester.setText(courseInfo.getSemester());
			holder.courseName.setText(courseInfo.getCourseName());
			holder.property.setText(courseInfo.getType());
			holder.grade.setText(courseInfo.getGrade());
			holder.hour.setText(courseInfo.getClasses());
			holder.credit.setText(courseInfo.getCredit());		
	        
	        return convertView;  
		}

		static class ViewHolder {
	        TextView actualSemester;  
	        TextView courseName;  
	        TextView property;
	        TextView grade;  
	        TextView hour;  
	        TextView credit;
		}
		
	}
	
}
