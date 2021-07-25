package com.rollin.cn.config;

import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rollin
 * @date 2021-07-24 16:20:59
 */
@Data
public class Configuration {
    private DataSource dataSource;
    private Map<String, MappedStatement> mappedStatements = new HashMap<>();

    public void addMappedStatement(String statementId, MappedStatement mappedStatement) {
        this.mappedStatements.put(statementId, mappedStatement);
    }

}
