package com.example.library.share;

/**
 * Created by fan on 2018/9/5.
 */
public class WeChatShareUtil {
//
//	private static final String TAG = WeChatShareUtil.class.getSimpleName();
//
//	@Retention(RetentionPolicy.CLASS)
//	@IntDef({FRIEND, CIRCLE})
//	public @interface ShareType {
//	}
//
//	public static final int FRIEND = SendMessageToWX.Req.WXSceneSession;
//	public static final int CIRCLE = SendMessageToWX.Req.WXSceneTimeline;
//
//	//从官网申请的合法appId
//	public static final String APP_ID = Constants.WE_CHAT_APP_ID;
//	private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
//
//	//IWXAPI是第三方app和微信通信的openapi接口
//	private IWXAPI api;
//	private Context context;
//	public static WeChatShareUtil weChatShareUtil;
//
//	public static WeChatShareUtil getInstance(Context context) {
//		if (weChatShareUtil == null) {
//			weChatShareUtil = new WeChatShareUtil();
//		}
//		if (weChatShareUtil.api != null) {
//			weChatShareUtil.api.unregisterApp();
//		}
//		weChatShareUtil.context = context;
//		weChatShareUtil.regToWx();
//		return weChatShareUtil;
//	}
//
//	//注册应用id到微信
//	private void regToWx() {
//		//通过WXAPIFactory工厂，获取IWXAPI的实例
//		api = WXAPIFactory.createWXAPI(context, APP_ID, true);
//		//将应用的appId注册到微信
//		api.registerApp(APP_ID);
//	}
//
//	/**
//	 * 分享文字到朋友圈或者好友
//	 *
//	 * @param text  文本内容
//	 * @param scene 分享方式：好友还是朋友圈
//	 */
//	public boolean shareText(String text,@ShareType int scene) {
//		//初始化一个WXTextObject对象，填写分享的文本对象
//		WXTextObject textObj = new WXTextObject();
//		textObj.text = text;
//		return share(textObj, text, scene);
//	}
//
//	/**
//	 * 分享图片到朋友圈或者好友
//	 *
//	 * @param bmp   图片的Bitmap对象
//	 * @param scene 分享方式：好友还是朋友圈
//	 */
//	public boolean sharePic(Bitmap bmp,@ShareType int scene, boolean needRecycle) {
//		if (null == bmp)
//			return false;
//		//初始化一个WXImageObject对象
//		WXImageObject imageObj = new WXImageObject();
//		Bitmap thumb = Bitmap.createScaledBitmap(bmp, 100, 100, true);
//		byte[] bytes1 = bitmap2Bytes(bmp, 95, 470, needRecycle);
//		imageObj.imageData = bytes1;
//		return share(imageObj, thumb, scene);
//	}
//
//	/**
//	 * 分享网页到朋友圈或者好友，视频和音乐的分享和网页大同小异，只是创建的对象不同。
//	 * 详情参考官方文档：
//	 * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317340&token=&lang=zh_CN
//	 *
//	 * @param url         网页的url
//	 * @param title       显示分享网页的标题
//	 * @param description 对网页的描述
//	 * @param scene       分享方式：好友还是朋友圈
//	 */
//	public boolean shareUrl(String url, String title, Bitmap thumb, String description,@ShareType int scene) {
//		//初试话一个WXWebpageObject对象，填写url
//		WXWebpageObject webPage = new WXWebpageObject();
//		webPage.webpageUrl = url;
//		Bitmap bmp = Bitmap.createScaledBitmap(thumb, 100, 100, true);
//		return share(webPage, title, bmp, description, scene, true);
//	}
//
//	public boolean shareUrl(String url, String title, Bitmap thumb, String description, int scene, boolean needRecycle) {
//		//初试话一个WXWebpageObject对象，填写url
//		WXWebpageObject webPage = new WXWebpageObject();
//		webPage.webpageUrl = url;
//		return share(webPage, title, thumb, description, scene, needRecycle);
//	}
//
//	private boolean share(WXMediaMessage.IMediaObject mediaObject, Bitmap thumb, int scene) {
//		return share(mediaObject, null, thumb, null, scene, true);
//	}
//
//	private boolean share(WXMediaMessage.IMediaObject mediaObject, String description, int scene) {
//		return share(mediaObject, null, null, description, scene, true);
//	}
//
//	private boolean share(WXMediaMessage.IMediaObject mediaObject, String title, Bitmap thumb, String description, int scene, boolean needRecycle) {
//		//初始化一个WXMediaMessage对象，填写标题、描述
//		WXMediaMessage msg = new WXMediaMessage(mediaObject);
//		if (title != null) {
//			msg.title = title;
//		}
//		if (description != null) {
//			msg.description = description;
//		}
//		if (thumb != null) {
//			msg.thumbData = bitmap2Bytes(thumb, 100, 33, needRecycle);
//		}
//		//构造一个Req
//		SendMessageToWX.Req req = new SendMessageToWX.Req();
//		req.transaction = String.valueOf(System.currentTimeMillis());
//		req.message = msg;
//		req.scene = scene;
//		return api.sendReq(req);
//	}
//
//	//判断是否支持转发到朋友圈
//	//微信4.2以上支持，如果需要检查微信版本支持API的情况， 可调用IWXAPI的getWXAppSupportAPI方法,0x21020001及以上支持发送朋友圈
//	public boolean isSupportWX() {
//		int wxSdkVersion = api.getWXAppSupportAPI();
//		return wxSdkVersion >= TIMELINE_SUPPORTED_VERSION;
//	}
//
//	/**
//	 * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
//	 *
//	 * @param bitmap
//	 * @return
//	 */
//	public static byte[] bitmap2Bytes(Bitmap bitmap, int normalQuality, int maxkb, boolean needRecycle) {
//		ByteArrayOutputStream output = new ByteArrayOutputStream();
//		int options = normalQuality;
//		do {
//			output.reset(); //清空output
//			bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
//			options -= 5;
//			CLog.e(TAG, "length=" + output.toByteArray().length / 1024);
//		} while (output.toByteArray().length / 1024 > maxkb && options > 0);
//		if (needRecycle) {
//			bitmap.recycle();
//		}
//		return output.toByteArray();
//	}
//
//	public static boolean isAvailable(Context ctx) {
//		String wxPackage = "com.tencent.mm";
//		final PackageManager pkgMgr = ctx.getPackageManager();
//		List<PackageInfo> info = pkgMgr.getInstalledPackages(0);
//		for (int i = 0; i < info.size(); i++) {
//			if (info.get(i).packageName.equalsIgnoreCase(wxPackage))
//				return true;
//		}
//		return false;
//	}
}
