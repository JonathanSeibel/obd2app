package de.dhbw.obdzweidashboard.obd2_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JZX8NT on 15.05.2018.
 */
public class AdapterFehlercodesListe extends BaseAdapter implements ListAdapter {
    protected ArrayList<String> list = new ArrayList<String>();
    private Context context;



    public AdapterFehlercodesListe(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setNewList(ArrayList<String> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
//        return list.get(pos).getId();
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fehlercode_list_element, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);


        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                list.remove(position); //or some other task
                //Anstoßen, dass fehler auch in OBD gelöscht wird #MQTT
                notifyDataSetChanged();
            }
        });

        return view;
    }
}