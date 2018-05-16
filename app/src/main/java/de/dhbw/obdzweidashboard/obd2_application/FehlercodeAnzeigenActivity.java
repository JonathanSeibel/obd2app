package de.dhbw.obdzweidashboard.obd2_application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import java.util.ArrayList;

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
        startMqtt();
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

                //Anstoßen, dass fehler auch in OBD gelöscht wird #MQTT #TODO
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void refresh(){
        list.clear();
    //    fehlercodeArray = new String[0];
        startMqtt();
        int n=0;

        while(n < 100){n++;}

        for (int i=0; i<fehlercodeArray.length; i++){
            list.add(fehlercodeArray[i]);
        }
        adapter.setNewList(list);
    }
    private void startMqtt() {
        mqttHelperFehlercodes = new MqttHelperFehlercodes(getApplicationContext());
        mqttHelperFehlercodes.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                JSONObject jsnonO = new JSONObject();
//                jsnonO = JSONObject.(JSONObject)mqttMessage.toString();
                //IRGENDWAS MIT DER MESSAGE MACHEN
                fehlercodeArray = new String[40];
                for (int i=0; i<fehlercodeArray.length; i++){
                    fehlercodeArray[i]=mqttMessage.toString()+i;
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
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initButtons();
        startMqtt();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initButtons();
        startMqtt();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, EinstellungenActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
