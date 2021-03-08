package com.codewithhamad.covid_19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class CountryAdapter extends ArrayAdapter {
    private Context context;
    private List<CountryModel> countryModelList;
    TextView countryName, countryPopulation;
    ImageView countryFlag;

    public CountryAdapter(Context context, List<CountryModel> countryModelList) {
        super(context, R.layout.list_cutom_item, countryModelList);
        this.context= context;
        this.countryModelList= countryModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cutom_item, null, true);
        countryName= view.findViewById(R.id.countryName);
        countryPopulation= view.findViewById(R.id.countryPopulation);
        countryFlag= view.findViewById(R.id.countryFlag);

        countryName.setText(countryModelList.get(position).getCountry());
        countryPopulation.setText(countryModelList.get(position).getPopulation());
        Glide.with(context).load(countryModelList.get(position).getFlag()).into(countryFlag);

        return view;
    }
}
