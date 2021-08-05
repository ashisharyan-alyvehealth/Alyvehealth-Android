package com.health.immunity.act.presenter;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.act.model.SourceResponse;
import com.health.immunity.act.view.IActFragment;
import com.health.immunity.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.health.immunity.IConstant.BASE_URL_REPORTS;

public class ActPresenter implements IActPresenter{
    IActFragment view;
    final String[] url = {""};
    Context context;
    public ActPresenter(IActFragment view, Context context1){
        this.view=view;
        this.context=context1;
    }

    @Override
    public String getUrlFromSourceAPI(WebView view1, String token,int urlPos) {

            Call<SourceResponse> call = RetrofitClient.getUniqInstance().getApi()
                    .sourceCall("webview");
            call.enqueue(new Callback<SourceResponse>() {
                @Override
                public void onResponse(Call<SourceResponse> call, Response<SourceResponse> response) {

                    if (response.body() != null) {
                        if (response.code() == 200) {
                            if (response.body().getStatus().equalsIgnoreCase("true")) {
                                if(urlPos>900){
                                    if(urlPos==999) {
                                        url[0] = "https://www.alyve.health/about-us";
                                        view1.loadUrl(url[0]);
                                    }
                                    if(urlPos==998){
                                        url[0] = IConstant.MY_PRG_URL;
                                        view1.loadUrl(url[0]);
                                    }
                                    if(urlPos==997){
                                        url[0] = "https://programs.alyve.health/engage";
                                        view1.loadUrl(url[0]);
                                    }
                                    if(urlPos==996){
                                        url[0] = BASE_URL_REPORTS + token;
                                      //  url[0] = "https://programs.alyve.health/acts?tabId=challenges";
                                        view1.loadUrl(url[0]);
                                    }
                                    if(urlPos==995) {
                                        url[0] = "https://programs.alyve.health/privacy";
                                        view1.loadUrl(url[0]);
                                    }

                                }
                                else{
                                System.out.println("###############@@@@@@@@@@foryu" + response.body().getJsonData().get(urlPos).getTokenValue());
                                url[0] = response.body().getJsonData().get(urlPos).getTokenValue();
                                view1.loadUrl(url[0] + token);
                                }

                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<SourceResponse> call, Throwable t) {

                }
            });
            view1.setOnKeyListener(new View.OnKeyListener() {

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK
                            && event.getAction() == MotionEvent.ACTION_UP
                            && view1.canGoBack()) {
                        view1.goBack();
                        return true;
                    }
                    return false;
                }

            });


            return url[0];
    }

    @Override
    public void setWebViewSettings(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setBlockNetworkLoads(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);


        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile Safari/535.19");
        webView.getSettings().setDomStorageEnabled(true);


    }
}
