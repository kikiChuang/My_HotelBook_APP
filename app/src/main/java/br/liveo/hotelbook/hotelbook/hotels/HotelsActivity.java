package br.liveo.hotelbook.hotelbook.hotels;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.liveo.hotelbook.hotelbook.R;
import br.liveo.hotelbook.hotelbook.home.HomeActivity;
import br.liveo.hotelbook.hotelbook.hoteldetails.hoteldetailsActivity;

public class HotelsActivity extends AppCompatActivity implements HotelController.HotelCallbackListener {

    private static final String TAG = HotelsActivity.class.getSimpleName();
    private Bundle bundle;
    private int cityId;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Hotel> hotelList = new ArrayList<>();
    private HotelsAdapter hotelsAdapter;
    private HotelController hotelController;
    private TextView checkInDate, checkOutDate, personNumber, roomNumber;
    private String sCheckInDate, sCheckOutDate, sPersonNumber, sRoomNumber;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);
        checkInDate = (TextView) findViewById(R.id.checkInDate);
        checkOutDate = (TextView) findViewById(R.id.checkOutDate);
        personNumber = (TextView) findViewById(R.id.personNumber);
        roomNumber = (TextView) findViewById(R.id.roomNumber);
        bundle = getIntent().getExtras();
        cityId = bundle.getInt("cityId");
        sCheckInDate = bundle.getString("checkInDate");
        sCheckOutDate = bundle.getString("checkOutDate");
        sPersonNumber = bundle.getString("personNumber");
        sRoomNumber = bundle.getString("roomNumber");
        context = getApplicationContext();
        hotelController = new HotelController(HotelsActivity.this);
        configViews();
        hotelController.startFetching(String.valueOf(cityId));
        setToolbar();
        init();
    }

    private void init(){
        checkInDate.setText(sCheckInDate.toString());
        checkOutDate.setText(sCheckOutDate.toString());
        personNumber.setText(sPersonNumber.toString());
        roomNumber.setText(sRoomNumber.toString());


    }

    private void configViews() {
        recyclerView = (RecyclerView) this.findViewById(R.id.list);
        swipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.swipe);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(HotelsActivity.this));
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());

        hotelsAdapter = new HotelsAdapter(hotelList, context, new HotelsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Hotel hotelList) {
                Toast.makeText(context, "Item Clicked"+hotelList.hotelName, Toast.LENGTH_LONG).show();
                GotoActivity(new Intent(HotelsActivity.this, hoteldetailsActivity.class));

            }
        });
        recyclerView.setAdapter(hotelsAdapter);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_accent),
                getResources().getColor(R.color.app_primary_accent),
                getResources().getColor(R.color.theme_primary_dark));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                hotelController.startFetching(String.valueOf(cityId));
            }
        });
    }

    private void setToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.title_hotels);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * Enables back navigation for activities that are launched from the NavBar. See
     * {@code AndroidManifest.xml} to find out the parent activity names for each activity.
     * @param intent
     */
    private void GotoActivity(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            TaskStackBuilder builder = TaskStackBuilder.create(this);
            builder.addNextIntentWithParentStack(intent);
            builder.startActivities();
        } else {
            startActivity(intent);
            finish();
        }
    }


    @Override
    public void onFetchStart() {

    }

    @Override
    public void onFetchProgress(Hotel hotel) {
        hotelsAdapter.addHotel(hotel);
    }

    @Override
    public void onFetchProgress(List<Hotel> hotelList) {

    }

    @Override
    public void onFetchComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFetchFailed() {

    }
}
