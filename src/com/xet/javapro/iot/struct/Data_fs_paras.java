package com.xet.javapro.iot.struct;

import com.xet.javapro.iot.ParseUtil;

public class Data_fs_paras {
	public int Len_id;
	public int Len_ids;
	public int Ids[];
	public String ids[];
	public int pos = 12;

	public void parseStruct(byte[] b) {
		Len_id = ParseUtil.int_8(b[pos]);
		Len_ids = ParseUtil.int_8(b[pos + 1]);
		Ids = ParseUtil.getBA(b, pos + 2, Len_id * Len_ids);
		ids = new String[Len_ids];
		for (int i = 0; i < Len_ids; i++) {
			char cs[] = new char[Len_id];
			for (int j = 0; j < Len_id; j++) {
				cs[j] = (char) Ids[j + i * Len_id];
			}
			ids[i] = new String(cs);
		}
		log();
	}

	public void log() {
		System.out.println("Data_fs_paras:\nLen_id:" + Len_id + "\nLen_ids:"
				+ Len_ids);
		for (String s : ids) {
			System.out.println("id:" + s);
		}
	}

}
