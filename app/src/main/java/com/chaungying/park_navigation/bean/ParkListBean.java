package com.chaungying.park_navigation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王晓赛 or 2016/8/16
 */
public class ParkListBean {

    /**
     * total : 3
     * data : [{"districtId":1,"name":"晴雪园","staffId":0,"buildDate":null,"buildingCount":0,"residentCount":0,"facilityCount":0,"coverArea":100,"roadArea":null,"buildingArea":null,"publicArea":null,"greenArea":null,"parkingArea":null,"parkingCount":33,"floorPic":null,"gis":null,"description":null,"gisList":null,"districtColor":null,"createUserId":0,"lastUpdateUserId":72297,"createTime":null,"lastUpdateTime":null,"organId":0,"chargeEmployeeIds":null,"chargeEmployeeNames":null}]
     */

    private int total;
    /**
     * districtId : 1
     * name : 晴雪园
     * staffId : 0
     * buildDate : null
     * buildingCount : 0
     * residentCount : 0
     * facilityCount : 0
     * coverArea : 100
     * roadArea : null
     * buildingArea : null
     * publicArea : null
     * greenArea : null
     * parkingArea : null
     * parkingCount : 33
     * floorPic : null
     * gis : null
     * description : null
     * gisList : null
     * districtColor : null
     * createUserId : 0
     * lastUpdateUserId : 72297
     * createTime : null
     * lastUpdateTime : null
     * organId : 0
     * chargeEmployeeIds : null
     * chargeEmployeeNames : null
     */

    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private int districtId;
        private String name;
        private int staffId;
        private Object buildDate;
        private int buildingCount;
        private int residentCount;
        private int facilityCount;
        private int coverArea;
        private Object roadArea;
        private Object buildingArea;
        private Object publicArea;
        private Object greenArea;
        private Object parkingArea;
        private int parkingCount;
        private Object floorPic;
        private Object gis;
        private Object description;
        private Object gisList;
        private Object districtColor;
        private int createUserId;
        private int lastUpdateUserId;
        private Object createTime;
        private Object lastUpdateTime;
        private int organId;
        private Object chargeEmployeeIds;
        private Object chargeEmployeeNames;

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

        public int getStaffId() {
            return staffId;
        }

        public void setStaffId(int staffId) {
            this.staffId = staffId;
        }

        public Object getBuildDate() {
            return buildDate;
        }

        public void setBuildDate(Object buildDate) {
            this.buildDate = buildDate;
        }

        public int getBuildingCount() {
            return buildingCount;
        }

        public void setBuildingCount(int buildingCount) {
            this.buildingCount = buildingCount;
        }

        public int getResidentCount() {
            return residentCount;
        }

        public void setResidentCount(int residentCount) {
            this.residentCount = residentCount;
        }

        public int getFacilityCount() {
            return facilityCount;
        }

        public void setFacilityCount(int facilityCount) {
            this.facilityCount = facilityCount;
        }

        public int getCoverArea() {
            return coverArea;
        }

        public void setCoverArea(int coverArea) {
            this.coverArea = coverArea;
        }

        public Object getRoadArea() {
            return roadArea;
        }

        public void setRoadArea(Object roadArea) {
            this.roadArea = roadArea;
        }

        public Object getBuildingArea() {
            return buildingArea;
        }

        public void setBuildingArea(Object buildingArea) {
            this.buildingArea = buildingArea;
        }

        public Object getPublicArea() {
            return publicArea;
        }

        public void setPublicArea(Object publicArea) {
            this.publicArea = publicArea;
        }

        public Object getGreenArea() {
            return greenArea;
        }

        public void setGreenArea(Object greenArea) {
            this.greenArea = greenArea;
        }

        public Object getParkingArea() {
            return parkingArea;
        }

        public void setParkingArea(Object parkingArea) {
            this.parkingArea = parkingArea;
        }

        public int getParkingCount() {
            return parkingCount;
        }

        public void setParkingCount(int parkingCount) {
            this.parkingCount = parkingCount;
        }

        public Object getFloorPic() {
            return floorPic;
        }

        public void setFloorPic(Object floorPic) {
            this.floorPic = floorPic;
        }

        public Object getGis() {
            return gis;
        }

        public void setGis(Object gis) {
            this.gis = gis;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public Object getGisList() {
            return gisList;
        }

        public void setGisList(Object gisList) {
            this.gisList = gisList;
        }

        public Object getDistrictColor() {
            return districtColor;
        }

        public void setDistrictColor(Object districtColor) {
            this.districtColor = districtColor;
        }

        public int getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(int createUserId) {
            this.createUserId = createUserId;
        }

        public int getLastUpdateUserId() {
            return lastUpdateUserId;
        }

        public void setLastUpdateUserId(int lastUpdateUserId) {
            this.lastUpdateUserId = lastUpdateUserId;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(Object lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public int getOrganId() {
            return organId;
        }

        public void setOrganId(int organId) {
            this.organId = organId;
        }

        public Object getChargeEmployeeIds() {
            return chargeEmployeeIds;
        }

        public void setChargeEmployeeIds(Object chargeEmployeeIds) {
            this.chargeEmployeeIds = chargeEmployeeIds;
        }

        public Object getChargeEmployeeNames() {
            return chargeEmployeeNames;
        }

        public void setChargeEmployeeNames(Object chargeEmployeeNames) {
            this.chargeEmployeeNames = chargeEmployeeNames;
        }
    }
}
