package de.dhbw.obdzweidashboard.obd2_application;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by JZX8NT on 30.04.2018.
 */

public class TabLadeDruckFragment extends Fragment {
    private static final String TAG = "TabLadeDruckFragment";
    TextView wertText4;
    boolean viewCreated = false;
    private String speed;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.livedaten_tab_ladedruck, container, false);
        return view;
    }

    public TextView getWertText(){
        return wertText4;
    }

    public boolean ready() {
        return viewCreated;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        wertText4 = (TextView) getView().findViewById(R.id.textViewLadeDruck);
        wertText4.setText(speed);

    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
        if(wertText4 != null) {
            wertText4.setText(speed);
        }
    }
}
