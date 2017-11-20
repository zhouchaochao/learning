package com.cc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Title: SimpleHttpClient
 * Description: SimpleHttpClient
 * Company: <a href=www.cc.com>cc</a>
 * Date:  2017/11/20
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
 */
public class SimpleHttpClient {


    private static Logger logger = LoggerFactory.getLogger(SimpleHttpClient.class);

    private static final int CONNECT_TIMEOUT = 5 * 1000; //2秒超时

    public static void main(String[] args) {
        try {

            for (int i = 0; i < 1000; i++) {
                try{

                }catch(Exception e){
                    logger.error(e.getMessage(),e);
                }
                String actionStr = PropertyUtil.getProperties("action"+i);//文件路径
                if(actionStr == null){
                    break;
                }
                String[] arr = actionStr.split(" @ ");
                if (arr != null) {
                    logger.info("准备执行 actionStr:" + actionStr);

                    if (arr[0].equalsIgnoreCase("get") && arr.length == 2) {
                        logger.info("get 请求");
                        logger.info(SimpleHttpClient.get(arr[1]));
                    } else if (arr[0].equalsIgnoreCase("put") && arr.length == 3) {
                        logger.info("put 请求");
                        logger.info(SimpleHttpClient.put(arr[1], arr[2]));
                    } else if (arr[0].equalsIgnoreCase("post") && arr.length == 3) {
                        logger.info("post 请求");
                        //logger.info(SimpleHttpClient.post(arr[1], arr[2]));
                    } else if (arr[0].equalsIgnoreCase("delete")) {
                        logger.info("delete 请求");
                        //logger.info(SimpleHttpClient.delete(arr[1], null));
                    } else {
                        logger.error("请求配置格式错误");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
    }


    public static String get(String urlStr, String requestMethod) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (requestMethod != null && !"".equals(requestMethod)) {
            connection.setRequestMethod(requestMethod);
        }
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(CONNECT_TIMEOUT * 20);
        connection.connect();
        InputStream in = null;
        if (connection.getResponseCode() >= 400) {
            in = connection.getErrorStream();
        } else {
            in = connection.getInputStream();
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        StringBuilder content = new StringBuilder();
        String tmp;
        while ((tmp = bufferedReader.readLine()) != null) {
            content.append(tmp);
        }
        if (in != null) {
            in.close();
        }
        bufferedReader.close();
        connection.disconnect();
        return content.toString();
    }


    public static String post(String urlStr, String content, String requestMethod, ContentType contentType) throws IOException {
        boolean error = false;
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod(requestMethod);
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-type", contentType.contentType);
        connection.connect();
        Writer writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
        if (content != null && !"".equals(content)) {
            writer.write(content);
        }
        writer.flush();
        writer.close();
        InputStream in = null;
        if (connection.getResponseCode() >= 400) {
            in = connection.getErrorStream();
            error = true;
        } else {
            in = connection.getInputStream();
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        StringBuilder response = new StringBuilder();
        String tmp;
        while ((tmp = bufferedReader.readLine()) != null) {
            response.append(tmp);
        }
        if (in != null) {
            in.close();
        }
        bufferedReader.close();
        connection.disconnect();
        if (error) {
            throw new RuntimeException(urlStr.concat(" error:").concat(response.toString()));
        }
        return response.toString();
    }

    public static enum ContentType {
        JSON("application/json");
        private String contentType;

        ContentType(String contentType) {
            this.contentType = contentType;
        }
    }

    public static String get(String urlStr) throws IOException {
        return get(urlStr, "GET");
    }

    public static String delete(String urlStr, String content) throws IOException {
        return request(urlStr, content, "DELETE", ContentType.JSON.contentType);
    }

    public static String post(String urlStr, String content) throws IOException {
        return request(urlStr, content, "POST", ContentType.JSON.contentType);
    }

    public static String put(String urlStr, String content) throws IOException {
        return request(urlStr, content, "PUT", ContentType.JSON.contentType);
    }

    public static String request(String urlStr, String content, String requestMethod, String contentType) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod(requestMethod);
        connection.setUseCaches(false);
        connection.setConnectTimeout(1000 * 5);//5秒
        connection.setReadTimeout(1000 * 60);//1分钟
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-type", contentType);
        connection.setRequestProperty("Authorization","esmadmin");
        connection.connect();
        Writer writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
        if (content != null && !"".equals(content)) {
            writer.write(content);
        }
        writer.flush();
        writer.close();
        InputStream in = null;
        if (connection.getResponseCode() >= 400) {
            in = connection.getErrorStream();
        } else {
            in = connection.getInputStream();
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        StringBuilder response = new StringBuilder();
        String tmp;
        while ((tmp = bufferedReader.readLine()) != null) {
            response.append(tmp);
        }
        if (in != null) {
            in.close();
        }
        bufferedReader.close();
        connection.disconnect();
        return response.toString();
    }


}
