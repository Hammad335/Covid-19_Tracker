package com.codewithhamad.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;

public class MainActivity extends AppCompatActivity {

    TextView cases, recoverd, critical, active, todayCases, todayDeaths, totalDeaths, affectedCountries;
    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing views
        initViews();

        // fetching data from corona.lmao.ninja api using volley
        fetchData();

    }

    private void fetchData() {

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

        simpleArcLoader= findViewById(R.id.loader);
        scrollView= findViewById(R.id.scrollStats);
        pieChart= findViewById(R.id.piechart);
    }
}