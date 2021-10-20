package me.hhhaiai.testjava.actions;

import ff.jnezha.jnt.cs.GithubHelper;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 * @Copyright © 2021 sanbo Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2021/10/20 10:25 上午
 * @author: sanbo
 */
public class Main {
    public static void main(String[] args) {
        String time = getNow();
        String TOKEN_GITHUB = getk("-Z2hwX2FUSFdIb1ZVdEg1QnZrWnJhRmZET3RpSmxKcnpVWTFrc3lOZg==-");
        GithubHelper.updateContent("hhhaiai", "testAPP", "/test.txt", TOKEN_GITHUB, time, "test");

    }
    public static final String getNow() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
    }
    public static String getk(String k) {
        String s = k.replaceAll("-", "");
        try {
            byte[] bs = Base64.getDecoder().decode(s.getBytes("UTF-8"));
            return new String(bs);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }

}
