package com.chaungying.round_malls1.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王晓赛 or 2016/9/22
 */
public class ShoppingStoreBean implements Serializable {


    /**
     * datas : [{"createTime":"","phone":"4124","state":0,"introduce":"发顺丰","evaluateLevel":"2.88","credential":"","id":1,"address":"124124","userId":"72297c8842604c059b05d28bfb11d10b","name":"412412","owner":"恶趣味","sellNum":114,"buildDate":"2016-08-31","longitude":"116.371067","orders":1111,"latitude":"39.925338","banner":""}]
     * respCode : 1001
     * respMsg : 成功！
     */

    private int respCode;
    private String respMsg;
    /**
     * createTime :
     * phone : 4124
     * state : 0
     * introduce : 发顺丰
     * evaluateLevel : 2.88
     * credential :
     * id : 1
     * address : 124124
     * userId : 72297c8842604c059b05d28bfb11d10b
     * name : 412412
     * owner : 恶趣味
     * sellNum : 114
     * buildDate : 2016-08-31
     * longitude : 116.371067
     * orders : 1111
     * latitude : 39.925338
     * banner :
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

    public static class DatasBean implements Serializable {
        private String createTime;
        private String phone;
        private int state;
        private String introduce;
        private String evaluateLevel;
        private String credential;
        private int id;
        private String address;
        private String userId;
        private String name;
        private String owner;
        private int sellNum;
        private String buildDate;
        private String longitude;
        private int orders;
        private String latitude;
        private String banner;
        private int isCollect;//是否收藏  0没有  1收藏

        public int getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(int isCollect) {
            this.isCollect = isCollect;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getEvaluateLevel() {
            return evaluateLevel;
        }

        public void setEvaluateLevel(String evaluateLevel) {
            this.evaluateLevel = evaluateLevel;
        }

        public String getCredential() {
            return credential;
        }

        public void setCredential(String credential) {
            this.credential = credential;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public int getSellNum() {
            return sellNum;
        }

        public void setSellNum(int sellNum) {
            this.sellNum = sellNum;
        }

        public String getBuildDate() {
            return buildDate;
        }

        public void setBuildDate(String buildDate) {
            this.buildDate = buildDate;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public int getOrders() {
            return orders;
        }

        public void setOrders(int orders) {
            this.orders = orders;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }
    }
}
