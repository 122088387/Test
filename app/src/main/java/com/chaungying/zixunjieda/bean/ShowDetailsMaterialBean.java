package com.chaungying.zixunjieda.bean;

import java.util.List;

/**
 * @author 王晓赛 or 2016/8/13
 */
public class ShowDetailsMaterialBean {


    /**
     * datas : [[{"itemtype":58,"title":"材料","value":null,"orders":11},{"itemtype":58,"title":"数量","value":null,"orders":12},{"itemtype":58,"title":"单价","value":null,"orders":13},{"itemtype":58,"title":"金额（￥ ）","value":null,"orders":14}]]
     * respCode : 1001
     * applicationId : 104
     * respMsg : 成功！
     */

    private int respCode;
    private int applicationId;
    private String respMsg;
    /**
     * itemtype : 58
     * title : 材料
     * value : null
     * orders : 11
     */

    private List<List<DatasBean>> datas;

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public List<List<DatasBean>> getDatas() {
        return datas;
    }

    public void setDatas(List<List<DatasBean>> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        private int itemtype;
        private String title;
        private Object value;
        private int orders;

        public int getItemtype() {
            return itemtype;
        }

        public void setItemtype(int itemtype) {
            this.itemtype = itemtype;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public int getOrders() {
            return orders;
        }

        public void setOrders(int orders) {
            this.orders = orders;
        }
    }
}
