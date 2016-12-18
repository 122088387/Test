package com.chaungying.common.utils.file;

import android.content.Context;
import android.os.Vibrator;

/**
 * @author 王晓赛 or 2016/9/13
 */
public class VibrationPlayer {

    Context mContext;

    public static long VIBRATE_DURATION = 500;

    public VibrationPlayer(Context mContext) {
        this.mContext = mContext;
    }

    public void play() {
        Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VIBRATE_DURATION);
    }
}
