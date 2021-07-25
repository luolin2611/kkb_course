package com.rollin.cn.sqlsource;

import com.rollin.cn.sqlnode.iface.SqlNode;
import com.rollin.cn.sqlsource.iface.SqlSource;
import lombok.AllArgsConstructor;

/**
 * @author rollin
 * @date 2021-07-25 21:26:15
 */
@AllArgsConstructor
public class RawSqlSource implements SqlSource {
    private SqlNode rootSqlNode;

    @Override
    public BoundSql getBoundSql(Object paramObject) {
        return null;
    }
}
