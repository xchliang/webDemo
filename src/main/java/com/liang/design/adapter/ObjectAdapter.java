package com.liang.design.adapter;

/**
 * 对象适配器
 * 适配器实现目标接口，并持有现有功能类的实例。
 * 通过现有功能类实例方法获取接口，再进行转换，为目标接口提供数据
 *
 * @author xcl
 */
public class ObjectAdapter implements Target {
    public Adaptee adaptee;//适配器持有现有组件实例

    public ObjectAdapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public String request(String str) {
        String s = adaptee.doSomething(str);//调用现有组件实例方法
        String res = s+" ok";//结果转换
        return res;
    }
}
