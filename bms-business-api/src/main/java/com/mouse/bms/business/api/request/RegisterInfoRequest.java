package com.mouse.bms.business.api.request;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * CopyRight(C),mouse
 *
 * @author : mouse
 * @fileName : RegisterInfoRequest
 * @date : 2019/4/6 11:03
 * @description :
 */
@Data
@Accessors(chain = true)
public class RegisterInfoRequest implements Serializable {

    private String nickName;

    private String mobile;

    private String password;

}
