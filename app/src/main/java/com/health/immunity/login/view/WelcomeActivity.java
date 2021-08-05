package com.health.immunity.login.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.health.immunity.BaseActivity;
import com.health.immunity.HomeContainer.HomeActivity;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.login.utility.PrefManager;

public class WelcomeActivity extends BaseActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4,
                R.layout.welcome_slide5,
                R.layout.welcome_slide6,
                R.layout.welcome_slide7,
        };

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        if (PreferenceHelper.getBooleanPreference(context, IConstant.IS_HR_ONBOARD))
        {
            Intent intent = new Intent(context, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //finish();
        }
        else
        {
            //PreferenceHelper.setBooleanPreference(context, IConstant.IS_HR_ONBOARD, false);

            Intent intent = new Intent(context, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //finish();
        }
        //startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
        //finish();
    }

    //	viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        TextView preview1, preview2, preview3;
        ImageView imgpreview1, imgpreview2, imgpreview3;
        int resId = 0;
        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            return view;
          /*  switch (position) {
                case 0: {
                    resId = R.layout.welcome_slide1;
                    break;
                }
                case 1: {
                    resId = R.layout.welcome_slide2;
                    break;
                }
                case 2: {
                    resId = R.layout.welcome_slide3;
                    break;
                }
               *//* case 3: {
                    resId = R.layout.welcome_slide4;
                    break;
                }*//*
            }
            ViewGroup layout = (ViewGroup) layoutInflater.inflate(resId, container, false);
            if (resId == R.layout.welcome_slide1) {

                preview1 = (TextView) layout.findViewById(R.id.preview1);
                imgpreview1=(ImageView)layout.findViewById(R.id.slide1);


            } else if (resId == R.layout.welcome_slide2) {
                preview2 = (TextView) layout.findViewById(R.id.preview2);
                imgpreview2=(ImageView)layout.findViewById(R.id.slide2);


            } else if (resId == R.layout.welcome_slide3) {
                preview3 = (TextView) layout.findViewById(R.id.preview3);
                imgpreview3=(ImageView)layout.findViewById(R.id.slide3);


            }
           *//* View view = layoutInflater.inflate(resId, null);
            ((ViewPager) container).addView(view, 0);
            return view;*//*
             *//*View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;*//*
            usepreviewApi();
            container.addView(layout,0);
            return layout;*/
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }


      /*  private void usepreviewApi() {
            Call<GetKeyValueResponse> call = RetrofitClient.getUniqInstance().getApi()
                    .getKeyValuecall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), "preview1,preview2,preview3");
            call.enqueue(new Callback<GetKeyValueResponse>() {
                @Override
                public void onResponse(Call<GetKeyValueResponse> call, Response<GetKeyValueResponse> response) {
                    if (response.body() != null) {
                        if (response.code() == 200) {
                            if (response.body().getStatus().equalsIgnoreCase("true")) {

                                preview1.setText(response.body().getJsonData().get(0).getKeey());
                                Glide.with(context).load(response.body().getJsonData().get(0).getValue()).into(imgpreview1);

                                preview2.setText(response.body().getJsonData().get(1).getKeey());
                                Glide.with(context).load(response.body().getJsonData().get(1).getValue()).into(imgpreview2);

                                 if (resId == R.layout.welcome_slide3) {
                                    preview3 = (TextView) findViewById(R.id.preview3);
                                    imgpreview3=(ImageView)findViewById(R.id.slide3);
                                     preview3.setText(response.body().getJsonData().get(2).getKeey());
                                     Glide.with(context).load(response.body().getJsonData().get(2).getValue()).into(imgpreview3);


                                }

                                //Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetKeyValueResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }*/
    }
}
