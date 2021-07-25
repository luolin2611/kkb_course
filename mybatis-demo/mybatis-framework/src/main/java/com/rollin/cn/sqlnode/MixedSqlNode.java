package com.rollin.cn.sqlnode;

import com.rollin.cn.sqlnode.iface.SqlNode;

import java.util.List;

/**
 * @author rollin
 * @date 2021-07-25 21:02:16
 */
public class MixedSqlNode implements SqlNode {
    private List<SqlNode> sqlNodes;

    public MixedSqlNode(List<SqlNode> contents) {
        this.sqlNodes = contents;
    }

    @Override
    public void apply(DynamicContext context) {

    }
}
