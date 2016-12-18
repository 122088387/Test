package com.chaungying.gongzuotai.bean;

import java.util.List;

/**
 * @author 王晓赛 or 2016/8/15
 */
public class AllMsgBean {


    /**
     * respCode : 1001
     * initTime : 2016-08-15 11:00:30
     * data : [{"layoutid":55,"datas":[[{"itemtype":58,"id":106,"title":"订餐人","value":"工资","orders":1},{"itemtype":58,"id":106,"title":"联系方式","value":"呵呵","orders":2},{"itemtype":58,"id":106,"title":"送餐日期","value":"2016-08-13","orders":3}]],"applicationId":103,"type":1},{"layoutid":57,"datas":[[{"itemtype":58,"id":367,"title":"电话","value":null,"orders":1},{"itemtype":58,"id":367,"title":"报修内容","value":null,"orders":2},{"itemtype":58,"id":367,"title":"备注","value":null,"orders":3},{"itemtype":1,"id":367,"title":"所属小区","value":null,"orders":4}],[{"itemtype":58,"id":366,"title":"电话","value":null,"orders":1},{"itemtype":58,"id":366,"title":"报修内容","value":null,"orders":2},{"itemtype":58,"id":366,"title":"备注","value":null,"orders":3},{"itemtype":1,"id":366,"title":"所属小区","value":null,"orders":4}],[{"itemtype":58,"id":365,"title":"电话","value":null,"orders":1},{"itemtype":58,"id":365,"title":"报修内容","value":null,"orders":2},{"itemtype":58,"id":365,"title":"备注","value":null,"orders":3},{"itemtype":1,"id":365,"title":"所属小区","value":null,"orders":4}],[{"itemtype":58,"id":364,"title":"电话","value":null,"orders":1},{"itemtype":58,"id":364,"title":"报修内容","value":null,"orders":2},{"itemtype":58,"id":364,"title":"备注","value":null,"orders":3},{"itemtype":1,"id":364,"title":"所属小区","value":null,"orders":4}],[{"itemtype":58,"id":363,"title":"电话","value":null,"orders":1},{"itemtype":58,"id":363,"title":"报修内容","value":null,"orders":2},{"itemtype":58,"id":363,"title":"备注","value":null,"orders":3},{"itemtype":1,"id":363,"title":"所属小区","value":null,"orders":4}],[{"itemtype":58,"id":362,"title":"电话","value":null,"orders":1},{"itemtype":58,"id":362,"title":"报修内容","value":null,"orders":2},{"itemtype":58,"id":362,"title":"备注","value":null,"orders":3},{"itemtype":1,"id":362,"title":"所属小区","value":null,"orders":4}],[{"itemtype":58,"id":361,"title":"电话","value":"噢噢","orders":1},{"itemtype":58,"id":361,"title":"报修内容","value":"的","orders":2},{"itemtype":58,"id":361,"title":"备注","value":"噢噢","orders":3},{"itemtype":1,"id":361,"title":"所属小区","value":"晴雪园","orders":4}],[{"itemtype":58,"id":360,"title":"电话","value":null,"orders":1},{"itemtype":58,"id":360,"title":"报修内容","value":null,"orders":2},{"itemtype":58,"id":360,"title":"备注","value":null,"orders":3},{"itemtype":1,"id":360,"title":"所属小区","value":null,"orders":4}],[{"itemtype":58,"id":359,"title":"电话","value":"噢噢","orders":1},{"itemtype":58,"id":359,"title":"报修内容","value":"的","orders":2},{"itemtype":58,"id":359,"title":"备注","value":"噢噢","orders":3},{"itemtype":1,"id":359,"title":"所属小区","value":"晴雪园","orders":4}],[{"itemtype":58,"id":358,"title":"电话","value":"噢噢","orders":1},{"itemtype":58,"id":358,"title":"报修内容","value":"的","orders":2},{"itemtype":58,"id":358,"title":"备注","value":"噢噢","orders":3},{"itemtype":1,"id":358,"title":"所属小区","value":"晴雪园","orders":4}],[{"itemtype":58,"id":357,"title":"电话","value":null,"orders":1},{"itemtype":58,"id":357,"title":"报修内容","value":null,"orders":2},{"itemtype":58,"id":357,"title":"备注","value":null,"orders":3},{"itemtype":1,"id":357,"title":"所属小区","value":"晴雪园","orders":4}],[{"itemtype":58,"id":356,"title":"电话","value":"噢噢","orders":1},{"itemtype":58,"id":356,"title":"报修内容","value":"的","orders":2},{"itemtype":58,"id":356,"title":"备注","value":"噢噢","orders":3},{"itemtype":1,"id":356,"title":"所属小区","value":"晴雪园","orders":4}],[{"itemtype":58,"id":355,"title":"电话","value":"噢噢","orders":1},{"itemtype":58,"id":355,"title":"报修内容","value":"的","orders":2},{"itemtype":58,"id":355,"title":"备注","value":"噢噢","orders":3},{"itemtype":1,"id":355,"title":"所属小区","value":"晴雪园","orders":4}],[{"itemtype":58,"id":354,"title":"电话","value":"吧","orders":1},{"itemtype":58,"id":354,"title":"报修内容","value":"健健康康","orders":2},{"itemtype":58,"id":354,"title":"备注","value":"我要用","orders":3},{"itemtype":1,"id":354,"title":"所属小区","value":"晴雪园","orders":4}],[{"itemtype":58,"id":353,"title":"电话","value":"地吃","orders":1},{"itemtype":58,"id":353,"title":"报修内容","value":"默默","orders":2},{"itemtype":58,"id":353,"title":"备注","value":"噢噢","orders":3},{"itemtype":1,"id":353,"title":"所属小区","value":"晴雪园","orders":4}]],"applicationId":104,"type":2}]
     * respMsg : 成功！
     */

    private int respCode;
    private String initTime;
    private String respMsg;
    /**
     * layoutid : 55
     * datas : [[{"itemtype":58,"id":106,"title":"订餐人","value":"工资","orders":1},{"itemtype":58,"id":106,"title":"联系方式","value":"呵呵","orders":2},{"itemtype":58,"id":106,"title":"送餐日期","value":"2016-08-13","orders":3}]]
     * applicationId : 103
     * type : 1
     */

    private List<DataBean> data;

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getInitTime() {
        return initTime;
    }

    public void setInitTime(String initTime) {
        this.initTime = initTime;
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

    public static class DataBean {
        private int layoutid;
        private int applicationId;
        private int type;
        /**
         * itemtype : 58
         * id : 106
         * title : 订餐人
         * value : 工资
         * orders : 1
         */

        private List<List<DatasBean>> datas;

        public int getLayoutid() {
            return layoutid;
        }

        public void setLayoutid(int layoutid) {
            this.layoutid = layoutid;
        }

        public int getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(int applicationId) {
            this.applicationId = applicationId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<List<DatasBean>> getDatas() {
            return datas;
        }

        public void setDatas(List<List<DatasBean>> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            private int itemtype;
            private int logicId;
            private String title;
            private String value;
            private int orders;

            public int getItemtype() {
                return itemtype;
            }

            public void setItemtype(int itemtype) {
                this.itemtype = itemtype;
            }

            public int getLogicId() {
                return logicId;
            }

            public void setLogicId(int logicId) {
                this.logicId = logicId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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
    }
}
