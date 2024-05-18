package com.yl.auth.service;

import com.yl.monster.system.api.RemoteLogClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: YL
 * @Date: 2024-05-18
 * @Project monster
 */
@Service
public class LoginService {
    @Autowired
    private RemoteLogClient remoteLogClient;

}
