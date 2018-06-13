package com.example.lenovo.mpplication.view.image;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import com.example.lenovo.mpplication.R;

import java.io.*;
import java.math.BigDecimal;

import static android.os.Environment.DIRECTORY_DCIM;

public class ImageActivity extends Activity {

	private static final String TAG = ImageActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		try {
			saveBitmap(getBitmap());
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();

		}
	}

	private Bitmap getBitmap() {
		InputStream is = null;
		try {
			is = getResources().getAssets().open("pg.png");
		} catch (IOException e) {
			e.printStackTrace();

		}

	//	Bitmap bitmap = BitmapFactory.decodeStream(is);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beauty00);

		return bitmap;
	}

	private void saveBitmap(Bitmap bitmap) throws IOException {
		if(bitmap == null){
			Toast.makeText(this,"图片不存在",Toast.LENGTH_SHORT).show();
			return;
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		int quality=100;
		bitmap.compress(Bitmap.CompressFormat.JPEG, quality, os);// quality==30 ==> 压缩70%
		while (os.toByteArray().length > 200) {
			quality -= 10;
			if (quality < 60)
				break;
			os.reset();//重置
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, os);
			Log.e(TAG,"quality---"+quality);
			Log.e(TAG,"压缩大小---"+getFormatSize(os.toByteArray().length ));
		}

		File finalImageFile = new File(Environment.getExternalStoragePublicDirectory (DIRECTORY_DCIM), System.currentTimeMillis() + ".jpg");
		if(finalImageFile.exists())
			finalImageFile.delete();
		finalImageFile.createNewFile();
		FileOutputStream out = new FileOutputStream(finalImageFile);
		out.write(os.toByteArray());
		out.flush();
		out.close();
		bitmap.recycle();// 释放资源
		Log.e(TAG, "压缩后图片大小=" + getFormatSize(finalImageFile.length()));

		Toast.makeText(this, "图片保存在："+ finalImageFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();

		//发广播告诉相册有图片需要更新，这样可以在图册下看到保存的图片了
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri uri = Uri.fromFile(finalImageFile);
		intent.setData(uri);
		sendBroadcast(intent);

	}
	public static String getFormatSize(long size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
			return size + "Byte(s)";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
	}
}
