package de.dhbw.obdzweidashboard.obd2_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JZX8NT on 15.05.2018.
 */
public class AdapterLivedatenSummaryListe extends BaseAdapter implements ListAdapter {
    protected ArrayList<String> listValues = new ArrayList<String>();
    protected ArrayList<String> listTypes = new ArrayList<String>();
    private Context context;


    public AdapterLivedatenSummaryListe(ArrayList<String> listValues, ArrayList<String> listTypes, Context context) {
        this.listValues = listValues;
        this.listTypes = listTypes;
        this.context = context;
    }

    public void setNewList(ArrayList<String> listValues, ArrayList<String> listTypes) {
        this.listValues = listValues;
        this.listTypes = listTypes;
        notifyDataSetChanged();
    }

    public void setNewList(ArrayList<String> listValues) {
        this.listValues = listValues;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listValues.size();
    }

    @Override
    public Object getItem(int pos) {
        return listValues.get(pos);
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
            view = inflater.inflate(R.layout.summary_list_element, null);
        }

        //Handle TextView and display string from your list
        TextView listItemTextValue = (TextView) view.findViewById(R.id.list_view_summary_livedaten);
        TextView listItemTextType = (TextView) view.findViewById(R.id.textViewSummary_list_item);

        listItemTextValue.setText(listValues.get(position));
        listItemTextType.setText(listTypes.get(position));


        return view;
    }
}