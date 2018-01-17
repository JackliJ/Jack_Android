package com.project.jack.chat.single.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by zejian
 * Time  16/1/7 上午11:40
 * Email shinezejian@163.com
 * Description:产生fragment的工厂类
 */
public class FragmentFactory {

    public static final String EMOTION_MAP_TYPE="EMOTION_MAP_TYPE";
    public static final String EMOTION_CLASSIC_EXTENDED="EMOTION_CLASSIC_EXTENDED";
    private static FragmentFactory factory;

    private FragmentFactory(){

    }

    /**
     * 双重检查锁定，获取工厂单例对象
     * @return
     */
    public static FragmentFactory getSingleFactoryInstance(){
        if(factory==null){
            synchronized (FragmentFactory.class){
                if(factory==null){
                    factory = new FragmentFactory();
                }
            }
        }
        return factory;
    }

    /**
     * 获取fragment的方法
     * @param emotionType 表情类型，用于判断使用哪个map集合的表情
     * @param classic_extended 表情的类型  是经典小表情还是大表情
     */
    public Fragment getFragment(int emotionType, boolean classic_extended){
        Bundle bundle = new Bundle();

        bundle.putInt(FragmentFactory.EMOTION_MAP_TYPE,emotionType);
        bundle.putBoolean(EMOTION_CLASSIC_EXTENDED,classic_extended);
        ChatEmotiomComplateFragment fragment= ChatEmotiomComplateFragment.newInstance(ChatEmotiomComplateFragment.class,bundle);

        return fragment;
    }

}
