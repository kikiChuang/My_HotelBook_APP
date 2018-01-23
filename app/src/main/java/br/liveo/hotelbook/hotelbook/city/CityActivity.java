package br.liveo.hotelbook.hotelbook.city;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.liveo.hotelbook.hotelbook.R;

public class CityActivity extends AppCompatActivity implements CityController.CityCallbackListener{

    private Toolbar toolbar;
    private SearchView searchView;
    private ListView listView;
    private List<City> cityList = new ArrayList<>();
    private br.liveo.hotelbook.hotelbook.city.CitiesAdapter citiesAdapter;
    private CityController  cityController;
    private String searchString;
    private City city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        init();
        setupSearchView();
        setToolbar();
    }

    private void init(){
        searchView = (SearchView) findViewById(R.id.searchView);
        listView = (ListView) findViewById(R.id.list_view);
        cityController = new CityController(CityActivity.this);
        configViews();
        cityController.startFetching();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                city = cityList.get(position);
                searchString = cityList.get(position).cityName;
                onBackPressed();
            }
        });
    }

    private void configViews() {
        citiesAdapter = new CitiesAdapter(getApplicationContext(), R.layout.city_listview, cityList);
        listView.setAdapter(citiesAdapter);
    }

    private void setToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.setSubmitButtonEnabled(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                citiesAdapter.filter(newText);
                return true;
            }
        });
        searchView.setQueryHint("Search");
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        if (searchString != null){
            bundle.putString("searchString", searchString);
            bundle.putInt("cityId", city.cityId);
            intent.putExtra("searchString", searchString);
            String cityString = city.cityName + "  " + city.cityChName + "\n" + city.countryName + "  " + city.countryChName;
            intent.putExtras(bundle);
        }
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }


    @Override
    public void onFetchStart() {

    }

    @Override
    public void onFetchProgress(City city) {
        citiesAdapter.addCity(city);
        citiesAdapter.addCityArrayList(city);
    }

    @Override
    public void onFetchProgress(List<City> cityList) {

    }

    @Override
    public void onFetchComplete() {
    }

    @Override
    public void onFetchFailed() {

    }
}
