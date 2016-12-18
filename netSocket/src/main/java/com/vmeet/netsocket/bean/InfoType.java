package com.vmeet.netsocket.bean;

/**
 * 发送和接收的头的类型
 *
 * @author sixgod
 */
public enum InfoType {

    Reg(8), // 注册暂不用
    Log(1), // 登录 有Info LoginName 优先级1（最高），返回data好友列表
    Msg(2), // 文字消息 消息放在INFO中，无Data部分DataLen =0 优先级1
    File(3), // DataSet GUID@DataSetName@SHA1，优先级3
    SendFile(4), // 发送大文件
    Comm(5), // 暂不用
    GetFile(6), // 端从服务器下载数据
    BuildPath(7), //
    FileTest(8),//接收文件测试
    Cmd(9), // 命令字 暂不用
    getUserListSet(10),
    GetFileList(12), // 获得文件列表
    GetDirList(13), // 获得目录列表
    DelFiles(14),// 删除文件或目录 　
    Mail(15),//注册认证码
    Auto(16), //判断认证码
    FileUpdate(17), //文件更新状态
    SearchFile(18),//查找文件目录
    getUserList(19),//获取客户列表
    getUserStat(20), //获取客户状态
    FileSendOK(21), //接收文件通知
    ChangeRegCode(22), //修改客户端认证码
    delClientFile(23),//删除端群
    txt(31),//文本消息
    voice(32),//语音消息
    img(33),//图片消息
    vedio(34),//视频
    html(35),
    vpdf(36),
    other(37),//其他文件
    DataObj(38),//数据类型
    Line(40),//涂鸦
    Page(41),//涂鸦翻页
    chess(42),//棋盘
    CreatSet(51),
    DropSet(52),
    CreatTbl(53),
    DropTbl(54),
    AddCol(55),
    DelCol(56),
    AddRows(57),
    SearchRows(58),
    SearchRowsLike(59),
    DelRows(60),
    UpdateRows(61),
    GetCols(62),
    bizData(99),
    E_CMD_REMOTE_CHESS(44),/*//远程下棋发送指令*/
    E_CMD_REQUEST_RTCHESS(45), //请求远程下棋
    E_CMD_CONFIRM_RTCHESS(46), //开始远程下棋
    E_CMD_CANCEL_RTCHESS(47),  //取消远程下棋
    E_CMD_CANCEL_ADDBOARD(48);  //取消添加棋盘

    private int _value;

    private InfoType(int value) {
        _value = value;
    }

    public int value() {
        return _value;
    }

    public static InfoType getInfoType(int val) {
        for (InfoType InfoType2 : InfoType.values()) {
            if (InfoType2.value() == val) {
                return InfoType2;
            }
        }
        return null;
    }
}
