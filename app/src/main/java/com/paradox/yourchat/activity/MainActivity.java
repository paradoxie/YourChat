package com.paradox.yourchat.activity;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.paradox.yourchat.R;
import com.paradox.yourchat.adapter.MsgAdapter;
import com.paradox.yourchat.base.BaseActivity;
import com.paradox.yourchat.bean.ChatResBean;
import com.paradox.yourchat.bean.JsonMsg;
import com.paradox.yourchat.bean.MsgBean;
import com.paradox.yourchat.databinding.ActivityMainBinding;


import com.alibaba.fastjson.JSONObject;
import com.paradox.yourchat.http.HttpUtil;
import com.paradox.yourchat.http.MyException;
import com.paradox.yourchat.room.common.CommonEntity;
import com.paradox.yourchat.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;

    private static Boolean isExit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding(R.layout.activity_main);
        initViewWithData();
    }

    @Override
    public void initViewWithData() {
        binding.clCommon.setOnClickListener(v -> {
            gotoActivity(ChatActivity.class);
        });
        binding.tvCommonIn.setOnClickListener(v -> {
            gotoActivity(ChatActivity.class);
        });

        binding.tvCommonHistory.setOnClickListener(v -> {
            gotoActivity(HistoryListActivity.class);
        });

        binding.llSpecial.setOnClickListener(v -> {
            CommonUtil.INSTANCE.toast(getResources().getString(R.string.stay_tuned));
        });
        binding.tvSetting.setOnClickListener(v -> {
            gotoActivity(SettingActivity.class);
        });
    }


    @Override
    public void onBackPressed() {
        exitBy2Click();
    }

    private void exitBy2Click() {
        Timer tExit = null;
        if (!isExit) {
            isExit = true;
            // 准备退出
            Toast.makeText(SELF, R.string.exit_app, Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            finish();
//            System.exit(0);
        }
    }

}