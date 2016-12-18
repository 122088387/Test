package com.chaungying.round_malls1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.round_malls1.bean.ShoppingStoreBean;
import com.chaungying.round_malls1.utils.DistanceUtils;
import com.chaungying.wuye3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/9/22
 */
public class ShoppingStoreAdapter extends BaseAdapter {

    private Context mContext;
    private List<ShoppingStoreBean.DatasBean> list = new ArrayList<>();

    private String longitude = "";
    private String latitude = "";

    public ShoppingStoreAdapter(Context mContext) {
        this.mContext = mContext;
        longitude = (String) SPUtils.get(mContext, Const.SPDate.LONGITHDE, "");
        latitude = (String) SPUtils.get(mContext, Const.SPDate.LATITUDE, "");
    }

    public void setList(List<ShoppingStoreBean.DatasBean> list) {
        this.list = list;
    }

    public List<ShoppingStoreBean.DatasBean> getList() {
        return list;
    }

    public void addDataToList(List<ShoppingStoreBean.DatasBean> list) {
        this.list.addAll(list);
    }

    private static final String TAG = "ShoppingStoreAdapter";

    private BDLocation bdLocation;

    public void setBdLocation(BDLocation bdLocation) {
        this.bdLocation = bdLocation;
    }

//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_shopping_store_item, parent, false);
//        MyViewHolder holder = new MyViewHolder(view);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//
////        holder.shopping_store_item.setImageResource(list.get(position).getName());
//        if (list.get(position).getBanner() != null && !list.get(position).getBanner().equals("")) {
//            Picasso.with(mContext).load(list.get(position).getBanner()).into(holder.shopping_store_item);
//        } else {
//            Picasso.with(mContext).load(R.drawable.login_head).into(holder.shopping_store_item);
//        }
//        holder.round_malls_item_name.setText(list.get(position).getName());
//        if (bdLocation != null) {
//            try {
//                holder.shopping_store_item_dis.setText((int) DistanceUtils.getDistance(bdLocation.getLongitude(), bdLocation.getLatitude(), Double.parseDouble(list.get(position).getLongitude()), Double.parseDouble(list.get(position).getLongitude())) + "米");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//
//        }
////        holder.shopping_store_item_score.setText(list.get(position).getName());
//        holder.shopping_store_sales.setText("销量：" + list.get(position).getSellNum() + "");
//        holder.shopping_store_item_introduce.setText(list.get(position).getIntroduce());
//    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_shopping_store_item, parent, false);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        if (list.get(position).getBanner() != null && !list.get(position).getBanner().equals("")) {
            Picasso.with(mContext).load(list.get(position).getBanner()).error(R.drawable.default_png).into(holder.shopping_store_item);
        } else {
            Picasso.with(mContext).load(R.drawable.default_png).into(holder.shopping_store_item);
        }
        holder.round_malls_item_name.setText(list.get(position).getName());
        if (bdLocation != null) {
            try {
                holder.shopping_store_item_dis.setText((int) DistanceUtils.getDistance(bdLocation.getLongitude(), bdLocation.getLatitude(), Double.parseDouble(list.get(position).getLongitude()), Double.parseDouble(list.get(position).getLatitude())) + "米");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (!longitude.equals("") && !latitude.equals("")) {
            holder.shopping_store_item_dis.setText((int) DistanceUtils.getDistance(Double.parseDouble(longitude), Double.parseDouble(latitude), Double.parseDouble(list.get(position).getLongitude()), Double.parseDouble(list.get(position).getLatitude())) + "米");
            ;
        } else {
            holder.shopping_store_item_dis.setText("0m");
        }
        holder.shopping_store_sales.setText("已售" + list.get(position).getSellNum() + "");
        holder.shopping_store_item_score.setText(list.get(position).getEvaluateLevel() + ".0");//评分
        for (int i = 0; i < holder.iv_list.size(); i++) {
            if (i < Integer.parseInt(list.get(position).getEvaluateLevel())) {
                holder.iv_list.get(i).setImageResource(R.drawable.star_full);
            } else {
                holder.iv_list.get(i).setImageResource(R.drawable.star_no);
            }
        }
        return convertView;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView shopping_store_item;
        TextView round_malls_item_name;
        TextView shopping_store_item_dis;
        ImageView shopping_store_item_score1;
        ImageView shopping_store_item_score2;
        ImageView shopping_store_item_score3;
        ImageView shopping_store_item_score4;
        ImageView shopping_store_item_score5;
        TextView shopping_store_sales;
        TextView shopping_store_item_score;
        //        TextView shopping_store_item_introduce;
//        View gray_line;
        List<ImageView> iv_list = new ArrayList<>();

        public MyViewHolder(View view) {
            super(view);
            shopping_store_item = (ImageView) view.findViewById(R.id.shopping_store_item);
            round_malls_item_name = (TextView) view.findViewById(R.id.round_malls_item_name);
            shopping_store_item_dis = (TextView) view.findViewById(R.id.shopping_store_item_dis);
//            gray_line = view.findViewById(R.id.gray_line);
            shopping_store_item_score1 = (ImageView) view.findViewById(R.id.shopping_store_item_score1);
            shopping_store_item_score2 = (ImageView) view.findViewById(R.id.shopping_store_item_score2);
            shopping_store_item_score3 = (ImageView) view.findViewById(R.id.shopping_store_item_score3);
            shopping_store_item_score4 = (ImageView) view.findViewById(R.id.shopping_store_item_score4);
            shopping_store_item_score5 = (ImageView) view.findViewById(R.id.shopping_store_item_score5);
            iv_list.add(shopping_store_item_score1);
            iv_list.add(shopping_store_item_score2);
            iv_list.add(shopping_store_item_score3);
            iv_list.add(shopping_store_item_score4);
            iv_list.add(shopping_store_item_score5);
            shopping_store_sales = (TextView) view.findViewById(R.id.shopping_store_sales);
            shopping_store_item_score = (TextView) view.findViewById(R.id.shopping_store_item_score);
//            shopping_store_item_introduce = (TextView) view.findViewById(R.id.shopping_store_item_introduce);
        }
    }

}
