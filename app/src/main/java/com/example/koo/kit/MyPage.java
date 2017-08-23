package com.example.koo.kit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Koo on 2017-08-04.
 */

public class MyPage extends AppCompatActivity {

    ViewPager vp;

    String ip;
    String userId, userEmail;

    Button btn_login, btn_logout;
    TextView text_id, text_email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        ip = sharedPreferences.getString("ip", "");
        userId = sharedPreferences.getString("userId", "");
        userEmail = sharedPreferences.getString("userEmail", "");

        text_id = (TextView)findViewById(R.id.text_id);
        text_email = (TextView)findViewById(R.id.text_email);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_logout = (Button) findViewById(R.id.btn_logout);

        // 로그인 되어있으면
        if (!userId.equals("")) {
            text_id.setText(userId);
            text_email.setText(userEmail);
            btn_login.setVisibility(View.INVISIBLE);
            btn_logout.setVisibility(View.VISIBLE);
        }
        else {
            text_id.setText("로그인해주세요.");
            text_email.setText("");
            btn_login.setVisibility(View.VISIBLE);
            btn_logout.setVisibility(View.INVISIBLE);
        }

        // ViewPager
        vp = (ViewPager) findViewById(R.id.vp);
        Button btn_first = (Button) findViewById(R.id.btn_first);
        Button btn_second = (Button) findViewById(R.id.btn_second);

        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(0);

        btn_first.setOnClickListener(movePageListener);
        btn_first.setTag(0);
        btn_second.setOnClickListener(movePageListener);
        btn_second.setTag(1);

        buttonClickListener();
    }

    private void buttonClickListener() {
        btn_login.setOnClickListener(ClickListener);
        btn_logout.setOnClickListener(ClickListener);
    }

    View.OnClickListener ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_login:
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    break;

                case R.id.btn_logout:
                    Toast.makeText(getApplicationContext(), "로그아웃", Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();                        //쉐어드 쓰기
                    sharedPreferencesEditor.putString("userId", "");
                    sharedPreferencesEditor.putString("userEmail", "");
                    sharedPreferencesEditor.commit();

                    Intent i = new Intent(getApplicationContext(), MyPage.class);
                    startActivity(i);
                    finish();
                    break;
            }
        }
    };

    View.OnClickListener movePageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int tag = (int) v.getTag();
            vp.setCurrentItem(tag);
        }
    };

    private class pagerAdapter extends FragmentStatePagerAdapter
    {
        public pagerAdapter(FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public Fragment getItem(int position)
        {
            switch(position)
            {
                case 0:
                    return new Reservation();
                case 1:
                    return new MyFavorite();
                default:
                    return null;
            }
        }
        @Override
        public int getCount()
        {
            return 2;
        }
    }
}
