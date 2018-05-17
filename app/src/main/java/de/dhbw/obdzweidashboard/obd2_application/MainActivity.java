package de.dhbw.obdzweidashboard.obd2_application;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import static android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements OnClickListener{
    MediaPlayer carSoundMediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carSoundMediaPlayer = MediaPlayer.create(this, R.raw.carstartgaragemp3);
        initButtons();
    }
    private void initButtons(){
        initFehlercodesAnzeigenButton();
        initLivedatenAnzeigenButton();
        initLogoButton();
    }
    private void initFehlercodesAnzeigenButton(){
        Button button_fehlercodes_anzeigen = (Button) findViewById(R.id.button_fehlercodes_anzeigen);
        button_fehlercodes_anzeigen.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), FehlercodeAnzeigenActivity.class));
            }
        });
    }
    private void initLivedatenAnzeigenButton(){
        Button button_livedaten_anzeigen = (Button) findViewById(R.id.button_livedaten_anzeigen);
        button_livedaten_anzeigen.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), LivedatenTabsActivity.class));
            }
        });
    }
    private void initLogoButton(){
        ImageButton imgbutton_logo = (ImageButton) findViewById(R.id.imageButton_logo);
        imgbutton_logo.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                carSoundMediaPlayer.start();
            }
        });
    }
    @Override
    public void onClick(View view) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_livedaten_tabs, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, EinstellungenActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
// startActivity(new Intent(this, ...Activity.class));