package com.chaungying.ji_xiao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王晓赛 or 2016/9/20
 */
public class PerCarBean implements Serializable{

    /**
     * respCode : 1001
     * data : [{"NAME":"冀A00000/别克君越","carSum":2,"totalMoney":"400.00"},{"NAME":"冀A00000/大众帕萨特","carSum":1,"totalMoney":"200.00"},{"NAME":"冀A00000/雪弗兰迈锐宝","carSum":0,"totalMoney":"0.00"}]
     * respMsg : 成功！
     */

    private int respCode;
    private String respMsg;
    /**
     * NAME : 冀A00000/别克君越
     * carSum : 2
     * totalMoney : 400.00
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
        private String NAME;
        private int carSum;
        private String totalMoney;

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public int getCarSum() {
            return carSum;
        }

        public void setCarSum(int carSum) {
            this.carSum = carSum;
        }

        public String getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(String totalMoney) {
            this.totalMoney = totalMoney;
        }
    }
}
