package com.chaungying.round_malls1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chaungying.round_malls1.bean.PayMoneyGoodsBean;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：  王晓赛
 * 时  间： 2016/10/10
 */

public class PayMoneyGoodsAdapter extends BaseAdapter {

    List<PayMoneyGoodsBean> payMoneyGoodsBeanList = new ArrayList<>();

    private Context mContext;

    public PayMoneyGoodsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setPayMoneyGoodsBeanList(List<PayMoneyGoodsBean> payMoneyGoodsBeanList) {
        this.payMoneyGoodsBeanList = payMoneyGoodsBeanList;
    }

    @Override
    public int getCount() {
        return payMoneyGoodsBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return payMoneyGoodsBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_pay_money_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.pay_money_item_name.setText(payMoneyGoodsBeanList.get(i).getName());
        holder.pay_money_item_price.setText("￥" + payMoneyGoodsBeanList.get(i).getPrice());
        holder.pay_money_item_num.setText("×" + payMoneyGoodsBeanList.get(i).getNum());

        return view;
    }

    class ViewHolder {

        TextView pay_money_item_name;
        TextView pay_money_item_price;
        TextView pay_money_item_num;

        ViewHolder(View view) {
            pay_money_item_name = (TextView) view.findViewById(R.id.pay_money_item_name);
            pay_money_item_price = (TextView) view.findViewById(R.id.pay_money_item_price);
            pay_money_item_num = (TextView) view.findViewById(R.id.pay_money_item_num);
        }

    }
}
