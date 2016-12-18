package com.chaungying.zixunjieda.broadcase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author 王晓赛 or 2016/8/18
 */
public class MyBroadcastReceiver extends BroadcastReceiver {



    public String Broadcast_TAG = "";

    public String getBroadcast_TAG() {
        return Broadcast_TAG;
    }

    public void setBroadcast_TAG(String broadcast_TAG) {
        Broadcast_TAG = broadcast_TAG;
    }


    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
