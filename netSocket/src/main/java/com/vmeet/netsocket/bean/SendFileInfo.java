package com.vmeet.netsocket.bean;

/**
 * 服务器文件对象以及上传文件对象封装
 * @author sixgod
 *
 */
public class SendFileInfo {
	public String filePath;
	public String path;
	public String macto;
	public String serverIp;
	public int serverPort;
	public InfoType fileType;
	public boolean isNew = false;
	public int time;
	public SocketObj socketObj;
	public String macFrom;

	/**
	 * 跨服务器发送文件
	 * @param filePath 本地文件路径
	 * @param path 上传到服务器的路径
	 * @param macto 发送给regCode
	 * @param fileType 发送文件类型
	 * @param serverIp 目标服务器ip
	 * @param serverPort 目标服务器port
	 * @param time 实例化次数
	 */
	public SendFileInfo(String filePath, String path, String macto,
			InfoType fileType, String serverIp,int serverPort,int time,SocketObj socketObjm) {
		this.filePath = filePath;
		this.path = path;
		this.macto = macto;
		this.fileType=fileType;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.time = time;
		this.socketObj = socketObj;

	}
	public SendFileInfo(String filePath, String path, String macto,
						InfoType fileType, String serverIp,int serverPort,int time,SocketObj socketObj,String macFrom) {
		this.filePath = filePath;
		this.path = path;
		this.macto = macto;
		this.fileType=fileType;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.time = time;
		this.socketObj = socketObj;
		this.macFrom = macFrom;
	}

	@Override
	public String toString() {
		return this.filePath+"§"+this.path+"§"+this.macto+"§"+this.fileType.value()+"§"+this.serverIp+"§"+this.serverPort+"§"+this.time;
	}
	
//	public static SendFileInfo getInstance(String toString){
//		if (TextUtils.isEmpty(toString)) return null;
//		String[] strings=toString.split("§");
//		if(strings.length!=7) return null;
//		SendFileInfo info = null;
//		try{
//			info=new SendFileInfo(strings[0], strings[1], strings[2], InfoType.getInfoType(Integer.parseInt(strings[3])), strings[4], Integer.parseInt(strings[5]), Integer.parseInt(strings[6]));
//		}catch(Exception e){
//		}
//		return info;
//	}

}
