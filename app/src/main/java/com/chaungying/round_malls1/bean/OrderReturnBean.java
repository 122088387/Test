package com.chaungying.round_malls1.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 创建者：  王晓赛
 * 时  间： 2016/9/30
 */

public class OrderReturnBean {

    /**
     * respCode : 1001
     * respMsg : {"timestamp":1475225128,"sign":"EAE6F472D11F6F881550CA5602876451","partnerid":"1354094802","noncestr":"CiphkRDFPHHdmivN","prepayid":"wx201609301645153d317507530276027136","package":"Sign=WXPay","appid":"wxd6c843644439cf96"}
     */

    private int respCode;
    /**
     * timestamp : 1475225128
     * sign : EAE6F472D11F6F881550CA5602876451
     * partnerid : 1354094802
     * noncestr : CiphkRDFPHHdmivN
     * prepayid : wx201609301645153d317507530276027136
     * package : Sign=WXPay
     * appid : wxd6c843644439cf96
     */

    private RespMsgBean respMsg;

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public RespMsgBean getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(RespMsgBean respMsg) {
        this.respMsg = respMsg;
    }

    public static class RespMsgBean {
        private int timestamp;
        private String sign;
        private String partnerid;
        private String noncestr;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String appid;

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }
    }
}
