package br.liveo.hotelbook.hotelbook.city;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.liveo.hotelbook.hotelbook.R;

/**
 * Created by phx on 2016/1/29.
 */
public class CitiesAdapter extends ArrayAdapter<City> implements Filterable {

    public static String TAG = CitiesAdapter.class.getSimpleName();
    private Context context;
    private int resource;
    private List<City> cityList;
    private ArrayList<City> cityArrayList;

    public CitiesAdapter(Context context, int resource, List<City> cityList) {
        super(context, resource,cityList);
        this.context = context;
        this.resource = resource;
        this.cityList = cityList;
        this.cityArrayList = new ArrayList<City>();

    }

    public void addCityArrayList(City city){
        cityArrayList.add(city);
    }

    public void addCity(City city){
        cityList.add(city);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater=(LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView =mInflater.inflate(R.layout.city_listview,null);
            holder = new ViewHolder();
            holder.cityName = (TextView) convertView.findViewById(R.id.cityName);
            holder.cityChName = (TextView) convertView.findViewById(R.id.cityChName);
            holder.countryName = (TextView) convertView.findViewById(R.id.countryName);
            holder.countryChName = (TextView) convertView.findViewById(R.id.countryChName);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //設置holder
        City city = cityList.get(position);
        holder.cityName.setText(city.getCityName());
        holder.cityChName.setText(city.getCityChName());
        holder.countryName.setText(city.getCountryName());
        holder.countryChName.setText(city.getCountryChName());
        return convertView;
    }

    public static class ViewHolder{
        private TextView cityName, cityChName, countryName, countryChName;
    }


    public void filter(String constraint){
        cityList.clear();
        if(constraint.length() == 0){
            cityList.addAll(cityArrayList);
        }else{
            for(City city : cityArrayList){
                if(city.getCityName().toLowerCase().contains(constraint.toLowerCase())){
                    cityList.add(city);
                }
            }
        }
        notifyDataSetChanged();

    }



}
