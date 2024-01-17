package com.mcw.sparkweb.service;

import com.mcw.sparkweb.domain.vo.QuerySpeakerReqVO;
import com.mcw.sparkweb.domain.vo.QuerySpeechReqVO;
import org.bson.Document;

import java.util.List;

/**
 * @author miaochangwei1
 * @Package : com.mcw.sparkweb.service
 * @Description : TODO
 * @Create on : 2024/1/16 14:47
 **/
public interface QueryService {

    /**
     * 查询演讲者信息
     * @param vo 查询演讲者请求参数
     * @return 符合查询条件的演讲者信息列表
     */
    List<Document> querySpeakerInfo(QuerySpeakerReqVO vo);

    /**
     * 根据时间查询演讲信息
     * @param vo 查询演讲请求参数
     * @return 符合条件的演讲信息列表
     */
    List<Document> querySpeechInfoByTime(QuerySpeechReqVO vo);

    /**
     * 根据ID查询演讲信息
     * @param id 演讲信息的ID
     * @return 演讲信息
     * @throws IllegalArgumentException 如果ID无效
     */
    Document querySpeechInfoById(String id);
}
