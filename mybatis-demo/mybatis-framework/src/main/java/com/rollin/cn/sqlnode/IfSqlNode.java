package com.rollin.cn.sqlnode;

import com.rollin.cn.sqlnode.iface.SqlNode;
import com.rollin.cn.utils.OgnlUtils;

/**
 * @author rollin
 * @date 2021-07-25 22:15:16
 */
public class IfSqlNode implements SqlNode {
    private String test;
    private SqlNode rootSqlNode;

    public IfSqlNode(String text, MixedSqlNode mixedSqlNode) {
        this.test = text;
        this.rootSqlNode = mixedSqlNode;
    }

    @Override
    public void apply(DynamicContext context) {
        Object parameterObject = context.getBindings().get("_parameter");
        boolean testValue = OgnlUtils.evaluateBoolean(test, parameterObject);
        if (testValue) {
            rootSqlNode.apply(context);
        }
    }
}
