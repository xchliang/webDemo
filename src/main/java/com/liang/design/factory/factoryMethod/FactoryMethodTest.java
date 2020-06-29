package com.liang.design.factory.factoryMethod;

/**
 * 工厂方法模式
 * 抽象工厂：定义一个创建产品的方法
 * 工厂实例：每个工厂实例创建一种产品，不同工厂创建不同产品
 */
public class FactoryMethodTest {
    public static void main(String[] args) {
        //手机工厂
        PhoneFactory[] phoneFactoryArr = new PhoneFactory[]{new HuaweiFactory(), new XiaomiFacory()};
        for (PhoneFactory phoneFactory : phoneFactoryArr) {
            IPhone phone = phoneFactory.createPhone();//生产手机
            phone.call("12312312312");
        }
    }
}
