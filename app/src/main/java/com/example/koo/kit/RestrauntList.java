package com.example.koo.kit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by Koo on 2017-08-04.
 */

public class

RestrauntList extends Fragment {

    public final static String ITEMS_COUNT_KEY = "RestrauntList$ItemsCount";

    public static RestrauntList createInstance(int itemsCount) {
        RestrauntList RestrauntList = new RestrauntList();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        RestrauntList.setArguments(bundle);
        return RestrauntList;
    }

    ListView listView;

    MyListAdapter adapter;
    ArrayList<MyItem> storeList = new ArrayList<MyItem>();

    String encodedString="";
    String ip;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result =inflater.inflate(R.layout.activity_list, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences", getActivity().MODE_PRIVATE);
        ip = sharedPreferences.getString("ip", "");

        //ListView
        listView = (ListView)result.findViewById(R.id.listview);
        listView.setOverScrollMode(View.OVER_SCROLL_ALWAYS);

        //MyListAdapter
        adapter = new MyListAdapter(getContext(), storeList, R.layout.activity_item);

        //ListView 안에 MyListAdapter 넣기
        listView.setAdapter(adapter);
        listView.setDivider(null);

        StoreAsyncTaskCall();

        return result;
    }

/* *********************************************************************************************************************************** */

    public class MyItem{
        public String url="http://" + ip + ":8080/kit/image/";

        String id="";
        String type="";
        String photoName="";
        String title="";
        String info="";

        public MyItem(String id, String type, String photoName, String title, String info){
            super();
            this.id = id;
            this.type = type;
            this.photoName = url+photoName;
            this.title = title;
            this.info = info;
        }
    }

    public class MyListAdapter extends BaseAdapter {
        Context context;
        LayoutInflater inflater;
        ArrayList<MyItem> myItems;
        int layout;

        public MyListAdapter(Context context, ArrayList<MyItem> myItems, int layout){
            this.context=context;
            this.myItems=myItems;
            this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.layout=layout;
        }

        @Override
        public int getCount() {
            return myItems.size();
        }

        @Override
        public Object getItem(int position) {return position;}

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos =position;
            if(convertView == null) {
                convertView = inflater.inflate(layout,parent,false);
            }

            final ImageView imageView=(ImageView)convertView.findViewById(R.id.businessImage);
            Glide.with(this.context).load(myItems.get(pos).photoName).into(imageView);

            TextView title=(TextView)convertView.findViewById(R.id.text_title);
            title.setText(myItems.get(pos).title);

            TextView info=(TextView)convertView.findViewById(R.id.text_info);
            info.setText(myItems.get(pos).info);


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //클릭 시 이동 !!
                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    intent.putExtra("businessId", myItems.get(pos).id);
                    intent.putExtra("businessType", myItems.get(pos).type);
                    //Toast.makeText(getContext(), myItems.get(pos).id, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }

/* *********************************************************************************************************************************** */

    // busienss의 모든정보와 photo의 이름을 가져옴
    public void StoreAsyncTaskCall(){
        new StoreAsyncTask().execute();
    }

    public class StoreAsyncTask extends AsyncTask<String,Integer,String> {

        protected void onPreExecute(){
        }

        @Override
        protected String doInBackground(String... params) {  // 통신을 위한 Thread
            String result =recvList();
            return result;
        }

        public String encodeString(Properties params) {  //한글 encoding??
            StringBuffer sb = new StringBuffer(256);
            Enumeration names = params.propertyNames();

            while (names.hasMoreElements()) {
                String name = (String) names.nextElement();
                String value = params.getProperty(name);
                sb.append(URLEncoder.encode(name) + "=" + URLEncoder.encode(value) );

                if (names.hasMoreElements()) sb.append("&");
            }
            return sb.toString();
        }

        private String recvList() { //데이터 보내고 받아오기!!

            HttpURLConnection urlConnection=null;
            URL url =null;
            DataOutputStream out=null;
            BufferedInputStream buf=null;
            BufferedReader bufreader=null;

            Properties prop = new Properties();
            prop.setProperty("businessType", "1"); // 음식점 Type = 1

            encodedString = encodeString(prop);

            try{
                url=new URL("http://" + ip + ":8080/kit/businessList.do");
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setUseCaches(false);

                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                out = new DataOutputStream(urlConnection.getOutputStream());

                out.writeBytes(encodedString);

                out.flush();    //서버로 버퍼의 내용 전송

                buf = new BufferedInputStream(urlConnection.getInputStream());
                bufreader = new BufferedReader(new InputStreamReader(buf,"utf-8"));

                String line = null;
                String result="";

                while((line=bufreader.readLine())!=null){
                    result += line;
                }

                return result;

            }catch(Exception e){
                e.printStackTrace();
                return "";
            }finally{
                urlConnection.disconnect();  //URL 연결해제
            }
        }
        protected void onPostExecute(String result){  //Thread 이후 UI 처리 result는 Thread의 리턴값!!!
            jsonFirstList(result);
        }
    }

    private void jsonFirstList(String recv) {

        Log.i("서버에서 받은 전체 내용 : ", recv);

        try{
            JSONObject json = new JSONObject(recv);
            JSONArray jArr = json.getJSONArray("businessList");

            int i;
            for (i = 0; i < jArr.length(); i++ ) {
                json = jArr.getJSONObject(i);
                storeList.add(new MyItem(json.getString("businessId"), json.getString("businessType"), json.getString("photoName"), json.getString("businessName"),json.getString("businessExplanation")));
            }
            adapter.notifyDataSetChanged();     //리스트
            //     mLockListView=false;

        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
