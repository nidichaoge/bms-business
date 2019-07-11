package com.mouse.bms.business.dal.dao;

import com.mouse.bms.business.dal.dataobject.BusinessAuthenticationDO;
import org.apache.ibatis.annotations.Param;

/**
 * CopyRight(C),mouse
 *
 * @author : mouse
 * @fileName : BusinessAuthenticationDAO
 * @date : 2019/4/6 10:04
 * @description :
 */
public interface BusinessAuthenticationDAO {

    long insert(BusinessAuthenticationDO businessAuthenticationDO);

    BusinessAuthenticationDO get(@Param("identifier") String identifier,
                                 @Param("identityType") Short identityType);

}
