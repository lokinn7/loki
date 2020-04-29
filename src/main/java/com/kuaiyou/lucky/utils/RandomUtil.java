package com.kuaiyou.lucky.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * 随机数工具，用于产生随机数，随机密码等
 */
public class RandomUtil {
	private static final Random random = new Random();

	public static Random getRandom() {
		return random;
	}

	public enum Type {
		NUMBER, ANY;
	}

	static char[] chars = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z' };

	/**
	 * 随机生成由0-9a-zA-Z组合而成的字符串
	 *
	 * @param len
	 *            字符串长度
	 * @return 生成结果
	 */
	public static String randomChar(int len) {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < len; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();
	}

	/**
	 * 随机生成由0-9a-zA-Z组合而成的字符串
	 *
	 * @param len
	 *            字符串长度
	 * @return 生成结果
	 */
	public static String getRandomChar(int length) {
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(chars[random.nextInt(chars.length)]);
		}
		return buffer.toString();
	}

	public static String randomChar() {
		return randomChar(8);
	}

	public static String seed(int length, Type type) {
		if (length < 1) {
			return "";
		}
		int limit = 62;
		if (Type.NUMBER == type) {
			limit = 10;
		}
		char cArr[] = new char[length];
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int idx = random.nextInt(limit);
			if (idx < 10) {
				cArr[i] = (char) ('0' + idx);
			} else if (idx < 36) {
				cArr[i] = (char) ('a' + idx - 10);
			} else {
				cArr[i] = (char) ('A' + idx - 36);
			}
		}
		return new String(cArr);
	}

	private final static String LEFTS = "lefts";
	private final static String BINGOS = "bingos";

	// 抽奖 传参 1.总人数 2.几人中奖
	public static Map<String, List<Integer>> getLuckList(List<Integer> idList, int n) {
		// id集合
		// 取得中奖人id
		ArrayList<Integer> a = makeRandom(0, idList.size(), n);
		// 获奖id临时存储集合
		ArrayList<Integer> eidList = new ArrayList<Integer>();
		// 排序
		Object[] b = a.toArray();
		Arrays.sort(b);
		// 定义一个中奖人数据集合
		List<Integer> t = new ArrayList<>();
		for (int i = 0; i < b.length; i++) {
			eidList.add(idList.get((int) b[i]));
			t.add(idList.get((int) b[i]));
		}
		// 总人数中移除中奖人数据
		idList.removeAll(t);
		// 写到Map集合回传
		Map<String, List<Integer>> m = new HashMap<>();
		// 剩余id
		m.put(LEFTS, idList);
		// 获奖id
		m.put(BINGOS, eidList);
		return m;
	}

	public static void main(String[] args) {
		ArrayList<Integer> makeRandom = makeRandom(0, 0, 10);
		System.out.println(makeRandom);
	}

	// 从x-y 取num个随机数
	public static ArrayList<Integer> makeRandom(int x, int y, int num) {
		// 创建一个integer的动态数组
		ArrayList<Integer> a = new ArrayList<Integer>();
		int index = 0;
		// 往数组里面逐一加取到不重复的元素
		while (index < num) {
			// 产生x-y的随机数
			int temp = random.nextInt(y - x) + x;
			// 设置是否重复的标记变量为false
			boolean flag = false;
			for (int i = 0; i < index; i++) {
				if (temp == a.get(i)) {
					flag = true;
					break;
				}
			}
			if (flag == false) {
				a.add(temp);
				index++;
			}
		}
		return a;
	}
}