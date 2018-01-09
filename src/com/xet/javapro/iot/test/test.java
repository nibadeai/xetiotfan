package com.xet.javapro.iot.test;

import com.xet.javapro.iot.ParseUtil;
import com.xet.javapro.iot.struct.IoTCtrlProtocolHeader;

public class test {
	public static void main(String[] args) {
		String a = "abc中!";
		byte b[] = a.getBytes();
		for (int i = 0; i < b.length; i++) {
			System.out.println(b[i]);
		}

	}

}
