package com.example.lenovo.mpplication.okhttp.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by fan on 2018/4/17.
 */
public class HeaderInterceptor implements Interceptor {
	private Map<String, String> mHeaders;

	public HeaderInterceptor(Map<String, String> headers) {
		mHeaders = headers;
	}

	/**
	 * 通过 Chain 的 request 方法可以获取到当前的 Request 对象。在使用完 Request 对象之后，
	 * 通过 Chain 对象的 proceed 方法来继续拦截器链条的执行。当执行完成之后，可以对得到的 Response 对象进行额外的处理。
	 *
	 * @param chain 对象表示的是当前的拦截器链条
	 * @return
	 * @throws IOException
	 */
	@Override
	public Response intercept(Chain chain) throws IOException {
		if (mHeaders == null || mHeaders.size() == 0)
			return chain.proceed(chain.request());

		Request.Builder builder = chain.request().newBuilder();
		Set<String> set = mHeaders.keySet();
		for(String key : set){
			builder.addHeader(key,mHeaders.get(key)).build();
		}
		return chain.proceed(builder.build());
	}
}
