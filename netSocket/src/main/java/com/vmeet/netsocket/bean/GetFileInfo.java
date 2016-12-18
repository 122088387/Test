package com.vmeet.netsocket.bean;

/**
 * 下载文件封装对象
 * @author sixgod
 *
 */
public class GetFileInfo {
	public String filename;//要下载的文件名
	public String path;//要下载的服务器文件路径
	public String myPath;//存放的本地路径
	public String parentPath;
//	public String macfrom;
	public String serverIp;
	public int serverPort;
	public int time;
	public String Downloadfileskey;
	public SocketObj socketObj;
//	public Context context;
	
	
	/**
	 * 
	 * @param filename 要下载的文件名
	 * @param path 要下载的服务器文件路径
	 * @param myPath 存放的本地相对路径(appRootPath/rootPath下的相对路径)
	 * @param parentPath 存放的本地父路径(appRootPath/rootPath)
	 * @param serverIp
	 */
	
	public GetFileInfo(String filename, String path, 
			String myPath,String parentPath,String serverIp,
			int serverPort,int time,SocketObj socketObj) {
		this.filename = filename;
		this.path = path;
		this.myPath = myPath;
		this.parentPath = parentPath;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.time = time;
		this.socketObj = socketObj;
	}
	
	public GetFileInfo(String filename, String path, String myPath,
			String parentPath,String serverIp,int serverPort,int time,
			String Downloadfileskey,SocketObj socketObj) {
		this.filename = filename;
		this.path = path;
		this.myPath = myPath;
		this.parentPath = parentPath;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.time = time;
		this.Downloadfileskey = Downloadfileskey;
		this.socketObj = socketObj;
	}
	
	@Override
	public String toString() {
		return this.filename+"§"+this.path+"§"+this.myPath+"§"+this.parentPath+"§"+this.serverIp+"§"+this.serverPort+"§"+this.time;
	}
}
