package com.liang.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {
    private static int n = 1;
    private static volatile int vola_m = 1;
    private static AtomicInteger atomic_int = new AtomicInteger(1);

    public static void main(String[] args) {
//        test1();
//        test2();
        test2_1();
//        test3();
    }

    //程序不停止，表示变量n不能保证在线程间的可见性
    public static void test1() {
        new Thread() {
            @Override
            public void run() {
                /*  1、线程启动，从主内存拷贝变量副本到工作内存；
                    2、线程运行，使用工作内中的值；
                    3、其它线程修改变量值，即便已刷新到主内存，当前线程工作内存不会重新加载变量值，程序一直执行；
                    4、System.out.println()内有同步代码块，会从主内存重新加载变量值，程序就会停止
                 */
                while (n == 1) {
                    //如果调用打印方法，循环就能停止，println内有同步代码段，导致变量从主内存重新加载
                    //System.out.println("运行中。。。");
                }
                System.out.println("结束");
            }
        }.start();
        try {
            //等待线程启动运行
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //修改变量值
        n = 2;
        System.out.println(n);
    }

    //使用volatile，程序停止，变量在线程间可见
    public static void test2() {
        new Thread() {
            @Override
            public void run() {
                /* volatile保证变量在线程间的可见性
                   某线程修改变量值，其它线程可见，程序停止
                 */
                while (vola_m == 1) {
                }
                System.out.println("结束");
            }
        }.start();
        try {
            //等待线程启动运行
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //修改变量值后，执行store指令，将新值刷新到主内存
        vola_m = 2;
        System.out.println(vola_m);
    }

    //使用volatile，变量自增，不是原子性
    public static void test2_1() {
        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        //等待线程启动运行
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i=0;i<10000;i++)
                        vola_m++;
                    System.out.println(vola_m);
                }
            }.start();
        }
    }

    //程序停止，表示变量在线程间可见
    public static void test3() {
        new Thread() {
            @Override
            public void run() {
                /*
                 */
                while (atomic_int.get() == 1) {
                }
                System.out.println("结束");
            }
        }.start();
        try {
            //等待线程启动运行
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //修改变量值
        atomic_int.set(2);
        System.out.println(atomic_int.get());
    }

    public static void test4() {
        new Thread() {
            @Override
            public void run() {
                /*
                 */
                while (atomic_int.get() == 1) {
                }
                System.out.println("结束");
            }
        }.start();
        try {
            //等待线程启动运行
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //修改变量值
        atomic_int.set(2);
        System.out.println(atomic_int.get());
    }


}
