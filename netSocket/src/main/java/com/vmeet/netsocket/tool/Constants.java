package com.vmeet.netsocket.tool;

import android.os.Build;
import android.os.Environment;

import com.vmeet.netsocket.bean.ClientType;

/**
 * 常量类
 */
public class Constants {
	public static final String basePath="com.vmeet.net";
	public static String WEB_IP;
	public static String SERVER_IP = "139.129.10.71" ;// "10.100.1.120";// "192.168.1.105" ;// "221.238.40.119";//      当前Ip..192.168.1.199..42.96.153.238..211.99.30.157..60.29.64.132
	public static int SERVER_PORT = 30002; //消息端口     6060端口
//	public static int SERVER_PORT = 30004; //消息端口   6061端口
	public static int WEB_PORT;
	public static final int HeaderLength=37;//默认消息头长度
	public static int clientType=ClientType.SYS.value();//默认sendLog类型
	public static Thread ConnectServerThread;
	public static boolean serverConnected = true;
	public static String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
	public static String LogName = getPhoneType();// 向服务器注册的name 
	
	//=====前端接收的所有action=====
	//收到tblnotice Calendar进行刷新通知
	public static final String Action_Socket_Notice = basePath+".socket_notice";
	//收到tblnoticerep 回复页面进行刷新
	public static final String Action_Socket_Notice_Rep = basePath+".socket_notice_rep";
	//sendfile发送成功提醒action,聊天页面图片显示
	public static final String Action_Socket_SendFileSuccess = basePath+".socket_sendfileSuccess";
	//getfile成功提醒action,聊天页面图片显示
	public static final String Action_Socket_GetFileSuccess = basePath+".socket_getfileSuccess";
	//getfile中getfiledir成功刷新提醒action
	public static final String Action_Socket_GetFileDirSucess = basePath+".socket_getfiledirSucess";
	//sendfile中sendfiledir成功刷新提醒action
	public static final String Action_Socket_SendFileDirSucess = basePath+".socket_sendfiledirSucess";
	//userlist刷新提醒action
	public static final String Action_Socket_UserListSet = basePath+".socket_userlist";
	//棋盘界面中的收到棋盘命令是的广播
	public static final String Action_Socket_Chess=basePath+".socket_chess";
	//用户状态更新action
	public static final String Action_UserState = basePath+".Action_UserState";
	//应用更新通知
	public static final String Action_Update_App = basePath + ".Action_Update_App";
	//msg气泡通知
	public static final String Action_Msg_Data = basePath + ".Action_Msg_Data";
	//发消息后lanuncheractivity进行刷新
	public static final String Action_Update_Launcher = basePath + ".Action_Update_Launcher";
	//退出登录
	public static final String Action_Exit_Activity = basePath+".Action_Exit_Activity";
	//应用applayfragment刷新
	public static final String Action_Apply_Refresh = basePath+".Action_Apply_Refresh";
	//后台server广播接收action
	public static final String Action_Server = basePath+".server";
	public static final String Action_Socket_Alarm = basePath+".socket_alarm";
	//点击Notification进入welAcitiy
	public static final String Action_OpenWel_Activity = basePath + ".Action_OpenWel_Activity";

	public static final String DOWNLOADSUCCESS=basePath+".DOWNLOADSUCCESS";//下载文件成功action
	public static final String SENDSUCCESS=basePath+".SENDSUCCESS";//上传文件成功action
	public static final String DIRSENDSUCCESS=basePath+".DIRSENDSUCCESS";//文件夹上传成功action
	
	public static String getPhoneType() {
		// 硬件制造商（手机品牌）
		String type = Build.MANUFACTURER;
		// 手机版本
		String model = Build.MODEL;
		return type + ":" + model;
	}
	public static void showLog(Object text) {
		android.util.Log.e("com.vmeet.net", "---" + text + "---");
	}
}
