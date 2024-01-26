package com.mcw.sparkweb.task.parser.impl;

import com.mcw.sparkweb.task.parser.BaseParser;
import com.mcw.sparkweb.task.po.NamedEntityPO;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class NamedEntityParser implements BaseParser<NamedEntityPO> {

    @Override
    public List<NamedEntityPO> parse(NodeList nodeList) {
        List<NamedEntityPO> list = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                NamedEntityPO po = new NamedEntityPO();
                po.setId(element.getAttribute("xmi:id"));
                po.setSofa(element.getAttribute("sofa"));
                po.setBegin(element.getAttribute("begin"));
                po.setEnd(element.getAttribute("end"));
                po.setValue(element.getAttribute("value"));
                list.add(po);
            }
        }
        return list;
    }
}
