package com.chaungying.zixunjieda.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaungying.site_repairs.utils.ImagePlayAnimUtils;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/8/24
 */
public class RecordDialog extends Dialog {

    private Context mContext;

    private TextView recordTime;
    private TextView recordFinish;
    private ImageView iv_anim;
    private ImageView btn_record;

    private static RecordDialog recordDialog = null;
    ImagePlayAnimUtils animUtils = null;
    static List<Integer> imageViewList = new ArrayList<Integer>();

    public RecordDialog(Context context) {
        super(context);
        mContext = context;
    }

    public RecordDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static RecordDialog createDialog(Context context) {
        recordDialog = new RecordDialog(context, R.style.RecordDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.record_dialog_view, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 0);
        recordDialog.setContentView(view, params);
        recordDialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;
        imageViewList.add(R.drawable.recordaudio_voice1);
        imageViewList.add(R.drawable.recordaudio_voice2);
        imageViewList.add(R.drawable.recordaudio_voice3);
        imageViewList.add(R.drawable.recordaudio_voice4);
        imageViewList.add(R.drawable.recordaudio_voice5);
        return recordDialog;
    }

    public void setTime(String time) {
        recordTime = (TextView) recordDialog.findViewById(R.id.recoreTime);
        recordTime.setText(time);
    }

    public void setFinishText(String finish) {
        recordFinish = (TextView) recordDialog.findViewById(R.id.recoreFinish);
        recordFinish.setText(finish);
    }

    public void startAnim() {
        iv_anim = (ImageView) recordDialog.findViewById(R.id.iv_anim);
        animUtils = ImagePlayAnimUtils.getInstance();
        animUtils.setImageView(iv_anim);
        animUtils.setImageViewList(imageViewList);
        animUtils.playImage();
    }

    public void endAnim() {
        animUtils.stopImage();
    }

    public ImageView getBtn_record() {
        btn_record = (ImageView) recordDialog.findViewById(R.id.recordaudio_record);
        return btn_record;
    }

    public TextView getRecordFinish() {
        recordFinish = (TextView) recordDialog.findViewById(R.id.recoreFinish);
        return recordFinish;
    }

    public void showDialog() {
        if (recordDialog != null && !recordDialog.isShowing()) {
            recordDialog.show();
        }
    }


}
