package com.chaungying.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 配置界面中json对应的bean
 *
 * @author 王晓赛 or 2016/6/24
 */
public class ConfigBean implements Serializable {

    /**
     * footer :
     * items : [{"dependency":null,"id":12,"title":"姓名","placeholder":"水印文字","itemType":4,"applicationId":75,"requestmethod":null,"image":null,"orders":1,"dicTYpe":null,"fieldname":"name"},{"dependency":null,"id":13,"subtitles":[{"val":6,"id":6,"level":null,"name":"室内报修","orders":1,"type":2},{"val":7,"id":7,"level":null,"name":"公共设施","orders":2,"type":2}],"title":"第二行","placeholder":null,"itemType":1,"applicationId":75,"requestmethod":null,"image":null,"orders":2,"dicTYpe":2,"fieldname":"second"},{"dependency":null,"id":14,"title":"第五行","placeholder":"水印文字","itemType":3,"applicationId":75,"requestmethod":null,"image":null,"orders":3,"dicTYpe":null,"fieldname":"fifth"},{"dependency":null,"id":15,"title":"选择时间","placeholder":null,"itemType":16,"applicationId":75,"requestmethod":null,"image":null,"orders":4,"dicTYpe":null,"fieldname":"time"},{"dependency":null,"id":16,"title":"签名","placeholder":null,"itemType":17,"applicationId":75,"requestmethod":null,"image":null,"orders":5,"dicTYpe":null,"fieldname":"myname"},{"dependency":null,"id":17,"title":"多项选择","placeholder":null,"itemType":2,"applicationId":75,"requestmethod":null,"image":null,"orders":6,"dicTYpe":null,"fieldname":"multiterm"},{"dependency":null,"id":18,"title":"拍照","placeholder":null,"itemType":14,"applicationId":75,"requestmethod":null,"image":null,"orders":7,"dicTYpe":null,"fieldname":"photo"},{"dependency":null,"id":19,"title":"小视频","placeholder":null,"itemType":18,"applicationId":75,"requestmethod":null,"image":null,"orders":8,"dicTYpe":null,"fieldname":"video"},{"dependency":null,"id":20,"title":"录音","placeholder":null,"itemType":19,"applicationId":75,"requestmethod":null,"image":null,"orders":9,"dicTYpe":null,"fieldname":"voice"},{"dependency":null,"id":21,"title":"提交","placeholder":"提交","itemType":15,"applicationId":75,"requestmethod":null,"image":null,"orders":10,"dicTYpe":null,"fieldname":null}]
     * header : 现场报修
     */

    private String footer;
    private String header;
    private String userId;
    /**
     * dependency : null
     * id : 12
     * title : 姓名
     * placeholder : 水印文字
     * itemType : 4
     * applicationId : 75
     * requestmethod : null
     * image : null
     * orders : 1
     * dicTYpe : null
     * fieldname : name
     */

    private List<ItemsBean> items;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        private List<DatasBean> datas;
        private Integer dependency;//依赖项
        private int id;//控件id
        private String title; //标题名
        private int isRequired;//是否必填项   0是 1否
        private String placeholder;//水印
        private int itemType;//控件类型
        private int applicationId;//哪个应用下的控件
        private Object requestmethod;//提交的接口
        private Object image;//图片
        private int orders;//排序
        private Object dicTYpe;
        private String fieldname;//提交的key
        private String hiddenKey;//隐藏控件隐藏属性
        private List<SubtitlesItem> subtitles;


        public String getHiddenKey() {
            return hiddenKey;
        }

        public void setHiddenKey(String hiddenKey) {
            this.hiddenKey = hiddenKey;
        }

        public int getIsRequired() {
            return isRequired;
        }

        public void setIsRequired(int isRequired) {
            this.isRequired = isRequired;
        }

        public Integer getDependency() {
            return dependency;
        }

        public void setDependency(Integer dependency) {
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

        public String getPlaceholder() {
            return placeholder;
        }

        public void setPlaceholder(String placeholder) {
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

        public Object getDicTYpe() {
            return dicTYpe;
        }

        public void setDicTYpe(Object dicTYpe) {
            this.dicTYpe = dicTYpe;
        }

        public String getFieldname() {
            return fieldname;
        }

        public void setFieldname(String fieldname) {
            this.fieldname = fieldname;
        }

        public List<SubtitlesItem> getSubtitles() {
            return subtitles;
        }

        public void setSubtitles(List<SubtitlesItem> subtitles) {
            this.subtitles = subtitles;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            private int dependency;
            private int id;
            private String title;
            private Object placeholder;
            private int itemType;
            private int applicationId;
            private Object requestmethod;
            private Object image;
            private int orders;
            private Object dicTYpe;
            private Object fieldname;

            public int getDependency() {
                return dependency;
            }

            public void setDependency(int dependency) {
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

            public Object getDicTYpe() {
                return dicTYpe;
            }

            public void setDicTYpe(Object dicTYpe) {
                this.dicTYpe = dicTYpe;
            }

            public Object getFieldname() {
                return fieldname;
            }

            public void setFieldname(Object fieldname) {
                this.fieldname = fieldname;
            }
        }
    }
}
