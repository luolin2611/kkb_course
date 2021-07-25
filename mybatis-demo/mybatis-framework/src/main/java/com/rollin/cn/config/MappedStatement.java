package com.rollin.cn.config;

import com.rollin.cn.sqlsource.iface.SqlSource;
import lombok.AllArgsConstructor;

/**
 * @author rollin
 * @date 2021-07-25 21:15:04
 */
@AllArgsConstructor
public class MappedStatement {
    private String id;
    private Class<?> parameterType;
    private Class<?> resultType;
    private String statementType;
    private SqlSource sqlSource;
}
