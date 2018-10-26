package com.example.library.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import com.example.library.CLog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FileUtils {
	private static final String TAG = FileUtils.class.getSimpleName();

	// 文件存储根目录,所有文件存储到该根目录下,各个类型文件存储再分子目录
	private static final String ROOT = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ File.separator + "yjszyxt" + File.separator;

	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";

	public final static String DEFAULT_SAVE_IMAGE_PATH_UPLOAD = ROOT + "Images" + File.separator + "Cache/HouseImage/";
	public final static String ID_CARD_IMAGE_SAVE_PATH = ROOT + "Images" + File.separator + "Cache/idCardImgs/";
	public final static String POSTER_IMAGE_SAVE_PATH = ROOT + "Images" + File.separator + "Cache/posters/";
	public final static String DEFAULT_GLIDE_CACHE = ROOT + "GlideCache";

	public static final String LAS_QR_CODE_IMG_FILE_NAME = "LAS_QR_CODE" + JPEG_FILE_SUFFIX; // 云贷推荐二维码图片
	public static final String INVITE_BIND_QR_CODE_IMG_FILE_NAME = "INVITE_BIND_QR_CODE" + JPEG_FILE_SUFFIX; // 邀请绑定二维码图片

	public static File createImageFile(AlbumStorageDirFactory albumStorageDirFactory) throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir(albumStorageDirFactory);
		return File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
	}

	private static File getAlbumDir(AlbumStorageDirFactory albumStorageDirFactory) {
		File storageDir = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			storageDir = albumStorageDirFactory.getAlbumStorageDir("");
			if (storageDir != null) {
				if (!storageDir.mkdirs()) {
					if (!storageDir.exists()) {
						CLog.d(TAG, "failed to create directory");
						return null;
					}
				}
			}
		} else {
			CLog.v(TAG, "External storage is not mounted READ/WRITE.");
		}
		return storageDir;
	}

	private static final String IMAGE_PRE_FILENAME = "mis_";
	private static final String IMAGE_FORMAT = ".jpg";
	private static final String TIME_FORMAT = "yyyyMMddHHmmssSSS";

	public static File createTmpFile(Context context, String imageCacheDir) {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			if (checkDirExists(imageCacheDir)) {
				StringBuilder sBuilder = new StringBuilder(imageCacheDir);
				String timeStamp = new SimpleDateFormat(TIME_FORMAT, Locale.CHINA).format(new Date());
				sBuilder.append(IMAGE_PRE_FILENAME).append(timeStamp).append(IMAGE_FORMAT);
				return new File(sBuilder.toString());
			} else {
				File pic = Environment.getExternalStorageDirectory();
				String timeStamp = new SimpleDateFormat(TIME_FORMAT, Locale.CHINA).format(new Date());
				String fileName = IMAGE_PRE_FILENAME + timeStamp + "";
				return new File(pic, fileName + IMAGE_FORMAT);
			}
		} else {
			StringBuilder sBuilder = new StringBuilder(imageCacheDir);
			File cacheDir = context.getCacheDir();
			String timeStamp = new SimpleDateFormat(TIME_FORMAT, Locale.CHINA).format(new Date());
			sBuilder.append(IMAGE_PRE_FILENAME).append(timeStamp).append(IMAGE_FORMAT);
			return new File(cacheDir, sBuilder.toString());
		}
	}


	public static String getTempFilePath(Context context, String imageCacheDir) {
		File absoluteCachePath = createTmpFile(context, imageCacheDir);
		String outPath = null;
		if (null != absoluteCachePath) {
			outPath = absoluteCachePath.getAbsolutePath();
		}
		return outPath;

	}

	private static boolean checkDirExists(String storageDirPath) {
		if (TextUtils.isEmpty(storageDirPath)) {
			return false;
		}
		File storageDir = new File(storageDirPath);
		if (!storageDir.exists()) {
			if (!storageDir.mkdirs()) {
				return false;
			}
		}
		return true;
	}

	public static String getFilesSize(List<String> list) {
		long total = 0;
		for (String url : list) {
			File file = new File(url);
			if (file.exists()) {
				total += file.length();
			}
		}
		return getFormatSize(total);
	}

	/**
	 * @param size 文件大小，单位字节
	 */
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

	private static boolean isSpace(String s) {
		if (s == null) return true;
		for (int i = 0, len = s.length(); i < len; ++i) {
			if (!Character.isWhitespace(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据文件路径获取文件
	 *
	 * @param filePath 文件路径
	 * @return 文件
	 */
	public static File getFileByPath(String filePath) {
		return isSpace(filePath) ? null : new File(filePath);
	}

	/**
	 * 判断目录是否存在，不存在则判断是否创建成功
	 *
	 * @param dirPath 目录路径
	 * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
	 */
	public static boolean createOrExistsDir(String dirPath) {
		return createOrExistsDir(getFileByPath(dirPath));
	}

	/**
	 * 判断目录是否存在，不存在则判断是否创建成功
	 *
	 * @param file 文件
	 * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
	 */
	public static boolean createOrExistsDir(File file) {
		// 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
		return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
	}

	/**
	 * 判断文件是否存在，不存在则判断是否创建成功
	 *
	 * @param file 文件
	 * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
	 */
	public static boolean createOrExistsFile(File file) {
		if (file == null) return false;
		// 如果存在，是文件则返回true，是目录则返回false
		if (file.exists()) return file.isFile();
		if (!createOrExistsDir(file.getParentFile())) return false;
		try {
			return file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 保存图片到本地
	 * @param dirPath
	 * @param fileName
	 * @param bitmap
	 * @return
	 */
	public static boolean saveBitmapToFile(String dirPath, String fileName, Bitmap bitmap) {
		return saveBitmapToFile(dirPath, fileName, bitmap, 80, true);
	}

	/**
	 * 保存图片到本地
	 *
	 * @param dirPath
	 * @param fileName
	 * @param bitmap
	 * @param quality
	 * @return
	 */
	public static boolean saveBitmapToFile(String dirPath, String fileName, Bitmap bitmap,int quality, boolean needRecycle) {
		File f = new File(dirPath, fileName);
		if (!createOrExistsDir(dirPath) || !createOrExistsFile(f)) {
			return false;
		}

		boolean saveSuccess = false;
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(f));
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
			saveSuccess = true;
		} catch (Exception e) {
			saveSuccess = false;
		} finally {
			if (bos != null) {
				try {
					bos.flush();
					bos.close();
				} catch (IOException ee) {
					saveSuccess = false;
				}
			}
			if (needRecycle && !bitmap.isRecycled()) {
				bitmap.recycle();
			}
		}
		return saveSuccess;
	}


}
