package com.liang.design;

import com.liang.util.SerializeUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SingletonTest {

    public static void main(String[] args) {
        testDCL();
        //reflectTest("com.liang.design.Singleton");
        //静态内部类方式，反射攻击，不安全
        //reflectTest("com.liang.design.SingletonInner");
        //enumTest();
        // reflectTest("com.liang.design.SingletonEnum");
        //reflectTest("com.liang.design.SingletonInner");
        // System.out.println(SingletonInner.getInstance().hashCode());
        // seri();
        seri2();
    }

    private static void testDCL() {
        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    System.out.println(SingletonDCL.getInstance().hashCode());
                }
            }.start();

        }
    }

    /**
     * 反射攻击，调用私有构造方法创建实例
     */
    private static void reflectTest(final String className) {
        for (int i = 0; i < 3; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        Class<?> cla = Class.forName(className);
                        Constructor<?> con = cla.getDeclaredConstructor();
                        con.setAccessible(true);//true 私有构造方法可被外部访问
                        Object o = con.newInstance();
                        System.out.println(o.hashCode());
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }
    }

    private static void enumTest() {
        SingletonEnum instance = SingletonEnum.INSTANCE;
        System.out.println(instance.hashCode());
    }

    /**
     * 反序列化不能保证单例
     * 需重写readResolve方法
     */
    private static void seri() {
        final byte[] b = SerializeUtil.serialize(SingletonDCL.getInstance());
        for (int i = 0; i < 100; i++) {
            new Thread() {
                @Override
                public void run() {
                    SingletonDCL dcl = (SingletonDCL) SerializeUtil.unserialize(b);
                    System.out.println(dcl.hashCode());
                }
            }.start();
        }
    }

    /**
     * 反序列化不能保证单例
     */
    private static void seri2() {
        SerializeUtil.serialize(SingletonDCL.getInstance(), "E:\\a.txt");
        for (int i = 0; i < 3; i++) {
            new Thread() {
                @Override
                public void run() {
                    Object o = SerializeUtil.unserializeFile("E:\\a.txt");
                    System.out.println(o.hashCode());
                }
            }.start();
        }
    }

}
