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

public class RestrauntList extends Fragment {
    /*
    private List<String> mData;
    private ArrayAdapter<String> mAdapter;
    private ListView mListView;
    */

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
                "봉구비어", "맥주집입니다");
        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.ic_pork),
                "J Bro", "고기집입니다");
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.ic_pork2),
                "오향족발", "족발집입니다");
        return layout;
    }

        /*
        //1.데이터준비
        initData();


        //2.Adapter준비
        initAdapter();


        //3.Listview에 Adapter 연결하기
        initListView();
    }

    private void initData() {
        mData = new ArrayList<>();
        for(int i = 1; i <= 10;i++) {
            mData.add("Item" + i);
        }
    }

    private void initAdapter() {
        mAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, mData);
    }


    private void initListView() {

        mListView = (ListView)findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);
    }
       /*
        ListView listView = (ListView)findViewById(R.id.list);

        ArrayList<item> data = new ArrayList<>();
        item beer = new item(R.drawable.beer,"Beer");
        item jbro = new item(R.drawable.jbro,"Jbro");
        item pork = new item(R.drawable.pork,"Pork");

        data.add(beer);
        data.add(jbro);
        data.add(pork);

        ListviewAdapter adapter = new ListviewAdapter(this,R.layout.item,data);
        listView.setAdapter(adapter);
        */
}
