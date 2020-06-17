package com.liang.design.adapter;

/**
 * 适配器模式（Adapter）：将一个类的接口转换成客户希望的另外一个接口，
 * 使得原本由于接口不兼容而不能一起工作的那些类能一起工作。
 * 适配器模式分为类结构型模式和对象结构型模式两种，前者类之间的耦合度比后者高，
 * 且要求程序员了解现有组件库中的相关组件的内部结构，所以应用相对较少些。
 *
 * 类适配器
 * 继承原有功能类，实现目标接口。使用原有功能类的实例方法获取结果，转换后，为新接口提供数据
 * @author xcl
 */
public class ClassAdapter extends Adaptee implements Target {
    @Override
    public String request(String str) {
        String s = super.doSomething(str);
        String res = s+" ok";
        return res;
    }
}
