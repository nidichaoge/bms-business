package com.mouse.bms.business.api.service;

import com.mouse.bms.business.api.request.RegisterInfoRequest;
import com.mouse.bms.business.common.response.PlainResult;

/**
 * CopyRight(C),mouse
 *
 * @author : mouse
 * @fileName : AccountService
 * @date : 2019/4/6 10:31
 * @description :
 */
public interface AccountService {

    /**
     * 登录
     * @param username
     * @param password
     * @param type
     * @return
     */
    PlainResult<Boolean> login(String username, String password, Short type);

    /**
     * 注册
     * @param registerInfoRequest
     * @return
     */
    PlainResult<Boolean> register(RegisterInfoRequest registerInfoRequest);

}
