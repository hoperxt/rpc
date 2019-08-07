package com.hoper.rpc.client.instance.demo1;

public class MyClassLoader extends ClassLoader {
    protected final Class<?> defineMyClass(String classname, byte[] b, int off, int len) {
        //ClassLoader是抽象类，且defineClass是protect，无法直接调用?
        return super.defineClass(classname, b, off, len);
    }

    protected void test(){

    }
}
