package com.chaungying.common.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.NotFoundCacheError;
import com.yolanda.nohttp.error.ServerError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;

/**
 * Toast统一管理类
 *
 * @author 种耀淮
 */
public class T {

    private T() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    /**
     * Toast短
     *
     * @param activity 传入context
     * @param time     long类型，我们传入的时间长度（如500）
     */
    public static void showTShort(final Activity activity, final CharSequence text, final long time) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                final Toast toast = Toast.makeText(activity, text, Toast.LENGTH_LONG);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        toast.cancel();
                    }
                }, time);
            }
        });
        // Toast.makeText(context, text, Toast.LENGTH_SHORT).show();

    }


    /**
     * 显示错误提示
     */
    public static void showHttpException(Activity activity, Exception exception, int responseCode) {
        /*if (exception instanceof err) {// 客户端错误
            if (responseCode == 400) {//服务器未能理解请求。
                T.showShort(activity, "服务器表示不能理解");
            } else if (responseCode == 403) {// 请求的页面被禁止
                T.showShort(activity, "服务器表示不愿意");
            } else if (responseCode == 404) {// 服务器无法找到请求的页面
                T.showShort(activity, "服务器表示找不到");
            } else {// 400-417都是客户端错误，开发者可以自己去查询噢
                T.showShort(activity, "服务器表示伤不起");
            }
        } else*/
        if (exception instanceof ServerError) {// 服务器错误
            if (500 == responseCode) {
                T.showShort(activity, "服务器遇到不可预知的情况");
            } else if (501 == responseCode) {
                T.showShort(activity, "服务器不支持的请求");
            } else if (502 == responseCode) {
                T.showShort(activity, "服务器从上游服务器收到一个无效的响应");
            } else if (503 == responseCode) {
                T.showShort(activity, "服务器临时过载或当机");
            } else if (504 == responseCode) {
                T.showShort(activity, "网关超时");
            } else if (505 == responseCode) {
                T.showShort(activity, "服务器不支持请求中指明的HTTP协议版本");
            } else {
                T.showShort(activity, "服务器脑子有问题");
            }
        } else if (exception instanceof NetworkError) {// 网络不好
            T.showShort(activity, "请检查网络");
        } else if (exception instanceof TimeoutError) {// 请求超时
            T.showShort(activity, "请求超时");
        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
            T.showShort(activity, "未发现指定服务器");
        } else if (exception instanceof URLError) {// URL是错的
            T.showShort(activity, "URL错误");
        } else if (exception instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            T.showShort(activity, "没有发现缓存");
        } else {
            T.showShort(activity, "未知错误");
        }
    }
}
