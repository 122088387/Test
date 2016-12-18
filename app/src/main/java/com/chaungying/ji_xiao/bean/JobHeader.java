package com.chaungying.ji_xiao.bean;

import java.util.List;

/**
 * Created by Chooo on 2016/9/8 0008.
 */
public class JobHeader {


    /**
     * respCode : 1001
     * items : [{"subtitles":[{"val":-1,"name":"时间"},{"val":61,"name":"今日"},{"val":62,"name":"昨日"},{"val":63,"name":"本周"},{"val":64,"name":"本月"}],"orders":1,"fieldname":"date"},{"subtitles":[{"val":-1,"name":"部门"},{"val":2,"name":"开发部"},{"val":3,"name":"销售部"},{"val":4,"name":"公关部"},{"val":5,"name":"市场部"}],"orders":2,"fieldname":"departmentId"}]
     * respMsg : 成功！
     */

    private int respCode;
    private String respMsg;
    /**
     * subtitles : [{"val":-1,"name":"时间"},{"val":61,"name":"今日"},{"val":62,"name":"昨日"},{"val":63,"name":"本周"},{"val":64,"name":"本月"}]
     * orders : 1
     * fieldname : date
     */

    private List<ItemsBean> items;

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

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        private int orders;
        private String fieldname;
        /**
         * val : -1
         * name : 时间
         */

        private List<SubtitlesBean> subtitles;

        public int getOrders() {
            return orders;
        }

        public void setOrders(int orders) {
            this.orders = orders;
        }

        public String getFieldname() {
            return fieldname;
        }

        public void setFieldname(String fieldname) {
            this.fieldname = fieldname;
        }

        public List<SubtitlesBean> getSubtitles() {
            return subtitles;
        }

        public void setSubtitles(List<SubtitlesBean> subtitles) {
            this.subtitles = subtitles;
        }

        public static class SubtitlesBean {
            private int val;
            private String name;

            public int getVal() {
                return val;
            }

            public void setVal(int val) {
                this.val = val;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}

