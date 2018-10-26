package com.example.library.share;

import android.support.v7.app.AppCompatActivity;

public class WXEntryActivity extends AppCompatActivity  {
//public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		IWXAPI api = WXAPIFactory.createWXAPI(this, WeChatShareUtil.APP_ID, false);
//		api.handleIntent(getIntent(),this);
//		finish();
//	}
//
//	@Override
//	public void onReq(BaseReq baseReq) {
//
//	}
//
//	@Override
//	public void onResp(BaseResp baseResp) {
//		String result;
//		switch (baseResp.errCode) {
//			case BaseResp.ErrCode.ERR_OK:
//				EventAgent.post(new ShareResult(true));
//				result = "分享成功";
//				break;
//			case BaseResp.ErrCode.ERR_USER_CANCEL:
//				result = null;
//				break;
//			default:
//				EventAgent.post(new ShareResult(false));
//				result = "分享失败";
//				break;
//		}
//		if (result != null) {
//			CompatToast.showShortToast(this, result);
//		}
//	}
}
