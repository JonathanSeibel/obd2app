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

public class TabCoolwaterTempFragment extends Fragment {
    private static final String TAG = "TabCoolwaterTempFragment";
    TextView wertText4;
    boolean viewCreated = false;
    private String tempe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.livedaten_tab_coolwater_temp, container, false);
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
        wertText4 = (TextView) getView().findViewById(R.id.textViewCoolwaterTemp);
        wertText4.setText(tempe);

    }

    public String getSpeed() {
        return tempe;
    }

    public void setSpeed(String tempe) {
        this.tempe = tempe;
        if(wertText4 != null) {
            wertText4.setText(tempe);
        }
    }
}
