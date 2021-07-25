package com.rollin.cn.sqlnode.iface;


import com.rollin.cn.sqlnode.DynamicContext;

public interface SqlNode {
    void apply(DynamicContext context);
}
