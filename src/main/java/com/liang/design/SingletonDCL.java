package com.liang.design;

import java.io.Serializable;

/**
 * 单例模式
 * 双重校验锁 - 懒汉模式
 * 线程安全，
 * 如果要保证不被反射攻击，需改造构造方法
 */
public class SingletonDCL implements Serializable {
    //volatile修饰变量，避免创建对象时指令重排，其它线程得到的是只分配了内存而未完成创建过程的对象
    private static volatile SingletonDCL instance = null;

    private SingletonDCL() {
        //防止反射攻击
        synchronized (SingletonDCL.class) {
            if (instance != null) {
                throw new RuntimeException("单例模式正在被攻击");
            }
        }
    }

    public static SingletonDCL getInstance() {
        //双重检验
        if (instance == null) {
            synchronized (SingletonDCL.class) {
                if (instance == null) {
                    try {
                        // do anything
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    instance = new SingletonDCL();
                }
            }
        }
        return instance;
    }

    //防止反序列化
    //重写readResolve方法，反序列化时自动调用
    //如果该类实例不可被序列化，就不用担心反序列化问题
    protected Object readResolve() {
        System.out.println("调用了readResolve方法");
        //通过getInstance方法获取实例，避免未实例化时返回null
        return getInstance();
    }
}