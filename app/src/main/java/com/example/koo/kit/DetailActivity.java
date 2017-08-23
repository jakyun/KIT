package com.example.koo.kit;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Koo on 2017-08-18.
 */

public class DetailActivity extends Activity {

    String ip="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        ip = sharedPreferences.getString("ip", "");

        buttonClickListener();
    }

    private void buttonClickListener() {
        //btn_login.setOnClickListener(ClickListener);
    }

    View.OnClickListener ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                /*
                case R.id.btn_login:
                    if(edt_login_id.getText().toString() == null || edt_login_id.getText().toString().length() == 0)
                        Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();

                    else if(edt_login_pw.getText().toString() == null || edt_login_pw.getText().toString().length() == 0)
                        Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();

                    else {
                        LoginAsyncTaskCall();
                    }
                    break;
                */
            }
        }
    };
}
