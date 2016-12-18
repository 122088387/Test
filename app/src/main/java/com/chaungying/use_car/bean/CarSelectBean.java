package com.chaungying.use_car.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 王晓赛 or 2016/6/29
 *
 * 某个车辆的实体
 */
public class CarSelectBean {

    /**
     * datas : [{"createTime":"","appointmentDate":"2016-07-07","groupName":"北京创赢无限","phone":"17778251120","appointmentBeginTime":"9:00","state":0,"remarks":"有急用","appointmentEndTime":"12:00","id":1,"meetingId":1,"userName":"任瑞志","operaterName":"","operaterId":0}]
     * respCode : 1001
     * respMsg : 成功！
     * user : {"id":4512,"createTime":{"nanos":0,"time":1466471365000,"minutes":9,"seconds":25,"hours":9,"month":5,"year":116,"timezoneOffset":-480,"day":2,"date":21},"sex":2,"payPassword":"","qrcode":"","state":0,"userName":"任","qrcodeDir":"","qrcodeFileName":"","password":"","portrait":"","roleId":0,"loginName":"17778251120"}
     */

    private int respCode;
    private String respMsg;
    /**
     * id : 4512
     * createTime : {"nanos":0,"time":1466471365000,"minutes":9,"seconds":25,"hours":9,"month":5,"year":116,"timezoneOffset":-480,"day":2,"date":21}
     * sex : 2
     * payPassword :
     * qrcode :
     * state : 0
     * userName : 任
     * qrcodeDir :
     * qrcodeFileName :
     * password :
     * portrait :
     * roleId : 0
     * loginName : 17778251120
     */

    private UserBean user;
    /**
     * createTime :
     * appointmentDate : 2016-07-07
     * groupName : 北京创赢无限
     * phone : 17778251120
     * appointmentBeginTime : 9:00
     * state : 0
     * remarks : 有急用
     * appointmentEndTime : 12:00
     * id : 1
     * carId : 1
     * userName : 任瑞志
     * operaterName :
     * operaterId : 0
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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class UserBean {
        private int id;
        /**
         * nanos : 0
         * time : 1466471365000
         * minutes : 9
         * seconds : 25
         * hours : 9
         * month : 5
         * year : 116
         * timezoneOffset : -480
         * day : 2
         * date : 21
         */

        private CreateTimeBean createTime;
        private int sex;
        private String payPassword;
        private String qrcode;
        private int state;
        private String userName;
        private String qrcodeDir;
        private String qrcodeFileName;
        private String password;
        private String portrait;
        private int roleId;
        private String loginName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public CreateTimeBean getCreateTime() {
            return createTime;
        }

        public void setCreateTime(CreateTimeBean createTime) {
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

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
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

        /**
         * user中的bean
         */
        public static class CreateTimeBean {
            private int nanos;
            private long time;
            private int minutes;
            private int seconds;
            private int hours;
            private int month;
            private int year;
            private int timezoneOffset;
            private int day;
            private int date;

            public int getNanos() {
                return nanos;
            }

            public void setNanos(int nanos) {
                this.nanos = nanos;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }
        }
    }

    public static class DatasBean implements Comparable<DatasBean>{
        private String createTime;
        private String appointmentDate;
        private String groupName;
        private String phone;
        private String appointmentBeginTime;
        private int state;
        private String remarks;
        private String appointmentEndTime;
        private int id;
        private int carId;
        private String userName;
        private String operaterName;
        private int operaterId;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getAppointmentDate() {
            return appointmentDate;
        }

        public void setAppointmentDate(String appointmentDate) {
            this.appointmentDate = appointmentDate;
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

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
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

        public int getCarId() {
            return carId;
        }

        public void setCarId(int meetingId) {
            this.carId = meetingId;
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

        @Override
        public int compareTo(DatasBean another) {
            return getLongTime(appointmentBeginTime) - getLongTime(another.appointmentEndTime);
        }
        public int getLongTime(String timeStr) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Calendar calendar = Calendar.getInstance();
            try {
                Date date = format.parse(timeStr);
                calendar.setTime(date);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                int startTime = hour * 60 * 60 + minute * 60;
                return startTime;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return -1;
        }
    }

}
