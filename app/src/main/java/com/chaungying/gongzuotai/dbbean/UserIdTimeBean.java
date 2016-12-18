package com.chaungying.gongzuotai.dbbean;

import org.litepal.crud.DataSupport;

/**
 * @author 王晓赛 or 2016/8/15
 */
public class UserIdTimeBean extends DataSupport {

    private int id;
    private String userID;
    private String dateTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
