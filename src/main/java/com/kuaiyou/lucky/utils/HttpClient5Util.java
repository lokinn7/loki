package com.kuaiyou.lucky.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.RequestBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.config.SocketConfig;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClient5Util {

	protected static Logger logger = LoggerFactory.getLogger(HttpClient5Util.class);

	public final static int connectTimeout = 30000;
	public final static int longConnectTimeout = 50000;

	private static PoolingHttpClientConnectionManager connManager = null;
	private static CloseableHttpClient httpclient = null;

	static {
		connManager = new PoolingHttpClientConnectionManager();
		connManager.setDefaultSocketConfig(SocketConfig.custom().setTcpNoDelay(true).build());
		connManager.setMaxTotal(600);
		connManager.setDefaultMaxPerRoute(10);
		httpclient = HttpClients.custom().setConnectionManager(connManager).build();
	}

	public static String invokeGet(String url, HashMap<String, String> params) {
		StringBuilder sb = new StringBuilder();
		String restring = null;
		HttpGet httpget = null;
		CloseableHttpResponse response = null;
		try {
			sb.append(url);
			if (params != null) {
				int i = 0;
				for (Entry<String, String> entry : params.entrySet()) {
					if (i == 0 && !url.contains("?")) {
						sb.append("?");
					} else {
						sb.append("&");
					}
					sb.append(entry.getKey());
					sb.append("=");
					String value = entry.getValue();
					sb.append(URLEncoder.encode(value, "UTF-8"));
					i++;
				}
			}
			httpget = new HttpGet(sb.toString());
			response = httpclient.execute(httpget);
			// res code
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			restring = stream2String(is);
			int rescode = response.getCode();
			EntityUtils.consume(entity);
			if (rescode == 200) {
				logger.error(String.format("invoke success, request url :%s", sb.toString()));
			} else {
				logger.error(String.format("invoke failed, request url :%s", sb.toString()));
			}
		} catch (Exception e) {
			logger.error(String.format("invoke exception, request url :%s, cause:%s", sb.toString(), e));
		} finally {
			if (null != httpget) {
				httpget.abort();
			}
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error(String.format("close failed, request url :%s, cause:%s", sb.toString(), e));
				}
			}
		}
		return restring;
	}

	public static String invokeGet(String url) {
		String restring = null;
		HttpGet httpget = new HttpGet(url);
		try (CloseableHttpResponse response = httpclient.execute(httpget)) {
			// res code
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			restring = stream2String(is);
			int rescode = response.getCode();
			EntityUtils.consume(entity);
			if (rescode == 200) {
				logger.error(String.format("invoke success, request url :%s", url));
			} else {
				logger.error(String.format("invoke failed, request url :%s", url));
			}
		} catch (Exception e) {
			logger.error(String.format("invoke exception, request url :%s, cause:%s", url, e));
			return null;
		} finally {
			if (null != httpget) {
				httpget.abort();
			}
		}
		return restring;
	}
	
	public static int invokeGet_0(String url) {
		int restring = -1;
		HttpGet httpget = new HttpGet(url);
		try (CloseableHttpResponse response = httpclient.execute(httpget)) {
			// res code
			HttpEntity entity = response.getEntity();
			restring = response.getCode();
			EntityUtils.consume(entity);
			if (restring == 200) {
//				logger.error(String.format("invoke success, request url :%s", url));
			} else {
//				logger.error(String.format("invoke failed, request url :%s", url));
			}
		} catch (Exception e) {
//			logger.error(String.format("invoke exception, request url :%s, cause:%s", url, e));
		} finally {
			if (null != httpget) {
				httpget.abort();
			}
		}
		return restring;
	}

	public static String stream2String(InputStream is) throws Exception {
		BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(is));
		StringBuffer sb = new StringBuffer();
		String sTempOneLine = new String("");
		while ((sTempOneLine = tBufferedReader.readLine()) != null) {
			sb.append(sTempOneLine);
		}
		return sb.toString();
	}

	public static int invokePostForm(String url, HashMap<String, String> params) {
		int rescode = -1;
		if (params.keySet().size() > 0) {
			Set<Entry<String, String>> entrySet = params.entrySet();
			ArrayList<NameValuePair> nvps = new ArrayList<>();
			for (Entry<String, String> entry : entrySet) {
				NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue());
				nvps.add(nvp);
			}
			BasicNameValuePair[] temp = nvps.toArray(new BasicNameValuePair[nvps.size()]);
			ClassicHttpRequest httprequest = RequestBuilder.post(url).addParameters(nvps.toArray(temp)).build();
			try (CloseableHttpClient httpclient = HttpClients.custom().build();
					CloseableHttpResponse response = httpclient.execute(httprequest)) {
				httpclient.execute(httprequest);
				HttpEntity entity = response.getEntity();
				rescode = response.getCode();
				EntityUtils.consume(entity);
				if (rescode == 200) {
					logger.info("invoke form post success.");
				}
				if (rescode == 500) {
					logger.error("invoke form post failed.");
				}
			} catch (Exception e) {
				logger.error("invoke form post failed, cause :{}", e);
			}
		}
		return rescode;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100000; i++) {
			// http://42.123.106.17:8020/bdapi/restful/fog/vendorkysj/getItems/vendorkysj/1c82926311368e21b25be566353838fb?key=rtbjob-836-20180827-Au-932
			HashMap<String, String> params = new HashMap<>();
			params.put("key", "rtbjob-836-20180827-Au-" + i);
			params.put("value",
					"FACCAFBA0875C160589BB269D45D7C5D|| okhttp/3.10.0  t;FD;D5661 Chrome s;DF@11406p;D3G@6GF0 HuaweiRIO-AL00 h;D36FC363|https_4g|20180901|0|S01-10");
			HttpClient5Util.invokeGet(
					"http://42.123.106.17:8020/bdapi/restful/fog/vendorkysj/setItems/vendorkysj/1c82926311368e21b25be566353838fb",
					params);
		}
	}

}
