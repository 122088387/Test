package com.vmeet.netsocket.bean;

import android.content.Context;

public class SocketObj {
	private String mac;//当前账号 mac
	private String ip="123.56.135.139";//长连接Ip
	private int port=30003;//长连接Port
	private Context context;//上下文对象

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
}
