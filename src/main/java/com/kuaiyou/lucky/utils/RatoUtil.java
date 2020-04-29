package com.kuaiyou.lucky.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;

/**
 * <pre>
  		按几率产生随机数 例如，产生0.1-100的随机数，0.1-1的几率是90%，1-10的几率是9%，10-100的几率是1%
  		0,18,28,58,68,88,98,108
 * </pre>
 */
public class RatoUtil {

	public static void main(String[] args) {
		List<Double> gifts = new ArrayList<Double>();
		// 序号==物品Id==物品名称==概率
		gifts.add((double) 10 / 100);
		gifts.add((double) 20 / 100);
		gifts.add((double) 10 / 100);
		gifts.add((double) 0 / 100);
		for (int i = 0; i < 11; i++) {
			int lottery = lottery(gifts);
			System.out.println(lottery);
		}
	}

	public static int lottery(List<Double> orignalRates) {
		if (orignalRates == null || orignalRates.isEmpty()) {
			return -1;
		}

		int size = orignalRates.size();

		// 计算总概率，这样可以保证不一定总概率是1
		double sumRate = 0d;
		for (double rate : orignalRates) {
			sumRate += rate;
		}

		// 计算每个物品在总概率的基础下的概率情况
		List<Double> sortOrignalRates = new ArrayList<Double>(size);
		Double tempSumRate = 0d;
		for (double rate : orignalRates) {
			tempSumRate += rate;
			sortOrignalRates.add(tempSumRate / sumRate);
		}

		// 根据区块值来获取抽取到的物品索引
		double nextDouble = Math.random();
		sortOrignalRates.add(nextDouble);
		Collections.sort(sortOrignalRates);

		return sortOrignalRates.indexOf(nextDouble);
	}

	public static int getJD(List<Double> orignalRates) {
		if (orignalRates == null || orignalRates.isEmpty()) {
			return -1;
		}

		int size = orignalRates.size();

		// 计算总概率，这样可以保证不一定总概率是1
		double sumRate = 0d;
		for (double rate : orignalRates) {
			sumRate += rate;
		}

		// 计算每个物品在总概率的基础下的概率情况
		List<Double> sortOrignalRates = new ArrayList<Double>(size);
		Double tempSumRate = 0d;
		for (double rate : orignalRates) {
			tempSumRate += rate;
			sortOrignalRates.add(tempSumRate / sumRate);
		}

		// 根据区块值来获取抽取到的物品索引
		double nextDouble = Math.random();
		sortOrignalRates.add(nextDouble);
		Collections.sort(sortOrignalRates);

		return sortOrignalRates.indexOf(nextDouble);
	}

	/**
	 * 产生随机数
	 * 
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @return 随机结果
	 */
	public static int produceRandomNumber(int min, int max) {
		return RandomUtils.nextInt(min, max); // [min,max]
	}

	public static int getPercent(int min, int max, List<Integer> separates, List<Integer> percents) {
		if (min > max) {
			throw new IllegalArgumentException("min值必须小于max值");
		}
		if (separates == null || percents == null || separates.size() == 0) {
			return 0;
		}
		if (separates.size() + 1 != percents.size()) {
			throw new IllegalArgumentException("分割数字的个数加1必须等于百分比个数");
		}
		int totalPercent = 0;
		for (Integer p : percents) {
			if (p < 0 || p > 100) {
				throw new IllegalArgumentException("百分比必须在[0,100]之间");
			}
			totalPercent += p;
		}
		if (totalPercent != 100) {
			throw new IllegalArgumentException("百分比之和必须为100");
		}
		for (int s : separates) {
			if (s <= min || s >= max) {
				throw new IllegalArgumentException("分割数值必须在(min,max)之间");
			}
		}
		int rangeCount = separates.size() + 1; // 例如：3个插值，可以将一个数值范围分割成4段
		// 构造分割的n段范围
		List<Range> ranges = new ArrayList<Range>();
		int scopeMax = 0;
		for (int i = 0; i < rangeCount; i++) {
			Range range = new Range();
			range.min = (i == 0 ? min : separates.get(i - 1));
			range.max = (i == rangeCount - 1 ? max - 1 : separates.get(i));
			range.percent = percents.get(i);

			// 片段占比，转换为[1,100]区间的数字
			range.percentScopeMin = scopeMax + 1;
			range.percentScopeMax = range.percentScopeMin + (range.percent - 1);
			scopeMax = range.percentScopeMax;

			ranges.add(range);
		}
		// 结果赋初值
		int randomInt = RandomUtils.nextInt(1, 101); // [1,100]
		int maxmum = 0;
		for (int i = 0; i < ranges.size(); i++) {
			Range range = ranges.get(i);
			// 判断使用哪个range产生最终的随机数
			if (range.percentScopeMin <= randomInt && randomInt <= range.percentScopeMax) {
				maxmum = range.percent;
				break;
			}
		}
		return maxmum;
	}

	/**
	 * 按比率产生随机数
	 * 
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @param separates
	 *            分割值
	 * @param percents
	 *            每段几率
	 * @return 按比率随机结果
	 */
	public static int produceRateRandomNumber(int min, int max, List<Integer> separates, List<Integer> percents) {
		if (min > max) {
			throw new IllegalArgumentException("min值必须小于max值");
		}
		if (separates == null || percents == null || separates.size() == 0) {
			return produceRandomNumber(min, max);
		}
		if (separates.size() + 1 != percents.size()) {
			throw new IllegalArgumentException("分割数字的个数加1必须等于百分比个数");
		}
		int totalPercent = 0;
		for (Integer p : percents) {
			if (p < 0 || p > 100) {
				throw new IllegalArgumentException("百分比必须在[0,100]之间");
			}
			totalPercent += p;
		}
		if (totalPercent != 100) {
			throw new IllegalArgumentException("百分比之和必须为100");
		}
		for (int s : separates) {
			if (s <= min || s >= max) {
				throw new IllegalArgumentException("分割数值必须在(min,max)之间");
			}
		}
		int rangeCount = separates.size() + 1; // 例如：3个插值，可以将一个数值范围分割成4段
		// 构造分割的n段范围
		List<Range> ranges = new ArrayList<Range>();
		int scopeMax = 0;
		for (int i = 0; i < rangeCount; i++) {
			Range range = new Range();
			range.min = (i == 0 ? min : separates.get(i - 1));
			range.max = (i == rangeCount - 1 ? max - 1 : separates.get(i));
			range.percent = percents.get(i);

			// 片段占比，转换为[1,100]区间的数字
			range.percentScopeMin = scopeMax + 1;
			range.percentScopeMax = range.percentScopeMin + (range.percent - 1);
			scopeMax = range.percentScopeMax;

			ranges.add(range);
		}
		// 结果赋初值
		int r = min;
		int randomInt = RandomUtils.nextInt(1, 101); // [1,100]
		for (int i = 0; i < ranges.size(); i++) {
			Range range = ranges.get(i);
			// 判断使用哪个range产生最终的随机数
			if (range.percentScopeMin <= randomInt && randomInt <= range.percentScopeMax) {
				r = produceRandomNumber(range.min, range.max);
				break;
			}
		}
		return r;
	}

	/**
	 * 按比率产生随机数
	 * 
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @param separates
	 *            分割值
	 * @param percents
	 *            每段几率
	 * @return 按比率随机结果
	 */
	public static int produceRangeNumber(int min, int max, List<Integer> separates, List<Integer> percents) {
		if (min > max) {
			throw new IllegalArgumentException("min值必须小于max值");
		}
		if (separates == null || percents == null || separates.size() == 0) {
			return max;
		}
		if (separates.size() + 1 != percents.size()) {
			throw new IllegalArgumentException("分割数字的个数加1必须等于百分比个数");
		}
		int totalPercent = 0;
		for (Integer p : percents) {
			if (p < 0 || p > 100) {
				throw new IllegalArgumentException("百分比必须在[0,100]之间");
			}
			totalPercent += p;
		}
		if (totalPercent != 100) {
			throw new IllegalArgumentException("百分比之和必须为100");
		}
		for (int s : separates) {
			if (s <= min || s >= max) {
				throw new IllegalArgumentException("分割数值必须在(min,max)之间");
			}
		}
		int rangeCount = separates.size() + 1; // 例如：3个插值，可以将一个数值范围分割成4段
		// 构造分割的n段范围
		List<Range> ranges = new ArrayList<Range>();
		int scopeMax = 0;
		for (int i = 0; i < rangeCount; i++) {
			Range range = new Range();
			range.min = (i == 0 ? min : separates.get(i - 1));
			range.max = (i == rangeCount - 1 ? max - 1 : separates.get(i));
			range.percent = percents.get(i);

			// 片段占比，转换为[1,100]区间的数字
			range.percentScopeMin = scopeMax + 1;
			range.percentScopeMax = range.percentScopeMin + (range.percent - 1);
			scopeMax = range.percentScopeMax;

			ranges.add(range);
		}
		// 结果赋初值
		int randomInt = RandomUtils.nextInt(1, 101); // [1,100]
		int maxmum = max;
		for (int i = 0; i < ranges.size(); i++) {
			Range range = ranges.get(i);
			// 判断使用哪个range产生最终的随机数
			if (range.percentScopeMin <= randomInt && randomInt <= range.percentScopeMax) {
				maxmum = range.max;
				break;
			}
		}
		return maxmum;
	}

	public static class Range {
		public int min;
		public int max;
		public int percent; // 百分比

		public int percentScopeMin; // 百分比转换为[1,100]的数字的最小值
		public int percentScopeMax; // 百分比转换为[1,100]的数字的最大值
	}

}