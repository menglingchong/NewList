package com.imooc.newslist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ListView listView;
	private static String URL = "http://www.imooc.com/api/teacher?type=4&num=30";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listview);
		new NewsAsyncTask().execute(URL);
	}

	class NewsAsyncTask extends AsyncTask<String, Void, List<NewsBean>> {

		@Override//ִ�к�̨���������߳������У������Ը���UI
		protected List<NewsBean> doInBackground(String... params) {
			// TODO Auto-generated method stub
			return getJsonData(params[0]);
		}

		@Override//����̨���ص����ݽ��д��������ú�̨���ݸ���UI
		protected void onPostExecute(List<NewsBean> newsbean) {
			// TODO Auto-generated method stub
			super.onPostExecute(newsbean);
			NewsAdapter adapter =new NewsAdapter(newsbean,MainActivity.this,listView);
			listView.setAdapter(adapter);
		}

		// ͨ��inputStream������ҳ���ص��ַ�������
		private String readString(InputStream is) {

			InputStreamReader isr;
			String result = "";
			try {
				String line = "";
				// �ֽ���ת�����ַ���
				isr = new InputStreamReader(is, "utf-8");
				BufferedReader br = new BufferedReader(isr);
				while ((line = br.readLine()) != null) {
					result += line;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		// ��url��Ӧ��JSON��ʽ���ַ�������ת��Ϊ��������װ��NewsBean����
		private List<NewsBean> getJsonData(String url) {
			List<NewsBean> newsBeanList = new ArrayList<NewsBean>();
			try {
				// String jsonString = readString(new URI(url).openStream());
				String jsonString = readString(new URL(url).openConnection().getInputStream());
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(jsonString);
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						jsonObject = jsonArray.getJSONObject(i);
						NewsBean newsBean = new NewsBean();
						newsBean.newsIconUrl = jsonObject.getString("picSmall");
						newsBean.newsTitle = jsonObject.getString("name");
						newsBean.newsContent = jsonObject
								.getString("description");
						newsBeanList.add(newsBean);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return newsBeanList;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
