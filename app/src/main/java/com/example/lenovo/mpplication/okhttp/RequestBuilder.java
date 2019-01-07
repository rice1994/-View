package com.example.lenovo.mpplication.okhttp;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.gson.Gson;
import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

/**
 * Created by fan on 2018/4/16.
 * <p>
 * okhttp???????
 */
public class RequestBuilder {

	private static final String TAG = RequestBuilder.class.getSimpleName();

	public static void call() throws IOException {
		OkHttpClient mClient = new OkHttpClient();
		Request request = new Request.Builder().url("").build();
		Response response = mClient.newCall(request).execute();
		if (response.isSuccessful()) {
			response.body();
			response.code();
		} else {
			throw new IOException("Unexpected code " + response);
		}
	}

	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	static OkHttpClient mClient = new OkHttpClient();

	@NonNull
	public static void post() throws IOException {
		Request.Builder builder = new Request.Builder();
		builder.addHeader("Referer", "172.29.3.82");
		builder.addHeader("token", "2eb6181dc50d49838ca520f84e5206b3");
//				builder.addHeader("Accept-Encoding", "gzip");
//				builder.addHeader("User-Agent", "okhttp/3.10.0");
		ArrayMap<String, String> params = new ArrayMap<>();
		params.put("source", "android");
		params.put("macId", "d6accac0-273e-3911-ae01-4e51fe7e7452");
		params.put("version", "1.0");
		params.put("sv", "v320");
		params.put("deviceType", "C");
		RequestBody requestBody = RequestBody.create(JSON, new Gson().toJson(params));
		Request request = builder.url("http://172.29.3.82/ceis/sys/config/get").post(requestBody).build();
		Headers headers = request.headers();
		String method = request.method();
		if (headers != null) {
			StringBuilder sb = new StringBuilder("Request Header [");
			for (int i = 0, count = headers.size(); i < count; i++) {
				String name = headers.name(i);
				if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
					sb.append(name).append(": ").append(headers.value(i)).append("; ");
				}
			}
			sb.append("]");
			System.out.println(String.format(Locale.getDefault(), "%s %s", method, sb.toString()));
		}
		if (requestBody != null) {
			StringBuilder sb = new StringBuilder("Request Body [");
			okio.Buffer buffer = new okio.Buffer();
			requestBody.writeTo(buffer);
			Charset charset = Charset.forName("UTF-8");
			MediaType contentType = requestBody.contentType();
			if (contentType != null) {
				charset = contentType.charset(charset);
			}
			if (isPlaintext(buffer)) {
				sb.append(buffer.readString(charset));
				sb.append(" (Content-Type = ").append(contentType == null ? "null" : contentType.toString()).append(",")
						.append(requestBody.contentLength()).append("-byte body)");
			} else {
				sb.append(" (Content-Type = ").append(contentType != null ? contentType.toString() : "application/json")
						.append(",binary ").append(requestBody.contentLength()).append("-byte body omitted)");
			}
			sb.append("]");
			System.out.println(String.format(Locale.getDefault(), "%s %s", method, sb.toString()));
		}
		Log.i(TAG, "Request--" + request.body().toString());
		//???????????
//		Response response = mClient.newCall(request).execute();
		//?????????
		mClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {

			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {

				if (response.isSuccessful()) {
					BufferedSource source = response.body().source();
					source.request(Long.MAX_VALUE);
					Buffer buffer = source.buffer();
					String bodyString = buffer.clone().readString(Charset.defaultCharset());
					System.out.println("bodyString--" + bodyString);
					// ???????? string()???? ???????????????????????????��?
					System.out.println("bodyString1--" + response.body().string());
				} else {
					throw new IOException("Unexpected code " + response);
				}
			}
		});

	}

	private static boolean isPlaintext(Buffer buffer) {
		try {
			Buffer prefix = new Buffer();
			long byteCount = buffer.size() < 64 ? buffer.size() : 64;
			buffer.copyTo(prefix, 0, byteCount);
			for (int i = 0; i < 16; i++) {
				if (prefix.exhausted()) {
					break;
				}
				int codePoint = prefix.readUtf8CodePoint();
				if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
					return false;
				}
			}
			return true;
		} catch (EOFException e) {
			return false; // Truncated UTF-8 sequence.
		}
	}
}
