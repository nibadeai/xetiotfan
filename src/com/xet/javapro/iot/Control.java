package com.xet.javapro.iot;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;

import com.xet.javapro.iot.struct.Data_finfo;
import com.xet.javapro.iot.struct.Data_finfo_paras;
import com.xet.javapro.iot.struct.Data_fnormal_paras;
import com.xet.javapro.iot.struct.Data_fs;
import com.xet.javapro.iot.struct.Data_fs_paras;
import com.xet.javapro.iot.struct.Data_header;
import com.xet.javapro.iot.struct.IoTCtrlProtocolHeader;

public class Control {

	public static void GetFriendsStatus(DatagramSocket ds,
			IoTCtrlProtocolHeader pHead, Data_fs_paras data_paras) {
		System.out.println("****************GetFriendsStatus**************");
		byte sendBuf[] = new byte[1024];
		pHead.Command = Define.CommandType.GetFriendsStatus_RESPONSE;
		pHead.createStruct(sendBuf);
		Data_header dHeader = new Data_header();
		dHeader.status = 100;
		dHeader.setDescribe("查询所有设备状态成功！！");
		dHeader.createStruct(sendBuf);
		Data_fs data_fs = new Data_fs();//这个是想要获得的数据，从数据库查询写入这个对象
		int onlineState[] = new int[data_paras.Len_ids];
		int onOffState[] = new int[data_paras.Len_ids];
		for (int i = 0; i < onlineState.length; i++) {
			onlineState[i] = new Random().nextInt(2);
			if (onlineState[i] == 1) {
				onOffState[i] = new Random().nextInt(2);
			}
		}
		data_fs.Len_id=data_paras.Len_id;
		data_fs.Len_ids=data_paras.Len_ids;
		data_fs.Ids=data_paras.Ids;
		data_fs.OnlineState=onlineState;
		data_fs.OnOffState=onOffState;
		data_fs.createStruct(sendBuf);
		/*
		 * for (int i = 0; i < 30; i++) { System.out.println("i:"+sendBuf[i]); }
		 */
		DatagramPacket senddp = new DatagramPacket(sendBuf, sendBuf.length,
				UDPIotServer.sendAdd, UDPIotServer.clientPort);
		try {
			ds.send(senddp);
			System.out.println("***********send to client *************");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void GetFriendInfomation(DatagramSocket ds,
			IoTCtrlProtocolHeader pHead, Data_finfo_paras data_paras) {
		System.out.println("**************GetFriendInfomation**************");
		byte sendBuf[] = new byte[1024];
		pHead.Command = Define.CommandType.GetFriendInfomation_RESPONSE;
		pHead.createStruct(sendBuf);
		Data_header dHeader = new Data_header();
		dHeader.status = 100;
		dHeader.setDescribe("查询设备信息成功！！");
		dHeader.createStruct(sendBuf);
		Data_finfo data_finfo = new Data_finfo();//这个是想要获得的数据，从数据库查询写入这个对象
		data_finfo.Len_id = data_paras.Len_id;
		data_finfo.Len_pwd = data_paras.Len_pwd;
		data_finfo.Id = data_paras.Id;
		data_finfo.Pwd = data_paras.Pwd;
		data_finfo.OnlineState = 1;
		data_finfo.OnOffState = 1;
		data_finfo.WindMode = 2;
		data_finfo.WindSpeed = 3;
		data_finfo.Shake = 0;
		data_finfo.Sleep_seconds = 3600;
		data_finfo.createStruct(sendBuf);
		/*
		 * for (int i = 0; i < 30; i++) { System.out.println("i:"+sendBuf[i]); }
		 */
		DatagramPacket senddp = new DatagramPacket(sendBuf, sendBuf.length,
				UDPIotServer.sendAdd, UDPIotServer.clientPort);
		try {
			ds.send(senddp);
			System.out.println("***********send to client *************");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void SetFriendOnOff(DatagramSocket ds, IoTCtrlProtocolHeader pHead,
			Data_fnormal_paras data_paras) {
		System.out.println("**************SetFriendOnOff**************");
		byte sendBuf[] = new byte[1024];
		pHead.Command = Define.CommandType.SetFriendOnOff_RESPONSE;
		pHead.createStruct(sendBuf);
		Data_header dHeader = new Data_header();
		dHeader.status = 100;
		dHeader.setDescribe("设置设备开关成功！！");
		dHeader.createStruct(sendBuf);
		Data_finfo data_finfo = new Data_finfo();//设置成功后从数据库查询写入这个对象
		data_finfo.Len_id = data_paras.Len_id;
		data_finfo.Len_pwd = data_paras.Len_pwd;
		data_finfo.Id = data_paras.Id;
		data_finfo.Pwd = data_paras.Pwd;
		data_finfo.OnlineState = 1;
		data_finfo.OnOffState = data_paras.Value;
		data_finfo.WindMode = 3;
		data_finfo.WindSpeed = 4;
		data_finfo.Shake = 1;
		data_finfo.Sleep_seconds = 1800;
		data_finfo.createStruct(sendBuf);
		/*
		 * for (int i = 0; i < 30; i++) { System.out.println("i:"+sendBuf[i]); }
		 */
		DatagramPacket senddp = new DatagramPacket(sendBuf, sendBuf.length,
				UDPIotServer.sendAdd, UDPIotServer.clientPort);
		try {
			ds.send(senddp);
			System.out.println("***********send to client *************");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
