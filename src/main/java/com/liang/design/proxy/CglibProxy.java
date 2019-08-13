package com.liang.design.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Cglib动态代理
 * 1、需要jar包：cglib, asm
 * 2、目标对象不需要实现接口
 * 3、目标对象类必须有无参构造方法，否则抛出异常：Superclass has no null constructors but no arguments were given
 * 4、
 */
public class CglibProxy implements MethodInterceptor {
    private Object target;
    public CglibProxy(Object target) {
        this.target=target;
    }

    //获取代理对象
    public static Object getProxy(Object target){
        CglibProxy in = new CglibProxy(target);
        //1.工具类
        Enhancer en = new Enhancer();
        //2、设置父类，cglib是以目标对象类作为父类，创建子类
        en.setSuperclass(target.getClass());
        //3、设置回调
        en.setCallback(in);
        //4、创建代理对象
        return en.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        //调用目标对象方法前处理
        System.out.println("目标对象方法执行前。。。");
        //调用目标对象方法，相当于反射效率不高
        Object invoke = method.invoke(target, objects);
        //调用目标对象方法后处理
        System.out.println("目标对象方法执行后。。。");
        return invoke;
    }

    public static void main(String[] args) {
        Dog dog = new Dog("haha");
        Dog proxy = (Dog)CglibProxy.getProxy(dog);
        proxy.run();

    }
}
