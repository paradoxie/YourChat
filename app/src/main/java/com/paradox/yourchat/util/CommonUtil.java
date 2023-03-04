package com.paradox.yourchat.util;

import static android.content.Context.WINDOW_SERVICE;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


import com.paradox.yourchat.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public enum CommonUtil {
    INSTANCE;
    private static Context mContext;
    private List<Dialog> dialogs;
    private ClipboardManager cm;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void init(Context context) {
        mContext = context;

        dialogs = new ArrayList<>();
    }

    public void toast(final String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }

    }

    /**
     * 复制到粘贴板
     *
     * @param activity
     * @param s
     */
    public void copy(Activity activity, String s) {
        cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(s);
        toast("复制成功");
    }


    public void startActivityWithAnimate(Activity activity, Class clazz) {
        Intent intent = new Intent(mContext, clazz);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        //右侧移入
        activity.overridePendingTransition(R.anim.h_fragment_enter, R.anim.h_fragment_exit);
    }

    public void startActivityWithAnimateNoExit(Activity activity, Class clazz) {
        Intent intent = new Intent(mContext, clazz);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        //右侧移入
        activity.overridePendingTransition(R.anim.h_fragment_enter, R.anim.h_fragment_exit_no_delay);
    }


    public Point getScreenInfo(Activity activity) {
        Point point = new Point();

        //第一种途径
        Display display = ((WindowManager) mContext.getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        display.getSize(point);
        if (point.x == 0 || point.y == 0) {//第二种途径
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            int height = dm.heightPixels;
            int width = dm.widthPixels;
            point.set(width, height);
        }
        return point;
    }


    public void addDialog(Dialog dialog) {
        dialogs.add(dialog);
    }

    public void hideDialog() {
        for (Dialog dialog : dialogs) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        dialogs.clear();
    }


    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * 获取版本号
     */
    public static int getAppVersionCode(Context context) {
        int versionCode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;

        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionCode;
    }


    //隐藏软键盘
    public void hideSoftInput(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView()
                    .getWindowToken(), 0);
        }
    }

    //弹出软键盘
    public void showSoftInput(View v) {
        InputMethodManager inputManager = (InputMethodManager) v
                .getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(v, 0);
    }

    /**
     * 获取当前时间
     * @return
     */
    public String getCurrentTime() {
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);

        return str;
    }

}
