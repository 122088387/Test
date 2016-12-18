package com.chaungying.qiandao;

import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * 动画工具类
 *
 * @author 种耀淮 在2016年07月11日0:44创建
 */
public class AnimationUtil {

    public static void setAlphaVisibility(View view) {
        if (view != null) {
            if (view.getVisibility() != View.VISIBLE) {
                view.setVisibility(View.VISIBLE);
                AlphaAnimation animation = new AlphaAnimation(0f, 1f);
                animation.setDuration(200);
                view.startAnimation(animation);
            }
        }
    }

    public static void setAlphaGone(View view) {
        if (view != null) {
            if (view.getVisibility() == View.VISIBLE) {
                AlphaAnimation animation = new AlphaAnimation(1f, 0f);
                animation.setDuration(200);
                view.startAnimation(animation);
                view.setVisibility(View.GONE);
            }
        }
    }

    public static void setAlphaInVisibility(View view) {
        if (view != null) {
            if (view.getVisibility() == View.VISIBLE) {
                AlphaAnimation animation = new AlphaAnimation(1f, 0f);
                animation.setDuration(200);
                view.startAnimation(animation);
                view.setVisibility(View.INVISIBLE);
            }
        }
    }
}
