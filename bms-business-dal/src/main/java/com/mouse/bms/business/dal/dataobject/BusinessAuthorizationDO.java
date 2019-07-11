package com.mouse.bms.business.dal.dataobject;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * CopyRight(C),mouse
 *
 * @author : mouse
 * @fileName : BusinessAuthorizationDO
 * @date : 2019/4/6 09:54
 * @description :
 */
@Data
@Accessors(chain = true)
public class BusinessAuthorizationDO {

    private Long id;

    private Long businessId;

    private Short role;

    private String authority;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

}
