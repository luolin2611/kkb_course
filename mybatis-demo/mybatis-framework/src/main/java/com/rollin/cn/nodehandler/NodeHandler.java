package com.rollin.cn.nodehandler;

import com.rollin.cn.sqlnode.iface.SqlNode;
import org.dom4j.Element;

import java.util.List;

/**
 * @author rollin
 * @date 2021-07-25 22:05:30
 */
public interface NodeHandler {
    void handlerNode(Element nodeToHandler, List<SqlNode> contents);
}
