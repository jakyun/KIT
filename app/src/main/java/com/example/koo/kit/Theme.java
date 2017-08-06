package com.example.koo.kit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Koo on 2017-08-04.
 */

public class Theme extends AppCompatActivity {

    Button btn_theme1;
    Button btn_theme2;
    Button btn_theme3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString("ip", "192.168.0.69" + "");  //ip 설정하기 저장하기
        sharedPreferencesEditor.commit();

        btn_theme1 = (Button)findViewById(R.id.btn_theme1);
        btn_theme2 = (Button)findViewById(R.id.btn_theme2);
        btn_theme3 = (Button)findViewById(R.id.btn_theme3);

        buttonClickListener();
    }

    private void buttonClickListener() {
        btn_theme1.setOnClickListener(ClickListener);
        btn_theme2.setOnClickListener(ClickListener);
        btn_theme3.setOnClickListener(ClickListener);
    }

    View.OnClickListener ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_theme1:
                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                    //intent1.putExtra("theme", btn_theme1.getText().toString());
                    startActivity(intent1);
                    break;

                case R.id.btn_theme2:
                    Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                    //intent2.putExtra("theme", btn_theme1.getText().toString());
                    startActivity(intent2);
                    break;

                case R.id.btn_theme3:
                    Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
                    //intent3.putExtra("theme", btn_theme1.getText().toString());
                    startActivity(intent3);
                    break;
            }
        }
    };
}
