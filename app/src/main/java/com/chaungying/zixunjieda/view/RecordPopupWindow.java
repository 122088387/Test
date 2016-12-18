package com.chaungying.zixunjieda.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chaungying.common.constant.Const;
import com.chaungying.site_repairs.utils.ImagePlayAnimUtils;
import com.chaungying.wuye3.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * @author 王晓赛 or 2016/8/26
 */
public class RecordPopupWindow extends PopupWindow {


    private TextView recordTime;
    private TextView recordFinish;
    private ImageView iv_anim;
    private ImageView btn_record;
    private View mMenuView;
    ImagePlayAnimUtils animUtils = null;
    List<Integer> imageViewList = new ArrayList<Integer>();
    private Context mContext;

    private String voicePathDir = "";

    public RecordPopupWindow(Activity context) {
        super(context);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.record_dialog_view, null);
        recordTime = (TextView) mMenuView.findViewById(R.id.recoreTime);
        recordTime.setText("00:00");

        recordFinish = (TextView) mMenuView.findViewById(R.id.recoreFinish);
        recordFinish.setText("取消");
        recordFinish.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        recordFinish.setOnClickListener(recordItemsOnClick);

        iv_anim = (ImageView) mMenuView.findViewById(R.id.iv_anim);

        btn_record = (ImageView) mMenuView.findViewById(R.id.recordaudio_record);
        btn_record.setOnClickListener(recordItemsOnClick);
        //设置按钮监听
//        btn_pick_photo.setOnClickListener(itemsOnClick);
//        btn_take_photo.setOnClickListener(itemsOnClick);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x22000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.rl_pop).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
//                        dismiss();
                    }
                }
                return true;
            }
        });

        animUtils = ImagePlayAnimUtils.getInstance();
        imageViewList.add(R.drawable.recordaudio_voice1);
        imageViewList.add(R.drawable.recordaudio_voice2);
        animUtils.setImageViewList(imageViewList);
        animUtils.setImageView(iv_anim);

        voicePathDir = Const.SAVE_MEDAR.VOICE_PATH;

    }


    private boolean isRecord;//判断正在录制的动画和是否开始录音

    File recAudioFile = null;

    //为弹出窗口实现监听类
    private View.OnClickListener recordItemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.recoreFinish://完成的文字
                    if (recordFinish.getText().toString().equals("取消")) {
                        dismiss();
                    } else if (recordFinish.getText().toString().equals("完成")) {
                        endAnim();
                        stopRecorder();
                        if (listener != null && recAudioFile != null) {
                            listener.sendVoicePathListener(recAudioFile, getRecordTime());
                        }
                        dismiss();
                    }
                    break;
                case R.id.recordaudio_record://录制按钮
                    if (!isRecord) {
                        isRecord = !isRecord;
                        recAudioFile = new File(voicePathDir, System.currentTimeMillis() + ".amr");
                        if (!recAudioFile.exists()) {
                            recAudioFile.mkdirs();
                        }
                        startAnim();//开启动画
                        startRecorder();
                        btn_record.setEnabled(false);
                    }
                    break;
            }
        }
    };
    private OnSendVoicePathListener listener;

    public void setListener(OnSendVoicePathListener listener) {
        this.listener = listener;
    }

    public interface OnSendVoicePathListener {
        void sendVoicePathListener(File path, String time);
    }


    private MediaRecorder mMediaRecorder;

    /**
     * 开始录音
     */
    private void startRecorder() {
        mMediaRecorder = new MediaRecorder();
        if (recAudioFile.exists()) {
            recAudioFile.delete();
        }
        try {
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setOutputFile(recAudioFile.getAbsolutePath());
        } catch (Exception e) {
            dismiss();
            tipShow();
            return;
        }
        try {
            mMediaRecorder.prepare();
            mMediaRecorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            dismiss();
            tipShow();
            return;
        }
    }

    private void tipShow() {
        final MaterialDialog dialog = new MaterialDialog(mContext);
        dialog.setMessage("请手动开启录音权限，否则无法使用录音功能");
//            dialog.setNegativeButton("取消", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
        dialog.setPositiveButton("设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri packageURI = Uri.parse("package:" + "com.chaungying.wuye3");
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                mContext.startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    public void stopRecorder() {
        if (mMediaRecorder != null) {
            try {
                mMediaRecorder.stop();
                mMediaRecorder.reset();
                mMediaRecorder.release();
                mMediaRecorder = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getRecordTime() {
        return recordTime.getText().toString();
    }

    public void startAnim() {
        btn_record.setImageResource(R.drawable.recordaudio_recording);
        recordFinish.setText("完成");
        initTime = System.currentTimeMillis() / 1000;
        timeUpdata = 0;
        recordHandler.sendEmptyMessage(UPDATA_TIME);
        animUtils = ImagePlayAnimUtils.getInstance();
        animUtils.setPlay(true);
        animUtils.playImage();
    }


    @Override
    public void dismiss() {
        super.dismiss();
        recordFinish.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        recordHandler.removeMessages(UPDATA_TIME);
        btn_record.setImageResource(R.drawable.recordaudio_record);
        animUtils.stopImage();

        btn_record.setEnabled(true);
        isRecord = false;

        stopRecorder();
    }

    public void endAnim() {
        recordFinish.setEnabled(true);
        recordFinish.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        recordHandler.removeMessages(UPDATA_TIME);
        btn_record.setImageResource(R.drawable.recordaudio_record);
        animUtils.stopImage();
    }

    long initTime;
    int timeUpdata;
    private static final int UPDATA_TIME = 0;

    private Handler recordHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATA_TIME:
                    long time = System.currentTimeMillis() / 1000;
                    timeUpdata = (int) (time - initTime);
                    recordHandler.sendEmptyMessageDelayed(UPDATA_TIME, 1000);
                    recordTime.setText(recordTime(timeUpdata));
                    break;
            }
        }
    };

    public String recordTime(int timeUpdata) {
        if (timeUpdata < 10) {
            return "00:0" + timeUpdata;
        } else if (timeUpdata >= 10 && timeUpdata < 60) {
            return "00:" + timeUpdata;
        } else if (timeUpdata > 60) {
            int m = timeUpdata / 60;
            int s = timeUpdata % 60;
            if (m < 10) {
                return "0" + m + ":" + s;
            } else {
                return m + ":" + s;
            }
        }
        return "时间过长";
    }
}