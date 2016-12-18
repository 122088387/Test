package com.chaungying.avery_menu.bean;

import java.util.List;

/**
 * @author 王晓赛 or 2016/8/16
 */
public class MenuBean {


    /**
     * respCode : 1001
     * data : [{"id":5,"createTime":"","viewDate":"2016-08-17","districtId":0,"cookbookInfos":[{"id":33,"cookbookId":5,"infoValue":"宫保鸡丁","infoId":70,"type":1},{"id":34,"cookbookId":5,"infoValue":"宫保鸡丁","infoId":70,"type":2},{"id":35,"cookbookId":5,"infoValue":"糖醋里脊","infoId":69,"type":3}]}]
     * respMsg : 成功！
     */

    private int respCode;
    private String respMsg;
    /**
     * id : 5
     * createTime :
     * viewDate : 2016-08-17
     * districtId : 0
     * cookbookInfos : [{"id":33,"cookbookId":5,"infoValue":"宫保鸡丁","infoId":70,"type":1},{"id":34,"cookbookId":5,"infoValue":"宫保鸡丁","infoId":70,"type":2},{"id":35,"cookbookId":5,"infoValue":"糖醋里脊","infoId":69,"type":3}]
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private String createTime;
        private String viewDate;
        private int districtId;
        /**
         * id : 33
         * cookbookId : 5
         * infoValue : 宫保鸡丁
         * infoId : 70
         * type : 1
         */

        private List<CookbookInfosBean> cookbookInfos;

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

        public String getViewDate() {
            return viewDate;
        }

        public void setViewDate(String viewDate) {
            this.viewDate = viewDate;
        }

        public int getDistrictId() {
            return districtId;
        }

        public void setDistrictId(int districtId) {
            this.districtId = districtId;
        }

        public List<CookbookInfosBean> getCookbookInfos() {
            return cookbookInfos;
        }

        public void setCookbookInfos(List<CookbookInfosBean> cookbookInfos) {
            this.cookbookInfos = cookbookInfos;
        }

        public static class CookbookInfosBean {
            private int id;
            private int cookbookId;
            private String infoValue;
            private int infoId;
            private int type;
            private String infoImage;

            public String getInfoImage() {
                return infoImage;
            }

            public void setInfoImage(String infoImage) {
                this.infoImage = infoImage;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getCookbookId() {
                return cookbookId;
            }

            public void setCookbookId(int cookbookId) {
                this.cookbookId = cookbookId;
            }

            public String getInfoValue() {
                return infoValue;
            }

            public void setInfoValue(String infoValue) {
                this.infoValue = infoValue;
            }

            public int getInfoId() {
                return infoId;
            }

            public void setInfoId(int infoId) {
                this.infoId = infoId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
