package com.health.immunity.login.utility;

import android.app.Activity;
import android.net.Uri;

import com.health.immunity.BaseActivity;
import com.health.immunity.CommonUtils;
import com.health.immunity.IConstant;

public class WebviewFallback extends BaseActivity implements CustomTabActivityHelper.CustomTabFallback {
    @Override
    public void openUri(Activity activity, Uri uri) {

        CommonUtils.showCustomDialog(context, "Please enable/install Google Chrome to use Google Fit data", IConstant.OKAY);
     /*   Intent intent = new Intent(activity, HomeActivity.class);
//        intent.putExtra(WebView.EXTRA_URL, uri.toString());
        Toast.makeText(activity, , Toast.LENGTH_SHORT).show();
        activity.startActivity(intent);*/
    }
}
