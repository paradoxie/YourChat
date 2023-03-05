package com.paradox.yourchat.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSONArray;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.junmeng.lib.sharemodel.viewmodel.ShareViewModelProvider;
import com.paradox.yourchat.R;
import com.paradox.yourchat.adapter.HistoryAdapter;
import com.paradox.yourchat.adapter.HistoryMsgAdapter;
import com.paradox.yourchat.base.BaseActivity;
import com.paradox.yourchat.base.EventCode;
import com.paradox.yourchat.bean.MsgBean;
import com.paradox.yourchat.databinding.ActivityHistoryDetailsBinding;
import com.paradox.yourchat.databinding.ActivityHistoryListBinding;
import com.paradox.yourchat.room.common.CommonEntity;
import com.paradox.yourchat.room.common.CommonViewModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryDetailsActivity extends BaseActivity {
    private ActivityHistoryDetailsBinding binding;
    private HistoryMsgAdapter historyMsgAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding(R.layout.activity_history_details);
        initViewWithData();
    }

    @Override
    public void initViewWithData() {
        binding.tvBack.setOnClickListener(v -> {
            finish();
        });


        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        historyMsgAdapter = new HistoryMsgAdapter();
        binding.recyclerview.setAdapter(historyMsgAdapter);

        LiveEventBus.get(EventCode.HISTORY_BEAN, String.class)
                .observeSticky(this, content -> {
                    JSONArray tableData = JSONArray.parseArray(content);
                    List<MsgBean> msgBeans = tableData.toJavaList(MsgBean.class);
                    historyMsgAdapter.setData(msgBeans);
                });

    }


}