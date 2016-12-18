package com.vmeet.netsocket.task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.vmeet.netsocket.bean.InfoType;
import com.vmeet.netsocket.bean.PkgHead;
import com.vmeet.netsocket.bean.SocketObj;
import com.vmeet.netsocket.tool.Constants;
import com.vmeet.netsocket.tool.Tool;

import android.os.AsyncTask;
import android.util.Log;

public class GetDataAysnc extends AsyncTask<String, Integer, String>{
	private String ip,content;
	private InfoType infoType;
	private int port;
	private SocketObj socketObj;
	public GetDataAysnc(String ip, int port,String content,InfoType infoType,SocketObj socketObj) {
		this.ip=ip;
		this.port=port;
		this.content = content;
		this.infoType = infoType;
		this.socketObj = socketObj;
	}
	@Override
	protected String doInBackground(String... params) {
		return getData();
	}
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}
	private String getData() {
		String str = "";
		PkgHead head = new PkgHead();
		head.set_VerNum((byte) 3);
		head.set_MacFrom(socketObj.getMac());
		head.set_MacTo("00-00-00-00-00-00");
		head.set_LocalIp(socketObj.getIp());
		head.set_InfoType(infoType);
		String sendInfo = content;
		head.set_DataLen(System.currentTimeMillis());
		byte[] bs = null;
		try{
			bs = sendInfo.getBytes("utf-8");
			head.set_InfoLen(bs.length);
		}catch(UnsupportedEncodingException e1){
			
		}
		Socket socket;
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress(ip==null?socketObj.getIp():ip,port==0?socketObj.getPort():port),2000);
		} catch (Exception e) {
			Constants.showLog("conn1 getData" + e.toString());
			return "";
		}
		try {
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(head.get_NetHeadByte(), 0,
					head.get_NetHeadByte().length);
			outputStream.write(bs, 0, bs.length);
			
			InputStream inputStream = socket.getInputStream();
			byte[] buffer = Tool.getByte(Constants.HeaderLength, inputStream);
			PkgHead head1 = new PkgHead();
			if (buffer == null)
				return "";
			head1.set_NetHeadByte(buffer);
			byte[] infoByte = Tool.getByte(head1.get_InfoLen(), inputStream);
			str = new String(infoByte, "utf-8");
//			Log.e("ssss", str);
		} catch (IOException e) {
			Tool.showLog(Constants.basePath,e.toString());
		}
		return str;
	}
}
