package com.chaungying.round_malls.bean;

/**
 * @author 王晓赛 or 2016/9/22
 */
public class RoundMallsBean {

    private String name;
    private double price;
    //商品的数量
    private int shoppingNum;


    public int getShoppingNum() {
        return shoppingNum;
    }

    public void setShoppingNum(int shoppingNum) {
        this.shoppingNum = shoppingNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
