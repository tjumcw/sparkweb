package com.mcw.sparkweb.task.parser;

import com.mcw.sparkweb.task.po.BasePO;
import org.w3c.dom.NodeList;

import java.util.List;

/**
 * @author miaochangwei1
 * @Package : com.mcw.sparkweb.task.Parser
 * @Description : TODO
 * @Create on : 2024/1/26 17:34
 **/
public interface BaseParser<T extends BasePO> {

     List<T> parse(NodeList nodeList);
}
