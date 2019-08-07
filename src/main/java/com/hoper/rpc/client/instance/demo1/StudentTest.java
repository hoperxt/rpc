package com.hoper.rpc.client.instance.demo1;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class StudentTest {
    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        String rootPath = System.getProperty("user.dir");
        System.out.println(rootPath);

        String path = Class.class.getClass().getResource("/com/hoper/rpc/client/instance/demo1/Student.class").getPath();
        File file = new File(path);
        System.out.println(file.exists());

        InputStream inputStream = new FileInputStream(file);
        byte[] result = new byte[1024];
        int count = inputStream.read(result);
        Class clz = new MyClassLoader().defineMyClass("com.hoper.rpc.client.instance.demo1.Student", result, 0, count);
        Object student = clz.newInstance();
        clz.getMethod("hello", null).invoke(student, null);
    }
}
