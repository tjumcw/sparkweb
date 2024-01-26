package com.mcw.sparkweb.task.parser.impl;

import com.mcw.sparkweb.task.parser.BaseParser;
import com.mcw.sparkweb.task.po.DependencyPO;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class DependencyParser implements BaseParser<DependencyPO> {

    @Override
    public List<DependencyPO> parse(NodeList nodeList) {
        List<DependencyPO> list = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                DependencyPO po = new DependencyPO();
                po.setId(element.getAttribute("xmi:id"));
                po.setSofa(element.getAttribute("sofa"));
                po.setBegin(element.getAttribute("begin"));
                po.setEnd(element.getAttribute("end"));
                po.setGovernor(element.getAttribute("Governor")); // 注意这里使用 "Governor"
                po.setDependent(element.getAttribute("Dependent")); // 注意这里使用 "Dependent"
                po.setDependencyType(element.getAttribute("DependencyType")); // 注意这里使用 "DependencyType"
                po.setFlavor(element.getAttribute("flavor"));
                list.add(po);
            }
        }
        return list;
    }
}
