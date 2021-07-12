package com.health.immunity.act.presenter;

import android.webkit.WebView;

public interface IActPresenter {
    String getUrlFromSourceAPI(WebView view, String token,int urlPos);

    void setWebViewSettings(WebView webView);
}
