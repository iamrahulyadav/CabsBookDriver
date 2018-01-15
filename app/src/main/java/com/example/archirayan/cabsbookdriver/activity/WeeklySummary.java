package com.example.archirayan.cabsbookdriver.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.Utils.Utils;
import com.example.archirayan.cabsbookdriver.activity.help.EarningsHelp;
import com.example.archirayan.cabsbookdriver.model.Constant;
import com.example.archirayan.cabsbookdriver.model.EarningsBalanceResponse;
import com.example.archirayan.cabsbookdriver.model.TotalOnlinetimeandtrip;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.example.archirayan.cabsbookdriver.adapter.PastWeeksAdaper.str_EndDate;
import static com.example.archirayan.cabsbookdriver.adapter.PastWeeksAdaper.str_StartDate;

public class WeeklySummary extends AppCompatActivity implements OnChartGestureListener {

    private static final String TAG = "WeeklySummary";
    ScatterChart scatterChart;
    private ImageView img_backpress;
    private LinearLayout linear_weeklypage, linear_transaction, linear_triponline, linear_trips, linear_earningshelp;
    private TextView txt_weeklydates, txt_weeklytxtamount, txt_min, txt_numberoftrip, txt_amountdeposit, txt_earningsyet;
    private LineChart mChart;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_summary);
        txt_weeklydates = (TextView) findViewById(R.id.txt_weeklydates);
        txt_weeklytxtamount = (TextView) findViewById(R.id.txt_weeklytxtamount);
        txt_min = (TextView) findViewById(R.id.txt_min);
        txt_numberoftrip = (TextView) findViewById(R.id.txt_numberoftrip);
        txt_amountdeposit = (TextView) findViewById(R.id.txt_amountdeposit);
        txt_earningsyet = (TextView) findViewById(R.id.txt_earningsyet);
        scatterChart = (ScatterChart) findViewById(R.id.chart);
        getAllTripSummery();
        getweeklyamount();
        getWeeklyonlinetimeandtrip();
        img_backpress = (ImageView) findViewById(R.id.img_backpress);
        img_backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WeeklySummary.this, DriverMainPage.class));
            }
        });

        linear_weeklypage = (LinearLayout) findViewById(R.id.linear_weeklypage);
        linear_weeklypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WeeklySummary.this, Pastweeks.class));
                scatterChart.setVisibility(View.VISIBLE);
            }
        });
        linear_transaction = (LinearLayout) findViewById(R.id.linear_transaction);
        linear_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WeeklySummary.this, TransactionsActivity.class));

            }
        });
        linear_triponline = (LinearLayout) findViewById(R.id.linear_triponline);
        linear_triponline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WeeklySummary.this);
                builder.setTitle("Time online");
                builder.setMessage("This number reflects the total time you had the app set to Online,not the amount of time you spent on trips.");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        linear_trips = (LinearLayout) findViewById(R.id.linear_trips);
        linear_trips.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WeeklySummary.this);
                builder.setTitle("Trips");
                builder.setMessage("This nimber includes all trips, some of which may not count toward promotions.");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        linear_earningshelp = (LinearLayout) findViewById(R.id.linear_earningshelp);
        linear_earningshelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WeeklySummary.this, EarningsHelp.class));
            }
        });
        mChart = (LineChart) findViewById(R.id.linechart);
        mChart.setOnChartGestureListener(this);
        mChart.setTouchEnabled(true);
        // mChart.setOnChartValueSelectedListener(this);
        mChart.animateX(2500, Easing.EasingOption.EaseInOutCirc);
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);

        scatterChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener()
        {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h)
            {

               /* String text = e.toString();
                String myString = (text.length() > 27)? text.substring(text.length() - 4, 5): text;
                Log.d("str_Fare","=== String ===="+myString);*/

               /* String str_Fare=e.toString();
                Log.d("str_Fare","=== String ===="+str_Fare);
                str_Fare.replacebefore (":","");*/
                startActivity(new Intent(WeeklySummary.this,DailyEarninigsActivity.class));

            }
            @Override
            public void onNothingSelected()
            {

            }
        });
    }

    private void getAllTripSummery()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
         /* googleparams.put("driverid", Utils.ReadSharePrefrence(WeeklySummary.this, Constant.DRIVERID));
       googleparams.put("start_date", str_StartDate);
        googleparams.put("end_date", str_EndDate);*/
        googleparams.put("driverid", Utils.ReadSharePrefrence(WeeklySummary.this, Constant.DRIVERID));
        googleparams.put("start_date", str_StartDate);
        googleparams.put("end_date", str_EndDate);
        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "get_total_trip_price.php?" + googleparams);
        Log.e(TAG, googleparams.toString());
        client.post(this, Constant.BASE_URL + "get_total_trip_price.php?", googleparams, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                pd = new ProgressDialog(WeeklySummary.this);
                pd.setMessage("Loading...");
                pd.setCancelable(true);
                pd.show();
            }

            @Override
            public void onFinish() {

                super.onFinish();
                pd.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                super.onSuccess(statusCode, headers, response);
                pd.dismiss();
                Log.e(TAG, "Driver RESPONSE-" + response);
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    if (jsonObject.getString("status").equalsIgnoreCase("true"))
                    {
                        final ArrayList<Entry> entries = new ArrayList<>();
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            entries.add(new Entry(Float.parseFloat(obj.getString("fare")), i));
                        }
                        ScatterDataSet dataset = new ScatterDataSet(entries, "");
                        ArrayList<String> labels = new ArrayList<>();
                        labels.add("M");
                        labels.add("TU");
                        labels.add("W");
                        labels.add("TH");
                        labels.add("F");
                        labels.add("SA");
                        labels.add("SU");
                        scatterChart.setHovered(true);
                        ScatterData data = new ScatterData(labels, dataset);
                        scatterChart.setData(data); // set the data and list of lables into chart<br />
                        dataset.setScatterShapeSize(5);
                        dataset.setScatterShapeSize(20);
                        scatterChart.animateY(5000);
                        XAxis xAxis = scatterChart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setDrawGridLines(false);
                        scatterChart.setDescription("");

                    } else {
                        Toast.makeText(WeeklySummary.this, R.string.no_result_found, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    private void getWeeklyonlinetimeandtrip() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
        googleparams.put("driver_id", Utils.ReadSharePrefrence(WeeklySummary.this, Constant.DRIVERID));

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "total_online_time_trip.php?" + googleparams);
        Log.e(TAG, googleparams.toString());
        client.post(this, Constant.BASE_URL + "total_online_time_trip.php?", googleparams, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFinish() {

                super.onFinish();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "Driver RESPONSE-" + response);
                TotalOnlinetimeandtrip dmodel = new Gson().fromJson(new String(String.valueOf(response)), TotalOnlinetimeandtrip.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")) {
                    txt_min.setText(dmodel.getData().getTotal_time());
                    txt_numberoftrip.setText(dmodel.getData().getTrip());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    private void getweeklyamount() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams googleparams = new RequestParams();
        googleparams.put("driver_id", Utils.ReadSharePrefrence(WeeklySummary.this, Constant.DRIVERID));

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "driver_weeklly_trip.php?" + googleparams);
        Log.e(TAG, googleparams.toString());
        client.post(this, Constant.BASE_URL + "driver_weeklly_trip.php?", googleparams, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFinish() {

                super.onFinish();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "Driver RESPONSE-" + response);
                EarningsBalanceResponse dmodel = new Gson().fromJson(new String(String.valueOf(response)), EarningsBalanceResponse.class);
                if (dmodel.getStatus().equalsIgnoreCase("true")) {
                    txt_weeklytxtamount.setText(dmodel.getData().getWeek_fare());
                }
                if (dmodel.getMsg().equalsIgnoreCase("Data Found")) {
                    txt_earningsyet.setText("Earnings yet this week");
                } else {
                    txt_earningsyet.setText("No Earnings yet this week");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    /**
     * Callbacks when a touch-gesture has started on the chart (ACTION_DOWN)
     *
     * @param me
     * @param lastPerformedGesture
     */
    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    /**
     * Callbacks when a touch-gesture has ended on the chart (ACTION_UP, ACTION_CANCEL)
     *
     * @param me
     * @param lastPerformedGesture
     */
    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    /**
     * Callbacks when the chart is longpressed.
     *
     * @param me
     */
    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    /**
     * Callbacks when the chart is double-tapped.
     *
     * @param me
     */
    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    /**
     * Callbacks when the chart is single-tapped.
     *
     * @param me
     */
    @Override
    public void onChartSingleTapped(MotionEvent me)
    {
       /* final Entry entry = scatterChart.getEntryByTouchPoint(me.getX(), me.getY());
        if (entry != null) {
            final Object data = entry.getData();
            Log.d(TAG, "onChartSingleTapped: Touched: "
                    + entry.getXIndex() + ", " + entry.getXIndex());

        }*/
    }

    /**
     * Callbacks then a fling gesture is made on the chart.
     *
     * @param me1
     * @param me2
     * @param velocityX
     * @param velocityY
     */
    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    /**
     * Callbacks when the chart is scaled / zoomed via pinch zoom gesture.
     *
     * @param me
     * @param scaleX scalefactor on the x-axis
     * @param scaleY scalefactor on the y-axis
     */
    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    /**
     * Callbacks when the chart is moved / translated via drag gesture.
     *
     * @param me
     * @param dX translation distance on the x-axis
     * @param dY translation distance on the y-axis
     */
    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    /**
     * Called when a value has been selected inside the chart.
     *
     * @param e            The selected Entry.
     * @param dataSetIndex The index in the datasets array of the data object
     *                     the Entrys DataSet is in.
     * @param h            the corresponding highlight object that contains information
     */
}
