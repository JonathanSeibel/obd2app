package de.dhbw.obdzweidashboard.obd2_application;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;

public class SummaryLivedatenActivity extends Activity {
    AdapterLivedatenSummaryListe adapter;
    ArrayList<String> listValues;
    ArrayList<String> listTypes;
    MqttHelperLivedaten mqttHelperLivedaten;
    String[] ergebnis = {"N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A"};
    boolean pace;
    boolean rpm;
    boolean oilTemp;
    boolean coolwaterTemp;
    boolean dtcCount;
    boolean fuelLevel;
    boolean fuelRate;
    boolean ladedruck;
    boolean throttlePos;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_livedaten);

        listValues = new ArrayList<String>();
        listTypes = new ArrayList<String>();

        //EEEEEEEEEEEEEEEEIIIIIIIIIIIIIIIIIINNNNNNNNNNNNNNNNNNNSSSSSSSSTTTTTEEEEELLLUUUUUUUUUUUUUUNNNNNNNNNNGGGGGGGGGGGGGEEEEEEEEEENNNNNNNNNNNNNNN
        SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String prefPaceKey = getString(R.string.preference_pace_key);
        pace = sPrefs.getBoolean(prefPaceKey, true);

        String prefRPMKey = getString(R.string.preference_rpm_key);
        rpm = sPrefs.getBoolean(prefRPMKey, true);

        String prefOilTempKey = getString(R.string.preference_oiltemp_key);
        oilTemp = sPrefs.getBoolean(prefOilTempKey, true);

        String prefCoolwaterTempKey = getString(R.string.preference_coolwatertemp_key);
        coolwaterTemp = sPrefs.getBoolean(prefCoolwaterTempKey, true);

        String prefDTCCountKey = getString(R.string.preference_dtccount_key);
        dtcCount = sPrefs.getBoolean(prefDTCCountKey, true);

        String prefFuelLevelKey = getString(R.string.preference_fuellevel_key);
        fuelLevel = sPrefs.getBoolean(prefFuelLevelKey, true);

        String prefFuelRateKey = getString(R.string.preference_fuelrate_key);
        fuelRate = sPrefs.getBoolean(prefFuelRateKey, true);

        String prefLadedruckKey = getString(R.string.preference_ladedruck_key);
        ladedruck = sPrefs.getBoolean(prefLadedruckKey, true);

        String prefThrottlePosKey = getString(R.string.preference_throttlepos_key);
        throttlePos = sPrefs.getBoolean(prefThrottlePosKey, true);
        //EEEEEEEEEEEEEEEEIIIIIIIIIIIIIIIIIINNNNNNNNNNNNNNNNNNNSSSSSSSSTTTTTEEEEELLLUUUUUUUUUUUUUUNNNNNNNNNNGGGGGGGGGGGGGEEEEEEEEEENNNNNNNNNNNNNNN

        bauTypesList();
        bauValueList(ergebnis);
        //    Log.i("Liste", Integer.toString(listTypes.size()));
        //    Log.i("Liste2", Integer.toString(listValues.size()));
        //instantiate custom adapter
        adapter = new AdapterLivedatenSummaryListe(listValues, listTypes, this);

        //handle listview and assign adapter
        ListView lView = (ListView) findViewById(R.id.list_view_summary_livedaten);
        lView.setAdapter(adapter);
        startMqtt2(this);
    }

    public void aktualisiere2(String[] daten) {// Boolean pace, Boolean rpm, Boolean oilTemp, Boolean coolwaterTemp, Boolean dtcCount, Boolean fuelLevel, Boolean fuelRate, Boolean ladedruck, Boolean throttlePos) {
        //EINSTELLUNGEN
        /*this.ergebnis= Arrays.copyOf(daten,10);
        this.pace=pace;
        this.rpm=rpm;
        this.oilTemp=oilTemp;
        this.coolwaterTemp=coolwaterTemp;
        this.dtcCount=dtcCount;
        this.fuelLevel=fuelLevel;
        this.fuelRate=fuelRate;
        this.ladedruck=ladedruck;
        this.throttlePos=throttlePos;*/
        /*SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

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
        Boolean throttlePos = sPrefs.getBoolean(prefThrottlePosKey, true);*/
        //EINSTELLUNGEN
        //WENN neue Nachricht mit aktuellen Daten kam ->
        /////////////////////////////////////////////////////////////////////////////////////////
        listValues.clear();
        listValues.add(daten[9]);
        if (pace) listValues.add(daten[0]);
        if (rpm) listValues.add(daten[1]);
        if (oilTemp) listValues.add(daten[2]);
        if (coolwaterTemp) listValues.add(daten[3]);
        if (dtcCount) listValues.add(daten[4]);
        if (fuelLevel) listValues.add(daten[5]);
        if (fuelRate) listValues.add(daten[6]);
        if (ladedruck) listValues.add(daten[7]);
        if (throttlePos) listValues.add(daten[8]);
        adapter.setNewList(listValues);
        //////////////////////////////////////////////////////////////////////////////////////////

    }

    public void bauValueList(String[] daten) {
        //WENN neue Nachricht mit aktuellen Daten kam ->
        listValues.add(daten[9]);
        if (pace) listValues.add(daten[0]);
        if (rpm) listValues.add(daten[1]);
        if (oilTemp) listValues.add(daten[2]);
        if (coolwaterTemp) listValues.add(daten[3]);
        if (dtcCount) listValues.add(daten[4]);
        if (fuelLevel) listValues.add(daten[5]);
        if (fuelRate) listValues.add(daten[6]);
        if (ladedruck) listValues.add(daten[7]);
        if (throttlePos) listValues.add(daten[8]);
    }


    public void bauTypesList() {
        listTypes.add("VIN");
        if (pace) listTypes.add("Geschwindigkeit (km/h)");
        if (rpm) listTypes.add("Motordrehzahl (rpm)");
        if (oilTemp) listTypes.add("Öltemperatur (°C)");
        if (coolwaterTemp) listTypes.add("Kühlwassertemperatur (°C)");
        if (dtcCount) listTypes.add("DTC-Anzahl");
        if (fuelLevel) listTypes.add("Kraftstoffstand (%)");
        if (fuelRate) listTypes.add("Kraftstoffverbrauch (l/h)");
        if (ladedruck) listTypes.add("Ladedruck (Bar)");
        if (throttlePos) listTypes.add("Throttle-Position (%)");
    }

    private void startMqtt2(final Activity activity) {
        SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
        mqttHelperLivedaten = new MqttHelperLivedaten(activity.getApplicationContext(), sPrefs, activity);
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
                LivedatenTabsActivity.LiveData data = gson.fromJson(gsonString, LivedatenTabsActivity.LiveData.class);
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
                    if (data.type.equals("VIN")) ergebnis[9] = data.datavalue;
                }

                aktualisiere2(ergebnis);//,  pace, rpm, oilTemp, coolwaterTemp, dtcCount, fuelLevel, fuelRate, ladedruck, throttlePos);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }
}
