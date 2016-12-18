package com.chaungying.site_repairs.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.common.utils.T;
import com.chaungying.common.utils.internet.NetworkUtils;
import com.chaungying.site_repairs.bean.RepairsRecordBean;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 王晓赛 or 2016/7/1
 */
public class RepairRecordDetailAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mContext;
    private List<RepairsRecordBean.ItemsBean> items;
    private int[] voiceIamges = {R.drawable.voice1, R.drawable.audio_green_2, R.drawable.audio_green_3};

    //文字展示类型
    private final int TYPE_TEXT = 1;
    //录音的样式
    private final int TYPE_VOICE = 2;
    //视频的样式
    private final int TYPE_VEDIO = 3;
    //照片的样式
    private final int TYPE_PICTURE = 4;

    //由于录音和录像用的是同一个布局，点击时需要区分开区分开
    private final int CLICK_VOICE = 1;
    private final int CLICK_VEDIO = 2;



    public void setItems(List<RepairsRecordBean.ItemsBean> items) {
        this.items = items;
    }

    /**
     * 根据传过来的items判定有几种类型的视图
     *
     * @return
     */
    private int getTypeCount() {
        //获取有几种不同类型的样式
        if (items != null && items.size() > 0) {//2    3   1    0   1
            int[] type = new int[items.size()];
            int typeNum = 0;
            for (int i = 0; i < type.length; i++) {
                int num = type[i];
                int numIndex = 0;
                for (int j = 0; j < type.length; j++) {
                    if (num == type[j]) {
                        numIndex++;
                    }
                }
                if (numIndex == 1) {
                    typeNum++;
                }
            }
            return typeNum;
        }
        return 0;
    }

    public RepairRecordDetailAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }


//    @Override
//    public int getViewTypeCount() {
//        return getTypeCount();
//    }

    //每个convert view都会调用此方法，获得当前所需要的view样式
//    @Override
//    public int getItemViewType(int position) {
//        return items.get(position).getType();
//    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private ImageView ivVoice = null;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder = null;
        // TODO: 王晓赛 2016年7月07日  稍后根据这个链接修改
//        http://blog.sina.com.cn/s/blog_637607ec0101d4mj.html
        View view;
        RepairsRecordBean.ItemsBean bean = items.get(position);
        int type = items.get(position).getType();
        if (convertView == null) {
            view = convertView;
            if (type == TYPE_TEXT) {
                view = LayoutInflater.from(mContext).inflate(R.layout.repairs_record_details, null);
                TextView tv_show_title = (TextView) view.findViewById(R.id.tv_show_title);
                TextView tv_show_content = (TextView) view.findViewById(R.id.tv_show_content);
                tv_show_title.setText("内容");
                tv_show_content.setText((CharSequence) bean.getCallPhone());
            } else if (type == TYPE_VOICE) {
                view = LayoutInflater.from(mContext).inflate(R.layout.repairs_record_details_voice, null);
                ivVoice = (ImageView) view.findViewById(R.id.iv_show_voice);
                ivVoice.setId(CLICK_VOICE);
                ivVoice.setOnClickListener(this);
            } else if (type == TYPE_VEDIO) {
                view = LayoutInflater.from(mContext).inflate(R.layout.repairs_record_details_voice, null);
                TextView tv = (TextView) view.findViewById(R.id.tv_show_title);
                ImageView iv = (ImageView) view.findViewById(R.id.iv_show_voice);
                tv.setText("录像");
                iv.setId(CLICK_VEDIO);
                iv.setImageResource(R.drawable.reported_pause);
                iv.setOnClickListener(this);
            } else if (type == TYPE_PICTURE) {
                view = LayoutInflater.from(mContext).inflate(R.layout.repairs_record_details_picture, null);
                final HorizontalScrollView ScrollView = (HorizontalScrollView) view.findViewById(R.id.selectimg_horizontalScrollView);
                GridView gridview = (GridView) view.findViewById(R.id.noScrollgridview);
                ShowPicAdapter adapter = new ShowPicAdapter(mContext);
                // TODO: 王晓赛 2016年7月04日  需要将照片连接获取到存入到listPic集合中
                ArrayList<String> listPic = new ArrayList<String>();
//                listPic.add("http://edu.yuhua.gov.cn/yey2/t2/201211/W020121112691543225302.jpg");
//                listPic.add("http://a.hiphotos.baidu.com/zhidao/wh%3D600%2C800/sign=7e1de35dcf1b9d168a929267c3ee98b7/8644ebf81a4c510fd12293986159252dd52aa549.jpg");
//                listPic.add("http://img10.360buyimg.com/n0/g12/M00/01/03/rBEQYVGDMhQIAAAAAAHP6kcOd3sAAAN-AL1Y2AAAdAC863.jpg");
//                listPic.add("http://img3.imgtn.bdimg.com/it/u=215038793,2488125508&fm=206&gp=0.jpg");
//                listPic.add("http://c.hiphotos.baidu.com/image/pic/item/472309f790529822583b1c2dd3ca7bcb0b46d4d7.jpg");
                int size = listPic.size();
                adapter.setBitList(listPic);
                ViewGroup.LayoutParams params = gridview.getLayoutParams();
                float dp = mContext.getResources().getDimension(R.dimen.dp);
                final int width = size * (int) (dp * 9.4f);
                params.width = width;
                gridview.setLayoutParams(params);
                gridview.setColumnWidth((int) (dp * 9.4f));
                gridview.setStretchMode(GridView.NO_STRETCH);
                gridview.setNumColumns(size);
                gridview.setAdapter(adapter);
                ScrollView.getViewTreeObserver()
                        .addOnPreDrawListener(// 绘制完毕
                                new ViewTreeObserver.OnPreDrawListener() {
                                    public boolean onPreDraw() {
                                        ScrollView.scrollTo(width,
                                                0);
                                        ScrollView
                                                .getViewTreeObserver()
                                                .removeOnPreDrawListener(this);
                                        return false;
                                    }
                                });
            }
        } else {
            //有convertView，按样式，取得不同的布局
            view = convertView;
        }
        return view;
    }

    private int num = 0;
    private boolean isStart = true;
    private boolean isPlay = true;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ivVoice.setImageResource(voiceIamges[num % voiceIamges.length]);
            num++;
            if (num == 20) {
                isStart = !isStart;
                stopTimer();
                num = 0;
            }
        }
    };

    ////////////////////对录音播放动画的控制/////////////////////


    private Timer mTimer = null;
    private TimerTask mTask = null;
    /**
     * 开启异步任务
     * @param delay  延迟时间
     * @param period 间隔时间
     */
    private void startTimer(int delay, int period){
        if(mTimer == null){
            mTimer = new Timer();
        }
        if(mTask == null){
            mTask = new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            };
        }
        if(mTimer != null && mTask != null )
            mTimer.schedule(mTask, delay, period);
    }

    public void stopTimer(){
        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
        if(mTask != null){
            mTask.cancel();
            mTask = null;
        }
    }
    /**
     * 点击录音的图标，进行播放录音,判断是否为wifi网络
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case CLICK_VOICE:
                if (isStart) {
                    if(!NetworkUtils.isWifi(mContext)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("非wifi环境，是否播放");
                        builder.setNegativeButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startTimer(100, 500);
                                isStart = !isStart;
                            }
                        });
                        builder.setPositiveButton("否", null);
                        builder.show();
                    }else{
                        startTimer(100, 500);
                        isStart = !isStart;
                    }
                }
                break;
            case CLICK_VEDIO://录像按钮
                T.showShort(mContext,"录像");
                break;
        }
    }


//    class ViewHolder {
//        TextView tv_show_title;
//        TextView tv_show_content;
//    }
}
