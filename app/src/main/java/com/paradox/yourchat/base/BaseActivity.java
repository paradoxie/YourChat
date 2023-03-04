package com.paradox.yourchat.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;

import com.paradox.yourchat.http.HttpApi;
import com.paradox.yourchat.http.RetrofitManager;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Retrofit;

public abstract class BaseActivity extends FragmentActivity {
    protected Activity SELF = null;
    public HttpApi mHttpApi;
    protected Handler mHandler = new Handler(Looper.getMainLooper());
    public CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    public abstract void initViewWithData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SELF = this;
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        mHttpApi = retrofit.create(HttpApi.class);
    }

    protected <T extends ViewDataBinding> T getBinding(@LayoutRes int layoutId) {
        return DataBindingUtil.setContentView(this, layoutId);
    }

    public void addDispose(Disposable disposable) {
        mCompositeDisposable.add(disposable);

    }


    public void gotoActivity(Class clazz) {
        startActivity(new Intent(SELF, clazz));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();

    }
}
