package com.yanchenmeng.github;

public class StringUtil {

    public static boolean isEmpty(String str) {
        if (null == str || str.trim().equals("") || str.trim().equalsIgnoreCase("null")) {
            return true;
        }
        return false;
    }
}
