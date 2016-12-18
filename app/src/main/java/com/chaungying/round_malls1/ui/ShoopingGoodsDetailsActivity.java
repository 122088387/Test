package com.chaungying.round_malls1.ui;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.round_malls.utils.BigDecimalUtils;
import com.chaungying.round_malls1.bean.ShoppingGoodsBean;
import com.chaungying.round_malls1.utils.SpannableUtils;
import com.chaungying.wuye3.R;
import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * @author 王晓赛 or 2016/9/23
 *         <p/>
 *         商品详情界面
 */
@ContentView(R.layout.activity_shopping_goods_details)
public class ShoopingGoodsDetailsActivity extends BaseActivity {


    private ShoppingGoodsBean.ProductsBean productsBean;

    @ViewInject(R.id.shopping_goods_details_image)
    private ImageView shopping_goods_details_image;

    //商品名字
    @ViewInject(R.id.shopping_goods_details_name)
    private TextView shopping_goods_details_name;

    //月销售
    @ViewInject(R.id.shopping_store_item_sellerNum)
    private TextView shopping_store_item_sellerNum;

    //好评率
    @ViewInject(R.id.shopping_store_item_evalGoodPercent)
    private TextView shopping_store_item_evalGoodPercent;

    //商品价格
    @ViewInject(R.id.shopping_goods_details_price)
    private TextView shopping_goods_details_price;

    //共有多少人评价
    @ViewInject(R.id.shopping_goods_details_total_eva)
    private TextView shopping_goods_details_total_eva;

    @ViewInject(R.id.shopping_goods_details_eva_ratio_value)
    private TextView shopping_goods_details_eva_ratio_value;

    //加入购物车
    @ViewInject(R.id.shopping_goods_details_add)
    private TextView shopping_goods_details_add;

    @ViewInject(R.id.shopping_goods_details_progressbar)
    private ProgressBar shopping_goods_details_progressbar;

    //商品描述
    @ViewInject(R.id.shopping_goods_details_describe)
    private TextView shopping_goods_details_describe;

    private int progressNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBar("商品详情", R.drawable.nav_return, R.drawable.share);

        productsBean = (ShoppingGoodsBean.ProductsBean) getIntent().getExtras().getSerializable("bean");
        initView();
    }

    private void initView() {
        String str = productsBean.getProLogo();
        if (str != null && str.length() > 0) {
            str = str.replace("\\", "/");
            Picasso.with(this).load(str).into(shopping_goods_details_image);
        } else {
            Picasso.with(this).load(R.drawable.no_pic).into(shopping_goods_details_image);
        }
        shopping_goods_details_name.setText(productsBean.getProName());
        shopping_store_item_sellerNum.setText("月销售：" + productsBean.getSellerSum());
        shopping_store_item_evalGoodPercent.setText("好评率：" + productsBean.getEvalGoodPercent());
        shopping_goods_details_price.setText(SpannableUtils.setTextSize(BigDecimalUtils.rounding(productsBean.getPrice()), 0, 1, 26));
        shopping_goods_details_total_eva.setText("共有" + productsBean.getTotalEval() + "人评价");
//        shopping_goods_details_progressbar.setProgress(Integer.parseInt(productsBean.getEvalGoodPercent().substring(0, productsBean.getEvalGoodPercent().indexOf("."))));
        progressNum = Integer.parseInt(productsBean.getEvalGoodPercent().substring(0, productsBean.getEvalGoodPercent().indexOf(".")));
        initProgress();
        shopping_goods_details_eva_ratio_value.setText(productsBean.getEvalGoodPercent());
        shopping_goods_details_describe.setText("");
    }

    private void initProgress() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(1000);
        animator.setStartDelay(300);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                shopping_goods_details_progressbar.setProgress((int) ((float) valueAnimator.getAnimatedValue() * progressNum));
            }
        });
        animator.start();
    }


    /**
     * 加入购物车
     *
     * @param view
     */
    @Event(value = R.id.shopping_goods_details_add)
    private void add_shop_cart(View view) {
        Intent intent = new Intent();
        intent.putExtra("proId", productsBean.getProId());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
