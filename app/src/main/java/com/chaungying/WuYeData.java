package com.chaungying;

/**
 * 全局数据
 *
 * @author 种耀淮
 */
public class WuYeData {

    private static WuYeData _WuYeDate;

    public static WuYeData Initialize() {
        if (_WuYeDate == null) {
            _WuYeDate = new WuYeData();
        }
        return _WuYeDate;
    }


    /**
     * 用户id
     */
    private int userId;

//    /**
//     * 好友
//     */
//    private ArrayList<Contact> contacts;
//
//    /**
//     * 定位城市名称
//     */
//    private String locationCity;
//    /**
//     * 是否显示大图的二维码 0没显示1显示
//     */
//    private int isShow = 0;
//
//    public int getIsShow() {
//        return isShow;
//    }
//
//    public void setIsShow(int isShow) {
//        this.isShow = isShow;
//    }
//
//    /**
//     * 酒店筛选
//     */
//    private Screen hotelScreen;
//
//    private String hotelDateSelectIn;
//    private String hotelDateSelectOut;
//    private int hotelDateSelectCount;
//
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
//
//    public ArrayList<Contact> getContacts() {
//        return contacts;
//    }
//
//    public void setContacts(ArrayList<Contact> contacts) {
//        this.contacts = contacts;
//    }
//
//    public Screen getHotelScreen() {
//        return hotelScreen;
//    }
//
//    public void setHotelScreen(Screen hotelScreen) {
//        this.hotelScreen = hotelScreen;
//    }
//
//    public String getHotelDateSelectIn() {
//        return hotelDateSelectIn;
//    }
//
//    public void setHotelDateSelectIn(String hotelDateSelectIn) {
//        this.hotelDateSelectIn = hotelDateSelectIn;
//    }
//
//    public String getHotelDateSelectOut() {
//        return hotelDateSelectOut;
//    }
//
//    public void setHotelDateSelectOut(String hotelDateSelectOut) {
//        this.hotelDateSelectOut = hotelDateSelectOut;
//    }
//
//    public int getHotelDateSelectCount() {
//        return hotelDateSelectCount;
//    }
//
//    public void setHotelDateSelectCount(int hotelDateSelectCount) {
//        this.hotelDateSelectCount = hotelDateSelectCount;
//    }
}
