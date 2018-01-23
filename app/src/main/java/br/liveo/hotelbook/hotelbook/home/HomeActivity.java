package br.liveo.hotelbook.hotelbook.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import br.liveo.hotelbook.hotelbook.R;
import br.liveo.hotelbook.hotelbook.city.CityActivity;
import br.liveo.hotelbook.hotelbook.hotels.HotelsActivity;
import br.liveo.hotelbook.hotelbook.ui.BaseActivity;
import br.liveo.hotelbook.hotelbook.ui.widget.DrawShadowFrameLayout;
import br.liveo.hotelbook.hotelbook.util.ToolUtils;
import br.liveo.hotelbook.hotelbook.util.UIUtils;

public class HomeActivity extends BaseActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private static final int CITY_RESULT = 1;

    private View rootView;
    //Define for inside
    private TextView checkInDate, checkOutDate, personNumber, roomNumber;
    private EditText destination;
    private Button btnSearch;
    private CalendarPickerView calendar;
    private AlertDialog theDialog, numberDialog;
    private ArrayList<Date> dates = new ArrayList<Date>();
    private ToolUtils toolUtils = new ToolUtils();
    private Boolean isCalendarDefault = false;
    private String[] arrayPerson = new String[]{};
    private String[] arrayRoom = new String[]{};
    private String searchString;
    private int cityId;
    private NumberPicker personNP, roomNP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle(R.string.title_home);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        rootView = findViewById(R.id.about_container);
        checkInDate = (TextView) rootView.findViewById(R.id.checkInDate);
        checkOutDate = (TextView) rootView.findViewById(R.id.checkOutDate);
        destination = (EditText) rootView.findViewById(R.id.destination);
        personNumber = (TextView) rootView.findViewById(R.id.personNumber);
        roomNumber = (TextView) rootView.findViewById(R.id.roomNumber);
        btnSearch = (Button) rootView.findViewById(R.id.btnSearch);
        overridePendingTransition(0, 0);
        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);


        init(nextYear, lastYear);
        calendarDefault();
    }
    private void init(final Calendar nextYear, final Calendar lastYear) {
        personNumber.setText("2");
        roomNumber.setText("1");
        //setCalendar(nextYear,lastYear);
        checkInDate.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               showCalendarInDialog(getResources().getString(R.string.check_in_date), R.layout.calendar_default);
                                               initCalendar(nextYear, lastYear);
                                           }
                                       }

        );
        checkOutDate.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                showCalendarInDialog(getResources().getString(R.string.check_out_date), R.layout.calendar_default);
                                                initCalendar(nextYear, lastYear);
                                            }
                                        }

        );
        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(HomeActivity.this, CityActivity.class);
                startActivityForResult(intent, CITY_RESULT);
            }
        });
        personNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPicker();
            }
        });
        roomNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPicker();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHotelsActivity(cityId);
            }
        });
    }

    private void calendarDefault() {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE, 3);
        checkInDate.setText(toolUtils.dateToString(today.getTime(), "yyyy-MM-dd"));
        dates.add(today.getTime());
        today.add(Calendar.DATE, 5);
        checkOutDate.setText(toolUtils.dateToString(today.getTime(), "yyyy-MM-dd"));
        dates.add(today.getTime());
        isCalendarDefault = true;
    }

    private void showCalendarInDialog(String title, int layoutResId) {
        calendar = (CalendarPickerView) getLayoutInflater().inflate(layoutResId, null, false);
        //"MM/dd/yyyy hh:mm:ss aa"
        if (!isCalendarDefault) {
            dates.clear();
            dates.add(toolUtils.ConvertToDate(checkInDate.getText().toString(), "yyyy-MM-dd"));
            dates.add(toolUtils.ConvertToDate(checkOutDate.getText().toString(), "yyyy-MM-dd"));
        }
        theDialog = new AlertDialog.Builder(this) //
                .setTitle(title)
                .setView(calendar)
                .setNeutralButton(getResources().getText(R.string.btn_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Log.d(TAG, "selected range dialog date:" + calendar.getSelectedDates());
                        int idate = calendar.getSelectedDates().size();
                        checkInDate.setText(toolUtils.dateToString(calendar.getSelectedDates().get(0), "yyyy-MM-dd"));
                        checkOutDate.setText(toolUtils.dateToString(calendar.getSelectedDates().get(idate - 1), "yyyy-MM-dd"));
                        isCalendarDefault = false;
                    }
                })
                .create();
        theDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Log.d(TAG, "onShow: fix the dimens!");
                calendar.fixDialogDimens();
            }
        });
        theDialog.show();

    }

    private void initCalendar(final Calendar nextYear, final Calendar lastYear) {

        calendar.setDecorators(Collections.<CalendarCellDecorator>emptyList());
        calendar.init(new Date(), nextYear.getTime()) //
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDates(dates);
    }

    private void showNumberPicker(){
        LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);
        final View v = inflater.inflate(R.layout.number_dialog, null);
        numberDialog = new AlertDialog.Builder(this) //
                .setTitle(getResources().getString(R.string.stay_number))
                .setView(v)
                .setPositiveButton(getResources().getText(R.string.btn_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int getPersonNumber = personNP.getValue() + 1;
                        int getRoomNumber = roomNP.getValue() + 1;
                        personNumber.setText(String.valueOf(getPersonNumber));
                        roomNumber.setText(String.valueOf(getRoomNumber));
                        dialog.dismiss();
                    }
                })
                .setNeutralButton(getResources().getText(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                })
                .create();
        numberDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Log.d(TAG, "onShow: fix the dimens!");
                arrayPerson = getResources().getStringArray(R.array.personNumber);
                personNP = (NumberPicker) numberDialog.findViewById(R.id.personNumberPicker);
                roomNP = (NumberPicker) numberDialog.findViewById(R.id.roomNumberPicker);
                int p = Integer.parseInt((String) personNumber.getText()) - 1;
                int r = Integer.parseInt((String) roomNumber.getText()) - 1;
                personNP.setMaxValue(arrayPerson.length - 1);
                personNP.setMinValue(0);
                personNP.setDisplayedValues(arrayPerson);
                personNP.setValue(p);
                personNP.setWrapSelectorWheel(false);
                personNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        Log.i("person value is", "" + newVal);
                    }
                });
                arrayRoom = getResources().getStringArray(R.array.roomNumber);
                roomNP.setMaxValue(arrayRoom.length - 1);
                roomNP.setMinValue(0);
                roomNP.setWrapSelectorWheel(false);
                roomNP.setDisplayedValues(arrayRoom);
                roomNP.setValue(r);
                roomNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        Log.i("room value is", "" + newVal);
                    }
                });
            }
        });
        numberDialog.show();
    }

    private void goToHotelsActivity(int cityId){
        Intent intent = new Intent();
        intent.setClass(HomeActivity.this, HotelsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("cityId", cityId);
        bundle.putString("checkInDate", (String) checkInDate.getText());
        bundle.putString("checkOutDate",(String) checkOutDate.getText());
        bundle.putString("personNumber", (String) personNumber.getText());
        bundle.putString("roomNumber", (String) roomNumber.getText());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_HOME;
    }

    private void setContentTopClearance(int clearance) {
        if (rootView != null) {
            rootView.setPadding(rootView.getPaddingLeft(), clearance,
                    rootView.getPaddingRight(), rootView.getPaddingBottom());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CITY_RESULT) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                if (bundle != null){
                    searchString = bundle.getString("searchString");
                    cityId = bundle.getInt("cityId");
                    destination.setText(searchString);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int actionBarSize = UIUtils.calculateActionBarSize(this);
        DrawShadowFrameLayout drawShadowFrameLayout =
                (DrawShadowFrameLayout) findViewById(R.id.main_content);
        if (drawShadowFrameLayout != null) {
            drawShadowFrameLayout.setShadowTopOffset(actionBarSize);
        }
        setContentTopClearance(actionBarSize);
    }
}
