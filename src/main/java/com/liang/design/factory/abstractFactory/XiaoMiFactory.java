package com.liang.design.factory.abstractFactory;

/**
 * 工厂实例：小米手机工厂
 * 生产小米手机及其配件
 */
public class XiaoMiFactory implements PhoneFactory {
    @Override
    public IPhone createPhone() {
        System.out.println("XiaoMiFactory 生产手机 XiaoMiPhone");
        IPhone xiaoMiPhone = new XiaoMiPhone();
        return xiaoMiPhone;
    }

    @Override
    public IScreen createScreen() {
        System.out.println("XiaoMiFactory 生产屏幕 ScreenB");
        IScreen screenB = new ScreenB();
        return screenB;
    }

    @Override
    public IBattery createBattery() {
        System.out.println("XiaoMiFactory 生产电池 BatteryB");
        IBattery batteryB = new BatteryB();
        return batteryB;
    }
}