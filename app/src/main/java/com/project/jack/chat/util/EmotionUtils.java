
package com.project.jack.chat.util;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;

import com.project.jack.R;


/**
 * @author : zejian
 * @time : 2016年1月5日 上午11:32:33
 * @email : shinezejian@163.com
 * @description :表情加载类,可自己添加多种表情，分别建立不同的map存放和不同的标志符即可
 */
public class EmotionUtils {

	/**
	 * 表情类型标志符
	 */
	public static final int EMOTION_TOTAL = 0;//总的
	public static final int EMOTION_CLASSIC_TYPE=1;//经典表情
    public static final int EMOTTON_AM_TYPE = 2;//小漠表情


	/**
	 * key-表情文字;
	 * value-表情图片资源
	 */
	public static ArrayMap<String, Integer> EMPTY_MAP;
	public static ArrayMap<String,Integer> EMPTY_TOTAL;//总的表情组
	public static ArrayMap<String, Integer> EMOTION_CLASSIC_MAP;
    public static ArrayMap<String, Integer> EMOTTON_AM_MAP;


	static {
		EMPTY_TOTAL = new ArrayMap<>();
		EMPTY_MAP = new ArrayMap<>();
		EMOTION_CLASSIC_MAP = new ArrayMap<>();
		/**
		 * 第一组表情
		 */
		EMOTION_CLASSIC_MAP.put("[bigem:1:]", R.drawable.exp_egg_01);
		EMOTION_CLASSIC_MAP.put("[bigem:2:]", R.drawable.exp_egg_02);
		EMOTION_CLASSIC_MAP.put("[bigem:3:]", R.drawable.exp_egg_03);
		EMOTION_CLASSIC_MAP.put("[bigem:4:]", R.drawable.exp_egg_04);
		EMOTION_CLASSIC_MAP.put("[bigem:5:]", R.drawable.exp_egg_05);
		EMOTION_CLASSIC_MAP.put("[bigem:6:]", R.drawable.exp_egg_06);
		EMOTION_CLASSIC_MAP.put("[bigem:7:]", R.drawable.exp_egg_07);
		EMOTION_CLASSIC_MAP.put("[bigem:8:]", R.drawable.exp_egg_08);
		EMOTION_CLASSIC_MAP.put("[bigem:9:]", R.drawable.exp_egg_09);
		EMOTION_CLASSIC_MAP.put("[bigem:10:]", R.drawable.exp_egg_10);
		EMOTION_CLASSIC_MAP.put("[bigem:11:]", R.drawable.exp_egg_11);
		EMOTION_CLASSIC_MAP.put("[bigem:12:]", R.drawable.exp_egg_12);
		EMOTION_CLASSIC_MAP.put("[bigem:13:]", R.drawable.exp_egg_13);
		EMOTION_CLASSIC_MAP.put("[bigem:14:]", R.drawable.exp_egg_14);
		EMOTION_CLASSIC_MAP.put("[bigem:15:]", R.drawable.exp_egg_15);
		EMOTION_CLASSIC_MAP.put("[bigem:16:]", R.drawable.exp_egg_16);
		EMOTION_CLASSIC_MAP.put("[bigem:17:]", R.drawable.exp_egg_17);
		EMOTION_CLASSIC_MAP.put("[bigem:18:]", R.drawable.exp_egg_18);
		EMOTION_CLASSIC_MAP.put("[bigem:19:]", R.drawable.exp_egg_19);
		EMOTION_CLASSIC_MAP.put("[bigem:20:]", R.drawable.exp_egg_20);


		/**
		 * 第二组表情
		 */
        EMOTTON_AM_MAP = new ArrayMap<>();
        EMOTTON_AM_MAP.put("[em:1:]",R.drawable.ee_1);
        EMOTTON_AM_MAP.put("[em:2:]",R.drawable.ee_2);
        EMOTTON_AM_MAP.put("[em:3:]",R.drawable.ee_3);
        EMOTTON_AM_MAP.put("[em:4:]",R.drawable.ee_4);
        EMOTTON_AM_MAP.put("[em:5:]",R.drawable.ee_5);
        EMOTTON_AM_MAP.put("[em:6:]",R.drawable.ee_6);
        EMOTTON_AM_MAP.put("[em:7:]",R.drawable.ee_7);
        EMOTTON_AM_MAP.put("[em:8:]",R.drawable.ee_8);
        EMOTTON_AM_MAP.put("[em:9:]",R.drawable.ee_9);
        EMOTTON_AM_MAP.put("[em:10:]",R.drawable.ee_10);
        EMOTTON_AM_MAP.put("[em:11:]",R.drawable.ee_11);
        EMOTTON_AM_MAP.put("[em:12:]",R.drawable.ee_12);
        EMOTTON_AM_MAP.put("[em:13:]",R.drawable.ee_13);
        EMOTTON_AM_MAP.put("[em:14:]",R.drawable.ee_14);
        EMOTTON_AM_MAP.put("[em:15:]",R.drawable.ee_15);
        EMOTTON_AM_MAP.put("[em:16:]",R.drawable.ee_16);
        EMOTTON_AM_MAP.put("[em:17:]",R.drawable.ee_17);
        EMOTTON_AM_MAP.put("[em:18:]",R.drawable.ee_18);
        EMOTTON_AM_MAP.put("[em:19:]",R.drawable.ee_19);
        EMOTTON_AM_MAP.put("[em:20:]",R.drawable.ee_20);
        EMOTTON_AM_MAP.put("[em:21:]",R.drawable.ee_21);
        EMOTTON_AM_MAP.put("[em:22:]",R.drawable.ee_22);
        EMOTTON_AM_MAP.put("[em:23:]",R.drawable.ee_23);
        EMOTTON_AM_MAP.put("[em:24:]",R.drawable.ee_24);
        EMOTTON_AM_MAP.put("[em:25:]",R.drawable.ee_25);
        EMOTTON_AM_MAP.put("[em:26:]",R.drawable.ee_26);
        EMOTTON_AM_MAP.put("[em:27:]",R.drawable.ee_27);
        EMOTTON_AM_MAP.put("[em:28:]",R.drawable.ee_28);
        EMOTTON_AM_MAP.put("[em:29:]",R.drawable.ee_29);
        EMOTTON_AM_MAP.put("[em:30:]",R.drawable.ee_30);
        EMOTTON_AM_MAP.put("[em:31:]",R.drawable.ee_31);
        EMOTTON_AM_MAP.put("[em:32:]",R.drawable.ee_32);
        EMOTTON_AM_MAP.put("[em:33:]",R.drawable.ee_33);
        EMOTTON_AM_MAP.put("[em:34:]",R.drawable.ee_34);


		EMPTY_TOTAL.putAll((SimpleArrayMap<? extends String, ? extends Integer>) EMOTION_CLASSIC_MAP);
		EMPTY_TOTAL.putAll((SimpleArrayMap<? extends String, ? extends Integer>) EMOTTON_AM_MAP);
	}

	/**
	 * 根据名称获取当前表情图标R值
	 * @param EmotionType 表情类型标志符
	 * @param imgName 名称
	 * @return
	 */
	public static int getImgByName(int EmotionType,String imgName) {
		Integer integer=null;
		switch (EmotionType){
			case EMOTION_TOTAL:
				integer = EMPTY_TOTAL.get(imgName);
				break;
			case EMOTION_CLASSIC_TYPE:
				integer = EMOTION_CLASSIC_MAP.get(imgName);
				break;
            case EMOTTON_AM_TYPE:
                integer = EMOTTON_AM_MAP.get(imgName);
                break;
			default:
				Log.d("EmotionUtils", "the emojiMap is null!!");
				break;
		}
		return integer == null ? -1 : integer;
	}

	/**
	 * 根据类型获取表情数据
	 * @param EmotionType
	 * @return
	 */
	public static ArrayMap<String, Integer> getEmojiMap(int EmotionType){
		ArrayMap EmojiMap=null;
		switch (EmotionType){
			case EMOTION_CLASSIC_TYPE:
				EmojiMap=EMOTION_CLASSIC_MAP;
				break;
            case EMOTTON_AM_TYPE:
                EmojiMap=EMOTTON_AM_MAP;
                break;
			default:
				EmojiMap=EMPTY_MAP;
				break;
		}
		return EmojiMap;
	}
}
