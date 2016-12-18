package com.chaungying.round_malls1.bean;

import java.util.List;

/**
 * 创建者：  王晓赛
 * 时  间： 2016/10/14
 */

public class OrderListDetailsBean {

    /**
     * datas : [{"proId":"0bd3804604c94727b6b4de4d17440e40","buyCount":1,"actualPrice":"99.23","price":"99.23","proName":"测试用商品","detailId":"02247e2829ed476d8987713ea07e7d15"},{"proId":"37177f5c6ba34bf89059ba1f706b3f2a","buyCount":1,"actualPrice":"279.00","price":"279.00","proName":"披肩颈椎按摩器","detailId":"04e9f54f02db4b43a8fc999cff5e4c4d"},{"proId":"0569f991e9034c4b87e320e0695a9db5","buyCount":1,"actualPrice":"199.00","price":"199.00","proName":"电子血压计","detailId":"41b65c1bfdf2447c88dd2a01c01881d4"},{"proId":"3352a5102eb842bdb996902afac8a5aa","buyCount":1,"actualPrice":"599.00","price":"599.00","proName":"全自动按摩洗脚盆","detailId":"6b3a57dc612743a8bc7247ffa71ae929"}]
     * respCode : 1001
     * respMsg : 成功！
     */

    private int respCode;
    private String respMsg;
    /**
     * proId : 0bd3804604c94727b6b4de4d17440e40
     * buyCount : 1
     * actualPrice : 99.23
     * price : 99.23
     * proName : 测试用商品
     * detailId : 02247e2829ed476d8987713ea07e7d15
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
        private String proId;
        private int buyCount;
        private String actualPrice;
        private String price;
        private String proName;
        private String detailId;

        public String getProId() {
            return proId;
        }

        public void setProId(String proId) {
            this.proId = proId;
        }

        public int getBuyCount() {
            return buyCount;
        }

        public void setBuyCount(int buyCount) {
            this.buyCount = buyCount;
        }

        public String getActualPrice() {
            return actualPrice;
        }

        public void setActualPrice(String actualPrice) {
            this.actualPrice = actualPrice;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProName() {
            return proName;
        }

        public void setProName(String proName) {
            this.proName = proName;
        }

        public String getDetailId() {
            return detailId;
        }

        public void setDetailId(String detailId) {
            this.detailId = detailId;
        }
    }
}
