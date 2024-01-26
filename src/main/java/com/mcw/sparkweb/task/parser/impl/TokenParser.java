package com.mcw.sparkweb.task.parser.impl;

import com.mcw.sparkweb.task.parser.BaseParser;
import com.mcw.sparkweb.task.po.TokenPO;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class TokenParser implements BaseParser<TokenPO> {

    @Override
    public List<TokenPO> parse(NodeList nodeList) {
        List<TokenPO> list = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                TokenPO po = new TokenPO();
                po.setId(element.getAttribute("xmi:id"));
                po.setSofa(element.getAttribute("sofa"));
                po.setBegin(element.getAttribute("begin"));
                po.setEnd(element.getAttribute("end"));
                po.setParent(element.getAttribute("parent"));
                po.setLemma(element.getAttribute("lemma"));
                po.setPos(element.getAttribute("pos"));
                po.setMorph(element.getAttribute("morph"));
                po.setOrder(element.getAttribute("order"));
                list.add(po);
            }
        }
        return list;
    }
}
