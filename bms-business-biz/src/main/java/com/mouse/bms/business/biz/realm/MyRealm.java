package com.mouse.bms.business.biz.realm;

import com.alibaba.fastjson.JSON;
import com.mouse.bms.business.biz.enums.RoleEnum;
import com.mouse.bms.business.biz.token.JwtToken;
import com.mouse.bms.business.biz.util.JwtUtil;
import com.mouse.bms.business.dal.dao.BusinessAuthenticationDAO;
import com.mouse.bms.business.dal.dao.BusinessAuthorizationDAO;
import com.mouse.bms.business.dal.dataobject.BusinessAuthenticationDO;
import com.mouse.bms.business.dal.dataobject.BusinessAuthorizationDO;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * CopyRight(C),mouse
 *
 * @author : mouse
 * @fileName : MyRealm
 * @date : 2019/4/6 14:14
 * @description :
 */
@Component
public class MyRealm extends AuthorizingRealm {

    @Resource
    private BusinessAuthenticationDAO businessAuthenticationDAO;
    @Resource
    private BusinessAuthorizationDAO businessAuthorizationDAO;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = JwtUtil.getUserName(principalCollection.toString());
        BusinessAuthenticationDO authenticationDO = businessAuthenticationDAO.get(username,(short)0);
        Long businessId = authenticationDO.getBusinessId();
        BusinessAuthorizationDO authorizationDO = businessAuthorizationDAO.get(businessId);
        Short role = authorizationDO.getRole();
        String authority = authorizationDO.getAuthority();
        List<String> authoritys = JSON.parseArray(authority, String.class);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(RoleEnum.sourceOf(role).getRole());
        simpleAuthorizationInfo.addStringPermissions(authoritys);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
        throws AuthenticationException {
        String token = authenticationToken.getCredentials().toString();
        String userName = JwtUtil.getUserName(token);
        if (Objects.isNull(userName)){
            throw new AuthenticationException("token is invalid");
        }
        BusinessAuthenticationDO authenticationDO = businessAuthenticationDAO.get(userName, (short) 0);
        if (Objects.isNull(authenticationDO)){
            throw new AuthenticationException("business doesn't exist");
        }
        if (!JwtUtil.verify(token, userName, authenticationDO.getCredential())) {
            throw new AuthenticationException("Username or password error");
        }
        return new SimpleAuthenticationInfo(token,token,"my_realm");
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

}
