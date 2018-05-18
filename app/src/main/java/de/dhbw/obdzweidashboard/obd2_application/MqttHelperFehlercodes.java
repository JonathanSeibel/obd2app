package de.dhbw.obdzweidashboard.obd2_application;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by JZX8NT on 11.05.2018.
 */

public class MqttHelperFehlercodes {
    final String clientId = "OBD2AndroidAppFehlercodes";
    final String subscriptionTopic = "Fehler";
    public MqttAndroidClient mqttAndroidClient;
    public String serverUri;
    public String username = "tiqhoouh";
    public String password = "9hhO2nOCJoGp";
    SharedPreferences sPrefs;
    String prefIpAddressKey;
    String prefIpAddressDefault;
    String ipadress;

    public MqttHelperFehlercodes(Context context, SharedPreferences sprefs, Activity activity) {
        SharedPreferences sPrefs = sprefs;
        prefIpAddressKey = activity.getString(R.string.preference_mqtt_ipaddress_key);
        prefIpAddressDefault = activity.getString(R.string.preference_mqtt_ipaddress_default);
        ipadress = sPrefs.getString(prefIpAddressKey, prefIpAddressDefault);
        serverUri = ipadress;
        //serverUri = "tcp://m23.cloudmqtt.com:19114";
        mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w("mqtt", s);
            }

            @Override
            public void connectionLost(Throwable throwable) {


            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Mqtt", mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
        connect();
    }

    public void setCallback(MqttCallbackExtended callback) {
        mqttAndroidClient.setCallback(callback);
    }

    public void connect() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());

        try {

            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Failed to connect to: " + serverUri + exception.toString());
                }
            });


        } catch (MqttException ex){
            ex.printStackTrace();
        }
    }

    public void disconnect() {
        if (mqttAndroidClient.isConnected()) try {
            mqttAndroidClient.unsubscribe(subscriptionTopic);
            mqttAndroidClient.disconnect();
            //mqttAndroidClient.close();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void subscribeToTopic() {
        try {
            mqttAndroidClient.subscribe(subscriptionTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w("Mqtt","Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Subscribed fail!");
                }
            });

        } catch (MqttException ex) {
            System.err.println("Exceptionst subscribing");
            ex.printStackTrace();
        }
    }
    private void loginByTopic(String topic, MqttMessage message){
        try {
            mqttAndroidClient.publish(topic, message);
        } catch (MqttException e) {
            System.err.println("Exception while publishing");
            e.printStackTrace();
        }
    }
}
