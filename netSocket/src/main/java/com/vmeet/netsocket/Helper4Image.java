package com.vmeet.netsocket;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.ThumbnailUtils;

public class Helper4Image {
	
	// 组合图片
    public static Bitmap mergePicture(Bitmap[] images){
    	
    	Bitmap[] bitmaps = new Bitmap[images.length];
    	//// 生成图片的缩略图 
    	 for (int i = 0; i <images.length; i++) {
        	 Bitmap bitmap=Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
             Bitmap bitmap1 = ThumbnailUtils.extractThumbnail
                     (images[i], 94, 94);
             Canvas canvas=new Canvas(bitmap);
             canvas.drawBitmap(bitmap1,3,3,null);
             bitmaps[i]=bitmap;
        	
        }
        // 生成一个空的 bitmap
    	int w=200; // 画布的宽、高
    	int s=100; // 单张图片的宽、高
    	if(bitmaps.length>4)w=300;
    	
        Bitmap bitmap=Bitmap.createBitmap(w,w, Bitmap.Config.ARGB_8888); 
        // 创建一个画布
        Canvas canvas=new Canvas(bitmap);
        canvas.drawColor(Color.parseColor("#dadee1"));
       if(bitmaps.length == 1){
    	   canvas.drawBitmap(bitmaps[0], s / 2, s / 2,null);
       }
       else if (bitmaps.length == 2)
        {
         canvas.drawBitmap(bitmaps[0], 0, s / 2,null);
         canvas.drawBitmap(bitmaps[1], s, s / 2,null);
        }
        else if (bitmaps.length == 3)
        {
         canvas.drawBitmap(bitmaps[0], s / 2, 0,null);
         canvas.drawBitmap(bitmaps[1], 0, s,null);
         canvas.drawBitmap(bitmaps[2], s, s,null);
        }
        else if (bitmaps.length == 4)
        {
         canvas.drawBitmap(bitmaps[0], 0, 0,null);
         canvas.drawBitmap(bitmaps[1], s, 0,null);
         canvas.drawBitmap(bitmaps[2], 0, s,null);
         canvas.drawBitmap(bitmaps[3], s, s,null);
        }
        else if (bitmaps.length == 5)
        {
         canvas.drawBitmap(bitmaps[0], s / 2, s / 2,null);
         canvas.drawBitmap(bitmaps[1], s / 2 + s, s / 2,null);
         canvas.drawBitmap(bitmaps[2], 0, s / 2 + s,null);
         canvas.drawBitmap(bitmaps[3], s, s / 2 + s,null);
         canvas.drawBitmap(bitmaps[4], s * 2, s / 2 + s,null);
        }
        else if (bitmaps.length == 6)
        {
         canvas.drawBitmap(bitmaps[0], 0, s / 2,null);
         canvas.drawBitmap(bitmaps[1], s , s / 2,null);
         canvas.drawBitmap(bitmaps[2], s * 2, s / 2,null);
         canvas.drawBitmap(bitmaps[3], 0, s / 2 + s,null);
         canvas.drawBitmap(bitmaps[4], s, s / 2 + s,null);
         canvas.drawBitmap(bitmaps[5], s * 2, s / 2 + s,null);
        }
        else if (bitmaps.length == 7)
        {
         canvas.drawBitmap(bitmaps[0], s, 0,null);
         canvas.drawBitmap(bitmaps[1], 0, s,null);
         canvas.drawBitmap(bitmaps[2], s, s,null);
         canvas.drawBitmap(bitmaps[3], 2 * s, s,null);
         canvas.drawBitmap(bitmaps[4], 0, 2 * s,null);
         canvas.drawBitmap(bitmaps[5], s, 2 * s,null);
         canvas.drawBitmap(bitmaps[6], 2 * s, 2 * s,null);
        }
        else if (bitmaps.length == 8)
        {
         canvas.drawBitmap(bitmaps[0], s / 2, 0,null);
         canvas.drawBitmap(bitmaps[1], s / 2 + s, 0,null);
         canvas.drawBitmap(bitmaps[2], 0, s,null);
         canvas.drawBitmap(bitmaps[3], s, s,null);
         canvas.drawBitmap(bitmaps[4], 2 * s, s,null);
         canvas.drawBitmap(bitmaps[5], 0, 2 * s,null);
         canvas.drawBitmap(bitmaps[6], s, 2 * s,null);
         canvas.drawBitmap(bitmaps[7], 2 * s, 2 * s,null);
        }
        else if (bitmaps.length >= 9)
        {
         canvas.drawBitmap(bitmaps[0], 0, 0,null);
         canvas.drawBitmap(bitmaps[1], s, 0,null);
         canvas.drawBitmap(bitmaps[2], 2 * s, 0,null);
         canvas.drawBitmap(bitmaps[3], 0, s,null);
         canvas.drawBitmap(bitmaps[4], s, s,null);
         canvas.drawBitmap(bitmaps[5], 2 * s, s,null);
         canvas.drawBitmap(bitmaps[6], 0, 2 * s,null);
         canvas.drawBitmap(bitmaps[7], s, 2 * s,null);
         canvas.drawBitmap(bitmaps[8], 2 * s, 2 * s,null);
        
        }

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return bitmap;
    }

}
