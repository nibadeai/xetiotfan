package com.xet.javapro.iot;

public class ParseUtil {

	public static int int_32(int b[]) {
		for (int i = 0; i < b.length; i++) {
			//System.out.println("int_32:" + b[i]);
		}
		return b[0] | (b[1] << 8) | (b[2] << 16) | (b[3] << 24);
	}

	public static int int_16(int b[]) {
		for (int i = 0; i < b.length; i++) {
			//System.out.println("int_16:" + b[i]);
		}
		return (b[0] | (b[1] << 8));
	}

	public static int int_8(byte b) {

		return b & 0xff;
	}
	public static byte[] b_32(byte b[],int int_32,int start){
		for (int i = 0; i < 4; i++) {
			b[start+i]=(byte) ((int_32>>(8*i))&0xff);
		}	
		return b;
	}
	public static byte[] b_16(byte b[],int int_16,int start){
		for (int i = 0; i < 2; i++) {
			b[start+i]=(byte) ((int_16>>(8*i))&0xff);
		}	
		return b;
	}
	public static byte[] b_8(byte b[],int int_8,int start){
			b[start]=(byte)int_8;
		return b;
	}

	public static int[] getBA(byte[] b, int start, int size) {
		int m[] = new int[size];
		for (int i = 0; i < m.length; i++) {
			m[i] = b[start + i] & 0xff;
		}
		return m;
	}
}
