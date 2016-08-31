package com.jr.poliv.planb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by poliv on 8/30/2016.
 */
public class ConfirmationDialog extends DialogFragment {

    ChangePassword changePassword = new ChangePassword();

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
                        changePassword.previousActivity();
                    }
                })
                .create();

    }
}