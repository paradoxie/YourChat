package com.paradox.yourchat.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.paradox.yourchat.R;
import com.paradox.yourchat.activity.HistoryDetailsActivity;
import com.paradox.yourchat.base.BaseActivity;
import com.paradox.yourchat.base.EventCode;
import com.paradox.yourchat.room.common.CommonEntity;
import com.paradox.yourchat.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private ArrayList<CommonEntity> mMsgList;
    private BaseActivity context;
    private LongClickListener listener;

    public HistoryAdapter(BaseActivity context,LongClickListener listener) {
        this.context = context;
        this.listener = listener;
        mMsgList = new ArrayList<>();
    }

    public void setData(List<CommonEntity> mMsgList) {
        if (mMsgList != null && mMsgList.size() != 0) {
            this.mMsgList.clear();
            this.mMsgList.addAll(mMsgList);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.recyclerview_history_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CommonEntity msg = mMsgList.get(position);

        holder.tv_time.setText(msg.getCreate_time());
        holder.tv_send.setText(msg.getSend());
        holder.tv_receive.setText(msg.getReceive());

        holder.rl.setOnClickListener(this);
        holder.rl.setOnLongClickListener(this);
        holder.rl.setTag(position);

    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        String content = mMsgList.get(position).getContent();
        if (!TextUtils.isEmpty(content)) {
            LiveEventBus
                    .get(EventCode.HISTORY_BEAN)
                    .post(content);
            CommonUtil.INSTANCE.startActivityWithAnimate(context, HistoryDetailsActivity.class);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        int position = (Integer) v.getTag();
        listener.longClick(position);
        return false;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_send, tv_time, tv_receive;
        private RelativeLayout rl;

        ViewHolder(View itemView) {
            super(itemView);
            tv_receive = itemView.findViewById(R.id.tv_receive);
            tv_send = itemView.findViewById(R.id.tv_send);
            tv_time = itemView.findViewById(R.id.tv_time);
            rl = itemView.findViewById(R.id.rl);
        }
    }

   public interface LongClickListener {
        void longClick(int pos);
    }
}
