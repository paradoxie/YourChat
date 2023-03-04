package com.paradox.yourchat.http;

import android.app.Activity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HttpUtil {
    private static HttpUtil httpUtil;

    private HttpUtil() {
    }

    public static HttpUtil getInstance() {
        if (httpUtil == null) {
            httpUtil = new HttpUtil();
        }
        return httpUtil;
    }

    /**
     *
     * @param mHttpApi
     * @param Self
     * @param response
     * @param <T>
     */
    public <T> void request(Observable mHttpApi, Activity Self, Response<T> response) {
        getResponse(mHttpApi, Self, true, response);
    }


    /**
     * 获取信息
     *
     * @param mHttpApi
     * @param Self
     * @param showDialog
     * @param response
     * @param <T>
     */
    private <T> void getResponse(Observable mHttpApi, Activity Self, boolean showDialog, Response<T> response) {
        mHttpApi.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<T>(Self, showDialog) {
                    @Override
                    public void next(T o) {
                        response.success(o);
                    }

                    @Override
                    public void error(MyException myException) {
                        response.failed(myException);
                    }
                });
    }

    public  interface Response<T> {
        void success(T t) ;

        void failed(MyException myException);
    }

}
