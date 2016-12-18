package com.chaungying.avery_menu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.chaungying.avery_menu.bean.WeekBean;
import com.chaungying.wuye3.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 王晓赛 or 2016/8/30
 *         <p/>
 *         每日菜谱的周历view
 */
public class MenuWeekView extends FrameLayout implements AdapterView.OnItemClickListener {


    @ViewInject(R.id.menu_week_grid_view)
    private GridView menu_week_view;

    @ViewInject(R.id.date_date)
    private TextView date_date;

    private WeekAdapter weekAdapter;

    public MenuWeekView(Context context) {
        this(context, null);
    }

    private ArrayList<WeekBean> list = new ArrayList<WeekBean>();

    public MenuWeekView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuWeekView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.menu_week_view, this);
        x.view().inject(view);
        weekAdapter = new WeekAdapter(context);
        creareView();
    }

    private void creareView() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
        date_date.setText(sdf.format(new Date()));

        menu_week_view.setAdapter(weekAdapter);
        menu_week_view.setOnItemClickListener(this);
        upData();
    }

    /**
     * 更改一周的数据
     */
    private void upData() {
        int index = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        getWeekdays();
        for (int i = 0; i < list.size(); i++) {
            WeekBean weekBean = list.get(i);
            if (i == index) {
                weekBean.setIndex(index);
                weekBean.setNow(true);
            } else {
                weekBean.setNow(false);
                weekBean.setIndex(i);
            }
            weekBean.setSelect(false);
            weekBean.setValue(weekBean.getValue());
        }
        weekAdapter.setWeekList(list);
        weekAdapter.notifyDataSetChanged();
    }

//    ArrayList<String> weekList = new ArrayList<String>();

    private static final int FIRST_DAY = Calendar.SUNDAY;

    private void getWeekdays() {
        Calendar calendar = Calendar.getInstance();
        setToFirstDay(calendar);
        for (int i = 0; i < 7; i++) {
            addDay(calendar);
            calendar.add(Calendar.DATE, 1);
        }
    }

    private void setToFirstDay(Calendar calendar) {
        while (calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY) {
            calendar.add(Calendar.DATE, -1);
        }
    }

    private void addDay(Calendar calendar) {
        WeekBean bean = new WeekBean();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        String day = dateFormat.format(calendar.getTime());
        bean.setValue(day);
        dateFormat.applyPattern("yyyy年MM月");
        String year_month = dateFormat.format(calendar.getTime());
        bean.setAllValue(year_month);
        dateFormat.applyPattern("yyyy-MM-dd");
        String year_month_day = dateFormat.format(calendar.getTime());
        bean.setYearMonthDay(year_month_day);
        list.add(bean);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for (int i = 0; i < list.size(); i++) {
            if (i == position) {
                list.get(i).setSelect(true);
            } else {
                list.get(i).setSelect(false);
            }
        }
        if (listener != null) {
            listener.weekViewListener(list.get(position).getYearMonthDay());
        }
        date_date.setText(list.get(position).getAllValue());
        weekAdapter.setWeekList(list);
        weekAdapter.notifyDataSetChanged();
    }

    private WeekViewItemOnClickListener listener;

    public void setListener(WeekViewItemOnClickListener listener) {
        this.listener = listener;
    }

    public interface WeekViewItemOnClickListener {
        void weekViewListener(String calendar);
    }


    class WeekAdapter extends BaseAdapter {

        private Context mContext;

        private ArrayList<WeekBean> weekList = new ArrayList<>();

        public void setWeekList(ArrayList<WeekBean> weekList) {
            this.weekList = weekList;
        }

        public WeekAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return weekList.size();
        }

        @Override
        public Object getItem(int position) {
            return weekList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            WeekBean bean = weekList.get(position);
            View view;
            if (convertView == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.menu_week_view_item, null);
            } else {
                view = convertView;
            }
            TextView tv = (TextView) view.findViewById(R.id.week_view_item);
            tv.setText(bean.getValue() + "");
            if (bean.isNow()) {
                if (bean.isSelect()) {
                    tv.setTextColor(getResources().getColor(R.color.White));
                    tv.setBackgroundResource(R.drawable.shape_week_bg_select);
                } else {
                    tv.setTextColor(getResources().getColor(R.color.White));
                    tv.setBackgroundResource(R.drawable.shape_week_bg_now);
                }
            } else {
                if (bean.isSelect()) {
                    tv.setTextColor(getResources().getColor(R.color.White));
                    tv.setBackgroundResource(R.drawable.shape_week_bg_select);
                } else {
                    tv.setTextColor(getResources().getColor(R.color.vice_title));
                    tv.setBackgroundResource(R.drawable.shape_week_bg_other);
                }
            }
            return view;
        }
    }
}
