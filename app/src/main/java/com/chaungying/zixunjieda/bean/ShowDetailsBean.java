package com.chaungying.zixunjieda.bean;

import java.util.List;

/**
 * @author 王晓赛 or 2016/8/5
 */
public class ShowDetailsBean {


    /**
     * datas : [{"itemtype":58,"id":455,"title":"所属园区","linkUrl":"","value":"未来科技城","orders":1},{"itemtype":58,"id":455,"title":"所属楼宇","linkUrl":"","value":"1","orders":2},{"itemtype":58,"id":455,"title":"所属楼层","linkUrl":"","value":"1","orders":3},{"itemtype":58,"id":455,"title":"所属房间","linkUrl":"","value":"102","orders":4},{"itemtype":58,"id":455,"title":"地址","linkUrl":"","value":"601","orders":5},{"itemtype":58,"id":455,"title":"报修类别","linkUrl":"","value":"室内报修","orders":6},{"itemtype":58,"id":455,"title":"报修人","linkUrl":"","value":"王小赛","orders":7},{"itemtype":58,"id":455,"title":"联系电话","linkUrl":"","value":"68686868","orders":8},{"itemtype":58,"id":455,"title":"报修内容","linkUrl":"","value":"门窗损坏","orders":9},{"itemtype":5,"id":455,"title":"现场拍照","linkUrl":"","value":"http://139.129.10.71:6060/propertyInterface/uploadFile/repairImages/2016/2016-08-23/1471912982191.JPG","orders":10},{"itemtype":19,"id":455,"title":"现场录音","linkUrl":"","value":"http://139.129.10.71:6060/propertyInterface/uploadFile/repairVoices/2016/2016-08-23/1471912982282.amr","orders":11},{"itemtype":59,"id":455,"title":"材料明细","linkUrl":"192.168.1.122/propertyInterface/applicationList/getApplicationDetailByIdForList.action?entityName=ApplicationDetailList&appId=134","value":null,"orders":1},{"itemtype":58,"id":455,"title":"人工费（￥）","linkUrl":"","value":{},"orders":2},{"itemtype":58,"id":455,"title":"费用合计（￥）","linkUrl":"","value":{},"orders":3},{"itemtype":58,"id":455,"title":"维修结果","linkUrl":"","value":"吧","orders":4},{"itemtype":58,"id":455,"title":"业主意见","linkUrl":"","value":"吧","orders":6},{"itemtype":5,"id":455,"title":"业主签字","linkUrl":"","value":null,"orders":7},{"itemtype":58,"id":455,"title":"签字日期","linkUrl":"","value":"2016-08-23","orders":8},{"itemtype":58,"id":455,"title":"备注","linkUrl":"","value":"你好","orders":9}]
     * respCode : 1001
     * userId : 0
     * applicationId : 104
     * grabberId : 4513
     * states : 0   //0  不显示按钮   1  完成订单（暂时不用） 2 报修处理
     * respMsg : 成功！
     */

    private int respCode;
    private String userId;
    private int applicationId;
    private int grabberId;
    private int states;
    private String respMsg;
    private int repairUserId = -1;
    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * itemtype : 58
     * id : 455
     * title : 所属园区
     * linkUrl :
     * value : 未来科技城
     * orders : 1
     */

    private List<DatasBean> datas;

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

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getGrabberId() {
        return grabberId;
    }

    public void setGrabberId(int grabberId) {
        this.grabberId = grabberId;
    }

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public int getRepairUserId() {
        return repairUserId;
    }

    public void setRepairUserId(int repairUserId) {
        this.repairUserId = repairUserId;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        private int itemtype;
        private int id;
        private String title;
        private String linkUrl;
        private String value;
        private int orders;

        public int getItemtype() {
            return itemtype;
        }

        public void setItemtype(int itemtype) {
            this.itemtype = itemtype;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getOrders() {
            return orders;
        }

        public void setOrders(int orders) {
            this.orders = orders;
        }
    }

    @Override
    public String toString() {
        return "ShowDetailsBean{" +
                "respCode=" + respCode +
                ", userId=" + userId +
                ", applicationId=" + applicationId +
                ", grabberId=" + grabberId +
                ", states=" + states +
                ", respMsg='" + respMsg + '\'' +
                '}';
    }
}
