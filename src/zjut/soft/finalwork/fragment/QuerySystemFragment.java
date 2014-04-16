/**
  * @Author Benson
  * @Time 2013-11-23
  */
package zjut.soft.finalwork.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import zjut.soft.finalwork.R;
import zjut.soft.finalwork.core.YCApplication;
import zjut.soft.finalwork.ui.ChooseSemesterUI;
import zjut.soft.finalwork.ui.ChooseSemesterUIForCourseTable;
import zjut.soft.finalwork.ui.GraduationGPAQueryUI;
import zjut.soft.finalwork.ui.LevelGradeQueryUI;
import zjut.soft.finalwork.ui.PersonalDevelopScheduleUI;
import zjut.soft.finalwork.ui.SlidingActivity;
import zjut.soft.finalwork.ui.StudentCourseTableQueryUI;
import zjut.soft.finalwork.ui.StudentGradeQueryUI;
import zjut.soft.finalwork.util.Constant;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class QuerySystemFragment extends Fragment {
	
	private GridView gridView;
	private ImageView menuIV,aboutTV;
	private Handler mHandler;
    private LayoutInflater inflater;
    private Spinner mSpinner;
    private ArrayAdapter<String> adapter3;	// 时间范围数据适配器
    private Button positveBtn;	// 时间范围确定按钮
    private List<String> datas = new ArrayList<String>();
    private SlidingActivity parent;
    
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.query_system_frag, null);
		gridView = (GridView) view.findViewById(R.id.query_system_frag_gridview);
		menuIV = (ImageView) view.findViewById(R.id.query_system_frag_menu);
		aboutTV = (ImageView) view.findViewById(R.id.query_system_frag_about_us);	
		mHandler = new Handler(getActivity().getMainLooper());
		
		datas.add("正在加载数据...");
		
		menuIV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				((SlidingActivity)getActivity()).showLeft();
			}
		});
		aboutTV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				parent.showRight();
			}
		});
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		parent = (SlidingActivity) activity;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		inflater = (LayoutInflater)getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		//添加元素给gridview
		gridView.setAdapter(new ImageAdapter(this.getActivity()));
//		gridView.setSelector(new ColorDrawable(Color.BLUE));	// 去掉点击黄色背景
				// 设置Gallery的背景
//		gridView.setBackgroundResource(R.drawable.bg0);

				//事件监听
		gridView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View v, int position, long id)
					{
						switch(position) {
						case 0:
							// 学生成绩查询
							studentGradeQuery();
							break;
						case 1:
							// 学生课表查询
							studentCourseTableQuery();
							break;
						case 2:
							// 个人培养计划查询
							personalDevelopmentScheduleQuery();
							break;
						case 3:
							// 个人排考结果查询
							personalTestArrangementQuery();
							break;
						case 4:
							// 毕业绩点查询
							graduationGPAQuery();
							break;
							
						case 5:
							// 等级考试成绩查询
							levelQuery();
							break;
						}
					}



				});
	}
	
	
	/**
	 * 个人培养计划查询
	 */
	private void personalDevelopmentScheduleQuery() {
		Intent i = new Intent(getActivity(),PersonalDevelopScheduleUI.class);
		startActivity(i);
	}
	
	/**
	 * 学生课表查询
	 */
	private void studentCourseTableQuery() {
		Intent i = new Intent(getActivity(),ChooseSemesterUIForCourseTable.class);
		startActivity(i);
	}
	
	/**
	 * 毕业绩点查询
	 */
	private void graduationGPAQuery() {
		Intent i = new Intent(getActivity(),GraduationGPAQueryUI.class);
		startActivity(i);
		getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	
	/**
	 * 等级考试成绩查询
	 */
	private void levelQuery() {
		Intent i = new Intent();
		i.setClass(getActivity(), LevelGradeQueryUI.class);
		startActivity(i);
		getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	
	/**
	 * 个人排考结果查询
	 */
	private void personalTestArrangementQuery() {
		Intent i = new Intent();
		i.setClass(getActivity(), ChooseSemesterUI.class);
		startActivity(i);
		getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	
	/**
	 * 学生成绩查询模块
	 */
	private void studentGradeQuery() {
		
	    ListView lv = (ListView) inflater.inflate(R.layout.query_system_student_grade_query_type, null);
		ListAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new String[]{
			"普通考试成绩查询",
			"普通考试不合格成绩查询",
			"补考课程及成绩查询",
			"重修课程及成绩查询"
		});
		lv.setAdapter(adapter);
		final AlertDialog queryDialog = new AlertDialog.Builder(getActivity())
		.setTitle("请选择查询类型")
		.setView(lv)
		.setNegativeButton("取消", null)
		.create();
		queryDialog.show();
		
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				final YCApplication app = (YCApplication)getActivity().getApplication();
				switch(arg2) {
					case 0:
					case 1:
					case 2:
					case 3:
						queryDialog.dismiss();
						app.put("query_type", arg2);
						ListAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new String[]{
							"学期查询",
							"学年查询"
						});
						ListView lv = (ListView) inflater.inflate(R.layout.query_system_student_grade_query_type, null);
						lv.setAdapter(adapter);
						final AlertDialog timeDialog = new AlertDialog.Builder(getActivity())
						.setTitle("请选择时间类型")
						.setNegativeButton("取消", null)
						.setView(lv)
						.create();
						timeDialog.show();
						lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, final int arg2, long arg3) {
								timeDialog.dismiss();
								app.put("time_type", arg2);
								adapter3 = new  ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,datas);
								mSpinner = (Spinner) inflater.inflate(R.layout.query_system_spinner, null);
								mSpinner.setAdapter(adapter3);
								final AlertDialog rangeDialog = new AlertDialog.Builder(getActivity())
								.setTitle("请选择时间范围")
								.setView(mSpinner)
								.setPositiveButton("确定", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										YCApplication app = (YCApplication)getActivity().getApplication();
										if(arg2 == 0)
										 app.put("grade_date", ((List)app.get("semesters")).get(mSpinner.getSelectedItemPosition()));
										else
									     app.put("grade_date", ((List)app.get("academics")).get(mSpinner.getSelectedItemPosition()));
										tryToQueryGrade();
									}
								}).setNegativeButton("取消", null)
								.create();
								rangeDialog.show();
								positveBtn = rangeDialog.getButton(AlertDialog.BUTTON_POSITIVE);
								if(app.get("semesters") == null || app.get("academics") == null) {
									positveBtn.setEnabled(false);
								}
								switch(arg2) {
									case 0:
										// 节约流量
										if(app.get("semesters") == null) {
											// 异步加载这两条数据
											new Thread(new Runnable() {
												@Override
												public void run() {
													loadSemAce("semesters");
												}
											}).start();
										} else {
											mHandler.post(new Runnable() {
												
												@Override
												public void run() {
													showRangeDialog("semesters");
												}
											});
										}

										break;
									case 1:
										// 节约流量
										if(app.get("academics") == null) {
											// 异步加载这两条数据
											new Thread(new Runnable() {
												@Override
												public void run() {
													loadSemAce("academics");
												}
											}).start();
										} else {
											mHandler.post(new Runnable() {
												
												@Override
												public void run() {
													showRangeDialog("academics");
												}
											});
										}

										break;
								}
							}
						});
						
				}
			}
		});
	}
	
	private void showRangeDialog(String type) {
		
		if(type.equals("semesters")) {
			datas.clear();
			YCApplication app = (YCApplication) getActivity().getApplication();
			datas.addAll((List)app.get(type));
			adapter3.notifyDataSetChanged();
		} else if(type.equals("academics")) {
			datas.clear();
			YCApplication app = (YCApplication) getActivity().getApplication();
			datas.addAll((List)app.get(type));
			adapter3.notifyDataSetChanged();
		}
		positveBtn.setEnabled(true);
	}
	
	/**
	 * 根据用户所选条件进行成绩查询
	 */
	private void tryToQueryGrade() {
		Intent i = new Intent();
		i.setClass(getActivity(), StudentGradeQueryUI.class);
		getActivity().startActivity(i);
		getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	
	/**
	 * 加载学期学年数组
	 */
	private void loadSemAce(final String type) {
		YCApplication app = (YCApplication) getActivity().getApplication();
		HttpGet request = new HttpGet(app.get("selectedIp") + Constant.gradeQuery);
		StringBuilder sb = new StringBuilder();
		try {
			HttpResponse response = app.getClient().execute(request);
			InputStream is = response.getEntity().getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,Constant.ENCODING));
			String temp = null;
			while((temp = br.readLine()) != null) {
				sb.append(temp);	// 获取html
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 解析html
		Document doc = Jsoup.parse(sb.toString());
		
		// 解析学期数组
		Elements options = doc.select("select[name=ddlXq] option");
		final List<String> semesters = new ArrayList<String>();
		for(int i = 1; i < options.size(); i++) {
			semesters.add(options.get(i).html());
		}
		app.put("semesters", semesters);
		// 解析学年数组
		options = doc.select("select[name=ddlXn] option");
		final List<String> academics = new ArrayList<String>();
		for(int i = 1; i < options.size(); i++) {
			academics.add(options.get(i).html());
		}
		app.put("academics", academics);
		mHandler.post(new Runnable() {
			
			@Override
			public void run() {
				if(type.equals("semesters")) {
					datas.clear();
					datas.addAll(semesters);
					adapter3.notifyDataSetChanged();
				} else if(type.equals("academics")) {
					datas.clear();
					datas.addAll(academics);
					adapter3.notifyDataSetChanged();
				}
				positveBtn.setEnabled(true);
			}
		});
	}
	
	private class ImageAdapter extends BaseAdapter
	{
		// 定义Context
		private Context		mContext;
		// 定义整型数组 即图片源
		private Integer[]	mImageIds	= 
		{ 
				R.drawable.q0,
				R.drawable.q1,
				R.drawable.q2,
				R.drawable.q3,
				R.drawable.q4,
				R.drawable.q5
		};

		private String[] mImageTexts = 
		{
			"学生成绩查询",
			"学生课表查询",
			"个人培养计划查询",
			"个人排考结果查询",
			"毕业成绩绩点查询",
			"等级考试成绩查询"
		};
		public ImageAdapter(Context c)
		{
			mContext = c;
		}

		// 获取图片的个数
		public int getCount()
		{
			return mImageIds.length;
		}

		// 获取图片在库中的位置
		public Object getItem(int position)
		{
			return position;
		}


		// 获取图片ID
		public long getItemId(int position)
		{
			return position;
		}


		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder;  
            if (convertView == null) {  
            	LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.user_conf_frag_gridview_viewholder,  null);  
                holder = new ViewHolder();  
                holder.text = (TextView) convertView.findViewById(R.id.user_conf_frag_gridview_viewholder_tvs);  
                holder.icon = (ImageView) convertView.findViewById(R.id.user_conf_frag_gridview_viewholder_iv);  
                convertView.setTag(holder);  
            } else {  
                holder = (ViewHolder) convertView.getTag();  
            } 
            convertView.setBackgroundResource(R.drawable.ic_found_item_bg_normal);
            holder.text.setText(mImageTexts[position]);  
            holder.icon.setImageResource(mImageIds[position]); 
            
            return convertView;
		}

		class ViewHolder {
			ImageView icon;
			TextView text;
		}
	}

}

