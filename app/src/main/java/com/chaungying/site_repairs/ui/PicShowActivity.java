package com.chaungying.site_repairs.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chaungying.wuye3.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author 王晓赛 or 2016/7/13
 */
public class PicShowActivity extends AppCompatActivity {

    private ArrayList<String> listUrl = new ArrayList<String>();
    private ArrayList<View> listViews = new ArrayList<View>();
    private ViewPager pager;
    private MyPageAdapter adapter;
    private int pageIndex;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    pager.setAdapter(adapter);
                    pager.setCurrentItem(pageIndex);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setOnPageChangeListener(pageChangeListener);
        Intent intent = getIntent();
        pageIndex = intent.getIntExtra("ID", 0);
        listUrl = intent.getStringArrayListExtra("URL");
        //子线程中获取图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < listUrl.size(); i++) {
                    Bitmap bitmap;
                    try {
                        if (listUrl.get(i) != null && !listUrl.get(i).equals("")) {
                            bitmap = Picasso.with(PicShowActivity.this).load(listUrl.get(i)).get();
                            listViews.add(initListViews(bitmap));
                        } else {
//                            bitmap = Picasso.with(PicShowActivity.this).load(R.drawable.image_load_error).get();
//                            listViews.add(initListViews(bitmap));
                        }
                    } catch (IOException e) {
                        try {
                            bitmap = Picasso.with(PicShowActivity.this).load(R.drawable.image_load_error).get();
                            listViews.add(initListViews(bitmap));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                }
                adapter = new MyPageAdapter(listViews);
                handler.sendEmptyMessage(0);

            }
        }).start();

//        adapter = new MyPageAdapter(listViews);// 构造adapter
//        pager.setAdapter(adapter);// 设置适配器
    }


    private ImageView initListViews(Bitmap bm) {
        ImageView img = new ImageView(this);// 构造textView对象
        img.setBackgroundColor(0xff000000);
        img.setImageBitmap(bm);
        img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return img;
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        public void onPageSelected(int arg0) {// 页面选择响应函数

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {// 滑动中。。。

        }

        public void onPageScrollStateChanged(int arg0) {// 滑动状态改变

        }
    };

    class MyPageAdapter extends PagerAdapter {

        //        private ArrayList<View> listView;// content
        private int size;// 页数

        public MyPageAdapter(ArrayList<View> listUrl) {// 构造函数
            // 初始化viewpager的时候给的一个页面
//            this.listView = listUrl;
//            size = listUrl == null ? 0 : listUrl.size();
        }

        public int getCount() {// 返回数量
            return listViews.size();
        }

        //        public int getItemPosition(Object object) {
//            return POSITION_NONE;
//        }
        @Override
        public void notifyDataSetChanged() {
            size = getCount();
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            if (size > 0) {
                size--;
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(listViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
//            FrameLayout frameLayout = new FrameLayout(PicShowActivity.this);
//            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            frameLayout.addView(listViews.get(position));
            container.addView(listViews.get(position), 0);
            return listViews.get(position);
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }
}
