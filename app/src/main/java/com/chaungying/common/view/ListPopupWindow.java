package com.chaungying.common.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chaungying.common.bean.SubtitlesItem;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/7/27
 */
public class ListPopupWindow extends PopupWindow implements AdapterView.OnItemClickListener {
    private Context mContext;
    private MyListView myListView;
    private List<SubtitlesItem> subList  = new ArrayList<SubtitlesItem>();
    private String TAG = "";
    private onSendDismissMsg listener;


    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public void setSubList(List<SubtitlesItem> subList) {
        this.subList = subList;
    }

    public ListPopupWindow(Context context) {
        super(context);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_popup_window,null);
        myListView = (MyListView) view.findViewById(R.id.my_list_view);
        myListView.setListViewHeight(480);
        myListView.setAdapter(new MyAdapter());
        myListView.setOnItemClickListener(this);
        setPupopStyle(context,view);
    }


    private void setPupopStyle(Context context,View view) {
        //设置SelectPicPopupWindow的View
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
//        this.setHeight(mContext.getWindowManager().getDefaultDisplay().getHeight() / 15 * 5);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
//        this.setAnimationStyle(R.style.AnimBottom);
    }



    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return subList.size();
        }

        @Override
        public Object getItem(int position) {
            return subList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if(convertView == null){
                view = LayoutInflater.from(mContext).inflate(R.layout.list_popup_window_item,null);
                TextView tv = (TextView) view.findViewById(R.id.tv_list_popup_window_item);
                tv.setText(subList.get(position).getName());
                for (int i = 0; i < subList.size(); i++) {
                    if(subList.get(position).getName().equals(TAG)){
                        tv.setTextColor(Color.RED);
                        break;
                    }
                }
            }else{
                view  = convertView;
            }
            return view;
        }
    }

    public void setListener(onSendDismissMsg listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tv = (TextView) view.findViewById(R.id.tv_list_popup_window_item);
        String msg = tv.getText().toString();
        listener.send(msg);
        dismiss();
    }


    public interface onSendDismissMsg{
       void send(String str);
    }
}
