package com.chaungying.round_malls1.bean;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2016/6/12.
 */
public class PayInfo {
    @Expose
    public long timestamp;
    @Expose
    public String sign;
    @Expose
    public String partnerid;
    @Expose
    public String noncestr;
    @Expose
    public String prepayid;
    @Expose
    public String appid;
}
