package com.rollin.cn.config;

import org.dom4j.Element;

import java.util.List;

/**
 * @author rollin
 * @date 2021-07-25 18:21:27
 */
public class XMLMapperBuilder {
    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }


    public void parse(Element rootElement) {
        String namespace = rootElement.attributeValue("namespace");
        List<Element> selectElements = rootElement.elements();
        selectElements.forEach(selectElement -> {
            parseStatement(namespace, selectElement);
        });
    }

    private void parseStatement(String namespace, Element selectElement) {
        XMLStatementBuilder xmlStatementBuilder = new XMLStatementBuilder(configuration);
        xmlStatementBuilder.parseStatement(namespace, selectElement);
    }
}
