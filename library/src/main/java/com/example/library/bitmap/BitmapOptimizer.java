package com.example.library.bitmap;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import com.example.library.BuildConfig;
import com.example.library.CLog;
import com.example.library.Global;

import java.io.*;

public class BitmapOptimizer {
	private static final String TAG = BitmapOptimizer.class.getSimpleName();

	/***
	 *
	 * @param ctx
	 * @param originalPath 图片原始路径
	 * @param cacheDir 图片缓存目录
	 * @return
	 */
	public static String autoOptimize(Context ctx, String originalPath, String cacheDir) {
		CLog.i(TAG, "originalPath>>" + originalPath + " Global.IMAGE_RESIZE>>>"
				+ Global.IMAGE_RESIZE + "Global.IMAGE_MIN_SIZE>>>" + Global.IMAGE_MIN_SIZE +
				"Global.IMAGE_WIDTH>>" + Global.IMAGE_WIDTH + "Global.IMAGE_HEIGHT>>>"
				+ Global.IMAGE_HEIGHT + "Global.IMAGE_QUALITY>>>" + Global.IMAGE_QUALITY);
		return autoOptimize(ctx, originalPath, cacheDir, Global.IMAGE_RESIZE,
				Global.IMAGE_MIN_SIZE, Global.IMAGE_WIDTH, Global.IMAGE_HEIGHT, Global.IMAGE_QUALITY);
	}

	/***
	 *
	 * @param ctx
	 * @param originalPath  图片原始路径
	 * @param cacheDir      图片缓存目录
	 * @param resize        是否压缩尺寸 true:压缩 false:不压缩
	 * @param minSize       图片允许上传最大值，单位K，图片大小超过minSize图片才压缩
	 * @param width         最大允许宽
	 * @param height        最大允许高
	 * @param quality       图片压缩参数，ios(0-1),android(0-100)
	 * @return
	 */
	public static String autoOptimize(Context ctx, String originalPath, String cacheDir, boolean resize, int minSize, int width, int height, int quality) {
		ImageInfo imageInfo = getImageInfo(originalPath);
		CLog.i(TAG, "图片原始大小>>>" + imageInfo.getImageSize()+"图片旋转>>>"+imageInfo.rotation+"图片是否翻转>>>>"
				+imageInfo.flipHorizontal+"图片宽>>>"+imageInfo.width+"图片高>>>"+imageInfo.height);
		String outPath = FileUtils.getTempFilePath(ctx, cacheDir);//图片输出路径
		//如果图片需要翻转
		if (imageInfo.flipHorizontal || imageInfo.rotation != 0) {
			Bitmap srcBitMap = BitmapFactory.decodeFile(originalPath);
			String newOriginalpath = FileUtils.getTempFilePath(ctx, cacheDir);
			Bitmap rotationBitmap = handleRotation(srcBitMap, imageInfo.rotation, imageInfo.flipHorizontal);//方向和翻转调整
			saveBitmap(rotationBitmap, newOriginalpath);
			originalPath = newOriginalpath;
		}
		//如果图片大小大于minSize需要压缩，否则不需要压缩,直接返回
		if (null != imageInfo && imageInfo.getImageSize() > minSize) {
			CLog.i(TAG, "图片大小大于minSize需要压缩");

			//如果需要压缩尺寸
			if (resize) {
				CLog.i(TAG, "图片大小大于minSize需要压缩  需要压缩尺寸");
				Bitmap reSizeBitmap = compressSize(originalPath, width, height);//尺寸压缩
				//压缩尺寸后如果还是大于允许的minSize，则进行质量压缩
				if (getBitmapSize(reSizeBitmap) > minSize) {
					CLog.i(TAG, "压缩尺寸后如果还是大于允许的minSize，则进行质量压缩");
					boolean saveFlag = compressQuality(reSizeBitmap, outPath, quality, minSize);//质量压缩
					if (saveFlag) {
						CLog.i(TAG, "压缩尺寸后如果还是大于允许的minSize，质量压缩成功");
						return outPath;
					} else {
						CLog.i(TAG, "压缩尺寸后如果还是大于允许的minSize，质量压缩失败");
						return null;
					}
				} else {
					CLog.i(TAG, "压缩尺寸后如果还是小于允许的minSize，则直接保存");
					boolean saveFlag = saveBitmap(reSizeBitmap, outPath);
					if (saveFlag) {
						CLog.i(TAG, "压缩尺寸后如果还是小于允许的minSize，保存成功");
						return outPath;
					} else {
						CLog.i(TAG, "压缩尺寸后如果还是小于允许的minSize，保存失败");
						return null;
					}
				}

			} //如果不需要压缩尺寸，则直接进行质量压缩
			else {
				CLog.i(TAG, "如果不需要压缩尺寸，则直接进行质量压缩");
				Bitmap bitmap = BitmapFactory.decodeFile(originalPath);
				boolean saveFlag = compressQuality(bitmap, outPath, quality, minSize);
				if (saveFlag) {
					CLog.i(TAG, "如果不需要压缩尺寸，质量压缩成功");
					return outPath;
				} else {
					CLog.i(TAG, "如果不需要压缩尺寸，质量压缩失败");
					return null;
				}
			}
		} else {
			CLog.i(TAG, "图片大小小于minSize不需要压缩");
			return originalPath;
		}
	}


	/***
	 * 图片尺寸压缩
	 * @param srcImagePath
	 * @param outWidth
	 * @param outHeight
	 * @return
	 */
	public static Bitmap compressSize(String srcImagePath, int outWidth, int outHeight) {
		//进行大小缩放来达到压缩的目的
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(srcImagePath, options);
		//根据原始图片的宽高比和期望的输出图片的宽高比计算最终输出的图片的宽和高
		float srcWidth = options.outWidth;
		float srcHeight = options.outHeight;
		float maxWidth = outWidth;
		float maxHeight = outHeight;
		float srcRatio = srcWidth / srcHeight;
		float outRatio = maxWidth / maxHeight;
		float actualOutWidth = srcWidth;
		float actualOutHeight = srcHeight;

		if (srcWidth > maxWidth || srcHeight > maxHeight) {
			//如果输入比率小于输出比率,则最终输出的宽度以maxHeight为准()
			//比如输入比为10:20 输出比是300:10 如果要保证输出图片的宽高比和原始图片的宽高比相同,则最终输出图片的高为10
			//宽度为10/20 * 10 = 5  最终输出图片的比率为5:10 和原始输入的比率相同

			//同理如果输入比率大于输出比率,则最终输出的高度以maxWidth为准()
			//比如输入比为20:10 输出比是5:100 如果要保证输出图片的宽高比和原始图片的宽高比相同,则最终输出图片的宽为5
			//高度需要根据输入图片的比率计算获得 为5 / 20/10= 2.5  最终输出图片的比率为5:2.5 和原始输入的比率相同
			if (srcRatio < outRatio) {
				actualOutHeight = maxHeight;
				actualOutWidth = actualOutHeight * srcRatio;
			} else if (srcRatio > outRatio) {
				actualOutWidth = maxWidth;
				actualOutHeight = actualOutWidth / srcRatio;
			} else {
				actualOutWidth = maxWidth;
				actualOutHeight = maxHeight;
			}
		}

		options.inSampleSize = getSampleSize(options, actualOutWidth, actualOutHeight);
		options.inJustDecodeBounds = false;
		Bitmap scaledBitmap = null;
		try {
			scaledBitmap = BitmapFactory.decodeFile(srcImagePath, options);
		} catch (OutOfMemoryError e) {
			if (BuildConfig.DEBUG)
				e.printStackTrace();
		}
		if (scaledBitmap == null) {
			return null;//压缩失败
		}
		//生成最终输出的bitmap
		Bitmap actualOutBitmap = Bitmap.createScaledBitmap(scaledBitmap, (int) actualOutWidth, (int) actualOutHeight, true);
		if (actualOutBitmap != scaledBitmap) {
			scaledBitmap.recycle();
		}

		return actualOutBitmap;
	}


	public static long getBitmapSize(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int baosLength = baos.toByteArray().length;
		return baosLength / 1024;
	}


	public static void deleteFile(String path) {
		File file = new File(path);
		if (null != file && file.exists()) {
			file.delete();
		}
	}

	/***
	 * 保存bitmap到指定文件
	 * @param srcBitmap
	 * @param outFileName
	 * @return
	 */
	public static boolean saveBitmap(Bitmap srcBitmap, String outFileName) {
		FileOutputStream out = null;
		try {
			File file = new File(outFileName);
			if (file.exists()) {
				file.createNewFile();
			}
			out = new FileOutputStream(file);
			srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			return true;
		} catch (IOException e) {
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					if (BuildConfig.DEBUG)
						e.printStackTrace();
				}
			}

			if (null != srcBitmap) {
				if (!srcBitmap.isRecycled()) {
					srcBitmap.recycle();
				}
			}
		}
	}

	/***
	 * 图片质量压缩
	 * @param bitmap
	 * @param outFileName
	 * @param quality 压缩率
	 * @return
	 */
	public static boolean compress(Bitmap bitmap, String outFileName, int quality) {
		if (null == bitmap) {
			return false;
		}
		try {
			File file = new File(outFileName);
			if (file.exists()) {
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);// quality==30 ==> 压缩70%
			out.flush();
			out.close();
			return true;
		} catch (IOException e) {
			CLog.i(TAG, "压缩失败：quality = " + quality + ", outFileName = " + outFileName);
			return false;
		} finally {
			bitmap.recycle();// 释放资源
		}
	}


	/***
	 * 图片质量压缩
	 * @param srcBitmap
	 * @param outFileName
	 * @param quality  压缩率
	 * @param maxFileSize 图片容许最大值
	 * @return
	 */
	public static boolean compressQuality(Bitmap srcBitmap, String outFileName, int quality, int maxFileSize) {
		//进行有损压缩
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int compressQuality = quality;
		srcBitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, baos);//质量压缩方法，把压缩后的数据存放到baos中 (100表示不压缩，0表示压缩到最小)

		int baosLength = baos.toByteArray().length;

		while (baosLength / 1024 > maxFileSize) {//循环判断如果压缩后图片是否大于maxMemmorrySize,大于继续压缩
			compressQuality -= 10;
			if (compressQuality > 0) {
				baos.reset();//重置baos即让下一次的写入覆盖之前的内容
				srcBitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, baos);//将压缩后的图片保存到baos中
				baosLength = baos.toByteArray().length;
			} else {
				break;
			}
		}

		if (!srcBitmap.isRecycled()) {
			srcBitmap.recycle();
		}

		//将bitmap保存到指定路径
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(outFileName);
			//包装缓冲流,提高写入速度
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fos);
			bufferedOutputStream.write(baos.toByteArray());
			bufferedOutputStream.flush();
			return true;
		} catch (Exception e) {
			if (BuildConfig.DEBUG)
				e.printStackTrace();
			return false;
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					if (BuildConfig.DEBUG)
						e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					if (BuildConfig.DEBUG)
						e.printStackTrace();
				}
			}
		}
	}


	/***
	 * 根据图片信息的旋转角度和是否翻转 处理图片
	 * @param srcBitMap
	 * @param rotation
	 * @param flipHorizontal
	 * @return
	 */
	private static Bitmap handleRotation(Bitmap srcBitMap, int rotation, boolean flipHorizontal) {
		Matrix m = new Matrix();
		//处理图片翻转
		if (flipHorizontal) {
			m.postScale(-1, 1);
		}
		// 处理图片旋转
		if (rotation != 0) {
			m.postRotate(rotation);
		}
		Bitmap finalBitmap = Bitmap.createBitmap(srcBitMap, 0, 0, srcBitMap.getWidth(), srcBitMap.getHeight(), m, true);
		if (!srcBitMap.isRecycled()) {
			srcBitMap.recycle();
		}
		return finalBitmap;
	}

	/***
	 * 根据要求的输出宽和高 计算尺寸的压缩比率
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int getSampleSize(BitmapFactory.Options options, float reqWidth, float reqHeight) {
		float srcWidth = options.outWidth;
		float srcHeight = options.outHeight;
		int sampleSize = 1;
		if (srcWidth > reqWidth || srcHeight > reqHeight) {
			int withRatio = Math.round(srcWidth / reqWidth);
			int heightRatio = Math.round(srcHeight / reqHeight);
			sampleSize = Math.min(withRatio, heightRatio);
		}
		CLog.i(TAG, "计算得到的尺寸要压缩率>>>" + sampleSize);
		return sampleSize;
	}

	/***
	 * 根据图片路径获取图片ImageInfo信息
	 * @param srcImagePath
	 * @return
	 */
	private static ImageInfo getImageInfo(String srcImagePath) {
		long imageSize = FileSizeUtils.getFileSizeWithSrcPath(srcImagePath);
		int width = 0;
		int height = 0;
		int rotation = 0;
		boolean flipHorizontal = false;
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(srcImagePath, options);
			width = options.outWidth;
			height = options.outHeight;
			ExifInterface exif = new ExifInterface(srcImagePath);
			int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (exifOrientation) {
				case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
					flipHorizontal = true;
				case ExifInterface.ORIENTATION_NORMAL:
					rotation = 0;
					break;
				case ExifInterface.ORIENTATION_TRANSVERSE:
					flipHorizontal = true;
				case ExifInterface.ORIENTATION_ROTATE_90:
					rotation = 90;
					break;
				case ExifInterface.ORIENTATION_FLIP_VERTICAL:
					flipHorizontal = true;
				case ExifInterface.ORIENTATION_ROTATE_180:
					rotation = 180;
					break;
				case ExifInterface.ORIENTATION_TRANSPOSE:
					flipHorizontal = true;
				case ExifInterface.ORIENTATION_ROTATE_270:
					rotation = 270;
					break;
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG)
				e.printStackTrace();
		}

		if ((rotation / 90) % 2 != 0) {
			width = height;
			height = width;
		}

		return new ImageInfo(imageSize, width, height, rotation, flipHorizontal);
	}

	/***
	 * 图片信息类
	 */
	private static class ImageInfo {
		public long imageSize;         //图片大小 单位Kb
		public int width;              //宽度
		public int height;             //高度
		public int rotation;           //旋转角度
		public boolean flipHorizontal; //是否翻转

		public ImageInfo(long imageSize, int width, int height, int rotation, boolean flipHorizontal) {
			this.imageSize = imageSize;
			this.width = width;
			this.height = height;
			this.rotation = rotation;
			this.flipHorizontal = flipHorizontal;
		}

		public long getImageSize() {
			return imageSize;
		}

		public void setImageSize(long imageSize) {
			this.imageSize = imageSize;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getRotation() {
			return rotation;
		}

		public void setRotation(int rotation) {
			this.rotation = rotation;
		}

		public boolean isFlipHorizontal() {
			return flipHorizontal;
		}

		public void setFlipHorizontal(boolean flipHorizontal) {
			this.flipHorizontal = flipHorizontal;
		}
	}

}
