package com.example.getcsdnlistview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class DMethod {
	/**
	 * 联网获得数据
	 * 
	 * @return 数据
	 */
	public static List<BlogListInfo> getBlogNetDate(String path, String regex) {
		List<BlogListInfo> result = new ArrayList<BlogListInfo>();
		String blogString = RemoveRN(http_get(path));
		Pattern p = Pattern.compile(regex);
		// 我的博客首页的源代码字符串
		Matcher m = p.matcher(blogString);
		while (m.find()) {// 循环查找匹配字串
			MatchResult mr = m.toMatchResult();
			BlogListInfo info = new BlogListInfo();
			info.setBlogUrl(mr.group(1));
			info.setBlogTitle(mr.group(2));
			info.setBlogSummary(mr.group(3));
			info.setBlogTime(mr.group(4));
			info.setBlogReadNum(mr.group(5));
			info.setBlogReply(mr.group(6));
			result.add(info);
		}
		return result;
	}

	public static String RemoveRN(String str) {
		str = str.replaceAll("\r\n", "");
		str = str.replaceAll("\n", "");
		return str;
	}

	/**
	 * get请求URL，失败时尝试三次
	 * 
	 * @param url
	 *            请求网址
	 * @return 网页内容的字符串
	 */
	public static String http_get(String url) {
		final int RETRY_TIME = 3;
		HttpClient httpClient = null;
		HttpGet httpGet = null;
		String responseBody = "";
		int time = 0;
		do {
			try {
				httpClient = getHttpClient();
				httpGet = new HttpGet(url);
				HttpResponse response = httpClient.execute(httpGet);
				if (response.getStatusLine().getStatusCode() == 200) {
					// 用utf-8编码转化为字符串
					byte[] bResult = EntityUtils.toByteArray(response
							.getEntity());
					if (bResult != null) {
						responseBody = new String(bResult, "utf-8");
					}
				}
				break;
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				e.printStackTrace();
			} finally {
				httpClient = null;
			}
		} while (time < RETRY_TIME);

		return responseBody;
	}

	public static HttpClient getHttpClient() {
		HttpParams httpParams = new BasicHttpParams();
		// 设定连接超时和读取超时时间
		HttpConnectionParams.setConnectionTimeout(httpParams, 6000);
		HttpConnectionParams.setSoTimeout(httpParams, 30000);
		return new DefaultHttpClient(httpParams);
	}
}
