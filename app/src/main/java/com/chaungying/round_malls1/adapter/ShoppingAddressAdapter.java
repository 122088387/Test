package com.chaungying.round_malls1.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaungying.common.constant.Base;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.T;
import com.chaungying.common.view.CustomDialog;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.round_malls1.bean.ShoppingAddressBean;
import com.chaungying.round_malls1.ui.AddShippingAddressActivity;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/9/23
 */
public class ShoppingAddressAdapter extends RecyclerView.Adapter<ShoppingAddressAdapter.MyViewHolder> {


    private Context mContext;

    private List<ShoppingAddressBean.DatasBean> list = new ArrayList<>();

    public ShoppingAddressAdapter(Context mContext) {
        this.mContext = mContext;
    }


    public void setList(List<ShoppingAddressBean.DatasBean> list) {
        this.list = list;
    }

    private String isRoundMalls = "";

    public void setIsRoundMalls(String isRoundMalls) {
        this.isRoundMalls = isRoundMalls;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_shipping_address_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.shipping_address_address.setText(list.get(position).getReceiverAddress() + " " + list.get(position).getDoorNumber());
        holder.shipping_address_name.setText(list.get(position).getReceiverName());
        holder.shipping_address_phoneNum.setText(list.get(position).getReceiverPhone());

        if ("0".equals(list.get(position).getReceiverEnable())) {
            holder.shipping_address_default_img.setImageResource(R.drawable.select);
            holder.shipping_address_name.setTextColor(mContext.getResources().getColor(R.color.red_fd4420));
            holder.shipping_address_phoneNum.setTextColor(mContext.getResources().getColor(R.color.red_fd4420));
        } else {
            holder.shipping_address_default_img.setImageResource(R.drawable.select_no);
            holder.shipping_address_name.setTextColor(mContext.getResources().getColor(R.color.black1));
            holder.shipping_address_phoneNum.setTextColor(mContext.getResources().getColor(R.color.black1));
        }
        //编辑布局
        holder.rl_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddShippingAddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", list.get(position));
                if (isRoundMalls.equals("是")) {
                    bundle.putString("isRoundMalls", "是");
                } else {
                    bundle.putString("isRoundMalls", "不是");
                }
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        //删除布局
        holder.rl_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
                builder.setTitle("提示");
                builder.setMessage("是否删除此地址");
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ProgressUtil.show(mContext, "删除中...");
                        RequestParams params = new RequestParams(Const.WuYe.URL_RECEIVER_DEL_RECEIVER);
                        params.addParameter("receiverId", list.get(position).getReceiverId());
                        //不是周边商城
                        if (!isRoundMalls.equals("是")) {
                            params.addParameter("type", 2);
                        }
                        x.http().post(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                ProgressUtil.close();
                                Base base = new Gson().fromJson(result, Base.class);
                                if (base.respCode == 1001) {
                                    list.remove(position);
                                    notifyDataSetChanged();
                                }
                                T.showShort(mContext, "删除"+base.respMsg);
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
                        builder.dismiss();
                    }
                });
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        builder.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView shipping_address_default_img;
        TextView shipping_address_address;
        TextView shipping_address_name;
        TextView shipping_address_phoneNum;
        RelativeLayout rl_22;//编辑
        RelativeLayout rl_11;//删除

        public MyViewHolder(View view) {
            super(view);
            shipping_address_default_img = (ImageView) view.findViewById(R.id.shipping_address_default_img);
            shipping_address_address = (TextView) view.findViewById(R.id.shipping_address_address);
            shipping_address_name = (TextView) view.findViewById(R.id.shipping_address_name);
            shipping_address_phoneNum = (TextView) view.findViewById(R.id.shipping_address_phoneNum);
            rl_22 = (RelativeLayout) view.findViewById(R.id.rl_22);
            rl_11 = (RelativeLayout) view.findViewById(R.id.rl_11);
        }
    }

}
