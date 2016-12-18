package com.chaungying.gongzuotai.dbbean;

import org.litepal.crud.DataSupport;

/**
 * @author 王晓赛 or 2016/8/15
 *
 * 创建数据库表的实体(订餐)
 */
public class OrderMealBean extends DataSupport {

    /**
     * layoutid: 布局的样式
     * applicationId：请求的appId
     * itemtype : 58
     * logicId : 106
     * title : 订餐人
     * value : 工资
     * orders : 1
     * type：1
     * isRead：是否已读未读
     * userId:哪个用户
     */

    private int layoutid;
    private int applicationId;
    private int itemtype;
    private int logicId;
    private String title;
    private String title1;
    private String title2;
    private String title3;
    private String value;
    private int orders;
    private int type;
    private boolean isRead;
    private int userId;

    public String getTitle3() {
        return title3;
    }

    public void setTitle3(String title3) {
        this.title3 = title3;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLayoutid() {
        return layoutid;
    }


    public void setLayoutid(int layoutid) {
        this.layoutid = layoutid;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getItemtype() {
        return itemtype;
    }

    public void setItemtype(int itemtype) {
        this.itemtype = itemtype;
    }

    public int getLogicId() {
        return logicId;
    }

    public void setLogicId(int logicId) {
        this.logicId = logicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
