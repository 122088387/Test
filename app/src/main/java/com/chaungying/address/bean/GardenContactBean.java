package com.chaungying.address.bean;

import java.util.List;

/**
 * @author 王晓赛 or 2016/8/26
 */
public class GardenContactBean {

    /**
     * isShowMembersParam : 0
     * respCode : 1001
     * isShowMembers : 1
     * isNextUrlParam : 1
     * data : [{"val":1,"name":"未来科技城"},{"val":2,"name":"世纪新景"}]
     * respMsg : 成功！
     * paramKey : districtId
     * requestUrl : contacts/showDepartments.action
     */

    private String isShowMembersParam;//0  存起来  1不存
    private int respCode;
    private String isShowMembers;//下一级是否有人员列表  1否 0是
    private String isNextUrlParam;//是否是下一个链接的请求参数 1不拼接 0 拼接
    private String respMsg;
    private String paramKey;//请求参数的key
    private String requestUrl;//下一级的请求链接
    /**
     * val : 1
     * name : 未来科技城
     */

    private List<DataBean> data;

    public String getIsShowMembersParam() {
        return isShowMembersParam;
    }

    public void setIsShowMembersParam(String isShowMembersParam) {
        this.isShowMembersParam = isShowMembersParam;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getIsShowMembers() {
        return isShowMembers;
    }

    public void setIsShowMembers(String isShowMembers) {
        this.isShowMembers = isShowMembers;
    }

    public String getIsNextUrlParam() {
        return isNextUrlParam;
    }

    public void setIsNextUrlParam(String isNextUrlParam) {
        this.isNextUrlParam = isNextUrlParam;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int val;
        private String name;

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
