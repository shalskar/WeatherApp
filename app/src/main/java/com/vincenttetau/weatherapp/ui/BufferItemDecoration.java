package com.vincenttetau.weatherapp.ui;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BufferItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int bufferSpacing = (parent.getWidth() / 2) - (view.getWidth() / 2);
        if (position == parent.getAdapter().getItemCount() - 1) {
            outRect.right = bufferSpacing;
        } else if (position == 0) {
            outRect.left = bufferSpacing;
        }
    }
}
