package com.example.koo.kit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Koo on 2017-08-04.
 */

public class SignUp extends Activity {
    RadioGroup rg1;
    RadioButton rd3, rd4;
    Spinner sp1;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        rg1 = (RadioGroup) findViewById(R.id.rdgroup1);
        sp1 =(Spinner)findViewById(R.id.txt_age_type);
        rd3 = (RadioButton) findViewById(R.id.buttonMan);
        rd4 = (RadioButton) findViewById(R.id.buttonWomen);




        Spinner AgeSpinner = (Spinner) findViewById(R.id.txt_age_type);
        ArrayAdapter AgeAdapter = ArrayAdapter.createFromResource(this,R.array.age,android.R.layout.simple_spinner_item);
        AgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AgeSpinner.setAdapter(AgeAdapter);


        Button button2 = (Button) findViewById(R.id.button02);
        button2.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getApplicationContext(), SignUp2.class);

                        EditText editTextId = (EditText)findViewById(R.id.editTextId);
                        intent.putExtra("page1_Id",editTextId.getText().toString());


                        EditText editTextPassword = (EditText)findViewById(R.id.editTextPassword);
                        intent.putExtra("page1_Password",editTextPassword.getText().toString());

                        EditText editTextPassword2 = (EditText)findViewById(R.id.editTextPassword2);
                        intent.putExtra("page1_Password2",editTextPassword2.getText().toString());

                        EditText editTextEmail = (EditText)findViewById(R.id.editTextEmail);
                        intent.putExtra("page1_Email",editTextEmail.getText().toString());

                        RadioButton rd1 = (RadioButton) findViewById(rg1.getCheckedRadioButtonId());
                        intent.putExtra("page1_rdgroup1",rd1.getText().toString());






                        String m1 = editTextId.getText().toString();
                        String m3 = editTextPassword.getText().toString();
                        String m4 = editTextPassword2.getText().toString();
                        String m2 = editTextEmail.getText().toString();





                        if(m1.getBytes().length <= 0)
                        {
                            Toast.makeText(getApplicationContext(),"아이디를 입력하세요",Toast.LENGTH_LONG).show();
                        }
                        else if(m3.getBytes().length <= 0)
                        {
                            Toast.makeText(getApplicationContext(),"비밀번호를 입력하세요",Toast.LENGTH_LONG).show();
                        }

                        else if(m2.getBytes().length <= 0)
                        {
                            Toast.makeText(getApplicationContext(),"이메일을 입력하세요",Toast.LENGTH_LONG).show();
                        }

                        else if(m3.getBytes().length>0) {
                            if (!m3.equals(m4)) {
                                Toast.makeText(getApplicationContext(), "비밀번호가 같은지 확인해주세요", Toast.LENGTH_LONG).show();
                            }
                            else {
                                startActivity(intent);
                            }
                        }




                        else if(rd3.isChecked()==false && rd4.isChecked()==false)
                        {
                            Toast.makeText(getApplicationContext(),"성별을 선택하세요",Toast.LENGTH_LONG).show();
                        }

                        else {
                            startActivity(intent);
                        }
                    }
                }
        );


        Button button3 = (Button) findViewById(R.id.button03);
        button3.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        startActivity(intent);
                    }

                }
        );

    }
}
