package com.yl.auth.service;

import com.yl.common.core.constant.Constants;
import com.yl.common.core.constant.UserConstants;
import com.yl.common.core.domain.R;
import com.yl.common.core.enums.UserStatus;
import com.yl.common.core.exception.ServiceException;
import com.yl.common.core.utils.ServletUtils;
import com.yl.common.core.utils.StringUtils;
import com.yl.common.core.utils.ip.IpUtils;
import com.yl.common.security.utils.SecurityUtils;
import com.yl.monster.system.api.RemoteLogClient;
import com.yl.monster.system.api.RemoteUserClient;
import com.yl.monster.system.api.domain.LoginInformationLog;
import com.yl.monster.system.api.domain.SysUser;
import com.yl.monster.system.api.model.LoginUser;
import org.springframework.stereotype.Service;

/**
 * @Author: YL
 * @Date: 2024-05-18
 * @Project monster
 */

@Service
public class LoginService {
    private final RemoteLogClient remoteLogClient;
    private final RemoteUserClient remoteUserClient;

    public LoginService(RemoteLogClient remoteLogClient, RemoteUserClient remoteUserClient) {
        this.remoteLogClient = remoteLogClient;
        this.remoteUserClient = remoteUserClient;
    }

    public LoginUser login(String username, String password) {
        if (StringUtils.isAnyBlank(username, password)) {
            recordLoginInformation(username, Constants.LOGIN_FAIL, "用户/密码必须填写");
            throw new ServiceException("用户/密码必须填写");
        }
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            recordLoginInformation(username, Constants.LOGIN_FAIL, "用户密码不在指定范围");
            throw new ServiceException("用户密码不在指定范围");
        }
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            recordLoginInformation(username, Constants.LOGIN_FAIL, "用户名不在指定范围");
            throw new ServiceException("用户名不在指定范围");
        }
        R<LoginUser> userResult = remoteUserClient.getUserInfo(username);
        if (R.FAIL == userResult.getCode()) {
            throw new ServiceException(userResult.getMsg());
        }
        if (StringUtils.isNull(userResult.getData())) {
            recordLoginInformation(username, Constants.LOGIN_FAIL, "登录用户不存在");
            throw new ServiceException("登录用户：" + username + " 不存在");
        }
        LoginUser userInfo = userResult.getData();
        SysUser user = userResult.getData().getSysUser();
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            recordLoginInformation(username, Constants.LOGIN_FAIL, "对不起，您的账号已被删除");
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            recordLoginInformation(username, Constants.LOGIN_FAIL, "用户已停用，请联系管理员");
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }
        if (!SecurityUtils.matchesPassword(password, user.getPassword())) {
            recordLoginInformation(username, Constants.LOGIN_FAIL, "用户密码错误");
            throw new ServiceException("用户不存在/密码错误");
        }
        recordLoginInformation(username, Constants.LOGIN_SUCCESS, "登录成功");
        return userInfo;
    }

    private void recordLoginInformation(String username, String status, String message) {
        LoginInformationLog information = new LoginInformationLog();
        information.setUserName(username)
                .setIpaddr(IpUtils.getIpAddr(ServletUtils.getRequest()))
                .setMsg(message);
        if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
            information.setStatus(Constants.LOGIN_SUCCESS_STATUS);
        } else if (Constants.LOGIN_FAIL.equals(status)) {
            information.setStatus(Constants.LOGIN_FAIL_STATUS);
        }
        remoteLogClient.saveLoginInformation(information);
    }

}
