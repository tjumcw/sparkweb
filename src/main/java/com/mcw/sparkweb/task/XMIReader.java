package com.mcw.sparkweb.task;

import com.alibaba.fastjson.JSON;
import com.mcw.sparkweb.common.utils.MongoDBUtils;
import com.mcw.sparkweb.task.parser.BaseParser;
import com.mcw.sparkweb.task.parser.impl.*;
import com.mcw.sparkweb.task.po.BasePO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMIReader {

    private static final Map<String, BaseParser> ELEMENT_PARSER_MAP = new HashMap<>();

    private static MongoCollection<org.bson.Document> speech_test = null;

    static {
        // 注册不同类型元素的解析器
        ELEMENT_PARSER_MAP.put("type5:Sentence", new SentenceParser());
        ELEMENT_PARSER_MAP.put("type5:Token", new TokenParser());
        ELEMENT_PARSER_MAP.put("type5:Lemma", new LemmaParser());
        ELEMENT_PARSER_MAP.put("pos:POS", new POSParser());
        ELEMENT_PARSER_MAP.put("morph:MorphologicalFeatures", new MorphologicalFeaturesParser());
        ELEMENT_PARSER_MAP.put("dependency:Dependency", new DependencyParser());
        ELEMENT_PARSER_MAP.put("type12:GerVaderSentiment", new GerVaderSentimentParser());
        ELEMENT_PARSER_MAP.put("tcas:DocumentAnnotation", new DocumentAnnotationParser());
        ELEMENT_PARSER_MAP.put("type3:DocumentMetaData", new DocumentMetaDataParser());
        ELEMENT_PARSER_MAP.put("annotation2:DocumentModification", new DocumentModificationParser());
        ELEMENT_PARSER_MAP.put("dependency:ROOT", new ROOTParser());
        ELEMENT_PARSER_MAP.put("type4:NamedEntity", new NamedEntityParser());

        speech_test = MongoDBUtils.getCollection("speech_test");
        // 可以继续注册其他类型的解析器
    }
    public static void syncDataToDB() {
        String directoryPath = "src/main/resources/data/speech/";

        // 获取目录下的所有文件
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".xmi")) {
                    processXMIFile(file);
                }
            }
        }
    }

    private static void processXMIFile(File file) {
        try {
            System.out.println("File: " + file.getName() + "开始");
            String speechId  = file.getName().split("\\.")[0].replaceAll("ID", "");
            // 读取 XMI 文件
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            // 获取文档的根元素
            Element rootElement = doc.getDocumentElement();
            Map<String, List<BasePO>> map = new HashMap<>();
            // 遍历注册的解析器
            for (String key : ELEMENT_PARSER_MAP.keySet()) {
                NodeList nodes = rootElement.getElementsByTagName(key);
                BaseParser parser = ELEMENT_PARSER_MAP.get(key);
                List<BasePO> data = parser.parse(nodes);
                map.put(key.split(":")[1], data);
            }
            org.bson.Document document = org.bson.Document.parse(JSON.toJSONString(map));
            document.append("_id", speechId);
            if (speech_test.countDocuments(Filters.eq("_id", speechId)) > 0) {
                System.out.println(speechId + "对应的document已存在，跳过当前任务");
            } else {
                MongoDBUtils.insertDocument(speech_test, document);
            }
            System.out.println("File: " + file.getName() + "结束");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
