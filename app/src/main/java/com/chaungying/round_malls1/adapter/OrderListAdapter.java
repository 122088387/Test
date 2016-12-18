package com.chaungying.round_malls1.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chaungying.common.constant.Base;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.date.DateStyle;
import com.chaungying.common.utils.date.DateUtil;
import com.chaungying.common.view.CustomDialog;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.round_malls1.bean.OrderListBean;
import com.chaungying.round_malls1.ui.MyShopActivity;
import com.chaungying.round_malls1.ui.OrderListEvaluationActivity;
import com.chaungying.round_malls1.ui.OrderListFragment;
import com.chaungying.round_malls1.utils.TelePhoneUtils;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 物业商城中我的中订单适配器
 */
public class OrderListAdapter extends BaseAdapter {

    private Context context;

    private List<OrderListBean.DatasBean> datas = new ArrayList<>();

    private static final int REQUEST_CODE = 0x0001;

    //区别商家和买家
    private String tag;

    public void setTag(String tag) {
        this.tag = tag;
    }

    public OrderListAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<OrderListBean.DatasBean> data) {
        datas = data;
        notifyDataSetChanged();
    }

    private Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_order_list, viewGroup, false);
            holder = new Holder(view);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        // 店家名字
        holder.item_order_list_name.setText(datas.get(i).getSellerName());
        // 商品
        holder.item_order_list_goods.setText(datas.get(i).getRsrvStr1() + "等" + datas.get(i).getRsrvStr2() + "件商品");
        holder.item_order_list_price.setText("￥" + datas.get(i).getOrderPrice());

        Date date = new Date();
        date.setTime(datas.get(i).getOperateTime() * 1000L);
        holder.item_order_list_time.setText(DateUtil.DateToString(date, DateStyle.YYYY_MM_DD));


//        0	待支付
//        1	已经付
//        2	商户确认
//        3	用户确认订单（完成订单）
//        4	用户取消
//        5	商户取消
//        6	退款中
//        7	退款完成
//        8	退款失败

        if ("0".equals(datas.get(i).getPaymentStatus())) {
            if (datas.get(i).getIsDelivery() == 0) {
                holder.item_order_list_state.setText("待支付");
                if (tag != null && tag.equals(OrderListFragment.ADAPTER_TAG)) {//买家
                    holder.item_order_list_left.setText("去支付");
                    holder.item_order_list_right.setText("取消订单");
                } else if (tag != null && tag.equals(MyShopActivity.ADAPTER_TAG)) {//卖家
                    holder.item_order_list_left.setText("取消订单");
                    holder.item_order_list_right.setText("联系买家");
                }
                holder.ll_double_btn.setVisibility(View.VISIBLE);
                holder.item_order_list_left.setVisibility(View.VISIBLE);
                holder.item_order_list_right.setVisibility(View.VISIBLE);
            } else if (datas.get(i).getIsDelivery() == 1) {
                holder.item_order_list_state.setText("待支付");
                if (tag != null && tag.equals(OrderListFragment.ADAPTER_TAG)) {//买家
                    holder.item_order_list_left.setText("去支付");
                    holder.item_order_list_right.setText("取消订单");
                    holder.ll_double_btn.setVisibility(View.VISIBLE);
                    holder.item_order_list_left.setVisibility(View.VISIBLE);
                    holder.item_order_list_right.setVisibility(View.VISIBLE);
                } else if (tag != null && tag.equals(MyShopActivity.ADAPTER_TAG)) {//卖家
                    holder.ll_double_btn.setVisibility(View.GONE);
                }
            }
        } else if ("1".equals(datas.get(i).getPaymentStatus())) {//支付完成的状态
            if (datas.get(i).getIsDelivery() == 0) {//派送
                holder.item_order_list_left.setText("退款");
                if (tag != null && tag.equals(OrderListFragment.ADAPTER_TAG)) {//买家
                    holder.item_order_list_right.setText("联系卖家");
                    holder.item_order_list_state.setText("已支付");
                } else if (tag != null && tag.equals(MyShopActivity.ADAPTER_TAG)) {//卖家
                    holder.item_order_list_right.setText("确认订单");
                    holder.item_order_list_state.setText("待确认");
                }
                holder.ll_double_btn.setVisibility(View.VISIBLE);
                holder.item_order_list_left.setVisibility(View.VISIBLE);
                holder.item_order_list_right.setVisibility(View.VISIBLE);
            } else if (datas.get(i).getIsDelivery() == 1) {//非派送
                holder.item_order_list_left.setText("退款");
                if (tag != null && tag.equals(OrderListFragment.ADAPTER_TAG)) {//买家
                    holder.item_order_list_right.setText("联系卖家");
                    holder.item_order_list_state.setText("待消费");
                    holder.ll_double_btn.setVisibility(View.VISIBLE);
                    holder.item_order_list_left.setVisibility(View.VISIBLE);
                    holder.item_order_list_right.setVisibility(View.VISIBLE);
                } else if (tag != null && tag.equals(MyShopActivity.ADAPTER_TAG)) {//卖家
                    holder.item_order_list_state.setText("待消费");
                    holder.ll_double_btn.setVisibility(View.GONE);
                }
            }
        } else if ("2".equals(datas.get(i).getPaymentStatus())) {
            if (datas.get(i).getIsDelivery() == 0) {
                if (tag != null && tag.equals(OrderListFragment.ADAPTER_TAG)) {//买家
                    holder.item_order_list_left.setText("联系卖家");
                    holder.item_order_list_right.setText("确认完成");
                    holder.item_order_list_state.setText("卖家已确认");
                    holder.ll_double_btn.setVisibility(View.VISIBLE);
                    holder.item_order_list_left.setVisibility(View.VISIBLE);
                    holder.item_order_list_right.setVisibility(View.VISIBLE);
                } else if (tag != null && tag.equals(MyShopActivity.ADAPTER_TAG)) {//卖家
                    holder.item_order_list_state.setText("已确认");
                    holder.ll_double_btn.setVisibility(View.GONE);
                }
            } else if (datas.get(i).getIsDelivery() == 1) {
                holder.item_order_list_state.setText("待消费");
                holder.ll_double_btn.setVisibility(View.GONE);
            }
        } else if ("3".equals(datas.get(i).getPaymentStatus())) {
            if (tag != null && tag.equals(OrderListFragment.ADAPTER_TAG)) {
                if (datas.get(i).getEvaluteStatus() == 0) {//0未评价 1已评价
                    holder.item_order_list_left.setText("评价");
                } else if (datas.get(i).getEvaluteStatus() == 1) {
                    holder.item_order_list_left.setText("查看评价");
                }
                holder.ll_double_btn.setVisibility(View.VISIBLE);
            } else if (tag != null && tag.equals(MyShopActivity.ADAPTER_TAG)) {
                if (datas.get(i).getEvaluteStatus() == 0) {//0未评价 1已评价
                    holder.ll_double_btn.setVisibility(View.GONE);
                } else if (datas.get(i).getEvaluteStatus() == 1) {
                    holder.item_order_list_left.setText("查看评价");
                    holder.ll_double_btn.setVisibility(View.VISIBLE);
                }
            }
            holder.item_order_list_left.setVisibility(View.VISIBLE);
            holder.item_order_list_right.setVisibility(View.GONE);
            holder.item_order_list_state.setText("已完成");
        } else if ("4".equals(datas.get(i).getPaymentStatus())) {
            holder.ll_double_btn.setVisibility(View.GONE);
            holder.item_order_list_state.setText("买家已取消订单");
        } else if ("5".equals(datas.get(i).getPaymentStatus())) {
            holder.ll_double_btn.setVisibility(View.GONE);
            holder.item_order_list_state.setText("卖家已取消订单");
        } else if ("6".equals(datas.get(i).getPaymentStatus())) {//退款中
            holder.ll_double_btn.setVisibility(View.GONE);
            holder.item_order_list_state.setText("退款中");
        } else if ("7".equals(datas.get(i).getPaymentStatus())) {///退款完成
            holder.ll_double_btn.setVisibility(View.GONE);
            holder.item_order_list_state.setText("退款完成");
        } else if ("8".equals(datas.get(i).getPaymentStatus())) {//退款失败
            holder.ll_double_btn.setVisibility(View.GONE);
            holder.item_order_list_state.setText("退款失败");
        }

        holder.item_order_list_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int state = 0;
                switch (datas.get(i).getPaymentStatus()) {
                    case "0":
                        if (tag != null && tag.equals(OrderListFragment.ADAPTER_TAG)) {//买家
                            upDateOrderState(i, 1);//去支付状态
                        } else if (tag != null && tag.equals(MyShopActivity.ADAPTER_TAG)) {//卖家
                            upDateOrderState(i, 5);//商户取消
                        }
                        break;
                    case "1":
                        if (tag != null && tag.equals(OrderListFragment.ADAPTER_TAG)) {//买家
                            showDialogBuy(i);
                        } else if (tag != null && tag.equals(MyShopActivity.ADAPTER_TAG)) {//卖家
                            showDialog(i);//卖家退款
                        }
                        break;
                    case "2":
                        TelePhoneUtils.telePhone(context, datas.get(i).getSellerPhone());
                        break;
                    case "3":
                        if (tag != null && tag.equals(OrderListFragment.ADAPTER_TAG)) {
                            if (datas.get(i).getEvaluteStatus() == 0) {//0未评价 1已评价
                                startNextActivity(i);
                            } else if (datas.get(i).getEvaluteStatus() == 1) {
                                startNextActivity(i);
                            }
                        } else if (tag != null && tag.equals(MyShopActivity.ADAPTER_TAG)) {
                            startNextActivity(i);
                        }
                        break;

                }
            }
        });

        holder.item_order_list_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int state = 0;
                switch (datas.get(i).getPaymentStatus()) {
                    case "0":
                        if (tag != null && tag.equals(OrderListFragment.ADAPTER_TAG)) {//买家
                            upDateOrderState(i, 4);//用户取消
                        } else if (tag != null && tag.equals(MyShopActivity.ADAPTER_TAG)) {//卖家
                            TelePhoneUtils.telePhone(context, datas.get(i).getCustomerPhone());
                        }
                        break;
                    case "1":
                        if (tag != null && tag.equals(OrderListFragment.ADAPTER_TAG)) {
                            TelePhoneUtils.telePhone(context, datas.get(i).getSellerPhone());
                        } else if (tag != null && tag.equals(MyShopActivity.ADAPTER_TAG)) {
                            upDateOrderState(i, 2);
                        }
                        break;
                    case "2":
                        upDateOrderState(i, 3); //买家完成订单
                        break;
                }
            }
        });
        //加载图片
        if (datas.get(i).getBanner() != null && datas.get(i).getBanner().length() > 0) {
            Glide.with(context).load(datas.get(i).getBanner()).error(R.drawable.no_pic).into(holder.item_order_list_iv);
        } else {
            Glide.with(context).load(R.drawable.news).into(holder.item_order_list_iv);
        }
        return view;
    }

    private void startNextActivity(int i) {
        Intent intent = new Intent(context, OrderListEvaluationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", datas.get(i));
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 卖家退款
     *
     * @param position
     */
    private void showDialog(final int position) {
        final CustomDialog.Builder dialog = new CustomDialog.Builder(context);
        dialog.setTitle("退款");
        View view = LayoutInflater.from(context).inflate(R.layout.my_edit_text, null);
        final EditText editText = (EditText) view.findViewById(R.id.et_input_result);
        dialog.setContentView(view);
        dialog.setPositiveButton("确认退款", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                refund(position, editText.getText().toString());//退款的订单号
            }
        });
        dialog.setNegativeButton("联系买家", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TelePhoneUtils.telePhone(context, datas.get(position).getCustomerPhone());
                dialog.dismiss();
            }
        });
        dialog.create().show();
    }

    /**
     * 买家退款
     *
     * @param position
     */
    private void showDialogBuy(final int position) {
        final CustomDialog.Builder dialog = new CustomDialog.Builder(context);
        dialog.setTitle("退款");
        View view = LayoutInflater.from(context).inflate(R.layout.my_edit_text, null);
        final EditText editText = (EditText) view.findViewById(R.id.et_input_result);
        dialog.setContentView(view);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                refund(position, editText.getText().toString());//退款的订单号
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.create().show();
    }

    /**
     * 改变订单状态
     *
     * @param position 点击的item的下标
     * @param state    点击item的状态
     */
    private void upDateOrderState(final int position, final int state) {
        ProgressUtil.show(context, "");
        RequestParams params = new RequestParams(Const.WuYe.URL_ORDER_UPDATE_ORDER_STATE);
//        orderNo：订单号；paymentStatus ：订单状态
        params.addParameter("orderNo", datas.get(position).getOrderNo());
        params.addParameter("paymentStatus", state);
        params.setConnectTimeout(30 * 1000);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Log.e("订单状态改变", result);
                Base base = new Gson().fromJson(result, Base.class);
                if (base.respCode == 1001) {
                    datas.get(position).setPaymentStatus(state + "");
                    notifyDataSetChanged();
                }
                ProgressUtil.close();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ProgressUtil.close();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    class Holder {
        ImageView item_order_list_iv;
        TextView item_order_list_name, item_order_list_goods,
                item_order_list_price, item_order_list_time, item_order_list_state, item_order_list_left, item_order_list_right;
        LinearLayout ll_double_btn;

        public Holder(View view) {
            item_order_list_iv = (ImageView) view.findViewById(R.id.item_order_list_iv);
            item_order_list_name = (TextView) view.findViewById(R.id.item_order_list_name);
            item_order_list_goods = (TextView) view.findViewById(R.id.item_order_list_goods);
            item_order_list_price = (TextView) view.findViewById(R.id.item_order_list_price);
            item_order_list_time = (TextView) view.findViewById(R.id.item_order_list_time);
            item_order_list_state = (TextView) view.findViewById(R.id.item_order_list_state);
            item_order_list_left = (TextView) view.findViewById(R.id.item_order_list_left);
            item_order_list_right = (TextView) view.findViewById(R.id.item_order_list_right);
            ll_double_btn = (LinearLayout) view.findViewById(R.id.ll_double_btn);
        }
    }


    private void refund(final int position, String refundReason) {
        ProgressUtil.show(context, "");
        RequestParams params = new RequestParams(Const.WuYe.URL_ORDER_REFUND);
//        orderNo：订单号；refundReason 退款原因
        params.addParameter("orderNo", datas.get(position).getOrderNo());
        params.addParameter("refundReason", refundReason);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("订单状态改变", result);
                Base base = new Gson().fromJson(result, Base.class);
                if (base.respCode == 1001) {
                    datas.get(position).setPaymentStatus(6 + "");//状态6 为退款中
                    notifyDataSetChanged();
                }
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
    }

}
