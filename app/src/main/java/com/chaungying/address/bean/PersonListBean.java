package com.chaungying.address.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王晓赛 or 2016/8/31
 */
public class PersonListBean {

    /**
     * respCode : 1001
     * data : [{"position":"软件工程师","createTime":null,"sex":2,"payPassword":"","state":0,"password":"96e79218965eb72c92a549dd5a330112","portrait":"","id":4597,"districtId":1,"qrcode":"","userName":"任广洲","departmentId":2,"qrcodeDir":"","qrcodeFileName":"","macId":"FF-FE-4F-1B-27-71","roleId":24,"loginName":"13716571398"},{"position":"软件工程师","createTime":null,"sex":2,"payPassword":"","state":0,"password":"96e79218965eb72c92a549dd5a330112","portrait":"","id":4598,"districtId":1,"qrcode":"","userName":"李德贵","departmentId":2,"qrcodeDir":"","qrcodeFileName":"","macId":"FF-F7-93-64-29-72","roleId":24,"loginName":"13120263236"},{"position":"软件工程师","createTime":null,"sex":2,"payPassword":"","state":0,"password":"96e79218965eb72c92a549dd5a330112","portrait":"","id":4599,"districtId":1,"qrcode":"","userName":"王伶江","departmentId":2,"qrcodeDir":"","qrcodeFileName":"","macId":"FF-B6-4A-79-4D-C3","roleId":24,"loginName":"13716089882"},{"position":"软件工程师","createTime":null,"sex":2,"payPassword":"","state":0,"password":"96e79218965eb72c92a549dd5a330112","portrait":"","id":4600,"districtId":1,"qrcode":"","userName":"聂德磊","departmentId":2,"qrcodeDir":"","qrcodeFileName":"","macId":"FF-13-B6-3B-95-0A","roleId":24,"loginName":"18211067417"},{"position":"软件工程师","createTime":null,"sex":2,"payPassword":"","state":0,"password":"96e79218965eb72c92a549dd5a330112","portrait":"","id":4601,"districtId":1,"qrcode":"","userName":"高彬","departmentId":2,"qrcodeDir":"","qrcodeFileName":"","macId":"FF-D6-CB-B5-E2-8A","roleId":24,"loginName":"15117980520"},{"position":"软件工程师","createTime":null,"sex":2,"payPassword":"","state":0,"password":"96e79218965eb72c92a549dd5a330112","portrait":"","id":4608,"districtId":1,"qrcode":"","userName":"sai","departmentId":2,"qrcodeDir":"","qrcodeFileName":"","macId":"FF-F7-05-E5-F0-16","roleId":22,"loginName":"15632288403"}]
     * respMsg : 成功！
     */

    private int respCode;
    private String respMsg;
    /**
     * position : 软件工程师
     * createTime : null
     * sex : 2
     * payPassword :
     * state : 0
     * password : 96e79218965eb72c92a549dd5a330112
     * portrait :
     * id : 4597
     * districtId : 1
     * qrcode :
     * userName : 任广洲
     * departmentId : 2
     * qrcodeDir :
     * qrcodeFileName :
     * macId : FF-FE-4F-1B-27-71
     * roleId : 24
     * loginName : 13716571398
     */

    private List<DataBean> data;

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private String position;
        private Object createTime;
        private int sex;
        private String payPassword;
        private int state;
        private String departmentName;
        private String password;
        private String portrait;
        private int id;
        private int districtId;
        private String qrcode;
        private String userName;
        private int departmentId;
        private String qrcodeDir;
        private String qrcodeFileName;
        private String macId;
        private int roleId;
        private String loginName;


        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getPayPassword() {
            return payPassword;
        }

        public void setPayPassword(String payPassword) {
            this.payPassword = payPassword;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDistrictId() {
            return districtId;
        }

        public void setDistrictId(int districtId) {
            this.districtId = districtId;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
        }

        public String getQrcodeDir() {
            return qrcodeDir;
        }

        public void setQrcodeDir(String qrcodeDir) {
            this.qrcodeDir = qrcodeDir;
        }

        public String getQrcodeFileName() {
            return qrcodeFileName;
        }

        public void setQrcodeFileName(String qrcodeFileName) {
            this.qrcodeFileName = qrcodeFileName;
        }

        public String getMacId() {
            return macId;
        }

        public void setMacId(String macId) {
            this.macId = macId;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }
    }
}
