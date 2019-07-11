package com.mouse.bms.business.dal.dataobject;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * CopyRight(C),mouse
 *
 * @author : mouse
 * @fileName : BusinessAuthenticationDO
 * @date : 2019/4/6 09:55
 * @description :
 */
@Data
@Accessors(chain = true)
public class BusinessAuthenticationDO {

    private Long id;

    private Long businessId;

    private Short identityType;

    private String identifier;

    private String credential;

    private Short status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

}
