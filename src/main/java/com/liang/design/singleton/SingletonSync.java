package com.liang.design.singleton;

/**
 * 单例模式
 * 懒汉模式-同步方法（或同步代码块）
 */
public class SingletonSync {
    private static SingletonSync instance = null;

    private SingletonSync() {
        //防止反射攻击
        synchronized (SingletonSync.class) {
            if (instance != null) {
                throw new RuntimeException("单例模式正在被攻击");
            }
        }
    }

    public static synchronized SingletonSync getInstance() {
        if (instance == null) {
            instance = new SingletonSync();
        }
        return instance;
    }

    /*
    //防止反序列化
    //如果该类实例不可被序列化，就不用担心反序列化问题
    protected Object readResolve() {
        System.out.println("调用了readResolve方法");
        return instance;
    }*/
}