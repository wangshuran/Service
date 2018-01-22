package com.selfsell.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class RandomUtil {

	public static final List<String> CHAR_LIST = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
			"q", "r", "s", "t", "u", "v", "w", "x", "y", "z");

	

	/**
	 * 随机生成num位的数字组合
	 * 
	 * @param num
	 * @return
	 */
	public static String genNum(Integer num) {
		Integer randomNum = new Random().nextInt(G.i(Math.pow(10D, num)));

		return String.format("%0" + num + "d", randomNum);
	}

	/**
	 * 生成几位随机字符
	 * 
	 * @param num
	 * @return
	 */
	public static String genRandomStr(Integer num) {
		StringBuffer inviteCode = new StringBuffer();
		for (int i = 0; i < num; i++) {
			int index = new Random().nextInt(CHAR_LIST.size());
			inviteCode.append(CHAR_LIST.get(index));
		}
		return inviteCode.toString();
	}


	public static void main(String[] args) {
	}
}
