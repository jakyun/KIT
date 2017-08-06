package com.example.koo.kit;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
 * Created by Koo on 2017-08-06.
 */

public class Login extends Activity{

    EditText edt_login_id, edt_login_pw;
    Button btn_login;
    TextView text_signup, text_find;

    String encodedString = "";
    String result = "";
    String ip;

    String id, pw, email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        ip = sharedPreferences.getString("ip", "");

        edt_login_id = (EditText)findViewById(R.id.edt_login_id);
        edt_login_pw = (EditText)findViewById(R.id.edt_login_pw);

        btn_login = (Button)findViewById(R.id.btn_login);

        text_signup = (TextView) findViewById(R.id.text_signup);
        text_find = (TextView)findViewById(R.id.text_find);

        buttonClickListener();
    }

    private void buttonClickListener() {
        btn_login.setOnClickListener(ClickListener);
        text_signup.setOnClickListener(ClickListener);
        text_find.setOnClickListener(ClickListener);
    }

    View.OnClickListener ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_login:
                    if(edt_login_id.getText().toString() == null || edt_login_id.getText().toString().length() == 0)
                        Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();

                    else if(edt_login_pw.getText().toString() == null || edt_login_pw.getText().toString().length() == 0)
                        Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();

                    else {
                        LoginAsyncTaskCall();
                    }
                    break;

                case R.id.text_signup:
                    Toast.makeText(getApplicationContext(), "회원가입", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), SignUp.class);
                    startActivity(i);
                    finish();
                    break;

                case R.id.text_find:
                    Toast.makeText(getApplicationContext(), "아이디/비번 찾기", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void LoginAsyncTaskCall(){
        new LoginAsyncTask().execute();
    }

    public class LoginAsyncTask extends AsyncTask<String,Integer,String> {

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
            prop.setProperty("id", edt_login_id.getText().toString());

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

            // 아이디가 있을때
            if(jArr != null && jArr.length() > 0) {
                json = jArr.getJSONObject(0);

                Toast.makeText(getApplicationContext(), "아이디가 있습니다.", Toast.LENGTH_SHORT).show();

                pw = json.getString("password");

                // 비번이 맞는다면   --->   로그인!!!!!
                if(pw.equals(edt_login_pw.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show();

                    email = json.getString("email");

                    Intent i = new Intent(getApplicationContext(), MyPage.class);
                    startActivity(i);

                    // 자동 로그인
                    SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);   //쉐어드 객체 얻기
                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();                        //쉐어드 쓰기
                    sharedPreferencesEditor.putString("userId", edt_login_id.getText().toString());
                    sharedPreferencesEditor.putString("userEmail", email);
                    sharedPreferencesEditor.commit();

                    finish();
                }

                // 비번이 틀리다면
                else {
                    Toast.makeText(getApplicationContext(), "비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                }
            }

            // 아이디가 없을때
            else {
                Toast.makeText(getApplicationContext(), "아이디가 없습니다.", Toast.LENGTH_SHORT).show();
            }

        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
