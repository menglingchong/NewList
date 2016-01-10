package com.imooc.newslist;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter implements OnScrollListener {

	private List<NewsBean> mList;
	private LayoutInflater mInflater;
	private ImageLoader mImageLoader;
	private int mStart, mEnd;
	public static String[] URLS;
	private boolean mFirstIn;

	public NewsAdapter(List<NewsBean> data, Context context, ListView listview) {
		mList = data;
		mInflater = LayoutInflater.from(context);
		// mImageLoader =new ImageLoader(listview);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	// 获取对应id对应的Item对象(即NewsBean对象)
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_layout, null);// 加载子项布局
			viewHolder = new ViewHolder();
			viewHolder.ivIcon = (ImageView) convertView
					.findViewById(R.id.image);
			viewHolder.tvTitle = (TextView) convertView
					.findViewById(R.id.title);
			viewHolder.tvContent = (TextView) convertView
					.findViewById(R.id.content);
			convertView.setTag(viewHolder);// 将viewHolder存在convertView中
		} else {
			viewHolder = (ViewHolder) convertView.getTag();// 重新获取viewHolder
		}
		viewHolder.ivIcon.setImageResource(R.drawable.ic_launcher);

		String url = mList.get(position).newsIconUrl;// 获取图片的url
		viewHolder.ivIcon.setTag(url);// 将控件和url进行绑定
//		new ImageLoader().showImageByThread(viewHolder.ivIcon, url);
		new ImageLoader().showImageByAsyncTask(viewHolder.ivIcon, url);
		viewHolder.tvTitle.setText(mList.get(position).newsTitle);// mList.get(position)获取newsBean对象,
		viewHolder.tvContent.setText(mList.get(position).newsContent);// 为控件添加数据

		return convertView;
	}

	class ViewHolder {
		TextView tvTitle, tvContent;
		ImageView ivIcon;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}

}
