package com.mcw.sparkweb.domain.po;

import lombok.Data;

/**
 * @author miaochangwei1
 * @Package : com.mcw.sparkweb.domain.po
 * @Description : TODO
 * @Create on : 2024/1/17 11:32
 **/

@Data
public class QueryLogPO {

    private String queryType;

    private String queryValue;

    private String queryTime;
}
