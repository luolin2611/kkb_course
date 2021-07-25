package com.rollin.cn.sqlnode;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 放置拼接完整的sql 以及参数集合
 *
 * @author rollin
 * @date 2021-07-25 21:02:02
 */
@Data
public class DynamicContext {
    private StringBuffer sb;
    private Map<String, Object> bindings = new HashMap<>();

    public DynamicContext(Object parameter) {
        bindings.put("_parameter", parameter);
    }

    public String getSql() {
        return sb.toString();
    }

    public void appendSql(String sql) {
        sb.append(sql);
        sb.append(" ");
    }
}
