package com.mcw.sparkweb.task.parser.impl;

import com.mcw.sparkweb.task.parser.BaseParser;
import com.mcw.sparkweb.task.po.GerVaderSentimentPO;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class GerVaderSentimentParser implements BaseParser<GerVaderSentimentPO> {

    @Override
    public List<GerVaderSentimentPO> parse(NodeList nodeList) {
        List<GerVaderSentimentPO> list = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                GerVaderSentimentPO po = new GerVaderSentimentPO();
                po.setId(element.getAttribute("xmi:id"));
                po.setSofa(element.getAttribute("sofa"));
                po.setBegin(element.getAttribute("begin"));
                po.setEnd(element.getAttribute("end"));
                po.setSentiment(element.getAttribute("sentiment"));
                po.setSubjectivity(element.getAttribute("subjectivity"));
                po.setPos(element.getAttribute("pos"));
                po.setNeu(element.getAttribute("neu"));
                po.setNeg(element.getAttribute("neg"));
                list.add(po);
            }
        }
        return list;
    }
}
