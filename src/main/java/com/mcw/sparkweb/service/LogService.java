package com.mcw.sparkweb.service;

import com.mcw.sparkweb.domain.po.QueryLogPO;
import org.bson.Document;

import java.util.List;

/**
 * @author miaochangwei1
 * @Package : com.mcw.sparkweb.service
 * @Description : TODO
 * @Create on : 2024/1/17 12:01
 **/
public interface LogService {

    /**
     * 查询日志历史记录（倒序）
     * @return 返回日志历史记录的列表
     */
    List<Document> queryLogHistory();

    /**
     * 插入新的查询日志
     * @param queryLogPO 要插入的查询日志对象
     * @return 插入操作的结果，成功返回true，失败返回false
     */
    Boolean insertNewLog(QueryLogPO queryLogPO);
}
