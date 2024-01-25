package com.mcw.sparkweb.task;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMIReader {

    public static void main(String[] args) {
        try {
            // 读取 XMI 文件
            File file = new File("path/to/your/file.xmi");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            // 获取文档的根元素
            Element rootElement = doc.getDocumentElement();

            // 获取所有 "type5:Sentence" 元素
            NodeList sentenceNodes = rootElement.getElementsByTagName("type5:Sentence");

            // 遍历每个 "type5:Sentence" 元素
            for (int i = 0; i < sentenceNodes.getLength(); i++) {
                Node sentenceNode = sentenceNodes.item(i);

                if (sentenceNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element sentenceElement = (Element) sentenceNode;

                    // 提取属性值
                    String xmiId = sentenceElement.getAttribute("xmi:id");
                    String begin = sentenceElement.getAttribute("begin");
                    String end = sentenceElement.getAttribute("end");

                    // 打印提取的信息
                    System.out.println("xmi:id: " + xmiId);
                    System.out.println("begin: " + begin);
                    System.out.println("end: " + end);
                    System.out.println("------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
