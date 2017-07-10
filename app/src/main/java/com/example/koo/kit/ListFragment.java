package com.example.koo.kit;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Koo on 2017-07-04.
 */

public class ListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //가아아아아아아아아아아ㅏㅇ은


    private String mParam1;
    private String mParam2;

    ListView listview;
    ListFragmentAdapter adapter;

    public ListFragment() {

    }

    public static ListFragment newInstances(String param1, String param2) {
        ListFragment fragment = new ListFragment();
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
        getActivity().setTitle(mParam1);
        View result =inflater.inflate(R.layout.fragment_list, container, false);

        listview = (ListView)result.findViewById(R.id.listview);
        listview.setOverScrollMode(View.OVER_SCROLL_ALWAYS);

        adapter = new ListFragmentAdapter();

        listview.setAdapter(adapter);

        adapter.addItem(getResources().getDrawable(R.drawable.aa), "봉구비어", "호프집입니다.") ;
        adapter.addItem(getResources().getDrawable(R.drawable.bb), "J BRO", "고기집입니다.") ;
        adapter.addItem(getResources().getDrawable(R.drawable.cc), "오향족발", "족발집입니다.") ;
        adapter.addItem(getResources().getDrawable(R.drawable.dd), "김수민", "지렸습니다.") ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListFragmentItem item = (ListFragmentItem) parent.getItemAtPosition(position) ;

                String titleStr = item.getTitle();
                String descStr = item.getDesc();
                //Drawable iconDrawable = item.getIcon();

                Toast.makeText(getContext(), titleStr, Toast.LENGTH_SHORT).show();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, DetailFragment.newInstances(titleStr, descStr));

                transaction.addToBackStack(null);
                transaction.commit();

                // TODO : use item data.
            }
        }) ;


        return result;
    }

}
