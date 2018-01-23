package br.liveo.hotelbook.hotelbook.hotels;

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
 * Created by phx on 2016/1/26.
 */
public class HotelController {

    private static final String TAG = HotelController.class.getSimpleName();
    private HotelCallbackListener listener;
    private RestApiManger restApiManger;

    public HotelController(HotelCallbackListener listener){
        this.listener = listener;
        restApiManger = new RestApiManger();

    }

    public void startFetching(String cityId){
        restApiManger.getHotelApi().getHotels(cityId, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.d(TAG, "JSON :: " + response);

                try {
                    JSONArray array = new JSONArray(s);

                    for(int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        Hotel hotel = new Hotel.Builder()
                                .setHotelName(object.getString("hotelname"))
                                .setHotelStar(object.getString("hotelstar"))
                                .setHotelRoomType(object.getString("hotelroomtype"))
                                .setHotelRoomPrice(object.getInt("hotelroomprice"))
                                .setHotelPriceType(object.getString("hotelpricetype"))
                                .setHotelDetailLink(object.getString("hoteldetaillink"))
                                .setHotelImgFile(object.getString("hotelimgfile"))
                                .setHotelId(object.getInt("id"))
                                .build();

                        listener.onFetchProgress(hotel);

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


    public interface HotelCallbackListener{
        void onFetchStart();
        void onFetchProgress(Hotel hotel);
        void onFetchProgress(List<Hotel> hotelList);
        void onFetchComplete();
        void onFetchFailed();

    }
}
