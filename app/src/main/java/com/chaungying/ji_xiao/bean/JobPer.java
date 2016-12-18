package com.chaungying.ji_xiao.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 工作绩效返回实体
 */
public class JobPer implements Serializable {
    @Expose
    public int respCode;
    @Expose
    public ArrayList<JobPerCon> data;
    @Expose
    public String respMsg;
//    ：totalSum   合计总数，finishSum  合计完成数，percentSum合计完成比例

    @Expose
    public int totalSum;
    @Expose
    public String percentSum;
    @Expose
    public int finishSum;

}
