package com.paradox.yourchat.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.paradox.yourchat.R;
import com.paradox.yourchat.bean.MsgBean;
import com.paradox.yourchat.util.CommonUtil;
import com.paradox.yourchat.view.MyTextView;

import java.util.ArrayList;
import java.util.List;

public class HistoryMsgAdapter extends RecyclerView.Adapter<HistoryMsgAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<MsgBean> mMsgList;

    public HistoryMsgAdapter() {
        mMsgList = new ArrayList<>();
    }

    public void setData(List<MsgBean> mMsgList) {
        if (mMsgList != null && mMsgList.size() != 0) {
            this.mMsgList.clear();
            this.mMsgList.addAll(mMsgList);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.recyclerview_details_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MsgBean msg = mMsgList.get(position);
        if (msg.getType() == MsgBean.TYPE_RECEIVE) {
            holder.tv_receive.setVisibility(View.VISIBLE);
            holder.tv_send.setVisibility(View.GONE);
            holder.tv_time.setVisibility(View.GONE);

            holder.tv_receive.setText(msg.getContent());

            holder.tv_receive.setOnClickListener(this);
            holder.tv_receive.setTag(position);

        } else {
            holder.tv_send.setVisibility(View.VISIBLE);
            holder.tv_receive.setVisibility(View.GONE);
            holder.tv_time.setVisibility(View.VISIBLE);
            holder.tv_time.setText(msg.getTime());
            holder.tv_send.setText(msg.getContent());
            holder.tv_send.setOnClickListener(this);
            holder.tv_send.setTag(position);
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        String content = mMsgList.get(position).getContent();
        Log.d("onClick", content);
        CommonUtil.INSTANCE.copy(content);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_send, tv_time, tv_receive;

        ViewHolder(View itemView) {
            super(itemView);
            tv_receive = itemView.findViewById(R.id.tv_receive);
            tv_send = itemView.findViewById(R.id.tv_send);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }
}
