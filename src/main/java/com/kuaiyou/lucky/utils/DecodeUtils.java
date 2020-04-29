package com.kuaiyou.lucky.utils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class DecodeUtils {
	public static String chgStrs(String info, int num) {
		byte[] bytes;
		try {
			bytes = info.getBytes("GBK");
			for (int i = 0; i < bytes.length; i++) {
				bytes[i] = (byte) (bytes[i] ^ num);
			}
			return new String(bytes, "GBK");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String deCode(String temp) {
		StringTokenizer st = new StringTokenizer(temp);
		StringBuilder sb = new StringBuilder();
		String hString = "";
		String tString = "";
		String pString = "";
		String sString = "";
		while (st.hasMoreTokens()) {
			String nextToken = st.nextToken();
			if (nextToken.startsWith("h;")) {
				hString = nextToken.substring(2, 10);
			} else if (nextToken.startsWith("t;")) {
				tString = nextToken.substring(2, 10);
			} else if (nextToken.startsWith("p;")) {
				pString = nextToken.substring(2, 10);
			} else if (nextToken.startsWith("s;")) {
				sString = nextToken.substring(2, 10);
			}
		}
		sb.append(hString);
		sb.append(tString);
		sb.append(pString);
		sb.append(sString);
		String jiemiresult = sb.toString();
		String chgStrs = chgStrs(jiemiresult, 2);
		String some = StringUtils.reverse(chgStrs);
		return some;
	}

	public static String getPn(String pns) {
		String pn = new StringBuilder(
				Arrays.stream(pns.split("-")).map(i -> new StringBuilder(i).reverse().toString()).collect(Collectors.joining()))
						.reverse().toString();
		return pn;
	}

	public static void main(String[] args) {
		if ("b6c878e3-b15c-c33e-e724-e07e05342c75".contains("\\-")) {
			System.out.println(getPn("b6c878e3-b15c-c33e-e724-e07e05342c75").toUpperCase());
		}
		System.out.println("4d8d67c2-71ca-2667-01b5-bcabc2bc82d9".length());
	}
}
