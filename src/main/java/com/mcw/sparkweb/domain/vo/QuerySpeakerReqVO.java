package com.mcw.sparkweb.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author miaochangwei1
 * @Package : com.mcw.sparkweb.domain.vo
 * @Description : TODO
 * @Create on : 2024/1/16 14:46
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuerySpeakerReqVO {

    private String field;

    private String queryValue;

}
