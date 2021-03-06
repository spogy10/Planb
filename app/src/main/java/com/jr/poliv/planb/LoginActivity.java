package com.jr.poliv.planb;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    String mEmail;
    String mPassword;
    View focusView = null;
    public int numberOfPasswordAttemps;
    Intent intent;

    SharedPreferences file;

    EditText mEmailView;
    EditText mPasswordView;
    TextView numberOfAttemptsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        file = this.getSharedPreferences(getString(R.string.pasword_file_name), Context.MODE_PRIVATE);
        mPassword = initialiseCurrentPassword();

        numberOfPasswordAttemps = 0;
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        numberOfAttemptsTextView = (TextView) findViewById(R.id.number_of_attempts_text_view);
        assert numberOfAttemptsTextView != null;

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin((mEmailView.getText().toString()), (mPasswordView.getText().toString()));
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.login_button);
        assert mEmailSignInButton != null;
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               attemptLogin((mEmailView.getText().toString()), (mPasswordView.getText().toString()));

            }
        });


    }



    String initialiseCurrentPassword(){

        if(!file.contains(getString(R.string.password)))
            return StaticMethods.defaultPassword;
        else
            return file.getString(getString(R.string.password), StaticMethods.defaultPassword);
    }

            private boolean attemptLogin(String email, String password) {

                if (!(validityCheck(email, password)))
                {
                    focusView.requestFocus();
                    return false;
                }

                if(password.equals(mPassword)){
                    mEmail = email;
                    //TODO:add code to change to next activity here and send email with intent
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("mEmail", mEmail);
                    startActivity(intent);
                    return true;
                }
                numberOfPasswordAttemps++;
                if (numberOfPasswordAttemps >= 10){
                    intent = new Intent(LoginActivity.this, IncorrectPassword.class);
                    startActivity(intent);
                }
                numberOfAttemptsTextView.setText(String.valueOf(10-numberOfPasswordAttemps));
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
                return false;
            }






        private boolean validityCheck(String email, String password) {

            // Check for a valid password, if the user entered one.
            if ((TextUtils.isEmpty(password)) || !(StaticMethods.isPasswordValid(password))) {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;
                return false;
            }

            // Check for a valid email address.
            if (TextUtils.isEmpty(email)) {
                mEmailView.setError(getString(R.string.error_field_required));
                focusView = mEmailView;
                return false;

            } else if (!(StaticMethods.isEmailValid(email))) {
                mEmailView.setError(getString(R.string.error_invalid_email));
                focusView = mEmailView;
                return false;
            }
            return true;
        }


}

