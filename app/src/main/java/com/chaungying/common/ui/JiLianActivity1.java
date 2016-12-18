package com.chaungying.common.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.chaungying.common.bean.TDistrict;
import com.chaungying.common.utils.HanziToPinyin;
import com.chaungying.common.utils.T;
import com.chaungying.common.view.SideBar;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.wuye3.R;
import com.chaungying.zixunjieda.ui.ZiXunJieDaConfigActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static com.chaungying.wuye3.R.id.title_back;

/**
 * @author 王晓赛 or 2016/6/25
 *         进入级联界面
 */

@ContentView(R.layout.activity_jilian1)
public class JiLianActivity1 extends AppCompatActivity implements SideBar.OnTouchingLetterChangedListener {

    @ViewInject(R.id.lv_jilian)
    private ListView lv_jilian;
    @ViewInject(R.id.btn_ok)
    private Button btn_ok;
    private MyAdapter adapter;
    @ViewInject(title_back)
    private ImageView iv_back;

    //滑动导航栏
    @ViewInject(R.id.contact_hint)
    private SideBar sideBar;

    /**
     * 快速检索提示框
     */
    @ViewInject(R.id.contact_dialog)
    private TextView mDialog;


    @ViewInject(R.id.title)
    private TextView title;

    ArrayList<TDistrict> moreBeanList = new ArrayList<>();

    //从现场报修界面传送过来展示的数据，用于默认选择的使用
    private String show;

    private String url = "";

    private int appId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 19) {
            //沉浸式状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        x.view().inject(this);
        initView();
        initData();
    }

    private void initData() {
        ProgressUtil.show(this, "加载中...");
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(30 * 1000);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //对多选中获取的json进行工作
                Type type = new TypeToken<ArrayList<TDistrict>>() {
                }.getType();
                Gson gson = new Gson();
                moreBeanList = gson.fromJson(result, type);
                if (moreBeanList.size() > 0) {//从服务器能够获取到数据
                    //通过循环 ，将传过来的字符串与获取的数据匹配，匹配到后，设置为true选中状态
                    for (int i = 0; i < moreBeanList.size(); i++) {
                        if (moreBeanList.get(i).getName().equals(show)) {
                            states.put(String.valueOf(i), true);
                            singSelect = show;
                            val = moreBeanList.get(i).getVal();
                        }
                    }
                    //设置拼音之后进行排序
                    for (int i = 0; i < moreBeanList.size(); i++) {
                        moreBeanList.get(i).setPinyin(HanziToPinyin.getPinYin(moreBeanList.get(i).getName()));
                    }
                    Collections.sort(moreBeanList);
                } else {
                    T.showShort(JiLianActivity1.this, "没有数据");
                }
                ProgressUtil.close();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ProgressUtil.close();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                //更新适配器
                adapter.notifyDataSetChanged();
            }

        });
    }

    private void initView() {
        adapter = new MyAdapter();
        lv_jilian.setAdapter(adapter);
        //设置title Bar
        iv_back.setImageResource(R.drawable.nav_return);
        appId = getIntent().getExtras().getInt("appId");
        url = getIntent().getExtras().getString("louyu");
        show = getIntent().getExtras().getString("show");
        String title = getIntent().getExtras().getString("title");
        this.title.setText(title.contains("*") ? title.substring(0, title.indexOf("*")) : title);

        if(appId == 106){
            sideBar.setVisibility(View.VISIBLE);
        }
        sideBar.setTextView(mDialog);
        sideBar.setOnTouchingLetterChangedListener(this);
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        int position = 1;
        // 该字母首次出现的位置
        if (adapter != null) {
            position = adapter.getPositionForSection(s.charAt(0));
        }
        if (!s.contains("#")) {
            lv_jilian.setSelection(position);
        } else if (s.contains("#")) {
            lv_jilian.setSelection(0);
        }
    }

    private String singSelect = "";
    private static final int CASE_RESULT = 5;
    private int val = 0;

    /**
     * 选择确定按钮，将数据传回到现场报修界面
     *
     * @param view
     */
    @Event(value = R.id.btn_ok)
    private void btnOk(View view) {

    }

    // 用于记录每个RadioButton的状态，并保证只可选一个
    HashMap<String, Boolean> states = new HashMap<String, Boolean>();

    class MyAdapter extends BaseAdapter implements SectionIndexer {

        @Override
        public int getCount() {
            return moreBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return moreBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object[] getSections() {
            return null;
        }

        @Override
        public int getPositionForSection(int i) {
            for (int j = 0; j < getCount(); j++) {
                char firstChar = moreBeanList.get(j).firstChar;
                if (firstChar == i) {
                    return j;
                }
            }
            return -1;
        }

        @Override
        public int getSectionForPosition(int i) {
            TDistrict item = moreBeanList.get(i);
            return item.firstChar;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
//            String bean = beans[position];
            LayoutInflater inflater = getLayoutInflater();
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(
                        R.layout.activity_jilian_item, null);
                holder.rb_state = (RadioButton) convertView.findViewById(R.id.rb_jilian);
                ;
                holder.tvLetter = (TextView) convertView.findViewById(R.id.item_contact_list_catalog);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if(appId == 106){
                //如果是第0个那么一定显示#号
                if (position == 0) {
                    holder.tvLetter.setVisibility(View.VISIBLE);
                    holder.tvLetter.setText("" + moreBeanList.get(position).firstChar);
                } else {
                    //如果和上一个item的首字母不同，则认为是新分类的开始
                    char a = moreBeanList.get(position).firstChar;
                    char b = moreBeanList.get(position - 1).firstChar;
                    if (a != b) {
                        holder.tvLetter.setVisibility(View.VISIBLE);
                        holder.tvLetter.setText("" + moreBeanList.get(position).firstChar);
                    } else {
                        holder.tvLetter.setVisibility(View.GONE);
                    }
                }
            }


            holder.rb_state.setText(moreBeanList.get(position).getName());
            holder.rb_state.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // 重置，确保最多只有一项被选中
                    for (String key : states.keySet()) {
                        states.put(key, false);
                    }
                    states.put(String.valueOf(position), holder.rb_state.isChecked());
                    singSelect = holder.rb_state.getText().toString();
                    val = moreBeanList.get(position).getVal();
                    adapter.notifyDataSetChanged();

                    returnData();
                    finish();
                }
            });

            //为了防止list复用的发生
            boolean res = false;
            if (states.get(String.valueOf(position)) == null || states.get(String.valueOf(position)) == false) {
                res = false;
                states.put(String.valueOf(position), false);
            } else
                res = true;

            holder.rb_state.setChecked(res);
            return convertView;
        }

        class ViewHolder {
            TextView tvLetter;
            RadioButton rb_state;
        }
    }


    @Event(value = title_back)
    private void IvBack(View view) {
        returnData();
        finish();
    }

    @Override
    public void onBackPressed() {
        returnData();
        finish();
    }

    public void returnData() {
        Intent intent = new Intent();
        intent.setClass(JiLianActivity1.this, ZiXunJieDaConfigActivity.class);
        Bundle bunlde = new Bundle();
        bunlde.putSerializable("select", singSelect);
        bunlde.putInt("val", val);
        intent.putExtras(bunlde);
        setResult(CASE_RESULT, intent);
    }
}
