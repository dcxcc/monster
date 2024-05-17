package com.yl.test.modules;

import com.yl.modules.test.TestApplication;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.SECOND;

/**
 * @Author: YL
 * @Date: 2024-05-14
 * @Project monster
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class BaseTest {

    public static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    /**
     * 将给定的时间（以秒为单位）转换为标准的HH:MM:SS格式的字符串。
     *
     * @param time 输入的时间，单位为秒。
     * @return 格式化后的时间字符串，格式为HH:MM:SS。
     */
    public String secondTimeConversion(Long time) {
        // 计算小时数
        Long hour = time / 3600;
        // 计算分钟数
        Long minute = time % 3600 / 60;
        // 计算秒数
        Long second = time % 60;
        // 使用String.format格式化时间，并返回格式化后的时间字符串
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    /**
     * 将给定的时间（以毫秒为单位）转换为标准的HH:MM:SS格式的字符串。
     *
     * @param time 输入的时间，单位为秒。
     * @return 格式化后的时间字符串，格式为HH:MM:SS.ms。
     */
    public String millisecondTimeConversion(Long time) {
        // 计算小时数
        Long hour = time / 3600000;
        // 计算分钟数
        Long minute = time % 3600000 / 60000;
        // 计算秒数
        Long second = time % 60000 / 1000;
        // 计算毫秒数
        Long millisecond = time % 1000;
        // 使用String.format格式化时间，并返回格式化后的时间字符串
        return String.format("%02d:%02d:%02d.%03d", hour, minute, second, millisecond);
    }

    /**
     * 将特定时间单位转换为字符串格式。
     *
     * @param time     指定的时间量，其单位由timeUnit参数指定。
     * @param timeUnit 时间量的单位，可选值包括秒和毫秒。
     * @return 根据输入的时间单位，将时间转换为相应的字符串格式。如果时间单位无效，则抛出IllegalArgumentException异常。
     */
    public String timeStampConversion(Long time, TimeUnit timeUnit) {
        // 将毫秒数转换为Instant对象
        Instant instant = null;
        switch (timeUnit) {
            case TimeUnit.SECONDS -> instant = Instant.ofEpochSecond(time);
            case TimeUnit.MILLISECONDS -> instant = Instant.ofEpochMilli(time);
            default -> throw new IllegalArgumentException("Invalid time unit.");
        }
        // 将Instant对象转换为ZonedDateTime对象
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        // 将Instant转换为ZonedDateTime，考虑时区
        ZonedDateTime zonedDateTime = instant.atZone(zoneId);
        return zonedDateTime.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    }

    //睡眠当前线程，毫秒为单位
    public void sleepMillisSecond(Long milliSecondTime) {
        try {
            Thread.sleep(milliSecondTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //睡眠当前线程，秒为单位
    public void sleepSecond(Long secondTime) {
        try {
            Thread.sleep(secondTime * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
