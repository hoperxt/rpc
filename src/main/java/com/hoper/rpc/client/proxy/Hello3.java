package com.hoper.rpc.client.proxy;

public class Hello3 implements Hello {
    @Override
    public void sayHello(String name) {
        System.out.println("hello3," + name);
    }

    public void sayHi(String name) {
        System.out.println("Hi," + name);
    }
}
