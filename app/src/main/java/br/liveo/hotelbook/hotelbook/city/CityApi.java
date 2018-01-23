package br.liveo.hotelbook.hotelbook.city;

import br.liveo.hotelbook.hotelbook.util.RestApiManger;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by phx on 2016/1/29.
 */
public interface CityApi {
    @GET(RestApiManger.CITY_URL)
    void getCitys(Callback<String> citys);
}
