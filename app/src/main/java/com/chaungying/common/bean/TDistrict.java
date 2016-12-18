package com.chaungying.common.bean;

import com.google.gson.annotations.Expose;

import java.sql.Timestamp;

/**
 * TDistrict entity. @author REN
 */
public class TDistrict implements java.io.Serializable, Comparable<TDistrict> {

    // Fields

    private Integer val;
    private String name;
    private Integer staffId;
    private Timestamp buildDate;
    private Integer buildingCount;
    private Integer residentCount;
    private Integer facilityCount;
    private Integer coverArea;
    private Double roadArea;
    private Double buildingArea;
    private Double publicArea;
    private Double greenArea;
    private Double parkingArea;
    private Integer parkingCount;
    private String floorPic;
    private String gis;
    private String description;
    private String gisList;
    private String districtColor;
    private Integer createUserId;
    private Integer lastUpdateUserId;
    private Timestamp createTime;
    private Timestamp lastUpdateTime;
    private Integer organId;
    private String chargeEmployeeIds;
    private String chargeEmployeeNames;

    // 拼音 用于排序
    @Expose
    public String pinyin;

    // 拼音首字母
    public char firstChar;


    /**
     * 存储拼音和拼音首字母
     *
     * @param pinyin
     */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin.toLowerCase();
        String first = pinyin.substring(0, 1);
        if (first.matches("[A-Za-z]")) {
            firstChar = first.toUpperCase().charAt(0);
        } else {
            firstChar = '#';
        }
    }

    // Constructors

    /** default_png constructor */


    /**
     * full constructor
     */
    public TDistrict(String name, Integer staffId, Timestamp buildDate,
                     Integer buildingCount, Integer residentCount,
                     Integer facilityCount, Integer coverArea, Double roadArea,
                     Double buildingArea, Double publicArea, Double greenArea,
                     Double parkingArea, Integer parkingCount, String floorPic,
                     String gis, String description, String gisList,
                     String districtColor, Integer createUserId,
                     Integer lastUpdateUserId, Timestamp createTime,
                     Timestamp lastUpdateTime, Integer organId,
                     String chargeEmployeeIds, String chargeEmployeeNames) {
        this.name = name;
        this.staffId = staffId;
        this.buildDate = buildDate;
        this.buildingCount = buildingCount;
        this.residentCount = residentCount;
        this.facilityCount = facilityCount;
        this.coverArea = coverArea;
        this.roadArea = roadArea;
        this.buildingArea = buildingArea;
        this.publicArea = publicArea;
        this.greenArea = greenArea;
        this.parkingArea = parkingArea;
        this.parkingCount = parkingCount;
        this.floorPic = floorPic;
        this.gis = gis;
        this.description = description;
        this.gisList = gisList;
        this.districtColor = districtColor;
        this.createUserId = createUserId;
        this.lastUpdateUserId = lastUpdateUserId;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
        this.organId = organId;
        this.chargeEmployeeIds = chargeEmployeeIds;
        this.chargeEmployeeNames = chargeEmployeeNames;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Timestamp getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(Timestamp buildDate) {
        this.buildDate = buildDate;
    }

    public Integer getBuildingCount() {
        return buildingCount;
    }

    public void setBuildingCount(Integer buildingCount) {
        this.buildingCount = buildingCount;
    }

    public Integer getResidentCount() {
        return residentCount;
    }

    public void setResidentCount(Integer residentCount) {
        this.residentCount = residentCount;
    }

    public Integer getFacilityCount() {
        return facilityCount;
    }

    public void setFacilityCount(Integer facilityCount) {
        this.facilityCount = facilityCount;
    }

    public Integer getCoverArea() {
        return coverArea;
    }

    public void setCoverArea(Integer coverArea) {
        this.coverArea = coverArea;
    }

    public Double getRoadArea() {
        return roadArea;
    }

    public void setRoadArea(Double roadArea) {
        this.roadArea = roadArea;
    }

    public Double getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(Double buildingArea) {
        this.buildingArea = buildingArea;
    }

    public Double getPublicArea() {
        return publicArea;
    }

    public void setPublicArea(Double publicArea) {
        this.publicArea = publicArea;
    }

    public Double getGreenArea() {
        return greenArea;
    }

    public void setGreenArea(Double greenArea) {
        this.greenArea = greenArea;
    }

    public Double getParkingArea() {
        return parkingArea;
    }

    public void setParkingArea(Double parkingArea) {
        this.parkingArea = parkingArea;
    }

    public Integer getParkingCount() {
        return parkingCount;
    }

    public void setParkingCount(Integer parkingCount) {
        this.parkingCount = parkingCount;
    }

    public String getFloorPic() {
        return floorPic;
    }

    public void setFloorPic(String floorPic) {
        this.floorPic = floorPic;
    }

    public String getGis() {
        return gis;
    }

    public void setGis(String gis) {
        this.gis = gis;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGisList() {
        return gisList;
    }

    public void setGisList(String gisList) {
        this.gisList = gisList;
    }

    public String getDistrictColor() {
        return districtColor;
    }

    public void setDistrictColor(String districtColor) {
        this.districtColor = districtColor;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getLastUpdateUserId() {
        return lastUpdateUserId;
    }

    public void setLastUpdateUserId(Integer lastUpdateUserId) {
        this.lastUpdateUserId = lastUpdateUserId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getOrganId() {
        return organId;
    }

    public void setOrganId(Integer organId) {
        this.organId = organId;
    }

    public String getChargeEmployeeIds() {
        return chargeEmployeeIds;
    }

    public void setChargeEmployeeIds(String chargeEmployeeIds) {
        this.chargeEmployeeIds = chargeEmployeeIds;
    }

    public String getChargeEmployeeNames() {
        return chargeEmployeeNames;
    }

    public void setChargeEmployeeNames(String chargeEmployeeNames) {
        this.chargeEmployeeNames = chargeEmployeeNames;
    }

    @Override
    public int compareTo(TDistrict contact) {
        return this.firstChar - contact.firstChar;
    }
}