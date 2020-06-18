package com.liang.design.prototype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *原型模式
 * 用一个已经创建的实例作为原型，通过复制该原型对象来创建一个和原型相同或相似的新对象
 * 由于 Java 提供了对象的 clone() 方法，所以用 Java 实现原型模式很简单。
 *
 *  * java的clone是浅拷贝，只拷贝基本数据类型的变量值。
 *  * 如果对象内部有引用类型成员变量，引用了其它对象，拷贝时不会拷贝引用对象，引用变量还是指向原来的对象
 *
 *
 * @author xcl
 */
public class PrototypeTest {
    public static void main(String[] args) {
        try {
            Shop shop = new Shop("seven-eleven","7-23");
            shop.setAddress("北京");
            Employee employee = new Employee("张三丰",25);
            shop.setManager(employee);
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(mapper.writeValueAsString(shop));
            //克隆
            Shop clone = shop.clone();
            System.out.println(mapper.writeValueAsString(clone));
            System.out.println("shop==clone " + (shop == clone));//false 不是同一个对象
            //true 浅拷贝，只拷贝了引用变量，未拷贝引用指向的对象
            //false 深拷贝，拷贝了引用指向的对象
            System.out.println("shop.getManager() == clone.getManager() "
                    + (shop.getManager() == clone.getManager()));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

