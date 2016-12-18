package com.vmeet.netsocket.task;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.vmeet.netsocket.SocketUtil;
import com.vmeet.netsocket.bean.GetFileInfo;
import com.vmeet.netsocket.bean.InfoType;
import com.vmeet.netsocket.bean.PkgHead;
import com.vmeet.netsocket.bean.SocketObj;
import com.vmeet.netsocket.tool.Constants;
import com.vmeet.netsocket.tool.DateTool;
import com.vmeet.netsocket.tool.OnSocketLisenter;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;

/**
 * 下载多个文件启动该线程
 * 
 * @author sixgod
 * 
 */
public class GetFilesAysncTask extends AsyncTask<GetFileInfo, Void, Void> {
	GetFileInfo getFileInfo;
	OnSocketLisenter<Object> lisenter;
	String path;//
	
	public GetFilesAysncTask(OnSocketLisenter<Object> lisenter) {
		this.lisenter = lisenter;
	}

	public GetFilesAysncTask() {
		super();
	}

	@Override
	protected Void doInBackground(GetFileInfo... params) {
		System.out.println("开始执行：");
		this.getFileInfo = params[0];
		Downloadfiles(getFileInfo.myPath, getFileInfo.path,getFileInfo.serverIp, getFileInfo.serverPort,getFileInfo.socketObj);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
	}

	/**
	 * 下载服务器上某文件夹下的所有文件
	 * 
	 * @param path
	 *            本地目录
	 * @param srvPath
	 *            服务器目录
	 * @param ServerIP
	 *            服务器ip
	 * @param ServerPort
	 *            服务器端口
	 */
	public static void Downloadfiles(String path, String srvPath,String ServerIP,int ServerPort,SocketObj socketObj) {
		String Downloadfileskey = path + "⊥" + srvPath + "⊥" + ServerIP + "⊥" + ServerPort;
		File file = new File(path+"/"+srvPath.replace("\\", "/"));
		 if (!file.exists()) file.mkdirs();
		String Dirs = GetService(srvPath, InfoType.GetDirList,socketObj, ServerIP, ServerPort);
		if (Dirs != null) {
			if (Dirs.equals("0")) Dirs = "";
			for (String srvPathStr : Dirs.split("∝")) {
				if (srvPathStr.trim().length() > 0)
					Downloadfiles(path, srvPath + "\\" + srvPathStr,ServerIP, ServerPort,socketObj);
			}
		}
		String rootFiles = GetService(srvPath, InfoType.GetFileList,socketObj,ServerIP, ServerPort);
		if(!TextUtils.isEmpty(rootFiles)){
			synchronized (SocketUtil.recDir) {//气泡中发送图片用到的
				SocketUtil.recDir.put(srvPath, rootFiles.split("∝").length);
			}
		}
		if (!TextUtils.isEmpty(rootFiles)) {
			for (String fileStr : rootFiles.split("∝")) {
				if (fileStr.length() == 0)continue;
				String filename = path + "/" + srvPath.replace("\\", "/") + "/" + fileStr.split("§")[0];
				File file1 = new File(filename);
				if (file1.exists() && file1.lastModified() >= DateTool.convertString2long(fileStr.split("§")[1])){
					//本地存在此文件
					String dir = srvPath;//服务器路径   img/目录名字
					if(SocketUtil.recDir.get(dir)!=null){//表明缓存里边有此目录
						int count = SocketUtil.recDir.get(dir);
						count--;
						if(count==0){//表明此目录下载完成,发送通知更新界面添加row
							synchronized (SocketUtil.recDir) {
								SocketUtil.recDir.remove(dir);
							}
							Intent intent = new Intent(Constants.Action_Socket_GetFileDirSucess);
							intent.putExtra("dirName", dir);
							socketObj.getContext().sendBroadcast(intent);
						}else{
							synchronized (SocketUtil.recDir) {
								SocketUtil.recDir.put(dir, count);
							}
						}
					}
					continue;
				}

				GetFileInfo getfile = new GetFileInfo(fileStr.split("§")[0], srvPath, "", path+"/"+srvPath.replace("\\", "/"),ServerIP, ServerPort,
						1,Downloadfileskey,socketObj);
				SocketUtil.getFile(getfile, null);
			}
		}

	}
	private static String GetService(String srvPath, InfoType infoType,SocketObj socketObj, String serverIP, int serverPort) {
		String str = null;
		PkgHead head = new PkgHead();
		head.set_VerNum((byte) 3);
		head.set_MacFrom(socketObj.getMac());
		head.set_MacTo("00-00-00-00-00-00");
		head.set_LocalIp(socketObj.getIp());
		head.set_InfoType(infoType);
		try {
			byte[] bs1 = srvPath.getBytes("utf-8");
			head.set_InfoLen(bs1.length);
			head.set_DataLen(0);
			Socket socket1 = new Socket(serverIP, serverPort);
			OutputStream outputStream = socket1.getOutputStream();
			outputStream.write(head.get_NetHeadByte(), 0, head.get_NetHeadByte().length);
			outputStream.write(bs1, 0, bs1.length);

			InputStream inputStream = socket1.getInputStream();
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			byte[] bs = new byte[1024];
			int len = -1;
			while ((len = inputStream.read(bs)) != -1) {
				arrayOutputStream.write(bs, 0, len);
			}
			bs = arrayOutputStream.toByteArray();
			arrayOutputStream.flush();
			arrayOutputStream.close();
			inputStream.close();
			if (bs.length > 37) {
				byte[] infoByte = new byte[bs.length - 37];
				System.arraycopy(bs, 37, infoByte, 0, infoByte.length);
				str = new String(infoByte, "utf-8");
				System.out.println("str==" + str);
			}
			socket1.close();
		} catch (UnsupportedEncodingException e) {
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		}
		head.Clear();
		head = null;
		return str;
	}

}
