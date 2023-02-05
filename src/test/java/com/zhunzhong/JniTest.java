package com.zhunzhong;

import org.junit.Test;

public class JniTest {

    @Test
    public void testLoadJni() {
        String path = System.getProperty("java.library.path");
        System.out.println(path);
    }
}
