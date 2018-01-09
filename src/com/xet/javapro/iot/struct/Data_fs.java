package com.xet.javapro.iot.struct;

import com.xet.javapro.iot.Define;
import com.xet.javapro.iot.ParseUtil;

public class Data_fs {
	public static int pos = IoTCtrlProtocolHeader.Len_pHeader
			+ Data_header.Len_dHeader;
	public static int len_data_fs = 1 + 1
			+ Define.Constants.IOT_DEVICE_ID_MAX_LEN
			+ Define.Constants.IOT_DEVICE_IDS_MAX_LEN * 2;
	public int Len_id;
	public int Len_ids;
	public int Ids[];
	public int OnlineState[];
	public int OnOffState[];
    public String ids[];
	public void setStrIds(String[] ids) {
		Ids = new int[Define.Constants.IOT_DEVICE_IDS_MAX_LEN];
		int m = 0;
		for (int i = 0; i < ids.length; i++) {
			for (int j = 0; j < ids[i].length(); j++) {
				Ids[m++] = ids[i].charAt(j);
			}
		}
	}

	public byte[] createStruct(byte[] b) {
		ParseUtil.b_8(b, Len_id, pos);
		ParseUtil.b_8(b, Len_ids, pos + 1);
		for (int i = 0; i < Ids.length; i++) {
			b[pos + 2 + i] = (byte) Ids[i];
		}
		for (int i = 0; i < OnlineState.length; i++) {
			b[pos + 2 + Define.Constants.IOT_DEVICE_IDS_MAX_LEN + i] = (byte) OnlineState[i];
		}
		for (int i = 0; i < OnOffState.length; i++) {
			b[pos + 2 + Define.Constants.IOT_DEVICE_IDS_MAX_LEN
					+ Define.Constants.IOT_DEVICE_MAX_COUNT + i] = (byte) OnOffState[i];
		}
		 log(b,pos,len_data_fs);
		 log();
		return b;
	}

	public void log(byte[] b, int start, int size) {
		if(!Define.Config.DEBUG||!Define.Config.log_sendBuf)return;
		for (int i = 0; i < size; i++) {
			System.out.println(b[start + i]);
		}
	}
	public void log(){
		System.out.println("Data_fs:\n"+"Len_id:"+Len_id+"\nLen_ids:"+Len_ids);
		ids=new String[Len_ids];
		for (int i = 0; i < Len_ids; i++) {
			char c[]=new char[Len_id];
			for (int j = 0; j < Len_id; j++) {
				c[j]=(char) Ids[j+i*Len_id];
			}
			ids[i]=new String(c);
			System.out.println("Id["+i+"]:"+ids[i]+" OnlineState:"+OnlineState[i]
					+" OnOffState:"+OnOffState[i]);
		}
		
	}
}
