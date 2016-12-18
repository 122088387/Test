package com.chaungying.common.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaungying.common.view.MovieRecorderView;
import com.chaungying.wuye3.R;

import java.io.File;

public class VideoActivity extends Activity {

	private MovieRecorderView mRecorderView;
	private Button mShootBtn;
	private TextView hintText;
	private float  y_tmp1, y_tmp2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_video);
		mRecorderView = (MovieRecorderView) findViewById(R.id.movieRecorderView);
		mShootBtn = (Button) findViewById(R.id.shoot_button);
		hintText = (TextView) findViewById(R.id.hintText);
		mShootBtn.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				// 获取当前坐标
				float y = event.getY();

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// 获取手指按下时的坐标
					y_tmp1 = y;
					// hintText 可见
					hintText.setVisibility(View.VISIBLE);
					mRecorderView.getLineView().setVisibility(View.VISIBLE);
					mRecorderView.record();
					break;
				case MotionEvent.ACTION_UP:
					// hintText 不可见
					hintText.setVisibility(View.GONE);
					mRecorderView.cancelAnimation();
					mRecorderView.getLineView().setVisibility(View.INVISIBLE);

					long endTime = System.currentTimeMillis();

					if (endTime - mRecorderView.getTimeStart() > 1000
							&& hintText.getText().equals("上移取消")) {
						mRecorderView.cancelAnimation();
						finishActivity();
					} else if (endTime - mRecorderView.getTimeStart() > 1000
							&& hintText.getText().equals("松开取消")) {
						File file = mRecorderView.getmVecordFile();
						file.delete();
						mRecorderView.stop2();
					} else {
						Toast.makeText(VideoActivity.this, "视频录制时间太短",
								Toast.LENGTH_SHORT).show();
						File file = mRecorderView.getmVecordFile();
						file.delete();
						mRecorderView.stop2();
					}

					break;
				case MotionEvent.ACTION_MOVE:
					y_tmp2 = y;
					if (y_tmp1 != 0) {
						if (y_tmp1 - y_tmp2 >= 60) {
							hintText.setText("松开取消");
						} else {
							hintText.setText("上移取消");
						}
					}
					break;
				default:
					break;
				}
				return true;
			}
		});
	}

	// 退出当前 activity
	private void finishActivity() {
		mRecorderView.stop();
		// 获取路径
		String file = mRecorderView.getmVecordFile().getAbsolutePath();
		Intent intent = new Intent();
		intent.putExtra("path", file);
		setResult(RESULT_OK, intent);
		finish();
	}

}
