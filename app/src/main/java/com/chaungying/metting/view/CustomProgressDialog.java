package com.chaungying.metting.view;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.chaungying.wuye3.R;

/** 提示语遮罩;主要是loading */
public class CustomProgressDialog extends Dialog {
	private static CustomProgressDialog customProgressDialog = null;

	public CustomProgressDialog(Context context) {
		super(context);
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public static CustomProgressDialog createDialog(Context context) {
		customProgressDialog = new CustomProgressDialog(context, R.style.CustomProgressDialog);
		customProgressDialog.setContentView(R.layout.myprogress);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		
		return customProgressDialog;
	}

	/**
	 * 
	 * [Summary] setMessage 提示内容
	 * 
	 * @param strMessage
	 * @return
	 * 
	 */
	public CustomProgressDialog setMessage(String strMessage) {
		TextView tvMsg = (TextView) customProgressDialog.findViewById(R.id.id_tv_loadingmsg);
		if (tvMsg != null) {
			tvMsg.setText(strMessage);
		}
		return customProgressDialog;
	}

}
