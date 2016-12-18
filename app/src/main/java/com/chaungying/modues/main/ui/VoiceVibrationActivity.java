package com.chaungying.modues.main.ui;

import android.os.Bundle;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.wuye3.R;
import com.zcw.togglebutton.ToggleButton;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author 王晓赛 or 2016/8/25
 */
@ContentView(R.layout.voice_vibration)
public class VoiceVibrationActivity extends BaseActivity {

    @ViewInject(R.id.auto_btn_voice)
    ToggleButton auto_btn_voice;

    @ViewInject(R.id.auto_btn_vibration)
    ToggleButton auto_btn_vibration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBar("新消息提醒", R.drawable.nav_return, 0);

        //声音
        auto_btn_voice.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                SPUtils.put(VoiceVibrationActivity.this, Const.SPDate.AUTO_VOICE, on);
            }
        });
        //震动
        auto_btn_vibration.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                SPUtils.put(VoiceVibrationActivity.this, Const.SPDate.AUTO_VIBRATION, on);
            }
        });


        boolean isVoiceOn = (boolean) SPUtils.get(this, Const.SPDate.AUTO_VOICE, true);
        if (isVoiceOn) {
            SPUtils.put(this, Const.SPDate.AUTO_VOICE, true);
            auto_btn_voice.setToggleOn();
        } else {
            auto_btn_voice.setToggleOff();
        }

        boolean isVibrationOn = (boolean) SPUtils.get(this, Const.SPDate.AUTO_VIBRATION, true);
        if (isVibrationOn) {
            SPUtils.put(this, Const.SPDate.AUTO_VIBRATION, true);
            auto_btn_vibration.setToggleOn();
        } else {
            auto_btn_vibration.setToggleOff();
        }

    }
}
