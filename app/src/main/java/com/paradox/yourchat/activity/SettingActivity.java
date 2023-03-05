package com.paradox.yourchat.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.paradox.yourchat.R;
import com.paradox.yourchat.base.BaseActivity;
import com.paradox.yourchat.databinding.ActivityMainBinding;
import com.paradox.yourchat.databinding.ActivitySettingBinding;
import com.paradox.yourchat.util.CommonUtil;

import java.util.Timer;
import java.util.TimerTask;

public class SettingActivity extends BaseActivity {
    private ActivitySettingBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding(R.layout.activity_setting);
        initViewWithData();
    }

    @Override
    public void initViewWithData() {
        binding.tvBack.setOnClickListener(v -> {
            onBackPressed();
        });
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingFragment())
                .commit();
    }


}