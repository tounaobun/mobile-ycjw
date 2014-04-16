/**
  * @Author Benson
  * @Time 2013-11-25
  */
package zjut.soft.finalwork.ui;

import zjut.soft.finalwork.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class WelcomeUI extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome_ui);
		
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
	    findViewById(R.id.textView4).startAnimation(shake);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent i = new Intent();
				i.setClass(WelcomeUI.this, LoginUI.class);
				startActivity(i);
				WelcomeUI.this.finish();
				
			}
		}, 3*1000);
	}

	@Override
	public void onBackPressed() {
		
	}

	
}
