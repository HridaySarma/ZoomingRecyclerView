package com.main.zoomingrecyclerview;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.PointF;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.renderscript.ScriptIntrinsic;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Range;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Optional;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public final class ZoomingRecyclerView extends LinearLayoutManager {

    private float mShrinkAmount = 0.3f;
    private float mShrinkDistance = 0.6f;

    public ZoomingRecyclerView(Context context){
        super(context);

    }

    public ZoomingRecyclerView(Context context,float shrinkAmount,float shrinkDistance) {
        super(context);
        this.mShrinkDistance = shrinkDistance;
        this.mShrinkAmount = shrinkAmount;
    }


    public ZoomingRecyclerView(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int orientation = this.getOrientation();
        if (orientation == 1) {
            int scrolled = super.scrollVerticallyBy(dy, recycler, state);
            float midpoint = (float)this.getHeight() / 2.0F;
            float d0 = 0.0F;
            float d1 = mShrinkDistance * midpoint;
            float s0 = 1.0F;
            float s1 = 1.0F - mShrinkAmount;
            int i = 0;

            for(int var12 = this.getChildCount(); i < var12; ++i) {
                View child = this.getChildAt(i);
                float childMidpoint = (float)(this.getDecoratedBottom(child) + this.getDecoratedTop(child)) / 2.0F;
                float d = Math.min(d1,Math.abs(midpoint- childMidpoint));
                float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
                child.setScaleX(scale);
                child.setScaleY(scale);
            }

            return scrolled;
        } else {
            return 0;
        }
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
            int scrolled = super.scrollHorizontallyBy(dx, recycler, state);
            float midpoint = getWidth() / 2.0f;
            float d0 = 0.f;
            float d1 = this.mShrinkDistance * midpoint;
            float s0 = 1.f;
            float s1 = 1.f - this.mShrinkAmount;
            int i = 0;

            for(int var12 = getChildCount(); i < var12; ++i) {
                View child = getChildAt(i);

                assert child != null;
                float childMidpoint = (getDecoratedRight(child) + getDecoratedLeft(child)) / 2.0f;

                float d = Math.min(d1,Math.abs(midpoint- childMidpoint));

                float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
                child.setScaleX(scale);
                child.setScaleY(scale);
            }
            return scrolled;

    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        scrollHorizontallyBy(0,recycler,state);
    }

    @Override
    public void offsetChildrenHorizontal(int dx) {
        super.offsetChildrenHorizontal(dx);
    }
}