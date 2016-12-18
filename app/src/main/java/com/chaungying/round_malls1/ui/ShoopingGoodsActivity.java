package com.chaungying.round_malls1.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.common.utils.T;
import com.chaungying.common.utils.file.Base;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.round_malls.interface_.ReturnShoppingTotalPirceListener;
import com.chaungying.round_malls.recyclerView.FamiliarRecyclerView;
import com.chaungying.round_malls.utils.BigDecimalUtils;
import com.chaungying.round_malls1.adapter.ShoppingGoodsEvaAdapter;
import com.chaungying.round_malls1.adapter.ShoppingGoodsLeftAdapter;
import com.chaungying.round_malls1.adapter.ShoppingGoodsRightAdapter;
import com.chaungying.round_malls1.bean.ShoppingGoodsBean;
import com.chaungying.round_malls1.bean.ShoppingStoreBean;
import com.chaungying.round_malls1.bean.ShoppingStoreEvaluationBean;
import com.chaungying.round_malls1.utils.SpannableUtils;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 王晓赛 or 2016/9/23
 *         <p/>
 *         商品列表
 */
@ContentView(R.layout.activity_shopping_goods)
public class ShoopingGoodsActivity extends BaseActivity implements View.OnClickListener, ReturnShoppingTotalPirceListener {

    @ViewInject(R.id.rl_shopping_goods)
    private RelativeLayout rl_shopping_goods;

    @ViewInject(R.id.iv_blurry)
    private ImageView iv_blurry;

    //店头像
    @ViewInject(R.id.shopping_goods_head)
    private ImageView shopping_goods_head;

    //电话
    @ViewInject(R.id.shopping_goods_telephone)
    private TextView shopping_goods_telephone;

    //店收藏
    @ViewInject(R.id.img_collection)
    private ImageView img_collection;
    //是否收藏的标志
    private boolean isCollection;

    @ViewInject(R.id.text)
    private TextView text;

    //店标题
    @ViewInject(R.id.shopping_goods_name)
    private TextView shopping_goods_name;
    //店描述
    @ViewInject(R.id.shopping_goods_description)
    private TextView shopping_goods_description;

    @ViewInject(R.id.lv_shopping_goods_list)
    private FamiliarRecyclerView lv_shopping_goods_list;

    @ViewInject(R.id.lv_shopping_goods_type)
    private FamiliarRecyclerView lv_shopping_goods_type;

    @ViewInject(R.id.lv_shopping_goods_list_evaluation)
    private FamiliarRecyclerView lv_shopping_goods_list_evaluation;

    private ShoppingGoodsRightAdapter goodsRightAdapter;
    private ShoppingGoodsLeftAdapter goodsLeftAdapter;
    private ShoppingGoodsEvaAdapter goodsEvaAdapter;


    //右边商品的列表
    private ArrayList<ShoppingGoodsBean.ProductsBean> goodsRightBeanList = new ArrayList<>();
    //左边商品的类型
    private List<ShoppingGoodsBean.ProductClassesBean> goodsLeftBeanList = new ArrayList<>();
    //商户评价的数据
    private List<ShoppingStoreEvaluationBean.EvaluatesBean> storeEvaList = new ArrayList<>();


    float mLastY = 0;

    private ShoppingStoreBean.DatasBean storeBean;

    //商品
    @ViewInject(R.id.shopping_goods_left_btn)
    private TextView shopping_goods_left_btn;
    @ViewInject(R.id.line1)
    private TextView line1;
    //评价
    @ViewInject(R.id.shopping_goods_right_btn)
    private TextView shopping_goods_right_btn;
    @ViewInject(R.id.line2)
    private TextView line2;
    //商品的线性布局
    @ViewInject(R.id.rl_layout_shopping_goods)
    private RelativeLayout rl_layout_shopping_goods;
    //评价的线性布局
    @ViewInject(R.id.linear_layout_shopping_goods_evaluation)
    private RelativeLayout linear_layout_shopping_goods_evaluation;

    //做下角总价
    @ViewInject(R.id.round_malls_total_price)
    private TextView round_malls_total_price;

    @ViewInject(R.id.shopping_cart)
    private ImageView shopping_cart;

    private String totalPrice = "0.00";//100

    //确认订单
    @ViewInject(R.id.confirm_order)
    private TextView confirm_order;


    //到详情界面的跳转请求码
    private static final int REQUEST_CODE = 0x0001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        storeBean = (ShoppingStoreBean.DatasBean) getIntent().getSerializableExtra("bean");

        round_malls_total_price.setTextColor(getResources().getColor(R.color.color_999999));

        shopping_goods_telephone.setText("电话：" + storeBean.getPhone());
        if (storeBean.getIsCollect() == 0) {//没有收藏
            isCollection = false;
            img_collection.setImageResource(R.drawable.collection_no);
            text.setText("收藏");
        } else {
            isCollection = true;
            img_collection.setImageResource(R.drawable.collection);
            text.setText("已收藏");
        }
        //加载该店的图片
        Picasso.with(this).load(storeBean.getBanner()).error(R.drawable.default_png).into(shopping_goods_head);

        //                Blurry.with(ShoopingGoodsActivity.this)
//                        .radius(10)
//                        .sampling(8)
//                        .async()
//                        .capture(iv_blurry)
//                        .into(iv_blurry);

        shopping_goods_name.setText(storeBean.getName());
        shopping_goods_description.setText(storeBean.getIntroduce());

        shopping_goods_left_btn.setTextColor(getResources().getColor(R.color.colorPrimary));
        shopping_goods_right_btn.setTextColor(getResources().getColor(R.color.black));
        shopping_goods_left_btn.setOnClickListener(this);
        shopping_goods_right_btn.setOnClickListener(this);
        confirm_order.setOnClickListener(this);
        confirm_order.setVisibility(View.GONE);


        lv_shopping_goods_list.setLayoutManager(new LinearLayoutManager(this));
        lv_shopping_goods_type.setLayoutManager(new LinearLayoutManager(this));
        lv_shopping_goods_list_evaluation.setLayoutManager(new LinearLayoutManager(this));


        goodsRightAdapter = new ShoppingGoodsRightAdapter(this);
        goodsRightAdapter.setToolPrice(totalPrice);
        goodsRightAdapter.setReturnPirceListener(this);
        goodsLeftAdapter = new ShoppingGoodsLeftAdapter(this);
        goodsEvaAdapter = new ShoppingGoodsEvaAdapter(this);
        initData();
    }

    /**
     * 初始化界面数据
     */
    private void initData() {

        round_malls_total_price.setText(SpannableUtils.setTextSize("￥0.00", 0, 1, 26));

        ProgressUtil.show(this, "加载中...");
        RequestParams params = new RequestParams(Const.WuYe.URL_SELLER_GET_SELLER_BY_ID);
        params.addParameter("id", storeBean.getId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ShoppingGoodsBean bean = new Gson().fromJson(result, ShoppingGoodsBean.class);
                goodsLeftBeanList = bean.getProductClasses();
                for (int i = 0; i < goodsLeftBeanList.size(); i++) {
                    if (i == 0) {
                        goodsLeftBeanList.get(i).setColor(true);
                    }
                }
                goodsRightBeanList = (ArrayList<ShoppingGoodsBean.ProductsBean>) bean.getProducts();
                initView();
                ProgressUtil.close();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        ///////////////////////////获取商户评价的数据/////////////////////////////////
        RequestParams params1 = new RequestParams(Const.WuYe.URL_SELLER_GET_SELLER_EVALUATE);
        params1.addParameter("id", storeBean.getId());
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ShoppingStoreEvaluationBean bean = new Gson().fromJson(result, ShoppingStoreEvaluationBean.class);
                storeEvaList = bean.getEvaluates();
                initViewEva();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }

    //存储右边的临时商品集合
    private List<ShoppingGoodsBean.ProductsBean> tempList = new ArrayList<>();

    private void initView() {
        for (int i = 0; i < goodsRightBeanList.size(); i++) {
            if (goodsLeftBeanList.size() > 0) {
                if (goodsRightBeanList.get(i).getClassId().equals(goodsLeftBeanList.get(0).getClassId())) {
                    tempList.add(goodsRightBeanList.get(i));
                }
            }
        }
        goodsRightAdapter.setList(tempList);
        lv_shopping_goods_list.setAdapter(goodsRightAdapter);
        lv_shopping_goods_list.setOnItemClickListener(goodsListClick);

        goodsLeftAdapter.setList(goodsLeftBeanList);
        lv_shopping_goods_type.setAdapter(goodsLeftAdapter);
        lv_shopping_goods_type.setOnItemClickListener(goodsTypeClick);
    }

    ////////////////////商户评价的数据设置////////////////
    private void initViewEva() {
        goodsEvaAdapter.setList(storeEvaList);
        lv_shopping_goods_list_evaluation.setAdapter(goodsEvaAdapter);
    }

    //点击商品列表
    private FamiliarRecyclerView.OnItemClickListener goodsListClick = new FamiliarRecyclerView.OnItemClickListener() {
        @Override
        public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("bean", tempList.get(position));
            openActivtyForResult(ShoopingGoodsActivity.this, ShoopingGoodsDetailsActivity.class, bundle, REQUEST_CODE);
        }
    };
    //点击左边的分类列表
    private FamiliarRecyclerView.OnItemClickListener goodsTypeClick = new FamiliarRecyclerView.OnItemClickListener() {
        @Override
        public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
//            TextView tv = (TextView) view.findViewById(R.id.shopping_goods_left_type);
            tempList.clear();
            for (int i = 0; i < goodsLeftBeanList.size(); i++) {
                if (i == position) {
//                    tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                    goodsLeftBeanList.get(i).setColor(true);
                    for (int j = 0; j < goodsRightBeanList.size(); j++) {
                        if (goodsLeftBeanList.size() > 0) {
                            if (goodsRightBeanList.get(j).getClassId().equals(goodsLeftBeanList.get(i).getClassId())) {
                                tempList.add(goodsRightBeanList.get(j));
                            }
                        }
                    }
                    goodsRightAdapter.setList(tempList);
                    goodsRightAdapter.notifyDataSetChanged();
                } else {
//                    TextView tvOther = (TextView) familiarRecyclerView.getChildAt(i).findViewById(R.id.shopping_goods_left_type);
//                    tvOther.setTextColor(getResources().getColor(R.color.black));
                    goodsLeftBeanList.get(i).setColor(false);
                }
            }
            goodsLeftAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shopping_goods_left_btn:
                rl_layout_shopping_goods.setVisibility(View.VISIBLE);
                linear_layout_shopping_goods_evaluation.setVisibility(View.GONE);
                shopping_goods_right_btn.setTextColor(getResources().getColor(R.color.black));
                shopping_goods_left_btn.setTextColor(getResources().getColor(R.color.colorPrimary));
                line1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                line2.setBackgroundColor(getResources().getColor(R.color.list_view_line));
                break;
            case R.id.shopping_goods_right_btn:
                rl_layout_shopping_goods.setVisibility(View.GONE);
                linear_layout_shopping_goods_evaluation.setVisibility(View.VISIBLE);
                shopping_goods_right_btn.setTextColor(getResources().getColor(R.color.colorPrimary));
                shopping_goods_left_btn.setTextColor(getResources().getColor(R.color.black));
                line1.setBackgroundColor(getResources().getColor(R.color.list_view_line));
                line2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;
            case R.id.confirm_order://确认下单
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", goodsRightBeanList);
                bundle.putString("totalPrice", totalPrice);
                bundle.putInt("storeId", storeBean.getId());
                bundle.putString("storeName", storeBean.getName());
                openActivty(this, ShoppingCartActivity.class, bundle, null);
                break;
        }
    }


    @Override
    public void returnTotalPirce(String price) {
        totalPrice = price;
        round_malls_total_price.setText(SpannableUtils.setTextSize("￥" + BigDecimalUtils.rounding(price), 0, 1, 26));
        if (!BigDecimalUtils.rounding(price).equals("0.00")) {
            confirm_order.setVisibility(View.VISIBLE);
            shopping_cart.setImageResource(R.drawable.img_shopping_cart);
            round_malls_total_price.setTextColor(getResources().getColor(R.color.red_fd4420));
        } else {
            confirm_order.setVisibility(View.GONE);
            shopping_cart.setImageResource(R.drawable.img_shopping_cart_no);
            round_malls_total_price.setTextColor(getResources().getColor(R.color.color_999999));
        }
    }

    //点击收藏心形图片的操作
    @Event(value = R.id.rl_collection)
    private void img_collection(View view) {
        String url = "";
        //收藏
        if (isCollection) {
            url = Const.WuYe.URL_SELLER_CANCEL_SELLER_COLLECT;
            img_collection.setImageResource(R.drawable.collection_no);
            text.setText("收藏");
            isCollection = !isCollection;
        } else {//没有收藏
            url = Const.WuYe.URL_SELLER_ADD_SELLER_COLLECT;
            img_collection.setImageResource(R.drawable.collection);
            text.setText("已收藏");
            isCollection = !isCollection;
        }
        RequestParams params = new RequestParams(url);
//        memberId=4516&sellerId=1
        params.addParameter("memberId", SPUtils.get(this, Const.SPDate.ID, -1));
        params.addParameter("sellerId", storeBean.getId());
        final String finalUrl = url;
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Base base = new Gson().fromJson(result, Base.class);
                if (base.respCode == 1001) {
                    if (finalUrl.equals(Const.WuYe.URL_SELLER_CANCEL_SELLER_COLLECT)) {
                        T.showShort(ShoopingGoodsActivity.this, "取消收藏成功");
                    } else {
                        T.showShort(ShoopingGoodsActivity.this, "收藏成功");
                    }
                } else {
                    T.showShort(ShoopingGoodsActivity.this, "收藏失败");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.showShort(ShoopingGoodsActivity.this, "收藏失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String proId = data.getStringExtra("proId");
                for (int i = 0; i < tempList.size(); i++) {
                    if (proId.equals(tempList.get(i).getProId())) {
                        int num = tempList.get(i).getShoppingNum();
                        num++;
                        tempList.get(i).setShoppingNum(num);
                        goodsRightAdapter.notifyDataSetChanged();
                        totalPrice = "0";
                        for (int j = 0; j < goodsRightBeanList.size(); j++) {
                            if (goodsRightBeanList.get(j).getShoppingNum() > 0) {
                                String price = BigDecimalUtils.cheng(goodsRightBeanList.get(j).getShoppingNum() + "", goodsRightBeanList.get(j).getPrice());
                                totalPrice = BigDecimalUtils.jia(totalPrice, price);
                                goodsRightAdapter.setToolPrice(totalPrice);
                                round_malls_total_price.setText(SpannableUtils.setTextSize("￥" + BigDecimalUtils.rounding(totalPrice), 0, 1, 26));
                                round_malls_total_price.setTextColor(getResources().getColor(R.color.red_fd4420));
                                confirm_order.setVisibility(View.VISIBLE);
                                shopping_cart.setImageResource(R.drawable.img_shopping_cart);
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        //数据是使用Intent返回
        Intent intent = new Intent();
        //把返回数据存入Intent
        if (isCollection) {
            intent.putExtra("TAG", 1);
        } else {
            intent.putExtra("TAG", 0);
        }
        //设置返回数据
        setResult(RESULT_OK, intent);
        finish();
    }

    @Event(value = R.id.image_return)
    private void image_return(View view) {
        //数据是使用Intent返回
        Intent intent = new Intent();
        //把返回数据存入Intent
        if (isCollection) {
            intent.putExtra("TAG", 1);
        } else {
            intent.putExtra("TAG", 0);
        }
        //设置返回数据
        setResult(RESULT_OK, intent);
        finish();
    }

    //分享
    @Event(value = R.id.image_share)
    private void image_share(View view) {

    }


//////////////////////////滑动监听/////////////////////////

//    lv_shopping_goods_list.setOnScrollListener(new RecyclerView.OnScrollListener() {
//
//        public void onScrollStateChanged(AbsListView view, int scrollState) {
//        }
//
//
//    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//        if (firstVisibleItem == 0) {
//            Log.d("ListView", "##### 滚动到顶部 #####");
//        } else if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
//            Log.d("ListView", "##### 滚动到底部 ######");
//        }
//    }
//});
//        lv_shopping_goods_list.setOnTouchListener(new View.OnTouchListener() {
//@Override
//public boolean onTouch(View v, MotionEvent event) {
//
//final int action = event.getAction();
//        switch (action & MotionEvent.ACTION_MASK) {
//        case MotionEvent.ACTION_MOVE:
//        float y = event.getY();
//        if (y > mLastY) { // 向下
//        Log.d("向下", y + ":" + mLastY);
//        } else {// 向上
//        Log.d("向上", y + ":" + mLastY);
//        double value = y - mLastY;
//        ValueAnimator animator = new ValueAnimator();
//        animator.
//        }
//        mLastY = y;
//        break;
//
//        }
//
//        return false;
//        }
//        });


}
