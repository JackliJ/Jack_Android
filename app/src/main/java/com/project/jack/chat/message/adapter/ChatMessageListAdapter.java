package com.project.jack.chat.message.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMMessage;
import com.project.jack.R;
import com.project.jack.chat.util.ApplicationUtils;
import com.project.jack.chat.util.CalendarUtil;
import com.project.jack.chat.util.ChatItemClickListener;
import com.project.jack.chat.util.Constant;
import com.project.jack.chat.util.EmotionUtils;
import com.project.jack.chat.util.SpanStringUtils;
import com.project.jack.chat.util.TimeUtils;
import com.project.jack.chat.weight.ShapedImageView;
import com.project.jack.utils.SharedPreferencedUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import jack.project.com.imdatautil.IMDataUtil;
import jack.project.com.imdatautil.model.MMSession;
import jack.project.com.imdatautil.model.MMessage;

/**
 * Create by www.lijin@foxmail.com on 2018/1/23 0023.
 * <br/>
 */

public class ChatMessageListAdapter extends RecyclerView.Adapter<ChatMessageListAdapter.ViewHolder> {

    /**
     * 头部，包含搜索，小秘书，脉友圈
     */
    private static final int CHAT_MESSAGE_HEAD = 0X001;
    /**
     * 消息item
     */
    private static final int CHAT_MESSAGE_ITEM = 0X002;


    Context mContext;
    public List<MMSession> mmSessions;
    private ChatItemClickListener mListener;

    public ChatMessageListAdapter(Context context, List<MMSession> mmSessions) {
        this.mContext = context;
        this.mmSessions = mmSessions;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return CHAT_MESSAGE_HEAD;
        } else {
            return CHAT_MESSAGE_ITEM;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int viewRes = -1;
        switch (viewType) {
            case CHAT_MESSAGE_HEAD: {
                viewRes = R.layout.row_chat_message_list_head;
            }
            break;
            case CHAT_MESSAGE_ITEM: {
                viewRes = R.layout.row_chat_message_list;
            }
            break;
        }
        return new ViewHolder(LayoutInflater.from(mContext).inflate(viewRes, parent, false), mListener);
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(ChatItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final int mPosition = position - 1;
        switch (getItemViewType(position)) {
            case CHAT_MESSAGE_HEAD:
                break;
            case CHAT_MESSAGE_ITEM:
                //=================================================群聊========================================================
                holder.vTdelete.setText(mContext.getResources().getString(R.string.chat_cp_delete));
                holder.vItemDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.vTdelete.getText().toString().equals(mContext.getResources().getString(R.string.chat_cp_delete_ok))){
                            //http://blog.csdn.net/iamdingruihaha/article/details/73274010
                            //由于notifyItemRangeChanged是多线程的 快速删除也可能导致越界，上面的解决方式不够优雅 可以参考
                            try {
                                //删除会话
                                IMDataUtil.DeleteSession(mmSessions.get(mPosition).getMMSUUid());
                                mmSessions.remove(holder.getAdapterPosition() - 1);
                                if(mmSessions.size() == 1){
                                    notifyDataSetChanged();
                                }else{
                                    notifyItemRemoved(holder.getAdapterPosition());
                                    notifyItemRangeChanged(holder.getAdapterPosition(),mmSessions.size()-holder.getAdapterPosition());
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }else{
                            holder.vTdelete.setText(mContext.getResources().getString(R.string.chat_cp_delete_ok));
                        }
                    }
                });
                holder.vLContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onItemClick(v, mPosition);
                        }
                    }
                });
                switch (mmSessions.get(mPosition).getMMSChatType()){
                    //单聊
                    case Chat:
                        ApplicationUtils.GetVipStatusUserNameResource(mContext,mmSessions.get(mPosition).getMMSUserLeavl(),
                                mmSessions.get(mPosition).getMMSUserName(),mmSessions.get(mPosition).getMMSUserNote(),holder.vMessageNameTv);
                        //VIP级别
                        ApplicationUtils.GetVipStatusResource(mmSessions.get(mPosition).getMMSUserLeavl(), holder.vVIPStatusIv);
                        //vip名字显示橙色
                        if(mmSessions.get(mPosition).getMMSUserLeavl()>0){
                            if(!mmSessions.get(mPosition).getMMSChatType().toString().equals(MMessage.ChatType.Chat.toString())){
                                holder.vMessageNameTv.setTextColor(Color.parseColor("#FE6A07"));
                            }
                        }
                        //加载时间
                        holder.vMessageTimeTv.setText(CalendarUtil.getHour(TimeUtils.GetTimerubbing(mmSessions.get(mPosition).getMMSEndTime())));
                        if (mmSessions.get(mPosition).getMMSUserAvatar() != null && !"".equals(mmSessions.get(mPosition).getMMSUserLeavl())) {
                            //头像
//                            ImageDisplayUtils.display(mContext, mConverdata.get(position - 1).getUseravatar(), holder.vMessageAvatarIv);
                            Glide.with(mContext).load(mmSessions.get(mPosition).getMMSUserAvatar()).into(holder.vMessageAvatarIv);
                        }
                        //消息数量
//                      //获取用户是否被屏蔽
//                        int mShielding = SharedPreferencedUtils.getInteger(mContext,PreferenceValues.GetLoginUid(mContext)+"|"+mConverdata.get(position - 1).getUid()+Constant.SHIELDING_CHAT,0);
//                        if(mShielding == 0){
                            holder.vGroupShieldMarkIv.setVisibility(View.GONE);
                            if (mmSessions.get(mPosition).getMMSUserUnRead() == 0) {
                                holder.vMessageCountTv.setVisibility(View.GONE);
                            } else {
                                holder.vMessageCountTv.setVisibility(View.VISIBLE);
                                if(mmSessions.get(mPosition).getMMSUserUnRead() > 99){
                                    holder.vMessageCountTv.setText("99+");
                                }else{
                                    holder.vMessageCountTv.setText(mmSessions.get(mPosition).getMMSUserUnRead()+"");
                                }

                            }
//                        }else if(mShielding == 1){
//                            holder.vGroupShieldMarkIv.setVisibility(View.VISIBLE);
//                            holder.vMessageCountTv.setVisibility(View.GONE);
//                        }
                        //设置商户和个人认证,增加非空判断
                        if(!TextUtils.isEmpty(mmSessions.get(mPosition).getMMSBusinessAuthStatus())&& !TextUtils.isEmpty(mmSessions.get(mPosition).getMMSAuthStatus())){
                            try {
                                ApplicationUtils.GetAuthstatusResource(Integer.parseInt(mmSessions.get(mPosition).getMMSAuthStatus()),Integer.parseInt(mmSessions.get(mPosition).getMMSBusinessAuthStatus()),holder.vMessagestatus);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }else if(TextUtils.isEmpty(mmSessions.get(mPosition).getMMSAuthStatus()) && !TextUtils.isEmpty(mmSessions.get(mPosition).getMMSAuthStatus())){
                            ApplicationUtils.GetAuthstatusResource(0,Integer.parseInt(mmSessions.get(mPosition).getMMSBusinessAuthStatus()),holder.vMessagestatus);
                        }else if(!TextUtils.isEmpty(mmSessions.get(mPosition).getMMSAuthStatus()) && TextUtils.isEmpty(mmSessions.get(mPosition).getMMSAuthStatus())){
                            ApplicationUtils.GetAuthstatusResource(Integer.parseInt(mmSessions.get(mPosition).getMMSAuthStatus()),0,holder.vMessagestatus);
                        }
                        //设置红点颜色
                        holder.vMessageCountTv.setBackgroundResource(R.drawable.message_oval_red);
                        //头像点击名片跳转
                        holder.vMessageAvatarIv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        //获取草稿
                        String draft = SharedPreferencedUtils.getString(mContext, mmSessions.get(mPosition).getMMSUUid());
                        if (!TextUtils.isEmpty(draft)) {
                            //显示草稿图标
                            holder.vImageDraft.setVisibility(View.VISIBLE);
                            holder.vMessageContentTv.setText(SpanStringUtils.getEmotionContentText(EmotionUtils.EMOTION_TOTAL, mContext, holder.vMessageContentTv, draft));
                        }else{
                            //隐藏草稿图标
                            holder.vImageDraft.setVisibility(View.GONE);
                            //获取当前用户是否被屏蔽
                            int mShielding = 0;
                            try {
                                mShielding = SharedPreferencedUtils.getInteger(mContext,mmSessions.get(mPosition).getMMSUUid()+ Constant.SHIELDING_CHAT,0);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            switch (mmSessions.get(mPosition).getMMSType()){
                                //文本类型
                                case TXT:
                                    if(mShielding == 0){//用户是否被屏蔽  0没有 1被屏蔽
                                        holder.vMessageContentTv.setText(SpanStringUtils.getEmotionContentText(EmotionUtils.EMOTION_TOTAL, mContext, holder.vMessageContentTv, mmSessions.get(mPosition).getMMSUserContext()));
                                    }else if(mShielding == 1){//屏蔽状态 显示用户的消息数量 [xx条]
                                        if(mmSessions.get(mPosition).getMMSUserUnRead() != 0){
                                            holder.vMessageContentTv.setText(SpanStringUtils.getEmotionContentText(
                                                    EmotionUtils.EMOTION_TOTAL, mContext,
                                                    holder.vMessageContentTv,
                                                    "["+mmSessions.get(mPosition).getMMSUserUnRead()+mContext.getResources().getString(R.string.chat_tiao_kuang)+mmSessions.get(mPosition).getMMSUserContext()));
                                        }else{
                                            holder.vMessageContentTv.setText(SpanStringUtils.getEmotionContentText(EmotionUtils.EMOTION_TOTAL, mContext, holder.vMessageContentTv, mmSessions.get(mPosition).getMMSUserContext()));
                                        }
                                    }
                                    break;
                                //图片
                                case IMAGE:

                                    break;
                                //语音
                                case VOICE:

                                    break;
                                //视屏
                                case VIDEO:

                                    break;
                                //地址
                                case LOCATION:

                                    break;
                                //扩展类型
                                case TEXT_TYPE:
                                    switch (mmSessions.get(mPosition).getMsgTypeString()){
                                        case Constant.EXTENSION_FIELD_10:
                                            break;
                                        case Constant.EXTENSION_FIELD_11:
                                            break;
                                        case Constant.EXTENSION_FIELD_12:
                                            break;
                                        case Constant.EXTENSION_FIELD_13:
                                            break;
                                        case Constant.EXTENSION_FIELD_14:
                                            break;
                                        case Constant.EXTENSION_FIELD_15:
                                            break;
                                        case Constant.EXTENSION_FIELD_16:
                                            break;
                                        case Constant.EXTENSION_FIELD_17:
                                            break;
                                        case Constant.EXTENSION_FIELD_18:
                                            break;
                                    }
                                    break;
                            }
                        }

                    //群聊
                    case ChatRoom:
                        switch (mmSessions.get(mPosition).getMMSType()){
                            //文本类型
                            case TXT:
                                break;
                            //图片
                            case IMAGE:

                                break;
                            //语音
                            case VOICE:

                                break;
                            //视屏
                            case VIDEO:

                                break;
                            //地址
                            case LOCATION:

                                break;
                            //扩展类型
                            case TEXT_TYPE:
                                switch (mmSessions.get(mPosition).getMsgTypeString()){
                                    case Constant.EXTENSION_FIELD_10:
                                        break;
                                    case Constant.EXTENSION_FIELD_11:
                                        break;
                                    case Constant.EXTENSION_FIELD_12:
                                        break;
                                    case Constant.EXTENSION_FIELD_13:
                                        break;
                                    case Constant.EXTENSION_FIELD_14:
                                        break;
                                    case Constant.EXTENSION_FIELD_15:
                                        break;
                                    case Constant.EXTENSION_FIELD_16:
                                        break;
                                    case Constant.EXTENSION_FIELD_17:
                                        break;
                                    case Constant.EXTENSION_FIELD_18:
                                        break;
                                }
                                break;
                        }

                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mmSessions.size() + 1;//+1展示小秘书和脉友圈
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /** begin 头部View    **/

        /**
         * 小秘书
         */
        @Nullable
        @BindView(R.id.rl_secretary)
        RelativeLayout vSecretaryRl;
        /**
         * 小秘书头像
         */
        @Nullable
        @BindView(R.id.iv_secretary)
        ShapedImageView vSecretartIv;
        /**
         * 小秘书消息数
         */
        @Nullable
        @BindView(R.id.tv_secretary_message_count)
        TextView vSecretartMessageCountTv;
        /**
         * 脉友圈
         */
        @Nullable
        @BindView(R.id.rl_frend_circle)
        RelativeLayout vFrendCircleRl;
        /**
         * 脉友圈头像
         */
        @Nullable
        @BindView(R.id.iv_frend_circle)
        ShapedImageView vFrendCircleIv;
        /**
         * 脉友圈消息数
         */
        @Nullable
        @BindView(R.id.tv_frend_circle_message_count)
        TextView vFrendCircleMessageCountTv;

        /** end 头部View  **/

        /**
         * 消息头像
         */
        @Nullable
        @BindView(R.id.iv_message_avatar)
        ShapedImageView vMessageAvatarIv;
        /**
         * 实名认证状态
         */
        @Nullable
        @BindView(R.id.iv_authentication)
        ImageView vMessagestatus;
        /**
         * 对方名称或群名称
         */
        @Nullable
        @BindView(R.id.tv_message_name)
        TextView vMessageNameTv;
        /**
         * Vip状态
         */
        @Nullable
        @BindView(R.id.iv_vip_status)
        ImageView vVIPStatusIv;
        /**
         * 草稿图标
         */
        @Nullable
        @BindView(R.id.tv_message_draft)
        ImageView vImageDraft;
        /**
         * 消息内容
         */
        @Nullable
        @BindView(R.id.tv_message_content)
        TextView vMessageContentTv;
        /**
         * 有人@我
         */
        @Nullable
        @BindView(R.id.tv_message_marking)
        TextView vMessageMarking;
        /**
         * 消息时间
         */
        @Nullable
        @BindView(R.id.tv_message_time)
        TextView vMessageTimeTv;
        /**
         * 消息数量
         */
        @Nullable
        @BindView(R.id.tv_message_count)
        TextView vMessageCountTv;
        /**
         * 群消息屏蔽表示
         */
        @Nullable
        @BindView(R.id.iv_group_shield_mark)
        ImageView vGroupShieldMarkIv;
        /**
         * 系统消息模块
         */
        @Nullable
        @BindView(R.id.system_message_convertion)
        TextView vSystemMessage;
        /**
         * 侧滑删除按钮
         */
        @Nullable
        @BindView(R.id.chat_del)
        LinearLayout vItemDelete;
        /**
         * 主布局区域
         */
        @Nullable
        @BindView(R.id.chat_message_content)
        LinearLayout vLContent;

        @Nullable
        @BindView(R.id.chat_message_delete_tv)
        TextView vTdelete;

        private ChatItemClickListener mListener;

        public ViewHolder(View itemView, ChatItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mListener = listener;
            itemView.setOnClickListener(this);
        }

        @OnClick({R.id.rl_frend_circle, R.id.rl_secretary,R.id.rl_frend_system})
        @Optional
        @Override
        public void onClick(View v) {
        }
    }
}
