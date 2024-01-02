package me.hamtom.redblue.assignment.hashmap;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMap 이용하여 String 글자 수 카운트
 */
public class HashMapCountString {
    /**
     * 모든 문자열 카운트
     */
    public static Map<String, Integer> countString(String str) {
        Map<String, Integer> countMap = new HashMap<>();
        for (char ch : str.toCharArray()) {
            String key = String.valueOf(ch);
            //빈 칸이면 key: blank 설정
            if (Character.isWhitespace(ch)) {
                key = "blank";
            }
            int count = countMap.getOrDefault(key, 0) + 1;
            countMap.put(key, count);
        }
        return countMap;
    }

    /**
     * 빈 칸을 제외한 모든 문자열 카운트
     */
    public static Map<Character, Integer> countStringWithoutBlank(String str) {
        Map<Character, Integer> countMap = new HashMap<>();
        str = str.replace(" ", "");
        for (char ch : str.toCharArray()) {
            int count = countMap.getOrDefault(ch, 0) + 1;
            countMap.put(ch, count);
        }
        return countMap;
    }

    /**
     * 모든 문자열을 소문자로 바꾼 후 카운트
     */
    public static Map<String, Integer> countStringRegardlessOfCase(String str) {
        str = str.toLowerCase();
        return countString(str);
    }

    public static void main(String[] args) {
        String testStr = "test With RedBlue";
        Map<String, Integer> test1 = countString(testStr);
        System.out.println("1: " + test1);
        Map<Character, Integer> test2 = countStringWithoutBlank(testStr);
        System.out.println("2: " + test2);
        Map<String, Integer> test3 = countStringRegardlessOfCase(testStr);
        System.out.println("3: " + test3);
    }
}
