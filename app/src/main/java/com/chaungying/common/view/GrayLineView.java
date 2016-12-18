package com.chaungying.common.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author 王晓赛 or 2016/6/28
 */
public class GrayLineView extends View{
    private TextView textView;
    private int lineHeight;
    private LinearLayout.LayoutParams layoutParams;
    private int lineColor;
    private Context mContext;
    public GrayLineView(Context context){
        this(context,null);
        mContext = context;
        textView = new TextView(context);
        initLine();
    }

    public GrayLineView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GrayLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLineHeight(int lineHeight){
        this.lineHeight = lineHeight;
        initLine();
    }
    public void setLineColor(int lineColor){
        this.lineColor= lineColor;
        initLine();
    }
    public void initLine(){
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,lineHeight);
        if(lineHeight == 0){
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1);
        }else {
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,lineHeight);
        }
        textView.setLayoutParams(layoutParams);
        if(lineColor == 0){
            textView.setBackgroundColor(Color.BLACK);
        }else{
            textView.setBackgroundColor(lineColor);

        }
    }
}
