package com.chaungying.gongzuotai.dbbean;

import org.litepal.crud.DataSupport;

/**
 * @author 王晓赛 or 2016/8/15
 *
 * 创建数据库表的实体（报修的实体）
 */
public class RepairBean extends DataSupport {

    /**
     * layoutid: 布局的样式
     * applicationId：请求的appId
     * itemtype : 58
     * id : 106
     * title : 订餐人
     * value : 工资
     * orders : 1
     * type：2
     * isRead: 是否已读未读
     */


    private int layoutid;
    private int applicationId;
    private int itemtype;
    private int id;
    private String title;
    private String title1;
    private String title2;
    private String title3;
    private String value;
    private int orders;
    private int type;
    private boolean isRead;
    private int logicId;
    private int userId;
    private int dataType;
    private String countersignTime;

    public String getCountersignTime() {
        return countersignTime;
    }

    public void setCountersignTime(String countersignTime) {
        this.countersignTime = countersignTime;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getLogicId() {
        return logicId;
    }

    public void setLogicId(int logicId) {
        this.logicId = logicId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getTitle3() {
        return title3;
    }

    public void setTitle3(String title3) {
        this.title3 = title3;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
