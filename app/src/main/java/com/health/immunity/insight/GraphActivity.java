package com.health.immunity.insight;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.devs.readmoreoption.ReadMoreOption;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.health.immunity.BaseActivity;
import com.health.immunity.CommonUtils;
import com.health.immunity.HomeContainer.HomeActivity;
import com.health.immunity.IConstant;
import com.health.immunity.PreferenceHelper;
import com.health.immunity.R;
import com.health.immunity.insight.model.GraphResponse;
import com.health.immunity.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GraphActivity extends BaseActivity implements View.OnClickListener{

    public final static String[] days = new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun",};
    private LineChartView chartTop;
    private LineChartData lineData;
    ImageView ivHeader,tvfilter;
    TextView tvreference,ivNavBar,tvlastmo,tvlastw,tvyester,tv1;
    LineChart combinedChart;
    public static final String KEY_Email = "patientId";
    public static final String KEY_Token = "flag";
    String Token,patient_id,Visitid;
    ArrayList<String> x;
    ArrayList<String> x1;
    ArrayList<String> xhealthylower;
    ArrayList<String> xhealthyupper;

    ArrayList<Entry> y;
    ArrayList<Entry> y1;
    ArrayList<Entry> yhealthylower;
    ArrayList<Entry> yhealthyupper;

    private LineChart mChart;
    float lower,higher;
    private String userstr="",agestr="",genderstr="",communitystr="";
    int LAUNCH_SECOND_ACTIVITY = 1;
    String chckcallgraph="day";
    boolean compareset=false;

    View viewtop,rectangle_color;
    TextView myrange,healthyrange,tvloren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        agestr= CommonUtils.communityage;
        userstr= CommonUtils.communityuser;
        genderstr=  CommonUtils.communitygender;
        communitystr= CommonUtils.communitycomm;



        higher= getIntent().getFloatExtra("healthyupeer",0);
        lower= getIntent().getFloatExtra("healthylower",0);

        System.out.println("################h"+higher);
        System.out.println("################l"+lower);
        System.out.println("################lage"+agestr);
        System.out.println("################luser"+userstr);

        x = new ArrayList<String>();
        x1 = new ArrayList<String>();
        xhealthylower = new ArrayList<String>();
        xhealthyupper = new ArrayList<String>();
        y = new ArrayList<Entry>();
        y1 = new ArrayList<Entry>();
        yhealthylower = new ArrayList<Entry>();
        yhealthyupper = new ArrayList<Entry>();
        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.getLegend().setEnabled(false);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisRight().setDrawGridLines(false);
        mChart.setDrawGridBackground(false);
        mChart.setDescription("");
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(true);
        mChart.getXAxis().setTextSize(10f);
        mChart.getAxisLeft().setTextSize(10f);
        XAxis xl = mChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setAvoidFirstLastClipping(true);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setInverted(false);
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        ivHeader = (ImageView) findViewById(R.id.ivHeader);
        ivHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.lastAct="Graph";
                onBackPressed();
//                finish();
//                Intent intent=new Intent(context,HomeActivity.class);
//                intent.putExtra("lastAct","graph");
//                startActivity(intent);

            }
        });

        ivNavBar = (TextView) findViewById(R.id.ivNavBar);
        tvreference = (TextView) findViewById(R.id.tvreference);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("getrangesource")))
        {
            tvreference.setVisibility(View.VISIBLE);
        }
        else
        {
            tvreference.setVisibility(View.INVISIBLE);

        }
        tvreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getIntent().getStringExtra("getrangesource")));
                startActivity(browserIntent);
            }
        });
        TextView comparetv = (TextView) findViewById(R.id.comparetv);
        healthyrange = (TextView) findViewById(R.id.healthyrangecom);
        rectangle_color = (View) findViewById(R.id.rectangle_colorcom);
        tvlastmo = (TextView) findViewById(R.id.tvlastmo);
        tvlastw = (TextView) findViewById(R.id.tvlastw);
        tvyester = (TextView) findViewById(R.id.tvyester);
        tv1 = (TextView) findViewById(R.id.tv1);
        myrange = (TextView) findViewById(R.id.myrange);
        tvloren = (TextView) findViewById(R.id.tvloren);
        viewtop = (View) findViewById(R.id.rectangle_at_the_top);
        tvfilter = (ImageView) findViewById(R.id.tvfilter);
        tvfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,FilterActivity.class);
                intent.putExtra("kpi_id",getIntent().getStringExtra("kpi_id"));

                startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);

            }
        });

        tv1.setText(getIntent().getStringExtra("decs"));
        System.out.println("########################123"+getIntent().getStringExtra("toolbarname"));
        // makeTextViewResizable(tv1, 3, "See More", true);





        ReadMoreOption readMoreOption = new ReadMoreOption.Builder(this)
                // Optional parameters
                .textLength(3, ReadMoreOption.TYPE_LINE) //OR
                //.textLength(300, ReadMoreOption.TYPE_CHARACTER)
                .moreLabel(" View more ")
                .lessLabel(" View less ")
                  .moreLabelColor(Color.parseColor("#8775ed"))
                   .lessLabelColor(Color.parseColor("#8775ed"))
                   .labelUnderLine(true)
                .expandAnimation(true)
                .build();
        readMoreOption.addReadMoreTo(tv1, getIntent().getStringExtra("decs"));

        tvlastmo.setOnClickListener(this);
        tvlastw.setOnClickListener(this);
        tvyester.setOnClickListener(this);


        ivNavBar.setText(getIntent().getStringExtra("toolbarname"));

        Switch sw = (Switch) findViewById(R.id.swtchbtn);
        if (getIntent().getStringExtra("toolbarname").equals("Weight (kg)"))
        {
            comparetv.setVisibility(View.INVISIBLE);
            sw.setVisibility(View.INVISIBLE);

        }

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    if (TextUtils.isEmpty(agestr)&&TextUtils.isEmpty(genderstr)&&TextUtils.isEmpty(userstr))
                    {

                        tvloren.setText(Html.fromHtml("<font color='#000000'>" + "Your comparision set is "+ "</font>"+"All Ages"+ "<font color='#000000'>"+" & "+"</font>" +"All Genders"+ "<font color='#000000'>"+" & "+"</font>"+"All Immunity Users." ));

                    } else
                    {
                        if (userstr.equalsIgnoreCase("all"))
                        {
                            if (TextUtils.isEmpty(agestr))
                            {
                                CommonUtils.communityagename="All Ages";
                            }
                            if (TextUtils.isEmpty(genderstr))
                            {
                                CommonUtils.communitygendername="All Genders";
                            }


                            tvloren.setText(Html.fromHtml("<font color='#000000'>" + "Your comparision set is "+ "</font>"+CommonUtils.communityagename+ "<font color='#000000'>"+" & "+"</font>" +CommonUtils.communitygendername+ "<font color='#000000'>"+" & "+"</font>"+"All Immunity Users." ));

                        }

                        else
                        {

                            if (TextUtils.isEmpty(agestr))
                            {
                                CommonUtils.communityagename="All Ages";
                            }
                            if (TextUtils.isEmpty(genderstr))
                            {
                                CommonUtils.communitygendername="All Genders";
                            }
                            // tvloren.setText("Your comparision set is "+ agename+" & "+gendername+" & "+communityname);
                            //tvloren.setText(Html.fromHtml("Your comparision set is "+" "+"<font color='#ff4769'><b>" + agename+" & "+gendername+" & "+ communityname + "</b></font>"));
                            tvloren.setText(Html.fromHtml("<font color='#000000'>" + "Your comparision set is "+ "</font>"+CommonUtils.communityagename+ "<font color='#000000'>"+" & "+"</font>" +CommonUtils.communitygendername+ "<font color='#000000'>"+" & "+"</font>"+CommonUtils.communitycommname ));

                        }


                    }



                    tvloren.setVisibility(View.VISIBLE);

                    viewtop.setVisibility(View.VISIBLE);
                    myrange.setVisibility(View.VISIBLE);
                    tvfilter.setVisibility(View.VISIBLE);

                    compareset=true;
                    CommonUtils.compareset=true;
                    if (chckcallgraph.equals("week"))
                    {
                        GraphWeekApi("week",getIntent().getStringExtra("kpi_id"));
                    }
                    else   if (chckcallgraph.equals("month"))
                    {
                        GraphMonthApi("month",getIntent().getStringExtra("kpi_id"));
                    }
                    else if (chckcallgraph.equals("day"))

                    {
                        GraphDayApi("day",getIntent().getStringExtra("kpi_id"));
                    }

                } else {
                    tvloren.setVisibility(View.GONE);


                    viewtop.setVisibility(View.INVISIBLE);
                    myrange.setVisibility(View.INVISIBLE);
                    tvfilter.setVisibility(View.INVISIBLE);
                    compareset=false;
                    CommonUtils.compareset=false;
                    if (chckcallgraph.equals("week"))
                    {
                        GraphWeekApi("week",getIntent().getStringExtra("kpi_id"));
                    }
                    else   if (chckcallgraph.equals("month"))
                    {
                        GraphMonthApi("month",getIntent().getStringExtra("kpi_id"));
                    }
                    else if (chckcallgraph.equals("day"))

                    {
                        GraphDayApi("day",getIntent().getStringExtra("kpi_id"));
                    }




                }
            }
        });


        if (CommonUtils.compareset) {

            sw.setChecked(true);
            compareset = true;
            if (getIntent().getStringExtra("toolbarname").equals("Weight (kg)")) {
                viewtop.setVisibility(View.INVISIBLE);
                myrange.setVisibility(View.INVISIBLE);
                tvfilter.setVisibility(View.INVISIBLE);
                tvloren.setVisibility(View.INVISIBLE);

            } else {

                viewtop.setVisibility(View.VISIBLE);
                myrange.setVisibility(View.VISIBLE);
                tvfilter.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            GraphDayApi("day",getIntent().getStringExtra("kpi_id"));
            sw.setChecked(false);
            compareset=false;
            viewtop.setVisibility(View.INVISIBLE);
            myrange.setVisibility(View.INVISIBLE);
            tvfilter.setVisibility(View.INVISIBLE);

        }

      /*  if (!TextUtils.isEmpty(agestr)||!TextUtils.isEmpty(genderstr)||!TextUtils.isEmpty(userstr)||!TextUtils.isEmpty(communitystr))
        {
            viewtop.setVisibility(View.VISIBLE);
            myrange.setVisibility(View.VISIBLE);
            tvfilter.setVisibility(View.VISIBLE);
            compareset=true;

        }
        else {
            viewtop.setVisibility(View.INVISIBLE);
            myrange.setVisibility(View.INVISIBLE);
            tvfilter.setVisibility(View.INVISIBLE);
            compareset=false;

        }*/
//        GraphDayApi("day",getIntent().getStringExtra("kpi_id"));


    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent=new Intent(getBaseContext(), HomeActivity.class);
        intent.putExtra("BackgraphCount",true);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
              /*  userstr=data.getStringExtra("user");
                agestr=data.getStringExtra("age");
                genderstr=data.getStringExtra("gender");
                communitystr=data.getStringExtra("commun");
*/
                userstr=data.getStringExtra("user");
                agestr=data.getStringExtra("age");
                String  agename=data.getStringExtra("agename");
                genderstr=data.getStringExtra("gender");
                String  gendername=data.getStringExtra("gendername");
                communitystr=data.getStringExtra("commun");
                String  communityname=data.getStringExtra("communname");

                if (TextUtils.isEmpty(agestr)&&TextUtils.isEmpty(genderstr)&&!TextUtils.isEmpty(userstr))
                {
                    //   binding.tvloren.setText("Your comparision set is All Ages , All Genders , All Immunity Users.");
                    tvloren.setText(Html.fromHtml("<font color='#000000'>" + "Your comparision set is "+ "</font>"+"All Ages"+ "<font color='#000000'>"+" & "+"</font>" +"All Genders"+ "<font color='#000000'>"+" & "+"</font>"+"All Immunity Users." ));

                }

                else
                {
                    if (userstr.equalsIgnoreCase("all"))
                    {
                        if (TextUtils.isEmpty(agestr))
                        {
                            agename="All Ages";
                        }
                        if (TextUtils.isEmpty(genderstr))
                        {
                            gendername="All Genders";
                        }


                        tvloren.setText(Html.fromHtml("<font color='#000000'>" + "Your comparision set is "+ "</font>"+agename+ "<font color='#000000'>"+" & "+"</font>" +gendername+ "<font color='#000000'>"+" & "+"</font>"+"All Immunity Users." ));

                    }

                    else
                    {

                        if (TextUtils.isEmpty(agestr))
                        {
                            agename="All Ages";
                        }
                        if (TextUtils.isEmpty(genderstr))
                        {
                            gendername="All Genders";
                        }
                        //  tvloren.setText("Your comparision set is "+ agename+" & "+gendername+" & "+communityname);
                        // tvloren.setText(Html.fromHtml("Your comparision set is "+" "+"<font color='#ff4769'><b>" + agename+" & "+gendername+" & "+ communityname + "</b></font>"));
                        tvloren.setText(Html.fromHtml("<font color='#000000'>" + "Your comparision set is "+ "</font>"+agename+ "<font color='#000000'>"+" & "+"</font>" +gendername+ "<font color='#000000'>"+" & "+"</font>"+communityname ));

                    }


                }



                if (chckcallgraph.equals("week"))
                {
                    GraphWeekApi("week",data.getStringExtra("kpi_id"));
                }
                else   if (chckcallgraph.equals("month"))
                {
                    GraphMonthApi("month",data.getStringExtra("kpi_id"));
                }
                else if (chckcallgraph.equals("day"))
                {
                    GraphDayApi("day",data.getStringExtra("kpi_id"));
                }


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    private void GraphDayApi(String type, String id) {
        y.clear();
        x.clear();
        xhealthyupper.clear();
        yhealthyupper.clear();
        yhealthylower.clear();
        xhealthylower.clear();
        x1.clear();
        y1.clear();
        mChart.invalidate();
        mChart.clear();
        mChart.removeAllViews();
        mChart.notifyDataSetChanged();
      //  CommonUtils.showSpotoProgressDialog(context);
        Call<GraphResponse> call = RetrofitClient.getUniqInstance().getApi()
                .GraphResponseCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), type, id,userstr,agestr,genderstr,communitystr);
        call.enqueue(new Callback<GraphResponse>() {
            @Override
            public void onResponse(Call<GraphResponse> call, Response<GraphResponse> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            if (response.body().getDaystatus().equals("false"))
                            {
                                //  Toast.makeText(context, "Please provide google fit permission", Toast.LENGTH_SHORT).show();

                            }
                            List<AxisValue> axisValues = new ArrayList<AxisValue>();
                            List<PointValue> values = new ArrayList<PointValue>();
                            List<Integer> list = new ArrayList<Integer>();
                            ArrayList<String> labels = new ArrayList<String> ();
                            ArrayList<Entry> line = new ArrayList<Entry> ();
                            for (int i = 0; i < response.body().getJsonData().getMyTrends().size(); ++i)
                            {
                                //if (!TextUtils.isEmpty(response.body().getJsonData().getMyTrends().get(i).getValue()))
                                {

                                    String score = String.valueOf(response.body().getJsonData().getMyTrends().get(i).getValue());
                                    String date = response.body().getJsonData().getMyTrends().get(i).getKey();
                                    x.add(date);
                                    xhealthylower.add(date);
                                    xhealthyupper.add(date);
                                    y.add(new Entry(Float.parseFloat(score), i));
                                    yhealthylower.add(new Entry(Float.parseFloat(String.valueOf(lower)), i));
                                    yhealthyupper.add(new Entry(Float.parseFloat(String.valueOf(higher)), i));

                                }

                      /*      list.add(response.body().getJsonData().get(i).getValue());
                            labels.add( response.body().getJsonData().get(i).getKey());
                            line.add(new Entry(i, response.body().getJsonData().get(i).getValue()));
                      */  }

                            for (int i = 0; i < response.body().getJsonData().getAllTrends().size(); ++i)
                            {
                                // if (!TextUtils.isEmpty(response.body().getJsonData().getAllTrends().get(i).getValue()))
                                {

                                    String score = String.valueOf(response.body().getJsonData().getAllTrends().get(i).getValue());
                                    String date = response.body().getJsonData().getAllTrends().get(i).getKey();
                                    x1.add(date);
                                    y1.add(new Entry(Float.parseFloat(score), i));
                                }

                            }

                            LineDataSet set1 = new LineDataSet(y, "");
                            set1.setColor(Color.parseColor("#8775ed"));
                            set1.setCircleColor(Color.parseColor("#8775ed"));
                            set1.setLineWidth(2f);
                            set1.setCircleRadius(4f);
                            set1.setDrawCircleHole(false);
                            set1.setValueTextSize(9f);
                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(set1);
                            if (compareset) {
                                if (response.body().getJsonData().getAllTrends().size() > 0)
                                {

                                    LineDataSet set11 = new LineDataSet(y1, "");
                                    set11.setColor(Color.parseColor("#D0A000"));
                                    set11.setCircleColor(Color.parseColor("#D0A000"));
                                    set11.setLineWidth(2f);
                                    set11.setCircleRadius(4f);
                                    set11.setDrawCircleHole(false);
                                    set11.setValueTextSize(9f);
                                    dataSets.add(set11);
                                    LineData data1 = new LineData(x1, dataSets);
                                    mChart.setData(data1);
                                    mChart.animateY(2000);
                                }
                                else {
                                    tvloren.setText("Comparative figures are only shown on dates where your personal data is available");

                                    LineData data = new LineData(x, dataSets);
                                    //dataSets.add(set1);
                                    mChart.setData(data);
                                    mChart.animateY(2000);
                                }
                            }
                            else
                            {
                                LineData data = new LineData(x, dataSets);
                                //dataSets.add(set1);
                                mChart.setData(data);
                                mChart.animateY(2000);
                            }
                            //  LineData data = new LineData(x, dataSets);
                            //  dataSets.add(set1);
                            // mChart.setData(data);
                            //mChart.invalidate();
                            //
                      /*  LineDataSet set11 = new LineDataSet(y1, "");
                        set11.setColor(Color.parseColor("#D0A000"));
                        set11.setCircleColor(Color.parseColor("#D0A000"));
                        set11.setLineWidth(2f);
                        set11.setCircleRadius(4f);
                        set11.setDrawCircleHole(false);
                        set11.setValueTextSize(9f);
                        dataSets.add(set11);
                        LineData data1 = new LineData(x1, dataSets);
                        mChart.setData(data1);
                        mChart.animateY(2000);*/
                            //mChart.invalidate();
                            //

                            if (getIntent().getStringExtra("limitchck").equals("yes")) {

                                if (getIntent().getStringExtra("toolbarname").equals("Distance (km)")) {
                                    healthyrange.setVisibility(View.INVISIBLE);
                                    rectangle_color.setVisibility(View.INVISIBLE);

                                } else if (getIntent().getStringExtra("toolbarname").equals("Social distancing ")) {
                                    healthyrange.setVisibility(View.INVISIBLE);
                                    rectangle_color.setVisibility(View.INVISIBLE);

                                } else {

                                    LineDataSet set11 = new LineDataSet(yhealthylower, "");
                                    set11.setColor(Color.parseColor("#47C6AB"));
                                    set11.setCircleColor(Color.parseColor("#47C6AB"));
                                    set11.setLineWidth(2f);
                                    set11.setCircleRadius(4f);
                                    set11.setDrawCircleHole(false);
                                    set11.setValueTextSize(9f);
                                    dataSets.add(set11);
                                    LineData data1 = new LineData(xhealthylower, dataSets);
//dataSets.add(set1);
                                    mChart.setData(data1);
                                    mChart.animateY(2000);


                                    LineDataSet set111 = new LineDataSet(yhealthyupper, "");
                                    set111.setColor(Color.parseColor("#47C6AB"));
                                    set111.setCircleColor(Color.parseColor("#47C6AB"));
                                    set111.setLineWidth(2f);
                                    set111.setCircleRadius(4f);
                                    set111.setDrawCircleHole(false);
                                    set111.setValueTextSize(9f);
                                    dataSets.add(set111);
                                    LineData data11 = new LineData(xhealthyupper, dataSets);
//dataSets.add(set1);
                                    mChart.setData(data11);
                                    mChart.animateY(2000);

                                }


                     /*       LimitLine ll1 = new LimitLine(higher, "");
                            ll1.setLineColor(Color.parseColor("#47C6AB"));
                            ll1.setLineWidth(4f);
                            ll1.enableDashedLine(10f, 10f, 0f);
                            ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                            ll1.setTextSize(10f);
                            LimitLine ll2 = new LimitLine(lower, "");
                            ll2.setLineColor(Color.parseColor("#47C6AB"));
                            ll2.setLineWidth(4f);
                            ll2.enableDashedLine(10f, 10f, 0f);
                            ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
                            ll2.setTextSize(10f);
                            YAxis leftAxis = mChart.getAxisLeft();
                            leftAxis.removeAllLimitLines();
                            leftAxis.addLimitLine(ll1);
                            leftAxis.addLimitLine(ll2);
                            leftAxis.enableGridDashedLine(10f, 10f, 0f);
                            leftAxis.setDrawZeroLine(false);
                            leftAxis.setDrawLimitLinesBehindData(false);
                            mChart.getAxisRight().setEnabled(false);*/

                            }
                            else
                            {
                                healthyrange.setVisibility(View.INVISIBLE);
                                rectangle_color.setVisibility(View.INVISIBLE);
                            }

                   /*     List<Entry> lineEntries = line;
                        LineDataSet lineDataSet = new LineDataSet(lineEntries, "My value");
                        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                        lineDataSet.setColors(Color.BLUE);
                        lineDataSet.setCircleRadius(5);
                        lineDataSet.setDrawHighlightIndicators(true);
                        lineDataSet.setHighLightColor(Color.BLUE);
                        LineData lineData = new LineData(lineDataSet);
                        combinedChart.setData(lineData);
                        XAxis xAxis = combinedChart.getXAxis();
                        xAxis.setGranularity(1f);
                        xAxis.setGranularityEnabled(true);
                        xAxis.setCenterAxisLabels(false);
                        xAxis.setDrawGridLines(false);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                        LimitLine ll1 = new LimitLine(215f, "Maximum Limit");
                        ll1.setLineWidth(4f);
                        ll1.enableDashedLine(10f, 10f, 0f);
                        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                        ll1.setTextSize(10f);
                        LimitLine ll2 = new LimitLine(70f, "Minimum Limit");
                        ll2.setLineWidth(4f);
                        ll2.enableDashedLine(10f, 10f, 0f);
                        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
                        ll2.setTextSize(10f);
                        YAxis leftAxis = combinedChart.getAxisLeft();
                        leftAxis.removeAllLimitLines();
                        leftAxis.addLimitLine(ll1);
                        leftAxis.addLimitLine(ll2);
                        leftAxis.setAxisMaximum(Collections.max(list));
                        leftAxis.setAxisMinimum(0f);
                        leftAxis.enableGridDashedLine(10f, 10f, 0f);
                        leftAxis.setDrawZeroLine(false);
                        leftAxis.setDrawLimitLinesBehindData(false);
                        combinedChart.getAxisRight().setEnabled(false);
*/
                  /*      Line line = new Line(values);
                        line.setColor(Color.parseColor("#8775ed")).setCubic(true);
                        List<Line> lines = new ArrayList<Line>();
                        lines.add(line);
                        lineData = new LineChartData(lines);
                        lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
                        lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(4));
                        chartTop.setLineChartData(lineData);
                        chartTop.setViewportCalculationEnabled(false);
                        Viewport v = new Viewport(0, Collections.max(list)+(Collections.max(list)/4), response.body().getJsonData().size(), 0);
                        chartTop.setMaximumViewport(v);
                        chartTop.setCurrentViewport(v);
                        chartTop.setZoomType(ZoomType.HORIZONTAL);

*/


                            //  Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();


                        }
                    }
                }
              //  CommonUtils.dismissSpotoProgressDialog();
            }

            @Override
            public void onFailure(Call<GraphResponse> call, Throwable t) {
                t.printStackTrace();
              //  CommonUtils.dismissSpotoProgressDialog();
            }
        });
    }

    private void GraphMonthApi(String type, String id) {
        y.clear();
        x.clear();
        x1.clear();
        y1.clear();
        xhealthyupper.clear();
        yhealthyupper.clear();
        yhealthylower.clear();
        xhealthylower.clear();
        mChart.invalidate();
        mChart.clear();
        mChart.removeAllViews();
        mChart.notifyDataSetChanged();
       // CommonUtils.showSpotoProgressDialog(context);
        Call<GraphResponse> call = RetrofitClient.getUniqInstance().getApi()
                .GraphResponseCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), type, id,userstr,agestr,genderstr,communitystr);
        call.enqueue(new Callback<GraphResponse>() {
            @Override
            public void onResponse(Call<GraphResponse> call, Response<GraphResponse> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            if (response.body().getMonthstatus().equals("false"))
                            {
                                //   Toast.makeText(context, "Please provide google fit permission", Toast.LENGTH_SHORT).show();

                            }
                            List<AxisValue> axisValues = new ArrayList<AxisValue>();
                            List<PointValue> values = new ArrayList<PointValue>();
                            List<Integer> list = new ArrayList<Integer>();
                            ArrayList<String> labels = new ArrayList<String> ();
                            ArrayList<Entry> line = new ArrayList<Entry> ();
                            for (int i = 0; i < response.body().getJsonData().getMyTrends().size(); ++i)
                            {
                                //  if (!TextUtils.isEmpty(response.body().getJsonData().getMyTrends().get(i).getValue()))
                                {

                                    String score = String.valueOf(response.body().getJsonData().getMyTrends().get(i).getValue());
                                    String date = response.body().getJsonData().getMyTrends().get(i).getKey();
                                    x.add(date);
                                    //     y.add(new Entry(Float.parseFloat(score), i));
                                    xhealthylower.add(date);
                                    xhealthyupper.add(date);
                                    y.add(new Entry(Float.parseFloat(score), i));
                                    yhealthylower.add(new Entry(Float.parseFloat(String.valueOf(lower)), i));
                                    yhealthyupper.add(new Entry(Float.parseFloat(String.valueOf(higher)), i));

                                }

                      /*      list.add(response.body().getJsonData().get(i).getValue());
                            labels.add( response.body().getJsonData().get(i).getKey());
                            line.add(new Entry(i, response.body().getJsonData().get(i).getValue()));
                      */  }

                            for (int i = 0; i < response.body().getJsonData().getAllTrends().size(); ++i)
                            {
                                // if (!TextUtils.isEmpty(response.body().getJsonData().getAllTrends().get(i).getValue()))
                                {

                                    String score = String.valueOf(response.body().getJsonData().getAllTrends().get(i).getValue());
                                    String date = response.body().getJsonData().getAllTrends().get(i).getKey();
                                    x1.add(date);
                                    y1.add(new Entry(Float.parseFloat(score), i));
                                }

                            }

                            LineDataSet set1 = new LineDataSet(y, "");
                            set1.setColor(Color.parseColor("#8775ed"));
                            set1.setCircleColor(Color.parseColor("#8775ed"));
                            set1.setLineWidth(2f);
                            set1.setCircleRadius(4f);
                            set1.setDrawCircleHole(false);
                            set1.setValueTextSize(9f);
                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(set1);
                            if (compareset)
                            {
                                if (response.body().getJsonData().getAllTrends().size() > 0)
                                {
                                    LineDataSet set11 = new LineDataSet(y1, "");
                                    set11.setColor(Color.parseColor("#D0A000"));
                                    set11.setCircleColor(Color.parseColor("#D0A000"));
                                    set11.setLineWidth(2f);
                                    set11.setCircleRadius(4f);
                                    set11.setDrawCircleHole(false);
                                    set11.setValueTextSize(9f);
                                    dataSets.add(set11);
                                    LineData data1 = new LineData(x1, dataSets);
                                    mChart.setData(data1);
                                    mChart.animateY(2000);

                                }
                                else {
                                    tvloren.setText("Comparative figures are only shown on dates where your personal data is available");

                                    LineData data = new LineData(x, dataSets);
                                    //dataSets.add(set1);
                                    mChart.setData(data);
                                    mChart.animateY(2000);
                                }
                            }
                            else
                            {
                                LineData data = new LineData(x, dataSets);
                                //dataSets.add(set1);
                                mChart.setData(data);
                                mChart.animateY(2000);
                            }
                            //  LineData data = new LineData(x, dataSets);
                            //mChart.setData(data);
                            //      mChart.invalidate();

                          /*  LineDataSet set11 = new LineDataSet(y1, "");
                            set11.setColor(Color.parseColor("#D0A000"));
                            set11.setCircleColor(Color.parseColor("#D0A000"));
                            set11.setLineWidth(2f);
                            set11.setCircleRadius(4f);
                            set11.setDrawCircleHole(false);
                            set11.setValueTextSize(9f);
                            dataSets.add(set11);
                            LineData data1 = new LineData(x1, dataSets);
                            mChart.setData(data1);
                            mChart.animateY(2000);
*/
                            if (getIntent().getStringExtra("limitchck").equals("yes")) {

                                if (getIntent().getStringExtra("toolbarname").equals("Distance (km)")) {
                                    healthyrange.setVisibility(View.INVISIBLE);
                                    rectangle_color.setVisibility(View.INVISIBLE);

                                } else if (getIntent().getStringExtra("toolbarname").equals("Social distancing ")) {
                                    healthyrange.setVisibility(View.INVISIBLE);
                                    rectangle_color.setVisibility(View.INVISIBLE);

                                } else {


                                    LineDataSet set11 = new LineDataSet(yhealthylower, "");
                                    set11.setColor(Color.parseColor("#47C6AB"));
                                    set11.setCircleColor(Color.parseColor("#47C6AB"));
                                    set11.setLineWidth(2f);
                                    set11.setCircleRadius(4f);
                                    set11.setDrawCircleHole(false);
                                    set11.setValueTextSize(9f);
                                    dataSets.add(set11);
                                    LineData data1 = new LineData(xhealthylower, dataSets);
//dataSets.add(set1);
                                    mChart.setData(data1);
                                    mChart.animateY(2000);


                                    LineDataSet set111 = new LineDataSet(yhealthyupper, "");
                                    set111.setColor(Color.parseColor("#47C6AB"));
                                    set111.setCircleColor(Color.parseColor("#47C6AB"));
                                    set111.setLineWidth(2f);
                                    set111.setCircleRadius(4f);
                                    set111.setDrawCircleHole(false);
                                    set111.setValueTextSize(9f);
                                    dataSets.add(set111);
                                    LineData data11 = new LineData(xhealthyupper, dataSets);
//dataSets.add(set1);
                                    mChart.setData(data11);
                                    mChart.animateY(2000);

                                }


                           /*     LimitLine ll1 = new LimitLine(higher, "");
                                ll1.setLineColor(Color.parseColor("#47C6AB"));
                                ll1.setLineWidth(4f);
                                ll1.enableDashedLine(10f, 10f, 0f);
                                ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                                ll1.setTextSize(10f);
                                LimitLine ll2 = new LimitLine(lower, "");
                                ll2.setLineColor(Color.parseColor("#47C6AB"));
                                ll2.setLineWidth(4f);
                                ll2.enableDashedLine(10f, 10f, 0f);
                                ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
                                ll2.setTextSize(10f);
                                YAxis leftAxis = mChart.getAxisLeft();
                                leftAxis.removeAllLimitLines();
                                leftAxis.addLimitLine(ll1);
                                leftAxis.addLimitLine(ll2);
                                leftAxis.enableGridDashedLine(10f, 10f, 0f);
                                leftAxis.setDrawZeroLine(false);
                                leftAxis.setDrawLimitLinesBehindData(false);
                                mChart.getAxisRight().setEnabled(false);*/

                            }
                            else
                            {
                                healthyrange.setVisibility(View.INVISIBLE);
                                rectangle_color.setVisibility(View.INVISIBLE);
                            }



                   /*     List<Entry> lineEntries = line;
                        LineDataSet lineDataSet = new LineDataSet(lineEntries, "My value");
                        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                        lineDataSet.setColors(Color.BLUE);
                        lineDataSet.setCircleRadius(5);
                        lineDataSet.setDrawHighlightIndicators(true);
                        lineDataSet.setHighLightColor(Color.BLUE);
                        LineData lineData = new LineData(lineDataSet);
                        combinedChart.setData(lineData);
                        XAxis xAxis = combinedChart.getXAxis();
                        xAxis.setGranularity(1f);
                        xAxis.setGranularityEnabled(true);
                        xAxis.setCenterAxisLabels(false);
                        xAxis.setDrawGridLines(false);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                        LimitLine ll1 = new LimitLine(215f, "Maximum Limit");
                        ll1.setLineWidth(4f);
                        ll1.enableDashedLine(10f, 10f, 0f);
                        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                        ll1.setTextSize(10f);
                        LimitLine ll2 = new LimitLine(70f, "Minimum Limit");
                        ll2.setLineWidth(4f);
                        ll2.enableDashedLine(10f, 10f, 0f);
                        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
                        ll2.setTextSize(10f);
                        YAxis leftAxis = combinedChart.getAxisLeft();
                        leftAxis.removeAllLimitLines();
                        leftAxis.addLimitLine(ll1);
                        leftAxis.addLimitLine(ll2);
                        leftAxis.setAxisMaximum(Collections.max(list));
                        leftAxis.setAxisMinimum(0f);
                        leftAxis.enableGridDashedLine(10f, 10f, 0f);
                        leftAxis.setDrawZeroLine(false);
                        leftAxis.setDrawLimitLinesBehindData(false);
                        combinedChart.getAxisRight().setEnabled(false);
*/
                  /*      Line line = new Line(values);
                        line.setColor(Color.parseColor("#8775ed")).setCubic(true);
                        List<Line> lines = new ArrayList<Line>();
                        lines.add(line);
                        lineData = new LineChartData(lines);
                        lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
                        lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(4));
                        chartTop.setLineChartData(lineData);
                        chartTop.setViewportCalculationEnabled(false);
                        Viewport v = new Viewport(0, Collections.max(list)+(Collections.max(list)/4), response.body().getJsonData().size(), 0);
                        chartTop.setMaximumViewport(v);
                        chartTop.setCurrentViewport(v);
                        chartTop.setZoomType(ZoomType.HORIZONTAL);

*/


                            //     Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();


                        }
                    }
                }
              //  CommonUtils.dismissSpotoProgressDialog();
            }

            @Override
            public void onFailure(Call<GraphResponse> call, Throwable t) {
                t.printStackTrace();
              //  CommonUtils.dismissSpotoProgressDialog();
            }
        });
    }

    private void GraphWeekApi(String type, String id) {
        xhealthyupper.clear();
        yhealthyupper.clear();
        yhealthylower.clear();
        xhealthylower.clear();
        y.clear();
        x.clear();
        x1.clear();
        y1.clear();
        mChart.invalidate();
        mChart.clear();
        mChart.removeAllViews();
        mChart.notifyDataSetChanged();
       // CommonUtils.showSpotoProgressDialog(context);
        Call<GraphResponse> call = RetrofitClient.getUniqInstance().getApi()
                .GraphResponseCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), type, id,userstr,agestr,genderstr,communitystr);
        call.enqueue(new Callback<GraphResponse>() {
            @Override
            public void onResponse(Call<GraphResponse> call, Response<GraphResponse> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            if (response.body().getWeekstatus().equals("false"))
                            {
                                //  Toast.makeText(context, "Please provide google fit permission", Toast.LENGTH_SHORT).show();

                            }
                            List<AxisValue> axisValues = new ArrayList<AxisValue>();
                            List<PointValue> values = new ArrayList<PointValue>();
                            List<Integer> list = new ArrayList<Integer>();
                            ArrayList<String> labels = new ArrayList<String> ();
                            ArrayList<Entry> line = new ArrayList<Entry> ();
                            for (int i = 0; i < response.body().getJsonData().getMyTrends().size(); ++i)
                            {
                                //if (!TextUtils.isEmpty(response.body().getJsonData().getMyTrends().get(i).getValue()))
                                {

                                    String score = String.valueOf(response.body().getJsonData().getMyTrends().get(i).getValue());
                                    String date = response.body().getJsonData().getMyTrends().get(i).getKey();
                                    x.add(date);
                                    //y.add(new Entry(Float.parseFloat(score), i));
                                    xhealthylower.add(date);
                                    xhealthyupper.add(date);
                                    y.add(new Entry(Float.parseFloat(score), i));
                                    yhealthylower.add(new Entry(Float.parseFloat(String.valueOf(lower)), i));
                                    yhealthyupper.add(new Entry(Float.parseFloat(String.valueOf(higher)), i));

                                }

                      /*      list.add(response.body().getJsonData().get(i).getValue());
                            labels.add( response.body().getJsonData().get(i).getKey());
                            line.add(new Entry(i, response.body().getJsonData().get(i).getValue()));
                      */  }

                            for (int i = 0; i < response.body().getJsonData().getAllTrends().size(); ++i)
                            {
                                //if (!TextUtils.isEmpty(response.body().getJsonData().getAllTrends().get(i).getValue()))
                                {

                                    String score = String.valueOf(response.body().getJsonData().getAllTrends().get(i).getValue());
                                    String date = response.body().getJsonData().getAllTrends().get(i).getKey();
                                    x1.add(date);
                                    y1.add(new Entry(Float.parseFloat(score), i));
                                }

                            }

                            LineDataSet set1 = new LineDataSet(y, "");
                            set1.setColor(Color.parseColor("#8775ed"));
                            set1.setCircleColor(Color.parseColor("#8775ed"));
                            set1.setLineWidth(2f);
                            set1.setCircleRadius(4f);
                            set1.setDrawCircleHole(false);
                            set1.setValueTextSize(9f);
                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(set1);
                            if (compareset)
                            {
                                if (response.body().getJsonData().getAllTrends().size() > 0)
                                {
                                    LineDataSet set11 = new LineDataSet(y1, "");
                                    set11.setColor(Color.parseColor("#D0A000"));
                                    set11.setCircleColor(Color.parseColor("#D0A000"));
                                    set11.setLineWidth(2f);
                                    set11.setCircleRadius(4f);
                                    set11.setDrawCircleHole(false);
                                    set11.setValueTextSize(9f);
                                    dataSets.add(set11);
                                    LineData data1 = new LineData(x1, dataSets);
                                    mChart.setData(data1);
                                    mChart.animateY(2000);
                                }
                                else {
                                    tvloren.setText("Comparative figures are only shown on dates where your personal data is available");

                                    LineData data = new LineData(x, dataSets);
                                    //dataSets.add(set1);
                                    mChart.setData(data);
                                    mChart.animateY(2000);
                                }
                            }
                            else
                            {
                                LineData data = new LineData(x, dataSets);
                                //dataSets.add(set1);
                                mChart.setData(data);
                                mChart.animateY(2000);
                            }
                            //LineData data = new LineData(x, dataSets);
                            //mChart.setData(data);
                            //  mChart.invalidate();

                         /*   LineDataSet set11 = new LineDataSet(y1, "");
                            set11.setColor(Color.parseColor("#D0A000"));
                            set11.setCircleColor(Color.parseColor("#D0A000"));
                            set11.setLineWidth(2f);
                            set11.setCircleRadius(4f);
                            set11.setDrawCircleHole(false);
                            set11.setValueTextSize(9f);
                            dataSets.add(set11);
                            LineData data1 = new LineData(x1, dataSets);
                            mChart.setData(data1);
                            mChart.animateY(2000);
*/
                            if (getIntent().getStringExtra("limitchck").equals("yes")) {

                                if (getIntent().getStringExtra("toolbarname").equals("Distance (km)")) {
                                    healthyrange.setVisibility(View.INVISIBLE);
                                    rectangle_color.setVisibility(View.INVISIBLE);

                                } else if (getIntent().getStringExtra("toolbarname").equals("Social distancing ")) {
                                    healthyrange.setVisibility(View.INVISIBLE);
                                    rectangle_color.setVisibility(View.INVISIBLE);

                                } else {


                                    LineDataSet set11 = new LineDataSet(yhealthylower, "");
                                    set11.setColor(Color.parseColor("#47C6AB"));
                                    set11.setCircleColor(Color.parseColor("#47C6AB"));
                                    set11.setLineWidth(2f);
                                    set11.setCircleRadius(4f);
                                    set11.setDrawCircleHole(false);
                                    set11.setValueTextSize(9f);
                                    dataSets.add(set11);
                                    LineData data1 = new LineData(xhealthylower, dataSets);
//dataSets.add(set1);
                                    mChart.setData(data1);
                                    mChart.animateY(2000);


                                    LineDataSet set111 = new LineDataSet(yhealthyupper, "");
                                    set111.setColor(Color.parseColor("#47C6AB"));
                                    set111.setCircleColor(Color.parseColor("#47C6AB"));
                                    set111.setLineWidth(2f);
                                    set111.setCircleRadius(4f);
                                    set111.setDrawCircleHole(false);
                                    set111.setValueTextSize(9f);
                                    dataSets.add(set111);
                                    LineData data11 = new LineData(xhealthyupper, dataSets);
//dataSets.add(set1);
                                    mChart.setData(data11);
                                    mChart.animateY(2000);

                                }

                            /*    LimitLine ll1 = new LimitLine(higher, "");
                                ll1.setLineColor(Color.parseColor("#47C6AB"));
                                ll1.setLineWidth(4f);
                                ll1.enableDashedLine(10f, 10f, 0f);
                                ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                                ll1.setTextSize(10f);
                                LimitLine ll2 = new LimitLine(lower, "");
                                ll2.setLineColor(Color.parseColor("#47C6AB"));
                                ll2.setLineWidth(4f);
                                ll2.enableDashedLine(10f, 10f, 0f);
                                ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
                                ll2.setTextSize(10f);
                                YAxis leftAxis = mChart.getAxisLeft();
                                leftAxis.removeAllLimitLines();
                                leftAxis.addLimitLine(ll1);
                                leftAxis.addLimitLine(ll2);
                                leftAxis.enableGridDashedLine(10f, 10f, 0f);
                                leftAxis.setDrawZeroLine(false);
                                leftAxis.setDrawLimitLinesBehindData(false);
                                mChart.getAxisRight().setEnabled(false);*/

                            }

                            else
                            {
                                healthyrange.setVisibility(View.INVISIBLE);
                                rectangle_color.setVisibility(View.INVISIBLE);
                            }

                   /*     List<Entry> lineEntries = line;
                        LineDataSet lineDataSet = new LineDataSet(lineEntries, "My value");
                        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                        lineDataSet.setColors(Color.BLUE);
                        lineDataSet.setCircleRadius(5);
                        lineDataSet.setDrawHighlightIndicators(true);
                        lineDataSet.setHighLightColor(Color.BLUE);
                        LineData lineData = new LineData(lineDataSet);
                        combinedChart.setData(lineData);
                        XAxis xAxis = combinedChart.getXAxis();
                        xAxis.setGranularity(1f);
                        xAxis.setGranularityEnabled(true);
                        xAxis.setCenterAxisLabels(false);
                        xAxis.setDrawGridLines(false);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                        LimitLine ll1 = new LimitLine(215f, "Maximum Limit");
                        ll1.setLineWidth(4f);
                        ll1.enableDashedLine(10f, 10f, 0f);
                        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                        ll1.setTextSize(10f);
                        LimitLine ll2 = new LimitLine(70f, "Minimum Limit");
                        ll2.setLineWidth(4f);
                        ll2.enableDashedLine(10f, 10f, 0f);
                        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
                        ll2.setTextSize(10f);
                        YAxis leftAxis = combinedChart.getAxisLeft();
                        leftAxis.removeAllLimitLines();
                        leftAxis.addLimitLine(ll1);
                        leftAxis.addLimitLine(ll2);
                        leftAxis.setAxisMaximum(Collections.max(list));
                        leftAxis.setAxisMinimum(0f);
                        leftAxis.enableGridDashedLine(10f, 10f, 0f);
                        leftAxis.setDrawZeroLine(false);
                        leftAxis.setDrawLimitLinesBehindData(false);
                        combinedChart.getAxisRight().setEnabled(false);
*/
                  /*      Line line = new Line(values);
                        line.setColor(Color.parseColor("#8775ed")).setCubic(true);
                        List<Line> lines = new ArrayList<Line>();
                        lines.add(line);
                        lineData = new LineChartData(lines);
                        lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
                        lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(4));
                        chartTop.setLineChartData(lineData);
                        chartTop.setViewportCalculationEnabled(false);
                        Viewport v = new Viewport(0, Collections.max(list)+(Collections.max(list)/4), response.body().getJsonData().size(), 0);
                        chartTop.setMaximumViewport(v);
                        chartTop.setCurrentViewport(v);
                        chartTop.setZoomType(ZoomType.HORIZONTAL);

*/


                            //   Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();


                        }
                    }
                }
             //   CommonUtils.dismissSpotoProgressDialog();
            }

            @Override
            public void onFailure(Call<GraphResponse> call, Throwable t) {
                t.printStackTrace();
                CommonUtils.dismissSpotoProgressDialog();
            }
        });



    }

    ///
   /* private void GraphWeekApi(String type, String id) {
        CommonUtils.showSpotoProgressDialog(context);
        Call<GraphResponse> call = RetrofitClient.getUniqInstance().getApi()
                .GraphResponseCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), type, id);
        call.enqueue(new Callback<GraphResponse>() {
            @Override
            public void onResponse(Call<GraphResponse> call, Response<GraphResponse> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            int numValues = 7;
                            List<AxisValue> axisValues = new ArrayList<AxisValue>();
                            List<PointValue> values = new ArrayList<PointValue>();
                            List<Integer> list = new ArrayList<Integer>();

                            for (int i = 0; i < response.body().getJsonData().size(); ++i)
                            {
                                values.add(new PointValue(i, response.body().getJsonData().get(i).getValue()));
                                axisValues.add(new AxisValue(i).setLabel(response.body().getJsonData().get(i).getKey()));
                                list.add(response.body().getJsonData().get(i).getValue());

                            }




                            System.out.println("#################"+ Collections.max(list));


                            Line line = new Line(values);
                            line.setColor(Color.parseColor("#8775ed")).setCubic(true);
                            List<Line> lines = new ArrayList<Line>();
                            lines.add(line);
                            lineData = new LineChartData(lines);
                            lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
                            lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));
                            chartTop.setLineChartData(lineData);
                            chartTop.setViewportCalculationEnabled(false);
                            Viewport v = new Viewport(0, Collections.max(list)+(Collections.max(list)/4), response.body().getJsonData().size(), 0);
                            chartTop.setMaximumViewport(v);
                            chartTop.setCurrentViewport(v);
                            chartTop.setZoomType(ZoomType.HORIZONTAL);




                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();


                        }
                    }
                }
                CommonUtils.dismissSpotoProgressDialog();
            }

            @Override
            public void onFailure(Call<GraphResponse> call, Throwable t) {
                t.printStackTrace();
                CommonUtils.dismissSpotoProgressDialog();
            }
        });
    }
    ///
    private void GraphMonthApi(String type, String id) {
        CommonUtils.showSpotoProgressDialog(context);
        Call<GraphResponse> call = RetrofitClient.getUniqInstance().getApi()
                .GraphResponseCall("Bearer " + PreferenceHelper.getStringPreference(context, IConstant.TOKEN), type, id);
        call.enqueue(new Callback<GraphResponse>() {
            @Override
            public void onResponse(Call<GraphResponse> call, Response<GraphResponse> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            int numValues = 7;
                            List<AxisValue> axisValues = new ArrayList<AxisValue>();
                            List<PointValue> values = new ArrayList<PointValue>();
                            List<Integer> list = new ArrayList<Integer>();

                            for (int i = 0; i < response.body().getJsonData().size(); ++i)
                            {
                                values.add(new PointValue(i, response.body().getJsonData().get(i).getValue()));
                                axisValues.add(new AxisValue(i).setLabel(response.body().getJsonData().get(i).getKey()));
                                list.add(response.body().getJsonData().get(i).getValue());

                            }
                            Line line = new Line(values);
                            line.setColor(Color.parseColor("#8775ed")).setCubic(true);
                            List<Line> lines = new ArrayList<Line>();
                            lines.add(line);
                            lineData = new LineChartData(lines);
                            lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
                            lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));
                            chartTop.setLineChartData(lineData);
                            chartTop.setViewportCalculationEnabled(false);
                            Viewport v = new Viewport(0, Collections.max(list)+(Collections.max(list)/4), response.body().getJsonData().size(), 0);
                            chartTop.setMaximumViewport(v);
                            chartTop.setCurrentViewport(v);
                            chartTop.setZoomType(ZoomType.HORIZONTAL);




                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();


                        }
                    }
                }
                CommonUtils.dismissSpotoProgressDialog();
            }

            @Override
            public void onFailure(Call<GraphResponse> call, Throwable t) {
                t.printStackTrace();
                CommonUtils.dismissSpotoProgressDialog();
            }
        });
    }*/


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvyester:

                chckcallgraph="day";

                GraphDayApi("day",getIntent().getStringExtra("kpi_id"));
                tvyester.setBackground(getResources().getDrawable(R.drawable.button_background));
                tvyester.setTextColor(getResources().getColor(R.color.white));

                tvlastw.setTextColor(getResources().getColor(R.color.white));
                tvlastw.setBackground(getResources().getDrawable(R.drawable.background_graph));

                tvlastmo.setTextColor(getResources().getColor(R.color.white));
                tvlastmo.setBackground(getResources().getDrawable(R.drawable.background_graph));

                break;
            case R.id.tvlastw:

                chckcallgraph="week";

                GraphWeekApi("week",getIntent().getStringExtra("kpi_id"));

                tvlastw.setBackground(getResources().getDrawable(R.drawable.button_background));
                tvlastw.setTextColor(getResources().getColor(R.color.white));

                tvyester.setTextColor(getResources().getColor(R.color.white));
                tvyester.setBackground(getResources().getDrawable(R.drawable.background_graph));

                tvlastmo.setTextColor(getResources().getColor(R.color.white));
                tvlastmo.setBackground(getResources().getDrawable(R.drawable.background_graph));


                break;
            case R.id.tvlastmo:


                chckcallgraph="month";

                GraphMonthApi("month",getIntent().getStringExtra("kpi_id"));
                tvlastmo.setBackground(getResources().getDrawable(R.drawable.button_background));
                tvlastmo.setTextColor(getResources().getColor(R.color.white));



                tvlastw.setBackground(getResources().getDrawable(R.drawable.background_graph));
                tvlastw.setTextColor(getResources().getColor(R.color.white));

                tvyester.setTextColor(getResources().getColor(R.color.white));
                tvyester.setBackground(getResources().getDrawable(R.drawable.background_graph));

                break;

        }
    }

    public static class MySpannable extends ClickableSpan {

        private boolean isUnderline = true;

        /**
         * Constructor
         */
        public MySpannable(boolean isUnderline) {
            this.isUnderline = isUnderline;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(isUnderline);
            ds.setColor(Color.parseColor("#1b76d3"));
        }

        @Override
        public void onClick(View widget) {


        }
    }
    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new MySpannable(false){
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "See Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 3, ".. See More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

}


/*



import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.Fragment;

        import android.graphics.Color;
        import android.os.Bundle;

        import com.github.mikephil.charting.charts.CombinedChart;
        import com.github.mikephil.charting.charts.LineChart;
        import com.github.mikephil.charting.components.LimitLine;
        import com.github.mikephil.charting.components.XAxis;
        import com.github.mikephil.charting.components.YAxis;
        import com.github.mikephil.charting.data.BarData;
        import com.github.mikephil.charting.data.BarDataSet;
        import com.github.mikephil.charting.data.BarEntry;
        import com.github.mikephil.charting.data.CombinedData;
        import com.github.mikephil.charting.data.Entry;
        import com.github.mikephil.charting.data.LineData;
        import com.github.mikephil.charting.data.LineDataSet;
        import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
        import com.github.mikephil.charting.utils.ColorTemplate;
        import com.health.immunity.R;

        import java.util.ArrayList;
        import java.util.List;

public class GraphActivity extends AppCompatActivity {
    public static final String KEY_Email = "patientId";
    public static final String KEY_Token = "flag";
    String Token,patient_id,Visitid;
    ArrayList<Entry> x;
    ArrayList<String> y;
    private LineChart mChart;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        LineChart combinedChart = (LineChart) findViewById(R.id.chart1);
        List<Entry> lineEntries = getDataSet();
        LineDataSet lineDataSet = new LineDataSet(lineEntries, getString(R.string.app_author));

        //    LineDataSet lineDataSet = new LineDataSet(line, "Brand 2");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setColors(Color.BLUE);
        lineDataSet.setCircleRadius(5);

        // set this to false to disable the drawing of highlight indicator (lines)
        lineDataSet.setDrawHighlightIndicators(true);
        lineDataSet.setHighLightColor(Color.BLUE);
        LineData lineData = new LineData(lineDataSet);
        //   LineData data = new LineData(lineData());
        // data.setData(barData());
        // data.set(lineData());
        combinedChart.setData(lineData);

        // xAxis customization
        XAxis xAxis = combinedChart.getXAxis();

        // Following code have no effect but you can change it if required
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(false);
        xAxis.setDrawGridLines(false);

        // Setting maximum limit of xAxis
        xAxis.setAxisMaximum(barData().getEntryCount());

        // Setting position of xAxis
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);

        // This is used to fix bar width of first bar
        xAxis.setSpaceMin(barData().getBarWidth() / 2f);
        xAxis.setSpaceMax(barData().getBarWidth() / 2f);

        // Setting labels to xAxis
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getXAxisValues()));

        LimitLine ll1 = new LimitLine(215f, "Maximum Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);

        LimitLine ll2 = new LimitLine(70f, "Minimum Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);

        YAxis leftAxis = combinedChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaximum(350f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLimitLinesBehindData(false);

        combinedChart.getAxisRight().setEnabled(false);


    }

    // creating list of x-axis values
    private ArrayList<String> getXAxisValues()
    {
        ArrayList<String> labels = new ArrayList<String> ();

        labels.add( "JAN");
        labels.add( "FEB");
        labels.add( "MAR");
        labels.add( "APR");
        labels.add( "MAY");
        labels.add( "JUN");
        return labels;
    }

    // this method is used to create data for line graph
    private List<Entry> getDataSet()
    {
        ArrayList<Entry> line = new ArrayList<Entry> ();
        line.add(new Entry(0, 100));
        line.add(new Entry(1, 0));
        line.add(new Entry(2, 0));
        line.add(new Entry(3, 140));
        line.add(new Entry(4, 18));
        line.add(new Entry(5, 310));


        return line;
    }

    // this method is used to create data for Bar graph
    public BarData barData()
    {
        ArrayList<BarEntry> group1 = new ArrayList<BarEntry>();
        group1.add(new BarEntry(0, 3));
        group1.add(new BarEntry(1, 1));
        group1.add(new BarEntry(2, 4));
        group1.add(new BarEntry(3, 7));
        group1.add(new BarEntry(4, 3));
        group1.add(new BarEntry(5, 8));

        BarDataSet barDataSet = new BarDataSet(group1, "Brand 1");
        barDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        //barDataSet.setColor(Color.rgb(0, 155, 0));
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData barData = new BarData(barDataSet);
        //        barData.setBarWidth(0.9f);

        return barData;
    }
}*/
