package com.paradox.yourchat.http;

import com.paradox.yourchat.bean.ChatResBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface HttpApi {
    //获取会话
    @POST("completions")
    Observable<ChatResBean> chat(@Body RequestBody body);
}
