package com.health.immunity.webviewUtilityClasses;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.health.immunity.HomeContainer.HomeActivity;
import com.health.immunity.IConstant;
import com.health.immunity.R;
import com.health.immunity.aboutus.AboutUsFragment;
import com.health.immunity.act.ActFragment;

import java.net.URISyntaxException;

import static com.google.firebase.crashlytics.internal.Logger.TAG;

public class ActWebViewClient extends WebViewClient {
    private static final String TEL_PREFIX = "tel:";
    Context context;
    WebView view;
    Fragment fragment;
    public ActWebViewClient(Context context, WebView view){
        this.context=context;
        this.view=view;

    }



    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {

        super.onPageStarted(view, url, favicon);
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {



        //boolean overrideUrlLoading = false;
        if(url.startsWith("https://programs.alyve.health/nativelink")){
            System.out.println("nativeurl1"+url);

            String [] pdfurlembed= url.split("nativelink/");
            System.out.println("nativeurl2"+ pdfurlembed[1]);

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdfurlembed[1]));
            context.startActivity(intent);

        }
        if(url.startsWith(TEL_PREFIX)) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            System.out.println("nativeurl1"+url);
            view.loadUrl(IConstant.MY_PRG_URL);
            intent.setData(Uri.parse(url));
            context.startActivity(intent);

        }
        if( url.startsWith("https://zoom.us/")  ) {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);

        }
        else if( url.startsWith("whatsapp://")  ) {
            // view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            //overrideUrlLoading = true;
            String [] whatsappcontact =url.split("phone=");
            try{
                //    Toast.makeText(context,view.getUrl(),Toast.LENGTH_SHORT).show();
                System.out.println("#################"+view.getUrl());
                String contact = whatsappcontact[1];
                PackageManager packageManager = context.getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);
                String urlw = "https://api.whatsapp.com/send?phone="+ contact ;
                i.setPackage("com.whatsapp");
                i.setData(Uri.parse(urlw));
                if (i.resolveActivity(packageManager) != null) {
                    context.startActivity(i);
                }else {
                    Toast.makeText(context, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                }
            } catch(Exception e) {
                Log.e("ERROR WHATSAPP",e.toString());
                Toast.makeText(context, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
            }
            view.goBack();
               /* Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(browserIntent);*/
        }

        else if (url.startsWith("intent://")) {
            try {
                Context context = view.getContext();
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);

                if (intent != null) {
                    view.stopLoading();

                    PackageManager packageManager = context.getPackageManager();
                    ResolveInfo info = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    if (info != null) {
                        context.startActivity(intent);
                    } else {
                        String fallbackUrl = intent.getStringExtra("browser_fallback_url");
                        view.loadUrl(fallbackUrl);


                    }

                    return true;
                }
            } catch (URISyntaxException e) {
                Log.e(TAG, "Can't resolve intent://", e);

            }
        }
        else
        {   if(url.startsWith(TEL_PREFIX)) {
            view.loadUrl(IConstant.MY_PRG_URL);
        }else {
            view.loadUrl(url);
        }
        }


        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);


    }

}

