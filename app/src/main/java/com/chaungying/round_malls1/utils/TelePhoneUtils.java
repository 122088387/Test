package com.chaungying.round_malls1.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 创建者：  王晓赛
 * 时  间： 2016/10/19
 */

public class TelePhoneUtils {

    public static void telePhone(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
//            intent.setData(Uri.parse("tel:"+bean.getSelectPeronPhone()));
        intent.setData(Uri.parse("tel:" + phoneNum));
        context.startActivity(intent);
    }
}
