package com.rollin.cn.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * @author rollin
 * @date 2021-07-24 16:25:24
 */
public class DocumentUtils {
    public static Document readDocument(InputStream inputStream) {
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(inputStream);
            return document;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
