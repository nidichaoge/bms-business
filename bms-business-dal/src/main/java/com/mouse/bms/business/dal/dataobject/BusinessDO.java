package com.mouse.bms.business.dal.dataobject;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * CopyRight(C),mouse
 *
 * @author : mouse
 * @fileName : BusinessDO
 * @date : 2019/4/6 09:54
 * @description :
 */
@Data
@Accessors(chain = true)
public class BusinessDO {

    private Long id;

    private Long storeId;

    private String nickName;

    private String icon;

    private Short status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

}
