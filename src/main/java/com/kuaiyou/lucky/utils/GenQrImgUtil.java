package com.kuaiyou.lucky.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class GenQrImgUtil {

	private static Logger logger = LoggerFactory.getLogger(GenQrImgUtil.class);

	public final static String QRURL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";

	public static void getminiqrQr(String sceneStr, String accessToken, String path) {
		try {
			URL url = new URL(QRURL + accessToken);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");// 提交模式
			// conn.setConnectTimeout(10000);//连接超时 单位毫秒
			// conn.setReadTimeout(2000);//读取超时 单位毫秒
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
			// 发送请求参数
			JSONObject paramJson = new JSONObject();
			paramJson.put("scene", sceneStr);
			paramJson.put("page", "pages/prizedetail/prizedetail");
			paramJson.put("width", 430);
			paramJson.put("auto_color", true);
			/**
			 * line_color生效 paramJson.put("auto_color", false); JSONObject
			 * lineColor = new JSONObject(); lineColor.put("r", 0);
			 * lineColor.put("g", 0); lineColor.put("b", 0);
			 * paramJson.put("line_color", lineColor);
			 */

			printWriter.write(paramJson.toString());
			// flush输出流的缓冲
			printWriter.flush();
			// 开始获取数据
			BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
			OutputStream os = new FileOutputStream(new File(path));
			int len;
			byte[] arr = new byte[1024];
			while ((len = bis.read(arr)) != -1) {
				os.write(arr, 0, len);
				os.flush();
			}
			os.close();
		} catch (Exception e) {
			logger.error("genarator file error " + e);
			e.printStackTrace();
		}
	}

	public static void getminiqrQr(String sceneStr, String accessToken, String path, String page) {
		try {
			URL url = new URL(QRURL + accessToken);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");// 提交模式
			// conn.setConnectTimeout(10000);//连接超时 单位毫秒
			// conn.setReadTimeout(2000);//读取超时 单位毫秒
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
			// 发送请求参数
			JSONObject paramJson = new JSONObject();
			paramJson.put("scene", sceneStr);
			paramJson.put("page", page);
			paramJson.put("width", 430);
			paramJson.put("auto_color", true);
			/**
			 * line_color生效 paramJson.put("auto_color", false); JSONObject
			 * lineColor = new JSONObject(); lineColor.put("r", 0);
			 * lineColor.put("g", 0); lineColor.put("b", 0);
			 * paramJson.put("line_color", lineColor);
			 */

			printWriter.write(paramJson.toString());
			// flush输出流的缓冲
			printWriter.flush();
			// 开始获取数据
			BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
			OutputStream os = new FileOutputStream(new File(path));
			int len;
			byte[] arr = new byte[1024];
			while ((len = bis.read(arr)) != -1) {
				os.write(arr, 0, len);
				os.flush();
			}
			os.close();
		} catch (Exception e) {
			logger.error("genarator file error " + e);
			e.printStackTrace();
		}
	}

	public static void getminiqrQr_0(String sceneStr, String accessToken, String path) {
		try {
			Map<String, Object> paramJson = new HashMap<>();
			paramJson.put("scene", sceneStr);
			paramJson.put("page", "pages/index/index");
			paramJson.put("width", 430);
			paramJson.put("auto_color", true);
			/**
			 * line_color生效 paramJson.put("auto_color", false); JSONObject
			 * lineColor = new JSONObject(); lineColor.put("r", 0);
			 * lineColor.put("g", 0); lineColor.put("b", 0);
			 * paramJson.put("line_color", lineColor);
			 */
			InputStream invokePost = HttpClientUtil.postJsonBody_0(QRURL + accessToken, paramJson);
			if (invokePost != null) {
				saveToImgByInputStream(invokePost, path);
			}
		} catch (Exception e) {
			logger.error("genarator file error " + e);
			e.printStackTrace();
		}
	}

	public static int saveToImgByInputStream(InputStream instreams, String filepath) {

		int stateInt = 1;
		if (instreams != null) {
			try {
				File file = new File(filepath);// 可以是任何图片格式.jpg,.png等
				FileOutputStream fos = new FileOutputStream(file);

				byte[] b = new byte[1024];
				int nRead = 0;
				while ((nRead = instreams.read(b)) != -1) {
					fos.write(b, 0, nRead);
					fos.flush();
				}
				fos.close();
			} catch (Exception e) {
				stateInt = 0;
				e.printStackTrace();
			} finally {
				try {
					instreams.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return stateInt;
	}
}
