package com.example.lenovo.mpplication.okhttp.interceptor;

import android.support.v4.util.ArrayMap;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

/**
 * Created by fan on 2018/4/17.
 */
public class JsonBodyInterceptor implements Interceptor {
	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	private ArrayMap<String, String> mParamsMap;

	public JsonBodyInterceptor(ArrayMap<String, String> params) {
		mParamsMap = params;
	}
	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		if(mParamsMap != null && !isMediaRequest(request.url().toString())){
			String params = new Gson().toJson(mParamsMap);
			RequestBody commonBody = RequestBody.create(JSON, params);
			request.newBuilder().post(commonBody).build();
		}
		return chain.proceed(request);
	}

	private static boolean isMediaRequest(String url) {
		String subtype = url.substring(url.lastIndexOf("."), url.length()).toLowerCase();
		return ".png".equals(subtype) || ".mpeg".equals(subtype) || ".mp4".equals(subtype) || ".jpg".equals(subtype)
				|| ".jpeg".equals(subtype) || ".webp".equals(subtype);
	}
}
