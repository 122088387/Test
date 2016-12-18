package com.chaungying.site_repairs.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.chaungying.site_repairs.ui.PicShowActivity;
import com.chaungying.wuye3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * @author 王晓赛 or 2016/7/13
 *         <p/>
 *         对报修详情界面，图片的展示的适配器
 */
public class ShowPicAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<String> bitList = new ArrayList<String>();

    public void setBitList(ArrayList<String> bitList) {
        this.bitList = bitList;
    }

    public ShowPicAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return bitList.size();
    }

    @Override
    public Object getItem(int position) {
        return bitList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.repairs_record_details_picture_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.item_grida_image_pic);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (bitList.get(position) != null && bitList.get(position).length() > 0) {
            Picasso.with(mContext).load(bitList.get(position)).error(R.drawable.image_load_error).into(holder.imageView);
        } else {
            Picasso.with(mContext).load(R.drawable.no_pic).into(holder.imageView);
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果有照片跳入到下一个界面
                if (bitList.get(position) != null && bitList.get(position).length() > 0) {
                    Intent intent = new Intent(mContext,
                            PicShowActivity.class);
                    intent.putExtra("ID", position);
                    intent.putExtra("URL", bitList);
                    mContext.startActivity(intent);
                }
            }
        });
        return convertView;
    }


    class ViewHolder {
        ImageView imageView;
    }
}
