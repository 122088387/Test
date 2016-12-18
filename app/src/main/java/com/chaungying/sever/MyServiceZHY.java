package com.chaungying.sever;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.chaungying.MyApplication;
import com.chaungying.wuye3.R;
import com.vmeet.netsocket.SocketUtil;
import com.vmeet.netsocket.bean.GetFileInfo;
import com.vmeet.netsocket.bean.InfoType;
import com.vmeet.netsocket.bean.MessageObj;
import com.vmeet.netsocket.data.Row;
import com.vmeet.netsocket.data.Tbl;
import com.vmeet.netsocket.tool.Constants;
import com.vmeet.netsocket.tool.OnSocketLisenter;
import com.vmeet.netsocket.tool.Tool;

import java.util.ArrayList;
import java.util.List;

/**
 * 长连接服务
 */
public class MyServiceZHY extends Service {
    /**
     * 接收到的语音的长度
     */
    private String voiceLenth;
    private BroadcastReceiver broad;
    private AlarmManager alarmMgr;
    private PendingIntent sender;

    private Intent alarmIntent;
    public static List<byte[]> bytesArray = new ArrayList<byte[]>();

    public static final String SCOKET_FLAG = "scoket_flag";

    @Override
    public IBinder onBind(Intent intent) {
        Tool.showLog(Constants.basePath, "onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Tool.showLog(Constants.basePath, "MyService onDestroy");
        unregisterReceiver(broad);
        Intent intent = new Intent(this, MyServiceZHY.class);
        this.startService(intent);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        broad = new MyBroad();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.Action_Socket_Alarm);
        filter.addAction(Constants.Action_Server);
        filter.addAction(Constants.Action_Socket_GetFileSuccess);
        filter.addAction(Constants.Action_Socket_GetFileDirSucess);//imgs图片下载完成通知
        filter.addAction(Constants.Action_Socket_SendFileDirSucess);
        filter.addAction(Constants.Action_OpenWel_Activity);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broad, filter);
        setAlertManager();
        SocketUtil.ConnectServerTH(MyApplication.socketObj);
    }

    private class MyBroad extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Tool.showLog(Constants.basePath, "onReceive:" + action);
            // 接收服务器返回消息
            if (Constants.Action_Server.equals(intent.getAction())) {
                MessageObj msgobj = (MessageObj) intent.getExtras()
                        .getSerializable("MessageObj");
                if (msgobj == null)
                    return;
                if (msgobj.msgType != InfoType.getUserStat) {//排掉服务器发来的重复消息
                    String lastRecive = MyApplication.sharedPreferences.getString("lastRecive", null);
                    if (msgobj.toString().equals(lastRecive))
                        return;
                    MyApplication.editor.putString("lastRecive", msgobj.toString());
                    MyApplication.editor.commit();
                }
                if (msgobj.msgType == InfoType.getUserStat) {
                    recUserState(msgobj);
                    return;
                } else if (msgobj.msgType == InfoType.txt) {


                    recDataMessage(msgobj);
                    return;
                } else if (msgobj.msgType == InfoType.chess) {
                    System.out.print("棋盘消息" + msgobj.content);
                    sendChessReceiver(msgobj.content);
                } else if (msgobj.msgType == InfoType.FileUpdate) {
                    serverFileUpdataMessage(msgobj);
                    return;
                } else if (msgobj.msgType == InfoType.Msg
                        && "SysUpdate".equals(msgobj.content)) {
                    // 新版本更新通知
//					Util.startNotification(getApplicationContext());
//					Util.openNotification1(getApplicationContext(), "新版本更新通知");
                    MyApplication.editor.putBoolean("update", true);
                    MyApplication.editor.commit();
                    Intent intent3 = new Intent(Constants.Action_Update_App);
                    sendBroadcast(intent3);
                    return;
                } else if (msgobj.msgType == InfoType.Line) {
                    String points = msgobj.content;
                    recTuyaPoints(points);
                    Intent intent4 = new Intent();
                    intent4.setAction("tuya.points");
                    sendBroadcast(intent4);
                    return;
                } else if (msgobj.msgType == InfoType.Page) {
                    String namePage = msgobj.content;
                    Intent intent5 = new Intent();
                    intent5.setAction("intentpage");
                    intent5.putExtra("page", namePage);
                    sendBroadcast(intent5);
                    return;
                } else if (msgobj.msgType == InfoType.voice) {
                    String con = msgobj.content;
                    int c1;//"/"的位置
                    int c2;//"_"的位置
                    c1 = con.indexOf("/", 0);
                    c2 = con.indexOf("_", 0);
                    voiceLenth = con.substring(c1 + 1, c2);
                    Log.i("as", voiceLenth);
                } else if (msgobj.msgType == InfoType.E_CMD_REQUEST_RTCHESS) {//请求远程下棋
                    String con = msgobj.content;
                    sendStartqingqiuChessBrocevier(con);
                } else if (msgobj.msgType == InfoType.E_CMD_CONFIRM_RTCHESS) {//开始远程下棋
                    String con = msgobj.content;
                    sendStartChessBrocevier();
                } else if (msgobj.msgType == InfoType.E_CMD_CANCEL_RTCHESS) {//取消远程下棋
                    String con = msgobj.content;
                    sendCancleChessBrocevier();
                } else if (msgobj.msgType == InfoType.E_CMD_REMOTE_CHESS) {//网络对战
                    String con = msgobj.content;
                    sendWlMoveBrovevier(con);
                } else if (msgobj.msgType == InfoType.bizData) {//物业消息
                    String con = msgobj.content;
                    Log.i("Service", con);
                    // TODO: 王晓赛 2016年8月15日   收到消息之后做请求的处理
                    sendWorkTask(con);
//                    sendWlMoveBrovevier(con);
                }
            } else if (Constants.Action_OpenWel_Activity.equals(action)) {//LauncherActivity点击返回键发出的广播
//				MyApplication.openWel = true;
            }
            if (Constants.Action_Socket_GetFileSuccess.equals(action)) {
                if (intent.getStringExtra(Constants.DOWNLOADSUCCESS).equals("epuserlist.txt")) {//更新企业通讯录
//					Util.startNotification(getApplicationContext());
                    Tbl tbl = MyApplication.BASE.getTblByTblName("epuserlist");
                    tbl.rows.clear();
                    tbl.loadRows(InitData.baseRowsPath);
                    Tbl tranuser = MyApplication.BASE.getTblByTblName("tranuserlist");
                    tranuser.rows.clear();
                    tranuser.save(InitData.baseRowsPath);
                    Tbl transexuser = MyApplication.BASE.getTblByTblName("transexuserlist");
                    transexuser.rows.clear();
                    transexuser.save(InitData.baseRowsPath);
                    Tbl tranoffuser = MyApplication.BASE.getTblByTblName("tranofficeuserlist");
                    tranoffuser.rows.clear();
                    tranoffuser.save(InitData.baseRowsPath);
                    Tbl tranunituser = MyApplication.BASE.getTblByTblName("tranunitlist");
                    tranunituser.rows.clear();
                    tranunituser.save(InitData.baseRowsPath);
                    Tbl tranpostuser = MyApplication.BASE.getTblByTblName("tranpostlist");
                    tranpostuser.rows.clear();
                    tranpostuser.save(InitData.baseRowsPath);
                    Tbl tranleveluser = MyApplication.BASE.getTblByTblName("tranlevellist");
                    tranleveluser.rows.clear();
                    tranleveluser.save(InitData.baseRowsPath);
                }
                return;
            }
            if (Constants.Action_Socket_SendFileDirSucess.equals(action)) {//判断将要发送的row什么时候调用sendRow方法
                String dirName = intent.getStringExtra("dirName");
//				if(MsgData.sendDirMap.get(dirName)!=null){
//					Row row = MsgData.sendDirMap.get(dirName);
//					MsgData.sendRow(row, row.getVal("recs"));
//					synchronized (MsgData.sendDirMap) {
//						MsgData.sendDirMap.remove(dirName);
//					}
//				}
                return;
            }

            if (Constants.Action_Socket_GetFileDirSucess.equals(action)) {//判断收到的row什么时候出现在气泡中
                String dir = intent.getStringExtra("dirName");
                Intent intent_getfile = new Intent("com.example.netvmeet.msgList.MsgChat");
                intent_getfile.setAction(Constants.Action_Msg_Data);
                if (dir.contains("doc")) {
                    dir = dir.replace("\\imgs", "");
                }
//				if(MsgData.recDirMap.get(dir)!=null){//表明map里边存了此row 气泡里边
//					Row row = MsgData.recDirMap.get(dir);
//					MsgData.addRowToMsgTblAnddRecentTbl(row,row.tbl);
//					synchronized (MsgData.recDirMap) {
//						MsgData.recDirMap.remove(dir);
//					}
//					notifiCation(row, intent_getfile);
                sendBroadcast(intent_getfile);
//				}
                return;
            }

            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                SocketUtil.sendHeartBeat(MyApplication.socketObj);
                Tool.showLog(Constants.basePath, "heartbeatconnectivityManager:" + "网络改变");
                return;
            } else if (Constants.Action_Socket_Alarm.equals(action)) {
                Tool.showLog(Constants.basePath, "conn1 alarm");
                SocketUtil.sendHeartBeat(MyApplication.socketObj);
            }
        }
    }

    private void setAlertManager() {
        Tool.showLog(Constants.basePath, "setAlertManager");
        if (alarmMgr == null)
            alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (alarmIntent == null)
            alarmIntent = new Intent(Constants.Action_Socket_Alarm);
        if (sender == null)
            sender = PendingIntent.getBroadcast(getApplicationContext(), 0,
                    alarmIntent, 0);
        alarmMgr.cancel(sender);
        // 2分钟发送广播，然后每个2分钟重复发广播。广播都是直接发到AlarmReceiver的
        alarmMgr.setRepeating(AlarmManager.RTC, SystemClock.elapsedRealtime(),
                1000 * 20, sender);
        // alarmMgr.setRepeating(AlarmManager.RTC,
        // SystemClock.elapsedRealtime(),10*60*1000, sender);
    }

    /**
     * 接收用户状态列表处理方法
     *
     * @param obj
     */
    private void recUserState(MessageObj obj) {
        Intent intent = new Intent();
        String getUserStat = obj.content;
        Log.e("recUserState:", getUserStat);
        String[] userStateInfos = getUserStat.split(",");
        for (String userStateInfo : userStateInfos) {
            String[] userInfo = userStateInfo.split("_");
            MyApplication.userState.put(userInfo[0], userInfo[1]);
        }

        intent.setAction(Constants.Action_UserState);
        sendBroadcast(intent);
    }

    /**
     * 接受data类型消息处理方法
     *
     * @param msgobj
     */
    private void recDataMessage(MessageObj msgobj) {
//        ChatMessage chatMessage = new ChatMessage(msgobj.macto, msgobj.macfrom, Config.MESSAGE_FROM, Config.MESSAGE_TYPE_TXT, TimeUtil.getAbsoluteTime(), msgobj.content);//得到的消息体
//
//        saveMessageToDb(Config.MESSAGE_FROM, chatMessage.getType(), chatMessage.getTime(), chatMessage.getContent(), msgobj.macto, msgobj.macfrom);
//        Intent intent = new Intent();
//        intent.setAction(MyApplication.getApplication().getResources().getString(R.string.filterText));
//        sendBroadcast(intent);

    }

    /**
     * 发送下棋请求广播
     *
     * @param
     */
    private void sendStartqingqiuChessBrocevier(String con) {
//        String str = null;
//        String macid = null;
//        String imei = null;
//        try {
//            JSONObject jsonObject = new JSONObject(con);
//            str = jsonObject.getString("userid");
//            macid = jsonObject.getString("macid");
//            imei = jsonObject.getString("boardid");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Intent intent = new Intent();
//        intent.setAction(MyApplication.getApplication().getResources().getString(R.string.start_chess_qingqiu));
//        intent.putExtra("content", str);
//        intent.putExtra("macid", macid);
//        intent.putExtra("imei", imei);
//        MyApplication.getApplication().sendBroadcast(intent);
    }

    /**
     * 发送开始下棋广播
     *
     * @param
     */
    private void sendStartChessBrocevier() {

//
//        Intent intent = new Intent();
//        intent.setAction(MyApplication.getApplication().getResources().getString(R.string.wl_sc));
//        intent.putExtra("wltype", "s");
//
//        MyApplication.getApplication().sendBroadcast(intent);
    }

    /**
     * 发送取消下棋广播
     *
     * @param
     */
    private void sendCancleChessBrocevier() {


//        Intent intent = new Intent();
//        intent.setAction(MyApplication.getApplication().getResources().getString(R.string.wl_sc));
//        intent.putExtra("wltype", "c");
//
//        MyApplication.getApplication().sendBroadcast(intent);
    }


    /**
     * 发送
     */
    private void sendWorkTask(String con) {
        Intent intentBro = new Intent();  //Itent就是我们要发送的内容
        intentBro.setAction(MyApplication.getApplication().getResources().getString(R.string.send_work_task));   //设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
        intentBro.putExtra("data", con);
        MyApplication.getApplication().sendBroadcast(intentBro);   //发送广播
    }

    /**
     * 发送网络对战的移动棋子广播
     */
    private void sendWlMoveBrovevier(String content) {
        Intent intent = new Intent();
        intent.setAction(MyApplication.getApplication().getResources().getString(R.string.send_work_task));
//        intent.putExtra("chess", content);

        MyApplication.getApplication().sendBroadcast(intent);
    }
    /**
     * 吧好友存进聊天好友的数据库
     */
//    private void saveFriend(PersonInfo friend) {
//        MYSQL mysql = new MYSQL(MyApplication.getApplication());
//        Cursor c = mysql.select("hisfriend", "mac = '" + MyApplication.socketObj.getMac() + "' and fmac= '" + friend.macId + "'");
//        if (c.getCount() > 0) {//西施说明数据哭已经有该好友了，不需要再存了
//            c.close();
//            return;
//        }
//        String s = SaveObject.savePerson(this, friend);
//
//        long i = mysql.insert("hisfriend", new String[]{"mac", "fmac", "user"}, new String[]{MyApplication.socketObj.getMac(), friend.macId, s});
//        L.i("存好友成功了", i + "");
//    }

    /**
     * 把收到的消息存进数据库
     *
     * @param direction
     * @param type
     * @param time
     * @param content
     */
    private void saveMessageToDb(int direction, int type, String time, String content, String sMacId, String fMacId) {
//        ContentValues values = new ContentValues();
//        values.put("self_Id", "'" + sMacId + "'");
//        values.put("friend_Id", "'" + fMacId + "'");
//        values.put("direction", direction);
//        values.put("type", type);
//        values.put("time", time);
//        values.put("content", content);
//        values.put("voiceLenth", voiceLenth);
//        ContentResolver resolver = MyApplication.getApplication().getContentResolver();
//        resolver.insert(ChatMsgProvider.CONTENT_URI, values);
//        new Thread(new SaveNumRunable(sMacId, fMacId)).start();

    }

    /**
     * 存接收到的未读消息的线程
     */
    private class SaveNumRunable implements Runnable {
        private String sMacId, fMacId;

        public SaveNumRunable(String sMacId, String fMacId) {

            this.fMacId = fMacId;
            this.sMacId = sMacId;
        }

        @Override
        public void run() {
//            MYSQL mysql = new MYSQL(MyApplication.getApplication());
//            Cursor cursor = mysql.select("hisfriend", "mac= '" + sMacId + "' and fmac = '" + fMacId + "'");
//            if (cursor.getCount() > 0) {//此时说明已经和该好友聊过天了，可以直接存到数据库了
//                int num = 0;
//                while (cursor.moveToNext()) {
//
//                    String sNum = cursor.getString(cursor.getColumnIndex("num"));
//                    if (sNum != null && !"".equals(sNum)) {
//                        num = Integer.parseInt(sNum);
//                    }
//                    cursor.close();
//                }
//                mysql.closeBase();
//                num += 1;
//                int i = mysql.update("hisfriend", new String[]{"num"}, new String[]{num + ""}, "mac = ? and fmac = ?", new String[]{sMacId, fMacId});
//                L.i("更新消息个数了", i + "");
//                Intent intent = new Intent();
//                intent.setAction(MyApplication.getApplication().getResources().getString(R.string.friChat));
//                sendBroadcast(intent);
//            } else {
//
//                PersonInfo personInfo = getFriends(fMacId);
//                if(personInfo!=null){
//                    saveFriend(personInfo);
//                    new Thread(new SaveNumRunable(sMacId, fMacId)).start();
//                }

            return;
//            }

        }
    }
    /**
     * 从数据库查询好友
     *
     * @return
     */
//    private PersonInfo getFriends(String mac) {
//
//        MYSQL mysql = new MYSQL(MyApplication.getApplication());
//        Cursor cursor = mysql.select("friend", null);
//        PersonInfo personInfo = null;
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                 personInfo = getPojo(cursor.getString(cursor.getColumnIndex("user")));
//                if (mac.equals(personInfo.macId)) {
//                    return personInfo;
//                };
//            }
//        }
//        return null;
//    }
    /**
     * 得到实体
     *
     * @param str
     * @return
     */
//    private PersonInfo getPojo(String str) {
//        PersonInfo personInfo = (PersonInfo) SaveObject.getObject(str);
//        return personInfo;
//    }

    /**
     * 发送棋盘的广播
     *
     * @param string
     */
    private void sendChessReceiver(String string) {
//        Intent intent = new Intent();
//        intent.setAction(MyApplication.getApplication().getResources().getString(R.string.socket_chess));
//        intent.putExtra("chess", string);
//        MyApplication.getApplication().sendBroadcast(intent);
    }

    private void notifiCation(Row row, Intent intent) {
        if (row == null) return;//如果是一条群的row，如果row的recs里边不包含自己，就将此row抛出
        if (row != null && !TextUtils.isEmpty(row.getVal("groupMac"))) {//群，消息免打扰，和通知显示
//			if(!MyApplication.unditurbMacs.contains(row.getVal("groupMac")))
//				Util.startNotification(getApplicationContext());
//			if(row.getVal("groupMac").equals(MyApplication.CurrentPage)){
//				sendBroadcast(intent);
//				return;
//			}
        } else {//个人
//			if(!MyApplication.unditurbMacs.contains(row.getVal("sender")))
//				Util.startNotification(getApplicationContext());
//			if(row.getVal("sender").equals(MyApplication.CurrentPage)){
//				sendBroadcast(intent);
//				return;
//			}
        }
        String name = row.getVal("senderName");
        String infoType = row.getVal("infoType");
        String content = row.getVal("msgTxt");
        Intent intent2 = null;
//		if (MyApplication.openWel){
//			intent2 = new Intent("com.example.netvmeet.activity.WelActivity");
//		}else{
//			intent2 = new Intent("com.example.netvmeet.msgList.MsgChat");
//		}

        //intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (!TextUtils.isEmpty(row.getVal("groupMac"))) {//群
//            intent2.putExtra("currmac", row.getVal("groupMac"));
//            intent2.putExtra("sendMac", row.getVal("recs"));
//            intent2.putExtra("rowCreatMac", row.getVal("rowCreatMac"));
//            intent2.putExtra("name", name);
        } else {
//            intent2.putExtra("currmac", row.getVal("sender"));
//            intent2.putExtra("name", name);
        }
        if (infoType.equals("img")) {
//				Util.openNotification(getApplicationContext(), "[图片]", intent2, name,
//						"[图片]");
        } else if (infoType.equals("vpdf")) {
//				Util.openNotification(getApplicationContext(), content.replace("doc/", "") , intent2, name,
//						 content.replace("doc/", "") );
        } else if (infoType.equals("spk")) {
//				Util.openNotification(getApplicationContext(), "[语音]", intent2, name,
//						"[语音]");
        } else if (infoType.equals("sight")) {
//				Util.openNotification(getApplicationContext(), "[视频]", intent2, name,
//						"[视频]");
        } else {
//				Util.openNotification(getApplicationContext(), content, intent2, name,
//						content);
        }
    }

    private void recTuyaPoints(String points) {
        Log.e("scrawlRec", points);
        String[] allInfo = points.split("≈");
        if (allInfo.length == 4 || allInfo.length == 3) {
            String pathName = allInfo[0];
            String pageStr = allInfo[1];
            ArrayList<String> pointRec;
//			if(Shared.points.get(pathName+pageStr)==null){
//				pointRec=new ArrayList<>();
//				Shared.points.put(pathName+pageStr,pointRec);
//			}else{
//				pointRec=Shared.points.get(pathName+pageStr);
//			}

//			if(allInfo.length==4){
//				pointRec.add(points);
//			}else if (pointRec.size()>0){
//				pointRec.remove(pointRec.size()-1);
//				Log.i("sss", "----size ddd--- " + pointRec.size());
//			}

        }
    }

    /**
     * 接收服务器文件推送消息处理方法
     *
     * @param obj
     */
    private void serverFileUpdataMessage(final MessageObj obj) {
        String c = obj.content;
        final String[] strings = c.split("§");
        Tool.showLog(Constants.basePath, "接收服务器文件推送消息处理方法:" + c);
        String parentPath = MyApplication.approotPath;
        if (c.contains(".sqlite") || c.contains("groups")) {
            parentPath = MyApplication.approotPath;
        } else if (strings.length > 1 && !TextUtils.isEmpty(strings[1])
                && strings[1].startsWith("cols")) {
            parentPath = MyApplication.approotPath;
        } else if (strings.length > 1 && !TextUtils.isEmpty(strings[1])
                && strings[1].equals("icons")) {
            parentPath = MyApplication.approotPath;
        }
        if (strings.length < 1) {
            return;
        }
        GetFileInfo getFileInfo = new GetFileInfo(strings[0],
                strings[1].replace("/", "\\"), strings[1].replace("\\", "/")
                , parentPath, MyApplication.socketObj.getIp(),
                MyApplication.socketObj.getPort(), 1, MyApplication.socketObj);

        SocketUtil.getFile(getFileInfo, new OnSocketLisenter<Object>() {

            @Override
            public void OnSuccess(Object object) {

//                if (strings[1].equals("icons")) {
////					MyApplication.getUserIcon.release(strings[0].replace(".png", ""));
//                }
////				Intent intent = new Intent(Constants.Action_GetServiceSuccessed);
////				sendBroadcast(intent);
                String fileName = (String) object;
                Log.i("下载下来的语音文件名", fileName);

                saveMessageToDb(Config.MESSAGE_FROM, Config.MESSAGE_TYPE_AUDIO, TimeUtil.getAbsoluteTime(), fileName, obj.macto, obj.macfrom);
//                Intent intent = new Intent();
//                intent.setAction(MyApplication.getApplication().getResources().getString(R.string.filterText));
//                sendBroadcast(intent);
            }

            @Override
            public void OnFail(Object object) {

            }
        });
    }

}

