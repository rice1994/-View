package com.example.lenovo.mpplication.view.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Toast;

import com.example.lenovo.mpplication.R;
import com.example.lenovo.mpplication.view.base.BaseView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapView extends BaseView {
	public BitmapView(Context context) {
		super(context);
	}

	public BitmapView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	private Bitmap getBitmap() {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beauty00);
		return bitmap;
	}

	private void saveBitmap(File file, Bitmap bitmap) throws IOException {

		String filePath = "D:\\";
		File localFile = new File(filePath);
		if (!localFile.exists())
			localFile.mkdir();
		File finalImageFile = new File(localFile, System.currentTimeMillis() + ".jpg");
		if(finalImageFile.exists())
			finalImageFile.delete();
		finalImageFile.createNewFile();
		FileOutputStream fos = null;
		new FileOutputStream(finalImageFile);
		if(bitmap == null){
			Toast.makeText(getContext(),"图片不存在",Toast.LENGTH_SHORT).show();
			return;
		}
		bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
		fos.flush();
		fos.close();
		Toast.makeText(getContext(),"图片已存在",Toast.LENGTH_SHORT).show();

	}
}
