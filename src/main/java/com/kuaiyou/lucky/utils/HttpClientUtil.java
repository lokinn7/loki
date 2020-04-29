package com.kuaiyou.lucky.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpClientUtil {

	private final static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	public final static int connectTimeout = 30000;
	public final static int longConnectTimeout = 50000;

	private static PoolingHttpClientConnectionManager connManager = null;
	private static CloseableHttpClient httpclient = null;

	static {
		connManager = new PoolingHttpClientConnectionManager();
		connManager.setMaxTotal(200);
		connManager.setDefaultMaxPerRoute(50);
		httpclient = HttpClients.custom().setRetryHandler(new DefaultHttpRequestRetryHandler(3, false))
				.setConnectionManager(connManager).build();

		// Create socket configuration
		SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
		connManager.setDefaultSocketConfig(socketConfig);
		// Create message constraints
		MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200)
				.setMaxLineLength(2000).build();
		// Create connection configuration
		ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE)
				.setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
				.setMessageConstraints(messageConstraints).build();
		connManager.setDefaultConnectionConfig(connectionConfig);
		// 总最大连接数
		connManager.setMaxTotal(600);
		// 每个host的最大连接数
		connManager.setDefaultMaxPerRoute(10);
	}

	/**
	 * 
	 * @param url
	 * @param timeout
	 * @param map
	 * @param encoding
	 * @return
	 */
	public static String postJsonBody(String url, Map<String, Object> map) {
		HttpPost post = new HttpPost(url);
		try {
			post.setHeader("Content-type", "application/json");
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(connectTimeout)
					.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
					.setExpectContinueEnabled(false).build();
			post.setConfig(requestConfig);

			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(map);
			post.setEntity(new StringEntity(jsonStr, "UTF-8"));
			// logger.info("Post begin url:{},params:{}", url, jsonStr);
			CloseableHttpResponse response = httpclient.execute(post);
			try {
				HttpEntity entity = response.getEntity();
				try {
					if (entity != null) {
						String str = EntityUtils.toString(entity, "UTF-8");
						// logger.info("response url:{} body:{}", url, str);
						return str;
					}
				} finally {
					if (entity != null) {
						entity.getContent().close();
					}
				}
			} finally {
				if (response != null) {
					response.close();
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		} finally {
			post.releaseConnection();
		}
		return "";
	}

	public static InputStream postJsonBody_0(String url, Map<String, Object> map) {
		HttpPost post = new HttpPost(url);
		try {
			post.setHeader("Content-type", "application/json");
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(connectTimeout)
					.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
					.setExpectContinueEnabled(false).build();
			post.setConfig(requestConfig);

			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(map);
			post.setEntity(new StringEntity(jsonStr, "UTF-8"));
			// logger.info("Post begin url:{},params:{}", url, jsonStr);
			CloseableHttpResponse response = httpclient.execute(post);
			try {
				HttpEntity entity = response.getEntity();
				String string = EntityUtils.toString(entity);
				System.out.println(string);
				try {
					if (entity != null) {
						InputStream content = entity.getContent();
						return content;
					}
				} finally {
					if (entity != null) {
						// entity.getContent().close();
					}
				}
			} finally {
				if (response != null) {
					// response.close();
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		} finally {
			// post.releaseConnection();
		}
		return null;
	}

	public static String postJsonBody(String url, Map<String, String> headers, Map<String, Object> params,
			int connectionRequestTimeout, int connectTimeout, int socketTimeout) {
		HttpPost post = new HttpPost(url);
		try {
			if (headers.size() > 0) {
				Set<Entry<String, String>> entrySet = headers.entrySet();
				for (Entry<String, String> e : entrySet) {
					post.setHeader(e.getKey(), e.getValue());
				}
			}
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(connectTimeout)
					.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
					.setExpectContinueEnabled(false).build();
			post.setConfig(requestConfig);

			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(params);
			post.setEntity(new StringEntity(jsonStr, "UTF-8"));
			// logger.info("Post begin url:{},params:{}", url, jsonStr);
			CloseableHttpResponse response = httpclient.execute(post);
			try {
				HttpEntity entity = response.getEntity();
				logger.info("{}", ToStringBuilder.reflectionToString(entity));
				try {
					if (entity != null) {
						String str = EntityUtils.toString(entity, "UTF-8");
						logger.info("response url:{} body:{}", url, str);
						return str;
					}
				} finally {
					if (entity != null) {
						entity.getContent().close();
					}
				}
			} finally {
				if (response != null) {
					response.close();
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		} finally {
			post.releaseConnection();
		}
		return "";
	}

	/**
	 * GET 请求设置相应超时时间
	 * 
	 * @param url
	 * @param params
	 * @param connectionRequestTimeout
	 *            : Returns the timeout in milliseconds used when requesting a
	 *            connection from the connection manager
	 * @param connectTimeout
	 *            : Determines the timeout in milliseconds until a connection is
	 *            established.
	 * @param socketTimeout
	 *            : Defines the socket timeout ({@code SO_TIMEOUT}) in
	 *            milliseconds
	 * @return response String
	 */
	public static String invokeGet(String url, Map<String, String> headers, Map<String, String> params,
			int connectionRequestTimeout, int connectTimeout, int socketTimeout) {
		String responseString = null;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout).build();
		StringBuilder sb = new StringBuilder();
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
				try {
					sb.append(URLEncoder.encode(value, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					logger.warn("encode http get params error, value is " + value, e);
					try {
						sb.append(URLEncoder.encode(value, "UTF-8"));
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
				}
				i++;
			}
		}
		// logger.info("begin invoke:{}", sb.toString());
		HttpGet get = new HttpGet(sb.toString());
		get.setConfig(requestConfig);

		if (headers.size() > 0) {
			Set<Entry<String, String>> entrySet = headers.entrySet();
			for (Entry<String, String> e : entrySet) {
				get.setHeader(e.getKey(), e.getValue());
			}
		}
		try {
			CloseableHttpResponse response = httpclient.execute(get);
			try {
				HttpEntity entity = response.getEntity();
				try {
					if (entity != null) {
						responseString = EntityUtils.toString(entity, Consts.UTF_8);
					}
				} finally {
					if (entity != null) {
						entity.getContent().close();
					}
				}
			} catch (Exception e) {
				logger.error(String.format("get response error, url:%s", sb.toString()), e);
				return responseString;
			} finally {
				if (response != null) {
					response.close();
				}
			}
			// logger.info("url:{} response:{} httpcode:{}",
			// sb.toString(),responseString,
			// response.getStatusLine().getStatusCode());
		} catch (SocketTimeoutException e) {
			logger.error(String.format("invoke get timout error, url:%s", sb.toString()), e);
			return responseString;
		} catch (Exception e) {
			logger.error(String.format("invoke get error, url:%s", sb.toString()), e);
		} finally {
			get.releaseConnection();
		}
		return responseString;
	}

	/**
	 * http get 请求 5秒超时
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static int invokeGet(String url, Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		CloseableHttpResponse response = null;
		HttpGet get = null;
		int statusCode = -1;
		try {
			sb.append(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(longConnectTimeout)
					.setConnectTimeout(longConnectTimeout).setConnectionRequestTimeout(longConnectTimeout).build();

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
			get = new HttpGet(sb.toString());
			get.setConfig(requestConfig);
			response = httpclient.execute(get);
			statusCode = response.getStatusLine().getStatusCode();
			logger.info(String.format("invoke get success , url:%s ,status:%s", sb.toString(), statusCode));
			return statusCode;
		} catch (Exception e) {
			logger.error(String.format("invoke get failed, url:%s", sb.toString()), e);
		} finally {
			try {
				if (get != null) {
					get.releaseConnection();
				}
			} catch (Exception e) {
			}
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
			}
		}
		return statusCode;
	}

	/**
	 * 
	 * @param url
	 * @return httpcode
	 */
	public static InputStream invokeGet(String url) {
		int responseCode = -1;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(connectTimeout)
				.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).build();

		HttpGet get = new HttpGet(url);
		get.setConfig(requestConfig);
		try {
			CloseableHttpResponse response = httpclient.execute(get);
			try {
				HttpEntity entity = response.getEntity();
				return entity.getContent();
			} catch (Exception e) {
				logger.error(String.format("get response error, url:%s", url), e);
			} finally {
				if (response != null) {
					response.close();
				}
			}
			logger.info("url:{} responseCode:{}", url, responseCode);
		} catch (SocketTimeoutException e) {
			logger.error(String.format("invoke get timout error, url:%s", url), e);
		} catch (Exception e) {
			logger.error(String.format("invoke get error, url:%s", url), e);
		} finally {
			get.releaseConnection();
		}
		return null;
	}

	/**
	 * HTTP请求，默认超时为5S
	 *
	 * @param reqURL
	 * @param params
	 * @return
	 */
	public static String invokePost(String reqURL, Map<String, String> headers, Map<String, Object> params,
			int connectionRequestTimeout, int connectTimeout, int socketTimeout) {

		String responseContent = null;

		HttpPost httpPost = new HttpPost(reqURL);
		try {
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
					.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout).build();

			List<NameValuePair> formParams = new ArrayList<NameValuePair>();
			// 绑定到请求 Entry
			if (params != null) {
				for (Entry<String, Object> entry : params.entrySet()) {
					formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
				}
			}
			if (headers != null) {
				Set<Entry<String, String>> entrySet = headers.entrySet();
				for (Entry<String, String> e : entrySet) {
					httpPost.setHeader(e.getKey(), e.getValue());
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, Consts.UTF_8));
			httpPost.setConfig(requestConfig);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				// 执行POST请求
				HttpEntity entity = response.getEntity(); // 获取响应实体
				try {
					if (null != entity) {
						responseContent = EntityUtils.toString(entity, Consts.UTF_8);

					}
				} finally {
					if (entity != null) {
						entity.getContent().close();
					}
				}
			} finally {
				if (response != null) {
					response.close();
				}
			}
			// logger.debug("requestURI:[{}],params:[{}],
			// responseContent:[{}]",httpPost.getURI() ,params,
			// responseContent);
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		} finally {
			httpPost.releaseConnection();
		}
		return responseContent;
	}

	/**
	 * HTTP请求，默认超时为5S
	 * 
	 * @param reqURL
	 * @param params
	 * @return
	 */
	public static String invokePost(String reqURL, Map<String, String> params) {

		String responseContent = null;

		HttpPost httpPost = new HttpPost(reqURL);
		try {
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(connectTimeout)
					.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).build();

			List<NameValuePair> formParams = new ArrayList<NameValuePair>();
			// 绑定到请求 Entry
			for (Entry<String, String> entry : params.entrySet()) {
				formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, Consts.UTF_8));
			httpPost.setConfig(requestConfig);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				// 执行POST请求
				HttpEntity entity = response.getEntity(); // 获取响应实体
				try {
					if (null != entity) {
						responseContent = EntityUtils.toString(entity, Consts.UTF_8);
					}
				} finally {
					if (entity != null) {
						entity.getContent().close();
					}
				}
			} finally {
				if (response != null) {
					response.close();
				}
			}
			// logger.debug("requestURI:[{}],params:[{}],
			// responseContent:[{}]",httpPost.getURI() ,params,
			// responseContent);
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		} finally {
			httpPost.releaseConnection();
		}
		return responseContent;
	}

	public static void main(String args[]) {

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

	public class T extends Thread {
		public int tag = 0;

		public T(int tag) {
			this.tag = tag;
		}

		@Override
		public void run() {
			String url = null;
			url = "http://contents.ctrip.com/market-channel-apppromotion/idfaconfirm.aspx";
			// invokeGet(url,null);
			Map<String, String> map = new HashMap<String, String>();
			map.put("Idfa",
					"C9E9B9F12B5D447799243F205344C105,1F258ED7F93C4F6F8A33F0080DD3C356,47DBEC17F5CA401092ED496D1D55A6C3");
			map.put("Appid", "379395415");
			invokePost(url, map);
		}
	}

}
