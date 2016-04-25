package com.jr.poliv.planb;

/**
 * Created by poliv on 4/23/2016.
 */
public class StaticMethods {

    public static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic

        return (email.contains("@") && email.contains(".") && (email.length() > 4));
    }


    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}
