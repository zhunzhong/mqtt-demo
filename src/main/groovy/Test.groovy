/**
 * @author: zhunzhong
 * @date: 2023-01-17 15:52
 * @description: todo
 */

/**
 * @author: zhunzhong
 * @date: 2023-01-17 15:52
 * @description: todo
 */

import org.apache.commons.lang3.StringUtils

public class Test{
    public String run(String str) {
        //方法1
        System.out.println(StringUtils.strip("[1,2,3,4,5,6,2,3,5,1]", "[]"));

        //方法2
//        Class<?> aClass = Class.forName("org.apache.commons.lang3.StringUtils");
//        Object instance = aClass.newInstance();
//        Object strip = aClass.getDeclaredMethod("strip", String.class, String.class).invoke(instance, "[1,2,3,4,5,6,2,3,5,1]", "[]");
//        System.out.println(strip);

        return "call groovy run " + str;
    }
}