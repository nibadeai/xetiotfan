package com.xet.javapro.iot.struct;

import com.xet.javapro.iot.Define;
import com.xet.javapro.iot.ParseUtil;

public class Data_finfo_paras {
	public int Len_id;
	public int Len_pwd;
	public int Id[];
	public int Pwd[];
	public String id;
	public String pwd;
	public int pos = 12;

	public void parseStruct(byte[] b) {
		Len_id = ParseUtil.int_8(b[pos]);
		Len_pwd = ParseUtil.int_8(b[pos + 1]);
		Id = ParseUtil.getBA(b, pos + 2, Len_id);
		Pwd = ParseUtil.getBA(b, pos + 2
				+ Define.Constants.DEVICE_ID_PWD_MAX_LEN, Len_pwd);
		char cs[] = new char[Len_id];
		for (int j = 0; j < Len_id; j++) {
			cs[j] = (char) Id[j];
		}
		id = new String(cs);
		cs = new char[Len_pwd];
		for (int j = 0; j < Len_pwd; j++) {
			cs[j] = (char) Pwd[j];
		}
		pwd = new String(cs);
		log();
	}

	public void log() {
		System.out.println("Data_fs_paras:\nLen_id:" + Len_id + "\nLen_pwd:"
				+ Len_pwd + "\nid:" + id + "\npwd:" + pwd);

	}
}
