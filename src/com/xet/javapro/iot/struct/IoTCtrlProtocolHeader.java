package com.xet.javapro.iot.struct;

import com.xet.javapro.iot.Define;
import com.xet.javapro.iot.ParseUtil;

public class IoTCtrlProtocolHeader {
	public static int Len_pHeader = 12;
	public int Magic; // 4
	public int Command; // 2
	public int Sequence; // 2
	public int Sock_fd; // 4

	public IoTCtrlProtocolHeader() {
	};

	public IoTCtrlProtocolHeader(int magic, int command, int sequence,
			int sock_fd) {
		Magic = magic;
		Command = command;
		Sequence = sequence;
		Sock_fd = sock_fd;
	}

	public void parseStruct(byte[] b) {
		Magic = ParseUtil.int_32(ParseUtil.getBA(b, 0, 4));
		Command = ParseUtil.int_16(ParseUtil.getBA(b, 4, 2));
		Sequence = ParseUtil.int_16(ParseUtil.getBA(b, 6, 2));
		Sock_fd = ParseUtil.int_32(ParseUtil.getBA(b, 8, 4));
		log();
	}

	public byte[] createStruct(byte[] b) {
		ParseUtil.b_32(b, Magic, 0);
		ParseUtil.b_16(b, Command, 4);
		ParseUtil.b_16(b, Sequence, 6);
		ParseUtil.b_32(b, Sock_fd, 8);
		log(b,0,Len_pHeader);
		return b;
	}

	public void log() {
		System.out.println("IoTCtrlProtocolHeader:Magic=" + Magic + ",Command="
				+ Command + ",Sequence=" + Sequence + ",Sock_fd=" + Sock_fd);
	}

	public void log(byte[] b,int start,int size) {
		if(!Define.Config.DEBUG||!Define.Config.log_sendBuf)return;
		for (int i = 0; i < size; i++) {
			System.out.println(b[start+i]);
		}
	}
}
