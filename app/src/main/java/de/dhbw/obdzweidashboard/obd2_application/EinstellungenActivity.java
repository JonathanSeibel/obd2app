package de.dhbw.obdzweidashboard.obd2_application;

/**
 * Created by JZX8NT on 16.05.2018.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

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

        Preference pacePref = findPreference(getString(R.string.preference_pace_key));
        pacePref.setOnPreferenceChangeListener(this);
        Preference rpmPref = findPreference(getString(R.string.preference_rpm_key));
        rpmPref.setOnPreferenceChangeListener(this);
        Preference oilTempPref = findPreference(getString(R.string.preference_oiltemp_key));
        oilTempPref.setOnPreferenceChangeListener(this);
        Preference dtcCountPref = findPreference(getString(R.string.preference_dtccount_key));
        dtcCountPref.setOnPreferenceChangeListener(this);
        Preference fuelLevelPref = findPreference(getString(R.string.preference_fuellevel_key));
        fuelLevelPref.setOnPreferenceChangeListener(this);
        Preference fuelRatePref = findPreference(getString(R.string.preference_fuelrate_key));
        fuelRatePref.setOnPreferenceChangeListener(this);
        Preference ladedruckPref = findPreference(getString(R.string.preference_ladedruck_key));
        ladedruckPref.setOnPreferenceChangeListener(this);
        Preference throttlePosPref = findPreference(getString(R.string.preference_throttlepos_key));
        throttlePosPref.setOnPreferenceChangeListener(this);
        Preference coolwaterTempPref = findPreference(getString(R.string.preference_coolwatertemp_key));
        coolwaterTempPref.setOnPreferenceChangeListener(this);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        Toast.makeText(this, "Restart of Application may be necessary...", Toast.LENGTH_SHORT).show();
        return true;
    }
}