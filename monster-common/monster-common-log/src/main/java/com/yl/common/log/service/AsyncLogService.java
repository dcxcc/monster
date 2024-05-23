package com.yl.common.log.service;

import com.yl.common.core.constant.SecurityConstants;
import com.yl.monster.system.api.RemoteLogClient;
import com.yl.monster.system.api.domain.OperLog;
import org.springframework.stereotype.Service;

/**
 * @Author: YL
 * @Date: 2024-05-23
 * @Project monster
 */
@Service
public class AsyncLogService {
    private final RemoteLogClient remoteLogClient;


    public AsyncLogService(RemoteLogClient remoteLogClient) {
        this.remoteLogClient = remoteLogClient;
    }

    public void saveLog(OperLog operLog){
        remoteLogClient.saveLog(operLog, SecurityConstants.INNER);
    }
}
