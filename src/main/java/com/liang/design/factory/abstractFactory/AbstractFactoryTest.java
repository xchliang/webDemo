package com.liang.design.factory.abstractFactory;

/**
 * 抽象工厂模式
 * 抽象工厂：定义创建多种产品的方法
 * 实例工厂：创建一组配套的产品
 */
public class AbstractFactoryTest {
    public static void main(String[] args) {
        //手机工厂
        PhoneFactory[] phoneFactoryArr = new PhoneFactory[]{new HuaweiFactroy(), new XiaoMiFactory()};
        for (PhoneFactory phoneFactory : phoneFactoryArr) {
            System.out.println("==========工厂创建产品==========开始==");
            IPhone phone = phoneFactory.createPhone();//生产手机
            IBattery battery = phoneFactory.createBattery();//生产电池
            IScreen screen = phoneFactory.createScreen();//生产屏幕
            phone.call("12312312312");
            battery.charge();
            screen.show();
            System.out.println("==========工厂创建产品==========结束==");
        }

    }
}
