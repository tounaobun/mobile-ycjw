/**
  * @Author Benson
  * @Time 2013-11-23
  */
package zjut.soft.finalwork.fragment;

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
import zjut.soft.finalwork.core.YCApplication;
import zjut.soft.finalwork.core.YCStudentManager;
import zjut.soft.finalwork.ui.SlidingActivity;
import zjut.soft.finalwork.util.Constant;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 缴费查询Fragment
 * @author tsw
 *
 */
public class PayQueryFragment extends Fragment implements View.OnClickListener {
	
	private ImageView menuIV,aboutTV;
	private TextView payInfoTV;
	private Button refreshBtn;
	private Handler mHandler;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pay_query_frag, null);
		
		mHandler = new Handler();
		menuIV = (ImageView) view.findViewById(R.id.pay_query_frag_menu);
		aboutTV = (ImageView) view.findViewById(R.id.pay_query_frag_about_us);		
		payInfoTV = (TextView) view.findViewById(R.id.pay_query_frag_pay_info);
		refreshBtn = (Button) view.findViewById(R.id.pay_query_frag_btn);
		refreshBtn.setOnClickListener(this);
		TextView file1TV,file2TV;
		file1TV = (TextView) view.findViewById(R.id.pay_query_frag_file1);
		file2TV = (TextView) view.findViewById(R.id.pay_query_frag_file2);
		file1TV.setText(Html.fromHtml("<a href='http://www.ycjw.zjut.edu.cn//news/sfglwj.htm'>浙江工业大学学分制收费管理办法(2010版)</a>"));
		file2TV.setText(Html.fromHtml("<a href='http://www.ycjw.zjut.edu.cn//news/xfjntz.htm'>关于实施注册选课与学费缴纳联动管理的通知 </a>"));
		file1TV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.ycjw.zjut.edu.cn//news/sfglwj.htm"));
				startActivity(i);
			}
		});
		file2TV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.ycjw.zjut.edu.cn//news/xfjntz.htm"));
				startActivity(i);				
			}
		});
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
		
		tryToGetPayInfo();		//只更新一次，以后退出前按钮可更新(节约流量)
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	
	private void tryToGetPayInfo() {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				YCApplication app = (YCApplication)getActivity().getApplication();
				HttpClient client = app.getClient();
				HttpGet request = new HttpGet(app.get("selectedIp") + Constant.payQuery);
				InputStream is = null;
				BufferedReader br = null;
				try {
					is = client.execute(request).getEntity().getContent();
					br = new BufferedReader(new InputStreamReader(is,Constant.ENCODING));
					String temp = null;
					while((temp = br.readLine()) != null) {
						System.out.println(temp);
						if(temp.contains("lbl_ts")) {
							break;
						}
					}
					Document doc = Jsoup.parse(temp);
					final String val = doc.select("span font").html();
					mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							payInfoTV.setText(val);
						}
					});
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if(br != null) {
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if(is != null) {
						try {
							is.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}
	
	private void buttonToRefreshPay() {
		
		payInfoTV.setText("正在更新缴费信息...");
		refreshBtn.setEnabled(false);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				YCApplication app = (YCApplication)getActivity().getApplication();
				HttpClient client = app.getClient();
				String url = app.get("selectedIp") + Constant.payQuery;
				HttpGet request = new HttpGet(url);
				List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
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
					urlParameters.add(new BasicNameValuePair("Btn_jf", "更新缴费信息"));
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
					String temp = null;
					while((temp = rd.readLine()) != null) {
						
						if(temp.contains("lbl_ts")) {
							System.out.print(temp);
							break;
						}
						System.out.print(temp);
					}
					final String val = Jsoup.parse(temp).select("span font").html();
					mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							payInfoTV.setText(val);
							mHandler.postDelayed(new Runnable() {
								
								@Override
								public void run() {
									refreshBtn.setEnabled(true);
								}
							}, 1500);
						}
					});
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				
			}
		}).start();
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
	}

	private SlidingActivity parent;
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		parent = (SlidingActivity)activity;
	}

	@Override
	public void onClick(View v) {
		buttonToRefreshPay();
	}
}

