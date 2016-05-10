package com.jr.poliv.planb;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by poliv on 4/23/2016.
 */
public abstract class StaticMethods {


    public static String defaultPassword = "polivers";

    public static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic

        return (email.contains("@") && email.contains(".") && (email.length() > 4));
    }


    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


}
