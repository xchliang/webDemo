package com.liang.design.adapter;

/**
 * 业务实现类：继承接口适配器，只重写所需的方法，不需要实现所有方法。
 * @author xcl
 */
public class TargetService extends InterfaceAdapter {
    @Override
    public String requestA(String str) {
        System.out.println("========ServiceImpl.requestA =======");
        return str+str;
    }

}
