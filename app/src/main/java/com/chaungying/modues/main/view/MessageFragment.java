package com.chaungying.modues.main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chaungying.wuye3.R;

/**
 * 消息界面
 * @anthor 王晓赛 or 2016/6/22
 */
public class MessageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg,null);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText("工作台");
        return view;
    }

}
