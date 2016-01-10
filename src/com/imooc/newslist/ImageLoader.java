package com.imooc.newslist;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class ImageLoader {

	private ImageView mImageView;
	private String mUrl;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (mImageView.getTag().equals(mUrl)) {
				mImageView.setImageBitmap((Bitmap) msg.obj);// 更新UI
			}
		}
	};

	public void showImageByThread(ImageView imageView, final String url) {
		mImageView = imageView;
		mUrl = url;
		new Thread() {
			@Override
			public void run() {
				super.run();
				Bitmap bitmap = getBitmapFromUrl(mUrl);
				Message message = Message.obtain();
				message.obj = bitmap;
				handler.sendMessage(message);
			}
		}.start();
	}

	protected Bitmap getBitmapFromUrl(String urlString) {
		// TODO Auto-generated method stub
		Bitmap bitmap;
		InputStream is = null;
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			is = new BufferedInputStream(connection.getInputStream());
			bitmap = BitmapFactory.decodeStream(is);// 将字节流url解析为bitmap
			connection.disconnect();
			return bitmap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	public void showImageByAsyncTask(ImageView imageView, String url) {
		new NewsAsyncTask(imageView, url).execute(url);
	}

	private class NewsAsyncTask extends AsyncTask<String, Void, Bitmap> {

		private String mUrl;
		private ImageView mImageView;

		public NewsAsyncTask(ImageView imageView, String url) {
			mImageView = imageView;
			mUrl = url;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url = params[0];
			// 从网络获取图片
			Bitmap bitmap = getBitmapFromUrl(url);
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			// TODO Auto-generated method stub
			super.onPostExecute(bitmap);
			if (mImageView.getTag().equals(mUrl)) {
				mImageView.setImageBitmap(bitmap);// 更新UI
			}
		}
	}

}
