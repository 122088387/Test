package com.chaungying.common.bean;

import java.util.List;

/**
 * @author 王晓赛 or 2016/8/3
 */
public class FillListBean {

    /**
     * respMsg : 正常
     * respCode : 0
     * data : [[{"value":"测试数据0","title":"标题:","itemtype":0,"order":0},{"value":"2016-05-23","title":"日期:","itemtype":1,"order":1},{"value":"张三","title":"填报人:","itemtype":0,"order":2}],[{"value":"测试数据1","title":"标题:","itemtype":0,"order":0},{"value":"2016-05-23","title":"日期:","itemtype":1,"order":1},{"value":"张三","title":"填报人:","itemtype":0,"order":2}],[{"value":"测试数据2","title":"标题:","itemtype":0,"order":0},{"value":"2016-05-23","title":"日期:","itemtype":1,"order":1},{"value":"张三","title":"填报人:","itemtype":0,"order":2}],[{"value":"测试数据3","title":"标题:","itemtype":0,"order":0},{"value":"2016-05-23","title":"日期:","itemtype":1,"order":1},{"value":"张三","title":"填报人:","itemtype":0,"order":2}],[{"value":"测试数据4","title":"标题:","itemtype":0,"order":0},{"value":"2016-05-23","title":"日期:","itemtype":1,"order":1},{"value":"张三","title":"填报人:","itemtype":0,"order":2}],[{"value":"测试数据5","title":"标题:","itemtype":0,"order":0},{"value":"2016-05-23","title":"日期:","itemtype":1,"order":1},{"value":"张三","title":"填报人:","itemtype":0,"order":2}],[{"value":"测试数据6","title":"标题:","itemtype":0,"order":0},{"value":"2016-05-23","title":"日期:","itemtype":1,"order":1},{"value":"张三","title":"填报人:","itemtype":0,"order":2}],[{"value":"测试数据7","title":"标题:","itemtype":0,"order":0},{"value":"2016-05-23","title":"日期:","itemtype":1,"order":1},{"value":"张三","title":"填报人:","itemtype":0,"order":2}],[{"value":"测试数据8","title":"标题:","itemtype":0,"order":0},{"value":"2016-05-23","title":"日期:","itemtype":1,"order":1},{"value":"张三","title":"填报人:","itemtype":0,"order":2}],[{"value":"测试数据9","title":"标题:","itemtype":0,"order":0},{"value":"2016-05-23","title":"日期:","itemtype":1,"order":1},{"value":"张三","title":"填报人:","itemtype":0,"order":2}]]
     * layoutid : 0
     * applicationid : 0
     */

    private String respMsg;
    private int respCode;
    private int layoutid;
    private int applicationId;
    /**
     * value : 测试数据0
     * title : 标题:
     * itemtype : 0
     * order : 0
     */

    private List<List<DataBean>> data;

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public int getLayoutid() {
        return layoutid;
    }

    public void setLayoutid(int layoutid) {
        this.layoutid = layoutid;
    }

    public int getApplicationid() {
        return applicationId;
    }

    public void setApplicationid(int applicationid) {
        this.applicationId = applicationid;
    }

    public List<List<DataBean>> getData() {
        return data;
    }

    public void setData(List<List<DataBean>> data) {
        this.data = data;
    }

    public static class DataBean {
        private String value;
        private String title;
        private int itemtype;
        private int logicId;
        private int orders;
        private int isShowTitle;

        public int getIsShowTitle() {
            return isShowTitle;
        }

        public void setIsShowTitle(int isShowTitle) {
            this.isShowTitle = isShowTitle;
        }

        public int getLogicId() {
            return logicId;
        }

        public void setLogicId(int logicId) {
            this.logicId = logicId;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getItemtype() {
            return itemtype;
        }

        public void setItemtype(int itemtype) {
            this.itemtype = itemtype;
        }

        public int getOrders() {
            return orders;
        }

        public void setOrders(int orders) {
            this.orders = orders;
        }
    }
}
