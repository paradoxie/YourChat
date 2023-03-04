package com.paradox.yourchat.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.paradox.yourchat.R;
import com.paradox.yourchat.activity.MainActivity;
import com.paradox.yourchat.base.BaseActivity;
import com.paradox.yourchat.databinding.ActivityWelcomeBinding;
import com.paradox.yourchat.util.SPUtil;

public class WelcomeActivity extends BaseActivity {
    private ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding(R.layout.activity_welcome);
        initViewWithData();
    }

    @Override
    public void initViewWithData() {

        String token = SPUtil.get("key", "").toString();
        if (!TextUtils.isEmpty(token)) {
            binding.clInput.setVisibility(View.GONE);
            binding.clInfo.setVisibility(View.VISIBLE);
            mHandler.postDelayed(() -> {
                gotoActivity(MainActivity.class);
                finish();
            }, 2000);

        } else {
            binding.clInput.setVisibility(View.VISIBLE);
            binding.clInfo.setVisibility(View.GONE);
            binding.edKey.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (TextUtils.isEmpty(s)) {
                        binding.btnNext.setHint(R.string.str_input);
                    } else {
                        binding.btnNext.setHint(R.string.str_next);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            binding.btnNext.setOnClickListener(v -> {
                String input = binding.edKey.getText().toString().trim();
                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(SELF, R.string.please_input_your_key, Toast.LENGTH_SHORT).show();
                    return;
                }

                SPUtil.put("key", input);

                gotoActivity(MainActivity.class);
                finish();
            });
        }


    }

}