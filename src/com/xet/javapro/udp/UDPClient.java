package com.xet.javapro.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
	public static DatagramSocket ds;
	public static int port = 7682;
	public static byte[] buf = new byte[1024];
	public static DatagramPacket recvdp = new DatagramPacket(buf, buf.length);

	public static void main(String[] args) throws Exception {
		ds = new DatagramSocket(port);
		System.out.println("start  udp client...");
		new receiveThread().start();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		byte[] buf;
		String line = br.readLine();
		while (!line.contains("over")) {
			buf = (line).getBytes();
			for (int i = 0; i < buf.length; i++) {
				System.out.println(buf[i]);
			}
			InetAddress sendAdd = InetAddress.getByName("127.0.0.1");
			DatagramPacket senddp = new DatagramPacket(buf, buf.length,
					sendAdd, 7681);
			ds.send(senddp);
			System.out.println("client send :" + line);
			line = br.readLine();
		}
		br.close();
		ds.close();
		System.out.println("close...");
	}

	static class receiveThread extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					ds.receive(recvdp);
					String data = new String(recvdp.getData(), 0,
							recvdp.getLength());
					String add = recvdp.getAddress().getHostAddress();
					int port = recvdp.getPort();
					System.out.println("client receive:" + data
							+ ",from address:" + add + ",port:" + port);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

	}
}
