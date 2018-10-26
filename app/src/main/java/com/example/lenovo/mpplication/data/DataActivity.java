package com.example.lenovo.mpplication.data;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.R;

import java.io.*;

public class DataActivity extends AppCompatActivity {

	private static final String TAG = DataActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data);
		ButterKnife.bind(this);
		Log.e(TAG, "getFilesDir= " + getFilesDir());
		Log.e(TAG, "getCacheDir= " + getCacheDir());
		Log.e(TAG, "getDataDir= " + getDataDir());
		Log.e(TAG, "getObbDir= " + getObbDir());
		Log.e(TAG, "getCodeCacheDir= " + getCodeCacheDir());
		Log.e(TAG, "getExternalCacheDir= " + getExternalCacheDir());
		Log.e(TAG, "getExternalFilesDir(DOWNLOAD_SERVICE)= " + getExternalFilesDir(DOWNLOAD_SERVICE));
		Log.e(TAG, "getExternalMediaDirs()[0]= " + getExternalMediaDirs()[0]);
		Log.e(TAG, "getNoBackupFilesDir= " + getNoBackupFilesDir());
		Log.e(TAG, "Environment.getDataDirectory= " + Environment.getDataDirectory());
		Log.e(TAG, "Environment.getRootDirectory= " + Environment.getRootDirectory());
		Log.e(TAG, "Environment.getDownloadCacheDirectory= " + Environment.getDownloadCacheDirectory());
		Log.e(TAG, "Environment.getExternalStorageDirectory= " + Environment.getExternalStorageDirectory());
		Log.e(TAG, "Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)= " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
		Log.e(TAG, "getExternalFilesDir= " + getExternalFilesDir(DOWNLOAD_SERVICE));
		File[] externalFilesDirs = getExternalFilesDirs(DOWNLOAD_SERVICE);
		Log.e(TAG, "getExternalFilesDirs= " + getExternalFilesDirs(DOWNLOAD_SERVICE));
		Log.e(TAG, "getExternalStorageDirectory= " + Environment.getExternalStorageDirectory());
		File[] files;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			files = getExternalFilesDirs(Environment.MEDIA_MOUNTED);
			for(File file1:files){
				Log.e("main",file1.getAbsolutePath());
			}
		}

		File dir = getExternalCacheDir();
		Log.e(TAG,"TotalSpace="+dir.getTotalSpace());
		Log.e(TAG,"FreeSpace="+dir.getFreeSpace());

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@OnClick({R.id.internal_no_cache, R.id.internal_cache, R.id.load, R.id.public_files_save, R.id.private_files_save, R.id.read_file})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.internal_no_cache:
				internal1();
				break;
			case R.id.internal_cache:
				internal2();
				File hhee = getDir("hhee", MODE_PRIVATE);
				break;
			case R.id.load:
				break;
			case R.id.public_files_save:
				publicFileSave();
				break;
			case R.id.private_files_save:
				privateFileSave();

				break;
			case R.id.read_file:
				break;
		}
	}

	private void publicFileSave() {
		String albumName="test.txt";
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			try {
				File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),albumName);
				Log.e(TAG,"file_AbsolutePath="+file.getAbsolutePath());
				FileOutputStream outputStream = new FileOutputStream(file);
				outputStream.write("PublicFileSave".getBytes());
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void privateFileSave() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			try {
				File file = new File(getExternalCacheDir(), "test111");
				file.mkdirs();
				 file = new File(getExternalFilesDir(DOWNLOAD_SERVICE), "test111");
				file.mkdirs();

				Log.e(TAG, "file_AbsolutePath=" + file.getAbsolutePath());
				FileOutputStream outputStream = new FileOutputStream(file);
				outputStream.write("PublicFileSave".getBytes());
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	/* Checks if external storage is available to at least read */
	public boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state) ||
				Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}

	private void load(String path) {
		try {
			File file = new File(getCacheDir(), "testCache.txt");
			FileInputStream fileInputStream = new FileInputStream(file);
			ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length = -1;
			while ((length = fileInputStream.read(buffer)) != -1) {
				byteArrayInputStream.write(buffer, 0, length);
			}
			Log.e(TAG, "byteArrayInputStream= " + byteArrayInputStream.toString());
			byteArrayInputStream.close();
			fileInputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void internal() {
		InputStream inputStream = getResources().openRawResource(R.raw.beauty00);

		try {
			AssetManager am = getResources().getAssets();
			inputStream = am.open("beauty00.jpg");

			FileOutputStream outputStream = this.openFileOutput("test.jpg", Context.MODE_PRIVATE);
			byte[] bytes = new byte[inputStream.available()];
			inputStream.read(bytes);
			byte[] bytes1 = new byte[1024];

			int length = -1;

			StringBuilder sb = new StringBuilder();
			while ((length = inputStream.read(bytes1)) != -1) {
				outputStream.write(bytes1, 0, length);
				sb.append(new String(bytes1));
			}
			System.out.println("sb.length()=" + sb.length());
			outputStream.close();
			inputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 内部可持久化存储
	 */
	private void internal1() {
		try {
			// 调用openFileOutput()以获取FileOutputStream 写入内部目录中的文件的内容
			//FileOutputStream outputStream = this.openFileOutput("test.txt", Context.MODE_WORLD_READABLE);

			//或者在目录中创建新文件
			File file = new File(getFilesDir(), "test.txt");
			FileOutputStream outputStream = new FileOutputStream(file);


			outputStream.write("132355".getBytes());
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 内部非持久化存储--缓存
	 */
	private void internal2() {
		try {
			//
			//File file = File.createTempFile("testCache", ".txt", getCacheDir());
			File file = new File(getCacheDir(), "testCache.txt");
			FileOutputStream outputStream = new FileOutputStream(file);
			StringBuilder sb = new StringBuilder();
			outputStream.write("132355".getBytes());
			outputStream.close();
			load(file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
