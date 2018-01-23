package br.liveo.hotelbook.hotelbook.hotels;

import br.liveo.hotelbook.hotelbook.util.RestApiManger;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by phx on 2016/1/26.
 */
public interface HotelApi {

    @GET(RestApiManger.HOTEL_URL)
    void getHotels(@Path("cityId") String cityId, Callback<String> hotel);
}
