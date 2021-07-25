package com.rollin.cn.config;

import com.rollin.cn.nodehandler.NodeHandler;
import com.rollin.cn.sqlnode.IfSqlNode;
import com.rollin.cn.sqlnode.MixedSqlNode;
import com.rollin.cn.sqlnode.StaticTextSqlNode;
import com.rollin.cn.sqlnode.TextSqlNode;
import com.rollin.cn.sqlnode.iface.SqlNode;
import com.rollin.cn.sqlsource.DynamicSqlSource;
import com.rollin.cn.sqlsource.RawSqlSource;
import com.rollin.cn.sqlsource.iface.SqlSource;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rollin
 * @date 2021-07-25 18:29:36
 */
public class XMLStatementBuilder {
    private Configuration configuration;
    private boolean isDynamic = false;
    private Map<String, NodeHandler> nodeHandlerMap = new HashMap<>();

    public XMLStatementBuilder(Configuration configuration) {
        this.configuration = configuration;
        initHandlerMap();
    }

    private void initHandlerMap() {
        nodeHandlerMap.put("if", new IfNodeHandler());
    }

    public void parseStatement(String namespace, Element selectElement) {
        String statementId = selectElement.attributeValue("id");
        statementId = namespace + "." + statementId;

        String parameterType = selectElement.attributeValue("parameterType");
        Class<?> parameterClass = resolveType(parameterType);


        String resultType = selectElement.attributeValue("resultType");
        Class<?> resultClass = resolveType(resultType);

        String statementType = selectElement.attributeValue("statementType");
        statementType = StringUtils.isEmpty(statementType) ? "prepared" : statementType;

        SqlSource sqlSource = createSqlSource(selectElement);

        MappedStatement mappedStatement = new MappedStatement(statementId, parameterClass, resultClass, statementType, sqlSource);
        configuration.addMappedStatement(statementId, mappedStatement);
    }

    private SqlSource createSqlSource(Element selectElement) {
        MixedSqlNode rootSqlNode = parseDynamicTags(selectElement);
        SqlSource sqlSource = null;
        if (isDynamic) {
            sqlSource = new DynamicSqlSource(rootSqlNode);
        } else {
            sqlSource = new RawSqlSource(rootSqlNode);
        }
        return sqlSource;
    }

    private MixedSqlNode parseDynamicTags(Element selectElement) {
        List<SqlNode> contents = new ArrayList<>();
        int count = selectElement.nodeCount();
        for (int i = 0; i < count; i++) {
            Node node = selectElement.node(i);
            if (node instanceof Text) {
                String sqlText = node.getText().trim();
                if (StringUtils.isEmpty(sqlText)) {
                    continue;
                }
                TextSqlNode textSqlNode = new TextSqlNode(sqlText);
                if (textSqlNode.isDynamic()) {
                    isDynamic = true;
                    contents.add(textSqlNode);
                } else {
                    contents.add(new StaticTextSqlNode(sqlText));
                }
            } else if (node instanceof Element) {
                Element nodeToHandler = (Element) node;
                String nodeName = nodeToHandler.getName();
                NodeHandler nodeHandler = nodeHandlerMap.get(nodeName);
                nodeHandler.handlerNode(nodeToHandler, contents);
                isDynamic = true;
            }
        }

        return new MixedSqlNode(contents);
    }

    private Class<?> resolveType(String parameterType) {
        try {
            Class<?> classObj = Class.forName(parameterType);
            return classObj;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    class IfNodeHandler implements NodeHandler {
        @Override
        public void handlerNode(Element nodeToHandler, List<SqlNode> contents) {
            String text = nodeToHandler.attributeValue("test");

            MixedSqlNode mixedSqlNode = parseDynamicTags(nodeToHandler);

            contents.add(new IfSqlNode(text, mixedSqlNode));
        }
    }
}
