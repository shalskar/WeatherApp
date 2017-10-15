package com.vincenttetau.weatherapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.vincenttetau.weatherapp.R;
import com.vincenttetau.weatherapp.models.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationSuggestionsAdapter extends ArrayAdapter<Location> {

    private List<Location> locations;

    private LocationsSuggestionsFilter filter;

    public LocationSuggestionsAdapter(@NonNull Context context, @NonNull List<Location> locations) {
        super(context, R.layout.layout_location_suggestion, new ArrayList<Location>());
        this.locations = locations;
        this.filter = new LocationsSuggestionsFilter();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    private class LocationsSuggestionsFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint != null) {
                List<Location> temporaryList = filterLocations(constraint);
                filterResults.values = temporaryList;
                filterResults.count = temporaryList.size();
            }

            return filterResults;
        }

        private List<Location> filterLocations(@NonNull CharSequence constraint) {
            ArrayList<Location> filteredList = new ArrayList<>();
            for (Location location : locations) {
                if (isValid(location, constraint)) {
                    filteredList.add(location);
                }
            }
            return filteredList;
        }

        private boolean isValid(@NonNull Location location, @NonNull CharSequence constraint) {
            return location.getName().toLowerCase().startsWith(constraint.toString().toLowerCase());
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List<Location>) results.values);

            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
