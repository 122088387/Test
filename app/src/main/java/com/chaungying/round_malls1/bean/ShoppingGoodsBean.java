package com.chaungying.round_malls1.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王晓赛
 */
public class ShoppingGoodsBean implements Serializable {


    /**
     * respCode : 1001
     * productClasses :
     * respMsg : ???
     * products : [{"proId":"0569f991e9034c4b87e320e0695a9db5","price":"199.00","proName":"?????","classId":"9df19712b9904974a344c1b51308a21d","proDetails":null,"sellerSum":0,"totalEval":2,"proLogo":"http://www.cooptec.cn:80\\uploadFiles\\product\\proLogo\\1469754583741.png","evalGoodPercent":"0.00%"},{"proId":"0bd3804604c94727b6b4de4d17440e40","price":"99.00","proName":"?????","classId":"9df19712b9904974a344c1b51308a21d","proDetails":null,"sellerSum":0,"totalEval":2,"proLogo":"http://www.cooptec.cn:80\\uploadFiles\\product\\proLogo\\1469756534492.jpg","evalGoodPercent":"0.00%"},{"proId":"3352a5102eb842bdb996902afac8a5aa","price":"599.00","proName":"????????","classId":"9df19712b9904974a344c1b51308a21d","proDetails":null,"sellerSum":0,"totalEval":4,"proLogo":"http://www.cooptec.cn:80\\uploadFiles\\product\\proLogo\\1469757076953.jpg","evalGoodPercent":"25.00%"},{"proId":"37177f5c6ba34bf89059ba1f706b3f2a","price":"279.00","proName":"???????","classId":"9df19712b9904974a344c1b51308a21d","proDetails":null,"sellerSum":0,"totalEval":2,"proLogo":"http://www.cooptec.cn:80\\uploadFiles\\product\\proLogo\\1469756677492.jpg","evalGoodPercent":"0.00%"}]
     */

    private int respCode;
    private String respMsg;
    /**
     * classId : 9df19712b9904974a344c1b51308a21d
     * classUrl :
     * className : ??
     */

    private List<ProductClassesBean> productClasses;
    /**
     * proId : 0569f991e9034c4b87e320e0695a9db5
     * price : 199.00
     * proName : ?????
     * classId : 9df19712b9904974a344c1b51308a21d
     * proDetails : null
     * sellerSum : 0
     * totalEval : 2
     * proLogo :
     * evalGoodPercent : 0.00%
     */

    private List<ProductsBean> products;

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

    public List<ProductClassesBean> getProductClasses() {
        return productClasses;
    }

    public void setProductClasses(List<ProductClassesBean> productClasses) {
        this.productClasses = productClasses;
    }

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public static class ProductClassesBean implements Serializable {
        private String classId;
        private String classUrl;
        private String className;
        private boolean isColor;

        public boolean isColor() {
            return isColor;
        }

        public void setColor(boolean color) {
            isColor = color;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getClassUrl() {
            return classUrl;
        }

        public void setClassUrl(String classUrl) {
            this.classUrl = classUrl;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }
    }

    public static class ProductsBean implements Serializable {
        private String proId;
        private String price;
        private String proName;
        private String classId;
        private Object proDetails;
        private int sellerSum;
        private int totalEval;
        private String proLogo;
        private String evalGoodPercent;

        private int ShoppingNum;//点击之后该商品被选择的数量

        private boolean isCheck = true;//CheckBox在购物车列表页是否被选中

        private boolean isEditor;//购物车中是否处于编辑状态下

        public boolean isEditor() {
            return isEditor;
        }

        public void setEditor(boolean editor) {
            isEditor = editor;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public int getShoppingNum() {
            return ShoppingNum;
        }

        public void setShoppingNum(int shoppingNum) {
            ShoppingNum = shoppingNum;
        }


        public String getProId() {
            return proId;
        }

        public void setProId(String proId) {
            this.proId = proId;
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

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public Object getProDetails() {
            return proDetails;
        }

        public void setProDetails(Object proDetails) {
            this.proDetails = proDetails;
        }

        public int getSellerSum() {
            return sellerSum;
        }

        public void setSellerSum(int sellerSum) {
            this.sellerSum = sellerSum;
        }

        public int getTotalEval() {
            return totalEval;
        }

        public void setTotalEval(int totalEval) {
            this.totalEval = totalEval;
        }

        public String getProLogo() {
            return proLogo;
        }

        public void setProLogo(String proLogo) {
            this.proLogo = proLogo;
        }

        public String getEvalGoodPercent() {
            return evalGoodPercent;
        }

        public void setEvalGoodPercent(String evalGoodPercent) {
            this.evalGoodPercent = evalGoodPercent;
        }
    }
}
