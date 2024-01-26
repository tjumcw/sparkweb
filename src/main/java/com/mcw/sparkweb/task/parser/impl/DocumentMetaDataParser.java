package com.mcw.sparkweb.task.parser.impl;

import com.mcw.sparkweb.task.parser.BaseParser;
import com.mcw.sparkweb.task.po.DocumentMetaDataPO;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class DocumentMetaDataParser implements BaseParser<DocumentMetaDataPO> {

    @Override
    public List<DocumentMetaDataPO> parse(NodeList nodeList) {
        List<DocumentMetaDataPO> list = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                DocumentMetaDataPO po = new DocumentMetaDataPO();
                po.setId(element.getAttribute("xmi:id"));
                po.setSofa(element.getAttribute("sofa"));
                po.setBegin(element.getAttribute("begin"));
                po.setEnd(element.getAttribute("end"));
                po.setDocumentId(element.getAttribute("documentId"));
                po.setDocumentUri(element.getAttribute("documentUri"));
                po.setDocumentBaseUri(element.getAttribute("documentBaseUri"));
                po.setIsLastSegment(element.getAttribute("isLastSegment"));
                list.add(po);
            }
        }
        return list;
    }
}
