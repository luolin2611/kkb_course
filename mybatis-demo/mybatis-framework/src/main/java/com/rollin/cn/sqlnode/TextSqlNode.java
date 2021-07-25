package com.rollin.cn.sqlnode;

import com.rollin.cn.sqlnode.iface.SqlNode;
import com.rollin.cn.utils.GenericTokenParser;
import com.rollin.cn.utils.OgnlUtils;
import com.rollin.cn.utils.SimpleTypeRegistry;
import com.rollin.cn.utils.TokenHandler;

/**
 * 处理文本的SQL语句(非<if></if>部分)
 *
 * @author rollin
 * @date 2021-07-25 21:37:12
 */
public class TextSqlNode implements SqlNode {
    private String sqlText;

    public TextSqlNode(String sqlText) {
        this.sqlText = sqlText;
    }

    @Override
    public void apply(DynamicContext context) {
        // 处理 ${}
        GenericTokenParser genericTokenParser = new GenericTokenParser("${", "}", new BindingTokenParser(context));
        String sql = genericTokenParser.parse(sqlText);
        context.appendSql(sql);
    }

    /**
     * 含有 ${} 的是动态的 SQL --> Statement
     *
     * @return
     */
    public boolean isDynamic() {
        if (sqlText.indexOf("${") > -1) {
            return true;
        }
        return false;
    }

    private static class BindingTokenParser implements TokenHandler {
        private DynamicContext context;

        public BindingTokenParser(DynamicContext context) {
            this.context = context;
        }

        /**
         * expression：比如说${username}，那么expression就是username username也就是Ognl表达式
         */
        @Override
        public String handleToken(String expression) {
            Object paramObject = context.getBindings().get("_parameter");
            if (paramObject == null) {
                // context.getBindings().put("value", null);
                return "";
            } else if (SimpleTypeRegistry.isSimpleType(paramObject.getClass())) {
                // context.getBindings().put("value", paramObject);
                return String.valueOf(paramObject);
            }

            // 使用Ognl api去获取相应的值
            Object value = OgnlUtils.getValue(expression, context.getBindings());
            String srtValue = value == null ? "" : String.valueOf(value);
            return srtValue;
        }
    }
}
