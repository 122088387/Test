package com.chaungying.ji_xiao.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * 工作绩效单个实体
 */
public class JobPerCon implements Serializable {
    @Expose
    public String name;
    @Expose
    public String percent;
    @Expose
    public int total;
    @Expose
    public int finish;
}
