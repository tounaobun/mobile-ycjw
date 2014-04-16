/**
  * @Author Benson
  * @Time 2013-11-23
  */
package zjut.soft.finalwork.fragment;

import zjut.soft.finalwork.R;
import zjut.soft.finalwork.ui.BasicInfoUI;
import zjut.soft.finalwork.ui.EditPassUI;
import zjut.soft.finalwork.ui.SlidingActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class UserConfFragment extends Fragment {
	
	private GridView gridView;
	private ImageView menuIV,moreBtn;
	private SlidingActivity parent;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.user_conf_frag, null);
		gridView = (GridView) view.findViewById(R.id.user_conf_frag_gridview);
		menuIV = (ImageView) view.findViewById(R.id.user_conf_frag_menu);
		moreBtn = (ImageView) view.findViewById(R.id.user_conf_frag_about_us);
		menuIV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				((SlidingActivity)getActivity()).showLeft();
			}
		});
		moreBtn.setOnClickListener(new View.OnClickListener() {
			
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
		parent = (SlidingActivity)activity;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		//添加元素给gridview
		gridView.setAdapter(new ImageAdapter(this.getActivity()));
//		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));	// 去掉点击黄色背景

				//事件监听
		gridView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View v, int position, long id)
					{
						switch(position) {
						case 0:
							Intent i = new Intent();
							i.setClass(getActivity(), EditPassUI.class);
							getActivity().startActivity(i);
							getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
							break;
						case 1:
							Intent i2 = new Intent(getActivity(),BasicInfoUI.class);
							getActivity().startActivity(i2);
							getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
							break;
						}
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
				R.drawable.q6,
				R.drawable.q7
		};

		private String[] mImageTexts = 
		{
			"学生密码修改",
			"基本信息维护"
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
            
            holder.text.setText(mImageTexts[position]);  
            holder.icon.setImageResource(mImageIds[position]);  
            convertView.setBackgroundResource(R.drawable.ic_found_item_bg_normal);
            return convertView;
		}

		class ViewHolder {
			ImageView icon;
			TextView text;
		}
	}

}

