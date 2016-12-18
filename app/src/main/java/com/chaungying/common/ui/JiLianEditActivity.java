package com.chaungying.common.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.common.bean.JiLianEditBean;
import com.chaungying.common.utils.T;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.wuye3.R;
import com.chaungying.zixunjieda.ui.ZiXunJieDaConfigActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 创建者：  王晓赛
 * 时  间： 2016/11/23
 */
@ContentView(R.layout.activity_jilian_edit)
public class JiLianEditActivity extends BaseActivity {

    private String url;

    @ViewInject(R.id.lv_jilian_edit)
    private ListView lv_jilian_edit;

    private JiLianEditAdapter adapter;

    private List<JiLianEditBean> jiLianEditBeanList = new ArrayList<>();

    @ViewInject(R.id.title)
    private TextView title;

    //从现场报修界面传送过来展示的数据，用于默认选择的使用
    private String show;

    public static final int CASE_RESULT = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBar("", R.drawable.nav_return, 0);
        url = getIntent().getExtras().getString("url");
        adapter = new JiLianEditAdapter();
        lv_jilian_edit.setAdapter(adapter);

        show = getIntent().getExtras().getString("show");
        this.title.setText(getIntent().getExtras().getString("title"));

        getData();
    }

    private void getData() {
        jiLianEditBeanList.clear();
        if (url != null && !"".equals(url)) {
            ProgressUtil.show(this,"加载中...");
            RequestParams params = new RequestParams(url);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Type type = new TypeToken<ArrayList<JiLianEditBean>>() {
                    }.getType();
                    Gson gson = new Gson();
                    jiLianEditBeanList.addAll((Collection<? extends JiLianEditBean>) gson.fromJson(result, type));
                    if (jiLianEditBeanList.size() > 0) {//从服务器能够获取到数据
                        //通过循环 ，将传过来的字符串与获取的数据匹配，匹配到后，设置为true选中状态
                        for (int i = 0; i < jiLianEditBeanList.size(); i++) {
                            if (jiLianEditBeanList.get(i).getName().equals(show)) {
                                states.put(String.valueOf(i), true);
                                singSelect = show;
                            }
                        }
                    } else {
                        T.showShort(JiLianEditActivity.this, "没有数据");
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    ProgressUtil.close();
                }
            });
        }
    }

    // 用于记录每个RadioButton的状态，并保证只可选一个
    HashMap<String, Boolean> states = new HashMap<String, Boolean>();
    private String singSelect = "";

    class JiLianEditAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return jiLianEditBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return jiLianEditBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            LayoutInflater inflater = getLayoutInflater();
            if (convertView == null) {
                convertView = inflater.inflate(
                        R.layout.activity_jilian_item, null);
                holder = new ViewHolder();
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final RadioButton radio = (RadioButton) convertView.findViewById(R.id.rb_jilian);
            holder.rb_state = radio;
            holder.rb_state.setText(jiLianEditBeanList.get(position).getName());

            holder.rb_state.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // 重置，确保最多只有一项被选中
                    for (String key : states.keySet()) {
                        states.put(key, false);

                    }
                    states.put(String.valueOf(position), radio.isChecked());
                    singSelect = holder.rb_state.getText().toString();
                    adapter.notifyDataSetChanged();

                    Intent intent = new Intent();
                    intent.setClass(JiLianEditActivity.this, ZiXunJieDaConfigActivity.class);
                    Bundle bunlde = new Bundle();
                    bunlde.putSerializable("select", singSelect);
                    intent.putExtras(bunlde);
                    setResult(CASE_RESULT, intent);
                    finish();
                }
            });


            //为了防止list复用的发生
            boolean res;
            if (states.get(String.valueOf(position)) == null || states.get(String.valueOf(position)) == false) {
                res = false;
                states.put(String.valueOf(position), false);
            } else
                res = true;

            holder.rb_state.setChecked(res);
            return convertView;
        }

        class ViewHolder {
            RadioButton rb_state;
        }
    }
}
