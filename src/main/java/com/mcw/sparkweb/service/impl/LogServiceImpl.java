package com.mcw.sparkweb.service.impl;

import com.alibaba.fastjson.JSON;
import com.mcw.sparkweb.common.exception.BizException;
import com.mcw.sparkweb.common.utils.MongoDBUtils;
import com.mcw.sparkweb.domain.po.QueryLogPO;
import com.mcw.sparkweb.service.LogService;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author miaochangwei1
 * @Package : com.mcw.sparkweb.service.impl
 * @Description : TODO
 * @Create on : 2024/1/17 12:01
 **/
public class LogServiceImpl implements LogService {

    @Override
    public List<Document> queryLogHistory() {
        MongoCollection<Document> queryLog = MongoDBUtils.getCollection("queryLog");
        List<Document> list = queryLog.find().sort(new Document("_id", -1)).into(new ArrayList<>());
        return list;
    }

    @Override
    public Boolean insertNewLog(QueryLogPO queryLogPO) {
        try {
            MongoCollection<Document> queryLog = MongoDBUtils.getCollection("queryLog");
            Document document = Document.parse(JSON.toJSONString(queryLogPO));
            MongoDBUtils.insertDocument(queryLog, document);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("插入日志失败");
        }
        return true;
    }
}
