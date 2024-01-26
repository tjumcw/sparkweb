package com.mcw.sparkweb.task.parser.impl;

import com.mcw.sparkweb.task.parser.BaseParser;
import com.mcw.sparkweb.task.po.MorphologicalFeaturesPO;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class MorphologicalFeaturesParser implements BaseParser<MorphologicalFeaturesPO> {

    @Override
    public List<MorphologicalFeaturesPO> parse(NodeList nodeList) {
        List<MorphologicalFeaturesPO> list = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                MorphologicalFeaturesPO po = new MorphologicalFeaturesPO();
                po.setId(element.getAttribute("xmi:id"));
                po.setSofa(element.getAttribute("sofa"));
                po.setBegin(element.getAttribute("begin"));
                po.setEnd(element.getAttribute("end"));
                po.setGender(element.getAttribute("gender"));
                po.setNumber(element.getAttribute("number"));
                po.setCase(element.getAttribute("case")); // 注意这里使用 "case"
                po.setValue(element.getAttribute("value"));
                po.setPerson(element.getAttribute("person"));
                po.setPronType(element.getAttribute("pronType"));
                po.setDefiniteness(element.getAttribute("definiteness"));
                po.setMood(element.getAttribute("mood"));
                po.setDegree(element.getAttribute("degree"));
                po.setVerbForm(element.getAttribute("verbForm"));
                list.add(po);
            }
        }
        return list;
    }
}
