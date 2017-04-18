package com.github.kazy1991.prefeditor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SchemaSpinnerAdapter extends ArrayAdapter<NavigationItem> {

    private LayoutInflater inflater;

    public SchemaSpinnerAdapter(@NonNull Context context, @NonNull List<NavigationItem> objects) {
        super(context, 0, objects);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NavigationItem item = getItem(position);
        View view = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        ((TextView) view).setText(item.normalizedName());
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NavigationItem item = getItem(position);
        View view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        ((TextView) view).setText(item.normalizedName());
        return view;
    }
}
