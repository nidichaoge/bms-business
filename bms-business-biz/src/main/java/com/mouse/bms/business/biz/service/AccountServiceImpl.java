package com.mouse.bms.business.biz.service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.mouse.bms.business.api.request.RegisterInfoRequest;
import com.mouse.bms.business.api.service.AccountService;
import com.mouse.bms.business.biz.util.JwtUtil;
import com.mouse.bms.business.common.enums.CommonResultEnum;
import com.mouse.bms.business.common.response.PlainResult;
import com.mouse.bms.business.common.util.PlainResults;
import com.mouse.bms.business.dal.dao.BusinessAuthenticationDAO;
import com.mouse.bms.business.dal.dao.BusinessAuthorizationDAO;
import com.mouse.bms.business.dal.dao.BusinessDAO;
import com.mouse.bms.business.dal.dao.StoreDAO;
import com.mouse.bms.business.dal.dataobject.BusinessAuthenticationDO;
import com.mouse.bms.business.dal.dataobject.BusinessAuthorizationDO;
import com.mouse.bms.business.dal.dataobject.BusinessDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * CopyRight(C),mouse
 *
 * @author : mouse
 * @fileName : AccountServiceImpl
 * @date : 2019/4/6 13:58
 * @description :
 */
@Service
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = AccountService.class)
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);
    private static final String ICON = "http://baifu.com";

    @Resource
    private StoreDAO storeDAO;
    @Resource
    private BusinessDAO businessDAO;
    @Resource
    private BusinessAuthenticationDAO businessAuthenticationDAO;
    @Resource
    private BusinessAuthorizationDAO businessAuthorizationDAO;

    @Override
    public PlainResult<Boolean> login(String username, String password, Short type) {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(username)&& StringUtils.isNotEmpty(password));
            BusinessAuthenticationDO exist = businessAuthenticationDAO.get(username, type);
            if (Objects.isNull(exist)){
                throw new Exception("");
            }
            if (password.equals(exist.getCredential())){
                String token = JwtUtil.generateToken(username, password);
                return PlainResults.success(Boolean.TRUE,token);
            }
            return PlainResults.error(CommonResultEnum.EXCEPTION.code,CommonResultEnum.EXCEPTION.message);
        }catch (Exception e){
            LOGGER.error("AccountServiceImpl | login, exception, msg:{}.",e.getMessage());
            return PlainResults.error(CommonResultEnum.EXCEPTION.code,CommonResultEnum.EXCEPTION.message);
        }
    }

    @Override
    public PlainResult<Boolean> register(RegisterInfoRequest registerInfoRequest) {
        try {
            Preconditions.checkArgument(!Objects.isNull(registerInfoRequest));
            String nickName = registerInfoRequest.getNickName();
            String mobile = registerInfoRequest.getMobile();
            String password = registerInfoRequest.getPassword();
            Preconditions.checkArgument(StringUtils.isNotEmpty(nickName)&&StringUtils.isNotEmpty(mobile)&&StringUtils.isNotEmpty(password));
            BusinessAuthenticationDO exist = businessAuthenticationDAO.get(mobile, (short) 0);
            if (!Objects.isNull(exist)){
                throw new Exception("");
            }
            long insert = businessAuthenticationDAO.insert(buildAuthenticationDO(mobile,password,1L));
            if (0<insert){
                businessDAO.insert(buildBusinessDO(nickName,1L));
                businessAuthorizationDAO.insert(buildAuthorizationDO(1L));
                return PlainResults.success(Boolean.TRUE);
            }
            return PlainResults.error(CommonResultEnum.EXCEPTION.code,CommonResultEnum.EXCEPTION.message);
        }catch (Exception e){
            LOGGER.error("AccountServiceImpl | register, exception, msg:{}.",e.getMessage());
            return PlainResults.error(CommonResultEnum.EXCEPTION.code,CommonResultEnum.EXCEPTION.message);
        }
    }

    private BusinessAuthorizationDO buildAuthorizationDO(Long businessId) {
        return new BusinessAuthorizationDO()
            .setBusinessId(businessId)
            .setRole((short)0)
            .setAuthority(JSON.toJSONString(Lists.newArrayList("view","edit")))
            .setCreatedAt(LocalDateTime.now());
    }

    private BusinessDO buildBusinessDO(String nickName,Long storeId) {
        return new BusinessDO()
            .setNickName(nickName)
            .setIcon(ICON)
            .setStatus((short)0)
            .setStoreId(storeId)
            .setCreatedAt(LocalDateTime.now());
    }

    private BusinessAuthenticationDO buildAuthenticationDO(String username, String password,Long businessId) {
        return new BusinessAuthenticationDO()
            .setIdentityType((short)0)
            .setIdentifier(username)
            .setCredential(password)
            .setBusinessId(businessId)
            .setCreatedAt(LocalDateTime.now());
    }

}
