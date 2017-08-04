package com.example.koo.kit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Koo on 2017-08-04.
 */

public class SignUp2 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        Intent intent = getIntent();

        TextView textViewId = (TextView) findViewById(R.id.textViewId);
        String Id = intent.getStringExtra("page1_Id");
        if(Id != null) {
            textViewId.setText(Id);
        }
        TextView textViewNickname = (TextView) findViewById(R.id.textViewEmail);
        String Email = intent.getStringExtra("page1_Email");
        if(Email != null) {
            textViewNickname.setText(Email);
        }
        TextView textViewPassword = (TextView) findViewById(R.id.textViewPassword);
        String Password = intent.getStringExtra("page1_Password");
        if(Password != null) {
            textViewPassword.setText(Password);
        }

        TextView textViewMW = (TextView) findViewById(R.id.textViewMW);
        String MW = intent.getStringExtra("page1_rdgroup1");
        if(MW != null) {
            textViewMW.setText(MW);
        }

        TextView textViewAge = (TextView) findViewById(R.id.textViewAge);
        String Age = intent.getStringExtra("age_type");
        if(Age != null) {
            textViewAge.setText(Age);
        }


    }
}
