package com.paradox.yourchat.http;

import android.app.Activity;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.paradox.yourchat.base.BaseActivity;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public abstract class MyObserver<T> implements Observer<T> {

    private CompositeDisposable compositeDisposable;
    private boolean showDialog = true;
    private final BaseActivity baseActivity;

    public MyObserver(Activity baseActivity) {
        this.baseActivity = (BaseActivity) baseActivity;

    }

    public MyObserver(Activity baseActivity, boolean showDialog) {
        this(baseActivity);
        this.showDialog = showDialog;
    }

    @Override
    public void onSubscribe(Disposable d) {
        baseActivity.addDispose(d);
        subscribe(d);
    }

    @Override
    public void onNext(T t) {
        next(t);
    }

    @Override
    public void onError(Throwable e) {

        MyException myException = new MyException();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            myException.httpCode = httpException.code();
            ResponseBody responseBody = httpException.response().errorBody();
            try {
                String json = responseBody.string();


            } catch (IOException ioException) {
                ioException.printStackTrace();
//                myException.reason = Arrays.toString(ioException.getStackTrace());
                myException.reason = "解析数据出错";
            }
        } else {
            myException.throwable = e;
            myException.reason = e.getMessage();
        }



    }

    @Override
    public void onComplete() {
//        complete();
    }

    public void subscribe(Disposable d) {

    }

    public abstract void next(T t);

    public void error(MyException myException) {

    }
}
