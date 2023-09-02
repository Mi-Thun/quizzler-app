package edu.ewubd.quizzler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {
    private String errMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        try {
            if (sharedPreferences.getBoolean("isRememberMe", false)) {
                startActivity(new Intent(SignupActivity.this, CategoryActivity.class));
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        findViewById(R.id.tvLogIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ((EditText) findViewById(R.id.etName)).getText().toString().trim();
                String email = ((EditText) findViewById(R.id.etEmail)).getText().toString().trim();
                String mobile = ((EditText) findViewById(R.id.etMobile)).getText().toString().trim();
                String password = ((EditText) findViewById(R.id.etPassword)).getText().toString().trim();
                String re_password = ((EditText) findViewById(R.id.etRePassword)).getText().toString().trim();
                boolean cbRememberMe = ((CheckBox) findViewById(R.id.cbRememberMe)).isChecked();

                if (validateCredentials(name, email, mobile, password, re_password)) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", name);
                    editor.putString("email", email);
                    editor.putString("mobile", mobile);
                    editor.putString("password", password);
                    editor.putBoolean("isRememberMe", cbRememberMe);
                    editor.apply();

                    String keys[] = {"action", "name", "email", "mobile", "password"};
                    String values[] = {"singup", name, email, mobile, password};
                    httpRequest(keys, values);

                    Intent intent = new Intent(SignupActivity.this, CategoryActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    showErrorDialog(errMessage);
                }
            }
        });

    }
    private boolean validateCredentials(String name, String email, String phone, String password, String rePassword) {
        errMessage = "";
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() ||  password.isEmpty() || rePassword.isEmpty()) {
            errMessage += "All fields are required.\n---------------------\n";
        }

        if (name.length() < 4 || name.length() > 16 || !name.matches("[a-zA-Z]+")) {
            errMessage += "Name should be 4-16 characters.\n";
        }

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(emailPattern)) {
            errMessage += "Invalid email format.\n";
        }

        String regex = "^(\\+)?(88)?01[0-9]{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        if (!matcher.matches()) {
            errMessage += "Invalid Phone.\n";
        }

        if (password.length() < 4 || password.length() > 12) {
            errMessage += "Password should be 4-6 characters long.\n";
        }

        if (!password.equals(rePassword)) {
            errMessage += "Passwords do not match.\n";
        }

        if (errMessage.isEmpty()){
            return true;
        } else {
            return false;
        }
    }

    private void showErrorDialog(String errMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(errMessage);
        builder.setTitle("Error");
        builder.setCancelable(true);

        builder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void httpRequest(final String keys[],final String values[]){
        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... voids) {
                List<NameValuePair> params=new ArrayList<NameValuePair>();
                for (int i=0; i<keys.length; i++){
                    params.add(new BasicNameValuePair(keys[i],values[i]));
                }
                String url= "http://localhost/quizzler/";
                String data="";
                try {
                    data=JSONParser.getInstance().makeHttpRequest(url,"POST",params);
                    System.out.println(data);
                    return data;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            protected void onPostExecute(String data){
                if(data!=null){
                    System.out.println(data);
                    System.out.println("Ok2");
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}