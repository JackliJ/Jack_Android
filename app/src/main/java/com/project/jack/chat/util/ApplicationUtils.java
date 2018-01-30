package com.project.jack.chat.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.jack.R;

/**
 * Create by www.lijin@foxmail.com on 2018/1/23 0023.
 * <br/>
 * 用于VIP等需求方式的页面展示  这里只是做展示 你可以根据你的具体需要去修改相应组件
 */

public class ApplicationUtils {


    /**
     * 获取男女性别的图标
     *
     * @param status
     * @param view
     */
    public static void GetgenderStatusResource(int status, ImageView view) {
        switch (status) {
            case 1: {//男
                view.setImageResource(R.drawable.my_homepage_man);
            }
            break;
            case 2: {// 女
                view.setImageResource(R.drawable.my_homepage_woman);
            }
            break;
            default:// 默认女
                view.setImageResource(R.drawable.my_homepage_woman);
                break;
        }
    }

    /**
     * 根据商户认证状态显示用户信息
     *
     * @param context
     * @param businessAuthStatus 商户认证
     * @param isSingle           是否单身
     * @param birthPeriod        年龄段
     * @param constellation      星座
     * @param position           职位
     * @param occupation         行业
     * @param company            公司
     * @return
     */
    public static String GetAuthStatusUserInfo(Context context, int businessAuthStatus, int isSingle, String birthPeriod, String constellation, String position, String occupation, String company) {
        String userInfo = "";

        // 是否商户认证
        if (businessAuthStatus == 1) {
            userInfo = company;
        } else {
            // 是否单身
            String mIsSingle = isSingle == 1 ? context.getResources().getString(R.string.dynamic_single) : "";
            // 年龄段
            String mBirthPeriod = !TextUtils.isEmpty(birthPeriod) ? birthPeriod : "";
            // 星座
            String mConstellation = !TextUtils.isEmpty(constellation) ? constellation : "";
            // 职位
            String userPosition = !TextUtils.isEmpty(position) ? position : "";
            // 行业
            String userOccupation = !TextUtils.isEmpty(occupation) ? occupation : "";

            String temp = !TextUtils.isEmpty(userPosition) ? userPosition : userOccupation;
            String[] str = new String[]{mIsSingle, mBirthPeriod, mConstellation, temp};
            for (int i = 0; i < str.length; i++) {
                if (!TextUtils.isEmpty(str[i])) {
                    if (i == 3) {
                        userInfo = userInfo + str[i];
                    } else {
                        userInfo = userInfo + str[i] + " / ";
                    }
                }
            }
//
//            // 用户信息
//            userInfo = mIsSingle + mBirthPeriod + mConstellation + temp;
        }

        return userInfo;
    }

    /**
     * 根据vip等级为昵称或者备注，添加字体颜色
     *
     * @param context
     * @param vipStatus vip等级
     * @param userName  姓名
     * @param userNote  昵称
     * @param textView
     */
    public static void GetVipStatusUserNameResource(Context context, int vipStatus, String userName, String userNote, TextView textView) {
        if (vipStatus > 0) {
            textView.setTextColor(context.getResources().getColor(R.color.T3));
        } else {
            textView.setTextColor(context.getResources().getColor(R.color.T8));
        }
        textView.setText(TextUtils.isEmpty(userNote) ? userName : userNote);
    }

    /**
     * 获得默认的VIP对应的图标资源
     *
     * @param status
     * @return
     */
    public static void GetVipStatusResource(int status, ImageView view) {
        switch (status) {
            case 0: {
                view.setImageBitmap(null);
            }
            break;
            case 1: {
                view.setImageResource(R.drawable.icon_vip_1);
            }
            break;
            case 2: {
                view.setImageResource(R.drawable.icon_vip_2);
            }
            break;
            case 3: {
                view.setImageResource(R.drawable.icon_vip_3);
            }
            break;
            case 4: {
                view.setImageResource(R.drawable.icon_vip_4);
            }
            break;
            case 5: {
                view.setImageResource(R.drawable.icon_vip_5);
            }
            break;
        }
    }

    /**
     * 认证图标显示
     *
     * @param authStatus         实名认证
     * @param businessAuthStatus 商户认证
     * @param imageView          view
     */
    public static void GetAuthstatusResource(int authStatus, int businessAuthStatus, ImageView imageView) {
        if (businessAuthStatus == 1) {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.common_enterprise);
        } else if (authStatus == 1) {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.common_autonym);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }
}
