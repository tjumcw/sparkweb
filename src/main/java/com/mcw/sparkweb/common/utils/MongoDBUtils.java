package com.mcw.sparkweb.common.utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author miaochangwei1
 * @Package : com.mcw.sparkweb.common.utils
 * @Description : TODO
 * @Create on : 2024/1/15 14:36
 **/
public class MongoDBUtils {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static String host;
    private static int port;
    private static String username;
    private static String password;
    private static String databaseName;

    static {
        try {
            Properties properties = new Properties();
            InputStream resourceAsStream = MongoDBUtils.class.getClassLoader().getResourceAsStream("properties/mongodb.properties");
            properties.load(resourceAsStream);

            host = properties.getProperty("mongodb.host");
            port = Integer.parseInt(properties.getProperty("mongodb.port"));
            username = properties.getProperty("mongodb.username");
            password = properties.getProperty("mongodb.password");
            databaseName = properties.getProperty("mongodb.database");
            connect();
        } catch (Exception e) {
            System.out.println("获取mongodb初始化配置失败");
            e.printStackTrace();
        }
    }

    /**
     * 连接到指定数据库
     * @throws IllegalArgumentException 如果数据库名称为空或null
     */
    private static void connect() {
//        MongoCredential credential = MongoCredential.createCredential(username, databaseName, password.toCharArray());
        MongoClientOptions options = MongoClientOptions.builder()
                .connectionsPerHost(20)
                .socketTimeout(300000)
                .maxWaitTime(300000)
                .socketKeepAlive(true)
                .serverSelectionTimeout(300000)
                .connectTimeout(300000)
                .sslEnabled(false)
                .build();
//        mongoClient = new MongoClient(new ServerAddress(host, port), Collections.singletonList(credential));
        mongoClient = new MongoClient(new ServerAddress(host, port), options);
        database = mongoClient.getDatabase(databaseName);
        System.out.println("Connect to database successfully!");
        System.out.println("MongoDatabase info is : " + database.getName());
    }

    /**
     * 获取指定名称的集合
     * @param collectionName 要获取的集合名称
     * @return 返回指定名称的集合
     */
    public static MongoCollection<Document> getCollection(String collectionName) {
        for (String existingCollection : database.listCollectionNames()) {
            if (existingCollection.equals(collectionName)) {
                return database.getCollection(collectionName); // 返回已存在的集合
            }
        }

        database.createCollection(collectionName);
        return database.getCollection(collectionName); // 返回新创建的集合
    }


    /**
     * 获取数据库中所有集合的名称
     * @return 返回包含所有集合名称的列表
     */
    public static List<String> listCollections() {
        List<String> collectionNames = new ArrayList<>();
        for (String name : database.listCollectionNames()) {
            collectionNames.add(name);
        }
        return collectionNames;
    }

    /**
     * 插入文档到MongoDB集合中
     * @param collection MongoDB集合
     * @param document 要插入的文档
     */
    public static void insertDocument(MongoCollection<Document> collection, Document document) {
        collection.insertOne(document);
        System.out.println("Document insert successfully!");
    }

    /**
     * 查询所有文档
     * @param collection MongoDB文档集合
     * @return documents 包含所有文档的列表
     */
    public static List<Document> queryAllDocuments(MongoCollection<Document> collection) {
        List<Document> documents = new ArrayList<>();
        FindIterable<Document> iterDoc = collection.find();
        for (Document document : iterDoc) {
            documents.add(document);
        }
        return documents;
    }

    /**
     * 查询文档
     * @param collection MongoDB文档集合
     * @param filter 过滤条件
     * @return 包含查询结果的文档列表
     */
    public static List<Document> findDocuments(MongoCollection<Document> collection, Bson filter) {
        List<Document> documents = new ArrayList<>();
        FindIterable<Document> result = collection.find(filter);
        // 遍历查询结果并输出
        for (Document document : result) {
            documents.add(document);
        }
        return documents;
    }


    /**
     * 更新文档
     * @param collection MongoDB文档集合
     * @param field 需要匹配的字段
     * @param value 字段匹配的值
     * @param updateField 需要更新的字段
     * @param updateValue 更新的值
     */
    public static void updateDocument(MongoCollection<Document> collection, String field, String value, String updateField, Object updateValue) {
        collection.updateMany(Filters.eq(field, value), Updates.set(updateField, updateValue));
        System.out.println("Document update successfully...");
    }

    /**
     * 删除指定集合中符合条件的文档
     * @param collection 要操作的MongoDB集合
     * @param field 字段名
     * @param value 字段值
     */
    public static void deleteDocument(MongoCollection<Document> collection, String field, String value) {
        collection.deleteOne(Filters.eq(field, value));
        System.out.println("Document delete successfully...");
    }

    /**
     * 删除指定名称的集合
     * @param collectionName 需要删除的集合名称
     */
    public static void dropCollection(String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        System.out.println("chose collection : " + collection.getNamespace());
        collection.drop();
        System.out.println("drop collection : " + collection.getNamespace());
        listCollections();
    }

    /**
     * 关闭数据库连接
     */
    public static void closeConnection() {
        mongoClient.close();
        System.out.println("Connection closed.");
    }

    /**
     * 通过日期范围查询条目
     * @param collection MongoDB集合
     * @param field 字段名称
     * @param startDateStr 开始日期字符串
     * @param endDateStr 结束日期字符串
     * @return 包含文档的列表
     * @throws ParseException 解析异常
     */
    public static List<Document> queryEntriesByDateRange(MongoCollection<Document> collection, String field, String startDateStr, String endDateStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = dateFormat.parse(startDateStr);
        Date endDate = dateFormat.parse(endDateStr);
        Document query = new Document(field, new Document("$gte", startDate).append("$lte", endDate));
        List<Document> documents = collection.find(query).into(new ArrayList<>());
        return documents;
    }


    /**
     * 通过字段和查询值查询文档条目
     * @param collection MongoDB文档集合
     * @param field 查询字段
     * @param queryValue 查询值
     * @return 包含查询结果的文档列表
     */
    public static List<Document> queryEntriesByField(MongoCollection<Document> collection, String field, String queryValue) {
        Document query = new Document(field, new Document("$eq", queryValue));
        List<Document> list = collection.find(query).into(new ArrayList<>());
        return list;
    }
}
