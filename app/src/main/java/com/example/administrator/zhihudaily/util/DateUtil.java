package com.example.administrator.zhihudaily.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by shinoko on 2016/8/9.
 */
public class DateUtil {

    public static final String TODAY_STR = "今日要闻";

    public static String getFormateDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String today = format.format(new Date());

        if(today.equals(date)){
            return TODAY_STR;
        }else{

            Date dateD = null;
            try {
                dateD = format.parse(date);// 将字符串转换为日期
            } catch (ParseException e) {
                return date;
            }

            StringBuilder stringBuilder = new StringBuilder();
            int year = Integer.parseInt(date.substring(0,4));
            if(Calendar.getInstance().get(Calendar.YEAR) != year){
                stringBuilder.append(year + "年");
            }
            stringBuilder.append(date.substring(4, 6) + "月" + date.substring(6, 8) + "日");

            SimpleDateFormat weekdf = new SimpleDateFormat("EEEE");
            String week = weekdf.format(dateD);

            stringBuilder.append(" " + week );

            return stringBuilder.toString();
        }
    }




}
