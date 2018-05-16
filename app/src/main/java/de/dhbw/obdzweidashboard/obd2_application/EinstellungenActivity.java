package de.dhbw.obdzweidashboard.obd2_application;

/**
 * Created by JZX8NT on 16.05.2018.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class EinstellungenActivity extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        Preference ipaddressPref = findPreference(getString(R.string.preference_mqtt_ipaddress_key));
        ipaddressPref.setOnPreferenceChangeListener(this);
        Preference userPref = findPreference(getString(R.string.preference_mqtt_user_key));
        userPref.setOnPreferenceChangeListener(this);
        Preference passPref = findPreference(getString(R.string.preference_mqtt_pass_key));
        passPref.setOnPreferenceChangeListener(this);

        // onPreferenceChange sofort aufrufen mit der in SharedPreferences gespeicherten Aktienliste
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

//        String gespeicherterUser = sharedPrefs.getString(userPref.getKey(), "");
//        onPreferenceChange(ipaddressPref, gespeicherterUser);
//        String gespeichertesPass = sharedPrefs.getString(passPref.getKey(), "");
//        onPreferenceChange(ipaddressPref, gespeichertesPass);
//        String gespeicherteIpAddress = sharedPrefs.getString(ipaddressPref.getKey(), "");
//        onPreferenceChange(ipaddressPref, gespeicherteIpAddress);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {

        preference.setSummary(value.toString());


        return true;
    }
}


   /* SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(this);
    String prefIpAddressKey = getString(R.string.preference_mqtt_ipaddress_key);
    String prefIpAddressDefault = getString(R.string.preference_mqtt_ipaddress_default);
    String ipadress = sPrefs.getString(prefIpAddressKey,prefIpAddressDefault);
    String prefUserKey = getString(R.string.preference_mqtt_user_key);
    String prefUserDefault = getString(R.string.preference_mqtt_user_default);
    String user = sPrefs.getString(prefUserKey,prefUserDefault);
    String prefPassKey = getString(R.string.preference_mqtt_pass_key);
    String prefPassDefault = getString(R.string.preference_mqtt_pass_default);
    String pass = sPrefs.getString(prefPassKey,prefPassDefault);
        mqttHelperLivedaten.serverUri=ipadress;
                mqttHelperLivedaten.username=user;
                mqttHelperLivedaten.password=pass;*/