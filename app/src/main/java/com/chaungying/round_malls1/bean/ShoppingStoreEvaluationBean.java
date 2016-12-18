package com.chaungying.round_malls1.bean;

import java.util.List;

/**
 * @author 王晓赛 or 2016/9/22
 */
public class ShoppingStoreEvaluationBean {


    /**
     * respCode : 1001
     * evaluates : [{"proId":"0569f991e9034c4b87e320e0695a9db5","createTime":"2016-08-30 17:59:13","evaStaus":"10","serverLevel":5,"evaluate_note":"dsfads","evaluateLevel":5,"additional_note":null,"anonymous_eva":"0","proName":"电子血压计","orderNo":"G20160830175810","proLevel":4,"sendLevel":4,"memberId":4617,"hasPic":"0","eva_additional":"0","explain":null,"evaluateId":15,"loginName":"18601049669"},{"proId":"0bd3804604c94727b6b4de4d17440e40","createTime":"2016-08-30 17:51:06","evaStaus":"10","serverLevel":5,"evaluate_note":"水电费大事发生的","evaluateLevel":5,"additional_note":null,"anonymous_eva":"0","proName":"测试用商品","orderNo":"K20160830174957","proLevel":4,"sendLevel":5,"memberId":4617,"hasPic":"0","eva_additional":"0","explain":null,"evaluateId":14,"loginName":"18601049669"},{"proId":"0bd3804604c94727b6b4de4d17440e40","createTime":"2016-08-30 17:29:31","evaStaus":"10","serverLevel":4,"evaluate_note":"测试第三个","evaluateLevel":5,"additional_note":null,"anonymous_eva":"0","proName":"测试用商品、披肩颈椎按摩器、全自动按摩洗脚盆","orderNo":"O20160830172810","proLevel":3,"sendLevel":5,"memberId":4617,"hasPic":"0","eva_additional":"0","explain":null,"evaluateId":13,"loginName":"18601049669"},{"proId":"37177f5c6ba34bf89059ba1f706b3f2a","createTime":"2016-08-30 17:20:33","evaStaus":"10","serverLevel":2,"evaluate_note":"第二个评价","evaluateLevel":5,"additional_note":null,"anonymous_eva":"0","proName":"披肩颈椎按摩器、电子血压计、全自动按摩洗脚盆","orderNo":"X20160830171826","proLevel":1,"sendLevel":3,"memberId":4615,"hasPic":"0","eva_additional":"0","explain":null,"evaluateId":9,"loginName":"17778251120"}]
     * respMsg : 成功！
     */

    private int respCode;
    private String respMsg;
    /**
     * proId : 0569f991e9034c4b87e320e0695a9db5
     * createTime : 2016-08-30 17:59:13
     * evaStaus : 10
     * serverLevel : 5
     * evaluate_note : dsfads
     * evaluateLevel : 5
     * additional_note : null
     * anonymous_eva : 0
     * proName : 电子血压计
     * orderNo : G20160830175810
     * proLevel : 4
     * sendLevel : 4
     * memberId : 4617
     * hasPic : 0
     * eva_additional : 0
     * explain : null
     * evaluateId : 15
     * loginName : 18601049669
     */

    private List<EvaluatesBean> evaluates;

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

    public List<EvaluatesBean> getEvaluates() {
        return evaluates;
    }

    public void setEvaluates(List<EvaluatesBean> evaluates) {
        this.evaluates = evaluates;
    }

    public static class EvaluatesBean {
        private String proId;
        private String createTime;
        private String evaStaus;
        private int serverLevel;
        private String evaluate_note;
        private int evaluateLevel;
        private Object additional_note;
        private String anonymous_eva;
        private String proName;
        private String orderNo;
        private int proLevel;
        private int sendLevel;
        private int memberId;
        private String hasPic;
        private String eva_additional;
        private Object explain;
        private int evaluateId;
        private String loginName;

        public String getProId() {
            return proId;
        }

        public void setProId(String proId) {
            this.proId = proId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getEvaStaus() {
            return evaStaus;
        }

        public void setEvaStaus(String evaStaus) {
            this.evaStaus = evaStaus;
        }

        public int getServerLevel() {
            return serverLevel;
        }

        public void setServerLevel(int serverLevel) {
            this.serverLevel = serverLevel;
        }

        public String getEvaluate_note() {
            return evaluate_note;
        }

        public void setEvaluate_note(String evaluate_note) {
            this.evaluate_note = evaluate_note;
        }

        public int getEvaluateLevel() {
            return evaluateLevel;
        }

        public void setEvaluateLevel(int evaluateLevel) {
            this.evaluateLevel = evaluateLevel;
        }

        public Object getAdditional_note() {
            return additional_note;
        }

        public void setAdditional_note(Object additional_note) {
            this.additional_note = additional_note;
        }

        public String getAnonymous_eva() {
            return anonymous_eva;
        }

        public void setAnonymous_eva(String anonymous_eva) {
            this.anonymous_eva = anonymous_eva;
        }

        public String getProName() {
            return proName;
        }

        public void setProName(String proName) {
            this.proName = proName;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public int getProLevel() {
            return proLevel;
        }

        public void setProLevel(int proLevel) {
            this.proLevel = proLevel;
        }

        public int getSendLevel() {
            return sendLevel;
        }

        public void setSendLevel(int sendLevel) {
            this.sendLevel = sendLevel;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getHasPic() {
            return hasPic;
        }

        public void setHasPic(String hasPic) {
            this.hasPic = hasPic;
        }

        public String getEva_additional() {
            return eva_additional;
        }

        public void setEva_additional(String eva_additional) {
            this.eva_additional = eva_additional;
        }

        public Object getExplain() {
            return explain;
        }

        public void setExplain(Object explain) {
            this.explain = explain;
        }

        public int getEvaluateId() {
            return evaluateId;
        }

        public void setEvaluateId(int evaluateId) {
            this.evaluateId = evaluateId;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }
    }
}
