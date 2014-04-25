/**
  * @Author Benson
  * @Time 2013-11-27
  */
package zjut.soft.finalwork.ui;

import zjut.soft.finalwork.R;
import zjut.soft.finalwork.core.YCApplication;
import zjut.soft.finalwork.util.ImageUtils;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BasicInfoUI extends Activity {

	private ImageView portraitIV;
	private TextView usernameTV,nameTV,classTV;
	private Button backBtn;
	private YCApplication app;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.basic_info_ui);
		
		init();
	}

	private void init() {
		
		app = (YCApplication)getApplication();
		portraitIV = (ImageView) findViewById(R.id.basic_info_portrait);
		usernameTV = (TextView) findViewById(R.id.basic_info_username);
		classTV = (TextView) findViewById(R.id.basic_info_class);
		nameTV = (TextView) findViewById(R.id.basic_info_name);
		backBtn = (Button) findViewById(R.id.basic_info_back);
		
		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BasicInfoUI.this.finish();
			}
		});
		
		Bitmap portraitBitmap = (Bitmap)app.get("portrait");
		
		portraitIV.setImageBitmap(portraitBitmap);
		
		int[] location = new int[2];
		portraitIV.getLocationInWindow(location);
		Animation anim = new TranslateAnimation(0, 0, location[1], location[1] + 30);
		anim.setInterpolator(new OvershootInterpolator(7.0f));
		anim.setDuration(800);
		anim.setFillAfter(true);
		portraitIV.setAnimation(anim);
		anim.startNow();
		String username = (String)app.get("username");
		if(username != null) {
			usernameTV.setText(username);
		}
		
		String name = (String)app.get("name");
		if(name != null) {
			nameTV.setText(name);
		}
		
		String className = (String)app.get("className");
		if(className != null) {
			classTV.setText(className);
		}		
	}
	
}
