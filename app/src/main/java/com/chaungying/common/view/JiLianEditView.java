package com.chaungying.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chaungying.wuye3.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 创建者：  王晓赛
 * 时  间： 2016/11/23
 * <p>
 * 级联后返回回来数据 控件
 */

public class JiLianEditView extends FrameLayout {

    private Context mContext;

    @ViewInject(R.id.et_input)
    private EditText et_input;

    @ViewInject(R.id.ji_lian_edit_title)
    TextView ji_lian_edit_title;
    @ViewInject(R.id.tv_show_result)
    TextView tv_show_result;
    @ViewInject(R.id.tv_show_id)
    TextView tv_show_id;

    private String fieldname;
    private int isRequired;

    public int getIsRequired() {
        return isRequired;
    }

    //级联的接口
    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public JiLianEditView(Context context) {
        this(context, null);
    }

    public JiLianEditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JiLianEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.ji_lian_edit_view, this);
        x.view().inject(view);
    }

    public void setEt_input(String inputStr) {
        et_input.setText(inputStr);
    }

    public String getEt_input(){
        return et_input.getText().toString();
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public void setHint(String hint) {
        String[] str = hint.split("#");
        tv_show_result.setHint(str[0]);
        et_input.setHint(str[1]);
    }

    public void setIsRequired(int isRequired) {
        this.isRequired = isRequired;
    }

    public void setTitle(String title) {
        ji_lian_edit_title.setText(title);
    }

    public String getTitle() {
        return ji_lian_edit_title.getText().toString();
    }


    public void setTv_show_result(String tv_show_result) {
        this.tv_show_result.setText(tv_show_result);
    }

    public String getTv_show_result() {
        return this.tv_show_result.getText().toString();
    }

    public void setTv_show_id(String tv_show_id) {
        this.tv_show_id.setText(tv_show_id);
    }
}
