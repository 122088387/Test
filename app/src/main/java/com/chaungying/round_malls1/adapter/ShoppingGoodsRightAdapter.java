package com.chaungying.round_malls1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.round_malls.interface_.AddSubStractBtnListtener;
import com.chaungying.round_malls.interface_.ReturnShoppingTotalPirceListener;
import com.chaungying.round_malls.utils.BigDecimalUtils;
import com.chaungying.round_malls.view.AddSubtractBtnView;
import com.chaungying.round_malls1.bean.ShoppingGoodsBean;
import com.chaungying.round_malls1.utils.SpannableUtils;
import com.chaungying.wuye3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/9/23
 */
public class ShoppingGoodsRightAdapter extends RecyclerView.Adapter<ShoppingGoodsRightAdapter.MyViewHolder> implements AddSubStractBtnListtener {

    private static final String TAG = "ShoppingGoodsRightAdapter";

    private Context mContext;

    private List<ShoppingGoodsBean.ProductsBean> list = new ArrayList<>();

    public ShoppingGoodsRightAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setToolPrice(String toolPrice) {
        this.toolPrice = toolPrice;
    }

    public void setList(List<ShoppingGoodsBean.ProductsBean> list) {
        this.list = list;
    }

    private String toolPrice = "0.00";

    private ReturnShoppingTotalPirceListener returnPirceListener;

    public void setReturnPirceListener(ReturnShoppingTotalPirceListener returnPirceListener) {
        this.returnPirceListener = returnPirceListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_shopping_goods_right_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String str = list.get(position).getProLogo();
        if (str != null && str.length() > 0) {
            str = str.replace("\\", "/");
            Picasso.with(mContext).load(str).into(holder.shopping_goods_right_item);
        } else {
            Picasso.with(mContext).load(R.drawable.no_pic).into(holder.shopping_goods_right_item);
        }
        holder.round_malls_item_name.setText(list.get(position).getProName());
        if (list.get(position).getProDetails() != null) {
            holder.shopping_store_item_describe.setText(list.get(position).getProDetails() + "");
            holder.shopping_store_item_describe.setVisibility(View.GONE);
        } else {
            holder.shopping_store_item_describe.setText("");
            holder.shopping_store_item_describe.setVisibility(View.GONE);
        }
        holder.shopping_store_item_pirce.setText(SpannableUtils.setTextSize("￥" + BigDecimalUtils.rounding(list.get(position).getPrice()),0,1,26));
        holder.shopping_store_item_sellerNum.setText("月售" + list.get(position).getSellerSum());
        holder.shopping_store_item_evalGoodPercent.setText("好评率" + list.get(position).getEvalGoodPercent());

        holder.shopping_goods_right_item_add.setNum(list.get(position).getShoppingNum());
        holder.shopping_goods_right_item_add.setId(position);
        holder.shopping_goods_right_item_add.setAddSubStractBtnListtener(this);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void getNumListtener(AddSubtractBtnView view, int num) {
        for (int i = 0; i < list.size(); i++) {
            if (i == view.getId()) {
                if (view.getView().getId() == R.id.shopping_add) {
                    toolPrice = BigDecimalUtils.jia(toolPrice, list.get(i).getPrice() + "");
                } else if (view.getView().getId() == R.id.shopping_substract) {
                    toolPrice = BigDecimalUtils.jian(toolPrice, list.get(i).getPrice() + "");
                }
                //将对应bean中的数量设置成改变后的  num
                list.get(i).setShoppingNum(num);
                if (returnPirceListener != null) {
                    returnPirceListener.returnTotalPirce(toolPrice);
                }
            }
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView shopping_goods_right_item;
        TextView round_malls_item_name;
        TextView shopping_store_item_sellerNum;
        TextView shopping_store_item_evalGoodPercent;
        TextView shopping_store_item_describe;
        TextView shopping_store_item_pirce;
        AddSubtractBtnView shopping_goods_right_item_add;

        public MyViewHolder(View view) {
            super(view);
            shopping_goods_right_item = (ImageView) view.findViewById(R.id.shopping_goods_right_item);
            round_malls_item_name = (TextView) view.findViewById(R.id.round_malls_item_name);
            shopping_store_item_sellerNum = (TextView) view.findViewById(R.id.shopping_store_item_sellerNum);
            shopping_store_item_evalGoodPercent = (TextView) view.findViewById(R.id.shopping_store_item_evalGoodPercent);
            shopping_store_item_describe = (TextView) view.findViewById(R.id.shopping_store_item_describe);
            shopping_store_item_pirce = (TextView) view.findViewById(R.id.shopping_store_item_pirce);
            shopping_goods_right_item_add = (AddSubtractBtnView) view.findViewById(R.id.shopping_goods_right_item_add);
        }
    }

}
