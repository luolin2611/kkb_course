package com.rollin.cn.sqlnode;

import com.rollin.cn.sqlnode.iface.SqlNode;

/**
 * @author rollin
 * @date 2021-07-25 22:01:40
 */
public class StaticTextSqlNode implements SqlNode {
    private String sqlText;

    public StaticTextSqlNode(String sqlText) {
        this.sqlText = sqlText;
    }

    @Override
    public void apply(DynamicContext context) {

    }
}
