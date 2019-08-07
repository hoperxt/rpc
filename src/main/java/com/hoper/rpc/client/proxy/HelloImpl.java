package com.hoper.rpc.client.proxy;

public class HelloImpl implements Hello {
    @Override
    public void sayHello(String name) {
        System.out.println("Hello," + name);
    }
}
