package com.chaungying.round_malls1.bean;

import java.io.Serializable;

/**
 * 创建者：  王晓赛
 * 时  间： 2016/10/10
 */

public class PayMoneyGoodsBean implements Serializable{

    private String name;

    private int num;

    private String price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
