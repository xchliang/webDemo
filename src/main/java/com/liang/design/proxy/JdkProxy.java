package com.liang.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author xcl
 * JDK动态代理
 * 前提：业务类实现了接口
 * 1、实现JDK的InvocationHandler接口，自定义handler
 * 2、在接口实现方法invoke中调用目标对象方法
 * 3、用handler对象作参数，调用JDK自带的Proxy类的静态方法创建代理对象并返回
 */
public class JdkProxy implements InvocationHandler {
    //代理目标对象
    private Object target;
    public JdkProxy(Object target){
        this.target=target;
    }

    public static Object getProxy(Object target) {
        JdkProxy h = new JdkProxy(target);
        //创建代理对象
        Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), h);
        return proxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //调用目标对象方法前处理
        System.out.println("目标对象方法执行前。。。");
        //调用目标对象方法
        Object invoke = method.invoke(target, args);
        //调用目标对象方法后处理
        System.out.println("目标对象方法执行后。。。");
        return invoke;
    }

    public static void main(String[] args) {
        Bird bird = new Bird("Swan");
        //用接口类接收对象，不可用实现类接收
        IBird instance = (IBird)JdkProxy.getProxy(bird);
        instance.fly();
    }

}
