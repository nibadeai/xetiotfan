package com.xet.javapro.iot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;

import com.xet.javapro.iot.Control;
import com.xet.javapro.iot.Define;
import com.xet.javapro.iot.struct.Data_finfo_paras;
import com.xet.javapro.iot.struct.Data_fnormal_paras;
import com.xet.javapro.iot.struct.Data_fs;
import com.xet.javapro.iot.struct.Data_fs_paras;
import com.xet.javapro.iot.struct.Data_header;
import com.xet.javapro.iot.struct.IoTCtrlProtocolHeader;

public class UDPIotServer {
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
					System.out
							.println("****************************************");
					sendAdd = recvdp.getAddress();
					clientPort = recvdp.getPort();
					byte[] buf = recvdp.getData();
					boolean tag = false;
					if (tag) {
						String data = new String(recvdp.getData(), 0,
								recvdp.getLength());
						String add = sendAdd.getHostAddress();
						System.out.println("server receive:" + data
								+ ",from address:" + add + ",port:"
								+ clientPort);
						for (int i = 0; i < 40; i++) {
							System.out.println((i + 1) + ":" + buf[i]);
						}
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
								- Define.MsgSequenceNum.GetFriendsStatus_SequenceNum) >= 100) {
							System.out
									.println("sequence number don't match...");
							continue;
						}
						Data_fs_paras data_fpara = new Data_fs_paras();
						data_fpara.parseStruct(buf);
						System.out
								.println("****************************************");
						Control.GetFriendsStatus(ds, pHead, data_fpara);

						break;
					case Define.CommandType.GetFriendInfomation_REQUEST:
						if (Math.abs(pHead.Sequence
								- Define.MsgSequenceNum.GetFriendInfomation_SequenceNum) >= 100) {
							System.out
									.println("sequence number don't match...");
							continue;
						}
						Data_finfo_paras data_finfo = new Data_finfo_paras();
						data_finfo.parseStruct(buf);
						System.out
								.println("****************************************");
						Control.GetFriendInfomation(ds, pHead, data_finfo);
						break;
					case Define.CommandType.SetFriendOnOff_REQUEST:
						if (Math.abs(pHead.Sequence
								- Define.MsgSequenceNum.SetFriendOnOff_SequenceNum) >= 100) {
							System.out
									.println("sequence number don't match...");
							continue;
						}
						Data_fnormal_paras data_paras = new Data_fnormal_paras();
						data_paras.parseStruct(buf);
						System.out
								.println("****************************************");
						Control.SetFriendOnOff(ds, pHead, data_paras);

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
