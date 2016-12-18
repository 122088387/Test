package com.chaungying.gongzuotai.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.chaungying.common.view.CustomDialog;
import com.chaungying.wuye3.R;

import org.xutils.x;

/**
 * 创建者：  王晓赛
 * 时  间： 2016/11/3
 */

public class AlarmActivity extends Activity {
    MediaPlayer alarmMusic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        // 加载指定音乐，并为之创建MediaPlayer对象
        alarmMusic = MediaPlayer.create(this, R.raw.message_come);
        alarmMusic.setLooping(true);
        alarmMusic.start();

        // 创建一个对话框
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle("提醒");
        builder.setMessage("接单后，请去执行任务");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 停止音乐
                alarmMusic.stop();
                //停止该服务
                Intent intent = new Intent(AlarmActivity.this, AlarmService.class);
                stopService(intent);
                // 结束该Activity
                AlarmActivity.this.finish();
            }
        });
        CustomDialog dialog = builder.create();
        dialog.show();
        dialog.setCancelable(false);

//        final AlertDialog.Builder builder = new AlertDialog.Builder(AlarmActivity.this);
//        AlertDialog dialog = builder.setTitle("提醒")
//                .setMessage("接单后，请去执行任务")
//                .setPositiveButton(
//                        "确定",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // 停止音乐
//                                alarmMusic.stop();
//                                dialog.dismiss();
//                                // 结束该Activity
//                                AlarmActivity.this.finish();
//                            }
//                        }
//                ).show();
    }
}
