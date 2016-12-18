package com.chaungying.round_malls1.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者：  王晓赛
 * 时  间： 2016/10/11
 */

public class ShoppingAddressBean {


    /**
     * datas : [{"receiverEnable":"0","sex":1,"receiverPro":"110000","status":"10","creatDate":1476062985,"receiverCounty":"0","receiverZipcode":null,"receiverAddress":"在di","doorNumber":null,"rsrvStr2":null,"rsrvStr3":null,"receiverCity":"110100","rsrvStr1":null,"receiverId":"02ccfd1a56624a71a8ebcf1a3b4a753b","receiverName":"我的","memberId":0,"receiverPhone":"15303239901"},{"receiverEnable":"0","sex":1,"receiverPro":"130000","status":"10","creatDate":1469695928,"receiverCounty":"130108","receiverZipcode":null,"receiverAddress":"金源广场a座606","doorNumber":null,"rsrvStr2":null,"rsrvStr3":null,"receiverCity":"130100","rsrvStr1":null,"receiverId":"12087ac83550402b93c675559df24385","receiverName":"张盼祥","memberId":0,"receiverPhone":"15303239901"},{"receiverEnable":"1","sex":1,"receiverPro":"320000","status":"10","creatDate":1476235131,"receiverCounty":"320101","receiverZipcode":null,"receiverAddress":"1255","doorNumber":null,"rsrvStr2":null,"rsrvStr3":null,"receiverCity":"320100","rsrvStr1":null,"receiverId":"1b1aab07d1434540940dbb43f7df026b","receiverName":"123","memberId":0,"receiverPhone":"17778251120"},{"receiverEnable":"1","sex":1,"receiverPro":"110000","status":"10","creatDate":1469672122,"receiverCounty":"110101","receiverZipcode":null,"receiverAddress":"weqeqeqw","doorNumber":null,"rsrvStr2":null,"rsrvStr3":null,"receiverCity":"110100","rsrvStr1":null,"receiverId":"2037b9de8bd34ad6b123cc91f86acbf0","receiverName":"测试","memberId":0,"receiverPhone":"15226514965"},{"receiverEnable":"1","sex":1,"receiverPro":"130000","status":"10","creatDate":1469699179,"receiverCounty":"130108","receiverZipcode":null,"receiverAddress":"金源商务广场a座606","doorNumber":null,"rsrvStr2":null,"rsrvStr3":null,"receiverCity":"0","rsrvStr1":null,"receiverId":"3c63c54922d649ceb0d23fc684035630","receiverName":"张盼祥","memberId":0,"receiverPhone":"15303239901"},{"receiverEnable":"1","sex":1,"receiverPro":"110000","status":"10","creatDate":1476062996,"receiverCounty":"0","receiverZipcode":null,"receiverAddress":"在di","doorNumber":null,"rsrvStr2":null,"rsrvStr3":null,"receiverCity":"110100","rsrvStr1":null,"receiverId":"689c94d95ff94c129db6ce7e454d463c","receiverName":"我的","memberId":0,"receiverPhone":"15303239901"},{"receiverEnable":"0","sex":1,"receiverPro":"110000","status":"10","creatDate":1469771631,"receiverCounty":"110108","receiverZipcode":null,"receiverAddress":"德胜商务中心","doorNumber":null,"rsrvStr2":null,"rsrvStr3":null,"receiverCity":"110100","rsrvStr1":null,"receiverId":"a7e53f91643d4f1aa9de0ed528fd86cb","receiverName":"任小雷","memberId":0,"receiverPhone":"18601049669"},{"receiverEnable":"0","sex":1,"receiverPro":"110000","status":"10","creatDate":1470118321,"receiverCounty":"110108","receiverZipcode":null,"receiverAddress":"德胜商务大厦","doorNumber":null,"rsrvStr2":null,"rsrvStr3":null,"receiverCity":"110100","rsrvStr1":null,"receiverId":"d948c10852274c9facdb423d24931e96","receiverName":"任小雷","memberId":0,"receiverPhone":"18601049669"},{"receiverEnable":"0","sex":1,"receiverPro":"110000","status":"10","creatDate":1476235150,"receiverCounty":"110101","receiverZipcode":null,"receiverAddress":"1111","doorNumber":null,"rsrvStr2":null,"rsrvStr3":null,"receiverCity":"110100","rsrvStr1":null,"receiverId":"e8a21702f3354837bcc9b9f2558e6756","receiverName":"222","memberId":0,"receiverPhone":"15803111812"},{"receiverEnable":"0","sex":1,"receiverPro":"110000","status":"10","creatDate":1470891813,"receiverCounty":"110101","receiverZipcode":null,"receiverAddress":"阿斯蒂芬大事发生的","doorNumber":null,"rsrvStr2":null,"rsrvStr3":null,"receiverCity":"110100","rsrvStr1":null,"receiverId":"f1cb57d52a6b43f9982109b1ee6fe283","receiverName":"ccc","memberId":0,"receiverPhone":"15226514965"},{"receiverEnable":"1","sex":1,"receiverPro":"110000","status":"10","creatDate":1476063175,"receiverCounty":"110101","receiverZipcode":null,"receiverAddress":"我们都","doorNumber":null,"rsrvStr2":null,"rsrvStr3":null,"receiverCity":"110100","rsrvStr1":null,"receiverId":"fd193c51e13e4af59c2cf5402051dafb","receiverName":"我的","memberId":0,"receiverPhone":"15303239901"}]
     * respCode : 1001
     * respMsg : 成功！
     */

    private int respCode;
    private String respMsg;
    /**
     * receiverEnable : 0   receiverEnable 收货人默认状态  0-默认 1-非默认； s
     * sex : 1   ex  性别 1男 2 女
     * receiverPro : 110000
     * status : 10
     * creatDate : 1476062985
     * receiverCounty : 0
     * receiverZipcode : null
     * receiverAddress : 在di
     * doorNumber : null
     * rsrvStr2 : null
     * rsrvStr3 : null
     * receiverCity : 110100
     * rsrvStr1 : null
     * receiverId : 02ccfd1a56624a71a8ebcf1a3b4a753b
     * receiverName : 我的
     * memberId : 0
     * receiverPhone : 15303239901
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

    public static class DatasBean implements Comparable<DatasBean> ,Serializable{
        private String receiverEnable;
        private int sex;
        private String receiverPro;
        private String status;
        private int creatDate;
        private String receiverCounty;
        private Object receiverZipcode;
        private String receiverAddress;
        private Object doorNumber;
        private Object rsrvStr2;
        private Object rsrvStr3;
        private String receiverCity;
        private Object rsrvStr1;
        private String receiverId;
        private String receiverName;
        private int memberId;
        private String receiverPhone;

        public String getReceiverEnable() {
            return receiverEnable;
        }

        public void setReceiverEnable(String receiverEnable) {
            this.receiverEnable = receiverEnable;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getReceiverPro() {
            return receiverPro;
        }

        public void setReceiverPro(String receiverPro) {
            this.receiverPro = receiverPro;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getCreatDate() {
            return creatDate;
        }

        public void setCreatDate(int creatDate) {
            this.creatDate = creatDate;
        }

        public String getReceiverCounty() {
            return receiverCounty;
        }

        public void setReceiverCounty(String receiverCounty) {
            this.receiverCounty = receiverCounty;
        }

        public Object getReceiverZipcode() {
            return receiverZipcode;
        }

        public void setReceiverZipcode(Object receiverZipcode) {
            this.receiverZipcode = receiverZipcode;
        }

        public String getReceiverAddress() {
            return receiverAddress;
        }

        public void setReceiverAddress(String receiverAddress) {
            this.receiverAddress = receiverAddress;
        }

        public Object getDoorNumber() {
            return doorNumber;
        }

        public void setDoorNumber(Object doorNumber) {
            this.doorNumber = doorNumber;
        }

        public Object getRsrvStr2() {
            return rsrvStr2;
        }

        public void setRsrvStr2(Object rsrvStr2) {
            this.rsrvStr2 = rsrvStr2;
        }

        public Object getRsrvStr3() {
            return rsrvStr3;
        }

        public void setRsrvStr3(Object rsrvStr3) {
            this.rsrvStr3 = rsrvStr3;
        }

        public String getReceiverCity() {
            return receiverCity;
        }

        public void setReceiverCity(String receiverCity) {
            this.receiverCity = receiverCity;
        }

        public Object getRsrvStr1() {
            return rsrvStr1;
        }

        public void setRsrvStr1(Object rsrvStr1) {
            this.rsrvStr1 = rsrvStr1;
        }

        public String getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(String receiverId) {
            this.receiverId = receiverId;
        }

        public String getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getReceiverPhone() {
            return receiverPhone;
        }

        public void setReceiverPhone(String receiverPhone) {
            this.receiverPhone = receiverPhone;
        }

        @Override
        public int compareTo(DatasBean datasBean) {
            int a = Integer.parseInt(this.receiverEnable);
            int b = Integer.parseInt(datasBean.getReceiverEnable());
            return a - b;
        }
    }
}
