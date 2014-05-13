package zjut.soft.finalwork.ui;

import zjut.soft.finalwork.R;
import zjut.soft.finalwork.core.YCApplication;
import zjut.soft.finalwork.util.Constant;
import zjut.soft.finalwork.util.ImageUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // »¶Ó­Äú
        TextView tv = (TextView) findViewById(R.id.welcome_tv);
        YCApplication app = (YCApplication)getApplication();
        tv.setText("»¶Ó­Äú," + app.get("name").toString());

        // ÄãµÄË§ÕÕ
        ImageView portraitIV = (ImageView)findViewById(R.id.portrait_iv);
        portraitIV.setImageBitmap(ImageUtils.getBitmapFromServer(this,((YCApplication)getApplication()).get("selectedIp") + Constant.portraitContext));
        
        Button btn = (Button) findViewById(R.id.main_modify_pass);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(MainActivity.this, EditPassUI.class);
				MainActivity.this.startActivity(i);
			}
		});
    }

}
