package com.chaungying.common.http;

import com.chaungying.common.constant.Base;
import com.chaungying.common.exception.JsonException;
import com.chaungying.metting.view.ProgressUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * @author 王晓赛 or 2016/8/8
 *         <p/>
 *         对xutils网络的封装
 */
public class MyHttpRequest{

    private HttpCallBack httpCallBack;

    public void setHttpCallBack(HttpCallBack httpCallBack) {
        this.httpCallBack = httpCallBack;
    }

    public static MyHttpRequest getInstance(){
        return new MyHttpRequest();
    }

    private MyHttpRequest(){

    }

    /**
     * 返回请求数据成功后的json串，如果不成功或者错误返回null
     * @param params   请求的参数
     *
     * @return    返回的json串
     */
    public void postRequse(RequestParams params) {
        params.setConnectTimeout(30 *1000);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Base base = gson.fromJson(result,Base.class);
                int resultCode = base.respCode;
                if(resultCode == 1001){
                    try {//是json结构时
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        if(jsonArray.length() > 0){
                            httpCallBack.requestSuccess(result);
                        }else{
                            httpCallBack.noData();
                        }
                    } catch (JSONException e) {//不是json结构时
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            if(jsonArray.length() > 0){
                                httpCallBack.requestSuccess(result);
                            }else{
                                httpCallBack.noData();
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                }else{
                    try {
                        throw new JsonException("Json请求错误码:"+resultCode);
                    } catch (JsonException e) {
                        e.printStackTrace();
                    }
                }
                ProgressUtil.close();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                httpCallBack.requestError(ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                ProgressUtil.close();
                httpCallBack.requestFinish();
            }
        });
    }

}
