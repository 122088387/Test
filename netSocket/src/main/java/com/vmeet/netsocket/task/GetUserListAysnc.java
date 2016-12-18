package com.vmeet.netsocket.task;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.vmeet.netsocket.bean.InfoType;
import com.vmeet.netsocket.bean.MessageObj;
import com.vmeet.netsocket.bean.PkgHead;
import com.vmeet.netsocket.bean.SocketObj;
import com.vmeet.netsocket.tool.Constants;
import com.vmeet.netsocket.tool.Tool;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class GetUserListAysnc extends AsyncTask<String, Integer, Boolean> {
	private String ip;
	private int port;
	private SocketObj socketObj;
	public GetUserListAysnc(String ip, int port,SocketObj socketObj) {
		this.ip=ip;
		this.port=port;
		this.socketObj = socketObj;
	}
	@Override
	protected Boolean doInBackground(String... params) {
		getUserList();
		return null;
	}
	private void getUserList(){
		String str1 = "";
		PkgHead head = new PkgHead();
		head.set_VerNum((byte) 3);
		head.set_MacFrom(socketObj.getMac());
		head.set_MacTo("00-00-00-00-00-00");
		head.set_LocalIp(socketObj.getIp());
		head.set_InfoType(InfoType.getUserListSet);
		try {
			byte[] bs = null;
			Socket socket1 = new Socket();
			socket1.connect(new InetSocketAddress(ip==null?socketObj.getIp():ip,port==0?socketObj.getPort():port),2000);
			if (!socket1.isConnected()) {
				socket1.close();
				return;
			}
			OutputStream outputStream = socket1.getOutputStream();
			head.set_DataLen(0);
			head.set_InfoLen(0);
			if (socketObj.getMac() != null) {
				bs = socketObj.getMac().getBytes("utf-8");
				head.set_InfoLen(bs.length);
			}
			outputStream.write(head.get_NetHeadByte(), 0,head.get_NetHeadByte().length);
			if (socketObj.getMac() != null) outputStream.write(bs, 0, bs.length);
			InputStream inputStream = socket1.getInputStream();
			byte[] HeadByte1 = Tool.getByte(Constants.HeaderLength, inputStream);
			if (HeadByte1 == null) 	return;
			PkgHead head1 = new PkgHead();
			head1.set_NetHeadByte(HeadByte1);
			byte[] infoByte1 = Tool.getByte(head1.get_InfoLen(),inputStream);
			if (infoByte1 == null) 	return;
			str1 = new String(infoByte1, "utf-8");
			if (str1 != null) {
				Intent intent=new Intent(Constants.Action_Server);
				MessageObj obj=new MessageObj(InfoType.DataObj,System.currentTimeMillis(), null, null, str1,ip,port,socketObj);
				Bundle bundle=new Bundle();
				bundle.putSerializable("MessageObj", obj);
				intent.putExtras(bundle);
				socketObj.getContext().sendBroadcast(intent);
			}
			socket1.close();
		} catch (Exception e) {
		}
		head.Clear();
		head = null;
	}
}
