package com.liang.design.factory.abstractFactory;

/**
 * 工厂实例：华为手机工厂
 * 生产华为手机及其配件
 */
public class HuaweiFactroy implements PhoneFactory {

    public IPhone createPhone() {
        System.out.println("HuaweiFactroy 生产手机 HuaweiPhone");
        IPhone huaweiPhone = new HuaweiPhone();
        return huaweiPhone;
    }

    /**
     * 生产huawei手机所需的屏幕
     */
    public IScreen createScreen() {
        System.out.println("HuaweiFactroy 生产屏幕 ScreenA");
        IScreen screenA = new ScreenA();
        return screenA;
    }

    /**
     * 生产huawei手机所需的电池
     */
    public IBattery createBattery() {
        System.out.println("HuaweiFactroy 生产电池 BatteryA");
        IBattery batteryA = new BatteryA();
        return batteryA;
    }

}