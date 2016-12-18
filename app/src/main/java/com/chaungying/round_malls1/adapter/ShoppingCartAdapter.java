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
import com.chaungying.round_malls1.listener.SelectListener;
import com.chaungying.round_malls1.utils.SpannableUtils;
import com.chaungying.wuye3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/9/23
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder> implements AddSubStractBtnListtener {

    private static final String TAG = "ShoppingCartAdapter";

    private Context mContext;

    private List<ShoppingGoodsBean.ProductsBean> list = new ArrayList<>();

    private String toolPrice = "0.00";

    public void setToolPrice(String toolPrice) {
        this.toolPrice = toolPrice;
    }

    private ReturnShoppingTotalPirceListener returnPirceListener;

    public void setReturnPirceListener(ReturnShoppingTotalPirceListener returnPirceListener) {
        this.returnPirceListener = returnPirceListener;
    }

    public ShoppingCartAdapter(Context mContext) {
        this.mContext = mContext;
    }


    public void setList(List<ShoppingGoodsBean.ProductsBean> list) {
        this.list = list;
    }

    public List<ShoppingGoodsBean.ProductsBean> getList() {
        return list;
    }

    private SelectListener listener;

    public void setListener(SelectListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_shopping_cart_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String str = list.get(position).getProLogo();
        if (str != null && str.length() > 0) {
            str = str.replace("\\", "/");
            Picasso.with(mContext).load(str).into(holder.shopping_cart_item_image);
        } else {
            Picasso.with(mContext).load(R.drawable.no_pic).into(holder.shopping_cart_item_image);
        }
        holder.shopping_cart_item_name.setText(list.get(position).getProName());
        holder.shopping_cart_item_price.setText(SpannableUtils.setTextSize("￥" + list.get(position).getPrice(), 0, 1, 26));
        holder.shopping_cart_add_sub_view.setNum(list.get(position).getShoppingNum());
        if (list.get(position).getShoppingNum() != 0) {
            holder.shopping_cart_add_sub_view.setSubVisable(View.VISIBLE);
        }
//        holder.shopping_cart_item_num.setText("×"+ list.get(position).getShoppingNum());
        holder.shopping_cart_add_sub_view.setId(position);
        holder.shopping_cart_add_sub_view.setAddSubStractBtnListtener(this);

        if (list.get(position).isCheck()) {
            holder.shopping_cart_item_select.setImageResource(R.drawable.select);
        } else {
            holder.shopping_cart_item_select.setImageResource(R.drawable.select_no);
        }
        holder.shopping_cart_item_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(position).isCheck()) {
                    list.get(position).setCheck(false);
                    holder.shopping_cart_item_select.setImageResource(R.drawable.select_no);
                } else {
                    list.get(position).setCheck(true);
                    holder.shopping_cart_item_select.setImageResource(R.drawable.select);
                }
                if (listener != null) {
                    listener.selectListener(calculatePrice());
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void getNumListtener(AddSubtractBtnView view, int num) {
        for (int i = 0; i < list.size(); i++) {
            if (i == view.getId()) {
                //将对应bean中的数量设置成改变后的num
                list.get(i).setShoppingNum(num);
                if (num == 0) {
                    list.get(i).setCheck(false);
                }
                if (returnPirceListener != null) {
                    returnPirceListener.returnTotalPirce(calculatePrice());
                }
                notifyDataSetChanged();
            }

        }
    }

    /**
     * 刷新数据时计算价格
     *
     * @return
     */
    public String calculatePrice() {
        String price = "0.00";
        for (ShoppingGoodsBean.ProductsBean bean : list) {
            if (bean.isCheck()) {
                String chengji = BigDecimalUtils.cheng(bean.getPrice(), bean.getShoppingNum() + "");
                price = BigDecimalUtils.jia(price, chengji);
            }
        }
        return price;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView shopping_cart_item_select;
        ImageView shopping_cart_item_image;
        TextView shopping_cart_item_name;
        TextView shopping_cart_item_price;
        //        TextView shopping_cart_item_num;
        AddSubtractBtnView shopping_cart_add_sub_view;

        public MyViewHolder(View view) {
            super(view);
            shopping_cart_item_name = (TextView) view.findViewById(R.id.shopping_cart_item_name);
            shopping_cart_item_select = (ImageView) view.findViewById(R.id.shopping_cart_item_select);
            shopping_cart_item_image = (ImageView) view.findViewById(R.id.shopping_cart_item_image);
            shopping_cart_item_price = (TextView) view.findViewById(R.id.shopping_cart_item_price);
//            shopping_cart_item_num = (TextView) view.findViewById(R.id.shopping_cart_item_num);
            shopping_cart_add_sub_view = (AddSubtractBtnView) view.findViewById(R.id.shopping_cart_add_sub_view);
        }
    }
}
