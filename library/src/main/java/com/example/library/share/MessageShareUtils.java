package com.example.library.share;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by fan on 2018/9/5.
 * Copyright  2018 www.yylending.com. All Rights Reserved.
 */
public class MessageShareUtils {
	/**
	 * 分享到短信
	 */
	public static void sendSMS(Activity aty, String content,String url) {
		Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
		sendIntent.setData(Uri.parse("smsto:"));
		sendIntent.putExtra("sms_body", content + url);
		aty.startActivity(sendIntent);
	}
}
