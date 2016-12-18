package com.chaungying.round_malls.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chaungying.round_malls.bean.RoundMallsBean;
import com.chaungying.round_malls.interface_.AddSubStractBtnListtener;
import com.chaungying.round_malls.interface_.ReturnShoppingTotalPirceListener;
import com.chaungying.round_malls.utils.BigDecimalUtils;
import com.chaungying.round_malls.view.AddSubtractBtnView;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/9/22
 */
public class RoundMallsAdapter extends RecyclerView.Adapter<RoundMallsAdapter.MyViewHolder> implements AddSubStractBtnListtener {

    private Context mContext;
    private List<RoundMallsBean> list = new ArrayList<>();

    public RoundMallsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<RoundMallsBean> list) {
        this.list = list;
    }

    private String toolPrice = "0.00";

    private static final String TAG = "ShoppingStoreAdapter";

    private ReturnShoppingTotalPirceListener returnPirceListener;

    public void setReturnPirceListener(ReturnShoppingTotalPirceListener returnPirceListener) {
        this.returnPirceListener = returnPirceListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_round_malls_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.round_malls_item_name.setText(list.get(position).getName());
        holder.round_malls_item_price.setText("￥：" + BigDecimalUtils.rounding(list.get(position).getPrice() + ""));
        holder.addSubtractBtnView.setNum(list.get(position).getShoppingNum());
        holder.addSubtractBtnView.setId(position);
        holder.addSubtractBtnView.setAddSubStractBtnListtener(this);
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
                //将对应bean中的数量设置成改变后的num
                list.get(i).setShoppingNum(num);
                try {
                    returnPirceListener.returnTotalPirce(toolPrice);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @return 返回当前的总价格
     */
    public String toolPrice() {
        return toolPrice;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView round_malls_item_name;
        TextView round_malls_item_price;
        AddSubtractBtnView addSubtractBtnView;

        public MyViewHolder(View view) {
            super(view);
            round_malls_item_name = (TextView) view.findViewById(R.id.round_malls_item_name);
            round_malls_item_price = (TextView) view.findViewById(R.id.round_malls_item_price);
            addSubtractBtnView = (AddSubtractBtnView) view.findViewById(R.id.item_add_sub);
        }
    }

}
