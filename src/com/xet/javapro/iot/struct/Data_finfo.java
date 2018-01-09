package com.xet.javapro.iot.struct;

import com.xet.javapro.iot.Define;
import com.xet.javapro.iot.ParseUtil;

public class Data_finfo {
	public static int pos = IoTCtrlProtocolHeader.Len_pHeader
			+ Data_header.Len_dHeader;
	// 最后的Sleep_seconds前要空出一位，其为4字节，前面的数据使用了47字节，为使
	// 结构体的数据在储存空间对齐，会空出一位，在下一位开始储存下个四字节整形数据
	public static int len_data_finfo = 7 + 1
			+ Define.Constants.IOT_DEVICE_ID_MAX_LEN * 2 + 4;
	public int Len_id;
	public int Len_pwd;
	public int Id[] = new int[Define.Constants.IOT_DEVICE_ID_MAX_LEN];
	public int Pwd[] = new int[Define.Constants.IOT_DEVICE_ID_MAX_LEN];
	public int OnlineState;
	public int OnOffState;
	public int WindSpeed;
	public int WindMode;
	public int Shake;
	public int Sleep_seconds;

	public Data_finfo() {
		// TODO Auto-generated constructor stub
	}

	public byte[] createStruct(byte[] b) {
		ParseUtil.b_8(b, Len_id, pos);
		ParseUtil.b_8(b, Len_pwd, pos + 1);
		for (int i = 0; i < Id.length; i++) {
			b[pos + 2 + i] = (byte) Id[i];
		}
		for (int i = 0; i < Pwd.length; i++) {
			b[pos + 2 + Define.Constants.IOT_DEVICE_ID_MAX_LEN + i] = (byte) Pwd[i];
		}
		ParseUtil.b_8(b, OnlineState, pos + 2
				+ Define.Constants.IOT_DEVICE_ID_MAX_LEN * 2);
		ParseUtil.b_8(b, OnOffState, pos + 3
				+ Define.Constants.IOT_DEVICE_ID_MAX_LEN * 2);
		ParseUtil.b_8(b, WindSpeed, pos + 4
				+ Define.Constants.IOT_DEVICE_ID_MAX_LEN * 2);
		ParseUtil.b_8(b, WindMode, pos + 5
				+ Define.Constants.IOT_DEVICE_ID_MAX_LEN * 2);
		ParseUtil.b_8(b, Shake, pos + 6
				+ Define.Constants.IOT_DEVICE_ID_MAX_LEN * 2);
		// 这里Sleep_seconds的起始位置加了一位，因为结构体会对齐数据，空出了一位
		ParseUtil.b_32(b, Sleep_seconds, pos + 7 + 1
				+ Define.Constants.IOT_DEVICE_ID_MAX_LEN * 2);
		log(b, pos, len_data_finfo);
		log();
		return b;
	}

	public void log(byte[] b, int start, int size) {
		if (!Define.Config.DEBUG || !Define.Config.log_sendBuf)
			return;
		for (int i = 0; i < size; i++) {
			System.out.println(b[start + i]);
		}
	}

	public void log() {
		System.out.println("Data_finfo:\n" + "Len_id:" + Len_id + "\nLen_pwd:"
				+ Len_pwd);
      char c[]=new char[Len_id];
      for (int i = 0; i < c.length; i++) {
		c[i]=(char) Id[i];
	 }
      System.out.println("Id:"+new String(c));
      c=new char[Len_pwd];
      for (int i = 0; i < c.length; i++) {
		c[i]=(char) Pwd[i];
	 }
      System.out.println("Pwd:"+new String(c));
      System.out.println("OnlineState:"+OnlineState);
      System.out.println("OnOffState:"+OnOffState);
      System.out.println("WindSpeed:"+WindSpeed);
      System.out.println("WindMode:"+WindMode);
      System.out.println("Shake:"+Shake);
      System.out.println("Sleep_seconds:"+Sleep_seconds);
	}
}
