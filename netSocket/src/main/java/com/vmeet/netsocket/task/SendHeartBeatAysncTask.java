package com.vmeet.netsocket.task;

import android.os.AsyncTask;
import android.util.Log;

import com.vmeet.netsocket.SocketUtil;
import com.vmeet.netsocket.bean.InfoType;
import com.vmeet.netsocket.bean.PkgHead;
import com.vmeet.netsocket.bean.SocketObj;
import com.vmeet.netsocket.tool.Constants;
import com.vmeet.netsocket.tool.Tool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 发送心跳包
 * 
 * @author sixgod
 * 
 */
public class  SendHeartBeatAysncTask extends AsyncTask<Void, Void, Void> {
	private SocketObj socketObj;
	public SendHeartBeatAysncTask(SocketObj socketObj){
		this.socketObj = socketObj;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		sendHeartBeat();
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		
	}
	
	private boolean sendHeartBeat() {
		PkgHead head = new PkgHead();
		head.set_VerNum((byte) 3);
		head.set_MacFrom(socketObj.getMac());
		head.set_MacTo("00-00-00-00-00-00");
		head.set_LocalIp(socketObj.getIp());
		head.set_InfoType(InfoType.ChangeRegCode);
		head.set_DataLen(0);
		head.set_InfoLen(0);
		Socket socket;
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress(socketObj.getIp(),socketObj.getPort()), 2000);
			Log.i("成功", "连接正常");
			Constants.serverConnected = true;
		} catch (Exception e) {
			Constants.serverConnected = false;
			Tool.showLog(Constants.basePath,"conn1 send:连接异常");
			SocketUtil.ConnectServerTH(socketObj);
			return false;
		}
		try {
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(head.get_NetHeadByte(), 0,
					head.get_NetHeadByte().length);
			outputStream.flush();
			byte[] b = new byte[1];
			InputStream inputStream = socket.getInputStream();
			
			int i = inputStream.read(b);
			// b[0]==1表示服务器存在此文件
			if(b[0]==0){
				SocketUtil.ConnectServerTH(socketObj);
			}
			inputStream.close();
			Tool.showLog(Constants.basePath,"heartbeat 在线状态" + b[0]);
			socket.close();
			
		} catch (IOException e) {
			Tool.showLog(Constants.basePath,e.getMessage());
		}
		return true;
	}
}
