package com.liang.design.factory.factoryMethod;

/**
 * 工厂实例：华为手机工厂
 */
public class HuaweiFactory implements PhoneFactory {

    public IPhone createPhone() {
        System.out.println("HuaweiFactory 生产 HuaweiPhone");
        IPhone huaweiPhone = new HuaweiPhone();
        return huaweiPhone;
    }

}