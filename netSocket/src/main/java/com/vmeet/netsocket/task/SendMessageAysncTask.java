package com.vmeet.netsocket.task;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.vmeet.netsocket.SocketUtil;
import com.vmeet.netsocket.bean.MessageObj;
import com.vmeet.netsocket.bean.PkgHead;
import com.vmeet.netsocket.tool.Constants;
import com.vmeet.netsocket.tool.OnSocketLisenter;
import com.vmeet.netsocket.tool.Tool;

import android.os.AsyncTask;

/**
 * 批量上传文件线程
 * 
 * @author sixgod
 * 
 */
public class SendMessageAysncTask extends AsyncTask<MessageObj, Integer, Boolean> {
	MessageObj messageObj;
	OnSocketLisenter<String> lisenter;

	public SendMessageAysncTask(OnSocketLisenter<String> lisenter) {
		this.lisenter = lisenter;
	}

	@Override
	protected Boolean doInBackground(MessageObj... params) {
		// System.out.println("开始执行：");
		messageObj = params[0];
		return sendMessage(messageObj);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (!result) {
			SocketUtil.sendMessage(messageObj, lisenter);
			if (lisenter!=null) {
				lisenter.OnFail("发送失败");
			}
		}else{
			Tool.showLog(Constants.basePath, messageObj.content + "消息发送成功");
			if (lisenter!=null) {
				lisenter.OnSuccess("发送成功");
			}
		}
	}
	
	private  boolean sendMessage(MessageObj msgObj) {
		PkgHead head = new PkgHead();
		head.set_VerNum((byte) 3);
		head.set_MacFrom(msgObj.macfrom);
		if (msgObj.macto == null)
			msgObj.macto = "00-00-00-00-00-00";
		
		//如果格式不是00-00-00-00-00-00，返回
		if(!Tool.isReg(msgObj.macto.split(",")[0])){
			msgObj.macto = msgObj.macto.replace(msgObj.macto.split(",")[0] + ",", "");
		}
		
		head.set_MacTo(msgObj.macto.split(",")[0]);
		head.set_LocalIp(msgObj.socketObj.getIp());
		head.set_InfoType(msgObj.msgType);
		String sendInfo = msgObj.content;
		head.set_DataLen(msgObj.timeTicks);
		if (msgObj.macto.split(",").length > 1)sendInfo += "∝" + msgObj.macto;
		byte[] bs = null;
		try {
			bs = sendInfo.getBytes("UTF-8");
			head.set_InfoLen(bs.length);
		} catch (UnsupportedEncodingException e1) {
		}
		Socket socket;
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress(msgObj.serverIp,msgObj.serverPort), 2000);
			Constants.serverConnected = true;
		} catch (Exception e) {
			Constants.serverConnected = false;
			Tool.showLog(Constants.basePath,"conn1 sendM");
			SocketUtil.ConnectServerTH(msgObj.socketObj);
			return false;
		}
		try {
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(head.get_NetHeadByte(), 0,
					head.get_NetHeadByte().length);
			outputStream.write(bs, 0, bs.length);
			
//			outputStream.flush();
//			socket.shutdownInput();
//			socket.shutdownOutput();
//			socket.close();
//			outputStream.close();
		} catch (IOException e) {
			
		}
		return true;
	}
}
