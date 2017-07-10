package com.example.koo.kit;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Koo on 2017-07-05.
 */

public class DetailFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ImageView img_detail;
    ImageView img_map;
    TextView text_title;
    TextView text_desc;

    public DetailFragment() {

    }

    public static DetailFragment newInstances(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
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
        getActivity().setTitle("Detail");
        View result =inflater.inflate(R.layout.fragment_detail, container, false);

        img_detail = (ImageView)result.findViewById(R.id.img_detail);
        img_map = (ImageView)result.findViewById(R.id.img_map);
        text_title = (TextView)result.findViewById(R.id.text_title);
        text_desc = (TextView)result.findViewById(R.id.text_desc);

        img_detail.setImageResource(R.drawable.a);
        img_map.setImageResource(R.drawable.map);
        text_title.setText(mParam1);

        return result;
    }

}
