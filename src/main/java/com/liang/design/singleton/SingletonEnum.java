package com.liang.design.singleton;

/**
 * 单例模式
 * 枚举模式-饿汉模式
 * 使用枚举除了线程安全和防止反射调用构造器之外，还提供了自动序列化机制，防止反序列化的时候创建新的对象。
 * 因此，《Effective Java》作者推荐使用的方法。
 */
public enum SingletonEnum {
    INSTANCE;

    public void doSomething(){
        System.out.println(" do something...");
    }
}