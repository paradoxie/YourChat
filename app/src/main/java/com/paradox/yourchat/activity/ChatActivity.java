package com.paradox.yourchat.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.junmeng.lib.sharemodel.viewmodel.ShareViewModelProvider;
import com.paradox.yourchat.R;
import com.paradox.yourchat.adapter.MsgAdapter;
import com.paradox.yourchat.base.BaseActivity;
import com.paradox.yourchat.bean.ChatResBean;
import com.paradox.yourchat.bean.JsonMsg;
import com.paradox.yourchat.bean.MsgBean;
import com.paradox.yourchat.databinding.ActivityChatBinding;
import com.paradox.yourchat.http.HttpUtil;
import com.paradox.yourchat.http.MyException;
import com.paradox.yourchat.room.common.CommonEntity;
import com.paradox.yourchat.room.common.CommonViewModel;
import com.paradox.yourchat.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ChatActivity extends BaseActivity {
    private ActivityChatBinding binding;
    private CommonViewModel commonViewModel;
    private List<MsgBean> msgBeans = new ArrayList<>();
    //提交给api的聊天记录
    private List<JsonMsg> msgBeansForApi = new ArrayList<>();
    private MsgAdapter msgAdapter;
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private CommonEntity commonEntity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonViewModel = ShareViewModelProvider.get(this, CommonViewModel.class);
        binding = getBinding(R.layout.activity_chat);
        initViewWithData();
    }

    @Override
    public void initViewWithData() {
        binding.tvBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.tvHistory.setOnClickListener(v -> {
            gotoActivity(HistoryListActivity.class);
        });

        binding.ivLoading.setVisibility(View.GONE);
        Glide.with(this)
                .asGif()
                .load(R.drawable.loading)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(binding.ivLoading);

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

            binding.recyclerview.smoothScrollToPosition(msgAdapter.getItemCount() - 1);
            binding.edInput.setText("");
            chat(input);
        });

    }

    private void chat(String input) {
        try {
            binding.ivLoading.setVisibility(View.VISIBLE);
            JsonMsg user = new JsonMsg("user", input);


            ArrayList<Object> arr = new ArrayList<>();
            arr.addAll(msgBeansForApi);
            arr.add(user);
//            arr.add(user);
            msgBeansForApi.add(user);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("model", "gpt-3.5-turbo");
            jsonObject.put("messages", arr);


            RequestBody body = RequestBody.create(JSON, jsonObject.toJSONString());
            Log.e("print", jsonObject.toJSONString());
            HttpUtil.getInstance().request(mHttpApi.chat(body), SELF, new HttpUtil.Response<ChatResBean>() {
                @Override
                public void success(ChatResBean o) {

                    binding.ivLoading.setVisibility(View.GONE);
                    Log.e("print", o.toString());
                    String str = o.getChoices().get(0).getMessage().getContent();
                    if (str.startsWith("\n")) {
                        str = str.substring(2);
                    }
                    msgBeansForApi.add(new JsonMsg(o.getChoices().get(0).getMessage().getRole(), str));
                    MsgBean msgBean = new MsgBean(str, 1, CommonUtil.INSTANCE.getCurrentTime());
                    msgBeans.add(msgBean);
                    msgAdapter.setData(msgBeans);

                    binding.recyclerview.smoothScrollToPosition(msgAdapter.getItemCount() - 1);


                    saveRoom(o.getId());
                }

                @Override
                public void failed(MyException myException) {
                    binding.ivLoading.setVisibility(View.GONE);
                }
            });
        } catch (Exception e) {
            Log.e("error", e.toString());
            binding.ivLoading.setVisibility(View.GONE);
        }

    }

    //保存数据库，如果存在则更新，不存在则添加；保存格式：第一条提问的创建时间，第一条提问内容，第一条回复内容，以及整个对话的序列化文本
    private void saveRoom(String id) {
        if (commonEntity == null) {
            commonEntity = new CommonEntity(id,
                    msgBeans.get(0).getContent(),
                    msgBeans.get(1).getContent(),
                    msgBeans.get(0).getTime(),
                    JSONObject.toJSONString(msgBeans));

            commonViewModel.insert(commonEntity);
        } else {
            commonViewModel.update(commonEntity.getComId(), JSONObject.toJSONString(msgBeans));
        }
    }

    @Override
    public void onBackPressed() {
        if (commonEntity != null) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.exit_conversation)//设置对话框的标题
                    .setMessage(R.string.exit_con_info)//设置对话框的内容
                    //设置对话框的按钮
                    .setNegativeButton(R.string.back, (dialog1, which) -> dialog1.dismiss())
                    .setPositiveButton(R.string.confirm_exit, (dialog12, which) -> {
                        dialog12.dismiss();
                        finish();
                    }).create();
            dialog.show();
        } else {
            finish();
        }
    }
}