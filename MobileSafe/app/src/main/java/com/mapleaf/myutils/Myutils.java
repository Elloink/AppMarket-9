package com.mapleaf.myutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Mapleaf on 2016/5/20.
 */

public class Myutils {
    public static String parseInputStream(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringWriter sw = new StringWriter();
        String str = null;
        while((str=br.readLine())!=null){
            sw.write(str);
        }
        sw.close();
        br.close();
        return sw.toString();
    }
    public static String MD5password(String password){
        try {
            StringBuilder sb = new StringBuilder();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(password.getBytes());
            for(int i = 0 ;i<digest.length;i++){
                int m = digest[i]&0xff;
                String hex = Integer.toHexString(m);
                if(hex.length()<2){
                    sb.append("0");
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
