package com.chaungying.zixunjieda.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.file.MusicPlayer;
import com.chaungying.common.view.ShowTextNextView;
import com.chaungying.common.view.ShowTextView;
import com.chaungying.site_repairs.adapter.ShowPicAdapter;
import com.chaungying.site_repairs.utils.ImagePlayAnimUtils;
import com.chaungying.wuye3.R;
import com.chaungying.zixunjieda.bean.ShowDetailsBean;
import com.chaungying.zixunjieda.bean.ShowDetailsMaterialBean;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 王晓赛 or 2016/8/5
 */
public class ZiXunBuildView implements View.OnClickListener, DetailsValueView.OnRemoveDetailsListener {


    private Context mContext;
    private LinearLayout llAddView;


    private List<List<ShowDetailsMaterialBean.DatasBean>> datas = new ArrayList<List<ShowDetailsMaterialBean.DatasBean>>();

    public void setDatas(List<List<ShowDetailsMaterialBean.DatasBean>> datas) {
        this.datas = datas;
        initMaterialData();
    }

    public ZiXunBuildView(Context mContext, LinearLayout llAddView) {
        this.mContext = mContext;
        this.llAddView = llAddView;
    }

    //详情界面点开明细数据的控件
    DetailsValueView detailsValueView = null;

    //获取到服务器上的语音url
    String voiceUrl = "";

    public void buildView(String result, boolean b) {
        Gson gson = new Gson();
        ShowDetailsBean bean = gson.fromJson(result, ShowDetailsBean.class);
        List<ShowDetailsBean.DatasBean> beanList = bean.getDatas();
        for (int i = 0; i < beanList.size(); i++) {
            if (beanList.get(i).getItemtype() == 58) {
                TitleValueView titleView = new TitleValueView(mContext);
                titleView.setBean(beanList.get(i));
                llAddView.addView(titleView);
            } else if (beanList.get(i).getItemtype() == 20) {
                ShowTextView showTextView = new ShowTextView(mContext);
                showTextView.setText(beanList.get(i).getTitle());
                showTextView.setTextViewType(Color.BLACK, 14, mContext.getResources().getColor(R.color.huise2));
                llAddView.addView(showTextView);
            } else if (beanList.get(i).getItemtype() == 127) {
                ShowTextNextView showTextNextView = new ShowTextNextView(mContext);
                showTextNextView.setText(beanList.get(i).getTitle());
                showTextNextView.setTextViewType(Color.BLACK, 14, mContext.getResources().getColor(R.color.huise2));
                llAddView.addView(showTextNextView);
            } else if (beanList.get(i).getItemtype() == 59) {//详情界面的展示明细数据的控件
                detailsValueView = new DetailsValueView(mContext);
                detailsValueView.setBean(beanList.get(i));
                //请求明细的详情
                detailsValueView.setMingxi_url(Const.WuYe.URL_BASE + beanList.get(i).getLinkUrl());
                detailsValueView.setOnRemoveDetailsListener(this);
                llAddView.addView(detailsValueView);
            } else if (beanList.get(i).getItemtype() == 5) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.repairs_record_details_picture, null);
                TextView tv = (TextView) view.findViewById(R.id.tv_show_title);
                tv.setText(beanList.get(i).getTitle());
                final HorizontalScrollView ScrollView = (HorizontalScrollView) view.findViewById(R.id.selectimg_horizontalScrollView);
                GridView gridview = (GridView) view.findViewById(R.id.noScrollgridview);
                ShowPicAdapter adapter = new ShowPicAdapter(mContext);
                // TODO: 王晓赛 2016年7月04日  需要将照片连接获取到存入到listPic集合中
                ArrayList<String> listPic = new ArrayList<String>();
                //对从网络上获取的照片url进行分割
                String url = beanList.get(i).getValue();
                String[] surl = url.split(",");
                for (int j = 0; j < surl.length; j++) {
                    listPic.add(surl[j]);
                }
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
                llAddView.addView(view);
            } else if (beanList.get(i).getItemtype() == 19) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.repairs_record_details_voice, null);
                ivVoice = (ImageView) view.findViewById(R.id.iv_show_voice);
                TextView tv_no_voice = (TextView) view.findViewById(R.id.text_no_voice);
                voiceUrl = beanList.get(i).getValue();
                if (voiceUrl != null && !voiceUrl.equals("")) {
                    saveVoice(voiceUrl);
                } else {
                    //如果没有录音
                    ivVoice.setVisibility(View.GONE);
                    tv_no_voice.setText("没有录音");
                }
                ivVoice.setOnClickListener(this);
                llAddView.addView(view);
            }
        }
        mPlayer = new MusicPlayer(mContext);
    }


    /**
     * 如果材料明细的数据没有，则移除该控件
     */
    @Override
    public void removeDetailsListener() {
        for (int i = 0; i < llAddView.getChildCount(); i++) {
            if (llAddView.getChildAt(i) instanceof DetailsValueView) {
                llAddView.removeView(llAddView.getChildAt(i));
            }
        }
    }


    private static final String FILENAME = "";
    File recAudioFile = null;

    /**
     * 下载音频
     *
     * @param value
     */
    private void saveVoice(final String value) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String fileTime = sdf.format(new Date());
                    //下载到声音文件夹
                    File sdCard = new File(Const.SAVE_MEDAR.VOICE_PATH);
                    if (!sdCard.exists()) {
                        sdCard.mkdirs();
                    }
                    sdCard = new File(sdCard.getAbsolutePath(), fileTime + ".amr");
                    sdCard.createNewFile();

                    recAudioFile = sdCard;
                    URL url = new URL(value);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setReadTimeout(30 * 1000);
                    InputStream is = conn.getInputStream();
                    OutputStream os = new FileOutputStream(sdCard);
                    byte[] buff = new byte[1024 * 8];
                    int index = 0;
                    while ((index = is.read(buff)) != -1) {
                        os.write(buff, 0, index);
                        os.flush();
                    }
                    os.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    ImageView ivVoice = null;

    /**
     * 对明细的展示设置
     */
    private void initMaterialData() {
        detailsValueView.setDatas(datas);
    }


    private int num = 0;
    private boolean isStart = true;
    private int[] voiceIamges = {R.drawable.voice1, R.drawable.audio_green_2, R.drawable.audio_green_3};

    private MusicPlayer mPlayer = null;
    private boolean isFirstInitData = true;
    //控制图片播放的对象
    ImagePlayAnimUtils playAnimUtils;
    List<Integer> imageList = new ArrayList<Integer>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (isFirstInitData) {
                        imageList.add(R.drawable.voice1);
                        imageList.add(R.drawable.audio_green_2);
                        imageList.add(R.drawable.audio_green_3);
                        isFirstInitData = !isFirstInitData;
                        playAnimUtils = ImagePlayAnimUtils.getInstance();
                    }
                    if (mPlayer.isPlay() == false) {
                        if (mPlayer != null && recAudioFile != null) {
                            mPlayer.playMicFile(recAudioFile);
                            playAnimUtils.setImageView(ivVoice);
                            playAnimUtils.setImageViewList(imageList);
                            playAnimUtils.setPlay(true);
                            playAnimUtils.playImage();
                        }
                    }
                    break;
            }

        }
    };

    @Override
    public void onClick(View v) {
        if (voiceUrl != null && !voiceUrl.equals("")) {
            handler.sendEmptyMessage(0);
        }
//            if (!NetworkUtils.isWifi(mContext)) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                builder.setMessage("非wifi环境，是否播放");
//                builder.setNegativeButton("是", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        startTimer(100, 500);
//                        handler.sendEmptyMessage(0);
//                        isStart = !isStart;
//                    }
//                });
//                builder.setPositiveButton("否", null);
//                builder.show();
//            } else {
//                startTimer(100, 500);
//                isStart = !isStart;
//            }
    }
}
