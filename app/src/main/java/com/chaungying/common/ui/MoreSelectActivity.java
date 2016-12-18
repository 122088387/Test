package com.chaungying.common.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.chaungying.BaseActivity;
import com.chaungying.common.bean.MoreSelectBean;
import com.chaungying.wuye3.R;
import com.chaungying.zixunjieda.ui.ZiXunJieDaConfigActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/20.
 */
@ContentView(R.layout.more_select_layout1)
public class MoreSelectActivity extends BaseActivity {

    @ViewInject(R.id.lv_more_select)
    ListView listView;
    @ViewInject(R.id.btn_ok)
    Button bntn_ok;

    ArrayList<MoreSelectBean> selectBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        //对标题的设置
//        setActionBar(R.string.baoxiudan, R.drawable.btn_homeasup_default, 0);
        selectBean = (ArrayList<MoreSelectBean>) getIntent().getExtras().getSerializable("subtitles");
        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    private static final int CASE_VIDEO = 10;

    @Event(value = R.id.btn_ok)
    private void QueDing(View view) {
//        ToastShow.toastShow(this,"可以");
        Intent intent = new Intent();
        intent.setClass(this, ZiXunJieDaConfigActivity.class);
        Bundle bunlde = new Bundle();
        bunlde.putSerializable("subtitles1", selectBean);
        intent.putExtras(bunlde);
        setResult(CASE_VIDEO, intent);
        finish();
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return selectBean.size();
        }

        @Override
        public Object getItem(int position) {
            return selectBean.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            MoreSelectBean brandItemInfo = (MoreSelectBean) getItem(position);
            if (convertView != null) {
                holder = (ViewHolder) convertView.getTag();
                holder.checkBox.setTag(brandItemInfo);
            } else {
                convertView = getLayoutInflater().inflate(R.layout.more_select_layout1_item, null);
                holder = new ViewHolder();
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb_more_select_item);
                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        MoreSelectBean info = (MoreSelectBean) holder.checkBox.getTag();
                        info.setChecked(buttonView.isChecked());
                    }
                });
                convertView.setTag(holder);
                holder.checkBox.setTag(brandItemInfo);
            }
            holder.checkBox.setText(selectBean.get(position).getInfo());
            holder.checkBox.setChecked(selectBean.get(position).isChecked());
            return convertView;
        }

        class ViewHolder {
            CheckBox checkBox;
        }
    }
}
