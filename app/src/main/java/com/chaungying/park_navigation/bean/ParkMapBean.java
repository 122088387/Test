package com.chaungying.park_navigation.bean;

/**
 * @author 王晓赛 or 2016/8/16
 */
public class ParkMapBean {

    /**
     * id : 4
     * districtId : 1
     * name : 天安门
     * longitude : 116.394638
     * latitude : 39.914271
     * states : 0
     * createTime : 2016-08-15 17:26:26
     * type :  建筑，餐厅，超市的区分
     */

    private int id;
    private int districtId;
    private String name;
    private String longitude;
    private String latitude;
    private int states;
    private String createTime;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
