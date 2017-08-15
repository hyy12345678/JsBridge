package com.github.lzyzsd.jsbridge;

import android.graphics.Bitmap;


import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by bruce on 10/28/15.
 */
public class BridgeWebViewClient extends WebViewClient {
    private BridgeWebView webView;

    public BridgeWebViewClient(BridgeWebView webView) {
        this.webView = webView;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
            webView.handlerReturnData(url);
            return true;
        } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
            webView.flushMessageQueue();
            return true;
        } else if (onCustomShouldOverrideUrlLoading(url)) {
            return true;
        } else {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }


    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        webView.pageStartedInvoke(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        if (BridgeWebView.toLoadJs != null) {
            BridgeUtil.webViewLoadLocalJs(view, BridgeWebView.toLoadJs);
        }

        //
        if (webView.getStartupMessage() != null) {
            for (Message m : webView.getStartupMessage()) {
                webView.dispatchMessage(m);
            }
            webView.setStartupMessage(null);
        }

        webView.pageFinishedInvoke(view, url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        webView.pageReceivedErrorInvoke(view, errorCode, description, failingUrl);
    }

//    @Override
//    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
////        super.onReceivedSslError(view, handler, error);
//        webView.pageReceivedSslError(view, handler, error);
//    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler sslErrorHandler, SslError sslError) {
        webView.pageReceivedSslError(view, sslErrorHandler, sslError);
    }

    protected boolean onCustomShouldOverrideUrlLoading(String url) {
        return false;
    }
}