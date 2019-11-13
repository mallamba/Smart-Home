package com.tablelayout.javacodegeeks.mallamba;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tablelayout.javacodegeeks.tablayoutexample.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class SecondFragment extends Fragment
        implements View.OnClickListener {

    Button add_btn;
    String[] urlArray, ipArray, nameArray;
    Button[] btnArray;

    Map<String,String> ip_Map;
    List<Button> btn_list;
    GridLayout l_layout;
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
        l_layout = getActivity().findViewById(R.id.conts);
        add_btn = getActivity().findViewById(R.id.add_plug);
        add_btn.setOnClickListener(this);

        btn_list = new ArrayList<>();
        ip_Map = new HashMap<>();
        queue = Volley.newRequestQueue(getContext());
    }

    public void initUrls() {
        SharedPreferences keyValues = getContext().getSharedPreferences("hashmap", Context.MODE_PRIVATE);
        SharedPreferences.Editor keyValuesEditor = keyValues.edit();

        try {
            ip_Map = (Map<String, String>) keyValues.getAll();
        }catch(Exception e ){
            Log.d(" ---------------  ---- ",e.getMessage() );
            ip_Map = new HashMap<>();
        }
        filButtons();

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
                AddForm aF = new AddForm();
                aF.addSecondFragment(this);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_placeholder, aF);

                ft.commit();
                break;

        }
        for (Button b : btn_list) {
            if(b.getId() == view.getId()){
                clickButton(b, true, ip_Map.get( b.getText() )+"update", ip_Map.get( b.getText() ));
            }

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

        //for (int i = 0; i < urlArray.length; i++) {
          //  vibe.vibrate(200);
            //clickButton(btnArray[i], false, urlArray[i], ipArray[i]);
        //}
    }

    public int indexOf(Object[] array, Object obj) {
        int index = -1;
        for(int i = 0; i < array.length; i++) {
            if(obj == array[i])
                index = i;
        }
        return index;
    }


    public void createButton(String btn_text, String ip_text){
        Button btn = new Button( getContext() );
        btn.setText(btn_text);
        btn.setWidth(0);
        btn.setHeight(80);
        btn.setId(View.generateViewId());
        GridLayout.LayoutParams param = new GridLayout.LayoutParams( GridLayout.spec( GridLayout.UNDEFINED,GridLayout.FILL,1f), GridLayout.spec(GridLayout.UNDEFINED,GridLayout.FILL,1f) );
        btn.setLayoutParams(param);
        btn_list.add(btn);
        btn_list.get(btn_list.indexOf(btn)).setOnClickListener(this);
        ip_Map.put(btn_text, ip_text);
        l_layout.addView(btn);

        saveHashMap();
    }

    public void filButtons(){
        l_layout.removeAllViewsInLayout();
        for (String s : ip_Map.keySet()) {
            createButton( s, ip_Map.get(s));
        }
        /*
        l_layout.addView(btn);
        btn_list.add(btn);
        btn_list.get(btn_list.indexOf(btn)).setOnClickListener(this);
        ip_Map.put(btn_text, ip_text);
        */

    }

    public void saveHashMap(){
        SharedPreferences keyValues = getContext().getSharedPreferences("hashmap", Context.MODE_PRIVATE);
        SharedPreferences.Editor keyValuesEditor = keyValues.edit();

        for (String s : ip_Map.keySet()) {
            keyValuesEditor.putString(s, ip_Map.get(s));
        }

        keyValuesEditor.apply();
    }

}
