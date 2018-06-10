package com.example.lenovo.mpplication.view.image;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.example.lenovo.mpplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.os.Environment.DIRECTORY_DCIM;
import static android.os.Environment.DIRECTORY_PICTURES;

public class ImageActivity extends Activity {

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
		String filePath = "D:";
		filePath = Environment.getExternalStorageDirectory()+"savePath";
		File localFile = new File(filePath);
	//	localFile = getFilesDir();
		if (!localFile.exists())
			localFile.mkdir();
		File finalImageFile = new File(localFile, System.currentTimeMillis() + ".jpg");
		finalImageFile = new File(Environment.getExternalStoragePublicDirectory (DIRECTORY_DCIM), System.currentTimeMillis() + ".jpg");
		if(finalImageFile.exists())
			finalImageFile.delete();
		finalImageFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(finalImageFile);
		bitmap.compress(Bitmap.CompressFormat.JPEG,10,fos);
		fos.flush();
		fos.close();
		Toast.makeText(this, "图片保存在："+ finalImageFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();

		//发广播告诉相册有图片需要更新，这样可以在图册下看到保存的图片了
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri uri = Uri.fromFile(finalImageFile);
		intent.setData(uri);
		sendBroadcast(intent);
	}
}
