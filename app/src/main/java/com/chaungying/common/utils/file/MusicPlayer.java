package com.chaungying.common.utils.file;

/**
 * Created by Administrator on 2016/6/19.
 */

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.util.Log;

import com.chaungying.site_repairs.utils.ImagePlayAnimUtils;

import java.io.File;

public class MusicPlayer {

    private final static String TAG = "MusicPlayer";
    private static MediaPlayer mMediaPlayer;
    private Context mContext;
    private boolean isPlay;

    private ImagePlayAnimUtils playAnimUtils;

    public MusicPlayer(Context context) {
        mContext = context;
        playAnimUtils = ImagePlayAnimUtils.getInstance();
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void playMicFile(File file) {
        if (file != null && file.exists()) {
            Uri uri = Uri.fromFile(file);
            mMediaPlayer = MediaPlayer.create(mContext, uri);
            if (mMediaPlayer == null) {
                return;
            }
            Log.e("dddd", mMediaPlayer.toString());
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.start();
            isPlay = true;
            mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    //TODO:finish
                    Log.i(TAG, "Finish");
                    Log.i(TAG, mMediaPlayer.isPlaying() + "");
                    isPlay = false;
                    mMediaPlayer.release();
                    playAnimUtils.stopImage();
                }
            });
        }

    }

    /**
     * 播放raw中的声音文件
     */
    public void playRaw(int resources) {
        try {
            mMediaPlayer = MediaPlayer.create(mContext, resources);//重新设置要播放的音频
            mMediaPlayer.start();//开始播放
            isPlay = true;
            mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    Log.i(TAG, "Finish");
                    Log.i(TAG, mMediaPlayer.isPlaying() + "");
                    isPlay = false;
                    mMediaPlayer.release();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopPlayer() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }
}

