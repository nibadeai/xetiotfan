package com.xet.javapro.iot.struct;

import com.xet.javapro.iot.Define;
import com.xet.javapro.iot.ParseUtil;

public class Data_header {
	public static int pos = IoTCtrlProtocolHeader.Len_pHeader;
	public static int Len_dHeader = 4 + Define.Constants.ERROR_DESCRIBE_MAX_LEN;
	public int status; // 4
	public int describe[] = new int[Define.Constants.ERROR_DESCRIBE_MAX_LEN];

	public void setDescribe(String describe) {
		byte b[] = describe.getBytes();
		for (int i = 0; i < b.length; i++) {
			if(i<this.describe.length)
			this.describe[i] = b[i];
		}
	}

	public Data_header() {
	}

	public byte[] createStruct(byte[] b) {
		ParseUtil.b_32(b, status, pos);
		for (int i = 0; i < describe.length; i++) {
			b[pos + 4 + i] = (byte) describe[i];
		}
		log(b, pos, Len_dHeader);
		return b;
	}

	public void log(byte[] b, int start, int size) {
		if(!Define.Config.DEBUG||!Define.Config.log_sendBuf)return;
		for (int i = 0; i < size; i++) {
			System.out.println(b[start + i]);
		}
	}
}
