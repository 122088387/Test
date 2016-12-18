package com.vmeet.netsocket;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.ThumbnailUtils;

public class Helper4Image1 {

	// 组合图片
	public static Bitmap mergePicture(Bitmap[] images) {
		// 生成一个空的 bitmap
		int w = 240; // 画布的宽、高

		Bitmap[] bitmaps = new Bitmap[images.length];
		// // 生成图片的缩略图
		for (int i = 0; i < images.length; i++) {
			Bitmap bitmap = null;
			Bitmap bitmap1 = null;
			if (images.length == 1) {
				bitmap = Bitmap.createBitmap(w, w, Bitmap.Config.ARGB_8888);
				bitmap1 = ThumbnailUtils.extractThumbnail(images[i], w, w);
			} else if (images.length == 2) {
				bitmap = Bitmap.createBitmap(w / 2 , w, Bitmap.Config.ARGB_8888);
				bitmap1 = ThumbnailUtils.extractThumbnail(images[i], w / 2, w);
			} else if (images.length == 3) {
				if (i == 0) {
					bitmap = Bitmap.createBitmap(w / 2, w,
							Bitmap.Config.ARGB_8888);
					bitmap1 = ThumbnailUtils.extractThumbnail(images[i], w / 2,
							w);
				} else {
					bitmap = Bitmap.createBitmap(w / 2, w / 2,
							Bitmap.Config.ARGB_8888);
					bitmap1 = ThumbnailUtils.extractThumbnail(images[i], w / 2,
							w / 2);
				}
			} else {
				bitmap = Bitmap.createBitmap(w / 2, w / 2, Bitmap.Config.ARGB_8888);
				bitmap1 = ThumbnailUtils.extractThumbnail(images[i], w / 2, w / 2);
			}
			Canvas canvas = new Canvas(bitmap);
			canvas.drawBitmap(bitmap1, 0, 0, null);
			bitmaps[i] = bitmap;

		}

		Bitmap bitmap = Bitmap.createBitmap(w, w, Bitmap.Config.ARGB_8888);
		// 创建一个画布
		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(Color.parseColor("#ffffff"));
		if (bitmaps.length == 1) {
			canvas.drawBitmap(bitmaps[0], 0, 0, null);
		} else if (bitmaps.length == 2) {
			canvas.drawBitmap(bitmaps[0], 0, 0, null);
			canvas.drawBitmap(bitmaps[1], w / 2 + 4, 0, null);
		} else if (bitmaps.length == 3) {
			canvas.drawBitmap(bitmaps[0], 0, 0, null);
			canvas.drawBitmap(bitmaps[1], w / 2 + 4, 0, null);
			canvas.drawBitmap(bitmaps[2], w / 2 + 4, w / 2 + 4, null);
		} else if (bitmaps.length >= 4) {
			canvas.drawBitmap(bitmaps[0], 0, 0, null);
			canvas.drawBitmap(bitmaps[1], w / 2 + 4, 0, null);
			canvas.drawBitmap(bitmaps[2], 0, w / 2 + 4, null);
			canvas.drawBitmap(bitmaps[3], w / 2 + 4, w / 2 + 4, null);
		}
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return bitmap;
	}

}
