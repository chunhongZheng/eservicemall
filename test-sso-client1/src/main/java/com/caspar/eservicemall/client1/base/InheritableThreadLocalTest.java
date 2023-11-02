package com.caspar.eservicemall.client1.base;

import io.micrometer.observation.Observation;

/*
父子线程共享数据测试
在主线程的InheritableThreadLocal实例设置值，在⼦线程中就可以拿到了
* **/
public class InheritableThreadLocalTest {
    static ThreadLocal<String> local = new InheritableThreadLocal<String>();
    public static void testParent(){
        local.set("i am parent");
        //public Thread(Runnable target, String name)
        Thread child = new Thread(InheritableThreadLocalTest::testChild, "子线程");
        child.start();
        try {
            child.join();   //当前父线程等待child线程终⽌之后才从 thread.join()返回
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 查看父线程的值是否对子线程有影响
        print();
    }

    private static void testChild() {
        print();
        // 在子线程中修改值，后续查看父线程是否受影响
        local.set("I'm child");
        // 查看子线程是否修改成功
        print();
    }
    public static void print() {
        System.out.println("当前线程为：" + Thread.currentThread().getName() + "; " +
                "当前ThreadLocal中的值为：" + local.get());
    }

    public static void main(String[] args) {
//        // 命名为父线程，方便后面打印结果
//        Thread parent = new Thread(InheritableThreadLocalTest::testParent, "父线程");
//        parent.start();
        testParent();
    }
}
