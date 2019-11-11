package com.tablelayout.javacodegeeks.mallamba;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tablelayout.javacodegeeks.tablayoutexample.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.content.ContentValues.TAG;
import static android.provider.ContactsContract.CommonDataKinds.Website.URL;


public class SecondFragment extends Fragment
        implements View.OnClickListener {
    Button livBtn1, livBtn2, hallBtn, bedBtn, kitchBtn1, kitchBtn2, compBtn, tvBtn, stdBtn1, stdBtn2, aquBtn, floorBtn;
    Button add_btn;
    String[] urlArray, ipArray, nameArray;
    Button[] btnArray;

    RequestQueue queue;

    Vibrator vibe;
    // textview.setMovementMethod(new ScrollingMovementMethod());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        return inflater.inflate(R.layout.fragment_second, container, false);


    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initButtons();
        initUrls();
        initNames();
        update();

    }


    public void initButtons() {
        add_btn = getActivity().findViewById(R.id.add_plug);
        add_btn.setOnClickListener(this);
        btnArray = new Button[12];
        btnArray[0] = getActivity().findViewById(R.id.livingBtn1);

        btnArray[1] = getActivity().findViewById(R.id.livingBtn2);
        btnArray[2] = getActivity().findViewById(R.id.hallBtn);
        btnArray[3] = getActivity().findViewById(R.id.bedBtn);
        btnArray[4] = getActivity().findViewById(R.id.kitchenBtn1);
        btnArray[5] = getActivity().findViewById(R.id.kitchenBtn2);
        btnArray[6] = getActivity().findViewById(R.id.compBtn);
        btnArray[7] = getActivity().findViewById(R.id.tvBtn);
        btnArray[8] = getActivity().findViewById(R.id.studioBtn1);
        btnArray[9] = getActivity().findViewById(R.id.studioBtn2);
        btnArray[10] = getActivity().findViewById(R.id.aquariumBtn);
        btnArray[11] = getActivity().findViewById(R.id.floorBtn);

        for (int i = 0; i < btnArray.length; i++) {
            btnArray[i].setOnClickListener(this);
        }

        queue = Volley.newRequestQueue(getContext());
    }

    public void initUrls() {
        urlArray = getResources().getStringArray(R.array.urlArray);
        ipArray = getResources().getStringArray(R.array.ipArray);
    }

    public void initNames() {
        nameArray = getResources().getStringArray(R.array.namesArray);
    }

    @Override
    public void onClick(View view)
    {
        String url, ip;
        switch (view.getId()) {
            // Living-room One
            case R.id.add_plug:
                vibe.vibrate(500);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_placeholder, new AddForm());
                ft.commit();
                break;
            // Living-room One
            case R.id.livingBtn1:
                vibe.vibrate(500);
                url = "http://192.168.0.121/update";
                ip = "http://192.168.0.121/";
                clickButton(btnArray[0], true, url, ip);
                break;
            // Living-room Two
            case R.id.livingBtn2:
                vibe.vibrate(500);
                url = "http://192.168.0.122/update";
                ip = "http://192.168.0.122/";
                clickButton(btnArray[1], true, url, ip);
                break;
             //*******************************************//

            // Hall
            case R.id.hallBtn:
                vibe.vibrate(500);
                url = "http://192.168.0.123/update";
                ip = "http://192.168.0.123/";
                clickButton(btnArray[2], true, url, ip);
                break;
            // Bedroom
            case R.id.bedBtn:
                vibe.vibrate(500);
                url = "http://192.168.0.124/update";
                ip = "http://192.168.0.124/";
                clickButton(btnArray[3], true, url, ip);
                break;
            //*******************************************//

            // Kitchen One
            case R.id.kitchenBtn1:
                vibe.vibrate(500);
                url = "http://192.168.0.131/update";
                ip = "http://192.168.0.131/";
                clickButton(btnArray[4], true, url, ip);
                break;
            // Kitchen Two
            case R.id.kitchenBtn2:
                vibe.vibrate(500);
                url = "http://192.168.0.132/update";
                ip = "http://192.168.0.132/";
                clickButton(btnArray[5], true, url, ip);
                break;
            //*******************************************//

            // Studio One
            case R.id.studioBtn1:
                vibe.vibrate(500);
                url = "http://192.168.0.137/update";
                ip = "http://192.168.0.137/";
                clickButton(btnArray[8], true, url, ip);
                break;
            // Studio Two
            case R.id.studioBtn2:
                vibe.vibrate(500);
                url ="http://192.168.0.139/update";
                ip = "http://192.168.0.139/";
                clickButton(btnArray[9], true, url, ip);
                break;
            //*******************************************//

            // Aquarium
            case R.id.aquariumBtn:
                vibe.vibrate(500);
                url = "http://192.168.0.127/update";
                ip = "http://192.168.0.127/";
                clickButton(btnArray[10], true, url, ip);
                break;
            // Nothing
            /*
            case R.id.studioBtn2:
            vibe.vibrate(500);
                url ="http://192.168.0.139/update";
                ip = "http://192.168.0.139/";
                clickButton(stdBtn2, true, url, ip);
                break;
                */
            //*******************************************//
        }
    }

    public void clickButton(final Button btn, final boolean repeat, String url, final String ip) {

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(repeat)
                    makeAction( btn, response.toString(), ip);

                if(response.equals("0"))
                    btn.setBackgroundColor(Color.RED);
                else if(response.equals("1"))
                    btn.setBackgroundColor(Color.WHITE);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                int index = indexOf(btnArray, btn);
                String message = "Unknown error";
                if(index != -1)
                    message = "Connection error: " + nameArray[index];
                Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();//display the response on screen
                btn.setBackgroundColor(Color.GRAY);
            }
        });

        queue.add(request);
    }

    public void makeAction(Button btn, String str, String ip) {
        String url = ip;
        if(str.equals("0")) {
            url += "on";
            clickButton(btn, false, url, ip);

        }
        else if(str.equals("1")) {
            url += "off";
            clickButton(btn, false, url, ip);
        }
    }

    public void update() {
        for (int i = 0; i < urlArray.length; i++) {
            vibe.vibrate(200);
            clickButton(btnArray[i], false, urlArray[i], ipArray[i]);
        }
    }

    public int indexOf(Object[] array, Object obj) {
        int index = -1;
        for(int i = 0; i < array.length; i++) {
            if(obj == array[i])
                index = i;
        }
        return index;
    }

}
