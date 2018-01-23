package br.liveo.hotelbook.hotelbook.hotels;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import br.liveo.hotelbook.hotelbook.R;
import br.liveo.hotelbook.hotelbook.util.RestApiManger;


/**
 * Created by phx on 2016/1/26.
 */
public class HotelsAdapter extends RecyclerView.Adapter<HotelsAdapter.Holder>{

    public static String TAG = HotelsAdapter.class.getSimpleName();
    public interface OnItemClickListener{
        void onItemClick(Hotel hotelList);
    }
    private List<Hotel> hotelList;
    private OnItemClickListener listener;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private static final String[] IMAGE_URLS = RestApiManger.IMAGES;
    private DisplayImageOptions options;


    public HotelsAdapter(List<Hotel> hotelList, Context context, OnItemClickListener listener){
        this.hotelList = hotelList;
        this.listener = listener;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_stub)
                .showImageForEmptyUri(R.mipmap.ic_empty)
                .showImageOnFail(R.mipmap.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

    }

    public void addHotel(Hotel hotel){
        hotelList.add(hotel);
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_cardview, parent, false);

        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Hotel hotel = hotelList.get(position);
        holder.bind(hotelList.get(position), listener);
        holder.hotelName.setText(hotel.hotelName);
        holder.hotelRoomType.setText(hotel.hotelRoomType);
        holder.hotelStar.setText(hotel.hotelStar);
        holder.hotelRoomPrice.setText(hotel.hotelPriceType + " $" + String.valueOf(hotel.hotelRoomPrice));
        if (position > IMAGE_URLS.length - 1){
            int random = (int)(Math.random()* IMAGE_URLS.length);
            position = random;
        }
        ImageLoader.getInstance().displayImage(IMAGE_URLS[position], holder.hotelImgFile, options, animateFirstListener);
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        public TextView hotelName, hotelRoomType, hotelStar,hotelRoomPrice;
        public ImageView hotelImgFile;

        public Holder(View itemView){
            super(itemView);
            hotelName =(TextView) itemView.findViewById(R.id.hotelName);
            hotelRoomType =(TextView) itemView.findViewById(R.id.hotelRoomType);
            hotelStar =(TextView) itemView.findViewById(R.id.hotelStar);
            hotelRoomPrice =(TextView) itemView.findViewById(R.id.hotelRoomPrice);
            hotelImgFile =(ImageView) itemView.findViewById(R.id.hotelImgFile);
        }

        public void bind(final Hotel item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

}

