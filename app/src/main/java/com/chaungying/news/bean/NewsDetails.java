package com.chaungying.news.bean;

import java.util.List;

/**
 * 新闻详情子项实体
 *
 * @author 种耀淮 在2016年08月04日16:25创建
 */
public class NewsDetails {


    /**
     * datas : [{"id":0,"content":"公司团委参加瑞达集团\u201c凝聚瑞达，同心思创\u201d","activityId":0,"type":1},{"id":0,"content":"http://139.129.10.71:6060/propertyManager/uploadFile/activity/1471347327110.png","activityId":0,"type":2},{"id":0,"content":" 为了迎接青年节、弘扬五四精神，充分展示当代团员青年朝气蓬勃、奋发进取的精神风貌，2016年5月5日至6日，瑞达集团团委在雁西湖拓展基地和北京青年创业园，组织开展了为期两天的\u201c凝聚瑞达，同心思创\u201d主题青年活动。我公司团委共计14名团员青年代表参加了该活动。\u2028 \n主题活动紧紧围绕\u201c凝聚瑞达，同心思创\u201d展开，内容包括团队拓展、集体联欢、学习参观等。在活动中，广大团员青年全心投入，乐观积极，展现出青春飞扬的激情与风采，在探索和磨合中培养了信任和默契，充分体现出了青年人团结协作、勇挑重担的大无畏精神，深度融合，增强了团队建设和凝聚力。\u2028 \n在此次活动中，我公司团员青年积极踊跃，团结协作，奋勇争先，完成了13.5公里的徒步拓展活动，充分展示了我公司青年人的风采。青年团员纷纷表示在结合时代要求和事业发展背景下，要以青春的激情、昂扬的风貌，立足工作岗位，务新求实，不断学习，开拓进取，为瑞达集团和物业公司发展贡献自己的青春力量，弘扬\u2018五四\u2019精神，创瑞达青年风采。","activityId":0,"type":3}]
     * respCode : 1001
     * respMsg : 成功！
     */

    private int respCode;
    private String respMsg;
    /**
     * id : 0
     * content : 公司团委参加瑞达集团“凝聚瑞达，同心思创”
     * activityId : 0
     * type : 1
     */

    private List<DatasBean> datas;

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

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        private int id;
        private String content;
        private int activityId;
        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getActivityId() {
            return activityId;
        }

        public void setActivityId(int activityId) {
            this.activityId = activityId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
