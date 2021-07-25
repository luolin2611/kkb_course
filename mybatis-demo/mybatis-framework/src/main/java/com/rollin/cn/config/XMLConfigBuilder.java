package com.rollin.cn.config;

import com.rollin.cn.io.Resources;
import com.rollin.cn.utils.DocumentUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author rollin
 * @date 2021-07-24 16:20:20
 */
public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    public Configuration parse(InputStream inputStream) {
        Document document = DocumentUtils.readDocument(inputStream);
        Element element = document.getRootElement();
        parseConfiguration(element);
        return configuration;
    }

    private void parseConfiguration(Element element) {
        Element environmentsElement = element.element("environments");
        parseEnvironmentsElement(environmentsElement);
        Element mapersElement = element.element("mappers");
        parseMappersElement(mapersElement);
    }

    private void parseMappersElement(Element mapersElement) {
        List<Element> mapperElement = mapersElement.elements();
        mapperElement.forEach(mapper -> {
            parseMapper(mapper);
        });
    }

    private void parseMapper(Element mapper) {
        String resource = mapper.attributeValue("resource");
        InputStream inputStream = Resources.getRousourceAsStream(resource);
        Document document = DocumentUtils.readDocument(inputStream);
        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
        xmlMapperBuilder.parse(document.getRootElement());

    }

    private void parseEnvironmentsElement(Element environmentsElement) {
        String defaultDev = environmentsElement.attributeValue("default");
        if (StringUtils.isEmpty(defaultDev)) {
            return;
        }
        List<Element> elements = environmentsElement.elements();
        elements.forEach(element -> {
            String id = element.attributeValue("id");
            if (defaultDev.equals(id)) {
                parseElement(element.element("dataSource"));
            }
        });
    }

    private void parseElement(Element dataSourceElement) {
        String type = dataSourceElement.attributeValue("type");
        if ("DBCP".equals(type)) {
            List<Element> elements = dataSourceElement.elements();
            Properties properties = new Properties();
            elements.forEach(element -> {
                String name = element.attributeValue("name");
                String value = element.attributeValue("value");
                properties.put(name, value);
            });
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(properties.getProperty("driver"));
            dataSource.setUrl(properties.getProperty("url"));
            dataSource.setUsername(properties.getProperty("username"));
            dataSource.setPassword(properties.getProperty("password"));
            configuration.setDataSource(dataSource);
        }
    }
}
