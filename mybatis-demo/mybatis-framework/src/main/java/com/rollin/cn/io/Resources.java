package com.rollin.cn.io;

import java.io.InputStream;

/**
 * @author rollin
 * @date 2021-07-24 16:18:57
 */
public class Resources {

    public static InputStream getRousourceAsStream(String location) {
        return Resources.class.getClassLoader().getResourceAsStream(location);
    }
}
