package com.project.jack.chat.message.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.project.jack.R;
import com.project.jack.chat.util.ApplicationUtils;
import com.project.jack.chat.weight.ShapedImageView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import jack.project.com.imdatautil.model.MMFrend;

/**
 * Created by Sky on 2017/9/14.
 * <br/>
 * 脉友Adapter
 */

public class ChatMgeFrendListAdapter extends BaseAdapter implements SectionIndexer {

    Context mContext;
    List<MMFrend> frends;

    public ChatMgeFrendListAdapter(Context context, List<MMFrend> frends) {
        this.mContext = context;
        this.frends = frends;
    }

    @Override
    public int getCount() {
        return frends.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final MMFrend frend = frends.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.chat_row_mge_frend_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 头部字母
        String catalog = frend.PinYin;
        if (position == 0) {
            viewHolder.vLetterTitle.setVisibility(View.VISIBLE);
            viewHolder.vDivider.setVisibility(View.GONE);
            viewHolder.vLetterTv.setText(frend.PinYin);
        } else {
            String lastCatalog;
            lastCatalog = frends.get(position - 1).PinYin;
            if (TextUtils.equals(catalog, lastCatalog)) {
                viewHolder.vLetterTitle.setVisibility(View.GONE);
                viewHolder.vDivider.setVisibility(View.VISIBLE);
            } else {
                viewHolder.vLetterTitle.setVisibility(View.VISIBLE);
                viewHolder.vDivider.setVisibility(View.GONE);
            }
            viewHolder.vLetterTv.setText(frend.PinYin);
        }

        // 头像
        Glide.with(mContext).load(frend.getMFAvatar()).into(viewHolder.vAvatarIv);
        // 认证
        ApplicationUtils.GetAuthstatusResource(frend.getMFAuthStatus(), frend.getMFBusinessAuthStatus(), viewHolder.vAuehenticationIv);
        //用户标签
        String mTextType = ApplicationUtils.GetAuthStatusUserInfo(mContext,frend.getMFBusinessAuthStatus(),frend.getMFIsSingle(),frend.getMFBirthPeriod(),frend.getMFConstellation(),frend.getMFPosition(),frend.getMFOccupation(),frend.getMFCompany());
        if(!TextUtils.isEmpty(mTextType)){
            viewHolder.vUserInfoTv.setText(mTextType);
        }
        // 昵称或备注
        viewHolder.vNickNameTv.setText(TextUtils.isEmpty(frend.getMFUserNote()) ? frend.getMFUserName() : frend.getMFUserNote());
        // 性别
        ApplicationUtils.GetgenderStatusResource(frend.getMFGender(), viewHolder.vSexIv);
        // Vip
        ApplicationUtils.GetVipStatusResource(frend.getMFVipLevel(), viewHolder.vVipStatusIv);
        viewHolder.vNickNameTv.setTextColor(frend.getMFVipLevel() > 0 ? ActivityCompat.getColor(mContext, R.color.T3) : ActivityCompat.getColor(mContext, R.color.T8));

        //头像点击
        viewHolder.vAvatarIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return convertView;
    }

    class ViewHolder {

        /**
         * 字母标题
         */
        @Nullable
        @BindView(R.id.li_letter_title)
        LinearLayout vLetterTitle;
        @Nullable
        @BindView(R.id.tv_letter)
        TextView vLetterTv;
        @Nullable
        @BindView(R.id.v_divider)
        View vDivider;


        /**
         * 头像
         */
        @Nullable
        @BindView(R.id.iv_avatar)
        ShapedImageView vAvatarIv;
        /**
         * 认证
         */
        @Nullable
        @BindView(R.id.iv_authentication)
        ImageView vAuehenticationIv;
        /**
         * 昵称
         */
        @Nullable
        @BindView(R.id.tv_nick_name)
        TextView vNickNameTv;
        /**
         * 性别
         */
        @Nullable
        @BindView(R.id.iv_sex)
        ImageView vSexIv;
        /**
         * VIP状态
         */
        @Nullable
        @BindView(R.id.iv_vip_status)
        ImageView vVipStatusIv;

        /**
         * 用户信息
         */
        @Nullable
        @BindView(R.id.tv_user_info)
        TextView vUserInfoTv;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        MMFrend sectionFrend = null;
        for (int i = 0; i < frends.size(); i++) {
            sectionFrend = frends.get(i);
            try {
                char firstChar = sectionFrend.PinYin.charAt(0);
                if (firstChar == sectionIndex) {
                    return i;
                }
            } catch (Exception e) {

            }
        }
        return frends.size();
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }
}
