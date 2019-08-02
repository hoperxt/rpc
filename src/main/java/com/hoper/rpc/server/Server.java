package com.hoper.rpc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    //存放响应线程
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    //存放被访问的对象
    private static final HashMap<String, Class> serviceRegistry = new HashMap<String, Class>();


    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.registryClass(Hello.class, HelloImpl.class);
        server.start();
    }


    public void registryClass(Class serviceInterface, Class serviceImpl) {
        serviceRegistry.put(serviceInterface.getName(), serviceImpl);
    }

    public void start() throws IOException {
        System.out.println("服务端启动");
        ServerSocket socket = new ServerSocket(8000);
        while (true) {
            Socket client = socket.accept();
            executor.execute(new ServiceTask(client));
        }
    }

    private static class ServiceTask implements Runnable {

        Socket clent = null;

        public ServiceTask(Socket client) {
            this.clent = client;
        }

        @Override
        public void run() {
            ObjectOutputStream output = null;
            ObjectInputStream input = null;
            try {
                input = new ObjectInputStream(clent.getInputStream());
                String serviceName = input.readUTF();
                String methodName = input.readUTF();
                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();    //参数类型
                Object[] arguments = (Object[]) input.readObject();             //参数
                Class serviceClass = serviceRegistry.get(serviceName);
                if (serviceClass == null) {
                    throw new ClassNotFoundException(serviceName + " not found");
                }
                Method method = serviceClass.getMethod(methodName, parameterTypes);
                Object result = method.invoke(serviceClass.newInstance(), arguments);

                // 3.将执行结果反序列化，通过socket发送给客户端
                output = new ObjectOutputStream(clent.getOutputStream());
                output.writeObject(result);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (clent != null) {
                    try {
                        clent.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
