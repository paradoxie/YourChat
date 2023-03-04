package com.paradox.yourchat.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.paradox.yourchat.view.MyTextView;
import com.paradox.yourchat.R;
import com.paradox.yourchat.bean.MsgBean;

import java.util.ArrayList;
import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private ArrayList<MsgBean> mMsgList;

    public MsgAdapter() {
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
        View view = View.inflate(parent.getContext(), R.layout.recyclerview_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MsgBean msg = mMsgList.get(position);
        if (msg.getType() == MsgBean.TYPE_RECEIVE) {
            holder.tv_receive.setVisibility(View.VISIBLE);
            holder.tv_send.setVisibility(View.GONE);
            holder.tv_time.setVisibility(View.GONE);
            if (position == mMsgList.size() - 1) {
                holder.tv_receive.setOnTypeViewListener(new MyTextView.OnTypeViewListener() {
                    @Override
                    public void onTypeStart() {
                        Log.e("onTypeStart", "tv_receive");
                    }

                    @Override
                    public void onTypeOver() {
                        Log.e("onTypeOver", "tv_receive");
                    }
                });
                holder.tv_receive.start(msg.getContent());
            } else {
                holder.tv_receive.setOnTypeViewListener(null);
                holder.tv_receive.setText(msg.getContent());
            }

        } else {
            holder.tv_send.setVisibility(View.VISIBLE);
            holder.tv_receive.setVisibility(View.GONE);
            holder.tv_time.setVisibility(View.VISIBLE);
            holder.tv_time.setText(msg.getTime());
            holder.tv_send.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private MyTextView tv_receive;
        private TextView tv_send, tv_time;

        ViewHolder(View itemView) {
            super(itemView);
            tv_receive = itemView.findViewById(R.id.tv_receive);
            tv_send = itemView.findViewById(R.id.tv_send);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }
}
