package com.example.library;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by lenovo on 2016/10/19.
 */
public class CLog {
	public static final int NONE = 0;

	public static final int VERBOSE = 10;
	public static final int DEBUG = 20;
	public static final int INFO = 30;
	public static final int WARN = 40;
	public static final int ERROR = 50;

	public static final int LOG_LEVEL = ERROR;

	public static boolean TOGGLE = false;//BuildConfig.DEBUG

	static final String TAG = "common";

	public static void v(@NonNull String tag, String log) {
		if (TOGGLE)
			Log.v(printTrack(1), log + " | " + tag);
	}

	public static void v(@NonNull String tag, String log, Throwable t) {
		if (TOGGLE)
			Log.v(printTrack(1), log + " | " + tag, t);
	}

	public static void d(@NonNull String tag, String log) {
		if (TOGGLE)
			Log.d(printTrack(1), log + " | " + tag);
	}

	public static void d(@NonNull String tag, String log, Throwable t) {
		if (TOGGLE)
			Log.d(printTrack(1), log + " | " + tag, t);
	}

	public static void i(@NonNull String tag, Throwable e) {
		if (TOGGLE)
			Log.i(printTrack(1), (e != null ? e.getMessage() : "Throwable object is null object") + " | " + tag);
	}

	public static void i(@NonNull String tag, String log) {
		if (TOGGLE)
			Log.i(printTrack(1), log + " | " + tag);
	}

	public static void i(@NonNull String tag, String log, Throwable t) {
		if (TOGGLE)
			Log.i(printTrack(1), log + " | " + tag, t);
	}

	public static void w(@NonNull String tag, String log) {
		if (TOGGLE)
			Log.w(printTrack(1), log + " | " + tag);
	}

	public static void w(@NonNull String tag, String log, Throwable t) {
		if (TOGGLE)
			Log.w(printTrack(1), log + " | " + tag, t);
	}

	public static void e(@NonNull String tag, String log) {
		if (TOGGLE)
			Log.e(printTrack(1), log + " | " + tag);
	}

	public static void e(@NonNull String tag, String log, Throwable t) {
		if (TOGGLE)
			Log.e(printTrack(1), log + " | " + tag, t);
	}


	@NonNull
	private static String printTrack(int methodCount) {
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		int stackOffset = getStackOffset(trace);

		//corresponding method count with the current stack may exceeds the stack trace. Trims the count
		if (methodCount + stackOffset > trace.length) {
			methodCount = trace.length - stackOffset - 1;
		}

		StringBuilder builder = new StringBuilder();
		String level = "";
		for (int i = methodCount; i > 0; i--) {
			int stackIndex = i + stackOffset;
			if (stackIndex >= trace.length) {
				continue;
			}
			builder.append(level)
					.append("(")
					.append(trace[stackIndex].getFileName())
					.append(":")
					.append(trace[stackIndex].getLineNumber())
					.append(")")
					.append(Thread.currentThread().getName());
			level += "   ";
		}
		if (builder.length() == 0) builder.append("Unknow Log Message");
		return builder.toString();
	}

	private static final int MIN_STACK_OFFSET = 3;

	private static int getStackOffset(StackTraceElement[] trace) {
		for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
			StackTraceElement e = trace[i];
			String name = e.getClassName();
			if (!name.equals(CLog.class.getName())) {
				return --i;
			}
		}
		return -1;
	}
}