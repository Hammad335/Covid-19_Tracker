package com.codewithhamad.covid_19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class AffectedCountries extends AppCompatActivity {

    EditText editSearch;
    ListView affectedCountriesListView;
    SimpleArcLoader affectedCountriesLoader;

    public static List<CountryModel> countryModelList= new ArrayList<>();
    CountryModel countryModel;
    CountryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_countries);

        // initializing views
        editSearch= findViewById(R.id.editSearch);
        affectedCountriesListView= findViewById(R.id.listView);
        affectedCountriesLoader= findViewById(R.id.affectedCountriesLoader);


        // retrieving data of affected countries
        fetchData();

        getSupportActionBar().setTitle("Affected Countries");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // adding onClick on listView items
        affectedCountriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), CountryDetails.class).putExtra("position", position));
            }
        });

        // implementing serach through textView
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {
        String url= "https://corona.lmao.ninja/v2/countries/";
        affectedCountriesLoader.start();

        StringRequest getRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    // storing details of all affected countries in json array
                    JSONArray affectedCountries= new JSONArray(response);
                    for(int i=0; i<affectedCountries.length(); i++){

                        // details of a particular country
                        JSONObject countryDetails= affectedCountries.getJSONObject(i);

                        String countryName= countryDetails.getString("country");
                        String cases= countryDetails.getString("cases");
                        String todayCases= countryDetails.getString("todayCases");
                        String deaths= countryDetails.getString("deaths");
                        String todayDeaths= countryDetails.getString("todayDeaths");
                        String recovered= countryDetails.getString("recovered");
                        String active= countryDetails.getString("active");
                        String critical= countryDetails.getString("critical");
                        String population= countryDetails.getString("population");

                        // fetching flag url from json object inside another json object
                        JSONObject countryInfo= countryDetails.getJSONObject("countryInfo");
                        String flagUrl= countryInfo.getString("flag");

                        // initializing country model each time with new data and storing in list
                        countryModel= new CountryModel(flagUrl, countryName, cases, todayCases, deaths, todayDeaths, recovered,
                                active, critical, population);
                        countryModelList.add(countryModel);
                    }

                    adapter= new CountryAdapter(AffectedCountries.this, countryModelList);
                    affectedCountriesListView.setAdapter(adapter);
                    affectedCountriesLoader.stop();
                    affectedCountriesLoader.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    affectedCountriesLoader.stop();
                    affectedCountriesLoader.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                affectedCountriesLoader.stop();
                affectedCountriesLoader.setVisibility(View.GONE);
                Toast.makeText(AffectedCountries.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(getRequest);
    }

}