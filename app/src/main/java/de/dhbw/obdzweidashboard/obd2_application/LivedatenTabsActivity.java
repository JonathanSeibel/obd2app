package de.dhbw.obdzweidashboard.obd2_application;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;

public class LivedatenTabsActivity extends AppCompatActivity {
    private static final String TAG = "LivedatenTabsActivity";
    TabPaceFragment tabPaceFragmentObj;
    TabRPMFragment tabRPMFragmentObj;
    TabOilTempFragment tabOilTempFragmentObj;
    TabLadeDruckFragment tabLadeDruckFragmentObj;
    TabCoolwaterTempFragment tabCoolwaterTempFragmentObj;
    TabFuelRateFragment tabFuelRateFragmentObj;
    TabFuelLevelFragment tabFuelLevelFragmentObj;
    TabThrottlePositionFragment tabThrottlePositionFragmentObj;
    TabDTCCountFragment tabDTCCountFragmentObj;
    TextView wertText1;
    TextView wertText2;
    TextView wertText3;
    TextView wertText4;
    TextView wertText5;
    TextView wertText6;
    MqttHelperLivedaten mqttHelperLivedaten;
    String[] ergebnis = {"N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A"};
    boolean connected = false;
    private ViewPager mViewPager;
    private AsyncTask backgroundThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livedaten_tabs);

        tabPaceFragmentObj = new TabPaceFragment();
        tabRPMFragmentObj = new TabRPMFragment();
        tabOilTempFragmentObj = new TabOilTempFragment();
        tabLadeDruckFragmentObj = new TabLadeDruckFragment();
        tabCoolwaterTempFragmentObj = new TabCoolwaterTempFragment();
        tabFuelRateFragmentObj = new TabFuelRateFragment();
        tabFuelLevelFragmentObj = new TabFuelLevelFragment();
        tabThrottlePositionFragmentObj = new TabThrottlePositionFragment();
        tabDTCCountFragmentObj = new TabDTCCountFragment();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        aktualisiere(ergebnis);
        startMqtt(this);
    }

    public void aktualisiere(String[] daten) {
        //WENN neue Nachricht mit aktuellen Daten kam ->

        tabPaceFragmentObj.setSpeed(daten[0]);
        tabRPMFragmentObj.setSpeed(daten[1]);
        tabOilTempFragmentObj.setSpeed(daten[2]);
        tabCoolwaterTempFragmentObj.setSpeed(daten[3]);
        tabDTCCountFragmentObj.setSpeed(daten[4]);
        tabFuelLevelFragmentObj.setSpeed(daten[5]);
        tabFuelRateFragmentObj.setSpeed(daten[6]);
        tabLadeDruckFragmentObj.setSpeed(daten[7]);
        tabThrottlePositionFragmentObj.setSpeed(daten[8]);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mqttHelperLivedaten.mqttAndroidClient.isConnected()) try {
            mqttHelperLivedaten.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //startMqtt(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        //Einstellungen abfragen
        SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String prefPaceKey = getString(R.string.preference_pace_key);
        Boolean pace = sPrefs.getBoolean(prefPaceKey, true);

        String prefRPMKey = getString(R.string.preference_rpm_key);
        Boolean rpm = sPrefs.getBoolean(prefRPMKey, true);

        String prefOilTempKey = getString(R.string.preference_oiltemp_key);
        Boolean oilTemp = sPrefs.getBoolean(prefOilTempKey, true);

        String prefCoolwaterTempKey = getString(R.string.preference_coolwatertemp_key);
        Boolean coolwaterTemp = sPrefs.getBoolean(prefCoolwaterTempKey, true);

        String prefDTCCountKey = getString(R.string.preference_dtccount_key);
        Boolean dtcCount = sPrefs.getBoolean(prefDTCCountKey, true);

        String prefFuelLevelKey = getString(R.string.preference_fuellevel_key);
        Boolean fuelLevel = sPrefs.getBoolean(prefFuelLevelKey, true);

        String prefFuelRateKey = getString(R.string.preference_fuelrate_key);
        Boolean fuelRate = sPrefs.getBoolean(prefFuelRateKey, true);

        String prefLadedruckKey = getString(R.string.preference_ladedruck_key);
        Boolean ladedruck = sPrefs.getBoolean(prefLadedruckKey, true);

        String prefThrottlePosKey = getString(R.string.preference_throttlepos_key);
        Boolean throttlePos = sPrefs.getBoolean(prefThrottlePosKey, true);
        //
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        if (pace) adapter.addFragment(tabPaceFragmentObj, "TABPace");
        if (rpm) adapter.addFragment(tabRPMFragmentObj, "TABRPM");
        if (oilTemp) adapter.addFragment(tabOilTempFragmentObj, "TABOilTemp");
        if (coolwaterTemp) adapter.addFragment(tabCoolwaterTempFragmentObj, "TABCoolwaterTemp");
        if (dtcCount) adapter.addFragment(tabDTCCountFragmentObj, "TABDTCCount");
        if (fuelLevel) adapter.addFragment(tabFuelLevelFragmentObj, "TABFuelLevel");
        if (fuelRate) adapter.addFragment(tabFuelRateFragmentObj, "TABFuelRate");
        if (ladedruck) adapter.addFragment(tabLadeDruckFragmentObj, "TABLadeDruck");
        if (throttlePos) adapter.addFragment(tabThrottlePositionFragmentObj, "TABThrottlePosition");
        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_livedaten_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, EinstellungenActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void startMqtt(final Activity activity) {
        SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
        mqttHelperLivedaten = new MqttHelperLivedaten(getApplicationContext(), sPrefs, activity);
        mqttHelperLivedaten.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Toast.makeText(activity, "Verbindung zu Pi-Livedaten hergestellt.", Toast.LENGTH_SHORT).show();
                MqttMessage m = new MqttMessage("Live".getBytes());
                try {
                    mqttHelperLivedaten.mqttAndroidClient.publish("Control", m);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void connectionLost(Throwable throwable) {
                Toast.makeText(activity, "Verbindung zu Pi-Livedaten verloren.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                String gsonString = mqttMessage.toString();
                Gson gson = new Gson();
                LiveData data = gson.fromJson(gsonString, LiveData.class);
                if (data.datavalue.equals("Car not connected")) {
                    Toast.makeText(activity, "Bitte Auto verbinden...", Toast.LENGTH_SHORT).show();
                } else if (data.datavalue.equals("Adapter not connected")) {
                    Toast.makeText(activity, "Bitte Adapter verbinden...", Toast.LENGTH_SHORT).show();
                } else {
                    if (data.type.equals("kmh")) ergebnis[0] = data.datavalue;
                    if (data.type.equals("rpm")) ergebnis[1] = data.datavalue;
                    if (data.type.equals("oiltemp")) ergebnis[2] = data.datavalue;
                    if (data.type.equals("coolwatertemp")) ergebnis[3] = data.datavalue;
                    if (data.type.equals("dtccount")) ergebnis[4] = data.datavalue;
                    if (data.type.equals("fuellevel")) ergebnis[5] = data.datavalue;
                    if (data.type.equals("fuelrate")) ergebnis[6] = data.datavalue;
                    if (data.type.equals("ladedruck")) ergebnis[7] = data.datavalue;
                    if (data.type.equals("throttlepos")) ergebnis[8] = data.datavalue;
                    if (data.type.equals("VIN")) setVIN(data.datavalue);
                }

                aktualisiere(ergebnis);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

    void setVIN(String vin) {
        TextView textVIN = (TextView) findViewById(R.id.textViewVIN);
        textVIN.setText("VIN: " + vin);
    }
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

    public class LiveData {

        public String datavalue;
        public String type;

    }
}
