package com.xet.javapro.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.xet.javapro.iot.Control;
import com.xet.javapro.iot.Define;
import com.xet.javapro.iot.struct.Data_fs_paras;
import com.xet.javapro.iot.struct.IoTCtrlProtocolHeader;

public class UDPServer {
	public static DatagramSocket ds;
	public static int port = 7681;
	public static byte[] buf = new byte[1024];
	public static DatagramPacket recvdp = new DatagramPacket(buf, buf.length);
	public static boolean isConnected = false;
	public static InetAddress sendAdd;
	public static int clientPort = 7682;

	public static void main(String[] args) {
		try {
			ds = new DatagramSocket(port);
			System.out.println("start  udp server...");
			new receiveThread().start();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			byte[] buf;
			String line;
			while (!(line = br.readLine()).contains("over")) {
				buf = line.getBytes();
				if (sendAdd == null) {
					continue;
				}
				DatagramPacket senddp = new DatagramPacket(buf, buf.length,
						sendAdd, clientPort);
				ds.send(senddp);
				System.out.println("server send :" + line);
			}
			br.close();
			ds.close();
			System.out.println("close...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class receiveThread extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					ds.receive(recvdp);
					System.out.println("receive..client..");
					sendAdd = recvdp.getAddress();
					clientPort = recvdp.getPort();
					if (true) {
						String data = new String(recvdp.getData(), 0,
								recvdp.getLength());
						String add = sendAdd.getHostAddress();
						System.out.println("server receive:" + data
								+ ",from address:" + add + ",port:"
								+ clientPort);
					}
					byte[] buf = recvdp.getData();
					for (int i = 0; i < 40; i++) {
						System.out.println((i + 1) + ":" + buf[i]);
					}
					IoTCtrlProtocolHeader pHead = new IoTCtrlProtocolHeader();
					pHead.parseStruct(buf);
					if (pHead.Magic != Define.Constants.IOT_MAGIC_NUMBER) {
						System.out.println("IOT_MAGIC_NUMBER don't match...");
						continue;
					}
					switch (pHead.Command) {
					case Define.CommandType.GetFriendsStatus_REQUEST:
						if (Math.abs(pHead.Sequence
								- Define.MsgSequenceNum.GetFriendInfomation_SequenceNum) >= 100) {
							System.out.println("sequence number don't match...");
							continue;
						}
						Data_fs_paras data_fpara=new Data_fs_paras();
						data_fpara.parseStruct(buf);
					
						break;

					default:
						break;
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

	}
}
