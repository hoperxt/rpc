package com.hoper.rpc.server;

public class HelloImpl implements Hello {
    @Override
    public void sayHello(String name) {
        System.out.println("Hello," + name);
    }
}
