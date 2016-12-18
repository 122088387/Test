package com.chaungying.round_malls1.bean;

/**
 * 创建者：  王晓赛
 * 时  间： 2016/9/30
 *
 * 购物车中要提交的商品bean
 */

public class OrderBean {
    private String goodsId;
    private int buyCount;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }
}
