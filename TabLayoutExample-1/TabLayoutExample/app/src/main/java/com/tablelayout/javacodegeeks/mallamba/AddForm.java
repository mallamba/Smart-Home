package com.tablelayout.javacodegeeks.mallamba;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

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

    public AddForm( ) {
        // Required empty public constructor
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
        test_btn.setOnClickListener(this);

        ed_ip = getActivity().findViewById(R.id.ip_addr);;
        ed_on = getActivity().findViewById(R.id.on_com);;
        ed_off = getActivity().findViewById(R.id.off_com);;
        ed_text = getActivity().findViewById(R.id.plug_name);;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        inputs[0] = ed_ip.getText().toString();
        inputs[1] = ed_on.getText().toString();
        inputs[2] = ed_off.getText().toString();
        inputs[3] = ed_text.getText().toString();
        switch (view.getId()) {
            // Living-room One
            case R.id.btn_test:
                vibe.vibrate(500);

                break;
            // Living-room One
            case R.id.btn_add:
                vibe.vibrate(500);

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
}
