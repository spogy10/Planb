package com.jr.poliv.planb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {

    protected EditText oldPassword, newPassword, newPassword2;
    CheckBox oldPasswordCheckBox, newPasswordCheckBox, newPassword2CheckBox;
    Button savePassword;

    String file_name;
    String variable_name;
    SharedPreferences file;
    SharedPreferences.Editor editor;
    String currentPassword;
    DialogFragment dialog = new ConfirmationDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        oldPassword = (EditText) findViewById(R.id.old_password);
        oldPasswordCheckBox = (CheckBox) findViewById(R.id.old_password_checkBox);
        newPassword = (EditText) findViewById(R.id.new_password);
        newPasswordCheckBox = (CheckBox) findViewById(R.id.new_password_checkBox);
        newPassword2 = (EditText) findViewById(R.id.new_password2);
        newPassword2CheckBox = (CheckBox) findViewById(R.id.new_password2_checkBox);
        savePassword = (Button) findViewById(R.id.save_new_password_button);

        file_name = getString(R.string.pasword_file_name);
        variable_name = getString(R.string.password);
        file = this.getSharedPreferences(file_name, Context.MODE_PRIVATE);

        currentPassword = initialiseCurrentPassword();

        if (currentPassword.equals(StaticMethods.defaultPassword)){
            oldPasswordCheckBox.setChecked(true);
            oldPassword.setText(StaticMethods.defaultPassword);
        }


        oldPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d("Paul", "The focus for oldPassword has changed");
                if(!hasFocus)
                    evaluateOldPasswordEditText();
            }
        });

        newPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    evaluateNewPasswordEditText();

                    evaluateNewPassword2EditText();
                }
            }
        });

        newPassword2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    evaluateNewPassword2EditText();

            }
        });

        savePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                savePasswordChange(evaluateOldPasswordEditText(),evaluateNewPasswordEditText(),evaluateNewPassword2EditText());
            }
        });



    }


    String initialiseCurrentPassword(){

        if(!file.contains(variable_name))
            return StaticMethods.defaultPassword;
        else
            return file.getString(variable_name, StaticMethods.defaultPassword);
    }


    void savePasswordChange(boolean A, boolean B, boolean C){
        if (!oldPasswordCheckBox.isChecked())
            Toast.makeText(this, "old password incorrect", Toast.LENGTH_SHORT).show();
        else if(!newPasswordCheckBox.isChecked())
            Toast.makeText(this, "password not valid", Toast.LENGTH_SHORT).show();
        else if (!newPassword2CheckBox.isChecked())
            Toast.makeText(this, "passwords  don't match", Toast.LENGTH_SHORT).show();
        else
            changePassword();
    }

    void changePassword() {
        String password = newPassword.getText().toString();
        editor = file.edit();
        if (editor.putString(variable_name, password).commit())
            setDialog("The new password is " + password);
        else
            Toast.makeText(this, "error saving password", Toast.LENGTH_LONG).show();
    }

    boolean evaluateOldPasswordEditText(){
        if (!currentPassword.equals(StaticMethods.defaultPassword)) {
            if (oldPassword.getText().toString().equals(currentPassword))
                oldPasswordCheckBox.setChecked(true);
            else {
                oldPasswordCheckBox.setChecked(false);
                oldPassword.setError(getString(R.string.error_incorrect_password));
            }
        }
        return true;
    }

    boolean evaluateNewPasswordEditText(){
        if(StaticMethods.isPasswordValid(newPassword.getText().toString()))
            newPasswordCheckBox.setChecked(true);
        else {
            newPasswordCheckBox.setChecked(false);
            newPassword.setError(getString(R.string.error_invalid_password));
        }
        return true;
    }

    boolean evaluateNewPassword2EditText(){
        if(newPassword2.getText().toString().equals(newPassword.getText().toString()))
            newPassword2CheckBox.setChecked(true);
        else {
            newPassword2CheckBox.setChecked(false);
            newPassword2.setError("passwords  don't match");
        }
        return true;
    }

    void previousActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    void setDialog(String message) {
        Bundle args = new Bundle();
        args.putString("message", message);
        dialog.setArguments(args);
        dialog.show(getFragmentManager(), "");
    }


    public class ConfirmationDialog extends DialogFragment{



        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){

            Bundle args = getArguments();
            String message = args.getString("message", "");

            return new AlertDialog.Builder(getActivity())
                    .setTitle("Password Successfully Changed")
                    .setMessage(message)
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            previousActivity();
                        }
                    })
                    .create();

        }
    }


}
