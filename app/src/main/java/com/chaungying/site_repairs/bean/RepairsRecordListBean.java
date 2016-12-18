package com.chaungying.site_repairs.bean;

import java.util.List;

/**
 * @author 王晓赛 or 2016/6/28
 */
public class RepairsRecordListBean {


    /**
     * respCode : 1001
     * items : [{"dependency":null,"id":189,"subtitles":[{"val":8,"id":8,"level":null,"name":"公共管道漏水","orders":1,"type":3},{"val":9,"id":9,"level":null,"name":"公共线路短路","orders":2,"type":3},{"val":10,"id":10,"level":null,"name":"卧室","orders":3,"type":3},{"val":11,"id":11,"level":null,"name":"卫生间","orders":4,"type":3},{"val":12,"id":12,"level":null,"name":"厨房","orders":5,"type":3},{"val":13,"id":13,"level":null,"name":"客厅","orders":6,"type":3}],"title":"时间选择","placeholder":null,"itemType":52,"applicationId":104,"requestmethod":null,"image":null,"orders":1,"type":2,"dicTYpe":3,"fieldname":"xx"},{"dependency":null,"id":188,"subtitles":[{"val":1,"id":50,"level":null,"name":"类型1","orders":1,"type":14},{"val":2,"id":51,"level":null,"name":"类型2","orders":2,"type":14}],"title":"排序选择","placeholder":null,"itemType":52,"applicationId":104,"requestmethod":null,"image":null,"orders":2,"type":2,"dicTYpe":14,"fieldname":"xx"},{"placeholder":null,"itemType":53,"applicationId":104,"image":null,"type":2,"fieldname":"xx","datas":[],"id":187,"dependency":null,"title":"筛选","requestmethod":null,"orders":3,"dicTYpe":null},{"dependency":115,"id":186,"title":"仅看我的","placeholder":null,"itemType":54,"applicationId":104,"requestmethod":null,"image":null,"orders":21,"type":2,"dicTYpe":null,"fieldname":"xx"},{"dependency":115,"id":185,"title":"审批状态","placeholder":null,"itemType":2,"applicationId":104,"requestmethod":"http://139.129.10.71:6060/propertyInterface/repair/showDics.action?type=14","image":null,"orders":22,"type":2,"dicTYpe":null,"fieldname":"xx"},{"dependency":115,"id":184,"title":"客户级别","placeholder":null,"itemType":2,"applicationId":104,"requestmethod":"http://139.129.10.71:6060/propertyInterface/repair/showDics.action?type=14","image":null,"orders":23,"type":2,"dicTYpe":null,"fieldname":"xx"},{"dependency":115,"id":183,"title":"客户","placeholder":null,"itemType":2,"applicationId":104,"requestmethod":"http://139.129.10.71:6060/propertyInterface/repair/showDics.action?type=14","image":null,"orders":24,"type":2,"dicTYpe":null,"fieldname":"xx"},{"dependency":115,"id":182,"title":"人员","placeholder":null,"itemType":2,"applicationId":104,"requestmethod":"http://139.129.10.71:6060/propertyInterface/repair/showDics.action?type=14","image":null,"orders":25,"type":2,"dicTYpe":null,"fieldname":"xx"}]
     * userId : userId
     * respMsg : 成功！
     * header2 : 报修记录
     * header1 : 现场报修
     */

    private int respCode;
    private String userId;
    private String respMsg;
    private String header2;
    private String header1;
    /**
     * dependency : null
     * id : 189
     * subtitles : [{"val":8,"id":8,"level":null,"name":"公共管道漏水","orders":1,"type":3},{"val":9,"id":9,"level":null,"name":"公共线路短路","orders":2,"type":3},{"val":10,"id":10,"level":null,"name":"卧室","orders":3,"type":3},{"val":11,"id":11,"level":null,"name":"卫生间","orders":4,"type":3},{"val":12,"id":12,"level":null,"name":"厨房","orders":5,"type":3},{"val":13,"id":13,"level":null,"name":"客厅","orders":6,"type":3}]
     * title : 时间选择
     * placeholder : null
     * itemType : 52
     * applicationId : 104
     * requestmethod : null
     * image : null
     * orders : 1
     * type : 2
     * dicTYpe : 3
     * fieldname : xx
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
        private Object dependency;
        private int id;
        private String title;
        private Object placeholder;
        private int itemType;
        private int applicationId;
        private Object requestmethod;
        private Object image;
        private int orders;
        private int type;
        private int dicTYpe;
        private String fieldname;
        /**
         * val : 8
         * id : 8
         * level : null
         * name : 公共管道漏水
         * orders : 1
         * type : 3
         */

        private List<SubtitlesBean> subtitles;

        public Object getDependency() {
            return dependency;
        }

        public void setDependency(Object dependency) {
            this.dependency = dependency;
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

        public Object getPlaceholder() {
            return placeholder;
        }

        public void setPlaceholder(Object placeholder) {
            this.placeholder = placeholder;
        }

        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        public int getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(int applicationId) {
            this.applicationId = applicationId;
        }

        public Object getRequestmethod() {
            return requestmethod;
        }

        public void setRequestmethod(Object requestmethod) {
            this.requestmethod = requestmethod;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public int getOrders() {
            return orders;
        }

        public void setOrders(int orders) {
            this.orders = orders;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getDicTYpe() {
            return dicTYpe;
        }

        public void setDicTYpe(int dicTYpe) {
            this.dicTYpe = dicTYpe;
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
            private int id;
            private Object level;
            private String name;
            private int orders;
            private int type;

            public int getVal() {
                return val;
            }

            public void setVal(int val) {
                this.val = val;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getLevel() {
                return level;
            }

            public void setLevel(Object level) {
                this.level = level;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getOrders() {
                return orders;
            }

            public void setOrders(int orders) {
                this.orders = orders;
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
