package com.rollin.cn.dao;

import com.rollin.cn.config.Configuration;
import com.rollin.cn.config.XMLConfigBuilder;
import com.rollin.cn.io.Resources;
import org.junit.Test;

import java.io.InputStream;

/**
 * @author rollin
 * @date 2021-07-24 16:16:45
 */
public class UserDao {
    @Test
    public void getDataResource() {
        String location = "mybatis-config.xml";

        InputStream inputStream = Resources.getRousourceAsStream(location);

        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parse(inputStream);

        System.out.println(configuration);

    }
}
