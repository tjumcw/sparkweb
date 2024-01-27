package com.mcw.sparkweb.task;

import com.mcw.sparkweb.common.utils.MongoDBUtils;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DelXMIDataFromDBTask {

    public static void main(String[] args) {
        List<String> ids = getXmiFiles("src/main/resources/data/speech/");
        MongoCollection<Document> speech = MongoDBUtils.getCollection("speech");
        ids.forEach(id -> {
            MongoDBUtils.deleteDocument(speech, "_id", id);
            System.out.println("id为: " + id + " 的数据删除完毕");
        });
    }

    // 获取目录中所有以 .xmi 结尾的文件名
    public static List<String> getXmiFiles(String directoryPath) {
        List<String> xmiFileNames = new ArrayList<>();

        File directory = new File(directoryPath);

        // 列出目录中的 .xmi 文件
        File[] xmiFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xmi"));

        // 检查是否存在 .xmi 文件
        if (xmiFiles != null) {
            // 遍历每个 .xmi 文件并获取文件名
            for (File xmiFile : xmiFiles) {
                xmiFileNames.add(xmiFile.getName());
            }
        }
        return xmiFileNames.stream().map(name ->
                name.split("\\.")[0].replaceAll("ID", "")).collect(Collectors.toList());
    }

}
