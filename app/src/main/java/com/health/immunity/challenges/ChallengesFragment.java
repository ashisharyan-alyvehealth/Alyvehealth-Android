package com.health.immunity.challenges;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.act.presenter.ActPresenter;
import com.health.immunity.act.presenter.IActPresenter;
import com.health.immunity.act.view.IActFragment;
import com.health.immunity.databinding.FragmentChallengesBinding;
import com.health.immunity.webviewUtilityClasses.MyWebViewClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChallengesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChallengesFragment extends Fragment implements IActFragment {
       FragmentChallengesBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context context;
    Fragment fragment1;
    String actUrl=" ";
    IActPresenter presenter;
    public ChallengesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChallengesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChallengesFragment newInstance(String param1, String param2) {
        ChallengesFragment fragment = new ChallengesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChallengesFragment fragment=new ChallengesFragment();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        presenter=new ActPresenter(this,context);
        fragment1=fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_challenges, container, false);
        System.out.println("challenges Fragment entered");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_challenges, container, false);
        View view = binding.getRoot();
        context=view.getContext();
        WebView webView = (WebView)view.findViewById(R.id.webView);
        presenter.setWebViewSettings(webView);
        webView.setWebViewClient(new MyWebViewClient(context,webView,getActivity()));
        presenter.getUrlFromSourceAPI(webView, PreferenceHelper.getStringPreference(context, IConstant.TOKEN),3);
        return view;
    }

    @Override
    public void setUrl(String urlz) {

    }
}