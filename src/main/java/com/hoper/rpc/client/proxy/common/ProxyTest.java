package com.hoper.rpc.client.proxy.common;

import com.hoper.rpc.client.proxy.Hello;
import com.hoper.rpc.client.proxy.Hello2;
import com.hoper.rpc.client.proxy.Hello3;
import com.hoper.rpc.client.proxy.HelloImpl;
import sun.misc.ProxyGenerator;

import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String[] args) {
        Hello hello = new HelloImpl();
        hello.sayHello("python");
        System.out.println(hello.getClass());           //class com.hoper.rpc.client.proxy.HelloImpl
        System.out.println("-----------------------");
        Handler handler = new Handler(new HelloImpl());
        Hello o = (Hello) Proxy.newProxyInstance(hello.getClass().getClassLoader(), hello.getClass().getInterfaces(), handler);
        System.out.println(o.getClass());               //class com.sun.proxy.$Proxy0
        o.sayHello("java");
        System.out.println("-----------------------");
        Hello2 hello2 = new Hello2();
        Handler handler1 = new Handler(hello2);
        Object h2 = Proxy.newProxyInstance(hello2.getClass().getClassLoader(), hello2.getClass().getInterfaces(), handler1);
        System.out.println(h2.getClass());
        System.out.println("-----------------------");
        Hello3 h3 = new Hello3();
        Handler handler3 = new Handler(h3);

        //会报错，无法将创建的代理对象转换成Hello3，可以将代理对象的.class保存到硬盘后，查看内部结构
        Hello3 o2 = (Hello3) Proxy.newProxyInstance(h3.getClass().getClassLoader(), h3.getClass().getInterfaces(), handler3);
        System.out.println(o2.getClass());
        o2.sayHello("java");
        o2.sayHi("R");
    }
}
