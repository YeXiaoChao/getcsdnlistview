package com.example.getcsdnlistview;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public BlogListAdapter mAdapter;
	private ListView mListView;
	// 博文信息列表
	public static List<BlogListInfo> blogList;
	final String CSDNURL = "http://www.cnblogs.com/yc-755909659/";
	final String REGEX = "class=\"postTitle2\" href=\"(.*?)\">(.*?)</a>.*?摘要:(.*?)<a.*?posted @(.*?)J灬叶小超 阅读(.*?) 评论(.*?)<a";
	Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		handler = getHandler();
		ThreadStart();
	}

	/**
	 * 新开辟线程处理联网操作
	 * 
	 */
	private void ThreadStart() {
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					blogList = DMethod.getBlogNetDate(CSDNURL, REGEX);
					msg.what = blogList.size();
				} catch (Exception e) {
					e.printStackTrace();
					msg.what = -1;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	/**
	 * 处理联网结果，显示在listview
	 * 
	 */
	private Handler getHandler() {
		return new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what < 0) {
					Toast.makeText(MainActivity.this, "数据获取失败",
							Toast.LENGTH_SHORT).show();
				} else {
					initListview();
				}
			}
		};
	}

	/**
	 * 在listview里显示数据
	 */
	private void initListview() {
		// 绑定XML中的ListView，作为Item的容器
		mListView = (ListView) findViewById(R.id.list);
		mAdapter = new BlogListAdapter(MainActivity.this);
		mListView.setAdapter(mAdapter);
	}
}
