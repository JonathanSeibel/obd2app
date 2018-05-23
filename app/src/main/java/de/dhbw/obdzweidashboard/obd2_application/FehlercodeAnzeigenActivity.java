package de.dhbw.obdzweidashboard.obd2_application;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by JZX8NT on 23.02.2018.
 */

public class FehlercodeAnzeigenActivity extends Activity {
    MqttHelperFehlercodes mqttHelperFehlercodes;
    ArrayList<String> list;
    String[] fehlercodeArray = new String[0];
    AdapterFehlercodesListe adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fehlercode_anzeigen);

        //generate list
        list = new ArrayList<String>();
        list.add("item1");
        list.add("item2");


        //instantiate custom adapter
        adapter = new AdapterFehlercodesListe(list, this);

        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.list_view);
        lView.setAdapter(adapter);
        initButtons();
        startMqtt(this);
    }

    private void initButtons(){
        Button button_update = (Button) findViewById(R.id.button_update);
        Button deleteAllBtn = (Button) findViewById(R.id.delete_all_btn);
        button_update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                refresh();
            }
        });
        deleteAllBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                adapter.list.clear();
                loeschanfrage();
                //Anstoßen, dass fehler auch in OBD gelöscht wird #MQTT #TODO
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void loeschanfrage() {
        MqttMessage m = new MqttMessage("Delete".getBytes());
        try {
            mqttHelperFehlercodes.mqttAndroidClient.publish("Control", m);
            Toast.makeText(this, "Alle Fehler werden gelöscht, bitte warten...", Toast.LENGTH_SHORT).show();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void refresh(){
        int n = 0;
        list.clear();
        MqttMessage m = new MqttMessage("Error".getBytes());
        try {
            mqttHelperFehlercodes.mqttAndroidClient.publish("Control", m);
            Toast.makeText(this, "Fehlerabfrage wurde wahrscheinlich gesendet...", Toast.LENGTH_SHORT).show();
        } catch (MqttException e) {
            e.printStackTrace();
        }

        while(n < 100){n++;}

        for (int i=0; i<fehlercodeArray.length; i++){
            list.add(fehlercodeArray[i]);
        }
        adapter.setNewList(list);
    }

    private void startMqtt(final Activity activity) {
        SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mqttHelperFehlercodes = new MqttHelperFehlercodes(getApplicationContext(), sPrefs, this);
        mqttHelperFehlercodes.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Toast.makeText(activity, "Connection to Pi-Fehlercodes established...", Toast.LENGTH_SHORT).show();

                MqttMessage m = new MqttMessage("Error".getBytes());
                try {
                    mqttHelperFehlercodes.mqttAndroidClient.publish("Control", m);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void connectionLost(Throwable throwable) {
                Toast.makeText(activity, "Connection to Pi-Fehlercodes lost...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                String gsonString = mqttMessage.toString();
                Gson gson = new Gson();
                ErrorData data = gson.fromJson(gsonString, ErrorData.class);
                if (data.anzahlFehler == 0) {
                    Toast.makeText(activity, "Keine Fehlercodes vorhanden!!!", Toast.LENGTH_SHORT).show();
                } else {
                    fehlercodeArray = new String[data.anzahlFehler];
                    fehlercodeArray = Arrays.copyOf(data.fehlerArray, data.anzahlFehler);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mqttHelperFehlercodes.mqttAndroidClient.isConnected()) try {
            mqttHelperFehlercodes.disconnect();
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
        initButtons();
        startMqtt(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initButtons();
        startMqtt(this);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, EinstellungenActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class ErrorData {
        public int anzahlFehler;
        public String[] fehlerArray;
    }
}
