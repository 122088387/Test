package com.vmeet.netsocket;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.vmeet.netsocket.bean.ClientType;
import com.vmeet.netsocket.bean.GetFileInfo;
import com.vmeet.netsocket.bean.InfoType;
import com.vmeet.netsocket.bean.MessageObj;
import com.vmeet.netsocket.bean.PkgHead;
import com.vmeet.netsocket.bean.SendFileInfo;
import com.vmeet.netsocket.bean.SocketObj;
import com.vmeet.netsocket.task.GetDataAysnc;
import com.vmeet.netsocket.task.GetFileAysncTask;
import com.vmeet.netsocket.task.GetFilesAysncTask;
import com.vmeet.netsocket.task.GetUserListAysnc;
import com.vmeet.netsocket.task.GetUserStatAysnc;
import com.vmeet.netsocket.task.SendFileAysncTask;
import com.vmeet.netsocket.task.SendHeartBeatAysncTask;
import com.vmeet.netsocket.task.SendMessageAysncTask;
import com.vmeet.netsocket.tool.Constants;
import com.vmeet.netsocket.tool.OnSocketLisenter;
import com.vmeet.netsocket.tool.Tool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Socket封装类
 */
public class SocketUtil {
	private static ArrayList<SendFileInfo> sendFileInfos = new ArrayList<SendFileInfo>();
	private static ArrayList<GetFileInfo> getFileInfos = new ArrayList<GetFileInfo>();
	private static ArrayList<MessageObj> sendMessageObjs = new ArrayList<MessageObj>();
	private static ExecutorService recService = Executors.newFixedThreadPool(5);// 接收文件线程池保持，5队列同时运行
	private static ExecutorService sendService = Executors.newFixedThreadPool(5);// 发送文件线程池保持，5队列同时运行
	private static ExecutorService sendMessageService = Executors.newFixedThreadPool(1);// 发送消息线程池保持，1
	public static boolean ConfirmStop = false;
	public static boolean isConnecting = false;
	private static Socket longConnSocket = new Socket();
	
//	public static HashMap<String, Integer> recCountDir = new HashMap<String, Integer>();
	public static HashMap<String, Integer> recDir = new HashMap<String, Integer>();//存放的是目录名与文件数量的集合
	public static HashMap<String, Integer> sendDir = new HashMap<String, Integer>();//存放的是目录名与文件数量的集合
	
	private static AlarmManager alarmMgr;
	private static PendingIntent sender;
	private static Intent alarmIntent;
	public static void setAlertManager(Context context) {
		if (alarmMgr == null)
			alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		if (alarmIntent == null)
			alarmIntent = new Intent(Constants.Action_Socket_Alarm);
		if (sender == null)
			sender = PendingIntent.getBroadcast(context, 0,
					alarmIntent, 0);
		alarmMgr.cancel(sender);
		// 5秒后发送广播，然后每个10秒重复发广播。广播都是直接发到AlarmReceiver的
		alarmMgr.setRepeating(AlarmManager.RTC, SystemClock.elapsedRealtime(),
				1000*5, sender);
		// alarmMgr.setRepeating(AlarmManager.RTC,
		// SystemClock.elapsedRealtime(),10*60*1000, sender);
	}
	
	/**
	 * 发送心跳包
	 */
	public static void sendHeartBeat(SocketObj socketObj){
		SendHeartBeatAysncTask heartBeatAysncTask = new SendHeartBeatAysncTask(socketObj);
		heartBeatAysncTask.executeOnExecutor(sendMessageService);
	}
	/**
	 * 启动长连接
	 */
	public static void ConnectServerTH(final SocketObj socketObj) {
		ConfirmStop = true;// 先关掉确认，防止丢包
		try {
			Thread.sleep(200);// 确保原有接收完成
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			longConnSocket.close();// 如果原有长连接存在，引发ReceiveCls退出，自动更换参数地址及客户端类型
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (Constants.ConnectServerThread != null && Constants.ConnectServerThread.isAlive()) {
			return;
		}
		Constants.ConnectServerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				ConnectServer(socketObj);
			}
		});
		Constants.ConnectServerThread.start();
	}

	private static void ConnectServer(SocketObj socketObj) {
		ConfirmStop = true;// 先关掉确认，防止丢包
		longConnSocket = new Socket();
		try {
			longConnSocket.connect(new InetSocketAddress(Constants.SERVER_IP, Constants.SERVER_PORT), 2000);
			Constants.serverConnected = true;
			ConfirmStop = false; // 打开与服务器的确认机制
			sendLog(socketObj.getMac(),Constants.SERVER_IP);// log发送注册机制，重要
			DoMsg(null);
			DoReq(null);
			DoSend(null);
			ReceiveCls(longConnSocket,socketObj);// 循环接受方法
		} catch (IOException e) {
			Tool.showLog(Constants.basePath,"ConnectServer" + e.toString());
		} finally {
			Constants.serverConnected = false;
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
			}
			ConnectServer(socketObj);
		}
	}
	/**
	 * 长连接接收线程
	 * 
	 * @throws IOException
	 */
	private static void ReceiveCls(Socket socket,SocketObj socketObj) {
		try {
			InputStream inputStream = socket.getInputStream();
			while (true) {
				System.out.print("哈哈哈哈哈哈哈");
				Log.i("哈哈哈", "哈哈哈哈哈哈哈");
				byte[] HeadByte1 = Tool.getByte(Constants.HeaderLength,
						inputStream); // 接受消息头
				if (HeadByte1 == null || HeadByte1[0] == 0 || HeadByte1[0] >= 4) {
					break;// 接受混乱退出
				}
				PkgHead head1 = new PkgHead();
				head1.set_NetHeadByte(HeadByte1);
				if (head1.get_InfoLen() == 0)
					continue;// 0长度退出
				byte[] infoByte1 = Tool.getByte(head1.get_InfoLen(),
						inputStream);

				if (Constants.clientType == ClientType.SYS.value()) {
					messageConfirm(socketObj);
				}

				String str1 = new String(infoByte1, "utf-8");
				System.out.print(str1);
				MessageObj msgbean = new MessageObj(head1.get_InfoType(),
						head1.get_DataLen(), head1.get_MacFrom(),
						head1.get_MacTo(), str1,socketObj.getIp(),socketObj.getPort(),socketObj.getIp());
				Intent intent = new Intent(Constants.Action_Server);
				intent.putExtra("MessageObj", msgbean);
				intent.putExtra("msg", msgbean.content);
				intent.putExtra("LocalIp", head1.get_LocalIp());
				socketObj.getContext().sendBroadcast(intent);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					Tool.showLog(Constants.basePath,"recssleep" + e.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Tool.showLog(Constants.basePath, "recs" + e.toString());
		} finally {
			ConfirmStop = true;
			if(socket!=longConnSocket)
				return;
			try {
				socket.close();
			} catch (IOException e1) {
			}
		}
	}
	/**
	 * 向服务器发送Log请求（onLine）
	 * 
	 * @throws IOException
	 * 
	 */
	private static void sendLog(String mac,String ip) throws IOException {
		PkgHead head = new PkgHead();
		head.set_VerNum((byte) 2);
		head.set_MacFrom(mac);
		head.set_MacTo("00-00-00-00-00-00");
		head.set_LocalIp(ip);
		head.set_ClientType(Constants.clientType);
		head.set_InfoType(InfoType.Log);
		byte[] bs = (Constants.LogName).getBytes("utf-8");
		head.set_InfoLen(bs.length);
		OutputStream outputStream = longConnSocket.getOutputStream();
		outputStream.write(head.get_NetHeadByte(), 0, head.get_NetHeadByte().length);
		outputStream.write(bs, 0, bs.length);
		System.out.println("登陆成功"+mac + "00-00-00-00-00-00" + ip + Constants.clientType + InfoType.Log);
		head.Clear();
		head = null;

	}
	
	private static void messageConfirm(SocketObj socketObj) throws IOException {
			PkgHead head = new PkgHead();
			head.set_VerNum((byte) 3);
			head.set_MacFrom(socketObj.getMac());
			head.set_MacTo("00-00-00-00-00-00");
			head.set_LocalIp(socketObj.getIp());
			head.set_InfoType(InfoType.Comm);
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(socketObj.getIp(),socketObj.getPort()), 2000);
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(head.get_NetHeadByte(), 0, head.get_NetHeadByte().length);
			socket.close();
			head.Clear();
	}
	
	public static void getUserStat(String mac,SocketObj socketObj) {
		new GetUserStatAysnc(mac,socketObj).execute("");
	}

	public static void getUserList(String ip, int port,SocketObj socketObj) {
		new GetUserListAysnc(ip, port,socketObj).execute("");
	}

	/**
	 * 发送文件
	 * 
	 * @param fileInfo
	 * @param sendLisenter
	 */
	public static void sendFile(SendFileInfo fileInfo, OnSocketLisenter<String> sendLisenter) {
		synchronized (sendFileInfos) {
			sendFileInfos.add(fileInfo);
		}
		DoSend(sendLisenter);
	}

	/**
	 * 下载文件
	 * 
	 * @param getFileInfo
	 * @param lisenter
	 */
	public static void getFile(GetFileInfo getFileInfo, OnSocketLisenter<Object> lisenter) {
		synchronized (getFileInfos) {
			getFileInfos.add(getFileInfo);
		}
		DoReq(lisenter);
	}
	
	private static GetFilesAysncTask task;

	/**
	 * 请求服务器指定路径下的所有文件
	 * 
	 * @param fileInfo
	 *            fileInfo.path 服务器文件路径 2 fileInfo.myPath 3 本地文件夹路径
	 *            fileInfo.serverIp 服务器Ip 5 fileInfo.serverPort 服务器Port 6
	 */
	public static void getFiles(GetFileInfo fileInfo) {
		task = new GetFilesAysncTask();
		task.executeOnExecutor(recService, fileInfo);
//		SocketUtil.recCountDir.clear();
	}

	/**
	 * 发送消息
	 * 
	 * @param msgObj
	 * @param lisenter
	 */

	public static void sendMessage(MessageObj msgObj, OnSocketLisenter<String> lisenter) {
		synchronized (sendMessageObjs) {
			sendMessageObjs.add(msgObj);
		}
		DoMsg(lisenter);
	}



	/**
	 * 上传队列
	 */
	public static void DoSend(OnSocketLisenter<String> lisenter) {
		if (!Constants.serverConnected)
			return;
		if (sendFileInfos.size() == 0)
			return;
		SendFileInfo fileInfo = null;
		synchronized (sendFileInfos) {
			fileInfo = sendFileInfos.remove(0);
		}
		if (fileInfo != null) {
			SendFileAysncTask aysncTask = new SendFileAysncTask(lisenter);
			aysncTask.executeOnExecutor(sendService, fileInfo);
		}
		DoSend(lisenter);
	}

	/**
	 * 下载队列
	 * 
	 * @param lisenter
	 */
	public static void DoReq(OnSocketLisenter<Object> lisenter) {
		if (!Constants.serverConnected)
			return;
		if (getFileInfos.size() == 0)
			return;
		GetFileInfo getFileInfo = null;
		synchronized (getFileInfos) {
			getFileInfo = getFileInfos.remove(0);
		}
		if (getFileInfo != null) {
			GetFileAysncTask aysncTask = new GetFileAysncTask(lisenter);
			aysncTask.executeOnExecutor(recService, getFileInfo);
		}
		DoReq(lisenter);
	}

	public static void DoMsg(OnSocketLisenter<String> lisenter) {
		if (!Constants.serverConnected)
			return;
		if (sendMessageObjs.size() == 0)
			return;
		MessageObj messageObj = null;
		synchronized (sendMessageObjs) {
			messageObj = sendMessageObjs.remove(0);
		}
		if (messageObj != null) {
			SendMessageAysncTask aysncTask = new SendMessageAysncTask(lisenter);
			aysncTask.executeOnExecutor(sendMessageService, messageObj);
		}
		DoMsg(lisenter);
	}
	
	/**
	 * 上载指定目录到服务器
	 * 
	 * @param mypath
	 *            本地路径
	 * @param SrvPath
	 *            服务器路径
	 * @param ServerIP
	 *            服务器ip
	 * @param ServerPort
	 *            服务器端口
	 */
	public static void sendFiles(String mypath, String SrvPath, String ServerIP, int ServerPort,SocketObj socketObj) {
		File rootfile = new File(mypath);
		if (!rootfile.exists())
			return;
		File[] files = rootfile.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				SendFileInfo sendfile = new SendFileInfo(mypath + "/" + file.getName(), SrvPath, null,
						InfoType.SendFile, ServerIP, ServerPort, 1,socketObj);
				sendFile(sendfile, null);
			} else if (file.isDirectory()) {
				sendFiles(mypath + "/" + file.getName(), SrvPath + "\\" + file.getName(),ServerIP, ServerPort,socketObj);
			}
		}
	}
	/**
	 * 数据查询
	 * @param ip 服务器Ip
	 * @param port 服务器port
	 * @param content 查询内容
	 * @param infoType 查询类型
	 * @return 返回结果
	 */
	public static String getData(String ip,int port,String content,
								 InfoType infoType,SocketObj socketObj){
		String str = "";
		try {
			str =  new GetDataAysnc(ip, port,content,infoType,socketObj).execute("").get();
		} catch (InterruptedException e) {
		} catch (ExecutionException e) {
		}
		return str;
	}
}