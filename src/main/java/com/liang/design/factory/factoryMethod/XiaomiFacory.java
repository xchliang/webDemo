package com.liang.design.factory.factoryMethod;

/**
 * 工厂实例：小米手机工厂
 */
public class XiaomiFacory implements PhoneFactory {
    public IPhone createPhone() {
        System.out.println("XiaomiFacory 生产 XiaomiPhone");
        IPhone xiaomiPhone = new XiaomiPhone();
        return xiaomiPhone;
    }
}