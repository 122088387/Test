package com.chaungying.common.utils.internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

/**
 * @author 王晓赛 or 2016/7/12
 *         对网络判断的工具类
 */
public class NetworkUtils {

    /**
     * 判断当前网络是否wifi类型
     *
     * @param context 上下文对象
     * @return 返回值为true代表是wifi状态
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 获取Gson实例
     * @return
     */
    public static Gson getGson() {
        return new Gson();
    }
}
