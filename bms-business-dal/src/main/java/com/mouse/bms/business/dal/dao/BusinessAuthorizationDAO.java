package com.mouse.bms.business.dal.dao;

import com.mouse.bms.business.dal.dataobject.BusinessAuthorizationDO;
import org.apache.ibatis.annotations.Param;

/**
 * CopyRight(C),mouse
 *
 * @author : mouse
 * @fileName : BusinessAuthorizationDAO
 * @date : 2019/4/6 10:04
 * @description :
 */
public interface BusinessAuthorizationDAO {

    long insert(BusinessAuthorizationDO businessAuthorizationDO);

    BusinessAuthorizationDO get(@Param("businessId") Long businessId);

}
