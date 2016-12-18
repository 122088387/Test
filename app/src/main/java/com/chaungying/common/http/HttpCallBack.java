package com.chaungying.common.http;

/**
 * @author 王晓赛 or 2016/8/8
 */
public interface HttpCallBack {


    /**
     * 请求成功
     * @param result
     */
    void requestSuccess(String result);

    /**
     * 请求失败
     * @param throwable
     */
    void requestError(Throwable throwable);

    /**
     * 请求完成
     */
    void requestFinish();

    /**
     * 没有数据
     */
    void noData();

}
