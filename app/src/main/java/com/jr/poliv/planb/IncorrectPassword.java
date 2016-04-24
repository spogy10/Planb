package com.jr.poliv.planb;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class IncorrectPassword extends AppCompatActivity {

    EditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incorrect_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        number = (EditText) findViewById(R.id.accept_phone_number);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(number.getText().toString())){
                    Toast.makeText(getApplicationContext(), "number field empty", Toast.LENGTH_LONG).show();
                }
                else{
                    //sendtext(number.getText().toString());
                    DialogFragment newFragment = AlertDialogueForTextMessage.newInstance(number.getText().toString());
                    newFragment.show(getSupportFragmentManager(), "dialog");
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void sendtext(String number){

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, "The password is polivers", null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
        }



    }





    public static class AlertDialogueForTextMessage extends DialogFragment {


        public static AlertDialogueForTextMessage newInstance(String number) {
            AlertDialogueForTextMessage frag = new AlertDialogueForTextMessage();
            Bundle args = new Bundle();
            args.putString("number", number);
            frag.setArguments(args);
            return frag;
        }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final String number = getArguments().getString("number");

            return new AlertDialog.Builder(getActivity())

                    .setTitle("Are you sure you want to send (standard carrier rates will apply)?")
                    .setPositiveButton("Yes, bye bye credit",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((IncorrectPassword)getActivity()).sendtext(number);
                                }
                            }
                    )
                    .setNegativeButton("No, I'm cheap and don't want to spend credit",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            }
                    )
                    .create();
        }
    }

}

