package com.paradox.yourchat.http;

import android.text.TextUtils;

import com.paradox.yourchat.util.SPUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Request.Builder builder = request.newBuilder();

        builder.addHeader("Content-Type", " application/json");
        String token = SPUtil.get("key", "").toString();
        if (!TextUtils.isEmpty(token)) {
            builder.addHeader("Authorization", "Bearer " + token);
        }
        request = builder.build();
        Response response = chain.proceed(request);
        return response;

    }
}
