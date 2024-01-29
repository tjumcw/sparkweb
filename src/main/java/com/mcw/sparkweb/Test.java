package com.mcw.sparkweb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mcw.sparkweb.common.utils.MongoDBUtils;
import com.mcw.sparkweb.task.po.GerVaderSentimentPO;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.*;

/**
 * @author miaochangwei1
 * @Package : com.mcw.sparkweb
 * @Description : TODO
 * @Create on : 2024/1/26 21:18
 **/
public class Test {

    public static void main(String[] args) {
        MongoCollection<Document> speech = MongoDBUtils.getCollection("speech");
        MongoCollection<Document> speaker = MongoDBUtils.getCollection("speaker");
//        List<Document> into = speech_test.find(new Document("_id", "20100100")).into(new ArrayList<>());
//        System.out.println(into);
        Document query = new Document("GerVaderSentiment", new Document("$exists", true));
        List<Document> documents = speech.find(query).into(new ArrayList<>());
        Map<String, Double> speakerToSentimentMap = new HashMap<>();
        documents.stream().forEach(document -> {
            String speakerId = document.get("speaker", String.class);
            double gerVaderSentiment = JSON.parseObject(JSON.toJSONString(document.get("GerVaderSentiment")), new TypeReference<List<GerVaderSentimentPO>>() {
                    })
                    .stream().mapToDouble(GerVaderSentimentPO -> Double.parseDouble(GerVaderSentimentPO.getSentiment()))
                    .average().orElse(0.0);
            speakerToSentimentMap.put(speakerId, gerVaderSentiment);
        });
        System.out.println(speakerToSentimentMap);
        Map<String, List<Double>> fractionToSentimentMap = new HashMap<>();
        speakerToSentimentMap.keySet().stream().forEach(speakerId -> {
            Document document = speaker.find(new Document("_id", speakerId)).first();
            if (document != null) {
                String fraction = document.get("fraction", String.class);
                if (fraction != null) {
                    if (!fractionToSentimentMap.containsKey(fraction)) {
                        fractionToSentimentMap.put(fraction, new ArrayList<>(Collections.singleton(speakerToSentimentMap.get(speakerId))));
                    } else {
                        fractionToSentimentMap.get(fraction).add(speakerToSentimentMap.get(speakerId));
                    }
                }
            }
        });
        System.out.println(fractionToSentimentMap);
    }
}
