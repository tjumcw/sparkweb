package com.mcw.sparkweb.task.parser.impl;

import com.mcw.sparkweb.task.parser.BaseParser;
import com.mcw.sparkweb.task.po.POSPO;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class POSParser implements BaseParser<POSPO> {

    @Override
    public List<POSPO> parse(NodeList nodeList) {
        List<POSPO> list = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node sentenceNode = nodeList.item(i);

            if (sentenceNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) sentenceNode;
                POSPO po = new POSPO();
                po.setId(element.getAttribute("xmi:id"));
                po.setSofa(element.getAttribute("sofa"));
                po.setBegin(element.getAttribute("begin"));
                po.setEnd(element.getAttribute("end"));
                po.setPosValue(element.getAttribute("PosValue"));
                po.setCoarseValue(element.getAttribute("coarseValue"));
                list.add(po);
            }
        }
        return list;
    }
}
