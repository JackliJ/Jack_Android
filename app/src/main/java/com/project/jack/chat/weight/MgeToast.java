package com.project.jack.chat.weight;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.jack.R;


/**
 * 自定義Toast
 * <p/>
 * Created by MyPC on 2015/8/7.
 * 由StomHong 修改
 */
public class MgeToast {

    private static Toast mToast;

    public static int LENGTH_LONG = Toast.LENGTH_LONG;

    public static int LENGTH_SHORT = Toast.LENGTH_SHORT;

    public enum Type {
        /**
         * 错误类型
         */
        ERROR,
        /**
         * 成功类型
         */
        SUCCESS,
        /**
         * 等待？
         */
        EXPECT
    }

    /**
     * 不允许初始化
     */
    private MgeToast() {
        throw new ExceptionInInitializerError("cannot init by new");
    }

    /**
     * 初始化toast
     *
     * @param context
     * @param duration
     */
    private static void initToast(Context context, int duration) {
        if (mToast == null) {
            mToast = new Toast(context);
            mToast.setDuration(duration);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflate.inflate(R.layout.toast_mge, null);
            mToast.setView(v);
        }
    }

    /**
     * 初始化成功View
     *
     * @param text 显示的文字
     */
    private static void initSuccessView(Context context, CharSequence text) {
        View v = mToast.getView();

        try {
            TextView tv = (TextView) v.findViewById(R.id.tv_content);
            ImageView iv = (ImageView) v.findViewById(R.id.iv_image);
            if (tv != null && iv != null) {
                tv.setText(text);
                iv.setImageResource(R.drawable.hint_icon_success);
            } else {
                LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layoutView = inflate.inflate(R.layout.toast_mge, null);
                tv = (TextView) layoutView.findViewById(R.id.tv_content);
                iv = (ImageView) layoutView.findViewById(R.id.iv_image);
                tv.setText(text);
                iv.setImageResource(R.drawable.hint_icon_success);
                mToast.setView(layoutView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化错误View
     *
     * @param text 显示的文字
     */
    private static void initErrorView(Context context, CharSequence text) {
        View v = mToast.getView();
        try {
            TextView tv = (TextView) v.findViewById(R.id.tv_content);
            ImageView iv = (ImageView) v.findViewById(R.id.iv_image);
            if (tv != null && iv != null) {
                tv.setText(text);
                iv.setImageResource(R.drawable.hint_icon_error);
            } else {
                LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layoutView = inflate.inflate(R.layout.toast_mge, null);
                tv = (TextView) layoutView.findViewById(R.id.tv_content);
                iv = (ImageView) layoutView.findViewById(R.id.iv_image);
                tv.setText(text);
                iv.setImageResource(R.drawable.hint_icon_error);
                mToast.setView(layoutView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化等待View
     *
     * @param text 显示的文字
     */
    private static void initExpectView(Context context, CharSequence text) {
        View v = mToast.getView();

        try {
            TextView tv = (TextView) v.findViewById(R.id.tv_content);
            ImageView iv = (ImageView) v.findViewById(R.id.iv_image);

            if (tv != null && iv != null) {
                tv.setText(text);
                iv.setImageResource(R.drawable.hint_icon_expect);
            } else {
                LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layoutView = inflate.inflate(R.layout.toast_mge, null);
                tv = (TextView) layoutView.findViewById(R.id.tv_content);
                iv = (ImageView) layoutView.findViewById(R.id.iv_image);
                tv.setText(text);
                iv.setImageResource(R.drawable.hint_icon_expect);
                mToast.setView(layoutView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化成功toast,不指定时长
     *
     * @param context
     * @param text     显示的文字
     * @param duration 时长 Toast.LENGTH_SHORT or Toast.LENGTH_LONG
     */
    private static void initSuccessToast(Context context, CharSequence text, int duration) {
        initToast(context, duration);
        initSuccessView(context, text);
        mToast.show();
    }

    /**
     * 初始化等待toast,不指定时长
     *
     * @param context
     * @param text     显示的文字
     * @param duration 时长 Toast.LENGTH_SHORT or Toast.LENGTH_LONG
     */
    private static void initExpectToast(Context context, CharSequence text, int duration) {
        initToast(context, duration);
        initExpectView(context, text);
        mToast.show();
    }

    /**
     * 初始化错误toast,不指定时长
     *
     * @param context
     * @param text     显示的文字
     * @param duration 时长 Toast.LENGTH_SHORT or Toast.LENGTH_LONG
     */
    private static void initErrorToast(Context context, CharSequence text, int duration) {
        initToast(context, duration);
        initErrorView(context, text);
        mToast.show();
    }

    /**
     * 显示成功toast,不指定时长
     *
     * @param context
     * @param text     显示的文字
     * @param duration 时长 Toast.LENGTH_SHORT or Toast.LENGTH_LONG
     */
    public static void showSuccessToast(Context context, CharSequence text, int duration) {
        initSuccessToast(context, text, duration);
    }

    /**
     * 显示等待toast,不指定时长
     *
     * @param context
     * @param text     显示的文字
     * @param duration 时长 Toast.LENGTH_SHORT or Toast.LENGTH_LONG
     */
    public static void showExpectToast(Context context, CharSequence text, int duration) {
        initExpectToast(context, text, duration);
    }

    /**
     * 显示错误toast,不指定时长
     *
     * @param context
     * @param text     显示的文字
     * @param duration 时长 Toast.LENGTH_SHORT or Toast.LENGTH_LONG
     */
    public static void showErrorToast(Context context, CharSequence text, int duration) {
        initErrorToast(context, text, duration);
    }

    /**
     * 显示成功toast，默认时长为Toast.LENGTH_SHORT
     *
     * @param context
     * @param text    显示的文字
     */
    public static void showSuccessToast(Context context, CharSequence text) {
        showSuccessToast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示等待toast，默认时长为Toast.LENGTH_SHORT
     *
     * @param context
     * @param text    显示的文字
     */
    public static void showExpectToast(Context context, CharSequence text) {
        showExpectToast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示错误toast，默认时长为Toast.LENGTH_SHORT
     *
     * @param context
     * @param text    显示的文字
     */
    public static void showErrorToast(Context context, CharSequence text) {
        showErrorToast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示toast
     *
     * @param context
     * @param type     Type.ERROR 错误类型,Type.SUCCESS 成功类型,Type.EXPECT 等待？
     * @param text     显示的文字
     * @param duration 时长 Toast.LENGTH_SHORT or Toast.LENGTH_LONG
     */
    public static Toast showToast(Context context, MgeToast.Type type, CharSequence text, int duration) {
        switch (type) {
            case ERROR: {
                initErrorToast(context, text, duration);
            }
            break;
            case SUCCESS: {
                initSuccessToast(context, text, duration);
            }
            break;
            case EXPECT: {
                initExpectToast(context, text, duration);
            }
            break;
            default:
                break;
        }
        return mToast;
    }

    /**
     * 显示时长为 Toast.LENGTH_SHORT 的toast
     *
     * @param context
     * @param type    Type.ERROR 错误类型,Type.SUCCESS 成功类型,Type.EXPECT 等待？
     * @param text    显示的文字
     */
    public static void showToast(Context context, MgeToast.Type type, CharSequence text) {
        showToast(context, type, text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示指定view的toast
     *
     * @param context
     * @param view     显示的view
     * @param duration 时长 Toast.LENGTH_SHORT or Toast.LENGTH_LONG
     */
    public static Toast showToast(Context context, View view, int duration) {
        initToast(context, duration);
        mToast.setView(view);
        mToast.show();
        return mToast;
    }

    /**
     * 显示指定view的toast ，默认时长为Toast.LENGTH_SHORT
     *
     * @param context
     * @param view    显示的view
     */
    public static void showToast(Context context, View view) {
        showToast(context, view, Toast.LENGTH_SHORT);
    }

    /**
     * 显示指定文字资源id 的toast
     *
     * @param context
     * @param type     Type.ERROR 错误类型,Type.SUCCESS 成功类型,Type.EXPECT 等待？
     * @param resId    文字资源id
     * @param duration 时长 Toast.LENGTH_SHORT or Toast.LENGTH_LONG
     */
    public static void showToast(Context context, MgeToast.Type type, int resId, int duration) {
        showToast(context, type, context.getResources().getString(resId), duration);
    }

    /**
     * 显示指定文字资源id 的toast ，默认时长为Toast.LENGTH_SHORT
     *
     * @param context
     * @param type    Type.ERROR 错误类型,Type.SUCCESS 成功类型,Type.EXPECT 等待？
     * @param resId   文字资源id
     */
    public static void showToast(Context context, MgeToast.Type type, int resId) {
        showToast(context, type, context.getResources().getString(resId), Toast.LENGTH_SHORT);
    }

    /**
     * 显示不指定类型的toast
     *
     * @param context
     * @param text     显示的内容
     * @param duration 时长
     */
    public static void showToast(Context context, CharSequence text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

    /**
     * Close the view if it's showing, or don't show it if it isn't showing yet.
     * You do not normally have to call this.  Normally view will disappear on its own
     * after the appropriate duration.
     */
    public static void hideToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    //***************************************************************************************
    //*******************************以下方法是为了兼容原来已有的写法而添加******************
    //***************************************************************************************

    /**
     * Make a standard toast that just contains a text view.
     *
     * @param context  The context to use.  Usually your {@link android.app.Application}
     *                 or {@link android.app.Activity} object.
     * @param type     指定圖片的樣式
     * @param text     The text to show.  Can be formatted text.
     * @param duration How long to display the message.  Either {@link #LENGTH_SHORT} or
     *                 {@link #LENGTH_LONG}
     */
    @Deprecated
    public static Toast makeText(Context context, MgeToast.Type type, CharSequence text, int duration) {
        return showToast(context, type, text, duration);
    }

    @Deprecated
    public static Toast makeText(View view, Context context, int duration) {
        return showToast(context, view, duration);
    }


    /**
     * Make a standard toast that just contains a text view with the text from a resource.
     *
     * @param context  The context to use.  Usually your {@link android.app.Application}
     *                 or {@link android.app.Activity} object.
     * @param type     指定圖片的樣式
     * @param resId    The resource id of the string resource to use.  Can be formatted text.
     * @param duration How long to display the message.  Either {@link #LENGTH_SHORT} or
     *                 {@link #LENGTH_LONG}
     * @throws Resources.NotFoundException if the resource can't be found.
     */
    @Deprecated
    public static Toast makeText(Context context, MgeToast.Type type, int resId, int duration)
            throws Resources.NotFoundException {
        return showToast(context, type, context.getResources().getText(resId), duration);
    }

    /**
     * 不指定显示时长
     *
     * @param context
     * @param type
     * @param message
     * @return
     * @throws Resources.NotFoundException
     */
    @Deprecated
    public static Toast makeText(Context context, MgeToast.Type type, String message)
            throws Resources.NotFoundException {
        return showToast(context, type, message, Toast.LENGTH_SHORT);
    }

    /**
     * 不指定显示时长
     *
     * @param context
     * @param type
     * @param resId
     * @return
     * @throws Resources.NotFoundException
     */
    @Deprecated
    public static Toast makeText(Context context, MgeToast.Type type, int resId)
            throws Resources.NotFoundException {
        return showToast(context, type, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

}
