package com.xet.javapro.tcpip;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Administrator on 2017/1/16.
 */

public class Client {
    //public String ip = "192.168.1.11";
    public String ip = "192.168.137.155";
   // public String ip = "192.168.23.1";
   // public String ip = "127.0.0.1";
    public int port = 7681;

    public static void main(String[] args) {
        new Client().startClient();

    }

    public void startClient() {
        Socket socket = null;
        PrintWriter pw = null;
        BufferedReader brSocket = null;
        BufferedReader br = null;
        try {
            System.out.println("开始连接。。。ip:"+ip+",port:"+port);
            socket = new Socket(ip, port);
            System.out.println("连接成功。。");
            br = new BufferedReader(new InputStreamReader(
                    System.in));
            brSocket = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            pw = new PrintWriter(
                    new BufferedOutputStream(socket.getOutputStream()), true);

            new ClientThread(brSocket).start();

            String line;
            while ((line = br.readLine()) != null && !line.endsWith("over")) {
                pw.println(line);
                pw.flush();

            }
            System.out.println("client:over..");
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("client:Exception.." + e.getMessage());
        } finally {
            try {
                socket.close();
                pw.close();
                br.close();

            } catch (Exception e) {
                //   e.printStackTrace();
                System.out.println("client:finally..exception:"+e.getMessage());
            }
        }

    }

    class ClientThread extends Thread {
        BufferedReader brSocket;

        public ClientThread(BufferedReader brSocket) {
            this.brSocket = brSocket;
        }

        @Override
        public void run() {
            String res;
            try {
                while ((res = brSocket.readLine()) != null && !res.endsWith("bye")) {
                    System.out.println("get..server msg...:" + res);
                }
            } catch (IOException e) {
                // e.printStackTrace();
                System.out.println("ClientThread..exception:" + e.getMessage());
            } finally {
                try {
                    brSocket.close();
                } catch (IOException e) {
                    System.out.println("ClientThread..finally.exception:" + e.getMessage());
                }
            }
        }
    }
}
