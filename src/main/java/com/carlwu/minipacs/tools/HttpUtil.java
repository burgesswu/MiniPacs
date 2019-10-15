package com.carlwu.minipacs.tools;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class HttpUtil {
    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);
    Map<String, String> params;
    String url;
    static String code;

    public static String post(String url, Map<String, String> params, String token) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        String body = null;

        log.info("create http post:" + url);
        HttpPost post = postForm(url, params);
        if (!StringUtils.isBlank(token)) {
            post.setHeader("Authentication", token);
        }
        post.setHeader("platform", "CLIENT");

        body = invoke(httpclient, post);

        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }


    public static String put(String url, Map<String, String> params, String token) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        String body = null;

        log.info("create http put:" + url);
        HttpPut put = putForm(url, params);
        if (!StringUtils.isBlank(token)) {
            put.setHeader("Authentication", token);
        }
        put.setHeader("platform", "CLIENT");

        body = invoke(httpclient, put);

        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }

    public HttpUtil(String url, Map<String, String> params) {
        this.url = url;
        this.params = params;
    }

    public static String get(String url) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        String body = null;

        log.info("create http get:" + url);
        HttpGet get = new HttpGet(url);
        body = invoke(httpclient, get);

        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }


    private static String invoke(CloseableHttpClient httpclient,
                                 HttpUriRequest httpost) {

        HttpResponse response = sendRequest(httpclient, httpost);
        String body = paseResponse(response);

        return body;
    }

    private static String paseResponse(HttpResponse response) {
        log.info("get response from http server..");
        HttpEntity entity = response.getEntity();

        log.info("response status: " + response.getStatusLine());
//        String charset = ContentType.get(entity).getCharset().name();
//        log.info(charset);

        String body = null;
        try {
            body = EntityUtils.toString(entity);
            log.info(body);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        code = "response status: " + response.getStatusLine() + "\n";

        return body;
    }

    private static HttpResponse sendRequest(CloseableHttpClient httpclient,
                                            HttpUriRequest httpost) {
        log.info("execute post...");
        HttpResponse response = null;

        try {
            response = httpclient.execute(httpost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static HttpPost postForm(String url, Map<String, String> params) {
        URIBuilder builder;
        HttpPost httpost = null;
        try {
            builder = new URIBuilder(url);

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                nvps.add(new BasicNameValuePair(key, params.get(key)));
            }

            builder.setCharset(Consts.UTF_8);
            builder.setParameters(nvps);
            httpost = new HttpPost(builder.build());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return httpost;

    }

    private static HttpPut putForm(String url, Map<String, String> params) {
        URIBuilder builder;
        HttpPut httpPut = null;
        try {
            builder = new URIBuilder(url);

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                nvps.add(new BasicNameValuePair(key, params.get(key)));
            }

            builder.setCharset(Consts.UTF_8);
            builder.setParameters(nvps);
            httpPut = new HttpPut(builder.build());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return httpPut;

    }

    public String postWithCode() {
        String xml = HttpUtil.post(url, params, "");
        return code + xml;
    }

    public static void main(String[] args) {
        Map dataMap = new HashMap<String, String>();
        dataMap.put("username", "13348940291");
        dataMap.put("password", "123456");

        String res = post("http://192.168.0.118:9527/login", dataMap, "");
        System.out.println(res);
    }
}
