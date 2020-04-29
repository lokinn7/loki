package com.kuaiyou.lucky.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatInfoUtil {

	public final static String PHONEREGEX = "(\\d{3})\\d{4}(\\d{4})";
	public final static String PHONEREPLACEMENT = "$1****$2";
	public final static String NAMEREGEX = "([\\d\\D]{1})(.*)";
	public final static String NAMEREPLACEMENT = "$1**";

	public final static String PHONE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";


	public static boolean parsePhone(String phone) {
		Pattern compile = Pattern.compile(PHONE_NUMBER_REG);
		Matcher matcher = compile.matcher(phone);
		return matcher.matches();
	}

	public static void main(String[] args) {
		System.out.println(parsePhone("13344445548"));
	}

}
