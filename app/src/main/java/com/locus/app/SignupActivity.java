package com.locus.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import controller.UserManager;
import model.User;

/**
 * Created by Melker on 2015-11-15.
 */
public class SignupActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);


        final EditText editTextUsername = (EditText) findViewById(R.id.editText_username);
        final EditText editTextEmail = (EditText) findViewById(R.id.editText_email);
        final EditText editTextPassword = (EditText) findViewById(R.id.editText_password);

        final UserManager userManager = new UserManager();


        ImageButton btnsignup = (ImageButton) findViewById(R.id.imageButton_signup);
        btnsignup.setOnClickListener(new View.OnClickListener() {

             public void onClick(View vi) {

                 String userName = editTextUsername.getText().toString();
                 String email = editTextEmail.getText().toString();
                 String password = editTextPassword.getText().toString();

                 User user = userManager.addUser(email,userName,password,"Singapore");

                 if(user.getEmail().equals(email)){
                     Intent intent = new Intent(vi.getContext(), MainActivity.class);
                     intent.putExtra("email",email);
                     startActivityForResult(intent, 0);
                 }
                 else{
                     Toast.makeText(SignupActivity.this, "User not added", Toast.LENGTH_SHORT).show();
                 }

             }
         }
        );


    }
}
