package com.paradox.yourchat.activity;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.junmeng.lib.sharemodel.viewmodel.ShareViewModelProvider;
import com.paradox.yourchat.R;
import com.paradox.yourchat.adapter.HistoryAdapter;
import com.paradox.yourchat.base.BaseActivity;
import com.paradox.yourchat.databinding.ActivityHistoryListBinding;
import com.paradox.yourchat.room.common.CommonEntity;
import com.paradox.yourchat.room.common.CommonViewModel;
import com.paradox.yourchat.util.CommonUtil;
import com.paradox.yourchat.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class HistoryListActivity extends BaseActivity {
    private ActivityHistoryListBinding binding;
    private CommonViewModel commonViewModel;
    private HistoryAdapter historyAdapter;
    private List<CommonEntity> commonEntities = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonViewModel = ShareViewModelProvider.get(this, CommonViewModel.class);
        binding = getBinding(R.layout.activity_history_list);
        initViewWithData();
    }

    @Override
    public void initViewWithData() {
        binding.tvBack.setOnClickListener(v -> {
            finish();
        });

        commonViewModel.getAll().observe(this, entities -> {
            if (entities != null) {
                commonEntities.clear();
                commonEntities.addAll(entities);
                historyAdapter.setData(commonEntities);
            }
        });

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new HistoryAdapter(this, pos -> {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.delete_con)//设置对话框的标题
                    .setMessage(R.string.delete_info)//设置对话框的内容
                    //设置对话框的按钮
                    .setNegativeButton(R.string.cancel, (dialog1, which) -> dialog1.dismiss())
                    .setPositiveButton(R.string.confirm_delete, (dialog12, which) -> {
                        CommonUtil.INSTANCE.toast(getString(R.string.deleted_successfully));
                        commonViewModel.delete(commonEntities.get(pos).getId());
                        dialog12.dismiss();
                    }).create();
            dialog.show();
        });
        binding.recyclerview.setAdapter(historyAdapter);
        binding.recyclerview.addItemDecoration(new SpaceItemDecoration(20));

    }


}