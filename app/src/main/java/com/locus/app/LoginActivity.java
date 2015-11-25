package com.locus.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import controller.UserManager;

/*
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;*/

public class LoginActivity extends Activity {

    private TextView info;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signin);

        final EditText editTextEmail = (EditText) findViewById(R.id.editText_email);
        final EditText editTextPassword = (EditText) findViewById(R.id.editText_password);

        ImageButton btnsignup = (ImageButton) findViewById(R.id.signup_button);
        btnsignup.setOnClickListener(new View.OnClickListener() {

                                         public void onClick(View vi) {

                                             Intent intent = new Intent(vi.getContext(), SignupActivity.class);
                                             startActivityForResult(intent, 0);

                                         }
                                     }
        );

        ImageButton btn = (ImageButton) findViewById(R.id.signin_button);
        btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               UserManager userManager = new UserManager();

               String email = editTextEmail.getText().toString();
               String password = editTextPassword.getText().toString();


              if( userManager.login(email,password, LoginActivity.this) ){
                  Intent intent = new Intent(v.getContext(), MainActivity.class);
                  intent.putExtra("email",email);
                  startActivityForResult(intent, 0);
                  finish();
              }
               else{
                  Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
              }
              }
           }
        );


    }
}
