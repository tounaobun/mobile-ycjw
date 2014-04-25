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

import zjut.soft.finalwork.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class RightFragment extends Fragment {

	private ImageView gearIV;
	
	private ImageView plannerIV;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.right, null);
		gearIV = (ImageView) view.findViewById(R.id.about_us_gear);
		plannerIV = (ImageView) view.findViewById(R.id.planner);
		
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		RotateAnimation anim = (RotateAnimation)AnimationUtils.loadAnimation(getActivity(), R.anim.gear_turning_around);
		anim.setRepeatCount(Animation.INFINITE);
		anim.setInterpolator(new LinearInterpolator());
		gearIV.startAnimation(anim);
		plannerScaleAction();
		
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
	    super.onHiddenChanged(hidden);
	    if (hidden) {
	        //do when hidden
	    } else {
	       //do when show

	    }
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser) {

			
		}
	}

	public void plannerScaleAction() {
		ScaleAnimation scaleanim1 = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleanim1.setDuration(1500);
		scaleanim1.setFillAfter(true);
		scaleanim1.setRepeatCount(Animation.INFINITE);
		scaleanim1.setRepeatMode(Animation.REVERSE);
		scaleanim1.setInterpolator(new AccelerateDecelerateInterpolator());
		
		
		plannerIV.setAnimation(scaleanim1);
		scaleanim1.startNow();		
	}
}
