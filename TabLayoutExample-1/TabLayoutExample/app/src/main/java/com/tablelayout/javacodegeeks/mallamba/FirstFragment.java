package com.tablelayout.javacodegeeks.mallamba;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.ListView;

import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tablelayout.javacodegeeks.tablayoutexample.R;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static android.content.Context.ALARM_SERVICE;


public class FirstFragment extends Fragment implements View.OnClickListener {

    View view;

    Vibrator vibe;
    TextView time;
    TimePicker firstTimePicker;



    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private static MainActivity inst;
    private TextView alarmTextView;

    public static MainActivity instance() {
        return inst;
    }

    /*
        Fragment is added!
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.w("--------- onAttatch", " -------------------");
    }

    @Override
    public void onCreate(Bundle saved) {
        super.onCreate(saved);
        Log.w("--------- onCreate", " -------------------");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_first, container, false);
        vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        return  view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        alarmTimePicker = (TimePicker) getActivity().findViewById(R.id.alarmTimePicker);
        alarmTextView = (TextView) getActivity().findViewById(R.id.alarmText);
        ToggleButton alarmToggle = (ToggleButton) getActivity().findViewById(R.id.alarmToggle);
        alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

        ToggleButton button = getActivity().findViewById(R.id.alarmToggle);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onToggleClicked(view);
    }

    public void onToggleClicked(View view) {
        if (((ToggleButton) view).isChecked()) {
            Log.d("MyActivity", "Alarm On");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
            Intent myIntent = new Intent(getContext(), AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(getContext(), 0, myIntent, 0);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            Log.d("alarm set to ", "" + calendar.getTimeInMillis() );


        } else {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();

            setAlarmText("");
            Log.d("MyActivity", "Alarm Off");
        }
    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }



}
