package com.house365.collector.beike.sell;

import com.house365.collector.beike.sell.Thread.BeikeCommand;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BeikeMain {
    public static void main(String[] args) throws Exception {

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        long oneDay = 24 * 60 * 60 * 1000;
        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        dayFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " 23:30:00");
        long initDelay = curDate.getTime() - System.currentTimeMillis();
        initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
        scheduledExecutorService.scheduleAtFixedRate(new BeikeCommand(), initDelay, oneDay, TimeUnit.MILLISECONDS);

    }
}
