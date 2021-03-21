package com.codewithhamad.covid_19tracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView cases, recoverd, critical, active, todayCases, todayDeaths, totalDeaths, affectedCountries;
    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;
    Button trackCountriesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.main_activity_toolbar_title);

        // initializing views
        initViews();

        // fetching data from corona.lmao.ninja api using volley
        fetchData();

        // moving to next activity
        trackCountriesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, AffectedCountries.class);
                startActivity(intent);
            }
        });
    }

    private void fetchData() {
        String url= "https://corona.lmao.ninja/v2/all/";
        simpleArcLoader.start();
        StringRequest getRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject= new JSONObject(response);

                    // setting data values to the textViews declared above
                    setData(jsonObject);

                    // parameterize the pieChart slices
                    pieChart.addPieSlice(new PieModel("Cases", Integer.parseInt(cases.getText().toString()),
                            Color.parseColor("#FFA726")));

                    pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(recoverd.getText().toString()),
                            Color.parseColor("#66BB6A")));

                    pieChart.addPieSlice(new PieModel("Deaths", Integer.parseInt(totalDeaths.getText().toString()),
                            Color.parseColor("#EF5350")));

                    pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(active.getText().toString()),
                            Color.parseColor("#29B6F6")));

                    pieChart.startAnimation();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);

                }
                catch (JSONException e){
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(getRequest);
    }

    private void setData(JSONObject jsonObject) throws JSONException {
        cases.setText(jsonObject.getString("cases"));
        recoverd.setText(jsonObject.getString("recovered"));
        critical.setText(jsonObject.getString("critical"));
        active.setText(jsonObject.getString("active"));
        todayCases.setText(jsonObject.getString("todayCases"));
        todayDeaths.setText(jsonObject.getString("todayDeaths"));
        totalDeaths.setText(jsonObject.getString("deaths"));
        affectedCountries.setText(jsonObject.getString("affectedCountries"));
    }

    private void initViews() {
        cases= findViewById(R.id.cases);
        recoverd= findViewById(R.id.recovered);
        critical= findViewById(R.id.critical);
        active= findViewById(R.id.active);
        todayCases= findViewById(R.id.todayCases);
        todayDeaths= findViewById(R.id.todayDeaths);
        totalDeaths= findViewById(R.id.totalDeaths);
        affectedCountries= findViewById(R.id.affectedCountries);

        simpleArcLoader= findViewById(R.id.globalStatsLoader);
        scrollView= findViewById(R.id.scrollStats);
        pieChart= findViewById(R.id.piechart);

        trackCountriesBtn= findViewById(R.id.trackCountriesBtn);
    }
}