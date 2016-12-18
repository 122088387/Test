package com.chaungying.common.bean;

import java.io.Serializable;

/**
 * @author 王晓赛 or 2016/6/24
 */
public class SubtitlesItem implements Serializable{


    /**
     * val : 6
     * id : 6
     * level : null
     * name : 室内报修
     * orders : 1
     * type : 2
     */

    private int val;
    private int id;
    private Object level;
    private String name;
    private int orders;
    private int type;

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getLevel() {
        return level;
    }

    public void setLevel(Object level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
