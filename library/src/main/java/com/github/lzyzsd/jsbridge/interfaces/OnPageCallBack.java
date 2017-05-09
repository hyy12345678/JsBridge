package com.github.lzyzsd.jsbridge.interfaces;

import android.graphics.Bitmap;


import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebView;


/**
 * Created by hyy on 2017/3/18.
 */

public interface OnPageCallBack {
     void pageStartedInvoke(WebView view, String url, Bitmap favicon);
     void pageFinishedInvoke(WebView view, String url);
     void pageReceivedErrorInvoke(WebView view, int errorCode, String description, String failingUrl);
     void pageReceivedSslError(WebView view, SslErrorHandler handler, SslError error);
}
