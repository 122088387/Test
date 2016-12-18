package com.chaungying.round_malls.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.round_malls.interface_.AddSubStractBtnListtener;
import com.chaungying.wuye3.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author 王晓赛 or 2016/9/22
 *         <p/>
 *         购物车中的加减按钮
 */
public class AddSubtractBtnView extends FrameLayout implements View.OnClickListener {


    private View view;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    private int id;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        setTextNum(num);
    }

    public void setSubVisable(int visable) {
        iv_substract.setVisibility(visable);
        shopp_num.setVisibility(visable);
    }

    @ViewInject(R.id.shopping_add)
    private ImageView iv_add;

    @ViewInject(R.id.shopping_substract)
    private ImageView iv_substract;

    @ViewInject(R.id.shopping_substract_num)
    private TextView shopp_num;


    private AddSubStractBtnListtener addSubStractBtnListtener;

    public void setAddSubStractBtnListtener(AddSubStractBtnListtener addSubStractBtnListtener) {
        this.addSubStractBtnListtener = addSubStractBtnListtener;
    }

    public AddSubtractBtnView(Context context) {
        this(context, null);
    }

    public AddSubtractBtnView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddSubtractBtnView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.add_substract_btn_view, this);
        x.view().inject(view);
        iv_add.setOnClickListener(this);
        iv_substract.setOnClickListener(this);
        shopp_num.setText(0 + "");
        num = Integer.parseInt(shopp_num.getText().toString());
        iv_substract.setVisibility(View.GONE);
        shopp_num.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shopping_add:
                setView(iv_add);
                if (num < 99) {
                    shopp_num.setText(++num + "");
                    if (addSubStractBtnListtener != null) {
                        addSubStractBtnListtener.getNumListtener(this, num);
                    }
                }
                iv_substract.setVisibility(View.VISIBLE);
                shopp_num.setVisibility(View.VISIBLE);
                break;
            case R.id.shopping_substract:
                setView(iv_substract);
                if (num > 0) {
                    num--;
                    shopp_num.setText(num + "");
                    if (addSubStractBtnListtener != null) {
                        addSubStractBtnListtener.getNumListtener(this, num);
                    }
                    if (num == 0) {
                        iv_substract.setVisibility(View.GONE);
                        shopp_num.setVisibility(View.GONE);
                    }
                }
                break;
        }
    }

    public void setTextNum(int num) {
        shopp_num.setText(num + "");
        if (num == 0) {
            iv_substract.setVisibility(View.GONE);
            shopp_num.setVisibility(View.GONE);
        } else {
            iv_substract.setVisibility(View.VISIBLE);
            shopp_num.setVisibility(View.VISIBLE);
        }
    }

//    /**
//     * @param visibility 显示与隐藏
//     */
//    public void setVisibility(int visibility) {
//        iv_add.setVisibility(visibility);
//        iv_substract.setVisibility(visibility);
//        shopp_num.setVisibility(visibility);
//    }

}
