package com.example.greeshma.bloodbank;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by allwyn on 21-09-2015.
 */
public class SaveSharedPreference {
    static final String REGISTER = "fragment_register";
    static final String NAME = "name";
    static final String MOBILE = "mobile";
    static final String DOB = "dob";


    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static String getRegister(Context ctx) {
        return getSharedPreferences(ctx).getString(REGISTER, "");
    }

    public static void setRegister(Context ctx, String registry) {
        SharedPreferences.Editor editor1 = getSharedPreferences(ctx).edit();
        editor1.putString(REGISTER, registry);
        editor1.commit();
    }

    public static String getName(Context ctx) {
        return getSharedPreferences(ctx).getString(NAME, "");
    }

    public static void setName(Context ctx, String registry) {
        SharedPreferences.Editor editor1 = getSharedPreferences(ctx).edit();
        editor1.putString(NAME, registry);
        editor1.commit();
    }

    public static String getMobile(Context ctx) {
        return getSharedPreferences(ctx).getString(MOBILE, "");
    }

    public static void setMobile(Context ctx, String registry) {
        SharedPreferences.Editor editor1 = getSharedPreferences(ctx).edit();
        editor1.putString(MOBILE, registry);
        editor1.commit();
    }
    public static String getDob(Context ctx) {
        return getSharedPreferences(ctx).getString(DOB, "");
    }

    public static void setDob(Context ctx, String registry) {
        SharedPreferences.Editor editor1 = getSharedPreferences(ctx).edit();
        editor1.putString(DOB, registry);
        editor1.commit();
    }


}
//SSETGENDER