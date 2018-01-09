package com.xet.javapro.iot;

public class Define {
	public static class Config {
		public static boolean DEBUG = true;
		public static boolean log_sendBuf = false;
	}

	public static class Constants {
		public static final int IOT_MAGIC_NUMBER = 0x76814350;
		public static final int IP_ADDR_LEN = 16;
		public static final int PACKAGE_BUFFER_MAX_LEN = 1024;
		public static final int IOT_DEVICE_MAX_COUNT = 20;
		public static final int IOT_DEVICE_ID_MAX_LEN = 20;
		public static final int IOT_DEVICE_IDS_MAX_LEN = 400;
		public static final int DEVICE_ID_PWD_MAX_LEN = 20;
		public static final int DESCRIBE_MAX_LEN = 20;
		public static final int ERROR_DESCRIBE_MAX_LEN = 40;
		public static final String SERVER_IPADDRESS = "192.168.1.11";
		public static final int SERVER_PORT = 7681;
		public static final int DEVICE_DISCOVERY_PORT = 7682;
	}

	public static class CommandType

	{

		public static final int GetFriendsStatus_REQUEST = 1;
		public static final int GetFriendsStatus_RESPONSE = 2;
		public static final int GetFriendInfomation_REQUEST = 3;
		public static final int GetFriendInfomation_RESPONSE = 4;
		public static final int SetFriendOnOff_REQUEST = 5;
		public static final int SetFriendOnOff_RESPONSE = 6;
		public static final int SetFriendWindSpeed_REQUEST = 7;
		public static final int SetFriendWindSpeed_RESPONSE = 8;
		public static final int SetFriendWindMode_REQUEST = 9;
		public static final int SetFriendWindMode_RESPONSE = 10;
		public static final int SetFriendWindShake_REQUEST = 11;
		public static final int SetFriendWindShake_RESPONSE = 12;
		public static final int SetFriendWindSleep_REQUEST = 13;
		public static final int SetFriendWindSleep_RESPONS = 14;

	}

	public static class MsgSequenceNum {
		public static final int GetFriendsStatus_SequenceNum = 100;
		public static final int GetFriendInfomation_SequenceNum = 200;
		public static final int SetFriendOnOff_SequenceNum = 300;
		public static final int SetFriendWindSpeed_SequenceNum = 400;
		public static final int SetFriendWindMode_SequenceNum = 500;
		public static final int SetFriendWindShake_SequenceNum = 600;
		public static final int SetFriendWindSleep_SequenceNum = 700;
	}
}
