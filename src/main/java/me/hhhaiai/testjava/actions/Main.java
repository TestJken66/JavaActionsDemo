package me.hhhaiai.testjava.actions;

import ff.jnezha.jnt.cs.GithubHelper;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.channels.FileLock;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

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
        Map<String, String> map = System.getenv();
        String userName = map.get("USERNAME");
        String computerName = map.get("COMPUTERNAME");
        String uname = getResultString("uname -a");
        String curlRes = getResultString("curl www.google.com");
        String comtext = "测试事件:" + time
                + "\r\n提交用户:" + userName
                + "\r\n电脑:" + computerName
                + "\r\nuname:" + uname
                + "\r\ncurlRes:" + curlRes;
        String comsg = "提交从Actions";
        String TOKEN_GITHUB = getk("-Z2hwX2FUSFdIb1ZVdEg1QnZrWnJhRmZET3RpSmxKcnpVWTFrc3lOZg==-");
        GithubHelper.updateContent("hhhaiai", "testAPP", "/test.txt", TOKEN_GITHUB, comtext, comsg);

        System.out.println(comtext);
        System.out.println();
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

    private static String getResultString(String cmd) {
        String result = "";
        Process proc = null;
        BufferedInputStream in = null;
        BufferedReader br = null;
        InputStreamReader is = null;
        InputStream ii = null;
        StringBuilder sb = new StringBuilder();
        DataOutputStream os = null;
        OutputStream pos = null;
        try {
            proc = Runtime.getRuntime().exec("sh");
            pos = proc.getOutputStream();
            os = new DataOutputStream(pos);

            // donnot use os.writeBytes(commmand), avoid chinese charset error
            os.write(cmd.getBytes());
            os.writeBytes("\n");
            os.flush();
            //exitValue
            os.writeBytes("exit\n");
            os.flush();
            ii = proc.getInputStream();
            in = new BufferedInputStream(ii);
            is = new InputStreamReader(in);
            br = new BufferedReader(is);
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            if (sb.length() > 0) {
                return sb.substring(0, sb.length() - 1);
            }
            result = String.valueOf(sb);
            if (!isEmpty(result)) {
                result = result.trim();
            }
        } catch (FileNotFoundException e) {

        } catch (Throwable e) {

        } finally {
            safeClose(pos, ii, br, is, in, os);
        }

        return result;
    }

    private static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static void safeClose(Object... os) {
        if (os != null && os.length > 0) {
            for (Object o : os) {
                if (o != null) {
                    try {
                        if (o instanceof HttpURLConnection) {
                            ((HttpURLConnection) o).disconnect();
                        } else if (o instanceof Closeable) {
                            ((Closeable) o).close();
                        } else if (o instanceof FileLock) {
                            ((FileLock) o).release();
                        }

                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
