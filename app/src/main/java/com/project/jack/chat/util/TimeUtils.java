package com.project.jack.chat.util;


import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by www.lijin@foxmail.com on 2016/6/23.
 * <br/>
 * 时间帮助类
 */
public class TimeUtils {

    /**
     * 2013-01-01 00:00:00
     */
    public static final String TIME_STYLE_DATE_TIME_1 = "yyyy-MM-dd HH:mm:ss";
    /**
     * 2013-01-01 00:00
     */
    public static final String TIME_STYLE_DATE_TIME_2 = "yyyy-MM-dd HH:mm";
    /**
     * 00:00:00
     */
    public static final String TIME_STYLE_DATE_TIME_3 = "HH:mm:ss";
    /**
     * 00:00
     */
    public static final String TIME_STYLE_DATE_TIME_4 = "HH:mm";
    /**
     * 2013-01-01
     */
    public static final String TIME_STYLE_DATE_TIME_5 = "yyyy-MM-dd";

    TimerReclen timerReclen;
    public TimeUtils(TimerReclen timerReclen) {
        this.timerReclen = timerReclen;
    }

    //毫秒转秒
    public static String long2String(long time) {

        //毫秒转秒
        int sec = (int) time / 1000;
        int min = sec / 60;    //分钟
        sec = sec % 60;        //秒
        if (min < 10) {    //分钟补0
            if (sec < 10) {    //秒补0
                return "0" + min + ":0" + sec;
            } else {
                return "0" + min + ":" + sec;
            }
        } else {
            if (sec < 10) {    //秒补0
                return min + ":0" + sec;
            } else {
                return min + ":" + sec;
            }
        }

    }

    //毫秒转秒
    public static int long25Int(long time) {
        //毫秒转秒
        int sec = (int) time / 1000;
        return sec;
    }

    //毫秒转秒
    public static String LongGetMinute(int seconds) {
        String timeStr = "" + seconds;
        if (seconds > 9) {
            timeStr = "" + seconds;
        }
        if (seconds > 59) {
            long second = seconds % 60;
            long min = seconds / 60;
            if (second < 9 && min < 10) {
                timeStr = "0" + min + ":" + "0" + second;
            } else if (second < 9 && min > 9) {
                timeStr = +min + ":" + "0" + second;
            } else {
                timeStr = min + ":" + second + "";
            }
            if (min > 59) {
                min = (seconds / 60) % 60;
                long hour = (seconds / 60) / 60;
                timeStr = hour + ":" + min + ":" + second + "";
                if (hour > 24) {
                    hour = ((seconds / 60) / 60) % 24;
                    long day = (((seconds / 60) / 60) / 24);
                    timeStr = day + ":" + hour + ":" + min + ":" + second + "";
                }
            }
        }
        return timeStr;
    }

    /**
     * 将秒转换为时分秒
     *
     * @return
     */
    public static String getMS(int seconds) {
        String timeStr = "00:0" + seconds;
        if (seconds > 9) {
            timeStr = "00:" + seconds;
        }
        if (seconds > 59) {
            long second = seconds % 60;
            long min = seconds / 60;
            if (second < 9 && min < 10) {
                timeStr = "0" + min + ":" + "0" + second;
            } else if (second < 9 && min > 9) {
                timeStr = +min + ":" + "0" + second;
            } else {
                timeStr = min + ":" + second + "";
            }
            if (min > 59) {
                min = (seconds / 60) % 60;
                long hour = (seconds / 60) / 60;
                timeStr = hour + ":" + min + ":" + second + "";
                if (hour > 24) {
                    hour = ((seconds / 60) / 60) % 24;
                    long day = (((seconds / 60) / 60) / 24);
                    timeStr = day + ":" + hour + ":" + min + ":" + second + "";
                }
            }
        }
        return timeStr;
    }

    /**
     * 将秒转换为时分秒
     *
     * @return
     */
    public static String getMSS(int seconds) {
        String timeStr = "00'0" + seconds;
        if (seconds > 9) {
            timeStr = "00'" + seconds;
        }
        if (seconds > 59) {
            long second = seconds % 60;
            long min = seconds / 60;
            if (second < 9 && min < 10) {
                timeStr = "0" + min + "'" + "0" + second;
            } else if (second < 9 && min > 9) {
                timeStr = +min + "'" + "0" + second;
            } else {
                timeStr = min + "'" + second + "";
            }
            if (min > 59) {
                min = (seconds / 60) % 60;
                long hour = (seconds / 60) / 60;
                timeStr = hour + "'" + min + "'" + second + "";
                if (hour > 24) {
                    hour = ((seconds / 60) / 60) % 24;
                    long day = (((seconds / 60) / 60) / 24);
                    timeStr = day + "'" + hour + "'" + min + "'" + second + "";
                }
            }
        }
        return timeStr;
    }


    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;


    }


    /**
     * 返回当前时间的格式为 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(System.currentTimeMillis());
    }


    public static String GetTimerubbing(long mTime) {
        //时间戳转化为Sting或Date
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long time = new Long(mTime);
        String d = df.format(time);
        Date date = null;
        try {
            date = df.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String times = CalendarUtil.ConverToString(date);
        return times;
    }

    /**
     * 由给定过去的时间计算距离现在的时间
     *
     * @param date 给定的时间,必须是已过去的时间
     * @return
     */
    public static String distanceBeforeNow(Date date, Context context) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return distanceBeforeNow(cal, context);
    }

    /**
     * 由给定过去的时间计算距离现在的时间
     *
     * @param date 给定的时间,必须是已过去的时间
     * @return
     */
    public static String distanceBeforeNow(Calendar date, Context context) {

        Calendar now = Calendar.getInstance();

        int year = now.get(Calendar.YEAR) - date.get(Calendar.YEAR);
        if (year < 0)
            return formats(date.getTime(), TIME_STYLE_DATE_TIME_1);
        else if (year > 0)
            return formats(date.getTime(), TIME_STYLE_DATE_TIME_5);

        int day = now.get(Calendar.DAY_OF_YEAR)
                - date.get(Calendar.DAY_OF_YEAR);
        if (day < 0)
            return formats(date.getTime(), TIME_STYLE_DATE_TIME_1);
        else if (day > 1)
//            return formats(date.getTime(), " MM" + context.getResources().getString(R.string.chat_month) + "dd" + context.getString(R.string.chat_day) + "HH:mm");
            return formats(date.getTime(), "MM-dd HH:mm");
        else if (day == 1)
//            return formats(date.getTime(), context.getResources().getString(R.string.chat_yesterday) + "HH:mm");
            return formats(date.getTime(), "HH:mm");
        int hour = now.get(Calendar.HOUR_OF_DAY)
                - date.get(Calendar.HOUR_OF_DAY);
        if (hour < 0)
            return formats(date.getTime(), TIME_STYLE_DATE_TIME_1);
        else if (hour > 0)/*&& hour <= 4)
            return hour + "小时前";
        else if (hour > 4)*/
            return formats(date.getTime(), TIME_STYLE_DATE_TIME_4);

        int minute = now.get(Calendar.MINUTE) - date.get(Calendar.MINUTE);
        if (minute < 0)
            return formats(date.getTime(), TIME_STYLE_DATE_TIME_1);
        else if (minute > 0)
            return formats(date.getTime(), TIME_STYLE_DATE_TIME_4);//minute + "分钟前";

        int second = now.get(Calendar.SECOND) - date.get(Calendar.SECOND);
        if (second < 0)
            return formats(date.getTime(), TIME_STYLE_DATE_TIME_1);
        else
            return formats(date.getTime(), TIME_STYLE_DATE_TIME_4);//"刚刚";

    }

    /**
     * 根据格式返回日期
     *
     * @param style
     * @return
     */
    public static synchronized String formats(Date date, String style) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(style, Locale.ENGLISH);
        return simpleDateFormat.format(date);
    }

    /**
     * 将时间字符串 转换为分秒字符串
     *
     * @param times
     * @return
     */
    public static String getTimerString(String times) {
        String timer = null;
        //时间戳转化为Sting或Date
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = df.parse(times);
            SimpleDateFormat dfs = new SimpleDateFormat("mm:ss");
            timer = dfs.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timer;
    }

    /**
     * 将时间字符串 转换为分秒字符串
     *
     * @param times
     * @return
     */
    public static String getTimerStringHM(String times) {
        String timer = null;
        //时间戳转化为Sting或Date
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = df.parse(times);
            SimpleDateFormat dfs = new SimpleDateFormat("HH:mm");
            timer = dfs.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timer;
    }

    private int recLen = 0;
    Handler handler = new Handler() {          // handle
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    recLen++;
                    timerReclen.retuanReclen(recLen);//将秒数进行回调
            }
            super.handleMessage(msg);
        }
    };

    private boolean istimer;

    //实现耗时操作的线程
    Runnable mBackgroundRunnable = new Runnable() {
        @Override
        public void run() {
            while (istimer) {
                try {
                    Thread.sleep(1000);     // sleep 1000ms
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (Exception e) {
                }


            }
        }
    };

    //启动计时器
    public  void startTimer() {
        recLen = 0;//启动计时器的时候
        istimer = true;
        if(mBackgroundRunnable != null){
            new Thread(mBackgroundRunnable).start();
        }
    }

    //暂停计时器
    public int stopTimer() {
        istimer = false;
        handler.removeCallbacks(mBackgroundRunnable);
        return recLen;
    }
}
