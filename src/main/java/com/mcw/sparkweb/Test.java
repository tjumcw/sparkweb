package com.mcw.sparkweb;

import com.mcw.sparkweb.common.utils.MongoDBUtils;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author miaochangwei1
 * @Package : com.mcw.sparkweb
 * @Description : TODO
 * @Create on : 2024/1/26 21:18
 **/
public class Test {

    public static void main(String[] args) {
        MongoCollection<Document> speech_test = MongoDBUtils.getCollection("speech");
        List<Document> into = speech_test.find(new Document("_id", "20100100")).into(new ArrayList<>());
        System.out.println(into);
    }
}
