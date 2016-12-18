package com.chaungying;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.wuye3.R;

/**
 * FragmentActivity 的基类
 *
 * @anthor 王晓赛 or 2016/6/23
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 19) {
            //沉浸式状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置每个页面动作当行条的内容和图片
     *
     * @param text           标题内容
     * @param left_drawable  左边图标
     * @param right_drawable 右边图标
     */
    public void setActionBar(
            int text,
            int left_drawable,
            int right_drawable) {
        TextView tv = (TextView) findViewById(R.id.title);
        ImageView iv_left = (ImageView) findViewById(R.id.title_back);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView iv_right = (ImageView) findViewById(R.id.title_menu);
        tv.setText(text);
        iv_left.setImageResource(left_drawable);
        if (right_drawable != 0) {
            iv_right.setImageResource(right_drawable);
        }
    }

    /**
     * 设置每个页面动作当行条的内容和图片
     *
     * @param LeftText           标题内容
     * @param left_drawable  左边图标
     * @param rightText 右边文字
     */
    public void setActionBarText(
            int LeftText,
            int left_drawable,
            int rightText) {
        TextView tv = (TextView) findViewById(R.id.title);
        ImageView iv_left = (ImageView) findViewById(R.id.title_back);
        TextView tv_right = (TextView) findViewById(R.id.title_menu);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_left.setImageResource(left_drawable);
        tv.setText(LeftText);
        if ( !tv_right.equals("") ) {
            tv_right.setText(rightText);
        }
    }

    /**
     * 设置每个页面动作当行条的内容和图片
     *
     * @param text           标题内容
     * @param left_drawable  左边图标
     * @param right_drawable 右边图标
     */
    public void setActionBar(
            String text,
            int left_drawable,
            int right_drawable) {
        TextView tv = (TextView) findViewById(R.id.title);
        ImageView iv_left = (ImageView) findViewById(R.id.title_back);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView iv_right = (ImageView) findViewById(R.id.title_menu);
        tv.setText(text);
        iv_left.setImageResource(left_drawable);
        if (right_drawable != 0) {
            iv_right.setImageResource(right_drawable);
        }else{
            iv_right.setVisibility(View.GONE);
        }
    }

    /**
     * 左边图片   中间文字   右边文字
     * @param context
     * @param c
     * @param bundle
     * @param requestCode
     */
    public void openActivtyForResult(Context context, Class<?> c,
                                     Bundle bundle, int requestCode) {
        Intent intent = new Intent(context, c);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void openActivtyForResult(Context context, Class<?> c,
                                     Bundle bundle, Uri uri, int resultCode) {
        Intent intent = new Intent(context, c);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (uri != null) {
            intent.setData(uri);

        }
        startActivityForResult(intent, resultCode);
    }

    /*************
     * activity跳转的封装
     *******************/
    public void openActivty(Context context, Class<?> c,
                            Bundle bundle, Uri uri) {
        Intent intent = new Intent(context, c);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (uri != null) {
            intent.setData(uri);
        }
        startActivity(intent);
    }

    /**
     * 销毁当前activity，打开另一activity
     *
     * @param context
     * @param c
     * @param bundle
     * @param uri
     */
    public void openActivityAndCloseThis(Context context, Class<?> c,
                                         Bundle bundle, Uri uri) {
        openActivty(context, c, bundle, uri);
        this.finish();

    }

}
