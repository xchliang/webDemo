package com.liang.design.adapter;

public class AdapterTest {

    public static void main(String[] args) {
        //对象适配器
        System.out.println("对象适配器");
        Target target = new ObjectAdapter(new Adaptee());
        String test = target.request("ObjectAdapter test! ");
        System.out.println(test);

        System.out.println("类适配器");
        //类适配器，耦合度比对象适配器高，使用较少
        Target classtarget = new ClassAdapter();
        String classtargetResult = classtarget.request("test");
        System.out.println(classtargetResult);

        System.out.println("接口适配器");
        //接口适配器，实现类只实现了所需方法
        TargetService targetService = new TargetService();
        String s = targetService.requestA("TargetService test! ");
        System.out.println(s);
    }
}
