package com.rollin.cn.sqlsource.iface;

import com.rollin.cn.sqlsource.BoundSql;

/**
 * @author rollin
 * @date 2021-07-25 18:41:32
 */
public interface SqlSource {
    BoundSql getBoundSql(Object paramObject);
}
