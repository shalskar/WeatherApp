package com.vincenttetau.weatherapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

// todo rename
public abstract class OnScrollStopListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager linearLayoutManager;

    private boolean active;

    public OnScrollStopListener(@NonNull LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (active)
            onScrollStopped(findCentreMostPosition());
    }

    private int findCentreMostPosition() {
        int firstPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        int diff = lastPosition - firstPosition;
        return Math.max(firstPosition + (diff / 2), 0);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public abstract void onScrollStopped(int centreMostPosition);
}
