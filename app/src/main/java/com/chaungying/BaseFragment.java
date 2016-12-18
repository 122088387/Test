package com.chaungying;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * @author 王晓赛 or 2016/7/15
 */
public class BaseFragment extends Fragment {
    public BaseFragment() {
        super();
    }

    Activity mContext;
    @SuppressLint("ValidFragment")
    public BaseFragment(Activity context) {
        super();
        mContext = context;
    }




    /*************
     * activity跳转的封装
     *******************/
    public void openActivty(Context context, Class<?> c,
                            Bundle bundle, Uri uri) {
        Intent intent = new Intent(context, c);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (uri != null) {
            intent.setData(uri);
        }
        startActivity(intent);
    }

    /**
     * 销毁当前activity，打开另一activity
     *
     * @param context
     * @param c
     * @param bundle
     * @param uri
     */
    public void openActivityAndCloseThis(Context context, Class<?> c,
                                         Bundle bundle, Uri uri) {
        openActivty(context, c, bundle, uri);
        ((Activity) context).finish();

    }

    public void openActivtyForResult(Context context, Class<?> c,
                                     Bundle bundle, int resultCode) {
        Intent intent = new Intent(context, c);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, resultCode);
    }

    public void openActivtyForResult(Activity context, Class<?> c,
                                     Bundle bundle, Uri uri, int resultCode) {
        Intent intent = new Intent(context, c);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (uri != null) {
            intent.setData(uri);

        }
        startActivityForResult(intent, resultCode);
    }
}
