package com.liang.design.factory.abstractFactory;

/**
 * 抽象工厂
 * 生产手机的抽象工厂
 * 包含生产手机，及屏幕、电池等配件
 */
public interface PhoneFactory {

    /**
     * 生产手机
     */
    public IPhone createPhone();

    /**
     * 生产屏幕
     */
    public IScreen createScreen();

    /**
     * 生产电池
     */
    public IBattery createBattery();

}