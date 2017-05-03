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

public class SchemaSpinnerAdapter extends ArrayAdapter<PrefItem> {

    private LayoutInflater inflater;

    public SchemaSpinnerAdapter(@NonNull Context context, @NonNull List<PrefItem> objects) {
        super(context, 0, objects);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PrefItem item = getItem(position);
        View view = inflater.inflate(R.layout.cell_spinner, parent, false);
        ((TextView) view.findViewById(R.id.text)).setText(item.normalizedName());
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PrefItem item = getItem(position);
        View view = inflater.inflate(R.layout.cell_spinner, parent, false);
        ((TextView) view.findViewById(R.id.text)).setText(item.normalizedName());
        return view;
    }
}
