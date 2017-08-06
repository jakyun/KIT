package com.example.koo.kit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by Koo on 2017-08-04.
 */

public class SightseeingList extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.activity_list, container, false);

        ListView listview;
        ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) layout.findViewById(R.id.listview);
        listview.setAdapter(adapter);

        // 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.ic_beer),
                "관광1", "맥주집입니다");
        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.ic_pork),
                "J Bro", "고기집입니다");
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.ic_pork2),
                "오향족발", "족발집입니다");
        return layout;
    }
}
