package com.health.immunity.act;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.act.presenter.ActPresenter;
import com.health.immunity.act.presenter.IActPresenter;
import com.health.immunity.act.view.IActFragment;
import com.health.immunity.databinding.FragmentActBinding;
import com.health.immunity.webviewUtilityClasses.MyWebViewClient;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActFragment extends Fragment implements IActFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    FragmentActBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ValueCallback<Uri[]> mUploadMessage;
    private static final int FILECHOOSER_RESULTCODE = 9994;

    Context context;
    String actUrl = " ";
    IActPresenter presenter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ActFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActFragment newInstance(String param1, String param2) {
        ActFragment fragment = new ActFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        presenter = new ActPresenter(this, context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_act, container, false);
        System.out.println("Act Fragment entered");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_act, container, false);
        View view = binding.getRoot();
        context = view.getContext();
        WebView webView = (WebView) view.findViewById(R.id.webView);
        webView.setWebChromeClient(new MyChromeclient());
        presenter.setWebViewSettings(webView);
        webView.setWebViewClient(new MyWebViewClient(context, webView));
        presenter.getUrlFromSourceAPI(webView, PreferenceHelper.getStringPreference(context, IConstant.TOKEN), 0);
        return view;

    }

    @Override
    public void setUrl(String urlz) {
        this.actUrl = urlz;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) return;
            Uri result[] = data == null || resultCode != RESULT_OK ? null
                    : new Uri[]{data.getData()};
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }


    }

    private class MyChromeclient extends WebChromeClient {

        public void openFileChooser(ValueCallback<Uri[]> uploadMsg) {

            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);

        }

        // For Android 3.0+
        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            startActivityForResult(
                    Intent.createChooser(i, "File Browser"),
                    FILECHOOSER_RESULTCODE);
        }


        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {


            openFileChooser(filePathCallback);
            return true;

        }


        @Override
        public void onProgressChanged(WebView view, int newProgress) {


        }

    }

}