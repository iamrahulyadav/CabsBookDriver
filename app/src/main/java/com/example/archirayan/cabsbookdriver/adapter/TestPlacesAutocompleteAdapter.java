package com.example.archirayan.cabsbookdriver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.seatgeek.placesautocomplete.PlacesApi;
import com.seatgeek.placesautocomplete.adapter.AbstractPlacesAutocompleteAdapter;
import com.seatgeek.placesautocomplete.history.AutocompleteHistoryManager;
import com.seatgeek.placesautocomplete.model.AutocompleteResultType;
import com.seatgeek.placesautocomplete.model.Place;

/**
 * Created by archirayan on 8/11/17.
 */

public class TestPlacesAutocompleteAdapter extends AbstractPlacesAutocompleteAdapter {

    public TestPlacesAutocompleteAdapter(final Context context, final PlacesApi api, final AutocompleteResultType resultType, AutocompleteHistoryManager history) {
        super(context, api, resultType, history);
    }



    @Override
    protected View newView(final ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.places_autocomplete_item,parent,false);
    }

    @Override
    protected void bindView(final View view, final Place item) {
        TextView txt_item = (TextView) view.findViewById(R.id.txt_item);
        txt_item.setText(item.description);
    }
}
