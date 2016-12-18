package com.vmeet.netsocket.task;

import android.content.Intent;
import android.os.AsyncTask;

import com.vmeet.netsocket.SocketUtil;
import com.vmeet.netsocket.bean.InfoType;
import com.vmeet.netsocket.bean.PkgHead;
import com.vmeet.netsocket.bean.SendFileInfo;
import com.vmeet.netsocket.tool.Constants;
import com.vmeet.netsocket.tool.DateTool;
import com.vmeet.netsocket.tool.OnSocketLisenter;
import com.vmeet.netsocket.tool.Tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 批量上传文件线程
 *
 * @author sixgod
 */
public class SendFileAysncTask extends AsyncTask<SendFileInfo, Integer, Boolean> {
    SendFileInfo fileInfo;
    OnSocketLisenter<String> lisenter;

    public SendFileAysncTask(OnSocketLisenter<String> lisenter) {
        this.lisenter = lisenter;
    }

    @Override
    protected Boolean doInBackground(SendFileInfo... params) {
        fileInfo = params[0];
        return sendFile(fileInfo);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (!result && fileInfo.time <= 3) {
            fileInfo.time++;
            SocketUtil.sendFile(fileInfo, lisenter);
        }
        if (result) {
            Tool.showLog(Constants.basePath, fileInfo.filePath + "上传成功");
            String dir = fileInfo.path;//上传到服务器的路径
            dir = dir.replace("img/", "");
            if (dir.contains("voice") || dir.contains("sight")) {
                dir = new File(fileInfo.filePath).getName();
            }
            if (SocketUtil.sendDir.get(dir) != null) {
                int count = SocketUtil.sendDir.get(dir);
                count--;
                if (count == 0) {
                    Intent intent = new Intent(Constants.Action_Socket_SendFileDirSucess);
                    intent.putExtra("dirName", dir);
                    fileInfo.socketObj.getContext().sendBroadcast(intent);
                    synchronized (SocketUtil.sendDir) {
                        SocketUtil.sendDir.remove(dir);
                    }
                } else {
                    synchronized (SocketUtil.sendDir) {
                        SocketUtil.sendDir.put(dir, count);
                    }
                }
            }
            Intent intent = new Intent(Constants.SENDSUCCESS);
            intent.setAction(Constants.Action_Socket_SendFileSuccess);//聊天页面广播刷新
            fileInfo.socketObj.getContext().sendBroadcast(intent);

        }
    }

    /**
     * 发送文件
     *
     * @param
     */
    private boolean sendFile(SendFileInfo fileInfo) {
        PkgHead head = new PkgHead();
        head.set_VerNum((byte) 3);
        head.set_MacFrom(fileInfo.socketObj.getMac());
        if (fileInfo.macto == null)
            fileInfo.macto = "00-00-00-00-00-00";

        head.set_MacTo(fileInfo.macto.split(",")[0]);
        head.set_LocalIp(fileInfo.socketObj.getIp());

        head.set_InfoType(InfoType.SendFile);
        File file = new File(fileInfo.filePath);
        String sendInfo = file.getName() + "§" + fileInfo.path+fileInfo.macFrom + "§" + DateTool.convertLong2String(file.lastModified())+ "§"+fileInfo.macto;
        if (fileInfo.macto.split(",").length > 1)
            sendInfo += "§" + fileInfo.macto;
        byte[] bs = null;
        try {
            bs = sendInfo.getBytes("UTF-8");
            head.set_InfoLen(bs.length);
        } catch (UnsupportedEncodingException e1) {
        }
        head.set_DataLen(file.length());
        Socket socket;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(fileInfo.serverIp,fileInfo.serverPort), 2000);
            Constants.serverConnected = true;
        } catch (Exception e) {
            Constants.serverConnected = false;
            Tool.showLog(Constants.basePath, "conn1 sendF");
            SocketUtil.ConnectServerTH(fileInfo.socketObj);
            return false;
        }
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(head.get_NetHeadByte(), 0, head.get_NetHeadByte().length);
            outputStream.write(bs, 0, bs.length);
            byte[] b = new byte[1];
            InputStream inputStream = socket.getInputStream();
            int i = inputStream.read(b);
            // b[0]==1表示服务器存在此文件

            if (i == 1 && b[0] == 3) {
                byte[] buffer = new byte[1024];
                FileInputStream fs = new FileInputStream(file);
                int len = -1;
                while ((len = fs.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
                outputStream.flush();
                fs.close();
            }
            socket.shutdownInput();
            socket.shutdownOutput();
            socket.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
