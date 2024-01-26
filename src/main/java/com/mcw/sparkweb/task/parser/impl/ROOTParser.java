package com.mcw.sparkweb.task.parser.impl;

import com.mcw.sparkweb.task.parser.BaseParser;
import com.mcw.sparkweb.task.po.ROOTPO;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class ROOTParser implements BaseParser<ROOTPO> {

    @Override
    public List<ROOTPO> parse(NodeList nodeList) {
        List<ROOTPO> list = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                ROOTPO po = new ROOTPO();
                po.setId(element.getAttribute("xmi:id"));
                po.setSofa(element.getAttribute("sofa"));
                po.setBegin(element.getAttribute("begin"));
                po.setEnd(element.getAttribute("end"));
                po.setGovernor(element.getAttribute("Governor"));
                po.setDependent(element.getAttribute("Dependent"));
                po.setDependencyType(element.getAttribute("DependencyType"));
                po.setFlavor(element.getAttribute("flavor"));
                list.add(po);
            }
        }
        return list;
    }
}
