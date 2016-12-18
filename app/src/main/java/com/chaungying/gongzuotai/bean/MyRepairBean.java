package com.chaungying.gongzuotai.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/8/19
 */
public class MyRepairBean {
    /**
     * respCode : 1001
     * initTime : 2016-09-07 10:08:40
     * data : [{"datas":[],"applicationId":104},{"datas":[[{"itemtype":58,"title":"","logicId":55,"value":"窗户","orders":1},{"itemtype":58,"title":"报修人","logicId":55,"value":"郭晓利","orders":2},{"itemtype":58,"title":"电话","logicId":55,"value":"13366247548","orders":3}],[{"itemtype":58,"title":"","logicId":54,"value":"","orders":1},{"itemtype":58,"title":"报修人","logicId":54,"value":"郭晓利","orders":2},{"itemtype":58,"title":"电话","logicId":54,"value":"13366247548","orders":3}],[{"itemtype":58,"title":"","logicId":53,"value":"22344","orders":1},{"itemtype":58,"title":"报修人","logicId":53,"value":"admin","orders":2},{"itemtype":58,"title":"电话","logicId":53,"value":"15303239930","orders":3}],[{"itemtype":58,"title":"","logicId":51,"value":"","orders":1},{"itemtype":58,"title":"报修人","logicId":51,"value":"测试人员_赛","orders":2},{"itemtype":58,"title":"电话","logicId":51,"value":"15631188403","orders":3}],[{"itemtype":58,"title":"","logicId":45,"value":"默默","orders":1},{"itemtype":58,"title":"报修人","logicId":45,"value":"测试人员_赛","orders":2},{"itemtype":58,"title":"电话","logicId":45,"value":"15631188403","orders":3}]],"applicationId":106}]
     * respMsg : 成功！
     * type : 3
     */

    private int respCode;
    private String initTime;
    private String respMsg;
    private int type;
    /**
     * datas : []
     * applicationId : 104
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int applicationId;
        private List<ArrayList<DatasBean>> datas;

        public int getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(int applicationId) {
            this.applicationId = applicationId;
        }

        public List<ArrayList<DatasBean>> getDatas() {
            return datas;
        }

        public void setDatas(List<ArrayList<DatasBean>> datas) {
            this.datas = datas;
        }

        public static class DatasBean {

            private int applicationId;

            private int itemtype;
            private String title;
            private int logicId;
            private String value;
            private int orders;
            private int dataType;
            private String countersignTime;

            public String getCountersignTime() {
                return countersignTime;
            }

            public void setCountersignTime(String countersignTime) {
                this.countersignTime = countersignTime;
            }

            public int getDataType() {
                return dataType;
            }

            public void setDataType(int dataType) {
                this.dataType = dataType;
            }

            public int getApplicationId() {
                return applicationId;
            }

            public void setApplicationId(int applicationId) {
                this.applicationId = applicationId;
            }

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

            public int getOrders() {
                return orders;
            }

            public void setOrders(int orders) {
                this.orders = orders;
            }
        }
    }

//    /**
//     * respCode : 1001
//     * initTime : 2016-08-19 14:42:20
//     * data : [[{"itemtype":58,"title":"","logicId":394,"value":"秃头","orders":1},{"itemtype":58,"title":"报修人","logicId":394,"value":"现在","orders":2},{"itemtype":58,"title":"电话","logicId":394,"value":"来咯","orders":3}]]
//     * respMsg : 成功！
//     * type : 3
//     */
//
//    private int respCode;
//    private String initTime;
//    private String respMsg;
//    private int type;
//    /**
//     * itemtype : 58
//     * title :
//     * logicId : 394
//     * value : 秃头
//     * orders : 1
//     */
//
//    private List<List<DataBean>> data;
//
//    public int getRespCode() {
//        return respCode;
//    }
//
//    public void setRespCode(int respCode) {
//        this.respCode = respCode;
//    }
//
//    public String getInitTime() {
//        return initTime;
//    }
//
//    public void setInitTime(String initTime) {
//        this.initTime = initTime;
//    }
//
//    public String getRespMsg() {
//        return respMsg;
//    }
//
//    public void setRespMsg(String respMsg) {
//        this.respMsg = respMsg;
//    }
//
//    public int getType() {
//        return type;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }
//
//    public List<List<DataBean>> getData() {
//        return data;
//    }
//
//    public void setData(List<List<DataBean>> data) {
//        this.data = data;
//    }
//
}
