package com.chaungying.sever;

public class Config
{
	public static String LOCAL_IP;
	public static int CLIENT_ANDROID = 0x7;

	public static final int MESSAGE_FROM=-2;
	public static final int MESSAGE_TO=-1;
	public static final int MESSAGE_TYPE_TXT=31;
	public static final int MESSAGE_TYPE_IMG=33;
	public static final int MESSAGE_TYPE_AUDIO=32;
	public static final int MESSAGE_TYPE_HEARTBEAT=22;
	public static final int MESSAGE_TYPE_CMD=42;
	public static final int MESSAGE_TYPE_CONTACT = 0x27;
	public static final int MESSAGE_TYPE_ADD_FRIEND=3;
    
    public static final int REQUEST_LOGIN=0x1;
    public static final int REQUEST_REGIST=14;
    public static final int REQUEST_SENDFILE=0x4;
    public static final int REQUEST_GETFILE=0x6;
	public static final int REQUEST_DELFILES=0x6;
    public static final int REQUEST_SEND_TXT=17;
    public static final int REQUEST_SEND_IMG=18;
    public static final int REQUEST_SEND_AUDIO=19;
    public static final int REQUEST_GET_OFFLINE_MSG=20;
    public static final int REQUEST_GETDIRLIST=0xD;
    public static final int REQUEST_SEARCH_USER=22;
    public static final int REQUEST_EXIT=23;
    public static final int REQUEST_GET_HEAD=24;
	public static final int REQUEST_GET_USER=25;
	
	public static final int RESULT_LOGIN=100;
	public static final int RESULT_REGIST=101;
	public static final int RESULT_UPDATE_HEAD=102;
	public static final int RESULT_GET_OFFLINE_MSG=103;
	public static final int RESULT_GET_FRIENDS=104;
	public static final int RESULT_SEARCH_USER=105;
	public static final int RESULT_GET_HEAD=106;
	public static final int RESULT_GET_USER=107;
	
	public static final int RECEIVE_TEXT=0x02;
	public static final int RECEIVE_AUDIO=501;
	public static final int RECEIVE_IMG=502;
	public static final int RECEIVE_MESSAGE=503;
	
	public static final int IMG_NEED_UPDATE=600;
    public static final int IMG_NO_UPDATE=601;
	
	
	public static final int LOGIN_SUCCESS=1000;
	public static final int LOGIN_FAILED=1001;
	public static final int RIGEST_SUCCESS=1002;
	public static final int RIGEST_FAILED=1003;
	public static final int SEARCH_USER_SUCCESS=1004;
	public static final int SEARCH_USER_FALSE=1005;
	public static final int USER_HAS_IMG=1006;
	public static final int USER_NOT_IMG=1007;
	public static final int ADD_FRIEND=1008;
	public static final int REGIST_HAS_IMG=1009;
	
	public static final int SEND_NOTIFICATION=2000;
	
	public static final int COONECT_SERVER_SUCCESS=3000;


	//远程象棋

}
