package com.chaungying.common.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.common.constant.Const;
import com.chaungying.wuye3.R;

import java.io.File;
import java.io.FileOutputStream;

import me.panavtec.drawableview.DrawableView;
import me.panavtec.drawableview.DrawableViewConfig;

/**
 * Created by Administrator on 2016/6/16.
 */
public class HandDrawView extends FrameLayout implements View.OnClickListener {

    private String handUrl;
    private TextView tv_title;
    private ImageView iv_hand;
    private Activity mContext;

    //是否必须签字的标志
    private int isRequired;
    //该签字控件的顺序
    private int orders;
    //是否已经签过字的标示
    private boolean isSign;

    public boolean isSign() {
        return isSign;
    }

    public int getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(int isRequired) {
        this.isRequired = isRequired;
    }


    public void setOrders(int orders) {
        this.orders = orders;
    }

    public String getHandUrl() {
        return handUrl;
    }

    public void setHandUrl(String handUrl) {
        this.handUrl = handUrl;
    }


    public void setTitle(String str) {
        tv_title.setText(str);
    }

    public HandDrawView(Context context) {
        this(context, null);
    }

    public HandDrawView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HandDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = (Activity) context;
        View view = LayoutInflater.from(context).inflate(R.layout.item_hand_draw, this);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        iv_hand = (ImageView) view.findViewById(R.id.iv_hand_draw);
        iv_hand.setOnClickListener(this);

        //对画布的初始化 宽高
        WINDOW_WIDTH = mContext.getWindowManager().getDefaultDisplay().getWidth();
        WINDOW_HEIGHT = mContext.getWindowManager().getDefaultDisplay().getHeight();

    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        handDrawView = LayoutInflater.from(mContext).inflate(R.layout.item_hand_draw_view, null);
//            Animation anim = AnimationUtils.loadAnimation(mContext,R.anim.hand_draw);
//            handDrawView.setAnimation(anim);
        initUi(handDrawView);
        builder.setView(handDrawView);
        dialog = builder.create();
        dialog.show();
        //设置大小
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = WINDOW_WIDTH - 10;
        layoutParams.height = WINDOW_HEIGHT / 3 * 2;
        Window window = dialog.getWindow();
        window.setAttributes(layoutParams);
        window.setWindowAnimations(R.style.AnimHandDraw);
    }


    //////////////////////////////////////////////对涂鸦界面的控制/////////////////////////////////

    private int WINDOW_WIDTH;
    private int WINDOW_HEIGHT;
    AlertDialog dialog;

    private String handDrawUrl = "";
//    public List<String> handDrawableList = new ArrayList<String>();

    private String path = "";

    private OnSendHandDrawPathListener onSendHandDrawPathListener;

    public void setOnSendHandDrawPathListener(OnSendHandDrawPathListener onSendHandDrawPathListener) {
        this.onSendHandDrawPathListener = onSendHandDrawPathListener;
    }

    //涂鸦界面
    DrawableView drawableView;
    DrawableViewConfig config = new DrawableViewConfig();
    View handDrawView;//能够绘制的界面
//    HandDrawView myDrawView;//自定义的显示签名的界面

    private void initUi(View view) {
        drawableView = (DrawableView) view.findViewById(R.id.paintView);
//        //对涂鸦布局控件设置大小
        Button strokeWidthMinusButton = (Button) view.findViewById(R.id.btn_save);
        Button strokeWidthPlusButton = (Button) view.findViewById(R.id.btn_clear);
        Button undoButton = (Button) view.findViewById(R.id.undoButton);
        config.setStrokeColor(getResources().getColor(android.R.color.black));
        config.setShowCanvasBounds(false);
        config.setStrokeWidth(10.0f);
        config.setMinZoom(1.0f);
        config.setMaxZoom(3.0f);
        config.setCanvasHeight(WINDOW_HEIGHT / 3 * 2);
        config.setCanvasWidth(WINDOW_WIDTH - 10);
        drawableView.setConfig(config);
        //清屏按钮
        strokeWidthPlusButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                config.setStrokeWidth(config.getStrokeWidth() + 10);
                drawableView.clear();
            }
        });
        //涂鸦的保存按钮
        strokeWidthMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int picw = WINDOW_WIDTH - 10;
                int pich = WINDOW_HEIGHT / 3 * 2;

                int[] pix = new int[picw * pich];
                for (int y = 0; y < pich; y++) {
                    for (int x = 0; x < picw; x++) {
                        int index = y * picw + x;
                        int r = ((pix[index] >> 16) & 0xff) | 0xff;
                        int g = ((pix[index] >> 8) & 0xff) | 0xff;
                        int b = (pix[index] & 0xff) | 0xff;
                        pix[index] = 0xffffffff | (r << 16) | (g << 8) | b;
                    }
                }
                Bitmap bm1 = Bitmap.createBitmap(picw, pich, Bitmap.Config.ARGB_8888);
                bm1.setPixels(pix, 0, picw, 0, 0, picw, pich);

                //保存数据
                Bitmap bitmap = drawableView.obtainBitmap(bm1);
                try {
                    String sdcardState = Environment.getExternalStorageState();
                    String sdcardPathDir = Const.SAVE_MEDAR.SIGN_PATH;
                    File file = null;
                    if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
                        // 有sd卡，是否有myImage文件夹
                        File fileDir = new File(sdcardPathDir);
//                        T.showShort(mContext, fileDir.exists() + "");
                        if (!fileDir.exists()) {
                            fileDir.mkdirs();
                        }
                        // 是否有headImg文件
                        file = new File(fileDir, System.currentTimeMillis()
                                + ".JPG");
                    }
                    if (file != null) {
                        path = file.getPath();
//                        photoUri = Uri.fromFile(file);
                        handDrawUrl = path;
//                        handDrawableList.add(handDrawUrl);
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                    }
                    if (file.exists()) {
                        file.delete();
                    }
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);

                    out.flush();
                    out.close();
//                    T.showLong(mContext, "涂鸦已放入文件" + file.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                myDrawView.setIv_hand(bitmap);
                iv_hand.setImageBitmap(bitmap);
//                myDrawView.setBackGround(getResources().getColor(R.color.White));
                iv_hand.setBackgroundColor(getResources().getColor(R.color.White));
                dialog.dismiss();
                // 回调   将保存的文件路径传入到RepairsFragment
                if (onSendHandDrawPathListener != null) {
                    onSendHandDrawPathListener.sendHandDrawPathListener(orders, handDrawUrl,handUrl);
                    isSign = true;
                }
            }
        });
        /**
         * 改变画笔的颜色
         */
//        changeColorButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override public void onClick(View v) {
//                Random random = new Random();
//                config.setStrokeColor(
//                        Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256)));
//            }
//        });
        undoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                drawableView.undo();
            }
        });
    }

    public interface OnSendHandDrawPathListener {
        /**
         *
         * @param orders       作为标志
         * @param handDrawPath 文件的路径
         * @param handUrl   上传的字段
         */
        void sendHandDrawPathListener(int orders, String handDrawPath,String handUrl);
    }


}
