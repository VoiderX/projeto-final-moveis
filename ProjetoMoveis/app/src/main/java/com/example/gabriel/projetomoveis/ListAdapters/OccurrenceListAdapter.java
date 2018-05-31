package com.example.gabriel.projetomoveis.ListAdapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gabriel.projetomoveis.R;


import java.util.ArrayList;

import Utils.ConverterUtils;
import objects.Occurrence;

public class OccurrenceListAdapter extends BaseAdapter {
    Context context;
    private ArrayList<Occurrence> occurrences;
    private ArrayList<Integer> selectedOccurrences;


    private class LayoutItems {
        TextView occurrenceTitle;
        TextView occurrenceDate;
    }

    private static LayoutInflater inflater = null;

    public OccurrenceListAdapter(Context context, ArrayList<Occurrence> occurrences) {
        this.context = context;
        this.occurrences = occurrences;
        selectedOccurrences = new ArrayList<>();
        inflater = (LayoutInflater) (context.getSystemService(context.LAYOUT_INFLATER_SERVICE));
    }

    @Override
    public int getCount() {
        return occurrences.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_occcurrence_list_item, null);
        }
        LayoutItems li = new LayoutItems();
        li.occurrenceTitle = convertView.findViewById(R.id.occurrenceTitleListItem);
        li.occurrenceDate = convertView.findViewById(R.id.occurrenceDateListItem);

        li.occurrenceTitle.setText(occurrences.get(position).getTitle());
        li.occurrenceDate.setText(ConverterUtils.convertDateToString(occurrences.get(position).getDate()));

        changeBackGroundColor(position, convertView);

        return convertView;
    }

    private void changeBackGroundColor(int position, View convertView) {
        if (selectedOccurrences.contains(position)) {
            convertView.setBackgroundColor(Color.LTGRAY);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void toggleItemSelection(int position) {
        if (selectedOccurrences.contains(position)) {
            selectedOccurrences.remove(new Integer(position));
        } else {
            selectedOccurrences.add(position);
        }
    }

    public ArrayList<Integer> getSelectedItems() {
        return selectedOccurrences;
    }

    public void clearSelection() {
        selectedOccurrences.clear();
    }
}
