package com.tablelayout.javacodegeeks.mallamba;

import android.app.Activity;
import android.content.Intent;

import android.content.res.TypedArray;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.tablelayout.javacodegeeks.tablayoutexample.R;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListItemDetail extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listitem);
        /*
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);



        // Here we turn your string.xml in an array
        String[] myKeys = getResources().getStringArray(R.array.sections);


        TextView myTextView = (TextView) findViewById(R.id.my_textview1);
        myTextView.setText(myKeys[position]);


        ImageView myImageView = (ImageView) findViewById(R.id.my_imageview);
        //TypedArray imgs = getResources().obtainTypedArray(R.array.images);
        myImageView.setBackgroundResource(imgs[position]);



        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

       for (int i = 0; i < 8; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_discription", listviewShortDescription[i]);
            hm.put("listview_image", Integer.toString(listviewImage[i]));
            aList.add(hm);
        }

        String[] from = {"listview_image", "listview_title", "listview_discription"};
        int[] to = {R.id.my_imageview, R.id.my_textview1, R.id.my_textview2};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.activity_listitem, from, to);
        ListView androidListView = (ListView) findViewById(R.id.listView);
        androidListView.setAdapter(simpleAdapter);
*/
    }

}