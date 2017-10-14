package com.vincenttetau.weatherapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class OnScrollListenerAdapter extends RecyclerView.OnScrollListener {

    private LinearLayoutManager linearLayoutManager;

    private boolean active = true;

    private int lastCentrePosition = -1;

    public OnScrollListenerAdapter(@NonNull LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (active) {
            int centreMostPosition = findCentreMostPosition();
            if (centreMostPosition != lastCentrePosition) {
                lastCentrePosition = centreMostPosition;
                onScrolled(findCentreMostPosition());
            }
        }
    }

    private int findCentreMostPosition() {
        int firstPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        int diff = lastPosition - firstPosition;
        return Math.max(firstPosition + (diff / 2), 0);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public abstract void onScrolled(int centreMostPosition);
}
