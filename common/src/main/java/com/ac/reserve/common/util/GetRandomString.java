package com.ac.reserve.common.util;

import java.util.Random;

/**
 * @Author: fan shi ke
 * @Description:
 * @Date: Created at 15:28 2019/7/26
 * @Modified By:
 */
public class GetRandomString {

    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
