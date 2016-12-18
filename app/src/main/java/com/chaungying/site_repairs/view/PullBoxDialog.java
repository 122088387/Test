package com.chaungying.site_repairs.view;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaungying.common.bean.ConfigBean;
import com.chaungying.common.view.BuildView;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.xutils.common.util.DensityUtil;

/**
 * 提示语遮罩;主要是loading
 */
public class PullBoxDialog extends Dialog {

    private static PullBoxDialog pullBoxDialog = null;

//    private ConfigBean.ItemsBean itemsBean;
    private static ImageView iv_back;
    private static TextView tv_back,tv_title;
    private static LinearLayout ll_my_pull_box_dialog;
    //    public void setItemsBean(ConfigBean.ItemsBean itemsBean) {
//        this.itemsBean = itemsBean;
//    }
    private static Context mContext;

    public PullBoxDialog(Context context) {
        super(context);
    }

    public PullBoxDialog(Context context, int theme) {
        super(context, theme);
    }

    public static PullBoxDialog createDialog(Context context) {
        mContext = context;
        pullBoxDialog = new PullBoxDialog(context, R.style.PullBoxDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.my_pull_box_dialog, null, false);
        ll_my_pull_box_dialog = (LinearLayout) view.findViewById(R.id.ll_my_pull_box_dialog);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pullBoxDialog.dismiss();
            }
        });
        tv_back = (TextView) view.findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pullBoxDialog.dismiss();
            }
        });
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
        params.width = ((Activity) context).getWindow().getWindowManager().getDefaultDisplay().getWidth() - DensityUtil.dip2px(120);
        view.setLayoutParams(params);
        pullBoxDialog.setContentView(view);
        pullBoxDialog.getWindow().getAttributes().gravity = Gravity.RIGHT;
        return pullBoxDialog;
    }

    /**
     * [Summary] setMessage 提示内容
     *
     * @param
     * @return
     */
//    public void setMessage(String strMessage) {
//        TextView tvMsg = (TextView) pullBoxDialog.findViewById(R.id.id_tv_loadingmsg);
//        if (tvMsg != null) {
//            tvMsg.setText(strMessage);
//        }
//    }

    public void initViewDatas(ConfigBean.ItemsBean itemsBean) {
        tv_title.setText(itemsBean.getTitle());
        Gson gson = new Gson();
        String json = gson.toJson(itemsBean.getDatas());
        BuildView buildView = new BuildView(ll_my_pull_box_dialog, mContext);
        buildView.buildView(json,false);
    }

}
