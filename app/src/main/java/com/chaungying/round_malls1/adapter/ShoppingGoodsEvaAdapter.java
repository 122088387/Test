package com.chaungying.round_malls1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.round_malls1.bean.ShoppingStoreEvaluationBean;
import com.chaungying.wuye3.R;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/9/23
 */
public class ShoppingGoodsEvaAdapter extends RecyclerView.Adapter<ShoppingGoodsEvaAdapter.MyViewHolder> {

    private static final String TAG = "ShoppingGoodsEvaAdapter";

    private Context mContext;

    private List<ShoppingStoreEvaluationBean.EvaluatesBean> list = new ArrayList<>();

    public ShoppingGoodsEvaAdapter(Context mContext) {
        this.mContext = mContext;
    }


    public void setList(List<ShoppingStoreEvaluationBean.EvaluatesBean> list) {
        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_shopping_store_eva_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.shopping_store_eva_item.setText(list.get(position).getEvaluate_note());
        holder.shopping_store_eva_item_login_name.setText(list.get(position).getLoginName());
        String str = list.get(position).getCreateTime();
        holder.shopping_store_eva_item_time.setText(str.substring(0, str.lastIndexOf(":")));
        if (list.get(position).getProName() != null && !list.get(position).getProName().equals("")) {
            holder.shopping_store_eva_name.setText(list.get(position).getProName());
            holder.shopping_store_eva_name.setVisibility(View.GONE);
        } else {
            holder.shopping_store_eva_name.setVisibility(View.GONE);
        }
        for (int i = 0; i < holder.iv_list.size(); i++) {
            if (i < list.get(position).getProLevel()) {
                holder.iv_list.get(i).setImageResource(R.drawable.star_full);
            } else {
                holder.iv_list.get(i).setImageResource(R.drawable.star_no);
            }
        }
        holder.shopping_store_eva_item_head.setImageResource(R.drawable.default_head);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView shopping_store_eva_item;
        TextView shopping_store_eva_item_login_name;
        TextView shopping_store_eva_item_time;
        TextView shopping_store_eva_name;
        ImageView shopping_store_eva_item_star1;
        ImageView shopping_store_eva_item_star2;
        ImageView shopping_store_eva_item_star3;
        ImageView shopping_store_eva_item_star4;
        ImageView shopping_store_eva_item_star5;
        CircularImageView shopping_store_eva_item_head;
        List<ImageView> iv_list = new ArrayList<>();

        public MyViewHolder(View view) {
            super(view);
            shopping_store_eva_item = (TextView) view.findViewById(R.id.shopping_store_eva_item);
            shopping_store_eva_item_login_name = (TextView) view.findViewById(R.id.shopping_store_eva_item_login_name);
            shopping_store_eva_item_time = (TextView) view.findViewById(R.id.shopping_store_eva_item_time);
            shopping_store_eva_name = (TextView) view.findViewById(R.id.shopping_store_eva_name);
            shopping_store_eva_item_head = (CircularImageView) view.findViewById(R.id.shopping_store_eva_item_head);
            shopping_store_eva_item_star1 = (ImageView) view.findViewById(R.id.shopping_store_eva_item_star1);
            shopping_store_eva_item_star2 = (ImageView) view.findViewById(R.id.shopping_store_eva_item_star2);
            shopping_store_eva_item_star3 = (ImageView) view.findViewById(R.id.shopping_store_eva_item_star3);
            shopping_store_eva_item_star4 = (ImageView) view.findViewById(R.id.shopping_store_eva_item_star4);
            shopping_store_eva_item_star5 = (ImageView) view.findViewById(R.id.shopping_store_eva_item_star5);
            iv_list.add(shopping_store_eva_item_star1);
            iv_list.add(shopping_store_eva_item_star2);
            iv_list.add(shopping_store_eva_item_star3);
            iv_list.add(shopping_store_eva_item_star4);
            iv_list.add(shopping_store_eva_item_star5);
        }
    }

}
