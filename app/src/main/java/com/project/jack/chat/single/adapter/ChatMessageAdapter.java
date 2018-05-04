package com.project.jack.chat.single.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMLocationMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVideoMessageBody;
import com.project.jack.R;
import com.project.jack.chat.eventbus.ChatReceivedEventBus;
import com.project.jack.chat.util.ApplicationUtils;
import com.project.jack.chat.util.ChatItemClickListener;
import com.project.jack.chat.util.Constant;
import com.project.jack.chat.util.EmotionUtils;
import com.project.jack.chat.util.SpanStringUtils;
import com.project.jack.chat.util.TimeUtils;
import com.project.jack.chat.weight.MgeToast;
import com.project.jack.chat.weight.ShapedImageView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jack.project.com.imdatautil.IMSendUtil;
import jack.project.com.imdatautil.model.MMessage;
import jack.project.com.imdatautil.model.body.MMessageImageBody;
import jack.project.com.imdatautil.model.body.MMessageTxTBody;

/**
 * Created by www.lijin@foxmail.com on 2017/6/7.
 * <br/>
 * 点对点聊天Adapter
 */

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {

    Context mContext;
    List<MMessage> mMMessage;

    /**
     * 接收方文字
     */
    private static final int RECEIVE_TXT_MESSAGE_VIEW = 0x01;
    /**
     * 接收方图片
     */
    private static final int RECEIVE_IMAGE_MESSAGE_VIEW = 0x02;
    /**
     * 接收方语音
     */
    private static final int RECEIVE_VOICE_MESSAGE_VIEW = 0x03;
    /**
     * 接收方视频消息
     */
    private static final int RECEIVE_VOIDE_MESSAGE_VIEW = 0x04;
    /**
     * 接收方位置消息
     */
    private static final int RECEIVE_ADDRESS_MESSAGE_VIEW = 0x05;
    /**
     * 发送方文字
     */
    private static final int SEND_TXT_MESSAGE_VIEW = 0x10;
    /**
     * 发送方图片
     */
    private static final int SEND_IMAGE_MESSAGE_VIEW = 0x11;
    /**
     * 发送方语音消息
     */
    private static final int SEND_VOICE_MESSAGE_VIEW = 0x12;
    /**
     * 发送方视频消息
     */
    private static final int SEND_VOIDE_MESSAGE_VIEW = 0x13;
    /**
     * 发送方位置消息
     */
    private static final int SEND_ADDRESS_MESSAGE_VIEW = 0x15;


    /**
     * 文本扩展字段
     * 1 实时视频 （已取消，未成功通话，呼叫方主动取消）
     * 2 实时视频 （拒绝通话，未成功通话，对方挂断，呼叫方显示：对方已拒绝，对方显示：未接听）
     * 3 实时视频 （视频时长，成功通话，显示通话时间）
     * 4 实时视频 （未接听，呼叫方呼叫超时自动挂断，双方都显示未接听）
     * 5 实时语音 （已取消，未成功通话，呼叫方主动取消）
     * 6 实时语音 （拒绝通话，未成功通话，对方挂断，呼叫方显示：对方已拒绝，对方显示：未接听）
     * 7 实时语音 （语音时长，成功通话，显示通话时间）
     * 8 实时语音 （未接听，呼叫方呼叫超时自动挂断，双方都显示未接听）
     * 10 名片推荐（好友推荐）文本内容格式为竖线分割的被推荐人相关信息字段： “uid|username|avatar|gender|vipLevel|isSingle|birthPeriod|constellation|occupation|position”
     * uid:用户id  username:用户昵称 avatar：用户头像 gender：用户性别  vipLevel：用户vip等级  isSingle：是否单身  birthPeriod：年龄段  constellation：星座  occupation：行业  position：职业
     * Ps:如果没有的资料就传空字符串“”，拼接的字符串要按上述的顺序排列
     * 11 脉友代付  文本内容竖线分割 “Orderid|text|orderPrice” orderid支付订单号     text代付说明文字  orderprice支付金额
     * 12  邀请评价   文本内容竖线分割 “uid” uid:用户id
     * 13  分享动态  文本内容格式为竖线分割的被推荐人的果动相关信息字段： “msgId| title|content|msgImage”(客户端发送时必添/服务器发送时不填写) msgId:文章id   title:文章标题   content：文章内容 msgImage：动态图片（第一张图）
     * 14  脉友红包   文本内容竖线分割 “redEnvelopeId|title|content”
     * 15 分享店铺    文本内容竖线分割
     * “storeId|storename|storeImage|profile|uid”
     * storeId:店铺id storename:店铺名称  storeImage：店铺图片    profile：店铺简介   uid：用户id
     * 16 分享商品    文本内容竖线分割
     * “goodsId|storeId|goodsName|goodsImage|specContent|goodsPrice|uid”
     * goodsId：商品id   storeId:店铺id  goodsName:商品名称  goodsImage：商品图片  specContent：商品规格  goodsPrice:商品价格   uid：用户id
     * 17 商品咨询    文本内容竖线分割
     * 18 群组分享    文本内容竖线分割 GroupID|GroupName|GroupType|GroupAvatar
     * “goodsId|goodsName|goodsImage|specContent|price|backPV|backRate|onSale”
     * goodsId：商品id   goodsName:商品名称  goodsImage：商品图片  specContent：商品规格 price:商品价格  backPV:让利PV  backRate：增值PV  onSale：商品是否在销售（1 在销售  0 已下架）
     * 30 大表情
     * 32 脉友代付提醒
     */

    private static final int RECEIVE_TXT_TEXTTYPE_01_VIEW = 0x21;
    private static final int SEND_TXT_TEXTTYPE_01_VIEW = 0x31;
    private static final int RECEIVE_TXT_TEXTTYPE_02_VIEW = 0x22;
    private static final int SEND_TXT_TEXTTYPE_02_VIEW = 0x32;
    private static final int RECEIVE_TXT_TEXTTYPE_03_VIEW = 0x23;
    private static final int SEND_TXT_TEXTTYPE_03_VIEW = 0x33;
    private static final int RECEIVE_TXT_TEXTTYPE_04_VIEW = 0x24;
    private static final int SEND_TXT_TEXTTYPE_04_VIEW = 0x34;
    private static final int RECEIVE_TXT_TEXTTYPE_05_VIEW = 0x25;
    private static final int SEND_TXT_TEXTTYPE_05_VIEW = 0x35;
    private static final int RECEIVE_TXT_TEXTTYPE_06_VIEW = 0x26;
    private static final int SEND_TXT_TEXTTYPE_06_VIEW = 0x36;
    private static final int RECEIVE_TXT_TEXTTYPE_07_VIEW = 0x27;
    private static final int SEND_TXT_TEXTTYPE_07_VIEW = 0x37;
    private static final int RECEIVE_TXT_TEXTTYPE_08_VIEW = 0x28;
    private static final int SEND_TXT_TEXTTYPE_08_VIEW = 0x38;
    private static final int RECEIVE_TXT_TEXTTYPE_10_VIEW = 0x29;
    private static final int SEND_TXT_TEXTTYPE_10_VIEW = 0x39;
    private static final int RECEIVE_TXT_TEXTTYPE_11_VIEW = 0x41;
    private static final int SEND_TXT_TEXTTYPE_11_VIEW = 0x51;
    private static final int RECEIVE_TXT_TEXTTYPE_12_VIEW = 0x42;
    private static final int SEND_TXT_TEXTTYPE_12_VIEW = 0x52;
    private static final int RECEIVE_TXT_TEXTTYPE_13_VIEW = 0x43;
    private static final int SEND_TXT_TEXTTYPE_13_VIEW = 0x53;
    private static final int RECEIVE_TXT_TEXTTYPE_14_VIEW = 0x44;
    private static final int SEND_TXT_TEXTTYPE_14_VIEW = 0x54;
    private static final int RECEIVE_TXT_TEXTTYPE_15_VIEW = 0x45;
    private static final int SEND_TXT_TEXTTYPE_15_VIEW = 0x55;
    private static final int RECEIVE_TXT_TEXTTYPE_16_VIEW = 0x46;
    private static final int SEND_TXT_TEXTTYPE_16_VIEW = 0x56;
    private static final int RECEIVE_TXT_TEXTTYPE_17_VIEW = 0x47;
    private static final int SEND_TXT_TEXTTYPE_17_VIEW = 0x57;
    private static final int RECEIVE_TXT_TEXTTYPE_18_VIEW = 0x48;
    private static final int SEND_TXT_TEXTTYPE_18_VIEW = 0x58;
    private static final int SEND_TXT_TEXTTYPE_30_VIEW = 0x70;
    private static final int RECEIVE_TXT_TEXTTYPE_30_VIEW = 0x60;
    private static final int RECEIVE_TXT_TEXTTYPE_31_VIEW = 0x61;
    private static final int SEND_TXT_TEXTTYPE_31_VIEW = 0x71;
    private static final int RECEIVE_TXT_TEXTTYPE_32_VIEW = 0x62;
    private static final int SEND_TXT_TEXTTYPE_32_VIEW = 0x72;


    private ChatItemClickListener mListener;

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(ChatItemClickListener listener) {
        this.mListener = listener;
    }


    public ChatMessageAdapter(Context mContext, List<MMessage> mMMessage) {
        this.mContext = mContext;
        this.mMMessage = mMMessage;
    }


    @Override
    public int getItemViewType(int position) {
        MMessage message = mMMessage.get(position);
        String type = message.getMsgStringAttribute("text_type", "-1");
        if (message.getMsgType() == MMessage.Type.TXT) {
            if (type.equals(Constant.EXTENSION_FIELD_01)) {
                // 实时视频 （已取消，未成功通话，呼叫方主动取消）
                return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_TXT_TEXTTYPE_01_VIEW : SEND_TXT_TEXTTYPE_01_VIEW;
            } else if (type.equals(Constant.EXTENSION_FIELD_02)) {
                // 实时视频 （拒绝通话，未成功通话，对方挂断，呼叫方显示：对方已拒绝，对方显示：未接听）
                return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_TXT_TEXTTYPE_02_VIEW : SEND_TXT_TEXTTYPE_02_VIEW;
            } else if (type.equals(Constant.EXTENSION_FIELD_03)) {
                // 实时视频 （视频时长，成功通话，显示通话时间）
                return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_TXT_TEXTTYPE_03_VIEW : SEND_TXT_TEXTTYPE_03_VIEW;
            } else if (type.equals(Constant.EXTENSION_FIELD_04)) {
                // 实时视频 （未接听，呼叫方呼叫超时自动挂断，双方都显示未接听）
                return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_TXT_TEXTTYPE_04_VIEW : SEND_TXT_TEXTTYPE_04_VIEW;
            } else if (type.equals(Constant.EXTENSION_FIELD_05)) {
                // 实时语音 （已取消，未成功通话，呼叫方主动取消）
                return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_TXT_TEXTTYPE_05_VIEW : SEND_TXT_TEXTTYPE_05_VIEW;
            } else if (type.equals(Constant.EXTENSION_FIELD_06)) {
                // 实时语音 （拒绝通话，未成功通话，对方挂断，呼叫方显示：对方已拒绝，对方显示：未接听）
                return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_TXT_TEXTTYPE_06_VIEW : SEND_TXT_TEXTTYPE_06_VIEW;
            } else if (type.equals(Constant.EXTENSION_FIELD_07)) {
                // 实时语音 （语音时长，成功通话，显示通话时间）
                return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_TXT_TEXTTYPE_07_VIEW : SEND_TXT_TEXTTYPE_07_VIEW;
            } else if (type.equals(Constant.EXTENSION_FIELD_08)) {
                // 实时语音 （未接听，呼叫方呼叫超时自动挂断，双方都显示未接听）
                return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_TXT_TEXTTYPE_08_VIEW : SEND_TXT_TEXTTYPE_08_VIEW;
            } else if (type.equals(Constant.EXTENSION_FIELD_10)) {//推荐脉友(名片分享)
                return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_TXT_TEXTTYPE_10_VIEW : SEND_TXT_TEXTTYPE_10_VIEW;
            } else if (type.equals(Constant.EXTENSION_FIELD_11)) {//脉友代付
                return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_TXT_TEXTTYPE_11_VIEW : SEND_TXT_TEXTTYPE_11_VIEW;
            } else if (type.equals(Constant.EXTENSION_FIELD_12)) {//邀请评价
                return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_TXT_TEXTTYPE_12_VIEW : SEND_TXT_TEXTTYPE_12_VIEW;
            } else if (type.equals(Constant.EXTENSION_FIELD_13)) {//分享动态
                return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_TXT_TEXTTYPE_13_VIEW : SEND_TXT_TEXTTYPE_13_VIEW;
            } else if (type.equals(Constant.EXTENSION_FIELD_14)) {//红包
                return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_TXT_TEXTTYPE_14_VIEW : SEND_TXT_TEXTTYPE_14_VIEW;
            } else if (type.equals(Constant.EXTENSION_FIELD_15)) {//分享店铺
                return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_TXT_TEXTTYPE_15_VIEW : SEND_TXT_TEXTTYPE_15_VIEW;
            } else if (type.equals(Constant.EXTENSION_FIELD_16)) {//分享商品
                return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_TXT_TEXTTYPE_16_VIEW : SEND_TXT_TEXTTYPE_16_VIEW;
            } else if (type.equals(Constant.EXTENSION_FIELD_17)) {//商品咨询
                return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_TXT_TEXTTYPE_17_VIEW : SEND_TXT_TEXTTYPE_17_VIEW;
            } else if (type.equals(Constant.EXTENSION_FIELD_18)) {//群组分享
                return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_TXT_TEXTTYPE_18_VIEW : SEND_TXT_TEXTTYPE_18_VIEW;
            }else if (type.equals(Constant.EXTENSION_FIELD_30)) {//大表情
                return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_TXT_TEXTTYPE_30_VIEW : SEND_TXT_TEXTTYPE_30_VIEW;
            }else if (type.equals(Constant.EXTENSION_FIELD_31)) {//代付提醒
                return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_TXT_TEXTTYPE_31_VIEW : SEND_TXT_TEXTTYPE_31_VIEW;
            } else if (type.equals(Constant.EXTENSION_FIELD_32)) {//代付提醒
                return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_TXT_TEXTTYPE_32_VIEW : SEND_TXT_TEXTTYPE_32_VIEW;
            } else {
                // 文字消息
                return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_TXT_MESSAGE_VIEW : SEND_TXT_MESSAGE_VIEW;
            }
        } else if (message.getMsgType() == MMessage.Type.IMAGE) {
            // 图片消息
            return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_IMAGE_MESSAGE_VIEW : SEND_IMAGE_MESSAGE_VIEW;
        } else if (message.getMsgType() == MMessage.Type.VOICE) {
            // 语音消息
            return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_VOICE_MESSAGE_VIEW : SEND_VOICE_MESSAGE_VIEW;
        } else if (message.getMsgType() == MMessage.Type.VIDEO) {
            //视频消息
            return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_VOIDE_MESSAGE_VIEW : SEND_VOIDE_MESSAGE_VIEW;
        } else if (message.getMsgType() == MMessage.Type.LOCATION) {
            //位置信息
            return message.getMsgDirect() == MMessage.Direct.RECEIVE ? RECEIVE_ADDRESS_MESSAGE_VIEW : SEND_ADDRESS_MESSAGE_VIEW;
        } else {
            return -1;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int viewRes = -1;
        switch (viewType) {
            case RECEIVE_TXT_MESSAGE_VIEW: {
                // 文字消息
                viewRes = R.layout.row_receive_chat_txt_message;
            }
            break;
            case SEND_TXT_MESSAGE_VIEW: {
                // 文字消息
                viewRes = R.layout.row_send_chat_txt_message;
            }
            break;
            case RECEIVE_IMAGE_MESSAGE_VIEW: {
                // 图片消息
                viewRes = R.layout.row_receive_chat_image_message;
            }
            break;
            case SEND_IMAGE_MESSAGE_VIEW: {
                // 图片消息
                viewRes = R.layout.row_send_chat_image_message;
            }
            break;
            case RECEIVE_VOICE_MESSAGE_VIEW: {
                //语音消息接收方
                viewRes = R.layout.row_receive_chat_voice_message;
            }
            break;
            case SEND_VOICE_MESSAGE_VIEW: {
                //语音消息发送方
                viewRes = R.layout.row_send_chat_voice_message;
            }
            break;
            case RECEIVE_VOIDE_MESSAGE_VIEW: {
                //视频消息接收方
                viewRes = R.layout.row_receive_chat_video_message;
            }
            break;
            case SEND_VOIDE_MESSAGE_VIEW: {
                //视频消息发送方
                viewRes = R.layout.row_send_chat_video_message;
            }
            break;
            case RECEIVE_ADDRESS_MESSAGE_VIEW: {
                //位置消息接收方
                viewRes = R.layout.row_receive_chat_address_message;
            }
            break;
            case SEND_ADDRESS_MESSAGE_VIEW: {
                //位置消息发送方
                viewRes = R.layout.row_send_chat_address_message;
            }
            break;
            //=========================文本扩展类的============详细请见顶部注释==================
            case RECEIVE_TXT_TEXTTYPE_01_VIEW: {
                viewRes = R.layout.row_receive_chat_txt_typevoide_message;
            }
            break;
            case RECEIVE_TXT_TEXTTYPE_02_VIEW: {
                viewRes = R.layout.row_receive_chat_txt_typevoide_message;
            }
            break;
            case RECEIVE_TXT_TEXTTYPE_03_VIEW: {
                viewRes = R.layout.row_receive_chat_txt_typevoide_message;
            }
            break;
            case RECEIVE_TXT_TEXTTYPE_04_VIEW: {
                viewRes = R.layout.row_receive_chat_txt_typevoide_message;
            }
            break;
            case RECEIVE_TXT_TEXTTYPE_05_VIEW: {
                viewRes = R.layout.row_receive_chat_txt_typevoice_message;
            }
            break;
            case RECEIVE_TXT_TEXTTYPE_06_VIEW: {
                viewRes = R.layout.row_receive_chat_txt_typevoice_message;
            }
            break;
            case RECEIVE_TXT_TEXTTYPE_07_VIEW: {
                viewRes = R.layout.row_receive_chat_txt_typevoice_message;
            }
            break;
            case RECEIVE_TXT_TEXTTYPE_08_VIEW: {
                viewRes = R.layout.row_receive_chat_txt_typevoice_message;
            }
            break;
            case RECEIVE_TXT_TEXTTYPE_10_VIEW: {
                viewRes = R.layout.row_receive_chat_txt_typefenx_message;
            }
            break;
            case RECEIVE_TXT_TEXTTYPE_11_VIEW: {
                viewRes = R.layout.row_chat_reciver_payfor_message;
            }
            break;
            case RECEIVE_TXT_TEXTTYPE_12_VIEW: {
                viewRes = R.layout.row_receive_chat_txt_typeyaoq_message;
            }
            break;
            case RECEIVE_TXT_TEXTTYPE_13_VIEW: {
                viewRes = R.layout.row_receive_chat_txt_typedongt_message;
            }
            break;
            case RECEIVE_TXT_TEXTTYPE_14_VIEW: {
                viewRes = R.layout.row_chat_reciver_redpackge_message;
            }
            break;
            case RECEIVE_TXT_TEXTTYPE_15_VIEW: {
                viewRes = R.layout.row_receive_chat_txt_type15_message;
            }
            break;
            case RECEIVE_TXT_TEXTTYPE_16_VIEW: {
                viewRes = R.layout.row_receive_chat_txt_type16_message;
            }
            break;
            case RECEIVE_TXT_TEXTTYPE_17_VIEW: {
                viewRes = R.layout.row_chat_reciver_type17_message;
            }
            break;
            case RECEIVE_TXT_TEXTTYPE_18_VIEW: {
                viewRes = R.layout.row_receive_chat_txt_type18_message;
            }
            break;
            case RECEIVE_TXT_TEXTTYPE_30_VIEW: {//大表情
                viewRes = R.layout.row_receive_chat_txt_30_message;
            }
            break;
            case RECEIVE_TXT_TEXTTYPE_31_VIEW: {//红包
                viewRes = R.layout.row_chat_reciver_redpackge_system;
            }
            break;
            case RECEIVE_TXT_TEXTTYPE_32_VIEW: {//代付
                viewRes = R.layout.row_chat_reciver_payfor_system;
            }
            break;
            //============================SEND====================================
            case SEND_TXT_TEXTTYPE_01_VIEW: {
                viewRes = R.layout.row_send_chat_txt_typevoide_message;
            }
            break;
            case SEND_TXT_TEXTTYPE_02_VIEW: {
                viewRes = R.layout.row_send_chat_txt_typevoide_message;
            }
            break;
            case SEND_TXT_TEXTTYPE_03_VIEW: {
                viewRes = R.layout.row_send_chat_txt_typevoide_message;
            }
            break;
            case SEND_TXT_TEXTTYPE_04_VIEW: {
                viewRes = R.layout.row_send_chat_txt_typevoide_message;
            }
            break;
            case SEND_TXT_TEXTTYPE_05_VIEW: {
                viewRes = R.layout.row_send_chat_txt_typevoice_message;
            }
            break;
            case SEND_TXT_TEXTTYPE_06_VIEW: {
                viewRes = R.layout.row_send_chat_txt_typevoice_message;
            }
            break;
            case SEND_TXT_TEXTTYPE_07_VIEW: {
                viewRes = R.layout.row_send_chat_txt_typevoice_message;
            }
            break;
            case SEND_TXT_TEXTTYPE_08_VIEW: {
                viewRes = R.layout.row_send_chat_txt_typevoice_message;
            }
            break;
            case SEND_TXT_TEXTTYPE_10_VIEW: {
                viewRes = R.layout.row_send_chat_txt_typefenx_message;
            }
            break;
            case SEND_TXT_TEXTTYPE_11_VIEW: {
                viewRes = R.layout.row_send_payfor_message;
            }
            break;
            case SEND_TXT_TEXTTYPE_12_VIEW: {
                viewRes = R.layout.row_send_chat_txt_typeyaoq_message;
            }
            break;
            case SEND_TXT_TEXTTYPE_13_VIEW: {
                viewRes = R.layout.row_send_chat_txt_typedongt_message;
            }
            break;
            case SEND_TXT_TEXTTYPE_14_VIEW: {
                viewRes = R.layout.row_send_redpackge_message;
            }
            break;
            case SEND_TXT_TEXTTYPE_15_VIEW: {
                viewRes = R.layout.row_send_chat_txt_type15_message;
            }
            break;
            case SEND_TXT_TEXTTYPE_16_VIEW: {
                viewRes = R.layout.row_send_chat_txt_type16_message;
            }
            break;
            case SEND_TXT_TEXTTYPE_17_VIEW: {
                viewRes = R.layout.row_chat_reciver_type17_message;
            }
            break;
            case SEND_TXT_TEXTTYPE_18_VIEW: {
                viewRes = R.layout.row_send_chat_txt_type18_message;
            }
            break;
            case SEND_TXT_TEXTTYPE_30_VIEW: {
                viewRes = R.layout.row_send_chat_txt_30_message;
            }
            break;
            case SEND_TXT_TEXTTYPE_31_VIEW: {
                viewRes = R.layout.row_send_redpackge_system;
            }
            break;
            case SEND_TXT_TEXTTYPE_32_VIEW: {
                viewRes = R.layout.row_send_payfor_system;
            }
            break;

        }
        return new ViewHolder(LayoutInflater.from(mContext).inflate(viewRes, parent, false), mListener);
    }

    JSONObject json;
    String mAvatar;
    int mVipLeaval;

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MMessage message = mMMessage.get(position);
        long time = message.getMsgTime();
        boolean showTime = true;
        if (position > 0) {
            showTime = time - mMMessage.get(position - 1).getMsgTime() > 300000;
        }

        if (showTime) {
            holder.vMessageTimeTv.setVisibility(View.VISIBLE);
            holder.vMessageTimeTv.setText(TimeUtils.distanceBeforeNow(new Date(time), mContext));
        } else {
            holder.vMessageTimeTv.setVisibility(View.GONE);
        }

        switch (message.getMsgDirect()) {
            case RECEIVE: {
                // ===============================================左(收到的消息)===================================
                mVipLeaval = Integer.parseInt(message.getMsgStringAttribute(Constant.STRING_ATTRIBUTE_GROUP_VIPLEVEL, null));
                if (getItemViewType(position) == RECEIVE_TXT_MESSAGE_VIEW) { // 文字消息
                    ReceiveAvatar(message, holder.vAvatarIv);
                    leftvvip(holder.vMessageBgRl);
                    // 消息内容
                    String mesgescontent = ((MMessageTxTBody) message.getMsgBody()).getContent();
                    if (!TextUtils.isEmpty(mesgescontent)) {
                        holder.vMessageContentTv.setText(SpanStringUtils.getEmotionContentText(EmotionUtils.EMOTION_TOTAL, mContext, holder.vMessageContentTv, mesgescontent));
                    }
                } else if (getItemViewType(position) == RECEIVE_IMAGE_MESSAGE_VIEW) {
                    ReceiveAvatar(message, holder.vAvatarIv);
                    leftvvip(holder.vMessageBgRl);
                    MMessageImageBody mEMImageBody = ((MMessageImageBody) message.getMsgBody());
                    // 图片消息
                    if (!TextUtils.isEmpty(mEMImageBody.getRemoteUrl())) {
                        Glide.with(mContext).load(mEMImageBody.getRemoteUrl()).into(holder.vMessageContentIv);
                    }
                } else if (getItemViewType(position) == RECEIVE_VOICE_MESSAGE_VIEW) {
//                    ReceiveAvatar(message, holder.vAvatarIv);
//                    leftvvip(holder.vMessageBgRl);
//                    //截取本地路径
//                    String reqResult = message.getMsgBody() + "";
//                    String length = null;
//                    try {
//                        String[] getSignInfo = reqResult.split(",");
//                        String getlenth = getSignInfo[3];
//                        String[] getlengths = getlenth.split(":");
//                        //截取语音长度
//                        length = getlengths[1];
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        e.printStackTrace();
//                    }
//                    if (!TextUtils.isEmpty(length)) {
//                        holder.vMessageContentTv.setText("    " + TimeUtils.LongGetMinute(Integer.parseInt(length)) + "”    ");
//                    }
//                    //判断语音是否已听
//                    if (message.isListened()) {
//                        holder.vImgRead.setVisibility(View.INVISIBLE);
//                    }else{
//                        holder.vImgRead.setVisibility(View.VISIBLE);
//                    }
                } else if (getItemViewType(position) == RECEIVE_VOIDE_MESSAGE_VIEW) {
//                    ReceiveAvatar(message, holder.vAvatarIv);
//                    leftvvip(holder.vMessageBgRl);
//                    //视频消息
//                    EMVideoMessageBody mEMVideoBody = (EMVideoMessageBody) message.getBody();
//                    if (mEMVideoBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.SUCCESSED) {
//                        if (!TextUtils.isEmpty(mEMVideoBody.getLocalThumb())) {
//                            Glide.with(mContext).load(mEMVideoBody.getLocalThumb()).into(holder.vMessageContentIv);
//                        }
//                    }

                } else if (getItemViewType(position) == RECEIVE_TXT_TEXTTYPE_01_VIEW) {//已取消，未成功通话，呼叫方主动取消
//                    ReceiveAvatar(message, holder.vAvatarIv);
//                    leftvvip(holder.vMessageBgRl);
//                    // 消息内容
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    if (!TextUtils.isEmpty(mesgescontent)) {
//                        holder.vMessageContentTv.setText(SpanStringUtils.getEmotionContentText(EmotionUtils.EMOTION_TOTAL, mContext, holder.vMessageContentTv, mesgescontent));
//                    }
                } else if (getItemViewType(position) == RECEIVE_TXT_TEXTTYPE_02_VIEW) {//拒绝通话，未成功通话，对方挂断，呼叫方显示：对方已拒绝，对方显示：未接听
//                    ReceiveAvatar(message, holder.vAvatarIv);
//                    leftvvip(holder.vMessageBgRl);
//                    // 消息内容
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    if (!TextUtils.isEmpty(mesgescontent)) {
//                        holder.vMessageContentTv.setText(mContext.getResources().getString(R.string.chat_texttype_Not_answered));
//                    }
                } else if (getItemViewType(position) == RECEIVE_TXT_TEXTTYPE_03_VIEW) {//语音时长，成功通话，显示通话时间
//                    ReceiveAvatar(message, holder.vAvatarIv);
//                    leftvvip(holder.vMessageBgRl);
//                    // 消息内容
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    if (!TextUtils.isEmpty(mesgescontent)) {
//                        holder.vMessageContentTv.setText(SpanStringUtils.getEmotionContentText(EmotionUtils.EMOTION_TOTAL, mContext, holder.vMessageContentTv, mesgescontent));
//                    }
                } else if (getItemViewType(position) == RECEIVE_TXT_TEXTTYPE_04_VIEW) {//未接听，呼叫方呼叫超时自动挂断，双方都显示未接听
//                    ReceiveAvatar(message, holder.vAvatarIv);
//                    leftvvip(holder.vMessageBgRl);
//                    // 消息内容
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    if (!TextUtils.isEmpty(mesgescontent)) {
//                        holder.vMessageContentTv.setText(mContext.getResources().getString(R.string.chat_texttype_Not_answered));
//                    }
                } else if (getItemViewType(position) == RECEIVE_TXT_TEXTTYPE_05_VIEW) {//已取消，未成功通话，呼叫方主动取消
//                    ReceiveAvatar(message, holder.vAvatarIv);
//                    leftvvip(holder.vMessageBgRl);
//                    // 消息内容
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    if (!TextUtils.isEmpty(mesgescontent)) {
//                        holder.vMessageContentTv.setText(SpanStringUtils.getEmotionContentText(EmotionUtils.EMOTION_TOTAL, mContext, holder.vMessageContentTv, mesgescontent));
//                    }
                } else if (getItemViewType(position) == RECEIVE_TXT_TEXTTYPE_06_VIEW) {//拒绝通话，未成功通话，对方挂断，呼叫方显示：对方已拒绝，对方显示：未接听
//                    ReceiveAvatar(message, holder.vAvatarIv);
//                    leftvvip(holder.vMessageBgRl);
//                    // 消息内容
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    if (!TextUtils.isEmpty(mesgescontent)) {
//                        holder.vMessageContentTv.setText(mContext.getResources().getString(R.string.chat_texttype_Not_answered));
//                    }
                } else if (getItemViewType(position) == RECEIVE_TXT_TEXTTYPE_07_VIEW) {//语音时长，成功通话，显示通话时间
//                    ReceiveAvatar(message, holder.vAvatarIv);
//                    leftvvip(holder.vMessageBgRl);
//                    // 消息内容
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    if (!TextUtils.isEmpty(mesgescontent)) {
//                        holder.vMessageContentTv.setText(SpanStringUtils.getEmotionContentText(EmotionUtils.EMOTION_TOTAL, mContext, holder.vMessageContentTv, mesgescontent));
//                    }
                } else if (getItemViewType(position) == RECEIVE_TXT_TEXTTYPE_08_VIEW) {//未接听，呼叫方呼叫超时自动挂断，双方都显示未接听
//                    ReceiveAvatar(message, holder.vAvatarIv);
//                    leftvvip(holder.vMessageBgRl);
//                    // 消息内容
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    if (!TextUtils.isEmpty(mesgescontent)) {
//                        holder.vMessageContentTv.setText(mContext.getResources().getString(R.string.chat_texttype_Not_answered));
//                    }
                } else if (getItemViewType(position) == RECEIVE_TXT_TEXTTYPE_10_VIEW) {//分享名片
//                    ReceiveAvatar(message, holder.vAvatarIv);
//                    //获取当前名片消息携带的扩展消息
//                    String TextType = ((EMTextMessageBody) message.getBody()).getMessage();
//                    Log.d("ChatMessageAdapter", "TextType-->" + TextType);
//                    String[] StringText = TextType.split("\\|", -1);
//                    Log.d("ChatMessageAdapter", "StringText.length:" + StringText.length);
//                    String username = null;
//                    String avatar = null;
//                    String gender = null;
//                    String vipLevel = null;
//                    String isSingle = null;
//                    String birthPeriod = null;
//                    String constellation = null;
//                    String occupation = null;
//                    String dposition = null;
//                    String businessAuthStatus = null;
//                    String authStatus = null;
//                    String company = null;
//                    try {
//                        username = StringText[1];
//                        avatar = StringText[2];
//                        gender = StringText[3];
//                        vipLevel = StringText[4];
//                        isSingle = StringText[5];
//                        birthPeriod = StringText[6];
//                        constellation = StringText[7];
//                        occupation = StringText[8];
//                        dposition = StringText[9];
//                        businessAuthStatus = StringText[10];
//                        authStatus = StringText[11];
//                        company = StringText[12];
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    if (!TextUtils.isEmpty(avatar)) {
//                        Glide.with(mContext).load(avatar).into(holder.vFavatar);
//                    }
//                    if (!TextUtils.isEmpty(authStatus) && !TextUtils.isEmpty(businessAuthStatus)) {
//                        ApplicationUtils.GetAuthstatusResource(Integer.parseInt(authStatus), Integer.parseInt(businessAuthStatus), holder.vFauthentication);
//                    } else {
//                        ApplicationUtils.GetAuthstatusResource(0, 0, holder.vFauthentication);
//                    }
//
//                    if (!TextUtils.isEmpty(authStatus) && !TextUtils.isEmpty(businessAuthStatus)) {
//                        ApplicationUtils.GetAuthstatusResource(Integer.parseInt(authStatus), Integer.parseInt(businessAuthStatus), holder.vFauthentication);
//                    } else {
//                        ApplicationUtils.GetAuthstatusResource(0, 0, holder.vFauthentication);
//                    }
//                    if (!TextUtils.isEmpty(vipLevel)) {
//                        ApplicationUtils.GetVipStatusUserNameResource(mContext, Integer.parseInt(vipLevel), username, "", holder.vFname);
//                    } else {
//                        ApplicationUtils.GetVipStatusUserNameResource(mContext, 0, username, "", holder.vFname);
//                    }
//                    if (!TextUtils.isEmpty(gender)) {
//                        ApplicationUtils.GetgenderStatusResource(Integer.parseInt(gender), holder.vFsex);
//                    } else {
//                        ApplicationUtils.GetgenderStatusResource(0, holder.vFsex);
//                    }
//                    if (!TextUtils.isEmpty(vipLevel)) {
//                        ApplicationUtils.GetVipStatusResource(Integer.parseInt(vipLevel), holder.vFstatus);
//                    } else {
//                        ApplicationUtils.GetVipStatusResource(0, holder.vFstatus);
//                    }
//                    if (!TextUtils.isEmpty(businessAuthStatus) && !TextUtils.isEmpty(isSingle)) {
//                        String info = ApplicationUtils.GetAuthStatusUserInfo(mContext, Integer.parseInt(businessAuthStatus), Integer.parseInt(isSingle), birthPeriod, constellation, dposition, occupation, company);
//                        holder.vFinfo.setText(info);
//                    } else {
//                        String info = ApplicationUtils.GetAuthStatusUserInfo(mContext, 0, 0, birthPeriod, constellation, dposition, occupation, company);
//                        holder.vFinfo.setText(info);
//                    }


                } else if (getItemViewType(position) == RECEIVE_TXT_TEXTTYPE_11_VIEW) {//代付
//                    ReceiveAvatar(message, holder.vAvatarIv);
//                    //获取携带的扩展信息 OrderNo|OrderPrice
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    String[] StringText = mesgescontent.split("\\|");
//                    String mRedStatus = null;
//                    try {
//                        mRedStatus = StringText[1];
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        e.printStackTrace();
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//                    if(!TextUtils.isEmpty(mRedStatus)){
//                        holder.vRedStatus.setText("￥" + mRedStatus);
//                    }else{
//                        holder.vRedStatus.setText("￥ 0.0");
//                    }

                } else if (getItemViewType(position) == RECEIVE_TXT_TEXTTYPE_12_VIEW) {//邀请评价
//                    ReceiveAvatar(message, holder.vAvatarIv);
                } else if (getItemViewType(position) == RECEIVE_ADDRESS_MESSAGE_VIEW) {//位置信息
//                    ReceiveAvatar(message, holder.vAvatarIv);
//                    EMLocationMessageBody mEmLocation = (EMLocationMessageBody) message.getBody();
//                    String address = mEmLocation.getAddress();
//                    String[] StringAddress = address.split("\\|");
//                    String addresstitle = null;
//                    String addressdetail = null;
//                    try {
//                        addresstitle = StringAddress[0];
//                        addressdetail = StringAddress[1];
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        e.printStackTrace();
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//                    if (!TextUtils.isEmpty(addresstitle) && !TextUtils.isEmpty(addressdetail)) {
//                        holder.vAddress.setText(addresstitle);
//                        holder.vAddressDetail.setText(addressdetail);
//                    }
                } else if (getItemViewType(position) == RECEIVE_TXT_TEXTTYPE_13_VIEW) {//分享动态
//                    ReceiveAvatar(message, holder.vAvatarIv);
//                    //获取携带的扩展信息 msgId| title|content|msgImage
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    String[] StringText = mesgescontent.split("\\|");
//                    String title = null;
//                    String content = null;
//                    String msgImage = null;
//                    try {
//                        title = StringText[3];
//                        content = StringText[4];
//                        msgImage = StringText[1];
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        e.printStackTrace();
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//                    if (!TextUtils.isEmpty(title)) {
//                        holder.vXTtitle.setText(title);
//                    }
//                    if (!TextUtils.isEmpty(content)) {
//                        holder.vXTdetail.setText(content);
//                    }
//                    if (!TextUtils.isEmpty(msgImage)) {
//                        Glide.with(mContext).load(msgImage).into(holder.vXavatar);
//                    }
                } else if (getItemViewType(position) == RECEIVE_TXT_TEXTTYPE_14_VIEW) {//红包
//                    ReceiveAvatar(message, holder.vAvatarIv);
//                    //获取携带的扩展信息 红包ID|详细描述
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    String[] StringText = mesgescontent.split("\\|");
//                    String mContent = null;
//                    try {
//                        mContent = StringText[1];
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        e.printStackTrace();
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//                    String status = message.getMsgStringAttribute("redType", null);
//                    if (!TextUtils.isEmpty(mContent)) {
//                        holder.vRedContent.setText(mContent);
//                    }
//                    if (status.equals("0")) {//未领取
//                        holder.vRedStatus.setText(mContext.getResources().getString(R.string.chat_red_pack_lingqu));
//                        holder.vLmengban.setBackgroundColor(Color.parseColor("#00FFFFFF"));
//                    } else if (status.equals("1")) {//已领取
//                        holder.vRedStatus.setText(mContext.getResources().getString(R.string.chat_red_pack_yilingqu));
//                        holder.vLmengban.setBackgroundColor(Color.parseColor("#90FFFFFF"));
//                    } else if (status.equals("2")) {//已过期
//                        holder.vRedStatus.setText(mContext.getResources().getString(R.string.chat_red_pack_yiguoqi));
//                        holder.vLmengban.setBackgroundColor(Color.parseColor("#90FFFFFF"));
//                    }
                } else if (getItemViewType(position) == RECEIVE_TXT_TEXTTYPE_15_VIEW) {//分享店铺
//                    //storeId|storename|storeImage|profile
//                    // storeId:店铺id storename:店铺名称  storeImage：店铺图片    profile：店铺简介   uid：用户id
//                    ReceiveAvatar(message, holder.vAvatarIv);
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    String[] StringText = mesgescontent.split("\\|");
//                    String storename = null;
//                    String storeImage = null;
//                    try {
//                        storename = StringText[1];
//                        storeImage = StringText[2];
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        e.printStackTrace();
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                    if (!TextUtils.isEmpty(storeImage)) {
//                        Glide.with(mContext).load(storeImage).into(holder.vXavatar);
//                    }
//                    if (!TextUtils.isEmpty(storename)) {
//                        holder.vXTtitle.setText(storename);
//                    }

                } else if (getItemViewType(position) == RECEIVE_TXT_TEXTTYPE_16_VIEW) {//分享商品
//                    //goodsId|storeId|goodsName|goodsImage|specContent|goodsPrice
//                    // goodsId：商品id   storeId:店铺id  goodsName:商品名称  goodsImage：商品图片
//                    // specContent：商品规格  goodsPrice:商品价格   uid：用户id
//                    ReceiveAvatar(message, holder.vAvatarIv);
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    String[] StringText = mesgescontent.split("\\|");
//                    String goodsName = null;
//                    String goodsImage = null;
//                    String goodsPrice = null;
//                    try {
//                        goodsName = StringText[2];
//                        goodsImage = StringText[3];
//                        goodsPrice = StringText[5];
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        e.printStackTrace();
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                    if (!TextUtils.isEmpty(goodsName)) {
//                        holder.vXTtitle.setText(goodsName);
//                    }
//                    if (!TextUtils.isEmpty(goodsPrice)) {
//                        holder.vXTdetail.setText("￥" + goodsPrice);
//                    }
//                    if (!TextUtils.isEmpty(goodsImage)) {
//                        Glide.with(mContext).load(goodsImage).into(holder.vXavatar);
//                    }

                } else if (getItemViewType(position) == RECEIVE_TXT_TEXTTYPE_17_VIEW) {//咨询商品
//                    //goodsId|goodsName|goodsImage|specContent|price|backPV|backRate|onSale
//                    //goodsId：商品id   goodsName:商品名称  goodsImage：商品图片  specContent：商品规格
//                    // price:商品价格  backPV:让利PV  backRate：增值PV  onSale：商品是否在销售（1 在销售  0 已下架）
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    String[] StringText = mesgescontent.split("\\|");
//                    String goodsName = null;
//                    String goodsImage = null;
//                    String specContent = null;
//                    String price = null;
//                    String backPV = null;
//                    String backRate = null;
//                    String onSale = null;
//                    try {
//                        goodsName = StringText[1];
//                        goodsImage = StringText[2];
//                        specContent = StringText[3];
//                        price = StringText[4];
//                        backPV = StringText[5];
//                        backRate = StringText[6];
//                        onSale = StringText[7];
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        e.printStackTrace();
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                    if (!TextUtils.isEmpty(goodsName)) {
//                        holder.vTmcdTitle.setText(goodsName);
//                    }
//                    if (!TextUtils.isEmpty(specContent)) {
//                        holder.vTmcdContent.setText(specContent);
//                    }
//                    if (!TextUtils.isEmpty(goodsImage)) {
//                        Glide.with(mContext).load(goodsImage).into(holder.vImcdImg);
//                    }
//                    if (!TextUtils.isEmpty(onSale)) {
//                        if (onSale.equals("1")) {
//                            holder.vImcdImgmb.setVisibility(View.GONE);
//                        } else if (onSale.equals("0")) {
//                            holder.vImcdImgmb.setVisibility(View.VISIBLE);
//                        }
//                    }
//                    if (!TextUtils.isEmpty(backPV) && !TextUtils.isEmpty(backRate)) {
//                        holder.vTmcdPv.setText("PV:"+backPV + "   " +mContext.getResources().getString(R.string.chat_typr_cunzhi)+ backRate);
//                    }
//                    if (!TextUtils.isEmpty(price)) {
//                        holder.vTmcdPrice.setText("￥" + price);
//                    }

                } else if(getItemViewType(position) == RECEIVE_TXT_TEXTTYPE_18_VIEW){//群组分享
//                    //GroupID|GroupName|GroupType|GroupAvatar
//                    ReceiveAvatar(message, holder.vAvatarIv);
//                    String mMessageContent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    String[] StringText = mMessageContent.split("\\|");
//                    String GroupName = null;
//                    String GroupType = null;
//                    String GroupAvatar = null;
//                    try {
//                        GroupName = StringText[1];
//                        GroupType = StringText[2];
//                        GroupAvatar = StringText[3];
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        e.printStackTrace();
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                    if(!TextUtils.isEmpty(GroupName)){
//                        holder.vTShardName.setText(GroupName);
//                    }
//                    if(!TextUtils.isEmpty(GroupType)){
//                        holder.vTShardType.setText(GroupType);
//                    }
//                    if(!TextUtils.isEmpty(GroupAvatar)){
//                        Glide.with(mContext).load(GroupAvatar).into(holder.vSherdAvatar);
//                    }

                }else if(getItemViewType(position) == RECEIVE_TXT_TEXTTYPE_30_VIEW){//大表情
//                    ReceiveAvatar(message, holder.vAvatarIv);
//                    // 消息内容
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    if (!TextUtils.isEmpty(mesgescontent)) {
//                        holder.vMessageContentTv.setText(SpanStringUtils.getEmotionContentText(EmotionUtils.EMOTION_TOTAL, mContext, holder.vMessageContentTv, mesgescontent));
//                    }
                }else if (getItemViewType(position) == RECEIVE_TXT_TEXTTYPE_31_VIEW) {//红包提醒
//                    String fasongrenuname = message.getMsgStringAttribute("username", null);
//                    holder.vTredSysMessage.setText(fasongrenuname + mContext.getResources().getString(R.string.chat_redpackage_chat_systemtmessage));
                } else if (getItemViewType(position) == RECEIVE_TXT_TEXTTYPE_32_VIEW) {//代付提醒
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    String[] StringText = mesgescontent.split("\\|");
//                    String type = null;
//                    String money = null;
//                    try {
//                        type = StringText[0];
//                        money = StringText[1];
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        e.printStackTrace();
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                    if (type.equals("1")) {
//                        //对方已成功帮你代付5000元
//                        String text = String.format(mContext.getString(R.string.chat_payfor_system_success), money);
//                        holder.vTredSysMessage.setText(text);
//                    } else if (type.equals("2")) {
//                        //对方已拒绝你的代付申请
//                        holder.vTredSysMessage.setText(mContext.getResources().getString(R.string.chat_payfor_system_jj));
//                    } else if (type.equals("3")) {
//                        //该代付申请已取消
//                        holder.vTredSysMessage.setText(mContext.getResources().getString(R.string.chat_payfor_system_qx));
//                    }
                }
            }
            break;
            case SEND: {
                // ===============================================右(发出的消息)===================================
                if (getItemViewType(position) == SEND_TXT_MESSAGE_VIEW) {
                    MessageStatus(message,holder,position);
                    SendAvatar(holder.vAvatarIv);
                    regitvip(holder.vMessageBgRl);
                    // 文字消息
                    // 消息内容
//                    holder.vMessageContentTv.setText(EmojiUtils.getSmiledText(mContext, ((EMTextMessageBody) message.getBody()).getMessage()));
                    // 消息内容
                    String mesgescontent = ((MMessageTxTBody) message.getMsgBody()).getContent();
                    if (!TextUtils.isEmpty(mesgescontent)) {
                        holder.vMessageContentTv.setText(SpanStringUtils.getEmotionContentText(EmotionUtils.EMOTION_TOTAL, mContext, holder.vMessageContentTv, mesgescontent));
                    }
                } else if (getItemViewType(position) == SEND_IMAGE_MESSAGE_VIEW) {
                    MessageStatus(message,holder,position);
                    SendAvatar(holder.vAvatarIv);
                    regitvip(holder.vMessageBgRl);
                    MMessageImageBody mEMImageBody = ((MMessageImageBody) message.getMsgBody());
                    // 图片消息
                    if (!TextUtils.isEmpty(mEMImageBody.getLocalUrl())) {
                        Glide.with(mContext).load(mEMImageBody.getLocalUrl()).into(holder.vMessageContentIv);
                    }
                } else if (getItemViewType(position) == SEND_VOICE_MESSAGE_VIEW) {
//                    MessageStatus(message,holder,position);
//                    SendAvatar(holder.vAvatarIv);
//                    regitvip(holder.vMessageBgRl);
//                    //截取本地路径
//                    String length = null;
//                    try {
//                        String reqResult = message.getBody() + "";
//                        String[] getSignInfo = reqResult.split(",");
//                        String getlenth = getSignInfo[3];
//                        String[] getlengths = getlenth.split(":");
//                        //截取语音长度
//                        length = getlengths[1];
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        e.printStackTrace();
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//                    //根据语音长度设置框的长度
//                    if (!TextUtils.isEmpty(length)) {
//                        if (Integer.parseInt(length) >= 0 && Integer.parseInt(length) <= 20) {
//                            holder.vMessageContentTv.setText("    " + TimeUtils.LongGetMinute(Integer.parseInt(length)) + "”    ");
//                        } else if (Integer.parseInt(length) > 20 && Integer.parseInt(length) <= 50) {
//                            holder.vMessageContentTv.setText("        " + TimeUtils.LongGetMinute(Integer.parseInt(length)) + "”    ");
//                        } else if (Integer.parseInt(length) > 50 && Integer.parseInt(length) <= 80) {
//                            holder.vMessageContentTv.setText("             " + TimeUtils.LongGetMinute(Integer.parseInt(length)) + "”    ");
//                        } else if (Integer.parseInt(length) > 80 && Integer.parseInt(length) < 110) {
//                            holder.vMessageContentTv.setText("                 " + TimeUtils.LongGetMinute(Integer.parseInt(length)) + "”    ");
//                        } else if (Integer.parseInt(length) > 110 && Integer.parseInt(length) < 140) {
//                            holder.vMessageContentTv.setText("                           " + TimeUtils.LongGetMinute(Integer.parseInt(length)) + "”    ");
//                        } else if (Integer.parseInt(length) > 140 && Integer.parseInt(length) < 180) {
//                            holder.vMessageContentTv.setText("                                   " + TimeUtils.LongGetMinute(Integer.parseInt(length)) + "”    ");
//                        } else {
//                            holder.vMessageContentTv.setText("                                            " + TimeUtils.LongGetMinute(Integer.parseInt(length)) + "”    ");
//                        }
//
//                    }
//                    //判断语音是否已听
//                    if (message.isListened()) {
//                        holder.vImgRead.setVisibility(View.INVISIBLE);
//                    }else{
//                        holder.vImgRead.setVisibility(View.VISIBLE);
//                    }
                } else if (getItemViewType(position) == SEND_VOIDE_MESSAGE_VIEW) {
//                    MessageStatus(message,holder,position);
//                    SendAvatar(holder.vAvatarIv);
//                    regitvip(holder.vMessageBgRl);
//                    //视频消息
//                    EMVideoMessageBody mEMVideoBody = (EMVideoMessageBody) message.getBody();
//                    if (mEMVideoBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.SUCCESSED) {
//                        if (!TextUtils.isEmpty(mEMVideoBody.getLocalThumb())) {
//                            Glide.with(mContext).load(mEMVideoBody.getLocalThumb()).into(holder.vMessageContentIv);
//                        }
//                    }
                } else if (getItemViewType(position) == SEND_TXT_TEXTTYPE_01_VIEW) {//已取消，未成功通话，呼叫方主动取消
//                    SendAvatar(holder.vAvatarIv);
//                    regitvip(holder.vMessageBgRl);
//                    // 消息内容
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    if (!TextUtils.isEmpty(mesgescontent)) {
//                        holder.vMessageContentTv.setText(SpanStringUtils.getEmotionContentText(EmotionUtils.EMOTION_TOTAL, mContext, holder.vMessageContentTv, mesgescontent));
//                    }
                } else if (getItemViewType(position) == SEND_TXT_TEXTTYPE_02_VIEW) {//拒绝通话，未成功通话，对方挂断，呼叫方显示：对方已拒绝，对方显示：未接听
//                    SendAvatar(holder.vAvatarIv);
//                    regitvip(holder.vMessageBgRl);
//                    // 消息内容
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    if (!TextUtils.isEmpty(mesgescontent)) {
//                        holder.vMessageContentTv.setText(mContext.getResources().getString(R.string.chat_texttype_party_has_refused));
//                    }
                } else if (getItemViewType(position) == SEND_TXT_TEXTTYPE_03_VIEW) {//语音时长，成功通话，显示通话时间
//                    SendAvatar(holder.vAvatarIv);
//                    regitvip(holder.vMessageBgRl);
//                    // 消息内容
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    if (!TextUtils.isEmpty(mesgescontent)) {
//                        holder.vMessageContentTv.setText(SpanStringUtils.getEmotionContentText(EmotionUtils.EMOTION_TOTAL, mContext, holder.vMessageContentTv, mesgescontent));
//                    }
                } else if (getItemViewType(position) == SEND_TXT_TEXTTYPE_04_VIEW) {//未接听，呼叫方呼叫超时自动挂断，双方都显示未接听
//                    SendAvatar(holder.vAvatarIv);
//                    regitvip(holder.vMessageBgRl);
//                    // 消息内容
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    if (!TextUtils.isEmpty(mesgescontent)) {
//                        holder.vMessageContentTv.setText(mContext.getResources().getString(R.string.chat_texttype_Not_answered));
//                    }
                } else if (getItemViewType(position) == SEND_TXT_TEXTTYPE_05_VIEW) {//已取消，未成功通话，呼叫方主动取消
//                    SendAvatar(holder.vAvatarIv);
//                    regitvip(holder.vMessageBgRl);
//                    // 消息内容
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    if (!TextUtils.isEmpty(mesgescontent)) {
//                        holder.vMessageContentTv.setText(SpanStringUtils.getEmotionContentText(EmotionUtils.EMOTION_TOTAL, mContext, holder.vMessageContentTv, mesgescontent));
//                    }
                } else if (getItemViewType(position) == SEND_TXT_TEXTTYPE_06_VIEW) {//拒绝通话，未成功通话，对方挂断，呼叫方显示：对方已拒绝，对方显示：未接听
//                    SendAvatar(holder.vAvatarIv);
//                    regitvip(holder.vMessageBgRl);
//                    // 消息内容
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    if (!TextUtils.isEmpty(mesgescontent)) {
//                        holder.vMessageContentTv.setText(mContext.getResources().getString(R.string.chat_texttype_party_has_refused));
//                    }
                } else if (getItemViewType(position) == SEND_TXT_TEXTTYPE_07_VIEW) {//语音时长，成功通话，显示通话时间
//                    SendAvatar(holder.vAvatarIv);
//                    regitvip(holder.vMessageBgRl);
//                    // 消息内容
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    if (!TextUtils.isEmpty(mesgescontent)) {
//                        holder.vMessageContentTv.setText(SpanStringUtils.getEmotionContentText(EmotionUtils.EMOTION_TOTAL, mContext, holder.vMessageContentTv, mesgescontent));
//                    }
                } else if (getItemViewType(position) == SEND_TXT_TEXTTYPE_08_VIEW) {//未接听，呼叫方呼叫超时自动挂断，双方都显示未接听
//                    SendAvatar(holder.vAvatarIv);
//                    regitvip(holder.vMessageBgRl);
//                    // 消息内容
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    if (!TextUtils.isEmpty(mesgescontent)) {
//                        holder.vMessageContentTv.setText(mContext.getResources().getString(R.string.chat_texttype_Not_answered));
//                    }
                } else if (getItemViewType(position) == SEND_TXT_TEXTTYPE_10_VIEW) {//分享名片
//                    MessageStatus(message,holder,position);
//                    SendAvatar(holder.vAvatarIv);
//                    //获取当前名片消息携带的扩展消息
//                    String TextType = ((EMTextMessageBody) message.getBody()).getMessage();
//                    String[] StringText = TextType.split("\\|", -1);
//                    String username = null;
//                    String avatar = null;
//                    String gender = null;
//                    String vipLevel = null;
//                    String isSingle = null;
//                    String birthPeriod = null;
//                    String constellation = null;
//                    String occupation = null;
//                    String dposition = null;
//                    String businessAuthStatus = null;
//                    String authStatus = null;
//                    String company = null;
//                    try {
//                        username = StringText[1];
//                        avatar = StringText[2];
//                        gender = StringText[3];
//                        vipLevel = StringText[4];
//                        isSingle = StringText[5];
//                        birthPeriod = StringText[6];
//                        constellation = StringText[7];
//                        occupation = StringText[8];
//                        dposition = StringText[9];
//                        businessAuthStatus = StringText[10];
//                        authStatus = StringText[11];
//                        company = StringText[12];
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        e.printStackTrace();
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//                    if (!TextUtils.isEmpty(avatar)) {
//                        Glide.with(mContext).load(avatar).into(holder.vFavatar);
//
//                    }
//                    if (!TextUtils.isEmpty(authStatus) || !TextUtils.isEmpty(businessAuthStatus)) {
//                        ApplicationUtils.GetAuthstatusResource(Integer.parseInt(authStatus), Integer.parseInt(businessAuthStatus), holder.vFauthentication);
//                    } else {
//                        ApplicationUtils.GetAuthstatusResource(0, 0, holder.vFauthentication);
//                    }
//                    if (!TextUtils.isEmpty(authStatus) || !TextUtils.isEmpty(businessAuthStatus)) {
//                        ApplicationUtils.GetAuthstatusResource(Integer.parseInt(authStatus), Integer.parseInt(businessAuthStatus), holder.vFauthentication);
//                    } else {
//                        ApplicationUtils.GetAuthstatusResource(0, 0, holder.vFauthentication);
//                    }
//                    if (!TextUtils.isEmpty(vipLevel)) {
//                        ApplicationUtils.GetVipStatusUserNameResource(mContext, Integer.parseInt(vipLevel), username, "", holder.vFname);
//                    } else {
//                        ApplicationUtils.GetVipStatusUserNameResource(mContext, 0, username, "", holder.vFname);
//                    }
//                    if (!TextUtils.isEmpty(gender)) {
//                        ApplicationUtils.GetgenderStatusResource(Integer.parseInt(gender), holder.vFsex);
//                    } else {
//                        ApplicationUtils.GetgenderStatusResource(0, holder.vFsex);
//                    }
//                    if (!TextUtils.isEmpty(vipLevel)) {
//                        ApplicationUtils.GetVipStatusResource(Integer.parseInt(vipLevel), holder.vFstatus);
//                    } else {
//                        ApplicationUtils.GetVipStatusResource(0, holder.vFstatus);
//                    }
//                    if (!TextUtils.isEmpty(businessAuthStatus) || !TextUtils.isEmpty(isSingle)) {
//                        String info = ApplicationUtils.GetAuthStatusUserInfo(mContext, Integer.parseInt(businessAuthStatus), Integer.parseInt(isSingle), birthPeriod, constellation, dposition, occupation, company);
//                        holder.vFinfo.setText(info);
//                    } else {
//                        String info = ApplicationUtils.GetAuthStatusUserInfo(mContext, 0, 0, birthPeriod, constellation, dposition, occupation, company);
//                        holder.vFinfo.setText(info);
//                    }
                } else if (getItemViewType(position) == SEND_TXT_TEXTTYPE_11_VIEW) {//代付
//                    MessageStatus(message,holder,position);
//                    SendAvatar(holder.vAvatarIv);
//                    //获取携带的扩展信息 OrderNo|OrderPrice
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    String[] StringText = mesgescontent.split("\\|");
//                    String mRedStatus = null;
//                    try {
//                        mRedStatus = StringText[1];
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        e.printStackTrace();
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                    if(!TextUtils.isEmpty(mRedStatus)){
//                        holder.vRedStatus.setText("￥" + mRedStatus);
//                    }else{
//                        holder.vRedStatus.setText("￥ 0.0");
//                    }

                } else if (getItemViewType(position) == SEND_TXT_TEXTTYPE_12_VIEW) {//邀请评价
//                    MessageStatus(message,holder,position);
//                    SendAvatar(holder.vAvatarIv);
                } else if (getItemViewType(position) == SEND_ADDRESS_MESSAGE_VIEW) {//位置信息
//                    MessageStatus(message,holder,position);
//                    SendAvatar(holder.vAvatarIv);
//                    EMLocationMessageBody mEmLocation = (EMLocationMessageBody) message.getBody();
//                    String address = mEmLocation.getAddress();
//                    String[] StringAddress = address.split("\\|");
//                    String addresstitle = null;
//                    String addressdetail = null;
//                    try {
//                        addresstitle = StringAddress[0];
//                        addressdetail = StringAddress[1];
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        e.printStackTrace();
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//                    if (!TextUtils.isEmpty(addresstitle) && !TextUtils.isEmpty(addressdetail)) {
//                        holder.vAddress.setText(addresstitle);
//                        holder.vAddressDetail.setText(addressdetail);
//                    }
                } else if (getItemViewType(position) == SEND_TXT_TEXTTYPE_13_VIEW) {//分享动态
//                    MessageStatus(message,holder,position);
//                    try {
//                        SendAvatar(holder.vAvatarIv);
//                        //获取携带的扩展信息 msgId| title|content|msgImage
//                        String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                        String[] StringText = mesgescontent.split("\\|");
//                        String title = null;
//                        String content = null;
//                        String msgImage = null;
//                        try {
//                            title = StringText[3];
//                            content = StringText[4];
//                            msgImage = StringText[1];
//                        } catch (ArrayIndexOutOfBoundsException e) {
//                            e.printStackTrace();
//                        } catch (Exception e){
//                            e.printStackTrace();
//                        }
//                        if (!TextUtils.isEmpty(title)) {
//                            holder.vXTtitle.setText(title);
//                        }
//                        if (!TextUtils.isEmpty(content)) {
//                            holder.vXTdetail.setText(content);
//                        }
//                        if (!TextUtils.isEmpty(msgImage)) {
//                            Glide.with(mContext).load(msgImage).into(holder.vXavatar);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                } else if (getItemViewType(position) == SEND_TXT_TEXTTYPE_14_VIEW) {//红包
//                    MessageStatus(message,holder,position);
//                    try {
//                        SendAvatar(holder.vAvatarIv);
//                        //获取携带的扩展信息 红包ID|详细描述
//                        String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                        String[] StringText = mesgescontent.split("\\|");
//                        String mContent = null;
//                        try {
//                            mContent = StringText[1];
//                        } catch (ArrayIndexOutOfBoundsException e) {
//                            e.printStackTrace();
//                        } catch (Exception e){
//                            e.printStackTrace();
//                        }
//                        String status = message.getMsgStringAttribute("redType", null);
//                        if (!TextUtils.isEmpty(mContent)) {
//                            holder.vRedContent.setText(mContent);
//                        }
//                        if (status.equals("0")) {//未领取
//                            holder.vRedStatus.setText(mContext.getResources().getString(R.string.chat_red_pack_lingqu));
//                            holder.vLmengban.setBackgroundColor(Color.parseColor("#00FFFFFF"));
//                        } else if (status.equals("1")) {//已领取
//                            holder.vRedStatus.setText(mContext.getResources().getString(R.string.chat_red_pack_yilingqu));
//                            holder.vLmengban.setBackgroundColor(Color.parseColor("#90FFFFFF"));
//                        } else if (status.equals("2")) {//已过期
//                            holder.vRedStatus.setText(mContext.getResources().getString(R.string.chat_red_pack_yiguoqi));
//                            holder.vLmengban.setBackgroundColor(Color.parseColor("#90FFFFFF"));
//                        }
//                    } catch (Resources.NotFoundException e) {
//                        e.printStackTrace();
//                    }
                } else if (getItemViewType(position) == SEND_TXT_TEXTTYPE_15_VIEW) {//分享店铺
//                    //storeId|storename|storeImage|profile|uid
//                    // storeId:店铺id storename:店铺名称  storeImage：店铺图片    profile：店铺简介   uid：用户id
//                    MessageStatus(message,holder,position);
//                    try {
//                        SendAvatar(holder.vAvatarIv);
//                        String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                        String[] StringText = mesgescontent.split("\\|");
//                        String storename = null;
//                        String storeImage = null;
//                        try {
//                            storename = StringText[1];
//                            storeImage = StringText[2];
//                        } catch (ArrayIndexOutOfBoundsException e) {
//                            e.printStackTrace();
//                        } catch (Exception e){
//                            e.printStackTrace();
//                        }
//
//                        if (!TextUtils.isEmpty(storeImage)) {
//                            Glide.with(mContext).load(storeImage).into(holder.vXavatar);
//                        }
//                        if (!TextUtils.isEmpty(storename)) {
//                            holder.vXTtitle.setText(storename);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    } finally {
//                    }

                } else if (getItemViewType(position) == SEND_TXT_TEXTTYPE_16_VIEW) {//分享商品
//                    //goodsId|storeId|goodsName|goodsImage|specContent|goodsPrice|uid
//                    // goodsId：商品id   storeId:店铺id  goodsName:商品名称  goodsImage：商品图片
//                    // specContent：商品规格  goodsPrice:商品价格   uid：用户id
//                    MessageStatus(message,holder,position);
//                    try {
//                        SendAvatar(holder.vAvatarIv);
//                        String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                        String[] StringText = mesgescontent.split("\\|");
//                        String goodsName = null;
//                        String goodsImage = null;
//                        String goodsPrice = null;
//                        try {
//                            goodsName = StringText[2];
//                            goodsImage = StringText[3];
//                            goodsPrice = StringText[5];
//                        } catch (ArrayIndexOutOfBoundsException e) {
//                            e.printStackTrace();
//                        } catch (Exception e){
//                            e.printStackTrace();
//                        }
//
//                        if (!TextUtils.isEmpty(goodsName)) {
//                            holder.vXTtitle.setText(goodsName);
//                        }
//                        if (!TextUtils.isEmpty(goodsPrice)) {
//                            holder.vXTdetail.setText("￥" + goodsPrice);
//                        }
//                        if (!TextUtils.isEmpty(goodsImage)) {
//                            Glide.with(mContext).load(goodsImage).into(holder.vXavatar);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }


                } else if (getItemViewType(position) == SEND_TXT_TEXTTYPE_17_VIEW) {//咨询商品
//                    //goodsId|goodsName|goodsImage|specContent|price|backPV|backRate|onSale
//                    //goodsId：商品id   goodsName:商品名称  goodsImage：商品图片  specContent：商品规格
//                    // price:商品价格  backPV:让利PV  backRate：增值PV  onSale：商品是否在销售（1 在销售  0 已下架）
//                    try {
//                        String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                        String[] StringText = mesgescontent.split("\\|");
//                        String goodsName = null;
//                        String goodsImage = null;
//                        String specContent = null;
//                        String price = null;
//                        String backPV = null;
//                        String backRate = null;
//                        String onSale = null;
//                        try {
//                            goodsName = StringText[1];
//                            goodsImage = StringText[2];
//                            specContent = StringText[3];
//                            price = StringText[4];
//                            backPV = StringText[5];
//                            backRate = StringText[6];
//                            onSale = StringText[7];
//                        } catch (ArrayIndexOutOfBoundsException e) {
//                            e.printStackTrace();
//                        }
//
//                        if (!TextUtils.isEmpty(goodsName)) {
//                            holder.vTmcdTitle.setText(goodsName);
//                        }
//                        if (!TextUtils.isEmpty(specContent)) {
//                            holder.vTmcdContent.setText(specContent);
//                        }
//                        if (!TextUtils.isEmpty(goodsImage)) {
//                            Glide.with(mContext).load(goodsImage).into(holder.vImcdImg);
//                        }
//                        if (!TextUtils.isEmpty(onSale)) {
//                            if (onSale.equals("1")) {
//                                holder.vImcdImgmb.setVisibility(View.GONE);
//                            } else if (onSale.equals("0")) {
//                                holder.vImcdImgmb.setVisibility(View.VISIBLE);
//                            }
//                        }
//                        if (!TextUtils.isEmpty(backPV) && !TextUtils.isEmpty(backRate)) {
//                            holder.vTmcdPv.setText("PV:"+backPV + "  " +mContext.getResources().getString(R.string.chat_typr_cunzhi)+ backRate);
//                        }
//                        if (!TextUtils.isEmpty(price)) {
//                            holder.vTmcdPrice.setText("￥" + price);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                } else if(getItemViewType(position) == SEND_TXT_TEXTTYPE_18_VIEW){//群组分享
//                    //GroupID|GroupName|GroupType|GroupAvatar
//                    MessageStatus(message,holder,position);
//                    SendAvatar(holder.vAvatarIv);
//                    String mMessageContent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    String[] StringText = mMessageContent.split("\\|");
//                    String GroupName = null;
//                    String GroupType = null;
//                    String GroupAvatar = null;
//                    try {
//                        GroupName = StringText[1];
//                        GroupType = StringText[2];
//                        GroupAvatar = StringText[3];
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        e.printStackTrace();
//                    }
//
//                    if(!TextUtils.isEmpty(GroupName)){
//                        holder.vTShardName.setText(GroupName);
//                    }
//                    if(!TextUtils.isEmpty(GroupType)){
//                        holder.vTShardType.setText(GroupType);
//                    }
//                    if(!TextUtils.isEmpty(GroupAvatar)){
//                        Glide.with(mContext).load(GroupAvatar).into(holder.vSherdAvatar);
//                    }

                }else if(getItemViewType(position) == SEND_TXT_TEXTTYPE_30_VIEW){//大表情
//                    MessageStatus(message,holder,position);
//                    SendAvatar(holder.vAvatarIv);
//                    // 消息内容
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    if (!TextUtils.isEmpty(mesgescontent)) {
//                        holder.vMessageContentTv.setText(SpanStringUtils.getEmotionContentText(EmotionUtils.EMOTION_TOTAL, mContext, holder.vMessageContentTv, mesgescontent));
//                    }
                }else if (getItemViewType(position) == SEND_TXT_TEXTTYPE_31_VIEW) {//红包提醒
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    String[] StringText = mesgescontent.split("\\|");
//                    String uid = null;
//                    String uname = null;
//                    try {
//                        uid = StringText[0];
//                        uname = StringText[1];
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        e.printStackTrace();
//                    }
//
//                    if(!TextUtils.isEmpty(uid) &&  !TextUtils.isEmpty(uname)){
////                        String usernad = Utils.getusernote(mContext, uid) != null ? Utils.getusernote(mContext, uid) : uname;
//                        String defatext = String.format(mContext.getResources().getString(R.string.chat_redpackage_chat_systemtmessages), uname);
//                        holder.vTredSysMessage.setText(defatext);
//                    }


                } else if (getItemViewType(position) == SEND_TXT_TEXTTYPE_32_VIEW) {//代付提醒
//                    String mesgescontent = ((EMTextMessageBody) message.getBody()).getMessage();
//                    String[] StringText = mesgescontent.split("\\|");
//                    String type = null;
//                    String money = null;
//                    try {
//                        type = StringText[0];
//                        money = StringText[1];
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        e.printStackTrace();
//                    }
//
//                    if(!TextUtils.isEmpty(type)){
//                        if (type.equals("1")) {
//
//                            if(!TextUtils.isEmpty(money)){
//                                //你已成功帮对方代付5000元
//                                String text = String.format(mContext.getString(R.string.chat_payfor_system_send_success), money);
//                                holder.vTredSysMessage.setText(text);
//                            }else{
//                                String text = String.format(mContext.getString(R.string.chat_payfor_system_send_success), "0.00");
//                                holder.vTredSysMessage.setText(text);
//                            }
//
//                        } else if (type.equals("2")) {
//                            //你已拒绝对方的代付申请
//                            holder.vTredSysMessage.setText(mContext.getResources().getString(R.string.chat_payfor_system_send_jj));
//                        } else if (type.equals("3")) {
//                            //该代付申请已取消
//                            holder.vTredSysMessage.setText(mContext.getResources().getString(R.string.chat_payfor_system_qx));
//                        }
//                    }

                }
            }
            break;
        }


    }

    /**
     * 赋值消息状态
     * @param message
     * @param holder
     * @param position
     */
    private void MessageStatus(final MMessage message, ChatMessageAdapter.ViewHolder holder, final int position) {
//        //赋值消息状态
//        if (message.status() == MMessage.Status.SUCCESS) {
//            holder.vLiStatus.setVisibility(View.GONE);
//        } else if (message.status() == MMessage.Status.FAIL) {
//            holder.vLiStatus.setVisibility(View.VISIBLE);
//            holder.vImgStatusError.setVisibility(View.VISIBLE);
//            holder.vStatusProgress.setVisibility(View.GONE);
//        } else {
//            holder.vLiStatus.setVisibility(View.VISIBLE);
//            holder.vImgStatusError.setVisibility(View.GONE);
//            holder.vStatusProgress.setVisibility(View.VISIBLE);
//        }
//        holder.vLiStatus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (message.status() == MMessage.Status.FAIL) {
//                    //进行该条消息重发
//                    IMSendUtil.resendMessage(Constant.config,message);
//                    //刷新消息状态
//                    notifyItemChanged(position);
//                    new Handler().postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            // 延迟执行的代码
//                            EventBus.getDefault().post(new ChatReceivedEventBus());
//                        }
//                    }, 1000);// 1000毫秒执行，1秒
//                }
//            }
//        });
    }

    private void ReceiveAvatar(final MMessage message, ImageView vAvatarIv) {
        mAvatar = message.getMsgStringAttribute(Constant.STRING_ATTRIBUTE_GROUP_USERAVATAR, null);
        if (!TextUtils.isEmpty(mAvatar)) {
            Glide.with(mContext).load(mAvatar).into(vAvatarIv);
        }
        vAvatarIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //获取的是自己本身的UID
                    int otherUid = Integer.parseInt(message.getMsgStringAttribute("uid", ""));
//                    CardActivity.selectCardActivity(mContext, otherUid);
                } catch (Exception e) {
                    MgeToast.showErrorToast(mContext, mContext.getResources().getString(R.string.chat_getuser_avatar), Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void SendAvatar(ImageView vAvatarIv) {
//        final User user = User.GetLoginedUser(mContext);
//        // 头像
//        if (!TextUtils.isEmpty(user.avatar)) {
//            ImageDisplayUtils.displayWithTransform(mContext, user.avatar, vAvatarIv, new CircleTransform());
//            Glide.with(mContext).load(mAvatar).into(vAvatarIv);
//        }
//        vAvatarIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
////                    CardActivity.selectCardActivity(mContext, user.uid);
//                } catch (Exception e) {
//                    MgeToast.showErrorToast(mContext, mContext.getResources().getString(R.string.chat_getuser_avatar), Toast.LENGTH_SHORT);
//                }
//            }
//        });
    }

    private void regitvip(RelativeLayout vMessageBgRl) {
        // VIP状态
        /*if (mVipLeaval > 0) {
           vMessageBgRl.setBackgroundResource(R.drawable.chat_vip_send_bg1);
        } else {
           vMessageBgRl.setBackgroundResource(R.drawable.chat_send_bg1);
        }*/
    }

    private void leftvvip(RelativeLayout vMessageBgRl) {
        /*if (mVipLeaval > 0) {
            vMessageBgRl.setBackgroundResource(R.drawable.chat_vip_receive_bg1);
        } else {
            vMessageBgRl.setBackgroundResource(R.drawable.chat_receive_bg1);
        }*/
    }

    @Override
    public int getItemCount() {
        return mMMessage.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        /**
         * 消息时间
         */
        @Nullable
        @BindView(R.id.tv_message_time)
        TextView vMessageTimeTv;
        /**
         * 消息头像
         */
        @Nullable
        @BindView(R.id.iv_avatar)
        ImageView vAvatarIv;
        /**
         * 文字消息内容
         */
        @Nullable
        @BindView(R.id.tv_message_content)
        TextView vMessageContentTv;
        /**
         * 文字消息对话气泡
         */
        @Nullable
        @BindView(R.id.rl_message_bg)
        RelativeLayout vMessageBgRl;
        /**
         * 图片消息，图片
         */
        @Nullable
        @BindView(R.id.iv_message_content)
        ImageView vMessageContentIv;

        /**
         * 分享名片-头像
         */
        @Nullable
        @BindView(R.id.iv_otheravatar)
        ShapedImageView vFavatar;

        /**
         * 分享名片-昵称
         */
        @Nullable
        @BindView(R.id.tv_username)
        TextView vFname;

        /**
         * 分享名片-是否认证
         */
        @Nullable
        @BindView(R.id.iv_authentication)
        ImageView vFauthentication;

        /**
         * 分享名片-是否认证
         */
        @Nullable
        @BindView(R.id.iv_vip_status)
        ImageView vFstatus;
        /**
         * 分享名片-性别
         */
        @Nullable
        @BindView(R.id.iv_sex)
        ImageView vFsex;

        /**
         * 分享名片-描述
         */
        @Nullable
        @BindView(R.id.tv_user_info)
        TextView vFinfo;
        /**
         * 位置信息
         */
        @Nullable
        @BindView(R.id.tv_hx_address)
        TextView vAddress;
        /**
         * 位置信息详细信息
         */
        @Nullable
        @BindView(R.id.tv_hx_address_detail)
        TextView vAddressDetail;
        /**
         * 分享动态图片
         */
        @Nullable
        @BindView(R.id.hx_f_avatar)
        ImageView vXavatar;
        /**
         * 分享动态标题
         */
        @Nullable
        @BindView(R.id.hx_f_tvtitle)
        TextView vXTtitle;
        /**
         * 分享动态内容
         */
        @Nullable
        @BindView(R.id.hx_f_tvdetail)
        TextView vXTdetail;
        /**
         * 语音播放按钮
         */
        @Nullable
        @BindView(R.id.tv_message_content_image)
        ImageView vImgContent;
        /**
         * 红包描述
         */
        @Nullable
        @BindView(R.id.red_package_content)
        TextView vRedContent;
        /**
         * 红包状态
         */
        @Nullable
        @BindView(R.id.red_package_status)
        TextView vRedStatus;
        /**
         * 红包已领取的蒙版
         */
        @Nullable
        @BindView(R.id.red_mengban)
        LinearLayout vLmengban;
        /**
         * 语音红点
         */
        @Nullable
        @BindView(R.id.img_voice_read)
        ImageView vImgRead;
        /**
         * 代付红包系统消息
         */
        @Nullable
        @BindView(R.id.red_system_message)
        TextView vTredSysMessage;
        /**
         * 商品图
         */
        @Nullable
        @BindView(R.id.type_seventeen_img)
        ImageView vImcdImg;
        /**
         * 商品已下架
         */
        @Nullable
        @BindView(R.id.type_seventeen_imgmb)
        ImageView vImcdImgmb;
        /**
         * 商品titile
         */
        @Nullable
        @BindView(R.id.type_seventeen_title)
        TextView vTmcdTitle;
        /**
         * 商品描述
         */
        @Nullable
        @BindView(R.id.type_seventeen_content)
        TextView vTmcdContent;
        /**
         * 商品pv
         */
        @Nullable
        @BindView(R.id.type_seventeen_pv)
        TextView vTmcdPv;
        /**
         * 商品价格
         */
        @Nullable
        @BindView(R.id.type_seventeen_price)
        TextView vTmcdPrice;
        /**
         * 分享群组头像
         */
        @Nullable
        @BindView(R.id.shard_group_avatar)
        ShapedImageView vSherdAvatar;
        /**
         * 分享群组名称
         */
        @Nullable
        @BindView(R.id.shard_group_name)
        TextView vTShardName;
        /**
         * 分享群组类型
         */
        @Nullable
        @BindView(R.id.shard_group_type)
        TextView vTShardType;
        /**
         * 消息状态布局
         */
        @Nullable
        @BindView(R.id.msg_status_li)
        LinearLayout vLiStatus;
        /**
         * 消息发送失败
         */
        @Nullable
        @BindView(R.id.msg_status_error)
        ImageView vImgStatusError;
        /**
         * 消息发送中
         */
        @Nullable
        @BindView(R.id.msg_status_progress)
        ProgressBar vStatusProgress;


        private ChatItemClickListener mListener;

        public ViewHolder(View itemView, ChatItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mListener = listener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mListener != null) {
                mListener.onLongClick(vMessageBgRl, getPosition());
            }
            return true;
        }
    }
}
