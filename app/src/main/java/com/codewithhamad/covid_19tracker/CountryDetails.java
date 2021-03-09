package com.codewithhamad.covid_19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class CountryDetails extends AppCompatActivity {

    private int countryPosition;
    TextView countryName, cases, recoverd, critical, active, todayCases, todayDeaths, totalDeaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);
        getSupportActionBar().setTitle("Country Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Intent intent= getIntent();
        countryPosition= intent.getIntExtra("position", -1);

        // initializing textViews
        countryName= findViewById(R.id.country_Name);
        cases= findViewById(R.id.countryCases);
        recoverd= findViewById(R.id.countryRecovered);
        critical= findViewById(R.id.countryCritical);
        active= findViewById(R.id.countryActive);
        todayCases= findViewById(R.id.countryTodayCases);
        todayDeaths= findViewById(R.id.countrytodayDeaths);
        totalDeaths= findViewById(R.id.countryDeaths);

        // setting data to textViews
        countryName.setText(AffectedCountries.countryModelList.get(countryPosition).getCountry());
        cases.setText(AffectedCountries.countryModelList.get(countryPosition).getCases());
        recoverd.setText(AffectedCountries.countryModelList.get(countryPosition).getRecovered());
        critical.setText(AffectedCountries.countryModelList.get(countryPosition).getCritical());
        active.setText(AffectedCountries.countryModelList.get(countryPosition).getActive());
        todayCases.setText(AffectedCountries.countryModelList.get(countryPosition).getTodayCases());
        todayDeaths.setText(AffectedCountries.countryModelList.get(countryPosition).getTodayDeaths());
        totalDeaths.setText(AffectedCountries.countryModelList.get(countryPosition).getDeaths());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}