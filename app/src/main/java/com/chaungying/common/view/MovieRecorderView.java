
package com.chaungying.common.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioEncoder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OutputFormat;
import android.media.MediaRecorder.VideoEncoder;
import android.media.MediaRecorder.VideoSource;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.chaungying.wuye3.R;

import java.io.File;
import java.io.IOException;

/**
 * 视频播放控件
 * 
 */
public class MovieRecorderView extends LinearLayout implements OnErrorListener {

	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;

	private MediaRecorder mMediaRecorder;
	private Camera mCamera;

	private long startTime;
	private File mVecordFile = null;// 文件
	private Animator animator;
	private LineView lineView;

	public MovieRecorderView(Context context) {
		this(context, null);
	}

	public MovieRecorderView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	// 为什么加了这个注解就没错了？？？
	@SuppressLint("NewApi")
	public MovieRecorderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		initCamera();

		LayoutInflater.from(context)
				.inflate(R.layout.movie_recorder_view, this);
		mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);
		lineView = (LineView) findViewById(R.id.lineView);

		ViewTreeObserver observer = lineView.getViewTreeObserver();
		observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				int width = lineView.getMeasuredWidth();
				if (width == 0) {
					stop();
				}
				return true;
			}
		});

		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(new CustomCallBack());
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

	}

	// SurfaceView 的 CustomCallBack
	private class CustomCallBack implements Callback {

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			initCamera();
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {

		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			freeCameraResource();
		}

	}

	/**
	 * 初始化摄像头
	 * 
	 */
	public void initCamera() {
		if (mCamera != null) {
			freeCameraResource();
		}

		try {
			mCamera = Camera.open();
		} catch (Exception e) {
			e.printStackTrace();
			freeCameraResource();
		}
		if (mCamera == null)
			return;

		setCameraParams();
		mCamera.setDisplayOrientation(90);
		try {
			mCamera.setPreviewDisplay(mSurfaceHolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mCamera.startPreview();

	}

	/**
	 * 设置摄像头为竖屏
	 */
	public void setCameraParams() {
		if (mCamera != null) {
			Camera.Parameters params = mCamera.getParameters();
			params.set("orientation", "portrait");
			mCamera.setParameters(params);
		}
	}

	/**
	 * 释放摄像头资源
	 */
	private void freeCameraResource() {
		if (mCamera != null) {
			mCamera.setPreviewCallback(null);
			mCamera.stopPreview();
			mCamera.lock();
			mCamera.release();
			mCamera = null;
		}
	}

	public void createRecordDir() {
		File sampleDir = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "aami/video/");
		if (!sampleDir.exists()) {
			sampleDir.mkdirs();
		}
		mVecordFile = new File(sampleDir, "recording"
				+ System.currentTimeMillis() + ".mp4");
	}

	/**
	 * 初始化录制
	 * 
	 */
	private void initRecord() throws IOException {
		if (mMediaRecorder == null) {
			mMediaRecorder = new MediaRecorder();
			mCamera.unlock();
		}
		mMediaRecorder.reset();
		mMediaRecorder.setCamera(mCamera);
		mMediaRecorder.setOnErrorListener(this);
		mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
		mMediaRecorder.setVideoSource(VideoSource.CAMERA);// 视频源
		mMediaRecorder.setAudioSource(AudioSource.MIC);// 音频源
		mMediaRecorder.setOutputFormat(OutputFormat.MPEG_4);// 视频输出格式
		mMediaRecorder.setAudioEncoder(AudioEncoder.AMR_NB);// 音频格式
		mMediaRecorder.setVideoSize(320, 240);// 设置分辨率：

		mMediaRecorder.setVideoEncodingBitRate(1 * 1024 * 512 * 2);// 设置帧频率，然后就清晰了

		mMediaRecorder.setOrientationHint(90);// 输出旋转90度，保持竖屏播放

		mMediaRecorder.setVideoEncoder(VideoEncoder.MPEG_4_SP);// 视频录制格式
		mMediaRecorder.setOutputFile(mVecordFile.getAbsolutePath());

		mMediaRecorder.prepare();
		try {
			mMediaRecorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void record() {
		startTime = System.currentTimeMillis();
		createRecordDir();

		try {
			// initCamera();
			initRecord();
			startAnimation(); // 设置进度条的动画

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	/**
	 * 停止录制并释放资源
	 * 
	 */
	public void stop() {
		stopRecord();
		releaseRecord();
		freeCameraResource();
	}

	public void stop2() {
		stopRecord();
		releaseRecord();
	}

	/**
	 * 停止录制
	 * 
	 */
	public void stopRecord() {
		if (mMediaRecorder != null) {
			// 设置后不会崩
			mMediaRecorder.setOnErrorListener(null);
			mMediaRecorder.setPreviewDisplay(null);
			try {
				mMediaRecorder.stop();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (RuntimeException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 释放资源
	 * 
	 */
	private void releaseRecord() {
		if (mMediaRecorder != null) {
			mMediaRecorder.setOnErrorListener(null);
			try {
				mMediaRecorder.release();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		mMediaRecorder = null;
	}

	public long getTimeStart() {
		return startTime;
	}

	/**
	 * @return the mVecordFile
	 */
	public File getmVecordFile() {
		return mVecordFile;
	}

	@Override
	public void onError(MediaRecorder mr, int what, int extra) {
		try {
			if (mr != null)
				mr.reset();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public LineView getLineView() {
		return lineView;
	}

	public void startAnimation() {
		int orignalWith = getResources().getDisplayMetrics().widthPixels;
		animator = ObjectAnimator
				.ofInt(lineView, "layoutWidth", orignalWith, 0);
		animator.setDuration(10000);
		animator.setInterpolator(new LinearInterpolator());
		animator.start();
	}

	public void cancelAnimation() {
		if (animator.isRunning()) {
			animator.cancel();
		}
	}
	
}
