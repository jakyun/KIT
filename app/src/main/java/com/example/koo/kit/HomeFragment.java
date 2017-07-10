package com.example.koo.kit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Koo on 2017-07-04.
 */

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Button btn_seoul;
    Button btn_manli;

    public HomeFragment(){

    }

    public static HomeFragment newInstances(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Home");
        View result =inflater.inflate(R.layout.fragment_home, container, false);
        btn_seoul = (Button)result.findViewById(R.id.btn_seoul);
        btn_manli = (Button)result.findViewById(R.id.btn_manli);

        buttonClickListener();
        return result;
    }

    private void buttonClickListener() {
        btn_seoul.setOnClickListener(ClickListener);
        btn_manli.setOnClickListener(ClickListener);
    }

    View.OnClickListener ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_seoul:
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, ListFragment.newInstances("서울", "0"));

                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;

                case R.id.btn_manli:
                    FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                    transaction2.replace(R.id.container, ListFragment.newInstances("만리동", "0"));

                    transaction2.addToBackStack(null);
                    transaction2.commit();
                    break;
            }
        }
    };
}
