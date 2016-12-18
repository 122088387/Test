package com.chaungying.qiandao.bean;

import java.util.List;

/**
 * @author 王晓赛 or 2016/7/28
 */
public class TongJiBean {


    /**
     * respCode : 1001
     * data : [{"id":317,"createTime":"2016-11-25 21:07:32","wifiName":"(null)","districtId":null,"flag":0,"signInAddress":"河北省石家庄市裕华区谈固东街134号","signInLatitude":"38.034324","signInLongitude":"114.576678","signInDate":"2016-11-25","SignInTime":"21:07","memberId":4613,"memberName":""},{"id":318,"createTime":"2016-11-25 21:07:49","wifiName":"(null)","districtId":null,"flag":1,"signInAddress":"河北省石家庄市裕华区谈固东街134号","signInLatitude":"38.034324","signInLongitude":"114.576678","signInDate":"2016-11-25","SignInTime":"21:07","memberId":4613,"memberName":""}]
     * respMsg : 成功！
     * isAdmin : 1
     */

    private int respCode;
    private String respMsg;
    private int isAdmin;
    /**
     * id : 317
     * createTime : 2016-11-25 21:07:32
     * wifiName : (null)
     * districtId : null
     * flag : 0
     * signInAddress : 河北省石家庄市裕华区谈固东街134号
     * signInLatitude : 38.034324
     * signInLongitude : 114.576678
     * signInDate : 2016-11-25
     * SignInTime : 21:07
     * memberId : 4613
     * memberName :
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

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private boolean isShow;
        private int id;
        private String createTime;
        private String wifiName;
        private Object districtId;
        private int flag;
        private String signInAddress;
        private String signInLatitude;
        private String signInLongitude;
        private String signInDate;
        private String SignInTime;
        private int memberId;
        private String memberName;
        private String userName;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public boolean isShow() {
            return isShow;
        }

        public void setShow(boolean show) {
            isShow = show;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getWifiName() {
            return wifiName;
        }

        public void setWifiName(String wifiName) {
            this.wifiName = wifiName;
        }

        public Object getDistrictId() {
            return districtId;
        }

        public void setDistrictId(Object districtId) {
            this.districtId = districtId;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getSignInAddress() {
            return signInAddress;
        }

        public void setSignInAddress(String signInAddress) {
            this.signInAddress = signInAddress;
        }

        public String getSignInLatitude() {
            return signInLatitude;
        }

        public void setSignInLatitude(String signInLatitude) {
            this.signInLatitude = signInLatitude;
        }

        public String getSignInLongitude() {
            return signInLongitude;
        }

        public void setSignInLongitude(String signInLongitude) {
            this.signInLongitude = signInLongitude;
        }

        public String getSignInDate() {
            return signInDate;
        }

        public void setSignInDate(String signInDate) {
            this.signInDate = signInDate;
        }

        public String getSignInTime() {
            return SignInTime;
        }

        public void setSignInTime(String SignInTime) {
            this.SignInTime = SignInTime;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }
    }
}
