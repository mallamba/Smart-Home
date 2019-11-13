package com.tablelayout.javacodegeeks.mallamba;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tablelayout.javacodegeeks.tablayoutexample.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddForm.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddForm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddForm extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageButton test_btn, add_btn;
    private EditText ed_ip, ed_on, ed_off, ed_text;

    private OnFragmentInteractionListener mListener;
    RequestQueue queue;
    SecondFragment sF;
    public AddForm() {
        // Required empty public constructor

    }
    public void addSecondFragment(SecondFragment sF) {
        // Required empty public constructor
        this.sF = sF;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addForm.
     */
    // TODO: Rename and change types and number of parameters
    public static AddForm newInstance(String param1, String param2) {
        AddForm fragment = new AddForm();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_form, container, false);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        test_btn = getActivity().findViewById(R.id.btn_test);
        test_btn.setOnClickListener(this);
        add_btn = getActivity().findViewById(R.id.btn_add);
        add_btn.setOnClickListener(this);

        ed_ip = getActivity().findViewById(R.id.ip_addr);
        ed_on = getActivity().findViewById(R.id.on_com);
        ed_off = getActivity().findViewById(R.id.off_com);
        ed_text = getActivity().findViewById(R.id.plug_name);
        queue = Volley.newRequestQueue(getContext());
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        Vibrator vibe;
        vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        String[] inputs = new String[4];
        inputs[0] = "http://" + ed_ip.getText().toString() + "/update";
        inputs[1] = "http://" + ed_ip.getText().toString() + "/";
        inputs[2] = ed_off.getText().toString();
        inputs[3] = ed_text.getText().toString();


        switch (view.getId()) {
            // Living-room One
            case R.id.btn_test:
                vibe.vibrate(500);
                clickButton(test_btn, true, inputs[0], inputs[1] );
                break;
            // Living-room One
            case R.id.btn_add:
                vibe.vibrate(500);
                add_btn.setBackgroundColor(Color.WHITE);
                sF.createButton(inputs[3] , inputs[1]);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.remove(this);
                ft.commit();
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void clickButton(final ImageButton btn, final boolean repeat, String url, final String ip) {

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
                Toast.makeText(getContext(),"ERROR", Toast.LENGTH_SHORT).show();//display the response on screen
            }
        });

        queue.add(request);
    }

    public void makeAction(ImageButton btn, String str, String ip) {
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

}
