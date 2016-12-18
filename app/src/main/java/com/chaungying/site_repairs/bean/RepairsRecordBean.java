package com.chaungying.site_repairs.bean;

import java.util.List;

/**
 * @author 王晓赛 or 2016/6/28
 */
public class  RepairsRecordBean{

    /**
     * respCode : 1001
     * items : [{"elementId":1,"createTime":"2016-06-27 16:59:51","phone":"电话","reason":"8","states":1,"callPhone":null,"remarks":"备注","type":6,"fieldname":"type","id":154,"districtId":1,"repairAddress":"晴雪园 1 1 102","token":null,"userId":4512,"money":null,"signature":null,"buildingId":1,"videos":null,"repairContent":"内容","replyContent":null,"repairTime":null,"voices":null,"callName":null,"images":null,"houseId":1},{"elementId":1,"createTime":"2016-06-27 16:59:51","phone":"电话","reason":"8","states":1,"callPhone":null,"remarks":"备注","type":6,"fieldname":"districtId","id":154,"districtId":1,"repairAddress":"晴雪园 1 1 102","token":null,"userId":4512,"money":null,"signature":null,"buildingId":1,"videos":null,"repairContent":"内容","replyContent":null,"repairTime":null,"voices":null,"callName":null,"images":null,"houseId":1},{"elementId":1,"createTime":"2016-06-27 16:59:51","phone":"电话","reason":"8","states":1,"callPhone":null,"remarks":"备注","type":6,"fieldname":"buildingId","id":154,"districtId":1,"repairAddress":"晴雪园 1 1 102","token":null,"userId":4512,"money":null,"signature":null,"buildingId":1,"videos":null,"repairContent":"内容","replyContent":null,"repairTime":null,"voices":null,"callName":null,"images":null,"houseId":1},{"elementId":1,"createTime":"2016-06-27 16:59:51","phone":"电话","reason":"8","states":1,"callPhone":null,"remarks":"备注","type":6,"fieldname":"elementId","id":154,"districtId":1,"repairAddress":"晴雪园 1 1 102","token":null,"userId":4512,"money":null,"signature":null,"buildingId":1,"videos":null,"repairContent":"内容","replyContent":null,"repairTime":null,"voices":null,"callName":null,"images":null,"houseId":1},{"elementId":1,"createTime":"2016-06-27 16:59:51","phone":"电话","reason":"8","states":1,"callPhone":null,"remarks":"备注","type":6,"fieldname":"houseId","id":154,"districtId":1,"repairAddress":"晴雪园 1 1 102","token":null,"userId":4512,"money":null,"signature":null,"buildingId":1,"videos":null,"repairContent":"内容","replyContent":null,"repairTime":null,"voices":null,"callName":null,"images":null,"houseId":1},{"elementId":1,"createTime":"2016-06-27 16:59:51","phone":"电话","reason":"8","states":1,"callPhone":null,"remarks":"备注","type":6,"fieldname":"phone","id":154,"districtId":1,"repairAddress":"晴雪园 1 1 102","token":null,"userId":4512,"money":null,"signature":null,"buildingId":1,"videos":null,"repairContent":"内容","replyContent":null,"repairTime":null,"voices":null,"callName":null,"images":null,"houseId":1},{"elementId":1,"createTime":"2016-06-27 16:59:51","phone":"电话","reason":"8","states":1,"callPhone":null,"remarks":"备注","type":6,"fieldname":"reason","id":154,"districtId":1,"repairAddress":"晴雪园 1 1 102","token":null,"userId":4512,"money":null,"signature":null,"buildingId":1,"videos":null,"repairContent":"内容","replyContent":null,"repairTime":null,"voices":null,"callName":null,"images":null,"houseId":1},{"elementId":1,"createTime":"2016-06-27 16:59:51","phone":"电话","reason":"8","states":1,"callPhone":null,"remarks":"备注","type":6,"fieldname":"repairContent","id":154,"districtId":1,"repairAddress":"晴雪园 1 1 102","token":null,"userId":4512,"money":null,"signature":null,"buildingId":1,"videos":null,"repairContent":"内容","replyContent":null,"repairTime":null,"voices":null,"callName":null,"images":null,"houseId":1},{"elementId":1,"createTime":"2016-06-27 16:59:51","phone":"电话","reason":"8","states":1,"callPhone":null,"remarks":"备注","type":6,"fieldname":"images","id":154,"districtId":1,"repairAddress":"晴雪园 1 1 102","token":null,"userId":4512,"money":null,"signature":null,"buildingId":1,"videos":null,"repairContent":"内容","replyContent":null,"repairTime":null,"voices":null,"callName":null,"images":null,"houseId":1},{"elementId":1,"createTime":"2016-06-27 16:59:51","phone":"电话","reason":"8","states":1,"callPhone":null,"remarks":"备注","type":6,"fieldname":"remarks","id":154,"districtId":1,"repairAddress":"晴雪园 1 1 102","token":null,"userId":4512,"money":null,"signature":null,"buildingId":1,"videos":null,"repairContent":"内容","replyContent":null,"repairTime":null,"voices":null,"callName":null,"images":null,"houseId":1},{"elementId":1,"createTime":"2016-06-27 16:59:51","phone":"电话","reason":"8","states":1,"callPhone":null,"remarks":"备注","type":6,"fieldname":null,"id":154,"districtId":1,"repairAddress":"晴雪园 1 1 102","token":null,"userId":4512,"money":null,"signature":null,"buildingId":1,"videos":null,"repairContent":"内容","replyContent":null,"repairTime":null,"voices":null,"callName":null,"images":null,"houseId":1}]
     * userId : userId
     * respMsg : 成功！
     * header2 :
     * header1 : 任务处理
     */

    private int respCode;
    private String userId;
    private String respMsg;
    private String header2;
    private String header1;
    /**
     * elementId : 1
     * createTime : 2016-06-27 16:59:51
     * phone : 电话
     * reason : 8
     * states : 1
     * callPhone : null
     * remarks : 备注
     * type : 6
     * fieldname : type
     * id : 154
     * districtId : 1
     * repairAddress : 晴雪园 1 1 102
     * token : null
     * userId : 4512
     * money : null
     * signature : null
     * buildingId : 1
     * videos : null
     * repairContent : 内容
     * replyContent : null
     * repairTime : null
     * voices : null
     * callName : null
     * images : null
     * houseId : 1
     */

    private List<ItemsBean> items;

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getHeader2() {
        return header2;
    }

    public void setHeader2(String header2) {
        this.header2 = header2;
    }

    public String getHeader1() {
        return header1;
    }

    public void setHeader1(String header1) {
        this.header1 = header1;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        private int elementId;
        private String createTime;
        private String phone;
        private String reason;
        private int states;
        private Object callPhone;
        private String remarks;
        private int type;
        private String fieldname;
        private int id;
        private int districtId;
        private String repairAddress;
        private Object token;
        private int userId;
        private Object money;
        private Object signature;
        private int buildingId;
        private Object videos;
        private String repairContent;
        private Object replyContent;
        private Object repairTime;
        private Object voices;
        private Object callName;
        private Object images;
        private int houseId;

        public int getElementId() {
            return elementId;
        }

        public void setElementId(int elementId) {
            this.elementId = elementId;
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

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public int getStates() {
            return states;
        }

        public void setStates(int states) {
            this.states = states;
        }

        public Object getCallPhone() {
            return callPhone;
        }

        public void setCallPhone(Object callPhone) {
            this.callPhone = callPhone;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getFieldname() {
            return fieldname;
        }

        public void setFieldname(String fieldname) {
            this.fieldname = fieldname;
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

        public String getRepairAddress() {
            return repairAddress;
        }

        public void setRepairAddress(String repairAddress) {
            this.repairAddress = repairAddress;
        }

        public Object getToken() {
            return token;
        }

        public void setToken(Object token) {
            this.token = token;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public Object getMoney() {
            return money;
        }

        public void setMoney(Object money) {
            this.money = money;
        }

        public Object getSignature() {
            return signature;
        }

        public void setSignature(Object signature) {
            this.signature = signature;
        }

        public int getBuildingId() {
            return buildingId;
        }

        public void setBuildingId(int buildingId) {
            this.buildingId = buildingId;
        }

        public Object getVideos() {
            return videos;
        }

        public void setVideos(Object videos) {
            this.videos = videos;
        }

        public String getRepairContent() {
            return repairContent;
        }

        public void setRepairContent(String repairContent) {
            this.repairContent = repairContent;
        }

        public Object getReplyContent() {
            return replyContent;
        }

        public void setReplyContent(Object replyContent) {
            this.replyContent = replyContent;
        }

        public Object getRepairTime() {
            return repairTime;
        }

        public void setRepairTime(Object repairTime) {
            this.repairTime = repairTime;
        }

        public Object getVoices() {
            return voices;
        }

        public void setVoices(Object voices) {
            this.voices = voices;
        }

        public Object getCallName() {
            return callName;
        }

        public void setCallName(Object callName) {
            this.callName = callName;
        }

        public Object getImages() {
            return images;
        }

        public void setImages(Object images) {
            this.images = images;
        }

        public int getHouseId() {
            return houseId;
        }

        public void setHouseId(int houseId) {
            this.houseId = houseId;
        }
    }
}
