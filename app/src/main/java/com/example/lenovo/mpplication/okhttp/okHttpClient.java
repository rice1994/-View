package com.example.lenovo.mpplication.okhttp;

import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.example.lenovo.mpplication.okhttp.interceptor.EmptyEntity;
import com.example.lenovo.mpplication.okhttp.interceptor.HeaderInterceptor;
import com.example.lenovo.mpplication.okhttp.interceptor.JsonBodyInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by fan on 2018/4/17.
 */
public class okHttpClient<T> {
	private static final String TAG = okHttpClient.class.getSimpleName();
	private Retrofit retrofit;

	public void post() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		ArrayMap<String, String> params = new ArrayMap<>();
		params.put("source", "android");
		params.put("macId", "d6accac0-273e-3911-ae01-4e51fe7e7452");
		params.put("version", "1.0");
		params.put("sv", "v320");
		params.put("deviceType", "C");
		builder.addInterceptor(new HeaderInterceptor(params));
		ArrayMap<String, String> params1 = new ArrayMap<>();
		params.put("Referer", "172.29.3.82");
		params.put("token", "2eb6181dc50d49838ca520f84e5206b3");
		builder.addInterceptor(new JsonBodyInterceptor(params1));
		OkHttpClient okHttpClient = builder.build();
		retrofit = new Retrofit.Builder()
				.client(okHttpClient)
				//把返回的值转换为我们设定的对象类型
				.addConverterFactory(GsonConverterFactory.create())
				//RxJavaCallAdapterFactory.create()用来把Retrofit转成RxJava可用的适配类
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.baseUrl("http://172.29.3.82/ceis")
				.build();
		//这里传入了一个 OnSubscribe 对象作为参数。OnSubscribe 会被存储在返回的 Observable 对象中，它的作用相当于一个计划表，
		// 当 Observable 被订阅的时候，OnSubscribe 的 call() 方法会自动被调用，事件序列就会依照设定依次触发
		Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				subscriber.onNext("Hello");
				subscriber.onNext("Hi");
				subscriber.onNext("Aloha");
				subscriber.onCompleted();
			}
		});
		// TODO: 2018/4/18 不完全定义
		Action1<String> onNextAction = new Action1<String>() {
			// onNext()
			@Override
			public void call(String s) {
				Log.d(TAG, s);
			}
		};
//		String[] words = {"Hello", "Hi", "Aloha"};
//		Observable observable1 = Observable.from(words);
		// TODO: 2018/4/18 与create等价
		Observable<String> stringObservable = Observable.just("Hello", "Hi", "Aloha");
		// 将会依次调用：
		// onNext("Hello");
		// onNext("Hi");
		// onNext("Aloha");
		// onCompleted();
		/**Subscriber 是对 Observer 进行一些扩展，区别如下:
		 *
		 *onStart(): 这是 Subscriber 增加的方法。它会在 subscribe 刚开始，而事件还未发送之前被调用，可以用于做一些准备工作，例如数据的清零或重置。这是一个可选方法，默认情况下它的实现为空。需要注意的是，如果对准备工作的线程有要求（例如弹出一个显示进度的对话框，这必须在主线程执行）， onStart() 就不适用了，因为它总是在 subscribe 所发生的线程被调用，而不能指定线程。要在指定的线程来做准备工作，可以使用 doOnSubscribe() 方法，具体可以在后面的文中看到。
		 unsubscribe(): 这是 Subscriber 所实现的另一个接口 Subscription 的方法，用于取消订阅。在这个方法被调用后，Subscriber 将不再接收事件。一般在这个方法调用前，可以使用 isUnsubscribed() 先判断一下状态。 unsubscribe() 这个方法很重要，因为在 subscribe() 之后， Observable 会持有 Subscriber 的引用，这个引用如果不能及时被释放，将有内存泄露的风险。所以最好保持一个原则：要在不再使用的时候尽快在合适的地方（例如 onPause() onStop() 等方法中）调用 unsubscribe() 来解除引用关系，以避免内存泄露的发生。
		 */
		Subscriber<String> stringSubscriber = new Subscriber<String>() {


			@Override
			public void onCompleted() {
				Log.d(TAG, "Completed!");
			}

			@Override
			public void onError(Throwable e) {
				Log.d(TAG, "Error!");
			}

			@Override
			public void onNext(String s) {
				Log.d(TAG, "Item: " + s);
			}
		};

		observable.subscribe(stringSubscriber);
	}

	public void post1() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		ArrayMap<String, String> params = new ArrayMap<>();
		params.put("source", "android");
		params.put("macId", "d6accac0-273e-3911-ae01-4e51fe7e7452");
		params.put("version", "1.0");
		params.put("sv", "v320");
		params.put("deviceType", "C");
		builder.addInterceptor(new HeaderInterceptor(params));
		ArrayMap<String, String> params1 = new ArrayMap<>();
		params.put("Referer", "172.29.3.82");
		builder.addInterceptor(new JsonBodyInterceptor(params1));
		OkHttpClient okHttpClient = builder.build();
		retrofit = new Retrofit.Builder()
				.client(okHttpClient)
				//把返回的值转换为我们设定的对象类型
				.addConverterFactory(GsonConverterFactory.create())
				//RxJavaCallAdapterFactory.create()用来把Retrofit转成RxJava可用的适配类
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.baseUrl("http://172.29.3.82/ceis/")
				.build();
		MyApiEndpointInterface service = retrofit.create(MyApiEndpointInterface.class);
		Observable observable = service.getUser("2eb6181dc50d49838ca520f84e5206b3", new EmptyEntity());
		observable.subscribeOn(Schedulers.io())//http请求线程
				.observeOn(Schedulers.io())//回调所在线程
//				.retryWhen(new RetryException()//失败后retry
				.unsubscribeOn(Schedulers.io());//http请求线程，取消订阅，解除引用关系，以避免内存泄露的发生

		//FuncX 和 ActionX 的区别在 FuncX 包装的是有返回值的方法
		//这里是对 对象的直接变换，将BaseResultEntity<T>，转成BaseResultEntity，前转后
		Observable observable1 = observable.map(new Func1<BaseResultEntity<T>, BaseResultEntity>() {
			@Override
			public BaseResultEntity call(BaseResultEntity<T> tBaseResultEntity) {
				Log.e(TAG, "new Observable: map--call");
				return tBaseResultEntity;
			}
		});
		observable1.doOnUnsubscribe(new Action0() {// 取消订阅
			@Override
			public void call() {
				Log.i(TAG, "Unsubscribe in " + getClass().getSimpleName());
			}
		})
//				.compose()//在操作符之后绑定才能生效
				.subscribe(new Subscriber<BaseResultEntity>() {//数据回调

					@Override
					public void onStart() {
						super.onStart();
						Log.e(TAG, "new Subscriber: onStart");
					}

					@Override
					public void onCompleted() {
						Log.e(TAG, "new Subscriber: onCompleted");
					}

					@Override
					public void onError(Throwable e) {

						Log.e(TAG, "new Subscriber: onError-----"+e.getMessage());
					}

					@Override
					public void onNext(BaseResultEntity s) {
						Log.e(TAG, "new Subscriber: onNext");
					}
				});
	}

	public interface MyApiEndpointInterface {
		// Request method and URL specified in the annotation
		// Callback for the parsed response is the last parameter

		@POST("sys/config/get")
		Observable<BaseResultEntity> getUser(@Header("token") String token, @Body EmptyEntity param);

	}
}
