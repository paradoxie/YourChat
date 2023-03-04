package com.paradox.yourchat.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSONObject;
import com.paradox.yourchat.R;
import com.paradox.yourchat.adapter.MsgAdapter;
import com.paradox.yourchat.base.BaseActivity;
import com.paradox.yourchat.bean.ChatResBean;
import com.paradox.yourchat.bean.JsonMsg;
import com.paradox.yourchat.bean.MsgBean;
import com.paradox.yourchat.databinding.ActivityChatBinding;
import com.paradox.yourchat.databinding.ActivityMainBinding;
import com.paradox.yourchat.http.HttpUtil;
import com.paradox.yourchat.http.MyException;
import com.paradox.yourchat.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ChatActivity extends BaseActivity {
    private ActivityChatBinding binding;
    private List<MsgBean> msgBeans = new ArrayList<>();
    private MsgAdapter msgAdapter;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static Boolean isExit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding(R.layout.activity_chat);
        initViewWithData();
    }

    @Override
    public void initViewWithData() {
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        msgAdapter = new MsgAdapter();
        binding.recyclerview.setAdapter(msgAdapter);

        binding.recyclerview.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> binding.recyclerview.scrollToPosition(msgAdapter.getItemCount() - 1));

        binding.btnSend.setOnClickListener(v -> {
            String input = binding.edInput.getText().toString().trim();
            if (TextUtils.isEmpty(input)) {
                Toast.makeText(SELF, R.string.please_input_something, Toast.LENGTH_SHORT).show();
                return;
            }
            MsgBean msgBean = new MsgBean(input, 2, CommonUtil.INSTANCE.getCurrentTime());
            msgBeans.add(msgBean);
            msgAdapter.setData(msgBeans);
            binding.edInput.setText("");
            binding.recyclerview.smoothScrollToPosition(msgAdapter.getItemCount() - 1);
            chat(input);
        });
    }

    private void chat(String input) {
        try {
            JsonMsg user = new JsonMsg("user", input);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("model", "gpt-3.5-turbo");

            ArrayList<Object> arr = new ArrayList<>();
            arr.add(user);

            jsonObject.put("messages", arr);

            RequestBody body = RequestBody.create(JSON, jsonObject.toJSONString());
            Log.e("print", jsonObject.toJSONString());
            HttpUtil.getInstance().request(mHttpApi.chat(body), SELF, new HttpUtil.Response<ChatResBean>() {
                @Override
                public void success(ChatResBean o) {
                    Log.e("print", o.toString());
                    String str = o.getChoices().get(0).getMessage().getContent().substring(2);
                    MsgBean msgBean = new MsgBean(str, 1,CommonUtil.INSTANCE.getCurrentTime());
                    msgBeans.add(msgBean);
                    msgAdapter.setData(msgBeans);

                    binding.recyclerview.smoothScrollToPosition(msgAdapter.getItemCount() - 1);
                }

                @Override
                public void failed(MyException myException) {

                }
            });
        } catch (Exception e) {
            Log.e("error", e.toString());
        }

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