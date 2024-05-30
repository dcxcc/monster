package com.yl.common.log.service;

import com.yl.common.core.constant.SecurityConstants;
import com.yl.monster.system.api.RemoteLogClient;
import com.yl.monster.system.api.domain.OperLog;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

/**
 * @Author: YL
 * @Date: 2024-05-23
 * @Project monster
 */
@EnableAsync
@Service
public class AsyncLogService {
    private final RemoteLogClient remoteLogClient;


    public AsyncLogService(RemoteLogClient remoteLogClient) {
        this.remoteLogClient = remoteLogClient;
    }

    /**
     * 异步保存操作日志。
     * 该方法将操作日志异步地保存到远端日志服务。
     *
     * @param operLog 操作日志对象，包含日志的详细信息。
     *                operLog中的信息应包括但不限于操作人、操作时间、操作内容等。
     */
    @Async
    public void saveLog(OperLog operLog) {
        // 将操作日志发送给远端日志服务进行保存，标识该操作来源于内部系统调用
        remoteLogClient.saveLog(operLog, SecurityConstants.INNER);
    }
    @Async
    public void asyncTask(){
        long startTime = System.currentTimeMillis();
        try {
            Thread.sleep(10000);
        }catch (Exception ignored){

        }
        long endTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "异步执行完毕，耗时：" + (endTime - startTime) + "毫秒");
    }
}
