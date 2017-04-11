package cn.edu.pku.sei.tsr.dbinsight.commons.util.hash;

import cn.edu.pku.sei.tsr.dbinsight.commons.lang.InternalServerErrorException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * Created by Icing on 2016/12/23.
 */
public class HashEncoder {

    public static String sha1(byte[] bytes) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1");
            digest.update(bytes);
            byte messageDigest[] = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String shaHex = Integer.toHexString(aMessageDigest & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new InternalServerErrorException("sha1计算出错");
        }
    }

    public static String sha1(String str) {
        try {
            return sha1(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new InternalServerErrorException(e);
        }
    }

}
