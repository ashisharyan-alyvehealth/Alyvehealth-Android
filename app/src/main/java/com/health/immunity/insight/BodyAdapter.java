package com.health.immunity.insight;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.health.immunity.CommonUtils;
import com.health.immunity.HomeContainer.HomeActivity;
import com.health.immunity.HomeContainer.view.IHomeActivity;
import com.health.immunity.R;
import com.health.immunity.insight.model.GetKpiCall;
import com.health.immunity.insight.model.MinMaxFilter;

import java.util.List;

public class BodyAdapter extends RecyclerView.Adapter<BodyAdapter.NoticeHolder> {
    private static final int REQUEST_OAUTH_REQUEST_CODE = 1;

    private Context context;
    private List<GetKpiCall.Body> myCommunitiesResponses ;

    float a,a1;
   IHomeActivity iHomeActivity;

    public BodyAdapter(Context context, List<GetKpiCall.Body> myCommunitiesResponses) {
        this.context = context;
        this.myCommunitiesResponses = myCommunitiesResponses;
    }

    @NonNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeHolder(LayoutInflater.from(context).inflate(R.layout.my_activity_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeHolder holder, int position) {


        holder.tvv1.setText(myCommunitiesResponses.get(position).getLowerRange());
        holder.tvv2.setText(myCommunitiesResponses.get(position).getUpperRange());
        //     holder.im1.setText(myCommunitiesResponses.get(position).getAlias()+"\n"+myCommunitiesResponses.get(position).getValue());
        if (!TextUtils.isEmpty(myCommunitiesResponses.get(position).getValue())) {
            float roundTotal = Float.parseFloat(myCommunitiesResponses.get(position).getValue());
            // holder.im1.setText(myCommunitiesResponses.get(position).getAlias() + "\n" + String.format("%.2f", roundTotal));
            holder.im1.setText(myCommunitiesResponses.get(position).getAlias());
            holder.name.setText(myCommunitiesResponses.get(position).getValue());
            holder.progressBar.setVisibility(View.VISIBLE);


        } else {
            holder.im1.setText(myCommunitiesResponses.get(position).getAlias());
            holder.name.setText("-");
            holder.progressBar.setVisibility(View.VISIBLE);
        }
        if(myCommunitiesResponses.get(position).getAlias().startsWith("BMI")){
            holder.imageView.setImageResource(R.drawable.ic_bmi);
        }else if(myCommunitiesResponses.get(position).getAlias().startsWith("Weight")) {
            holder.imageView.setImageResource(R.drawable.ic_weight1);
        }else if(myCommunitiesResponses.get(position).getAlias().startsWith("SPO")) {
            holder.imageView.setImageResource(R.drawable.ic_spo2_1);
        }

        holder.tvHelpInfo1.setVisibility(View.VISIBLE);

        holder.tvHelpInfo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FitnessOptions fitnessOptions = FitnessOptions.builder()
                        .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                        .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
                        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA)
                        .addDataType(DataType.TYPE_DISTANCE_CUMULATIVE)
                        .addDataType(DataType.TYPE_DISTANCE_DELTA)
                        .addDataType(DataType.AGGREGATE_DISTANCE_DELTA)
                        .addDataType(DataType.AGGREGATE_ACTIVITY_SUMMARY)
                        .addDataType(DataType.TYPE_WEIGHT)
                        .addDataType(DataType.TYPE_HEIGHT)
                        .addDataType(DataType.AGGREGATE_HEIGHT_SUMMARY)
                        .addDataType(DataType.AGGREGATE_WEIGHT_SUMMARY)
                        .addDataType(DataType.TYPE_HEART_RATE_BPM)
                        .addDataType(DataType.TYPE_BODY_FAT_PERCENTAGE)
                        .addDataType(DataType.TYPE_WORKOUT_EXERCISE)
                        .addDataType(DataType.TYPE_ACTIVITY_SEGMENT)
                        .build();

                if (myCommunitiesResponses.get(position).getName().equalsIgnoreCase("SPO2")) {
                    final AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();

                    View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_weight, null);

                    final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
                    //editText.setFilters( new InputFilter[]{ new MinMaxFilter( "1" , "100" )}) ;

                    editText.setHint("Enter SPO2 (%)");
                    Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
                    Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogBuilder.dismiss();
                        }
                    });
                    button1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialogBuilder.dismiss();

                            if (TextUtils.isEmpty(editText.getText().toString().trim()))
                            {

                                Toast.makeText(context, "Please enter the spo2", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                float spo2 = Float.parseFloat(editText.getText().toString().trim());
                                if (spo2 <= 100) {
                                    Intent intent = new Intent(context, HomeActivity.class);
                                    intent.putExtra("spo2body", "spo2");
                                    intent.putExtra("spo2value", editText.getText().toString().trim());
                                    context.startActivity(intent);
                                } else {
                                    Toast.makeText(context, "Enter spo2 less than or equal to 100", Toast.LENGTH_SHORT).show();
                                }

                            }


                        }
                    });

                    dialogBuilder.setView(dialogView);
                    dialogBuilder.show();
                }


                else if (myCommunitiesResponses.get(position).getName().equalsIgnoreCase("BMI")) {
                  /*  if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(context), fitnessOptions)) {

                        AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
                        View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_profile, null);
                        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
                        Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);
                        TextView textView = (TextView) dialogView.findViewById(R.id.textView);
                        textView.setText("Please connect to google fit to proceed");

                        button2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogBuilder.dismiss();
                                final AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
                                View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_weight, null);
                                final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
                                editText.setFilters(new InputFilter[]{new MinMaxFilter("1", "250")});
                                final EditText editText1 = (EditText) dialogView.findViewById(R.id.edt_comment1);
                                editText.setHint("Enter Height (cm)");
                                Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
                                Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);
                                button2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialogBuilder.dismiss();
                                    }
                                });
                                button1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialogBuilder.dismiss();
                                        Intent intent = new Intent(context, HomeActivity.class);
                                        intent.putExtra("heightbody", "height");
                                        intent.putExtra("heightvalue", editText.getText().toString().trim());
                                        context.startActivity(intent);
                                    }
                                });
                                dialogBuilder.setView(dialogView);
                                dialogBuilder.show();

                            }
                        });
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialogBuilder.dismiss();

                                if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(context), fitnessOptions))
                                {
                                    GoogleSignIn.requestPermissions((Activity) context, REQUEST_OAUTH_REQUEST_CODE,
                                            GoogleSignIn.getLastSignedInAccount(context),
                                            fitnessOptions);
                                }

                            }
                        });

                        dialogBuilder.setView(dialogView);
                        dialogBuilder.show();
                    } else*/
                    {
                        final AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
                        View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_weight, null);
                        final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
                        editText.setFilters(new InputFilter[]{new MinMaxFilter("1", "250")});
                        final EditText editText1 = (EditText) dialogView.findViewById(R.id.edt_comment1);
                        editText.setHint("Enter Height (cm)");
                        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
                        Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);
                        button2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogBuilder.dismiss();
                            }
                        });
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogBuilder.dismiss();
                                if (TextUtils.isEmpty(editText.getText().toString().trim())) {

                                    Toast.makeText(context, "Please enter the height", Toast.LENGTH_SHORT).show();

                                } else {

                                    Intent intent = new Intent(context, HomeActivity.class);
                                    intent.putExtra("heightbody", "height");
                                    intent.putExtra("heightvalue", editText.getText().toString().trim());
//                                    iHomeActivity=new HomeActivity(view.getContext());
//                                    iHomeActivity.setHeight(editText.getText().toString().trim());
                                    context.startActivity(intent);
                                  // BodyAdapter.this.notify();
                                  // notifyDataSetChanged();
                                 //  notifyItemChanged(position);


                                }
                            }
                        });
                        dialogBuilder.setView(dialogView);
                        dialogBuilder.show();
                    }
                } else if (myCommunitiesResponses.get(position).getName().equalsIgnoreCase("Weight")) {

                    /*if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(context), fitnessOptions)) {

                        AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
                        View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_profile, null);
                        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
                        Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);
                        TextView textView = (TextView) dialogView.findViewById(R.id.textView);
                        textView.setText("Please connect to google fit to proceed");

                        button2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogBuilder.dismiss();
                                final AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
                                View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_weight, null);
                                final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
                                //editText.setFilters( new InputFilter[]{ new MinMaxFilter( "1" , "250" )}) ;
                                editText.setHint("Enter Weight (kg)");
                                Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
                                Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);

                                button2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialogBuilder.dismiss();
                                    }
                                });
                                button1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        dialogBuilder.dismiss();
                                        if (TextUtils.isEmpty(editText.getText().toString().trim())) {

                                            Toast.makeText(context, "Please enter the weight", Toast.LENGTH_SHORT).show();

                                        } else {
                                            float weight = Float.parseFloat(editText.getText().toString().trim());
                                            if (weight <= 250) {
                                                Intent intent = new Intent(context, HomeActivity.class);
                                                intent.putExtra("weightbody", "weight");
                                                intent.putExtra("weightvalue", editText.getText().toString().trim());
                                                context.startActivity(intent);

                                            } else {
                                                Toast.makeText(context, "Enter weight less than or equal to 250", Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                    }
                                });
                                dialogBuilder.setView(dialogView);
                                dialogBuilder.show();
                            }
                        });
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialogBuilder.dismiss();

                                if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(context), fitnessOptions))
                                {
                                    GoogleSignIn.requestPermissions((Activity) context, REQUEST_OAUTH_REQUEST_CODE,
                                            GoogleSignIn.getLastSignedInAccount(context),
                                            fitnessOptions);
                                }

                            }
                        });

                        dialogBuilder.setView(dialogView);
                        dialogBuilder.show();
                    }
                    else*/ {


                        final AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
                        View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_weight, null);
                        final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
                        //editText.setFilters( new InputFilter[]{ new MinMaxFilter( "1" , "250" )}) ;
                        editText.setHint("Enter Weight (kg)");
                        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
                        Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);

                        button2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogBuilder.dismiss();
                            }
                        });
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialogBuilder.dismiss();
                                if (TextUtils.isEmpty(editText.getText().toString().trim())) {

                                    Toast.makeText(context, "Please enter the weight", Toast.LENGTH_SHORT).show();

                                } else {
                                    float weight = Float.parseFloat(editText.getText().toString().trim());
                                    if (weight <= 250) {
                                        Intent intent = new Intent(context, HomeActivity.class);
                                        intent.putExtra("weightbody", "weight");
                                        intent.putExtra("weightvalue", editText.getText().toString().trim());
                                        context.startActivity(intent);

                                    } else {
                                        Toast.makeText(context, "Enter weight less than or equal to 250", Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }
                        });
                        dialogBuilder.setView(dialogView);
                        dialogBuilder.show();

                    }
                }
                else
                {
                    Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.google.android.apps.fitness");
                    if (launchIntent != null) {
                        context.startActivity(launchIntent);
                    } else {
                        Toast.makeText(context, "There is no app available in android", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });




        if (myCommunitiesResponses.get(position).getName().equalsIgnoreCase("SPO2"))
        {
            if (!TextUtils.isEmpty(myCommunitiesResponses.get(position).getHealthyLowerRange()) && !TextUtils.isEmpty(myCommunitiesResponses.get(position).getHealthyUpperRange())
                    && !TextUtils.isEmpty(myCommunitiesResponses.get(position).getLowerRange()) && !TextUtils.isEmpty(myCommunitiesResponses.get(position).getUpperRange())) {

                int sa = (int) ((Float.parseFloat(myCommunitiesResponses.get(position).getHealthyLowerRange()) - Float.parseFloat(myCommunitiesResponses.get(position).getLowerRange())) * 100 / (Float.parseFloat(myCommunitiesResponses.get(position).getUpperRange()) - Float.parseFloat(myCommunitiesResponses.get(position).getLowerRange())));
                int  sa1 = (int) ((Float.parseFloat(myCommunitiesResponses.get(position).getHealthyUpperRange()) - Float.parseFloat(myCommunitiesResponses.get(position).getLowerRange())) * 100 / (Float.parseFloat(myCommunitiesResponses.get(position).getUpperRange()) - Float.parseFloat(myCommunitiesResponses.get(position).getLowerRange())));
                String str = String.valueOf(sa);
                String str1 = String.valueOf(sa1);
               /* if (str.length()>=2)
                {
                    holder.progressBar.setProgress(Integer.parseInt(str.substring(0,2).replace("."," ").trim()));

                }
                else
                {
                    holder.progressBar.setProgress(Integer.parseInt(str));
                }
                if (str1.length()>=2)
                {
                    holder.progressBar.setSecondaryProgress(Integer.parseInt(str1.substring(0,2).replace("."," ").trim()));

                }
                else
                {
                    holder.progressBar.setSecondaryProgress(Integer.parseInt(str1));

                }*/
                holder.progressBar.setProgress(Integer.parseInt(str));
                holder.progressBar.setSecondaryProgress(Integer.parseInt(str1));

            }
        }
        else if (!TextUtils.isEmpty(myCommunitiesResponses.get(position).getHealthyLowerRange()) && !TextUtils.isEmpty(myCommunitiesResponses.get(position).getHealthyUpperRange())
                && !TextUtils.isEmpty(myCommunitiesResponses.get(position).getLowerRange()) && !TextUtils.isEmpty(myCommunitiesResponses.get(position).getUpperRange()))
        {

            a = (Float.parseFloat(myCommunitiesResponses.get(position).getHealthyLowerRange())-Float.parseFloat(myCommunitiesResponses.get(position).getLowerRange()))*100/(Float.parseFloat(myCommunitiesResponses.get(position).getUpperRange())-Float.parseFloat(myCommunitiesResponses.get(position).getLowerRange()));
            a1 = (Float.parseFloat(myCommunitiesResponses.get(position).getHealthyUpperRange())-Float.parseFloat(myCommunitiesResponses.get(position).getLowerRange()))*100/(Float.parseFloat(myCommunitiesResponses.get(position).getUpperRange())-Float.parseFloat(myCommunitiesResponses.get(position).getLowerRange()));

            System.out.println("##############"+myCommunitiesResponses.get(position).getAlias()+"lower->"+a);
            System.out.println("##############"+myCommunitiesResponses.get(position).getAlias()+"upper->"+a1);

            String str = String.valueOf(a);
            if (str.length()>=2)
            {
                holder.progressBar.setProgress(Integer.parseInt(str.substring(0,2).replace("."," ").trim()));

            }
            else
            {
                holder.progressBar.setProgress(Integer.parseInt(str));
            }
            String str1= String.valueOf(a1);
            if (str1.length()>=2)
            {
                holder.progressBar.setSecondaryProgress(Integer.parseInt(str1.substring(0,2).replace("."," ").trim()));

            }
            else
            {
                holder.progressBar.setSecondaryProgress(Integer.parseInt(str1));

            }


        }
        else
        {
            holder.progressBar.setProgress(0);
            holder.progressBar.setSecondaryProgress(0);


        }



        if (!TextUtils.isEmpty(myCommunitiesResponses.get(position).getValue())  && !TextUtils.isEmpty(myCommunitiesResponses.get(position).getLowerRange()) && !TextUtils.isEmpty(myCommunitiesResponses.get(position).getUpperRange())) {
            float a2 = (Float.parseFloat(myCommunitiesResponses.get(position).getValue()) - Float.parseFloat(myCommunitiesResponses.get(position).getLowerRange())) * 100 / (Float.parseFloat(myCommunitiesResponses.get(position).getUpperRange()) - Float.parseFloat(myCommunitiesResponses.get(position).getLowerRange()));
            System.out.println("##############" + myCommunitiesResponses.get(position).getAlias() + "value->" + a2);

            if (a2 == 100)
            {
                holder.uparrowLast.setVisibility(View.VISIBLE);

            }
            else
            {
                String values = String.valueOf(a2).substring(0, 2).replace(".", " ").trim();
                int v1 = Integer.parseInt(values);


                if (v1 == 0) {
                    holder.uparrowL0.setVisibility(View.VISIBLE);

                } else if (v1 > 0 && v1 <= 10) {
                    holder.uparrowL1.setVisibility(View.VISIBLE);

                } else if (v1 > 10 && v1 <= 20) {
                    holder.uparrowL2.setVisibility(View.VISIBLE);

                } else if (v1 > 20 && v1 <= 30) {
                    holder.uparrowL3.setVisibility(View.VISIBLE);

                } else if (v1 > 30 && v1 <= 40) {
                    holder.uparrowL4.setVisibility(View.VISIBLE);

                } else if (v1 > 40 && v1 <= 50) {

                    holder.uparrow.setVisibility(View.VISIBLE);

                } else if (v1 > 50 && v1 <= 60) {
                    holder.uparrowR1.setVisibility(View.VISIBLE);

                } else if (v1 > 60 && v1 <= 70) {
                    holder.uparrowR2.setVisibility(View.VISIBLE);

                } else if (v1 > 70 && v1 <= 80) {
                    holder.uparrowR3.setVisibility(View.VISIBLE);

                } else if (v1 > 80 && v1 <= 90) {
                    holder.uparrowR4.setVisibility(View.VISIBLE);

                } else if (v1 > 90) {
                    holder.uparrowLast.setVisibility(View.VISIBLE);

                } else {
                    holder.uparrowL0.setVisibility(View.GONE);
                    holder.uparrowL1.setVisibility(View.GONE);
                    holder.uparrowL2.setVisibility(View.GONE);
                    holder.uparrowL3.setVisibility(View.GONE);
                    holder.uparrowL4.setVisibility(View.GONE);
                    holder.uparrow.setVisibility(View.GONE);
                    holder.uparrowR1.setVisibility(View.GONE);
                    holder.uparrowR2.setVisibility(View.GONE);
                    holder.uparrowR3.setVisibility(View.GONE);
                    holder.uparrowR4.setVisibility(View.GONE);
                    holder.uparrowLast.setVisibility(View.GONE);
                }
            }

        }

        holder.im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(myCommunitiesResponses.get(position).getHealthyLowerRange()) && !TextUtils.isEmpty(myCommunitiesResponses.get(position).getHealthyUpperRange())) {
                    a = (Float.parseFloat(myCommunitiesResponses.get(position).getHealthyLowerRange()));
                    a1 = (Float.parseFloat(myCommunitiesResponses.get(position).getHealthyUpperRange()));
                    Intent intent = new Intent(context, GraphActivity.class);
                    intent.putExtra("toolbarname", myCommunitiesResponses.get(position).getAlias());
                    intent.putExtra("getrangesource",myCommunitiesResponses.get(position).getHealthy_range_source());

                    intent.putExtra("kpi_id", myCommunitiesResponses.get(position).getId());
                    intent.putExtra("decs", myCommunitiesResponses.get(position).getDescription());
                    intent.putExtra("healthyupeer", a1);
                    intent.putExtra("healthylower", a);
                    intent.putExtra("limitchck", "yes");
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, GraphActivity.class);
                    intent.putExtra("toolbarname", myCommunitiesResponses.get(position).getAlias());
                    intent.putExtra("getrangesource",myCommunitiesResponses.get(position).getHealthy_range_source());

                    intent.putExtra("kpi_id", myCommunitiesResponses.get(position).getId());
                    intent.putExtra("decs", myCommunitiesResponses.get(position).getDescription());
                    intent.putExtra("healthyupeer", 0);
                    intent.putExtra("healthylower", 0);
                    intent.putExtra("limitchck", "no");
                    context.startActivity(intent);

                }


            }
        });

        holder.progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(myCommunitiesResponses.get(position).getHealthyLowerRange()) && !TextUtils.isEmpty(myCommunitiesResponses.get(position).getHealthyUpperRange()))
                {
                    a = (Float.parseFloat(myCommunitiesResponses.get(position).getHealthyLowerRange()));
                    a1 = (Float.parseFloat(myCommunitiesResponses.get(position).getHealthyUpperRange()));
                    Intent intent=new Intent(context, GraphActivity.class);
                    intent.putExtra("toolbarname",myCommunitiesResponses.get(position).getAlias());
                    intent.putExtra("getrangesource",myCommunitiesResponses.get(position).getHealthy_range_source());

                    intent.putExtra("kpi_id",myCommunitiesResponses.get(position).getId());
                    intent.putExtra("decs",myCommunitiesResponses.get(position).getDescription());
                    intent.putExtra("healthyupeer",a1);
                    intent.putExtra("healthylower",a);
                    intent.putExtra("limitchck","yes");
                    context.startActivity(intent);
                }
                else
                {
                    Intent intent=new Intent(context, GraphActivity.class);
                    intent.putExtra("toolbarname",myCommunitiesResponses.get(position).getAlias());
                    intent.putExtra("getrangesource",myCommunitiesResponses.get(position).getHealthy_range_source());

                    intent.putExtra("kpi_id",myCommunitiesResponses.get(position).getId());
                    intent.putExtra("decs",myCommunitiesResponses.get(position).getDescription());
                    intent.putExtra("healthyupeer",0);
                    intent.putExtra("healthylower",0);
                    intent.putExtra("limitchck","no");
                    context.startActivity(intent);

                }


            }
        });


        if (CommonUtils.compareset)
        {



            if (!TextUtils.isEmpty(myCommunitiesResponses.get(position).getComparevalue()))
            {
                float   a2=   (Float.parseFloat(myCommunitiesResponses.get(position).getComparevalue())-Float.parseFloat(myCommunitiesResponses.get(position).getLowerRange()))*100/(Float.parseFloat(myCommunitiesResponses.get(position).getUpperRange())-Float.parseFloat(myCommunitiesResponses.get(position).getLowerRange()));
                System.out.println("##############"+myCommunitiesResponses.get(position).getAlias()+"comparevalue->"+a2);

                if (a2 == 100)
                {
                    holder.ivcomparesetLast.setVisibility(View.VISIBLE);

                }
                else {

                    String values = String.valueOf(a2).substring(0, 2).replace(".", " ").trim();
                    int v1 = Integer.parseInt(values);

                    if (v1 == 0) {
                        holder.ivcompareset10.setVisibility(View.VISIBLE);

                    } else if (v1 > 0 && v1 <= 10) {
                        holder.ivcompareset9.setVisibility(View.VISIBLE);

                    } else if (v1 > 10 && v1 <= 20) {
                        holder.ivcompareset8.setVisibility(View.VISIBLE);

                    } else if (v1 > 20 && v1 <= 30) {
                        holder.ivcompareset7.setVisibility(View.VISIBLE);

                    } else if (v1 > 30 && v1 <= 40) {
                        holder.ivcompareset6.setVisibility(View.VISIBLE);

                    } else if (v1 > 40 && v1 <= 50) {

                        holder.ivcompareset1.setVisibility(View.VISIBLE);

                    } else if (v1 > 50 && v1 <= 60) {
                        holder.ivcompareset2.setVisibility(View.VISIBLE);

                    } else if (v1 > 60 && v1 <= 70) {
                        holder.ivcompareset3.setVisibility(View.VISIBLE);

                    } else if (v1 > 70 && v1 <= 80) {
                        holder.ivcompareset4.setVisibility(View.VISIBLE);

                    } else if (v1 > 80 && v1 <= 90) {
                        holder.ivcompareset5.setVisibility(View.VISIBLE);

                    } else if (v1 > 90) {
                        holder.ivcomparesetLast.setVisibility(View.VISIBLE);

                    } else {
                        holder.ivcompareset1.setVisibility(View.GONE);
                        holder.ivcompareset2.setVisibility(View.GONE);
                        holder.ivcompareset3.setVisibility(View.GONE);
                        holder.ivcompareset4.setVisibility(View.GONE);
                        holder.ivcompareset5.setVisibility(View.GONE);
                        holder.ivcompareset6.setVisibility(View.GONE);
                        holder.ivcompareset7.setVisibility(View.GONE);
                        holder.ivcompareset8.setVisibility(View.GONE);
                        holder.ivcompareset9.setVisibility(View.GONE);
                        holder.ivcompareset10.setVisibility(View.GONE);
                        holder.ivcomparesetLast.setVisibility(View.GONE);
                    }
                }

            }
        }






    }

    @Override
    public int getItemCount() {
        return myCommunitiesResponses.size();

    }



    public class NoticeHolder extends RecyclerView.ViewHolder {
        private TextView im1,name,tvv1,tvv2,tvHelpInfo1;
        private ImageView imageView, uparrowLast,ivcomparesetLast,ivcompareset1,ivcompareset2,ivcompareset3,ivcompareset4,ivcompareset5,ivcompareset6,ivcompareset7,ivcompareset8,ivcompareset9,ivcompareset10,imvideo,imviply,inffo,uparrowL0,uparrowR4,uparrowR3,uparrowR2,uparrowR1,uparrow,uparrowL4,uparrowL3,uparrowL2,uparrowL1;
        private ProgressBar progressBar;

        public NoticeHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.textView3);
            tvv1 = itemView.findViewById(R.id.tvv1);
            tvv2 = itemView.findViewById(R.id.tvv2);
            im1 = itemView.findViewById(R.id.im1);
            inffo = itemView.findViewById(R.id.inffo);
            tvHelpInfo1 = itemView.findViewById(R.id.tvHelpInfo1);
            uparrowR4 = itemView.findViewById(R.id.uparrowR4);
            uparrowR3 = itemView.findViewById(R.id.uparrowR3);
            uparrowR2 = itemView.findViewById(R.id.uparrowR2);
            uparrowR1 = itemView.findViewById(R.id.uparrowR1);
            uparrow = itemView.findViewById(R.id.uparrow);
            uparrowL4 = itemView.findViewById(R.id.uparrowL4);
            uparrowL3 = itemView.findViewById(R.id.uparrowL3);
            uparrowL2 = itemView.findViewById(R.id.uparrowL2);
            uparrowL1 = itemView.findViewById(R.id.uparrowL1);
            uparrowL0 = itemView.findViewById(R.id.uparrowL0);
         //   imvideo = itemView.findViewById(R.id.imvideo);
            imviply = itemView.findViewById(R.id.imviply);
            progressBar = itemView.findViewById(R.id.progressBar1);
            ivcompareset1 = itemView.findViewById(R.id.ivcompareset1);
            ivcompareset2 = itemView.findViewById(R.id.ivcompareset2);
            ivcompareset3 = itemView.findViewById(R.id.ivcompareset3);
            ivcompareset4 = itemView.findViewById(R.id.ivcompareset4);
            ivcompareset5 = itemView.findViewById(R.id.ivcompareset5);
            ivcompareset6 = itemView.findViewById(R.id.ivcompareset6);
            ivcompareset7 = itemView.findViewById(R.id.ivcompareset7);
            ivcompareset8 = itemView.findViewById(R.id.ivcompareset8);
            ivcompareset9 = itemView.findViewById(R.id.ivcompareset9);
            ivcompareset10 = itemView.findViewById(R.id.ivcompareset10);
            ivcomparesetLast = itemView.findViewById(R.id.ivcomparesetLast);
            uparrowLast = itemView.findViewById(R.id.uparrowLast);


        }
    }
}

