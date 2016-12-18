package com.chaungying.common.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaungying.common.utils.file.MusicPlayer;
import com.chaungying.site_repairs.utils.ImagePlayAnimUtils;
import com.chaungying.wuye3.R;
import com.chaungying.zixunjieda.view.RecordPopupWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/19.
 */
public class LuYinView extends FrameLayout {

    ImageView iv_luyin, iv_show_luyin;
    TextView voice_time;

    String fieldName;

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    //显示喇叭和时间的线性布局
    LinearLayout ll_horn;

    Context mContext;

    public LuYinView(Context context) {
        this(context, null);
    }

    public LuYinView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuYinView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.yuyin_layout, this);
        iv_luyin = (ImageView) view.findViewById(R.id.iv_luyin);
        iv_show_luyin = (ImageView) view.findViewById(R.id.iv_show_luyin);
        voice_time = (TextView) view.findViewById(R.id.voice_time);
        ll_horn = (LinearLayout) view.findViewById(R.id.ll_horn);
        ll_horn.setVisibility(View.GONE);

        iv_luyin.setOnClickListener(luyinClickListener);
        iv_show_luyin.setOnClickListener(luyinShowClickListener);
    }


    /////////////////////////对录音的功能实现////////////////////////////

    private boolean isFirstInitData = true;
    //控制图片播放的对象
    ImagePlayAnimUtils playAnimUtils;
    List<Integer> imageList = new ArrayList<Integer>();

    RecordPopupWindow recordPopupWindow;

    /**
     * 录音的监听
     */
    View.OnClickListener luyinClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //点击之后弹出录音对话框
            recordPopupWindow = new RecordPopupWindow((Activity) mContext);

            //设置layout在PopupWindow中显示的位置
            recordPopupWindow.showAtLocation(((Activity) mContext).findViewById(R.id.main),
                    Gravity.FILL | Gravity.CENTER_HORIZONTAL, 0, 0);
            //设置回调声音文件和时间的监听
            recordPopupWindow.setListener(onSendVoicePathListener);
        }
    };

    //为弹出窗口实现监听类
    private MusicPlayer mPlayer = new MusicPlayer(mContext);
    private File recAudioFile = null;

    public File getRecAudioFile() {
        return recAudioFile;
    }

    RecordPopupWindow.OnSendVoicePathListener onSendVoicePathListener = new RecordPopupWindow.OnSendVoicePathListener() {
        @Override
        public void sendVoicePathListener(File path, String time) {
            recAudioFile = path;
            ll_horn.setVisibility(View.VISIBLE);
            voice_time.setText(time);
        }
    };

    /**
     * 录音的播放监听
     */
    View.OnClickListener luyinShowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isFirstInitData) {
                imageList.add(R.drawable.voice1);
                imageList.add(R.drawable.audio_green_2);
                imageList.add(R.drawable.audio_green_3);
                isFirstInitData = !isFirstInitData;
            }

            playAnimUtils = ImagePlayAnimUtils.getInstance();
            if (mPlayer.isPlay() == false) {
                mPlayer.playMicFile(recAudioFile);
                playAnimUtils.setImageView(iv_show_luyin);
                playAnimUtils.setImageViewList(imageList);
                playAnimUtils.setPlay(true);
                playAnimUtils.playImage();
            }
        }
    };
}
