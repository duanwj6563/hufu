
package com.sunlands.feo.common;

import org.apache.commons.lang.StringUtils;

import java.security.MessageDigest;

/**
 * @author duanwj
 * @describe 加密工具类
 * @since 2017年9月30日 下午5:51:09
 */
public class MD5Util {
    private static final String SALT = "";//密码盐

    public static String encode(String password) {
        if (StringUtils.isBlank(password)) {
            return "";
        }
//		password = password + SALT;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        char[] charArray = password.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

}
