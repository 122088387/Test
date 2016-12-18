package com.chaungying.round_malls1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chaungying.round_malls1.bean.ShoppingGoodsBean;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/9/23
 */
public class ShoppingGoodsLeftAdapter extends RecyclerView.Adapter<ShoppingGoodsLeftAdapter.MyViewHolder> {

    private static final String TAG = "ShoppingGoodsRightAdapter";

    private Context mContext;

    private List<ShoppingGoodsBean.ProductClassesBean> list = new ArrayList<>();

    public ShoppingGoodsLeftAdapter(Context mContext) {
        this.mContext = mContext;
    }


    public void setList(List<ShoppingGoodsBean.ProductClassesBean> list) {
        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_shopping_goods_left_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.shopping_goods_left_type.setText(list.get(position).getClassName());
        if (list.get(position).isColor()) {
//            holder.shopping_goods_left_type.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.shopping_goods_left_type.setBackgroundColor(mContext.getResources().getColor(R.color.White));
//            holder.left_line.setVisibility(View.VISIBLE);
        } else {
//            holder.shopping_goods_left_type.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.shopping_goods_left_type.setBackgroundColor(mContext.getResources().getColor(R.color.color_f8f8f8));
//            holder.left_line.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView shopping_goods_left_type;
//        TextView left_line;

        public MyViewHolder(View view) {
            super(view);
            shopping_goods_left_type = (TextView) view.findViewById(R.id.shopping_goods_left_type);
//            left_line = (TextView) view.findViewById(left_line);
        }
    }

}
