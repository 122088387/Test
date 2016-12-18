package com.chaungying.use_car.bean;

import java.util.List;

/**
 * @author 种耀淮 在2016年09月09日17:31创建
 */
public class UseCarRecord {


    /**
     * datas : [{"createTime":"2016-09-09 17:23:11","carId":1,"groupName":"公","phone":"15354095859","appointmentDate":"2016-09-09","appointmentBeginTime":"17:50","state":0,"remarks":null,"appointmentEndTime":"18:00","id":7,"carName":"别克君越","userName":"测试人员_东","operaterName":"测试人员_东","operaterId":4524}]
     * respCode : 1001
     * respMsg : 成功！
     */

    private int respCode;
    private String respMsg;
    /**
     * createTime : 2016-09-09 17:23:11
     * carId : 1
     * groupName : 公
     * phone : 15354095859
     * appointmentDate : 2016-09-09
     * appointmentBeginTime : 17:50
     * state : 0
     * remarks : null
     * appointmentEndTime : 18:00
     * id : 7
     * carName : 别克君越
     * userName : 测试人员_东
     * operaterName : 测试人员_东
     * operaterId : 4524
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
        private int carId;
        private String groupName;
        private String phone;
        private String appointmentDate;
        private String appointmentBeginTime;
        private int state;
        private Object remarks;
        private String appointmentEndTime;
        private int id;
        private String carName;
        private String userName;
        private String operaterName;
        private int operaterId;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getCarId() {
            return carId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
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

        public String getCarName() {
            return carName;
        }

        public void setCarName(String carName) {
            this.carName = carName;
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
