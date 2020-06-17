package com.liang.design.adapter;

/**
 * 接口适配器：抽象类实现目标接口，对接口方法提供空方法体或默认值。
 * 使用：业务实现类继承适配器，只重写所需的方法，不需要实现所有方法。
 *   接口方法过多时，接口实现类需要实现所有方法，用不到的方法即使只提供空方法体，也会使实现类代码臃肿。
 *   接口适配器封装为抽线类，提供目标接口方法的默认实现。子类不需要再实现无用的方法
 * 优点：避免接口实现类实现无用的方法，避免代码臃肿。
 * 缺点：适配器子类使用了继承，不能再继承其它类
 * @author xcl
 */
public abstract class InterfaceAdapter implements TargetMore {
    @Override
    public String requestA(String str) {
        return null;
    }

    @Override
    public String requestB(String str) {
        return null;
    }

    @Override
    public String requestC(String str) {
        return null;
    }

    @Override
    public String requestD(String str) {
        return null;
    }
}
