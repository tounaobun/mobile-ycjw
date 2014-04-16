/**
 * @Author Benson
 * @Time 2013-11-24
 */
package zjut.soft.finalwork.ui;

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
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * 等级考试成绩查询UI
 * 
 * @author tsw
 * 
 */
public class LevelGradeQueryUI extends Activity {

	private List<LevelTest> levelTest;
	private Handler mHandler;
	private TableLayout tl;
	private Button backBtn;
	private ProgressBar pb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.level_grade_query_ui);

		mHandler = new Handler();
		tl = (TableLayout) findViewById(R.id.level_grade_query_tl);
		backBtn = (Button) findViewById(R.id.level_grade_query_btn);
		pb = (ProgressBar) findViewById(R.id.level_grade_query_ui_pb);
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				LevelGradeQueryUI.this.finish();
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			}
		});
		tl.setStretchAllColumns(true);
		showLevelResult();
	}

	public void showLevelResult() {
		pb.setVisibility(View.VISIBLE);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					HttpGet get = new HttpGet(
							((YCApplication) getApplication())
									.get("selectedIp") + Constant.levelQuery);
					YCApplication app = (YCApplication) getApplication();
					HttpResponse response = app.getClient().execute(get);
					HttpEntity entity = response.getEntity();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(entity.getContent(),
									Constant.ENCODING));
					StringBuilder sb = new StringBuilder();
					String temp = null;
					while ((temp = br.readLine()) != null) {
						sb.append(temp);
					}
					Document doc = Jsoup.parse(sb.toString());
					final Elements tables = doc.select("#DJKCJ");
					if (tables.size() > 0) {
						Element table = tables.get(0);
						Elements trs = table.select("tr");
						levelTest = new ArrayList<LevelTest>();
						if (trs.size() > 1) {
							for (int i = 1; i < trs.size(); i++) {
								LevelTest test = new LevelTest();
								Element tr = trs.get(i);
								Elements tds = tr.select("td");
								String name = tds.get(0).select("span").get(0)
										.html();
								String grade = tds.get(1).select("span").get(0)
										.html();
								String date = tds.get(2).select("span").get(0)
										.html();
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
							fillTableWithLevelTest();
						}

					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void fillTableWithLevelTest() {

		for (LevelTest test : levelTest) {
			// 表行
			TableRow tr = new TableRow(this);

			// 表列
			// 名称
			TextView tv1 = new TextView(this);
			tv1.setBackgroundResource(R.drawable.cell_shape2);
			tv1.setText(test.getName());
			// 成绩
			TextView tv2 = new TextView(this);
			tv2.setBackgroundResource(R.drawable.cell_shape2);
			tv2.setText(test.getGrade());
			// 成时间
			TextView tv3 = new TextView(this);
			tv3.setBackgroundResource(R.drawable.cell_shape2);
			tv3.setText(test.getDate());

			tr.addView(tv1);
			tr.addView(tv2);
			tr.addView(tv3);

			tl.addView(tr, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

		}
		pb.setVisibility(View.GONE);
	}
}
