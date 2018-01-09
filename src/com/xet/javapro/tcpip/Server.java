package com.xet.javapro.tcpip;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2017/1/16.
 */

public class Server {
    //public String ip = "192.168.1.11";
    public int port = 7681;
    ServerSocket serverSocket = null;
    public static InetAddress address = null;

    public static void main(String[] args) {
        try {
            address = InetAddress.getLocalHost();
            System.out.println("main()...hostName:" + address.getHostName() +
                    ",hostAddress:" + address.getHostAddress()+
                    ",inetAddress:" + address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Server server = new Server();
        server.startServer();
        //    server.input();
//       server.getInput();
    }

    public void startServer() {

        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("start..server...port:" + port);
        while (true) {
            System.out.println("while...等待连接");
            Socket socket = null;
            try {
                socket = serverSocket.accept();

                System.out.println("已连接...ipAddress:" + socket.getInetAddress() +
                        ", port:" + socket.getPort() + ",local..:" + socket.getLocalAddress());
                new MyThread(socket).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    class MyThread extends Thread {
        Socket socket;
        BufferedReader reader = null;
        PrintWriter pw = null;

        public MyThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            try {
           
                reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                pw = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream()), true);
                pw.println("Welcome to server!!");
                input();
                outPut(pw);
                String str;
                /*char buf[] =new char[24];
               int len= reader.read(buf,0,buf.length);
                System.out.println("read:len:"+len);
                for (int i = 0; i < buf.length; i++) {
                    System.out.println(""+buf[i]);
                }
*/
            while ((str = reader.readLine()) != null) {

                    System.out.println("收到client消息："+str);
                    pw.println("server:" + str);
                }
                System.out.println("客户端断开连接。。.");
            } catch (IOException e) {
                // e.printStackTrace();
                System.out.println("server...read.exception.." + e.getMessage());
            } finally {
                try {
                    socket.close();
                    pw.close();
                    reader.close();
                    System.out.println("server.close()");
                } catch (Exception e) {
                    // e.printStackTrace();
                    System.out.println("server...finally..exception.." + e.getMessage());
                }
            }
        }
    }


    public static String input2 = null;
    public static String input = null;

    public void input() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader br = null;
                try {
                    System.out.println("ready input ...");
                    //System.in 随机？
                    br = new BufferedReader(new InputStreamReader(System.in));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        input = line;
                       // System.out.println("input:" + input);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
        }).start();

    }

    public void outPut(final PrintWriter pw) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (input != null && !input.equals(input2)) {
                        input2 = input;
                        pw.println(input2);
                        System.out.println(" pw.println().." + input2);
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    public void getInput() {
        System.out.println("ready..getInput..");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (input != null && !input.equals(input2)) {
                        input2 = input;
                        System.out.println("rw.." + input);
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }


}
