package com.vmeet.netsocket.task;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.vmeet.netsocket.SocketUtil;
import com.vmeet.netsocket.bean.GetFileInfo;
import com.vmeet.netsocket.bean.InfoType;
import com.vmeet.netsocket.bean.PkgHead;
import com.vmeet.netsocket.tool.Constants;
import com.vmeet.netsocket.tool.OnSocketLisenter;
import com.vmeet.netsocket.tool.Tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 下载单个文件启动该线程
 * 
 * @author sixgod
 * 
 */
public class GetFileAysncTask extends AsyncTask<GetFileInfo, Integer, Boolean> {
	GetFileInfo getFileInfo;
	OnSocketLisenter<Object> lisenter;
	String path;//
	private final int retryTime = 3;

	public GetFileAysncTask(OnSocketLisenter<Object> lisenter) {
		this.lisenter = lisenter;
	}

	public GetFileAysncTask() {
		super();
	}

	@Override
	protected Boolean doInBackground(GetFileInfo... params) {
		System.out.println("开始执行：");
		this.getFileInfo = params[0];
		return getFile(getFileInfo);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (!result && getFileInfo.time <= retryTime) {
			getFileInfo.time++;
			SocketUtil.getFile(getFileInfo, lisenter);
		}
		if(result){
			if(!TextUtils.isEmpty(getFileInfo.Downloadfileskey)){//调用getFiles方法
				String dir = getFileInfo.path;//服务器路径   img/目录名字
				if(SocketUtil.recDir.get(dir)!=null){//表明缓存里边有此目录
					int count = SocketUtil.recDir.get(dir);
					count--;
					if(count==0){//表明此目录下载完成,发送通知更新界面添加row
						synchronized (SocketUtil.recDir) {
							SocketUtil.recDir.remove(dir);
						}
						Intent intent = new Intent(Constants.Action_Socket_GetFileDirSucess);
						intent.putExtra("dirName", dir);
						getFileInfo.socketObj.getContext().sendBroadcast(intent);
					}else{
						synchronized (SocketUtil.recDir) {
							SocketUtil.recDir.put(dir, count);
						}
					}
				}
			}else{//调用getfile方法
				String dir = getFileInfo.path;//服务器路径   img/目录名字
				if(dir.contains("voice")||dir.contains("sight")){
					dir = getFileInfo.filename;
					dir = getFileInfo.filename;
				}
				if(SocketUtil.recDir.get(dir)!=null){//表明缓存里边有此目录
					int count = SocketUtil.recDir.get(dir);
					count--;
					if(count==0){//表明此目录下载完成,发送通知更新界面添加row
						synchronized (SocketUtil.recDir) {
							SocketUtil.recDir.remove(dir);
						}
						Intent intent = new Intent(Constants.Action_Socket_GetFileDirSucess);
						intent.putExtra("dirName", dir);
						getFileInfo.socketObj.getContext().sendBroadcast(intent);
					}else{
						synchronized (SocketUtil.recDir) {
							SocketUtil.recDir.put(dir, count);
						}
					}
				}
				Intent intent = new Intent(Constants.DOWNLOADSUCCESS);
				intent.setAction(Constants.Action_Socket_GetFileSuccess);//聊天界面视频或语音下载成功刷新
				intent.putExtra(Constants.DOWNLOADSUCCESS, getFileInfo.filename);
				Tool.showLog(Constants.basePath, getFileInfo.filename + "文件下载成功");
				getFileInfo.socketObj.getContext().sendBroadcast(intent);
			}
		}
	}

	private Boolean getFile(GetFileInfo fileInfo) {
		String newPath = fileInfo.myPath + "/" + fileInfo.filename;
		File file = new File(fileInfo.parentPath + newPath + ".bap");
		if (fileInfo.parentPath == null) fileInfo.parentPath = Constants.rootPath;
		if (!file.exists()) 
			file.getParentFile().mkdirs();
		else 
			file.delete();
		try {
			file.createNewFile();
		} catch (IOException e) {
			Tool.showLog(Constants.basePath, e.toString());
		}
		if (TextUtils.isEmpty(fileInfo.serverIp)) fileInfo.serverIp = fileInfo.socketObj.getIp();
		long pos = file.length();
		PkgHead head = new PkgHead();
		head.set_VerNum((byte) 3);
		head.set_MacFrom(fileInfo.socketObj.getMac());
		head.set_MacTo("00-00-00-00-00-00");
		head.set_LocalIp(fileInfo.socketObj.getIp());
		head.set_InfoType(InfoType.GetFile);
		String content = fileInfo.filename + "§" + fileInfo.path + "§"+ fileInfo.myPath + "§" + pos;
		Socket socket;
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress(fileInfo.serverIp,fileInfo.serverPort==0?fileInfo.socketObj.getPort():fileInfo.serverPort), 2000);
			Constants.serverConnected = true;
		} catch (Exception e) {
			Constants.serverConnected = false;
			Tool.showLog(Constants.basePath,"conn1 getF error::"+e.toString());
			SocketUtil.ConnectServerTH(fileInfo.socketObj);
			return false;
		}
		try {
			byte[] b = content.getBytes("utf-8");
			head.set_InfoLen(b.length);
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(head.get_NetHeadByte(), 0,head.get_NetHeadByte().length);
			outputStream.write(b, 0, b.length);
			InputStream inputStream = socket.getInputStream();
			byte[] buffer = Tool.getByte(Constants.HeaderLength, inputStream);
			PkgHead head1 = new PkgHead();
			if (buffer == null)
				return false;
			head1.set_NetHeadByte(buffer);
			byte[] infoByte = Tool.getByte(head1.get_InfoLen(), inputStream);
			String str = new String(infoByte, "utf-8");
			if (str.split("§").length < 3) {
				socket.close();
				return false;
			}
			String FileName = str.split("§")[0];
			String path = str.split("§")[1];
			FileOutputStream fileOutputStream = new FileOutputStream(file, true);
			byte[] data = new byte[1024];
			long position = 0;
			if (head1.get_DataLen() < 1024)
				buffer = new byte[(int) head1.get_DataLen()];
			while (position < head1.get_DataLen()) {
				if ((head1.get_DataLen() - position) < data.length)
					data = new byte[(int) (head1.get_DataLen() - position)];
				int len = inputStream.read(data);
				position += (long) len;
				fileOutputStream.write(data, 0, len);
			}
			String newPath1 = path + "/" + FileName;
			File file2 = new File(fileInfo.parentPath + newPath1);
			if (file2.exists()) {
				file2.delete();
			}
			boolean bool = file.renameTo(file2);
			if (!bool) {
				file.delete();
			}

			fileOutputStream.flush();
			fileOutputStream.close();
			fileOutputStream = null;
			// file2.setWritable(true);
			// file2.setLastModified(DateTool.convertTimelong(time));
			socket.close();
			head.Clear();
			head = null;

			if (lisenter != null) {
				lisenter.OnSuccess(file2.getAbsolutePath());
			}
		} catch (Exception e) {
			Tool.showLog(Constants.basePath, e.toString());
			return false;
		}
		return true;
	}
}
