package com.chaungying.modues.main.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * 应用中的实体
 *
 * @anthor 王晓赛 or 2016/6/22
 */
public class RoleAppsBean implements Serializable{
    /**
     * isEnable : 0
     * icoUrl : http://139.129.10.71:9000/propertyManager/uploadFile/appMenusIcos/1466558188588@2x.png
     * applicationId : 70
     * code : APP_PTCOLLECT
     * type : 1
     * url :
     * id : 70
     * pId : 0
     * description : null
     * name : 点位采集
     * icoCode :
     * orders : 1
     * roleId : 4
     */
    @Expose
    private int isEnable;
    @Expose
    private String icoUrl;
    @Expose
    private int applicationId;
    @Expose
    private String code;
    @Expose
    private int type;
    @Expose
    private String url;
    @Expose
    private int id;
    @Expose
    private int pId;
    @Expose
    private Object description;
    @Expose
    private String name;
    @Expose
    private String icoCode;
    @Expose
    private int orders;
    @Expose
    private int roleId;

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
    }

    public String getIcoUrl() {
        return icoUrl;
    }

    public void setIcoUrl(String icoUrl) {
        this.icoUrl = icoUrl;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPId() {
        return pId;
    }

    public void setPId(int pId) {
        this.pId = pId;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcoCode() {
        return icoCode;
    }

    public void setIcoCode(String icoCode) {
        this.icoCode = icoCode;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
