package com.codewithhamad.covid_19tracker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends ArrayAdapter {

    private Context context;
    private List<CountryModel> countryModelList;
    private List<CountryModel> countryModelListFiltered;
    TextView countryName, countryPopulation;
    ImageView countryFlag;

    public CountryAdapter(Context context, List<CountryModel> countryModelList) {
        super(context, R.layout.list_cutom_item, countryModelList);
        this.context= context;
        this.countryModelList= countryModelList;
        this.countryModelListFiltered= countryModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cutom_item, null, true);
        countryName= view.findViewById(R.id.countryName);
        countryPopulation= view.findViewById(R.id.countryPopulation);
        countryFlag= view.findViewById(R.id.countryFlag);

        countryName.setText(countryModelListFiltered.get(position).getCountry());
        countryPopulation.setText(countryModelListFiltered.get(position).getPopulation());
        Glide.with(context).load(countryModelListFiltered.get(position).getFlag()).into(countryFlag);

        return view;
    }

    @Override
    public int getCount() {
        return countryModelListFiltered.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return countryModelListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults= new FilterResults();
                if(constraint==null || constraint.length()==0){
                    filterResults.count= countryModelList.size();
                    filterResults.values= countryModelList;
                    notifyDataSetChanged();
                }
                else{
                    List<CountryModel> resultsModel= new ArrayList<>();
                    String searchStr= constraint.toString().toLowerCase();

                    for(CountryModel countryModel : countryModelList){
                        if(countryModel.getCountry().toLowerCase().contains(searchStr)){
                            resultsModel.add(countryModel);
                        }
                        filterResults.count= resultsModel.size();
                        filterResults.values= resultsModel;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                countryModelListFiltered= (List<CountryModel>) results.values;
                AffectedCountries.countryModelList= (List<CountryModel>) results.values;
                notifyDataSetChanged();

            }
        };
    }
}
