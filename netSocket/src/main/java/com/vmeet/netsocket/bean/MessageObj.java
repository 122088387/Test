package com.vmeet.netsocket.bean;

import java.io.Serializable;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import com.vmeet.netsocket.tool.DateTool;

public class MessageObj implements Serializable {

	public String content;
	public String macto;
	public String macfrom;
	public InfoType msgType;
	public String serverIp;//短连接Ip
	public int serverPort;//短连接port
	public long timeTicks;
	public String longIp;//长连接Ip
	public SocketObj socketObj;
	
	public MessageObj(InfoType msgType, long timeTicks,String macfrom,String macto, String content,String serverIp,int serverPort,SocketObj socketObj) {
		this.content = content;
		this.macfrom = macfrom;
		this.macto = macto;
		this.msgType = msgType;
		this.timeTicks = timeTicks;
		this.serverIp=serverIp;
		this.serverPort=serverPort;
		this.socketObj = socketObj;
	}
	
	public MessageObj(InfoType msgType, long timeTicks,String macfrom,String macto, String content,String serverIp,int serverPort,String longIp) {
		this.content = content;
		this.macfrom = macfrom;
		this.macto = macto;
		this.msgType = msgType;
		this.timeTicks = timeTicks;
		this.serverIp=serverIp;
		this.serverPort=serverPort;
		this.longIp = longIp;
	}
	
	public MessageObj(String fromFileString) {
		String[] contents = fromFileString.split("§");
		if (contents.length >= 5) {
			this.macfrom = contents[2];
//			this.socketObj.setMac(contents[2]);
			this.macto = contents[3];
			this.content = contents[4];
			try {
				this.msgType = InfoType.getInfoType(Integer.valueOf(contents[0]));
				this.timeTicks = getTimeDate(contents[1]);
			} catch (Exception e) {
			}
		}else{
			this.msgType = InfoType.txt;
			this.timeTicks = 0;
			this.macfrom = "00-00-00-00-00-00";
			this.macto = "00-00-00-00-00-00";
			this.content = "null";
		}
	}

	@Override
	public String toString() {
		return msgType.value() + "§" + getTimeStr() + "§" + macfrom + "§"+ macto + "§" + content + "∝";
//		return msgType.value() + "§" + getTimeStr() + "§" + socketObj.getMac() + "§"+ macto + "§" + content + "∝";
	}
	
	public static MessageObj getInstance(String toString){

		return new MessageObj(toString);
	}

	public long getTimeDate(String timeStr) {
		try {
			ParsePosition pp = new ParsePosition(0);
			//Date date = new Date(timeStr);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.parse(timeStr,pp).getTime();
		} catch (Exception e) {
		}
		return 0;
	}

	public String getTimeStr() {
		return DateTool.convertLong2String(timeTicks);
	}
}
