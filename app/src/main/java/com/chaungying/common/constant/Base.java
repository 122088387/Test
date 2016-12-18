package com.chaungying.common.constant;

import com.google.gson.annotations.Expose;

/**
 * Base实体类
 * 
 * @author 侯博鹏2016.3.21
 *
 */
public class Base {
	/**
	 * 返回码 0为成功
	 */
	@Expose
	public int  respCode;
	/**
	 * 返回信息
	 */
	@Expose
	public String respMsg;
	/**
	 * 数据
	 */
	@Expose
	public Integer logicId;

}
