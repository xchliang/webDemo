package com.liang.design.singleton;

/**
 * 单例模式
 * 饿汉模式
 */
public class Singleton {
    private static final Singleton instance = new Singleton();

    private Singleton() {
        //防止反射攻击
        synchronized (Singleton.class) {
            if (instance != null) {
                throw new RuntimeException("单例模式正在被攻击");
            }
        }
    }

    public static Singleton getInstance() {
        return instance;
    }

    /*
    //防止反序列化
    //如果实例不可序列化，就不用担心反序列化问题
    protected Object readResolve() {
        System.out.println("调用了readResolve方法");
        return instance;
    }*/

}