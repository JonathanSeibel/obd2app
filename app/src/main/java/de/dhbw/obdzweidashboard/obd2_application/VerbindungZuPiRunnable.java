/*
package de.dhbw.obdzweidashboard.obd2_application;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import static java.lang.Thread.sleep;

*/
/**
 * Created by JZX8NT on 02.05.2018.
 *//*


public class VerbindungZuPiRunnable extends AsyncTask {
    String[] ergebnis = new String[6];
    int counter = 0;
    LivedatenTabsActivity zielActivity;
    MqttHelper mqttHelper;

    //private Tab1Fragment tab1Fragment;
//    Handler handler;

    public VerbindungZuPiRunnable(LivedatenTabsActivity zielActivity) {//, Handler handler) {
        this.zielActivity = zielActivity;
        // this.handler = handler;
        //Object[] objects = new Object[1];
        //this.doInBackground(objects);
        //this.tab1Fragment = tab1Fragment;
        //this.threadRunning = threadRunning;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        //hier wird die ganze kacke vom Pi empfangen und in ergebnis gespeichert
        //Bau Connection auf

        //wait for data
//DATA_DUMMY_START
       while (!isCancelled()) {

               ergebnis[0] = Integer.toString(counter);
               ergebnis[1] = Integer.toString(counter*100);
               ergebnis[2] = Integer.toString(counter+40);
               ergebnis[3] = Integer.toString(counter+10);
               ergebnis[4] = Integer.toString(counter+15);
               ergebnis[5] = Double.toString(Math.round((counter*0.2)*100)/100.0);

               counter++;
//DATA_DUMMY_END

//MQTT



//MQTTEND
           new Handler(Looper.getMainLooper()).post(new Runnable() {
               @Override
               public void run() {
                   zielActivity.aktualisiere(ergebnis);
               }
           });

           try {
               sleep(100);
           } catch (Exception ex) {}

           //EVENT GENErieren, damit sich LivedatenTablsActivity seinen Kram (ergebnis[]) abholen kann...
           //
       }

        return null;
    }

   */
/* private void startMqtt() {
        mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug", mqttMessage.toString());
                ergebnis[0] = mqttMessage.toString();
                ergebnis[1] = mqttMessage.toString();
                ergebnis[2] = mqttMessage.toString();
                ergebnis[3] = mqttMessage.toString();
                ergebnis[4] = mqttMessage.toString();
                ergebnis[5] = mqttMessage.toString();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }*//*



}
*/
