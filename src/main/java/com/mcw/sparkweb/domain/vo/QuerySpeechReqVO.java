package com.mcw.sparkweb.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author miaochangwei1
 * @Package : com.mcw.sparkweb.domain.vo
 * @Description : TODO
 * @Create on : 2024/1/16 20:53
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuerySpeechReqVO {

    private String id;

    private Long startTime;

    private Long endTime;
}
