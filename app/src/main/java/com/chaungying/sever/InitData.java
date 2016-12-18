package com.chaungying.sever;

import com.chaungying.MyApplication;
import com.vmeet.netsocket.Helper4Data;
import com.vmeet.netsocket.data.Col;




public class InitData {

	public static String msgColsPath = MyApplication.approotPath + "Data/MSGSET/cols";
	public static String msgRowsPath = MyApplication.approotPath + "Data/MSGSET/rows";
	public static void initMsgSet(){
		Helper4Data.creatSet("MSGSET",msgColsPath,msgRowsPath);
		createMsglist();
		createRecentlist();
		createCollectList();
		MyApplication.MSGSET.viewDidLoad(msgColsPath,msgRowsPath);
	}
	private static void createMsglist() {
		Helper4Data.creatTbl("MSGSET","msglist",msgColsPath);
		 Col col1 = new Col();
	        col1.idx = 4; col1.name = "infoType"; col1.headTxt = "消息类型"; Helper4Data.AddCol("MSGSET", "msglist",msgColsPath, col1.toString());
	        col1.idx = 5; col1.name = "msgTxt"; col1.headTxt = "文字"; Helper4Data.AddCol("MSGSET", "msglist",msgColsPath, col1.toString());
	        col1.idx = 6; col1.name = "datetime"; col1.headTxt = "时间"; Helper4Data.AddCol("MSGSET", "msglist",msgColsPath, col1.toString());
	        col1.idx = 7; col1.name = "DoT"; col1.headTxt = "发送方向"; Helper4Data.AddCol("MSGSET", "msglist",msgColsPath, col1.toString());
	        col1.idx = 8; col1.name = "recs"; col1.headTxt = "接收人"; Helper4Data.AddCol("MSGSET", "msglist",msgColsPath, col1.toString());
	        col1.idx = 9; col1.name = "sender"; col1.headTxt = "发送人"; Helper4Data.AddCol("MSGSET", "msglist",msgColsPath, col1.toString());
	        col1.idx = 10; col1.name = "labeltxt"; col1.headTxt = "辅助标签"; Helper4Data.AddCol("MSGSET", "msglist",msgColsPath, col1.toString());
	        col1.headTxt = null;
	        col1.idx = 11; col1.name = "py"; Helper4Data.AddCol("MSGSET", "msglist",msgColsPath, col1.toString());
	        col1.idx = 12; col1.name = "tag"; Helper4Data.AddCol("MSGSET", "msglist",msgColsPath, col1.toString());
	        col1.idx = 13;col1.name = "searchKey";col1.headTxt = "查询主键"; Helper4Data.AddCol("MSGSET", "msglist",msgColsPath, col1.toString());
	        col1.idx = 14;col1.name = "senderName";col1.headTxt = "发送人姓名"; Helper4Data.AddCol("MSGSET", "msglist",msgColsPath, col1.toString());
	        col1.idx = 15;col1.name = "groupMac";col1.headTxt = "群Mac"; Helper4Data.AddCol("MSGSET", "msglist",msgColsPath, col1.toString());
	        col1.idx = 16;col1.name = "groupName";col1.headTxt = "群名字"; Helper4Data.AddCol("MSGSET", "msglist",msgColsPath, col1.toString());
	        col1.idx = 17;col1.name = "isRead";col1.headTxt = "是否已读"; Helper4Data.AddCol("MSGSET", "msglist",msgColsPath, col1.toString());
	        col1.idx = 18;col1.name = "duration";col1.headTxt = "录音时长"; Helper4Data.AddCol("MSGSET", "msglist",msgColsPath, col1.toString());
			col1.idx = 19;col1.name = "fileSize";col1.headTxt = "资料长度"; Helper4Data.AddCol("MSGSET", "msglist",msgColsPath, col1.toString());
	}
	private static void createRecentlist() {
		Helper4Data.creatTbl("MSGSET","recentlist",msgColsPath);
		 Col col1 = new Col();
		 //infoType:单聊single,群聊：group
	        col1.idx = 4; col1.name = "infoType"; col1.headTxt = "消息类型"; Helper4Data.AddCol("MSGSET", "recentlist",msgColsPath, col1.toString());
	        col1.idx = 5; col1.name = "msgTxt"; col1.headTxt = "最新记录"; Helper4Data.AddCol("MSGSET", "recentlist",msgColsPath, col1.toString());
	        col1.idx = 6; col1.name = "datetime"; col1.headTxt = "时间"; Helper4Data.AddCol("MSGSET", "recentlist",msgColsPath, col1.toString());
	        col1.idx = 7; col1.name = "isRead"; col1.headTxt = "isRead"; Helper4Data.AddCol("MSGSET", "recentlist",msgColsPath, col1.toString());
	        col1.idx = 8; col1.name = "isTop"; col1.headTxt = "置顶聊天";
		Helper4Data.AddCol("MSGSET", "recentlist",msgColsPath, col1.toString());
	        col1.idx = 9; col1.name = "sendMac"; col1.headTxt = "群成员Macs"; Helper4Data.AddCol("MSGSET", "recentlist",msgColsPath, col1.toString());
	        col1.idx = 10; col1.name = "name"; col1.headTxt = "名字"; Helper4Data.AddCol("MSGSET", "recentlist",msgColsPath, col1.toString());
	        col1.idx = 11; col1.name = "rowCreatMac"; col1.headTxt = "群创建人";
		Helper4Data.AddCol("MSGSET", "recentlist",msgColsPath, col1.toString());
	}
	private static void createCollectList(){
		Helper4Data.creatTbl("MSGSET", "collectList",msgColsPath);
		Col col1 = new Col();
		col1.idx = 4; col1.name = "infoType"; col1.headTxt = "消息类型"; Helper4Data.AddCol("MSGSET", "collectList",msgColsPath, col1.toString());
		col1.idx = 5; col1.name = "msgTxt"; col1.headTxt = "文字"; Helper4Data.AddCol("MSGSET", "collectList",msgColsPath, col1.toString());
		col1.idx = 6; col1.name = "datetime"; col1.headTxt = "时间"; Helper4Data.AddCol("MSGSET", "collectList",msgColsPath, col1.toString());
		col1.idx = 7; col1.name = "sender"; col1.headTxt = "发送人"; Helper4Data.AddCol("MSGSET", "collectList",msgColsPath, col1.toString());
		col1.idx = 8;col1.name = "searchKey";col1.headTxt = "查询主键"; Helper4Data.AddCol("MSGSET", "collectList",msgColsPath, col1.toString());
		col1.idx = 9;col1.name = "senderName";col1.headTxt = "发送人姓名"; Helper4Data.AddCol("MSGSET", "collectList",msgColsPath, col1.toString());
		col1.idx = 10;col1.name = "duration";col1.headTxt = "录音时长"; Helper4Data.AddCol("MSGSET", "collectList",msgColsPath, col1.toString());
		col1.idx = 11;col1.name = "fileSize";col1.headTxt = "文件大小"; Helper4Data.AddCol("MSGSET", "collectList",msgColsPath, col1.toString());
	}

	public static String baseColsPath = MyApplication.approotPath + "Data/BASE/cols";
	public static String baseRowsPath = MyApplication.approotPath + "Data/BASE/rows";

	public static void initBaseSet(){
		Helper4Data.creatSet("BASE", baseColsPath, baseRowsPath);
		createUserlist();
		createGrouplist();
		createEpuserlist();
		createTransTbl("tranuserlist");//地区
		createTransTbl("transexuserlist");//性别
		createTransTbl("tranofficeuserlist");//部门
		createTransTbl("tranunitlist");//单位
		createTransTbl("tranpostlist");//职务
		createTransTbl("tranlevellist");//级别
		MyApplication.BASE.viewDidLoad(baseColsPath, baseRowsPath);
	}

	private static void createUserlist() {
		Helper4Data.creatTbl("BASE", "userlist",baseColsPath);
		Col col1 = new Col();
		col1.idx = 4; col1.name = "name"; col1.headTxt = "姓名"; Helper4Data.AddCol("BASE", "userlist",baseColsPath, col1.toString());
		col1.idx = 5; col1.name = "phone"; col1.headTxt = "电话"; Helper4Data.AddCol("BASE", "userlist",baseColsPath, col1.toString());
		col1.idx = 6; col1.name = "py"; col1.headTxt = "缩拼"; Helper4Data.AddCol("BASE", "userlist",baseColsPath, col1.toString());
		col1.idx = 7; col1.name = "alpha"; col1.headTxt = "姓名首字母"; Helper4Data.AddCol("BASE", "userlist",baseColsPath, col1.toString());
		col1.idx = 8; col1.name = "mail"; col1.headTxt = "邮件";
		Helper4Data.AddCol("BASE", "userlist",baseColsPath, col1.toString());
		col1.idx = 9; col1.name = "dep"; col1.headTxt = "地区";
		Helper4Data.AddCol("BASE", "userlist",baseColsPath, col1.toString());
	    col1.idx = 10; col1.name = "unit"; col1.headTxt = "单位";
		Helper4Data.AddCol("BASE", "userlist",baseColsPath, col1.toString());
		col1.idx = 11; col1.name = "office"; col1.headTxt = "部门";
		Helper4Data.AddCol("BASE", "userlist",baseColsPath, col1.toString());
		col1.idx = 12; col1.name = "post"; col1.headTxt = "岗位";
		Helper4Data.AddCol("BASE", "userlist",baseColsPath, col1.toString());
		col1.idx = 13; col1.name = "level"; col1.headTxt = "级别";
		Helper4Data.AddCol("BASE", "userlist",baseColsPath, col1.toString());
		col1.idx = 14; col1.name = "sex"; col1.headTxt = "性别";
		Helper4Data.AddCol("BASE", "userlist",baseColsPath, col1.toString());
		col1.idx = 15; col1.name = "selected"; col1.headTxt = "是否选择"; Helper4Data.AddCol("BASE", "userlist",baseColsPath, col1.toString());
	}
	private static void createGrouplist() {
		Helper4Data.creatTbl("BASE", "grouplist",baseColsPath);
		Col col1 = new Col();
		col1.idx = 4; col1.name = "name"; col1.headTxt = "群名"; Helper4Data.AddCol("BASE", "grouplist",baseColsPath, col1.toString());
		col1.idx = 5; col1.name = "members"; col1.headTxt = "群成员"; Helper4Data.AddCol("BASE", "grouplist",baseColsPath, col1.toString());
		col1.idx = 6;col1.name = "names"; col1.headTxt = "群成员名"; Helper4Data.AddCol("BASE", "grouplist",baseColsPath, col1.toString());
	}
	private static void createEpuserlist() {
		Helper4Data.creatTbl("BASE", "epuserlist",baseColsPath);
		Col col1 = new Col();
		col1.idx = 4; col1.name = "name"; col1.headTxt = "姓名"; Helper4Data.AddCol("BASE", "epuserlist",baseColsPath, col1.toString());
		col1.idx = 5; col1.name = "phone"; col1.headTxt = "电话"; Helper4Data.AddCol("BASE", "epuserlist",baseColsPath, col1.toString());
		col1.idx = 6; col1.name = "py"; col1.headTxt = "缩拼"; Helper4Data.AddCol("BASE", "epuserlist",baseColsPath, col1.toString());
		col1.idx = 7; col1.name = "alpha"; col1.headTxt = "姓名首字母"; Helper4Data.AddCol("BASE", "epuserlist",baseColsPath, col1.toString());
		col1.idx = 8; col1.name = "mail"; col1.headTxt = "邮件";
		Helper4Data.AddCol("BASE", "epuserlist",baseColsPath, col1.toString());
		col1.idx = 9; col1.name = "dep"; col1.headTxt = "地区";
		Helper4Data.AddCol("BASE", "epuserlist",baseColsPath, col1.toString());
	    col1.idx = 10; col1.name = "unit"; col1.headTxt = "单位";
		Helper4Data.AddCol("BASE", "epuserlist",baseColsPath, col1.toString());
		col1.idx = 11; col1.name = "office"; col1.headTxt = "部门";
		Helper4Data.AddCol("BASE", "epuserlist",baseColsPath, col1.toString());
		col1.idx = 12; col1.name = "post"; col1.headTxt = "岗位";
		Helper4Data.AddCol("BASE", "epuserlist",baseColsPath, col1.toString());
		col1.idx = 13; col1.name = "level"; col1.headTxt = "级别";
		Helper4Data.AddCol("BASE", "epuserlist",baseColsPath, col1.toString());
		col1.idx = 14; col1.name = "sex"; col1.headTxt = "性别";
		Helper4Data.AddCol("BASE", "epuserlist",baseColsPath, col1.toString());
		col1.idx = 15; col1.name = "selected"; col1.headTxt = "是否选择"; Helper4Data.AddCol("BASE", "epuserlist",baseColsPath, col1.toString());
		col1.idx = 16; col1.name = "qp"; col1.headTxt = "全拼"; Helper4Data.AddCol("BASE", "epuserlist",baseColsPath, col1.toString());
	}
	private static void createTransTbl(String tblName){
		Helper4Data.creatTbl("BASE", tblName,baseColsPath);
		Col col1 = new Col();
		col1.idx = 4; col1.name = "name"; col1.headTxt = "姓名"; Helper4Data.AddCol("BASE", tblName,baseColsPath, col1.toString());
		col1.idx = 5; col1.name = "phone"; col1.headTxt = "电话"; Helper4Data.AddCol("BASE",tblName,baseColsPath, col1.toString());
		col1.idx = 6; col1.name = "py"; col1.headTxt = "缩拼"; Helper4Data.AddCol("BASE", tblName,baseColsPath, col1.toString());
		col1.idx = 7; col1.name = "alpha"; col1.headTxt = "姓名首字母"; Helper4Data.AddCol("BASE", tblName,baseColsPath, col1.toString());
		col1.idx = 8; col1.name = "mail"; col1.headTxt = "邮件";
		Helper4Data.AddCol("BASE",tblName,baseColsPath, col1.toString());
		col1.idx = 9; col1.name = "dep"; col1.headTxt = "地区";
		Helper4Data.AddCol("BASE",tblName,baseColsPath, col1.toString());
		col1.idx = 10; col1.name = "unit"; col1.headTxt = "单位";
		Helper4Data.AddCol("BASE",tblName,baseColsPath, col1.toString());
		col1.idx = 11; col1.name = "office"; col1.headTxt = "部门";
		Helper4Data.AddCol("BASE", tblName,baseColsPath, col1.toString());
		col1.idx = 12; col1.name = "post"; col1.headTxt = "岗位";
		Helper4Data.AddCol("BASE", tblName,baseColsPath, col1.toString());
		col1.idx = 13; col1.name = "level"; col1.headTxt = "级别";
		Helper4Data.AddCol("BASE",tblName,baseColsPath, col1.toString());
		col1.idx = 14; col1.name = "sex"; col1.headTxt = "性别";
		Helper4Data.AddCol("BASE",tblName,baseColsPath, col1.toString());
		col1.idx = 15; col1.name = "selected"; col1.headTxt = "是否选择"; Helper4Data.AddCol("BASE", tblName,baseColsPath, col1.toString());
	}

	public static String logColsPath = MyApplication.approotPath + "Data/LOGSET/cols";
	public static String logRowsPath = MyApplication.approotPath + "Data/LOGSET/rows";

	public static void initLogSet(){
		Helper4Data.creatSet("LOGSET",logColsPath,logRowsPath);
		createLoglist();
		createTblLogShareList();
		MyApplication.LOGSET.viewDidLoad(logColsPath, logRowsPath);
	}

	private static void createLoglist() {
		Helper4Data.creatTbl("LOGSET", "loglist",logColsPath);
		Col col1 = new Col();
		col1.idx = 4; col1.name = "isRead"; col1.headTxt = "小红点"; Helper4Data.AddCol("LOGSET", "loglist",logColsPath, col1.toString());
		col1.idx = 5; col1.name = "imgs"; col1.headTxt = "图片"; Helper4Data.AddCol("LOGSET", "loglist",logColsPath, col1.toString());
		col1.idx = 6; col1.name = "logimg"; col1.headTxt = "日志图片"; Helper4Data.AddCol("LOGSET", "loglist", logColsPath, col1.toString());
		col1.idx = 6; col1.name = "content"; col1.headTxt = "内容"; Helper4Data.AddCol("LOGSET", "loglist",logColsPath, col1.toString());
		col1.idx = 7; col1.name = "topic"; col1.headTxt = "主题"; Helper4Data.AddCol("LOGSET", "loglist",logColsPath, col1.toString());
		col1.idx = 8; col1.name = "recs"; col1.headTxt = "谁可见"; Helper4Data.AddCol("LOGSET", "loglist",logColsPath, col1.toString());
		col1.idx = 9; col1.name = "time"; col1.headTxt = "时间"; Helper4Data.AddCol("LOGSET", "loglist",logColsPath, col1.toString());
	}
	private static void createTblLogShareList(){
		Helper4Data.creatTbl("LOGSET", "TblLogShareList",logColsPath);
		Col col1 = new Col();
		col1.idx = 4; col1.name = "isRead"; col1.headTxt = "小红点"; Helper4Data.AddCol("LOGSET", "TblLogShareList",logColsPath, col1.toString());
		col1.idx = 5; col1.name = "imgs"; col1.headTxt = "图片"; Helper4Data.AddCol("LOGSET", "TblLogShareList",logColsPath, col1.toString());
		col1.idx = 6; col1.name = "logimg"; col1.headTxt = "日志图片"; Helper4Data.AddCol("LOGSET", "TblLogShareList", logColsPath, col1.toString());
		col1.idx = 6; col1.name = "content"; col1.headTxt = "内容"; Helper4Data.AddCol("LOGSET", "TblLogShareList",logColsPath, col1.toString());
		col1.idx = 7; col1.name = "topic"; col1.headTxt = "主题"; Helper4Data.AddCol("LOGSET", "TblLogShareList",logColsPath, col1.toString());
		col1.idx = 8; col1.name = "recs"; col1.headTxt = "谁可见"; Helper4Data.AddCol("LOGSET", "TblLogShareList",logColsPath, col1.toString());
		col1.idx = 9; col1.name = "CreatTime"; col1.headTxt = "时间"; Helper4Data.AddCol("LOGSET", "TblLogShareList",logColsPath, col1.toString());
		col1.idx = 9; col1.name = "CreatMac"; col1.headTxt = "创建人mac"; Helper4Data.AddCol("LOGSET", "TblLogShareList",logColsPath, col1.toString());
	}

	public static String localColsPath = MyApplication.approotPath + "Data/LOCALSET/cols";
	public static String localRowsPath = MyApplication.approotPath + "Data/LOCALSET/rows";

	public static void initLocalSet(){
		Helper4Data.creatSet("LOCALSET", localColsPath, localRowsPath);
		createTblNotice();
		createTblNoticeRep();
		createTblMynotice();
		MyApplication.LOCALSET.viewDidLoad(localColsPath, localRowsPath);
	}
	private static void createTblNotice() {
		Helper4Data.creatTbl("LOCALSET", "tblnotice",localColsPath);
		Col col1 = new Col();
		col1.idx = 1; col1.name = "rowid1"; col1.Hidden = "Y"; Helper4Data.AddCol("LOCALSET", "tblnotice",localColsPath,col1.toString());
		col1.idx = 2; col1.name = "rowCreatTime"; col1.Hidden = "Y"; Helper4Data.AddCol("LOCALSET", "tblnotice",localColsPath, col1.toString());
		col1.idx = 3; col1.name = "rowCreatMac"; col1.headTxt="发起人";col1.Hidden = "R";col1.section = "Y";col1.type = "ltext";
		Helper4Data.AddCol("LOCALSET", "tblnotice",localColsPath, col1.toString());
		col1.idx = 4; col1.name = "ReceiversMAC"; col1.headTxt = "参与人MAC";col1.Hidden = "Y"; col1.section = "N"; Helper4Data.AddCol("LOCALSET", "tblnotice",localColsPath,col1.toString());
		col1.idx = 5; col1.name = "ReceiversName"; col1.headTxt = "参与人"; col1.Hidden = "R"; col1.section = "Y";col1.type = "ltext";
		Helper4Data.AddCol("LOCALSET", "tblnotice",localColsPath, col1.toString());
		col1.idx = 6; col1.name = "Topic"; col1.headTxt = "主题";col1.Hidden = "R"; col1.type = "ltext"; col1.selstr = "无主题"; col1.section = "N"; Helper4Data.AddCol("LOCALSET", "tblnotice",localColsPath, col1.toString());
		col1.idx = 7; col1.name = "Content"; col1.headTxt = "内容";col1.Hidden = "R"; col1.type = "htext"; col1.section = "Y"; Helper4Data.AddCol("LOCALSET", "tblnotice",localColsPath, col1.toString());
		col1.idx = 8; col1.name = "MeetingPlace"; col1.headTxt = "会址";col1.Hidden = "R"; col1.type = "ltext"; col1.section = "Y"; Helper4Data.AddCol("LOCALSET", "tblnotice",localColsPath, col1.toString());
		col1.idx = 9; col1.name = "MeetingData"; col1.headTxt = "文件"; col1.Hidden = "Y"; col1.type = "ltext"; col1.section = "N";
		Helper4Data.AddCol("LOCALSET", "tblnotice",localColsPath, col1.toString());
		col1.idx = 10; col1.name = "DataName"; col1.headTxt = "文件"; col1.Hidden = "R"; col1.type = "ltext"; col1.section = "Y";
		Helper4Data.AddCol("LOCALSET", "tblnotice",localColsPath, col1.toString());
		col1.idx = 11; col1.name = "AlarmTime"; col1.headTxt = "提醒时间";col1.Hidden = "R"; col1.type = "ltext"; col1.section = "N"; Helper4Data.AddCol("LOCALSET", "tblnotice",localColsPath, col1.toString());
		col1.idx = 12; col1.name = "ConfirmStatus"; col1.headTxt = "确认状态";col1.Hidden = "Y"; col1.section = "N"; Helper4Data.AddCol("LOCALSET", "tblnotice",localColsPath, col1.toString());
		col1.idx = 13; col1.name = "ReceiversConfirm"; col1.headTxt = "发送"; col1.Hidden = "R"; col1.type = "button"; col1.selstr = "确认参会⊕因故请假"; col1.section = "bottom"; Helper4Data.AddCol("LOCALSET", "tblnotice",localColsPath, col1.toString());
		col1.idx = 14; col1.name = "tblname"; col1.headTxt = "发起通知"; col1.Hidden = "Y"; col1.type = "tblname";
		Helper4Data.AddCol("LOCALSET", "tblnotice",localColsPath, col1.toString());
		col1.idx = 15; col1.name = "isRead"; col1.headTxt = "是否已读"; col1.Hidden = "Y"; Helper4Data.AddCol("LOCALSET", "tblnotice",localColsPath, col1.toString());
	}
	private static void createTblNoticeRep() {
		Helper4Data.creatTbl("LOCALSET", "tblnoticerep",localColsPath);
		Col col1 = new Col();
		col1.idx = 1; col1.name = "rowid1"; col1.Hidden = "Y"; Helper4Data.AddCol("LOCALSET", "tblnoticerep",localColsPath,col1.toString());
		col1.idx = 2; col1.name = "rowCreatTime"; col1.Hidden = "Y"; Helper4Data.AddCol("LOCALSET", "tblnoticerep",localColsPath, col1.toString());
		col1.idx = 3; col1.name = "rowCreatMac"; col1.headTxt="发起人";col1.Hidden = "Y";col1.section = "Y";col1.type = "ltext";
		Helper4Data.AddCol("LOCALSET", "tblnoticerep",localColsPath, col1.toString());
		col1.idx = 4; col1.name = "prowid1"; col1.headTxt = "父表编号";col1.Hidden = "FK"; col1.selstr = "rowid1"; Helper4Data.AddCol("LOCALSET", "tblnoticerep",localColsPath,col1.toString());
		col1.idx = 5; col1.name = "ReceiversMAC"; col1.headTxt = "回复人MAC";col1.Hidden = "Y"; col1.type = "sender";
		Helper4Data.AddCol("LOCALSET", "tblnoticerep",localColsPath, col1.toString());
		col1.idx = 6; col1.name = "UpdateTime"; col1.headTxt = "回复时间";col1.Hidden = "Y"; Helper4Data.AddCol("LOCALSET", "tblnoticerep",localColsPath, col1.toString());
		col1.idx = 7; col1.name = "Status"; col1.headTxt = "状态";col1.Hidden = "Y"; Helper4Data.AddCol("LOCALSET", "tblnoticerep",localColsPath, col1.toString());
		col1.idx = 8; col1.name = "Instructions"; col1.headTxt = "说明";col1.Hidden = "N"; col1.type = "htext"; col1.selstr = "晚到⊕会议冲突⊕出差"; col1.section = "N"; Helper4Data.AddCol("LOCALSET", "tblnoticerep",localColsPath, col1.toString());
		col1.idx = 9; col1.name = "recs"; col1.headTxt = "最初发起人";col1.Hidden = "Y"; Helper4Data.AddCol("LOCALSET", "tblnoticerep",localColsPath, col1.toString());
		col1.idx = 10;col1.name = "isRead"; col1.headTxt = "是否已读";
		Helper4Data.AddCol("LOCALSET", "tblnoticerep",localColsPath, col1.toString());
	}

	private static void createTblMynotice(){
		Helper4Data.creatTbl("LOCALSET", "mynotice",localColsPath);
		Col col1 = new Col();
		col1.idx = 1; col1.name = "rowid1"; col1.Hidden = "Y"; Helper4Data.AddCol("LOCALSET", "mynotice",localColsPath,col1.toString());
		col1.idx = 2; col1.name = "rowCreatTime"; col1.Hidden = "Y"; Helper4Data.AddCol("LOCALSET", "mynotice",localColsPath, col1.toString());
		col1.idx = 3; col1.name = "rowCreatMac"; col1.Hidden = "Y";
		Helper4Data.AddCol("LOCALSET", "mynotice",localColsPath, col1.toString());
		col1.idx = 4; col1.name = "Topic"; col1.headTxt = "主题";col1.Hidden = "N"; col1.type = "ltext"; col1.selstr = "无主题"; col1.section = "N"; Helper4Data.AddCol("LOCALSET", "mynotice",localColsPath, col1.toString());
		col1.idx = 5; col1.name = "Content"; col1.headTxt = "内容";col1.Hidden = "N"; col1.type = "htext"; col1.section = "Y"; Helper4Data.AddCol("LOCALSET", "mynotice",localColsPath, col1.toString());
		col1.idx = 6; col1.name = "AlarmTime"; col1.headTxt = "提醒时间";col1.Hidden = "N"; col1.type = "datetime"; col1.section = "N"; Helper4Data.AddCol("LOCALSET", "mynotice",localColsPath, col1.toString());
		col1.idx = 7; col1.name = "Sendbutton"; col1.headTxt = "保存"; col1.Hidden = "N"; col1.type = "button"; col1.selstr = "保存"; col1.section = "bottom";
		Helper4Data.AddCol("LOCALSET", "mynotice",localColsPath, col1.toString());
		col1.idx = 8;col1.name = "isRead"; col1.headTxt = "是否已读";
		Helper4Data.AddCol("LOCALSET", "mynotice",localColsPath, col1.toString());
	}

	public static String rssColsPath = MyApplication.approotPath + "Data/RSSSET/cols";
	public static String rssRowsPath = MyApplication.approotPath + "Data/RSSSET/rows";
	public static void initRssSet(){
		Helper4Data.creatSet("RSSSET",rssColsPath,rssRowsPath);
		createTblnews();
		createTblnewsComment();
		MyApplication.RSSSET.viewDidLoad(rssColsPath, rssRowsPath);
	}

	private static void createTblnews() {
		Helper4Data.creatTbl("RSSSET", "newslist", rssColsPath);
		Col col1 = new Col();col1.idx = 1;col1.name = "rowid1";col1.headTxt = "ID";
		Helper4Data.AddCol("RSSSET", "newslist", rssColsPath, col1.toString());
		col1.idx = 2;col1.name = "rowCreatTime";col1.headTxt = "时间";
		Helper4Data.AddCol("RSSSET", "newslist", rssColsPath, col1.toString());
		col1.idx = 4;col1.name = "Topic";col1.headTxt = "主题";
		Helper4Data.AddCol("RSSSET", "newslist", rssColsPath, col1.toString());
		col1.idx = 5;col1.name = "Content";col1.headTxt = "内容";
		Helper4Data.AddCol("RSSSET", "newslist", rssColsPath, col1.toString());
		col1.idx = 6;col1.name = "url";col1.headTxt = "网址";
		Helper4Data.AddCol("RSSSET", "newslist", rssColsPath, col1.toString());
		col1.idx = 7;col1.name = "img";col1.headTxt = "图片";
		Helper4Data.AddCol("RSSSET", "newslist", rssColsPath, col1.toString());
	}
	private static void createTblnewsComment() {
		Helper4Data.creatTbl("RSSSET", "newscommentlist", rssColsPath);
		Col col1 = new Col();
		col1.idx = 4; col1.name = "senderName"; col1.headTxt="评论人";
		Helper4Data.AddCol("RSSSET", "newscommentlist",rssColsPath, col1.toString());
		col1.idx = 5; col1.name = "prowid1"; col1.headTxt="父表编号";
		Helper4Data.AddCol("RSSSET", "newscommentlist",rssColsPath, col1.toString());
		col1.idx = 6; col1.name = "content"; col1.headTxt="评论内容";
		Helper4Data.AddCol("RSSSET", "newscommentlist",rssColsPath, col1.toString());
	}

	public static String orderColsPath = MyApplication.approotPath + "Data/ORDERSET/cols";
	public static String orderRowsPath = MyApplication.approotPath + "Data/ORDERSET/rows";


	public static void initOrderSet1(){
		Helper4Data.creatSet("ORDERSET", orderColsPath, orderRowsPath);
		createcarList();
        createmeetList();//还没完成
		createmaintainList();
		MyApplication.ORDERSET.viewDidLoad(orderColsPath, orderRowsPath);
	}

	public static void initOrderSet2(String department){
		Helper4Data.creatSet("ORDERSET", orderColsPath, orderRowsPath);
		createOrderTbl(department);
		MyApplication.ORDERSET.viewDidLoad(orderColsPath, orderRowsPath);
	}
	private static void createOrderTbl(String department) {
		Helper4Data.creatTbl("ORDERSET",department,orderColsPath);
		Col col1 = new Col();
		col1.idx = 4; col1.name = "mealType"; col1.headTxt = "就餐类型"; Helper4Data.AddCol("ORDERSET", department, orderColsPath, col1.toString());
		col1.idx = 5; col1.name = "foodType"; col1.headTxt = "食品种类"; Helper4Data.AddCol("ORDERSET", department,orderColsPath, col1.toString());
		col1.idx = 6; col1.name = "foodName"; col1.headTxt = "食品名称"; Helper4Data.AddCol("ORDERSET", department,orderColsPath, col1.toString());
		col1.idx = 7; col1.name = "foodImagePath"; col1.headTxt = "食品图片"; Helper4Data.AddCol("ORDERSET", department,orderColsPath, col1.toString());
		col1.idx = 8; col1.name = "foodPrice"; col1.headTxt = "食品价格"; Helper4Data.AddCol("ORDERSET", department,orderColsPath, col1.toString());
		col1.idx = 9; col1.name = "foodCount"; col1.headTxt = "食品数量"; Helper4Data.AddCol("ORDERSET", department,orderColsPath, col1.toString());
		col1.idx = 10; col1.name = "foodDescription"; col1.headTxt = "食品介绍"; Helper4Data.AddCol("ORDERSET", department,orderColsPath, col1.toString());
	}
	private static void createcarList(){
		Helper4Data.creatTbl("ORDERSET", "carList",orderColsPath);
		Col col1 = new Col();
		col1.idx = 1; col1.name = "rowid1"; col1.Hidden = "Y";col1.type = "text";
		Helper4Data.AddCol("ORDERSET", "carList",orderColsPath, col1.toString());
		col1.idx = 2; col1.name = "rowCreatTime"; col1.Hidden = "Y"; Helper4Data.AddCol("ORDERSET", "carList",orderColsPath, col1.toString());
		col1.idx = 3; col1.name = "rowCreatMac";col1.Hidden = "Y";
		Helper4Data.AddCol("ORDERSET", "carList",orderColsPath, col1.toString());
		col1.idx = 4; col1.name = "sender"; col1.headTxt="发起人";col1.Hidden = "Y";col1.section = "N";col1.type = "sender";
		Helper4Data.AddCol("ORDERSET", "carList",orderColsPath, col1.toString());
		col1.idx = 5; col1.name = "sqdw"; col1.headTxt="申请单位";col1.Hidden = "N";col1.section = "N";col1.type = "ltext";
		Helper4Data.AddCol("ORDERSET", "carList",orderColsPath, col1.toString());
		col1.idx = 6; col1.name = "ycbm"; col1.headTxt="用车部门";col1.Hidden = "N";col1.section = "Y";col1.type = "ltext";
		Helper4Data.AddCol("ORDERSET", "carList",orderColsPath, col1.toString());
		col1.idx = 7; col1.name = "qx"; col1.headTxt="去向";col1.Hidden = "N";col1.section = "Y";col1.type = "radiobox";col1.selstr = "区内⊕区外"; Helper4Data.AddCol("ORDERSET", "carList",orderColsPath, col1.toString());
		col1.idx = 8; col1.name = "ccr"; col1.headTxt="乘车人";col1.Hidden = "N";col1.section = "N";col1.type = "ltext"; Helper4Data.AddCol("ORDERSET", "carList",orderColsPath, col1.toString());
		col1.idx = 9; col1.name = "ccrsj"; col1.headTxt="乘车人手机";col1.Hidden = "N";col1.section = "Y";col1.type = "ltext"; Helper4Data.AddCol("ORDERSET", "carList",orderColsPath, col1.toString());
		col1.idx = 10; col1.name = "fcsj"; col1.headTxt="发车时间";col1.Hidden = "N";col1.section = "N";col1.type = "datetime"; Helper4Data.AddCol("ORDERSET", "carList",orderColsPath, col1.toString());
		col1.idx = 11; col1.name = "fcdd"; col1.headTxt="发车地点";col1.Hidden = "N";col1.section = "N";col1.type = "ltext"; Helper4Data.AddCol("ORDERSET", "carList",orderColsPath, col1.toString());
		col1.idx = 12; col1.name = "mddd"; col1.headTxt="目的地点";col1.Hidden = "N";col1.section = "Y";col1.type = "ltext"; Helper4Data.AddCol("ORDERSET", "carList",orderColsPath, col1.toString());
		col1.idx = 13; col1.name = "ycsy"; col1.headTxt="用车事由";col1.Hidden = "N";col1.section = "N";col1.type = "htext"; Helper4Data.AddCol("ORDERSET", "carList",orderColsPath, col1.toString());
		col1.idx = 14; col1.name = "sendbutton"; col1.headTxt="发送";col1.Hidden = "N";col1.section = "N";col1.type = "button";col1.selstr = "发送"; Helper4Data.AddCol("ORDERSET", "carList",orderColsPath, col1.toString());
		col1.idx = 15; col1.name = "timestamp"; col1.headTxt="时间";col1.Hidden = "Y";col1.section = "N";col1.type = "timestamp"; Helper4Data.AddCol("ORDERSET", "carList",orderColsPath, col1.toString());
		col1.idx = 16; col1.name = "tblname"; col1.headTxt="申请用车";col1.Hidden = "Y";col1.section = "N";col1.type = "tblname"; Helper4Data.AddCol("ORDERSET", "carList",orderColsPath, col1.toString());
		col1.idx = 17; col1.name = "VoicePath";col1.headTxt = "语音地址";
		Helper4Data.AddCol("ORDERSET", "maintainList",orderColsPath, col1.toString());
		col1.idx = 18; col1.name = "duration";col1.headTxt = "语音时长";
		Helper4Data.AddCol("ORDERSET", "maintainList",orderColsPath, col1.toString());
	}
    private static void createmeetList(){
        Helper4Data.creatTbl("ORDERSET", "meetList",orderColsPath);
		Col col1 = new Col();
		col1.idx = 1; col1.name = "rowid1"; col1.Hidden = "Y"; Helper4Data.AddCol("LOCALSET", "meetList",orderColsPath,col1.toString());
		col1.idx = 2; col1.name = "rowCreatTime"; col1.Hidden = "Y"; Helper4Data.AddCol("LOCALSET", "meetList",orderColsPath, col1.toString());
		col1.idx = 3; col1.name = "rowCreatMac"; col1.headTxt="发起人";col1.Hidden = "R";col1.section = "Y";col1.type = "ltext";
		Helper4Data.AddCol("ORDERSET", "meetList",orderColsPath, col1.toString());
		col1.idx = 4; col1.name = "ReceiversMAC"; col1.headTxt = "参与人MAC";col1.Hidden = "Y"; col1.section = "N"; Helper4Data.AddCol("ORDERSET", "meetList",orderColsPath,col1.toString());
		col1.idx = 5; col1.name = "ReceiversName"; col1.headTxt = "参与人"; col1.Hidden = "R"; col1.section = "Y";col1.type = "ltext";
		Helper4Data.AddCol("ORDERSET", "meetList",orderColsPath, col1.toString());
		col1.idx = 6; col1.name = "Topic"; col1.headTxt = "主题";col1.Hidden = "R"; col1.type = "ltext"; col1.selstr = "无主题"; col1.section = "N"; Helper4Data.AddCol("ORDERSET", "meetList",orderColsPath, col1.toString());
		col1.idx = 7; col1.name = "Content"; col1.headTxt = "内容";col1.Hidden = "R"; col1.type = "htext"; col1.section = "Y"; Helper4Data.AddCol("ORDERSET", "meetList",orderColsPath, col1.toString());
		col1.idx = 8; col1.name = "MeetingPlace"; col1.headTxt = "会址";col1.Hidden = "R"; col1.type = "ltext"; col1.section = "Y"; Helper4Data.AddCol("ORDERSET", "meetList",orderColsPath, col1.toString());
		col1.idx = 9; col1.name = "MeetingData"; col1.headTxt = "文件"; col1.Hidden = "Y"; col1.type = "ltext"; col1.section = "N";
		Helper4Data.AddCol("ORDERSET", "meetList",orderColsPath, col1.toString());
		col1.idx = 10; col1.name = "DataName"; col1.headTxt = "文件"; col1.Hidden = "R"; col1.type = "ltext"; col1.section = "Y";
		Helper4Data.AddCol("ORDERSET", "meetList",orderColsPath, col1.toString());
		col1.idx = 11; col1.name = "AlarmTime"; col1.headTxt = "提醒时间";col1.Hidden = "R"; col1.type = "ltext"; col1.section = "N"; Helper4Data.AddCol("ORDERSET", "meetList",orderColsPath, col1.toString());
		col1.idx = 12; col1.name = "ConfirmStatus"; col1.headTxt = "确认状态";col1.Hidden = "Y"; col1.section = "N"; Helper4Data.AddCol("ORDERSET", "meetList",orderColsPath, col1.toString());
		col1.idx = 13; col1.name = "ReceiversConfirm"; col1.headTxt = "发送"; col1.Hidden = "R"; col1.type = "button"; col1.selstr = "接受⊕不接受"; col1.section = "bottom"; Helper4Data.AddCol("ORDERSET", "meetList",orderColsPath, col1.toString());
		col1.idx = 14; col1.name = "tblname"; col1.headTxt = "发起通知"; col1.Hidden = "Y"; col1.type = "tblname";
		Helper4Data.AddCol("ORDERSET", "meetList",orderColsPath, col1.toString());
		col1.idx = 15; col1.name = "isRead"; col1.headTxt = "是否已读"; col1.Hidden = "Y"; Helper4Data.AddCol("ORDERSET", "meetList",orderColsPath, col1.toString());
    }
	private static void createmaintainList() {
		Helper4Data.creatTbl("ORDERSET", "maintainList", orderColsPath);
		Col col1 = new Col();
		col1.idx = 4; col1.name = "Topic"; col1.headTxt = "主题";col1.Hidden = "Y"; col1.section = "N"; Helper4Data.AddCol("ORDERSET", "maintainList",orderColsPath,col1.toString());
		col1.idx = 5; col1.name = "Type"; col1.headTxt = "类型"; col1.Hidden = "R"; col1.section = "Y";col1.type = "ltext";
		Helper4Data.AddCol("ORDERSET", "maintainList",orderColsPath, col1.toString());
		col1.idx = 6; col1.name = "MaintainTime"; col1.headTxt = "时间";col1.Hidden = "R"; col1.type = "ltext"; col1.selstr = "无主题"; col1.section = "N"; Helper4Data.AddCol("ORDERSET", "maintainList",orderColsPath, col1.toString());
		col1.idx = 7; col1.name = "MaintainPlace"; col1.headTxt = "地址";col1.Hidden = "R"; col1.type = "htext"; col1.section = "Y"; Helper4Data.AddCol("ORDERSET", "maintainList",orderColsPath, col1.toString());
		col1.idx = 8; col1.name = "Record"; col1.headTxt = "录音";col1.Hidden = "R"; col1.type = "ltext"; col1.section = "Y"; Helper4Data.AddCol("ORDERSET", "maintainList",orderColsPath, col1.toString());
		col1.idx = 9; col1.name = "Description"; col1.headTxt = "描述"; col1.Hidden = "Y"; col1.type = "ltext"; col1.section = "N";
		Helper4Data.AddCol("ORDERSET", "maintainList",orderColsPath, col1.toString());
		col1.idx = 10; col1.name = "Contacts"; col1.headTxt = "联系人"; col1.Hidden = "R"; col1.type = "ltext"; col1.section = "Y";
		Helper4Data.AddCol("ORDERSET", "maintainList",orderColsPath, col1.toString());
		col1.idx = 11; col1.name = "Phone"; col1.headTxt = "联系电话";col1.Hidden = "R"; col1.type = "ltext"; col1.section = "N"; Helper4Data.AddCol("ORDERSET", "maintainList",orderColsPath, col1.toString());
		col1.idx = 12; col1.name = "ImgsPath";col1.headTxt = "图片地址";
		Helper4Data.AddCol("ORDERSET", "maintainList",orderColsPath, col1.toString());
		col1.idx = 13; col1.name = "duration";col1.headTxt = "录音时长";
		Helper4Data.AddCol("ORDERSET", "maintainList",orderColsPath, col1.toString());
		col1.idx = 14; col1.name = "VoicePath";col1.headTxt = "录音路径";
		Helper4Data.AddCol("ORDERSET", "maintainList",orderColsPath, col1.toString());
		col1.idx = 15; col1.name = "Staus";col1.headTxt = "申请状态";
		Helper4Data.AddCol("ORDERSET", "maintainList",orderColsPath, col1.toString());

	}

	public static String bizColsPath = MyApplication.approotPath + "Data/BIZSET/cols";
	public static String bizRowsPath = MyApplication.approotPath + "Data/BIZSET/rows";

	public static void initBizSet(){
		Helper4Data.creatSet("BIZSET", bizColsPath, bizRowsPath);
		createEmailList();//邮件
		createRecMaintainList();//维修保障
		createoaList();//OA
		createoaprocessList();//OA流程
		createwagesList();
		createUnionList();
		MyApplication.BIZSET.viewDidLoad(bizColsPath, bizRowsPath);
	}

	private static void createEmailList() {
		String table = "emailList";
		Helper4Data.creatTbl("BIZSET", "emailList",bizColsPath);
		Col col1 = new Col();
		col1.idx = 4; col1.name = "title"; col1.headTxt = "主题"; Helper4Data.AddCol("BIZSET", table, bizColsPath, col1.toString());
		col1.idx = 5; col1.name = "sender"; col1.headTxt = "发件人"; Helper4Data.AddCol("BIZSET",table,bizColsPath,col1.toString());
		col1.idx = 6; col1.name = "receiver"; col1.headTxt = "收件人"; Helper4Data.AddCol("BIZSET",table,bizColsPath,col1.toString());
		col1.idx = 7; col1.name = "content"; col1.headTxt = "内容"; Helper4Data.AddCol("BIZSET",table,bizColsPath,col1.toString());
		col1.idx = 8; col1.name = "attachment"; col1.headTxt = "附件"; Helper4Data.AddCol("BIZSET",table,bizColsPath,col1.toString());
	}
	private static void createRecMaintainList() {
		String table = "recmaintainList";
		Helper4Data.creatTbl("BIZSET", table,bizColsPath);
		Col col1 = new Col();
		col1.idx = 4; col1.name = "Topic"; col1.headTxt = "主题"; Helper4Data.AddCol("BIZSET", table, bizColsPath, col1.toString());
		col1.idx = 5; col1.name = "Type"; col1.headTxt = "发件人"; Helper4Data.AddCol("BIZSET",table,bizColsPath,col1.toString());
		col1.idx = 6; col1.name = "MaintainTime"; col1.headTxt = "收件人"; Helper4Data.AddCol("BIZSET",table,bizColsPath,col1.toString());
		col1.idx = 7; col1.name = "MaintainPlace"; col1.headTxt = "内容"; Helper4Data.AddCol("BIZSET",table,bizColsPath,col1.toString());
		col1.idx = 8; col1.name = "Record"; col1.headTxt = "附件"; Helper4Data.AddCol("BIZSET",table,bizColsPath,col1.toString());
		col1.idx = 9; col1.name = "Description"; col1.headTxt = "描述"; col1.Hidden = "Y"; col1.type = "ltext"; col1.section = "N";
		Helper4Data.AddCol("ORDERSET", table,bizColsPath, col1.toString());
		col1.idx = 10; col1.name = "Contacts"; col1.headTxt = "联系人"; col1.Hidden = "R"; col1.type = "ltext"; col1.section = "Y";
		Helper4Data.AddCol("ORDERSET", table,bizColsPath, col1.toString());
		col1.idx = 11; col1.name = "Phone"; col1.headTxt = "联系电话";col1.Hidden = "R"; col1.type = "ltext"; col1.section = "N"; Helper4Data.AddCol("ORDERSET", table,bizColsPath, col1.toString());
		col1.idx = 12; col1.name = "ImgsPath";col1.headTxt = "图片地址";
		Helper4Data.AddCol("BIZSET",table,bizColsPath, col1.toString());
		col1.idx = 13; col1.name = "isRead"; col1.headTxt = "已阅"; Helper4Data.AddCol("BIZSET", table, bizColsPath, col1.toString());
		col1.idx = 14; col1.name = "recReply"; col1.headTxt = "回复内容"; Helper4Data.AddCol("BIZSET", table, bizColsPath, col1.toString());
	}
	private static void createoaList() {
		Helper4Data.creatTbl("BIZSET", "oalist", bizColsPath);
		Col col1 = new Col();
		col1.idx = 4; col1.name = "symbol"; col1.headTxt="文号";
		Helper4Data.AddCol("BIZSET", "oalist", bizColsPath, col1.toString());
		col1.idx = 5; col1.name = "topic"; col1.headTxt="主题";
		Helper4Data.AddCol("BIZSET", "oalist",bizColsPath, col1.toString());
		col1.idx = 6; col1.name = "datetime"; col1.headTxt="文件时间";
		Helper4Data.AddCol("BIZSET", "oalist",bizColsPath, col1.toString());
		col1.idx = 6; col1.name = "lwdw"; col1.headTxt="来文单位";
		Helper4Data.AddCol("BIZSET", "oalist",bizColsPath, col1.toString());
		col1.idx = 7; col1.name = "lwfl"; col1.headTxt="来文分类";
		Helper4Data.AddCol("BIZSET", "oalist",bizColsPath, col1.toString());
		col1.idx = 8; col1.name = "blqx"; col1.headTxt="办理期限";
		Helper4Data.AddCol("BIZSET", "oalist",bizColsPath, col1.toString());
		col1.idx = 9; col1.name = "gdbm"; col1.headTxt="归档部门";
		Helper4Data.AddCol("BIZSET", "oalist",bizColsPath, col1.toString());
		col1.idx = 10; col1.name = "lwzy"; col1.headTxt="来文摘要";
		Helper4Data.AddCol("BIZSET", "oalist",bizColsPath, col1.toString());
		col1.idx = 11; col1.name = "attachment"; col1.headTxt="附件";
		Helper4Data.AddCol("BIZSET", "oalist",bizColsPath, col1.toString());
		col1.idx = 12; col1.name = "attachmentsize"; col1.headTxt="附件大小";
		Helper4Data.AddCol("BIZSET", "oalist",bizColsPath, col1.toString());
	}
	private static void createoaprocessList() {
		Helper4Data.creatTbl("BIZSET", "oaprocesslist", bizColsPath);
		String tbl = "oaprocesslist";
		Col col1 = new Col();
		col1.idx = 4; col1.name = "sender"; col1.headTxt="发送人姓名";
		Helper4Data.AddCol("BIZSET",tbl, bizColsPath, col1.toString());
		col1.idx = 5; col1.name = "opinion"; col1.headTxt="意见";
		Helper4Data.AddCol("BIZSET", tbl,bizColsPath, col1.toString());
		col1.idx = 6; col1.name = "datetime"; col1.headTxt="文件时间";
		Helper4Data.AddCol("BIZSET", tbl,bizColsPath, col1.toString());
	}
	private static void createwagesList() {
		Helper4Data.creatTbl("BIZSET", "wageslist", bizColsPath);
		String tbl = "wageslist";
		Col col1 = new Col();
		col1.idx = 4; col1.name = "sender"; col1.headTxt="姓名";
		Helper4Data.AddCol("BIZSET",tbl, bizColsPath, col1.toString());
		col1.idx = 5; col1.name = "total"; col1.headTxt="总工资";
		Helper4Data.AddCol("BIZSET", tbl,bizColsPath, col1.toString());
		col1.idx = 6; col1.name = "month"; col1.headTxt="月份";
		Helper4Data.AddCol("BIZSET", tbl,bizColsPath, col1.toString());
		col1.idx = 7; col1.name = "give_style"; col1.headTxt="应发";
		Helper4Data.AddCol("BIZSET", tbl,bizColsPath, col1.toString());
		col1.idx = 8; col1.name = "take_style"; col1.headTxt="代扣";
		Helper4Data.AddCol("BIZSET", tbl,bizColsPath, col1.toString());
		col1.idx = 9; col1.name = "give_info"; col1.headTxt="应发明细";
		Helper4Data.AddCol("BIZSET", tbl,bizColsPath, col1.toString());
		col1.idx = 10; col1.name = "take_info"; col1.headTxt="代扣明细";
		Helper4Data.AddCol("BIZSET", tbl,bizColsPath, col1.toString());
	}
	private static void createUnionList(){
		Helper4Data.creatTbl("BIZSET", "tblunion", bizColsPath);
	}

	public static String chartColsPath = MyApplication.approotPath + "Data/CHARTSET/cols";
	public static String chartRowsPath = MyApplication.approotPath + "Data/CHARTSET/rows";
	public static void initChartSet(){
		Helper4Data.creatSet("CHARTSET", chartColsPath, chartRowsPath);
		createchart_list("chart_capacity");
		createchart_list("chart_daily");
		createchart_list("tblmtime");
		createchart_list("tblktime");
		MyApplication.CHARTSET.viewDidLoad(chartColsPath,chartRowsPath);
	}

	private static void createchart_list(String tblName) {
		Helper4Data.creatTbl("CHARTSET",tblName,chartColsPath);
	}


}
