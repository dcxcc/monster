package com.yl.auth.service;

import com.yl.common.core.constant.Constants;
import com.yl.common.core.constant.SecurityConstants;
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

    /**
     * 用户登录验证。
     *
     * @param username 用户名，不能为空。
     * @param password 密码，不能为空且必须在指定长度范围内。
     * @return LoginUser 登录成功返回用户信息。
     * @throws ServiceException 如果登录过程中出现任何错误，抛出此异常。
     */
    public LoginUser login(String username, String password) {
        // 验证用户名和密码是否为空
        if (StringUtils.isAnyBlank(username, password)) {
            recordLoginInformation(username, Constants.LOGIN_FAIL, "用户/密码必须填写");
            throw new ServiceException("用户/密码必须填写");
        }

        // 验证密码长度是否符合要求
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            recordLoginInformation(username, Constants.LOGIN_FAIL, "用户密码不在指定范围");
            throw new ServiceException("用户密码不在指定范围");
        }

        // 验证用户名长度是否符合要求
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            recordLoginInformation(username, Constants.LOGIN_FAIL, "用户名不在指定范围");
            throw new ServiceException("用户名不在指定范围");
        }

        // 从远程服务获取用户信息
        R<LoginUser> userResult = remoteUserClient.getUserInfo(username, SecurityConstants.INNER);
        if (R.FAIL == userResult.getCode()) {
            throw new ServiceException(userResult.getMsg());
        }

        // 检查获取的用户信息是否为空
        if (StringUtils.isNull(userResult.getData())) {
            recordLoginInformation(username, Constants.LOGIN_FAIL, "登录用户不存在");
            throw new ServiceException("登录用户：" + username + " 不存在");
        }

        LoginUser userInfo = userResult.getData();
        SysUser user = userResult.getData().getSysUser();

        // 检查用户账号是否被删除
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            recordLoginInformation(username, Constants.LOGIN_FAIL, "对不起，您的账号已被删除");
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        }

        // 检查用户账号是否被停用
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            recordLoginInformation(username, Constants.LOGIN_FAIL, "用户已停用，请联系管理员");
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }

        // 验证密码是否正确
        if (!SecurityUtils.matchesPassword(password, user.getPassword())) {
            recordLoginInformation(username, Constants.LOGIN_FAIL, "用户密码错误");
            throw new ServiceException("用户不存在/密码错误");
        }

        // 记录登录成功信息
        recordLoginInformation(username, Constants.LOGIN_SUCCESS, "登录成功");
        return userInfo;
    }

    public void logout(String userName) {
        recordLoginInformation(userName, Constants.LOGOUT, "退出成功");
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
        remoteLogClient.saveLoginInformation(information, SecurityConstants.INNER);
    }


    public void register(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password)) {
            throw new ServiceException("用户/密码必须填写");
        }
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            throw new ServiceException("账户长度必须在2到20个字符之间");
        }
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            throw new ServiceException("密码长度必须在5到20个字符之间");
        }
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username)
                .setNickName(username)
                .setPassword(SecurityUtils.encryptPassword(password));
        R<?> registerResult = remoteUserClient.registerUserInfo(sysUser, SecurityConstants.INNER);
        if (R.FAIL == registerResult.getCode()) {
            throw new ServiceException(registerResult.getMsg());
        }
        recordLoginInformation(username, Constants.REGISTER, "注册成功");
    }
}
