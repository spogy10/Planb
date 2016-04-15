package com.jr.poliv.planb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ChangePassword extends AppCompatActivity {

    protected EditText oldPassword, newPassword, newPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPassword = (EditText) findViewById(R.id.old_password);
        newPassword = (EditText) findViewById(R.id.new_password);
        newPassword2 = (EditText) findViewById(R.id.new_password2);


    }
}
