package com.health.immunity.login.utility;

import android.content.ComponentName;

import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsServiceConnection;

import java.lang.ref.WeakReference;

public class ServiceConnection extends CustomTabsServiceConnection {
    // A weak reference to the ServiceConnectionCallback to avoid leaking it.
    private WeakReference<ServiceConnectionCallback> mConnectionCallback;

    public ServiceConnection(ServiceConnectionCallback connectionCallback) {
        mConnectionCallback = new WeakReference<>(connectionCallback);
    }

    @Override
    public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
        ServiceConnectionCallback connectionCallback = mConnectionCallback.get();
        if (connectionCallback != null) connectionCallback.onServiceConnected(client);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        ServiceConnectionCallback connectionCallback = mConnectionCallback.get();
        if (connectionCallback != null) connectionCallback.onServiceDisconnected();
    }

}