package com.jhzf.controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientUtil {

    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static CloseableHttpResponse executeHttpPost(HttpPost httpPost) throws IOException {
        return httpClient.execute(httpPost);
    }

    public static String getResponseBody(CloseableHttpResponse response) throws IOException {
        return EntityUtils.toString(response.getEntity());
    }

    public static void closeHttpClient() throws IOException {
        httpClient.close();
    }
}
