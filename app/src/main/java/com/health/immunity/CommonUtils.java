package com.health.immunity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.health.immunity.R;

import com.health.immunity.TransparentProgressDialog;
import com.health.immunity.IConstant;
import com.health.immunity.SnackBarClickListener;
import com.google.android.material.snackbar.Snackbar;

import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class CommonUtils implements IConstant {

    private static SpotsDialog dialogProgressSpots;
    private static TransparentProgressDialog mProgressDialog;


    private static ProgressDialog m1ProgressDialog;
    private static Dialog dialog;
    private static final String TAG = "Common";
    public static int countsend = 0;
    public static int activeblecount = 0;
    public static double officelat = 28.7041;
    public static double officelong = 77.1025;
    public static double homelat = 28.7041;
    public static double homelong = 77.1025;
    public static String profilelong = "";
    public static String communityuser = "";
    public static String communityage = "";
    public static String communitygender = "";
    public static String communitycomm = "";
    public static String communityusername = "";
    public static String communityagename = "";
    public static String communitygendername = "";
    public static String communitycommname = "";
    public static boolean compareset = false;

    public static void showSpotoProgressDialog(Context context) {
        dialogProgressSpots = (SpotsDialog) new SpotsDialog.Builder().setContext(context)
                .setTheme(R.style.Custom)
                .setMessage("   ").setCancelable(false).build();
        dialogProgressSpots.show();
        dialogProgressSpots.setCancelable(false);

        mProgressDialog = new TransparentProgressDialog(context);
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
        //   mProgressDialog.setTitle(getResources().getString(R.string.title_progress_dialog));
        //          mProgressDialog.setCancelable(true);
        mProgressDialog.show();

    }

    public static void dismissSpotoProgressDialog() {
//        try {
//            if (dialogProgressSpots != null)
//                dialogProgressSpots.cancel();
//            dialogProgressSpots = null;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        try {
            if(mProgressDialog!=null){
                mProgressDialog.cancel();
            }
            mProgressDialog=null;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean isInternetAvail(final Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            } else {
                //  showCustomDialog(mContext, INTERNET_AVAIL, OKAY);
                /*showSnackBarWithAction(mContext, mView, INTERNET_AVAIL, GO_ONLINE, new SnackBarClickListener() {
                    @Override
                    public void setOnClick() {
                        mContext.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                });*/
            }
        }
        return false;
    }

    private static void showInternetDialog() {

    }

    public static void showSnackBarWithAction(Context mContext, View mView, String message, String actionMsg, final SnackBarClickListener snackBarClickListener) {
        Snackbar snackbar = Snackbar.make(mView, message, Snackbar.LENGTH_LONG).setAction(actionMsg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBarClickListener.setOnClick();
            }
        });
        snackbar.show();
        snackbar.setDuration(6000);
        snackbar.setActionTextColor(mContext.getResources().getColor(R.color.colorPrimary));
    }

    public static void showCustomDialog(Context context, String message, String btnText) {
        dialog = new Dialog(context.getApplicationContext());
        dialog.setContentView(R.layout.custom_dialog_layout);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        try {
            dialog.show();
        }catch (WindowManager.BadTokenException badTokenException){badTokenException.printStackTrace();}
        TextView tvDialog = dialog.findViewById(R.id.tvDialog);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        tvDialog.setText(message);
        btnSubmit.setText(btnText);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    public static void showDialog(Context context, String message, String btnText) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.show_dialog_layout);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        TextView tvDialog = dialog.findViewById(R.id.tvDialog);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        tvDialog.setText(message);
        btnSubmit.setText(btnText);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public static String getDeviceId(Context activity) {
        String device_id = null;

        device_id = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);

        return device_id;

    }

    public static String getLocalBluetoothName() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // if device does not support Bluetooth
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "device does not support bluetooth");
            return null;
        }

        return mBluetoothAdapter.getName();
    }

    /**
     * get bluetooth adapter MAC address
     *
     * @return MAC address String
     */
//    public static String getBluetoothMacAddress() {
//        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//
//        // if device does not support Bluetooth
//        if (mBluetoothAdapter == null) {
//            Log.d(TAG, "device does not support bluetooth");
//            return null;
//        }
//
//        return mBluetoothAdapter.getAddress();
//    }

    public static String getAddress(Context context) {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    public static void showDate(TextView textView) {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String formattedDate = df.format(c);
        textView.setText(CommonUtils.dateFormat(formattedDate));
        Log.e("TAG", "--->" + formattedDate);
    }

    public static String dateFormat(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            Date myDate = format.parse(date);
            SimpleDateFormat myDateFormat = new SimpleDateFormat(MY_DATE_FORMAT, Locale.US);
            return myDateFormat.format(myDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
}
