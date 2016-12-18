package com.chaungying.metting.view;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.KeyEvent;

/** 加载loading */
public class ProgressUtil {

	private static CustomProgressDialog pd;

	public static void show(Context context, int strId) {
		show(context, context.getResources().getString(strId));
	}

	/**
	 * 打开loding
	 *
	 * @param context
	 *            上下文
	 * @param msg
	 *            loding显示的信息
	 * @param isCancle
	 * 			 是否能够手动取消对话框
	 */
	public static void show(Context context, String msg,boolean isCancle) {
		if (pd == null || !pd.isShowing()) {
			pd = CustomProgressDialog.createDialog(context);
			if (!TextUtils.isEmpty(msg))
				pd.setMessage(msg);
			pd.setCancelable(false);
			pd.setCanceledOnTouchOutside(isCancle);
			pd.show();
			pd.setOnKeyListener(new DialogInterface.OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
					if (arg1 == KeyEvent.KEYCODE_BACK) {
						pd.dismiss();
					}
					return false;
				}
			});
		}
	}

	/**
	 * 打开loding
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            loding显示的信息
	 */
	public static void show(Context context, String msg) {
		if (pd == null || !pd.isShowing()) {
			pd = CustomProgressDialog.createDialog(context);
			if (!TextUtils.isEmpty(msg))
				pd.setMessage(msg);
			pd.setCancelable(false);
			pd.setCanceledOnTouchOutside(false);
			pd.show();
			pd.setOnKeyListener(new DialogInterface.OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
					if (arg1 == KeyEvent.KEYCODE_BACK) {
						pd.dismiss();
					}
					return false;
				}
			});
		}
	}

	/**
	 * 关闭loding
	 */
	public static void close() {
		if (pd != null) {
			if (pd.isShowing()) {
				pd.cancel();
			}

		}
	}

	public static void setMessage(String msg) {
		if (pd != null && pd.isShowing() && !TextUtils.isEmpty(msg))
			pd.setMessage(msg);
	}

	public static void hide() {
		try {
			if (pd != null) {
				pd.cancel();
				pd = null;
			}
		} catch (Exception e) {
			// Log.e("tag", e.toString());
		}
	}
}
