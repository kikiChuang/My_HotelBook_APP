package br.liveo.hotelbook.hotelbook.city;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import br.liveo.hotelbook.hotelbook.util.RestApiManger;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by phx on 2016/1/29.
 */
public class CityController {
    private static final String TAG = CityController.class.getSimpleName();
    private CityCallbackListener listener;
    private RestApiManger restApiManger;

    public CityController(CityCallbackListener listener){
        this.listener = listener;
        restApiManger = new RestApiManger();

    }

    public void startFetching(){
        restApiManger.getCityApi().getCitys(new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.d(TAG, "JSON :: " + response);

                try {
                    JSONArray array = new JSONArray(s);

                    for(int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        City city = new City.Builder()
                                .setCountryName(object.getString("CountryName"))
                                .setCountryChName(object.getString("CountryChName"))
                                .setCityName(object.getString("CityName"))
                                .setCityChName(object.getString("CityChName"))
                                .setCityId(object.getInt("CityId"))
                                .build();

                        listener.onFetchProgress(city);

                    }

                } catch (JSONException e) {
                    listener.onFetchFailed();
                }


                listener.onFetchComplete();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "Error :: " + error.getMessage());
                listener.onFetchComplete();
            }
        });
    }



    public interface CityCallbackListener{
        void onFetchStart();
        void onFetchProgress(City city);
        void onFetchProgress(List<City> cityList);
        void onFetchComplete();
        void onFetchFailed();

    }
}

