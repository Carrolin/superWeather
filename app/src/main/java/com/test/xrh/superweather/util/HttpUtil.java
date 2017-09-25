package com.test.xrh.superweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by admin on 2017/9/25.
 */

public class HttpUtil {
    public static void sendOkhttpRequest(String url ,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        //传入地址,注册回调
        client.newCall(request).enqueue(callback);
    }
}
