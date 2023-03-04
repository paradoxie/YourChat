package com.paradox.yourchat.base;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.paradox.yourchat.util.CommonUtil;

import io.reactivex.plugins.RxJavaPlugins;

public class MyApplication extends Application {
    static MyApplication myApplication;
    static Activity activity;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        initConfig();
        setRxJavaErrorHandler();
    }

    private void initConfig() {
        CommonUtil.INSTANCE.init(MyApplication.this);
    }

    public static Application getContext() {
        return myApplication;
    }
    public static Activity getActivityContext() {
        return activity;
    }
    //rxJava异常捕获
    private void setRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler(throwable -> {
            throwable.printStackTrace();
            Log.e("MyApplication", "MyApplication setRxJavaErrorHandler " + throwable.getMessage());
        });
    }
}
