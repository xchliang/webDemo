package com.liang.reflect;

import com.liang.vo.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 反射
 * 在阅读Class类文档时发现一个特点，以通过反射获得Method对象为例，一般会提供四种方法：
 * getMethod(parameterTypes)用来获取某个公有的方法的对象，
 * getMethods()获得该类所有公有的方法，
 * getDeclaredMethod(parameterTypes)获得该类某个方法，
 * getDeclaredMethods()获得该类所有方法。
 * 带有Declared修饰的方法可以反射到私有的方法，没有Declared修饰的只能用来反射公有的方法。
 *
 * 其他的Annotation、Field、Constructor也是如此。
 *
 */
public class ReflectDemo {

    public static void main(String[] args) {
        reflectNewInstance();
        reflectConstructorNewInstance();
        reflectMethod();
    }

    // 创建对象
    public static User reflectNewInstance() {
        try {
            System.out.println("反射newInstance");
            Class<?> userClass = Class.forName("com.liang.vo.User");
            User user = (User) userClass.newInstance();
            user.setName("浩克");
            user.setAddress("美国");
            user.setAge(20);
            System.out.println(user.toString());
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // 有参构造方法创建对象
    public static User reflectConstructorNewInstance() {
        try {
            System.out.println("反射有参构造方法创建对象");
            Class<?> classUser = Class.forName("com.liang.vo.User");
            Constructor<?> declaredConstructorUser = classUser.getDeclaredConstructor(String.class,Integer.class);
            //declaredConstructorUser.setAccessible(true);//private方法设置true，可外部调用
            Object objectUser = declaredConstructorUser.newInstance("钢铁侠",22);
            User user = (User)objectUser;
            System.out.println(user.toString());
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 反射方法
    // private方法可以设置method.setAccessible(true)后，外部调用
    public static void reflectMethod() {
        try {
            Class<?> classUser = Class.forName("com.liang.vo.User");
            Method[] declaredMethods = classUser.getDeclaredMethods();
            System.out.println("所有方法");
            for (Method m : declaredMethods) {
                System.out.println(m.getName());
                System.out.println("参数："+m.getParameters());
            }
            System.out.println("=========================");
            //有参方法
            String methodName="setName";
            String param="雷神";
            Method method = classUser.getDeclaredMethod(methodName, String.class);
            //method.setAccessible(true);
            Object objectUser = classUser.newInstance();
            String res = (String) method.invoke(objectUser,param);//方法调用
            System.out.println(methodName+"方法返回值："+res);//方法返回值
            System.out.println(((User)objectUser).toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
