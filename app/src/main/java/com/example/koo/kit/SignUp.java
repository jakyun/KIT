package com.example.koo.kit;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.Enumeration;
import java.util.Properties;


/**
 * Created by Koo on 2017-08-04.
 */

public class SignUp extends Activity {

    String encodedString = "";
    String result = "";
    String ip = "192.168.0.17";

    EditText edt_id, edt_pw, edt_pw2, edt_email;
    Button btn_accept, btn_cancel, btn_check;

    RadioGroup rdgroup;
    RadioButton rbtn_man, rbtn_women;

    Spinner spn_age_type;
    String string_age_type;

    int check = 0;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edt_id = (EditText)findViewById(R.id.edt_id);
        edt_pw = (EditText)findViewById(R.id.edt_pw);
        edt_pw2 = (EditText)findViewById(R.id.edt_pw2);
        edt_email = (EditText)findViewById(R.id.edt_email);
        btn_accept = (Button)findViewById(R.id.btn_accept);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);
        btn_check = (Button)findViewById(R.id.btn_check);

        rdgroup = (RadioGroup) findViewById(R.id.rdgroup);
        rbtn_man = (RadioButton) findViewById(R.id.rbtn_man);
        rbtn_women = (RadioButton) findViewById(R.id.rbtn_women);

        spn_age_type =(Spinner)findViewById(R.id.spn_age_type);
        Spinner AgeSpinner = (Spinner) findViewById(R.id.spn_age_type);

        ArrayAdapter AgeAdapter = ArrayAdapter.createFromResource(this,R.array.age,android.R.layout.simple_spinner_item);
        AgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AgeSpinner.setAdapter(AgeAdapter);

        buttonClickListener();
    }

    private void buttonClickListener() {
        btn_accept.setOnClickListener(ClickListener);
        btn_cancel.setOnClickListener(ClickListener);
        btn_check.setOnClickListener(ClickListener);
    }

    View.OnClickListener ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        switch(v.getId()){

            // 확인
            case R.id.btn_accept:
                Toast.makeText(getApplicationContext(), "확인.", Toast.LENGTH_SHORT).show();

                if(edt_id.getText().toString() == null || edt_id.getText().toString().length() == 0)
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();

                else if(edt_pw.getText().toString() == null || edt_pw.getText().toString().length() == 0)
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();

                else if(edt_pw2.getText().toString() == null || edt_pw2.getText().toString().length() == 0)
                    Toast.makeText(getApplicationContext(), "비밀번호 확인을 입력해주세요.", Toast.LENGTH_SHORT).show();

                else if(!edt_pw.getText().toString().equals(edt_pw2.getText().toString()))
                    Toast.makeText(getApplicationContext(), "비밀번호가 같지 않습니다.", Toast.LENGTH_SHORT).show();

                else if(edt_email.getText().toString() == null || edt_email.getText().toString().length() == 0)
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();

                else {
                    if(check == 0)
                        Toast.makeText(getApplicationContext(), "아이디 중복을 확인해주세요.", Toast.LENGTH_SHORT).show();

                    else {
                        Intent intent = new Intent(getApplicationContext(), SignUp2.class);

                        //아이디, 비번, 이메일
                        intent.putExtra("page1_Id", edt_id.getText().toString());
                        intent.putExtra("page1_Password", edt_pw.getText().toString());
                        intent.putExtra("page1_Password2", edt_pw2.getText().toString());
                        intent.putExtra("page1_Email", edt_email.getText().toString());
                        //라디오버튼
                        RadioButton rd1 = (RadioButton) findViewById(rdgroup.getCheckedRadioButtonId());
                        intent.putExtra("page1_rdgroup1", rd1.getText().toString());
                        //스피너
                        string_age_type = spn_age_type.getSelectedItem().toString();
                        intent.putExtra("age_type", string_age_type);

                        startActivity(intent);

                        finish();
                        SignUpAsyncTaskCall();
                    }
                }
                break;

            //취소
            case R.id.btn_cancel:
                Toast.makeText(getApplicationContext(), "취소.", Toast.LENGTH_SHORT).show();
                break;

            //중복확인
            case R.id.btn_check:
                Toast.makeText(getApplicationContext(), "중복확인.", Toast.LENGTH_SHORT).show();

                //아이디가 중복 확인
                CheckAsyncTaskCall();

                break;
        }
        }
    };

    public void SignUpAsyncTaskCall(){
        new SignUpAsyncTask().execute();
    }

    public class SignUpAsyncTask extends AsyncTask<String,Integer,String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {  // 통신을 위한 Thread
            result = recvList();
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

            HttpURLConnection urlConnection = null;
            URL url = null;
            DataOutputStream out = null;
            BufferedInputStream buf = null;
            BufferedReader bufreader = null;

            RadioButton rd1 = (RadioButton) findViewById(rdgroup.getCheckedRadioButtonId());

            Properties prop = new Properties();
            prop.setProperty("id", edt_id.getText().toString());
            prop.setProperty("pw", edt_pw.getText().toString());
            prop.setProperty("age", spn_age_type.getSelectedItem().toString());
            prop.setProperty("email", edt_email.getText().toString());
            prop.setProperty("gender", rd1.getText().toString());

            encodedString = encodeString(prop);

            try{
                url = new URL("http://" + ip + ":8080/kit/insertUser.do");
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
                String result = "";

                while((line = bufreader.readLine()) != null){
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

        }
    }

    public void CheckAsyncTaskCall(){
        new CheckAsyncTask().execute();
    }

    public class CheckAsyncTask extends AsyncTask<String,Integer,String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {  // 통신을 위한 Thread
            result = recvList();
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

            HttpURLConnection urlConnection = null;
            URL url = null;
            DataOutputStream out = null;
            BufferedInputStream buf = null;
            BufferedReader bufreader = null;

            Properties prop = new Properties();
            prop.setProperty("id", edt_id.getText().toString());

            encodedString = encodeString(prop);

            try{
                url = new URL("http://" + ip + ":8080/kit/checkUser.do");
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
                String result = "";

                while((line = bufreader.readLine()) != null){
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
            checkList(result);
        }
    }

    private void checkList(String recv) {

        Log.i("서버에서 받은 전체 내용 : ", recv);

        try{
            JSONObject json= new JSONObject(recv);
            JSONArray jArr = json.getJSONArray("checkUser");

            // 아이디가 있을 때
            if(jArr != null && jArr.length() > 0) {

                Toast.makeText(getApplicationContext(), "이미 사용중인 아이디입니다.", Toast.LENGTH_SHORT).show();

                /*
                json = jArr.getJSONObject(0);

                id = json.getInt("id"); //정회원과 준회원 구분

                // 정회원일때
                if(userCode == 1) {
                    userId = json.getInt("userId");
                    userName = json.getString("userName");
                    userBirth = json.getString("userBirth");
                    userGender = json.getString("userGender");

                    // 자동 로그인
                    SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);   //쉐어드 객체 얻기
                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();                        //쉐어드 쓰기
                    sharedPreferencesEditor.putInt("userId", userId);
                    sharedPreferencesEditor.putString("userTel", edt_tel.getText().toString());
                    sharedPreferencesEditor.putString("userName", userName);
                    sharedPreferencesEditor.putString("userBirth", userBirth);
                    sharedPreferencesEditor.putString("userGender", userGender);
                    if(json.length() == 7) { //이메일이 NULL이 아닐 때
                        userEmail = json.getString("userEmail");
                        sharedPreferencesEditor.putString("userEmail", userEmail);
                    }
                    sharedPreferencesEditor.commit();

                    Intent i = new Intent(getApplicationContext(), Main.class);
                    startActivity(i);
                    finish();
                }
                */
            }

            // 아이디가 없을 때
            else {
                Toast.makeText(getApplicationContext(), "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                check = 1;
                /*
                Intent i = new Intent(getApplicationContext(), Enroll.class);
                i.putExtra("userTel", edt_tel.getText().toString());
                startActivity(i);
                finish();
                */
            }

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

}
