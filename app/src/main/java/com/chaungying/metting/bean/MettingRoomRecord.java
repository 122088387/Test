package com.chaungying.metting.bean;

import java.util.List;

/**
 * @author 种耀淮 在2016年09月09日10:00创建
 */
public class MettingRoomRecord {


    /**
     * datas : [{"createTime":"2016-09-08 11:04:51","groupName":"123","phone":"15731131542","appointmentDate":"2016-09-08","appointmentBeginTime":"12:20","state":0,"remarks":null,"appointmentEndTime":"12:30","id":132,"meetingName":"一号会议室","meetingId":1,"userName":"测试人员_兴","operaterName":"测试人员_兴","operaterId":4614},{"createTime":"2016-09-08 17:43:34","groupName":"666","phone":"15731131542","appointmentDate":"2016-09-08","appointmentBeginTime":"16:40","state":0,"remarks":null,"appointmentEndTime":"17:00","id":133,"meetingName":"一号会议室","meetingId":1,"userName":"测试人员_兴","operaterName":"测试人员_兴","operaterId":4614}]
     * respCode : 1001
     * respMsg : 成功！
     */

    private int respCode;
    private String respMsg;
    /**
     * createTime : 2016-09-08 11:04:51
     * groupName : 123
     * phone : 15731131542
     * appointmentDate : 2016-09-08
     * appointmentBeginTime : 12:20
     * state : 0
     * remarks : null
     * appointmentEndTime : 12:30
     * id : 132
     * meetingName : 一号会议室
     * meetingId : 1
     * userName : 测试人员_兴
     * operaterName : 测试人员_兴
     * operaterId : 4614
     */

    private List<DatasBean> datas;

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

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        private String createTime;
        private String groupName;
        private String phone;
        private String appointmentDate;
        private String appointmentBeginTime;
        private int state;
        private Object remarks;
        private String appointmentEndTime;
        private int id;
        private String meetingName;
        private int meetingId;
        private String userName;
        private String operaterName;
        private int operaterId;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAppointmentDate() {
            return appointmentDate;
        }

        public void setAppointmentDate(String appointmentDate) {
            this.appointmentDate = appointmentDate;
        }

        public String getAppointmentBeginTime() {
            return appointmentBeginTime;
        }

        public void setAppointmentBeginTime(String appointmentBeginTime) {
            this.appointmentBeginTime = appointmentBeginTime;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }

        public String getAppointmentEndTime() {
            return appointmentEndTime;
        }

        public void setAppointmentEndTime(String appointmentEndTime) {
            this.appointmentEndTime = appointmentEndTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMeetingName() {
            return meetingName;
        }

        public void setMeetingName(String meetingName) {
            this.meetingName = meetingName;
        }

        public int getMeetingId() {
            return meetingId;
        }

        public void setMeetingId(int meetingId) {
            this.meetingId = meetingId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getOperaterName() {
            return operaterName;
        }

        public void setOperaterName(String operaterName) {
            this.operaterName = operaterName;
        }

        public int getOperaterId() {
            return operaterId;
        }

        public void setOperaterId(int operaterId) {
            this.operaterId = operaterId;
        }
    }
}
