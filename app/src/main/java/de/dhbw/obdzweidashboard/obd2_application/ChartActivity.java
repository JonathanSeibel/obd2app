package de.dhbw.obdzweidashboard.obd2_application;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChartActivity extends Activity {
    TextView resultArea;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultArea = new TextView(this);
        resultArea.setText("Please wait.");
        setContentView(resultArea);
        new FetchSQL().execute();
    }

    private class FetchSQL extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String retval = "";
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                retval = e.toString();
            }
            String url = "jdbc:postgresql://10.3.141.1/obddb?user=appclient&password=clientpw1";
            Connection conn;
            try {
                DriverManager.setLoginTimeout(5);
                conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                String sql;
                sql = "SELECT * FROM marvinmeinhardisteinuhrensohn";
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    retval = rs.getString(1);
                }
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                retval = e.toString();
            }
            return retval;
        }

        @Override
        protected void onPostExecute(String value) {
            resultArea.setText(value);
        }
    }
}