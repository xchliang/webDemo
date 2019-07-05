package com.liang.design;

/**
 * 单例模式
 * 静态内部类-懒汉模式
 *
 * 外部类被加载时，内部类不会被加载。只有内部类被使用时才会加载
 * 优点：避免了线程不安全，延迟加载，效率高。
 * 缺点：不能防止反射攻击，需要改写构造方法
 *
 */
public class SingletonInner {

    private static class SingletonInstance {
        private static SingletonInner INSTANCE = new SingletonInner();
    }

    private SingletonInner() {
        //防止反射攻击
        synchronized (Singleton.class) {
            if (SingletonInstance.INSTANCE != null) {
                throw new RuntimeException("单例模式正在被攻击");
            }
        }
    }

    public static SingletonInner getInstance() {
        return SingletonInstance.INSTANCE;
    }
    /*
    //防止反序列化
    //如果该类实例不可被序列化，就不用担心反序列化问题
    protected Object readResolve() {
        System.out.println("调用了readResolve方法");
        //通过getInstance方法获取实例，避免未实例化时返回null
        return getInstance();
    }*/
}