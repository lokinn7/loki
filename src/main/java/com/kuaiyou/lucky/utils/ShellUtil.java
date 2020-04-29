package com.kuaiyou.lucky.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellUtil {

	private static Logger logger = LoggerFactory.getLogger(ShellUtil.class);

	public static int excute(String... args) {
		int rescode = 0;
		ProcessBuilder p = new ProcessBuilder(args);
		try {
			logger.info("Excute shell has already begin.");
			Process start = p.start();
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(start.getInputStream()));
			BufferedReader errInput = new BufferedReader(new InputStreamReader(start.getErrorStream()));
			rescode = start.waitFor();
			String info = null;
			String error = null;
			while ((info = stdInput.readLine()) != null) {
				logger.info(info);
			}
			while ((error = errInput.readLine()) != null) {
				logger.error(error);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("End with processbuilder ....");
		return rescode;
	}
}
