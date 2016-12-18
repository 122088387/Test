package com.chaungying.ji_xiao.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chaungying.BaseActivity;
import com.chaungying.ji_xiao.adapter.JobPerformanceRootAdapter;
import com.chaungying.wuye3.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 工作绩效
 *
 * @author 种耀淮 在2016年09月08日10:38创建
 */
@ContentView(R.layout.activity_job_performance_root)
public class JobPerformanceActivity extends BaseActivity {

    @ViewInject(R.id.jobPerformanceRoot_list)
    private ListView listView;

    private JobPerformanceRootAdapter adapter;

    private String[] items = {"员工绩效", "图表"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        createView();
    }

    /**
     * 初始化
     */
    private void createView() {
        // 初始化Title
        setActionBar("工作绩效", 0, 0);
        // 初始化列表
        adapter = new JobPerformanceRootAdapter(this, items);
        listView.setAdapter(adapter);
        // 列表点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:// 员工绩效
                        startJobPerformance();
                        break;
                    case 1:// 图表
                        startChart();
                        break;
                }
            }
        });
    }

    /**
     * 跳转员工绩效
     */
    private void startJobPerformance() {
        openActivty(this, PerRepairDispatchActivity.class, null, null);
    }

    /**
     * 跳转图表
     */
    private void startChart() {

    }
}
