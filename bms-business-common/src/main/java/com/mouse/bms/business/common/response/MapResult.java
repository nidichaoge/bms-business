package com.mouse.bms.business.common.response;

import lombok.Data;

import java.util.Map;

/**
 * CopyRight(C),mouse
 *
 * @author : mouse
 * @fileName : MapResult
 * @date : 2019/3/2 13:49
 * @description : 接口返回的是map
 */
@Data
public class MapResult<K, V> extends BaseResult {

    private static final long serialVersionUID = 3322979975143941046L;

    private Map<K, V> data;
}
