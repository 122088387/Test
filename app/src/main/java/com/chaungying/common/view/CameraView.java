package com.chaungying.common.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.common.ui.PhotoActivity;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/16.
 */
public class CameraView extends FrameLayout implements AdapterView.OnItemClickListener {
    private GridView gridview;
    private GridAdapter adapter;
    private float dp;

    private Context mContext;

    private String upLoadeUrl;
    private String filedName;

    private TextView tv_paizhao;
    /**
     * 相机拍照
     */
    public static final int CAMERA_PIC = 0;

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public String getUpLoadeUrl() {
        return upLoadeUrl;
    }

    public void setUpLoadeUrl(String upLoadeUrl) {
        this.upLoadeUrl = upLoadeUrl;
    }

    private HorizontalScrollView selectimg_horizontalScrollView;

    public ArrayList<Bitmap> bmp = new ArrayList<Bitmap>();

    public CameraView(Context context) {
        this(context, null);
    }

    public CameraView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    public void setTitle(String strTitle) {
        tv_paizhao.setText(strTitle);
    }

    /**
     * 初始化GridView控件
     */
    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.camera_layout, this);
        tv_paizhao = (TextView) view.findViewById(R.id.tv_paizhao);
        selectimg_horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.selectimg_horizontalScrollView);
        gridview = (GridView) view.findViewById(R.id.noScrollgridview);
        dp = getResources().getDimension(R.dimen.dp);
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridviewInit(new ArrayList<String>());

    }

    private List<String> myArr;

    public void gridviewInit(List<String> arr) {
        if (arr.size() != 0) {
            myArr = arr;
        }
        adapter = new GridAdapter(mContext);
        adapter.setSelectedPosition(0);
        int size = 0;
        if (bmp.size() < 6) {
            size = bmp.size() + 1;
        } else {
            size = bmp.size();
        }
        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        final int width = size * (int) (dp * 9.4f);
        params.width = width;
        gridview.setLayoutParams(params);
        gridview.setColumnWidth((int) (dp * 9.4f));
        gridview.setStretchMode(GridView.NO_STRETCH);
        gridview.setNumColumns(size);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(this);

        selectimg_horizontalScrollView.getViewTreeObserver()
                .addOnPreDrawListener(// 绘制完毕
                        new ViewTreeObserver.OnPreDrawListener() {
                            public boolean onPreDraw() {
                                selectimg_horizontalScrollView.scrollTo(width,
                                        0);
                                selectimg_horizontalScrollView
                                        .getViewTreeObserver()
                                        .removeOnPreDrawListener(this);
                                return false;
                            }
                        });
    }

    /**
     * 点击单张图片
     *
     * @param parent
     * @param view
     * @param arg2
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int arg2, long id) {
        if (arg2 == bmp.size()) {//点击加号的时候
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("bmp", bmp);
            msg.setData(bundle);
            msg.what = CAMERA_PIC;
            handler.sendMessage(msg);
        } else {//点击图片的时候
            Intent intent = new Intent(mContext, PhotoActivity.class);
            intent.putExtra("ID", arg2);
            mContext.startActivity(intent);
        }

    }

    /**
     * 相片布局 适配器
     */
    private class GridAdapter extends BaseAdapter {
        private LayoutInflater listContainer;
        private int selectedPosition = -1;
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public class ViewHolder {
            public ImageView image;
            public Button bt;
        }

        public GridAdapter(Context context) {
            listContainer = LayoutInflater.from(context);
        }

        public int getCount() {
            if (bmp.size() < 6) {
                return bmp.size() + 1;
            } else {
                return bmp.size();
            }
        }

        public Object getItem(int arg0) {

            return null;
        }

        public long getItemId(int arg0) {

            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        /**
         * ListView Item设置
         */
        public View getView(final int position, View convertView, ViewGroup parent) {
            final int index = position;
            // 自定义视图
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                // 获取list_item布局文件的视图
                convertView = listContainer.inflate(R.layout.item_published_grida, null);
                // 获取控件对象
                holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
                holder.bt = (Button) convertView.findViewById(R.id.item_grida_bt);
                // 设置控件集到convertView
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == bmp.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_unfocused));
                holder.bt.setVisibility(View.GONE);
                if (position == 6) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(bmp.get(position));
                //点击照片上的删除红点时
                holder.bt.setOnClickListener(new OnClickListener() {

                    public void onClick(View v) {
                        PhotoActivity.bitmap.remove(index);
                        bmp.get(index).recycle();
                        bmp.remove(index);
                        if (myArr != null && myArr.size() != 0) {
                            myArr.remove(position);
                            gridviewInit(myArr);
                        }
                    }
                });
            }

            return convertView;
        }
    }

    private Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }


}
