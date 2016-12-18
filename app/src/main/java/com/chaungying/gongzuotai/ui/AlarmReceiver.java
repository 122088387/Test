package com.chaungying.gongzuotai.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import static com.chaungying.MyApplication.getApplication;

/**
 * 闹钟服务
 */
public class AlarmReceiver extends BroadcastReceiver {
    long lastTime = 0;
    MediaPlayer alarmMusic;

    @Override
    public void onReceive(Context context, Intent intent) {

//        // 指定启动AlarmActivity组件
//        Intent newIntent = new Intent(context
//                , AlarmActivity.class);
//        context.startActivity(newIntent);
//        // 创建PendingIntent对象
//        PendingIntent pi = PendingIntent.getActivity(
//                context, 0, intent, 0);
//
//        long time = System.currentTimeMillis() - lastTime;
//        lastTime = System.currentTimeMillis();
//        Toast.makeText(context, "闹铃响了, 可以做点事情了~~" + time, Toast.LENGTH_LONG).show();

        Intent dialogIntent = new Intent(context, AlarmActivity.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(dialogIntent);
    }
}
