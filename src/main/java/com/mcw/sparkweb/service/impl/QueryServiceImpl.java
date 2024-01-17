package com.mcw.sparkweb.service.impl;

import com.mcw.sparkweb.common.exception.BizException;
import com.mcw.sparkweb.common.utils.MongoDBUtils;
import com.mcw.sparkweb.domain.vo.QuerySpeakerReqVO;
import com.mcw.sparkweb.domain.vo.QuerySpeechReqVO;
import com.mcw.sparkweb.service.QueryService;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import spark.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author miaochangwei1
 * @Package : com.mcw.sparkweb.service.impl
 * @Description : TODO
 * @Create on : 2024/1/16 14:48
 **/

public class QueryServiceImpl implements QueryService {

    @Override
    public List<Document> querySpeakerInfo(QuerySpeakerReqVO vo) {
        MongoCollection<Document> speaker = MongoDBUtils.getCollection("speaker");
        List<Document> list = MongoDBUtils.queryEntriesByField(speaker, vo.getField(), vo.getQueryValue());
        if (CollectionUtils.isEmpty(list)) {
            throw new BizException("query Result is empty");
        }
        return list;
    }

    @Override
    public List<Document> querySpeechInfoByTime(QuerySpeechReqVO vo) {
        MongoCollection<Document> speech = MongoDBUtils.getCollection("speech");
        List<Document> into = speech.find(new Document("protocol.starttime", 1665568800000L)).into(new ArrayList<>());
        // 创建查询条件
        Document query = new Document("protocol.starttime", new Document("$gte", vo.getStartTime()))
                .append("protocol.endtime", new Document("$lte", vo.getEndTime()));
        List<Document> list = speech.find(query).into(new ArrayList<>());
        if (CollectionUtils.isEmpty(list)) {
            throw new BizException("query Result is empty");
        }
        return list;
    }

    @Override
    public Document querySpeechInfoById(String id) {
        MongoCollection<Document> speech = MongoDBUtils.getCollection("speech");
        // 构建查询条件
        Document query = new Document("_id", id);
        // 执行查询
        Document document = speech.find(query).first();
        if (document == null) {
            // 处理查询结果为空的情况
            throw new BizException("Speech not found with ID: " + id);
        }
        return document;
    }
}
