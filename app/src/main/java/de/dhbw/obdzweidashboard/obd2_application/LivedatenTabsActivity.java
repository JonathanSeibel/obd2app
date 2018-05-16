package de.dhbw.obdzweidashboard.obd2_application;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LivedatenTabsActivity extends AppCompatActivity {
    private static final String TAG = "LivedatenTabsActivity";
    private ViewPager mViewPager;
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
    private AsyncTask backgroundThread;
    MqttHelperLivedaten mqttHelperLivedaten;
    String[] ergebnis = new String[6];
    boolean connected = false;

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


        startMqtt(this);
    }

    public void aktualisiere(String[] daten) {
        //WENN neue Nachricht mit aktuellen Daten kam ->

//        Tab1Fragment tab1 = (Tab1Fragment) Tab1FragmentObj;
//        Tab2Fragment tab2 = (Tab2Fragment) Tab2FragmentObj;
//        Tab3Fragment tab3 = (Tab3Fragment) Tab3FragmentObj;
//        Tab4Fragment tab4 = (Tab4Fragment) Tab4FragmentObj;
//        Tab5Fragment tab5 = (Tab5Fragment) Tab5FragmentObj;
//        Tab6Fragment tab6 = (Tab6Fragment) Tab6FragmentObj;
//
//        tab1.setSpeed(daten[0]);
//        tab2.setSpeed(daten[1]);
//        tab3.setSpeed(daten[2]);
//        tab4.setSpeed(daten[3]);
//        tab5.setSpeed(daten[4]);
//        tab6.setSpeed(daten[5]);

        tabPaceFragmentObj.setSpeed(daten[0]);
        tabRPMFragmentObj.setSpeed(daten[1]);
        tabOilTempFragmentObj.setSpeed(daten[2]);
        tabCoolwaterTempFragmentObj.setSpeed(daten[3]);
        tabDTCCountFragmentObj.setSpeed(daten[4]);
        tabThrottlePositionFragmentObj.setSpeed(daten[5]);
        tabFuelLevelFragmentObj.setSpeed("asdf");
        tabFuelRateFragmentObj.setSpeed("sdf");
        tabLadeDruckFragmentObj.setSpeed("aöldkfj");
    }

    @Override
    protected void onStop() {
        super.onStop();

//        backgroundThread.cancel(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        backgroundThread.cancel(true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        backgroundThread = new VerbindungZuPiRunnable(this).execute("");
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(tabPaceFragmentObj, "TABPace", 0);
        adapter.addFragment(tabRPMFragmentObj, "TABRPM", 1);
        adapter.addFragment(tabOilTempFragmentObj, "TABOilTemp", 2);
        adapter.addFragment(tabCoolwaterTempFragmentObj, "TABCoolwaterTemp", 3);
        adapter.addFragment(tabDTCCountFragmentObj, "TABDTCCount", 4);
        adapter.addFragment(tabThrottlePositionFragmentObj, "TABThrottlePosition", 5);
        adapter.addFragment(tabFuelLevelFragmentObj, "TABFuelLevel", 6);
        adapter.addFragment(tabFuelRateFragmentObj, "TABFuelRate", 7);
        adapter.addFragment(tabLadeDruckFragmentObj, "TABLadeDruck", 8);
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
        mqttHelperLivedaten = new MqttHelperLivedaten(getApplicationContext(), this);
        mqttHelperLivedaten.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Toast.makeText(activity, "Connected to PI!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                JSONObject jsnonO = new JSONObject();
//                jsnonO = JSONObject.(JSONObject)mqttMessage.toString();
                ergebnis[0] = mqttMessage.toString();
                ergebnis[1] = mqttMessage.toString();
                ergebnis[2] = mqttMessage.toString();
                ergebnis[3] = mqttMessage.toString();
                ergebnis[4] = mqttMessage.toString();
                ergebnis[5] = mqttMessage.toString();
                aktualisiere(ergebnis);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public void addFragment(Fragment fragment, String title, int position){
            mFragmentList.add(position, fragment);
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
}
