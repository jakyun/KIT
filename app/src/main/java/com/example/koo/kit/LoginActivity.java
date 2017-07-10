package com.example.koo.kit;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Koo on 2017-07-05.
 */

public class LoginActivity extends Activity {

    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        btn_login = (Button)findViewById(R.id.btn_login);

        buttonClickListener();
    }

    private void buttonClickListener() {
        btn_login.setOnClickListener(ClickListener);
    }

    View.OnClickListener ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_login:
                    Toast.makeText(getApplicationContext(), "로그인", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
    };

}
