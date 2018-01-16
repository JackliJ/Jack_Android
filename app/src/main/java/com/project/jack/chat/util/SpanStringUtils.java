/**
 *
 */
package com.project.jack.chat.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : zejian
 * @time : 2016年1月5日 上午11:30:39
 * @email : shinezejian@163.com
 * @description :文本中的emojb字符处理为表情图片
 */
public class SpanStringUtils {

    public static SpannableString getEmotionContent(int emotion_map_type, final Context context, final EditText tv, String source) {
        SpannableString spannableString = new SpannableString(source);
        Resources res = context.getResources();

//		String regexEmotion = "\\[([\u4e00-\u9fa5\\w])+\\]";
        String regexEmotion = "\\[[^\\]]+\\]";
        Pattern patternEmotion = Pattern.compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(spannableString);

        while (matcherEmotion.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion.group();
            // 匹配字符串的开始位置
            int start = matcherEmotion.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = EmotionUtils.getImgByName(emotion_map_type, key);
            if (imgRes != null) {
                // 压缩表情图片
                int size = (int) tv.getTextSize() * 13 / 10;
                Log.d("SpanStringUtils", "imgRes:" + imgRes);
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

                ImageSpan span = new ImageSpan(context, scaleBitmap);
                spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spannableString;
    }

    public static SpannableString getEmotionContentText(int emotion_map_type, final Context context, final TextView tv, String source) {
        SpannableString spannableString = new SpannableString(source);
        Resources res = context.getResources();

//		String regexEmotion = "\\[([\u4e00-\u9fa5\\w])+\\]";
        String regexEmotion = "\\[[^\\]]+\\]";
        Pattern patternEmotion = Pattern.compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(spannableString);
        while (matcherEmotion.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion.group();
            // 匹配字符串的开始位置
            int start = matcherEmotion.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = EmotionUtils.getImgByName(emotion_map_type, key);
            if (imgRes != null && imgRes > 0) {
                Log.d("SpanStringUtils", "key" + key);
                // 压缩表情图片
                String[] des = key.split(":");
                Log.d("SpanStringUtils", "des[0].length():" + des[0].length()+"----des[0]--->"+des[0]+"---des[0]---"+des[1]);
                int size;
                if(des[0].length() > 3){
                    size = (int) tv.getTextSize() * 5;
                }else{
                    size = (int) tv.getTextSize() * 13 / 7;
                }
                Log.d("SpanStringUtils", "imgRes:" + imgRes);
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

                ImageSpan span = new ImageSpan(context, scaleBitmap);
                spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        return spannableString;
    }
}
