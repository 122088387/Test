package com.chaungying.site_repairs.utils;

import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/8/24
 *
 *
 * 控制动画播放的工具类
 *
 */
public class ImagePlayAnimUtils {

    private static ImagePlayAnimUtils imagePlayAnimUtils = null;

    public static ImagePlayAnimUtils getInstance() {
        if (imagePlayAnimUtils == null) {
            imagePlayAnimUtils = new ImagePlayAnimUtils();
        }
        return imagePlayAnimUtils;
    }

    private ImagePlayAnimUtils() {

    }

    private static final int START_PLAY = 0;
    private boolean isPlay = true;

    public void setPlay(boolean play) {
        isPlay = play;
    }

    List<Integer> imageViewList = new ArrayList<Integer>();
    ImageView imageView;

    public void setImageViewList(List<Integer> imageViewList) {
        this.imageViewList = imageViewList;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void playImage() {
        handler.sendEmptyMessage(START_PLAY);
    }

    public void stopImage() {
        isPlay = false;
        imageView.setImageResource(imageViewList.get(imageViewList.size() - 1));
    }

    private int imagIndex;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_PLAY:
                    if (isPlay) {
                        handler.sendEmptyMessageDelayed(START_PLAY, 400);
                        imageView.setImageResource(imageViewList.get(imagIndex % imageViewList.size()));
                        imagIndex++;
                    }
                    break;
            }
        }
    };
}
