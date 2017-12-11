package com.messedcode.swipecollapseanimationexample;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public class CustomItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final OnSwipeListener onSwipeListener;

    private final int leftBackgroundColor;
    private final Bitmap leftIcon;
    private final int rightBackgroundColor;
    private final Bitmap rightIcon;

    private final float iconSize;

    private final Paint paint = new Paint();

    private CustomItemTouchHelperCallback(Builder b) {
        this.onSwipeListener = b.onSwipeListener;

        this.leftBackgroundColor = b.leftBackgroundColor;
        this.leftIcon = b.leftIcon;
        this.rightBackgroundColor = b.rightBackgroundColor;
        this.rightIcon = b.rightIcon;

        this.iconSize = b.iconSize;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.START | ItemTouchHelper.END);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (onSwipeListener != null) {
            if (direction == ItemTouchHelper.START) {
                onSwipeListener.onSwipeLeft(viewHolder);
            } else if (direction == ItemTouchHelper.END) {
                onSwipeListener.onSwipeRight(viewHolder);
            }
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            View itemView = viewHolder.itemView;

            float width = itemView.getWidth();
            float height = itemView.getHeight();

            if (height > 0) {
                float left = itemView.getLeft();
                float top = itemView.getTop();
                float right = itemView.getRight();
                float bottom = itemView.getBottom();

                float centerY = (top + bottom) / 2;

                float margin = width * 0.025f;

                if (dX > 0) {
                    paint.setColor(leftBackgroundColor);
                    RectF background = new RectF(left, top, dX, bottom);
                    c.drawRect(background, paint);

                    float iconLeft = left + margin;
                    RectF iconRect = new RectF(iconLeft, centerY - iconSize / 2, iconLeft + iconSize, centerY + iconSize / 2);
                    c.drawBitmap(leftIcon, null, iconRect, paint);
                } else {
                    paint.setColor(rightBackgroundColor);
                    RectF background = new RectF(right + dX, top, right, bottom);
                    c.drawRect(background, paint);

                    float iconRight = right - margin;
                    RectF iconRect = new RectF(iconRight - iconSize, centerY - iconSize / 2, iconRight, centerY + iconSize / 2);
                    c.drawBitmap(rightIcon, null, iconRect, paint);
                }
            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public interface OnSwipeListener {

        void onSwipeRight(RecyclerView.ViewHolder vh);

        void onSwipeLeft(RecyclerView.ViewHolder vh);

    }

    public static class Builder {

        private OnSwipeListener onSwipeListener;

        private int leftBackgroundColor;
        private Bitmap leftIcon;
        private int rightBackgroundColor;
        private Bitmap rightIcon;

        private float iconSize;

        public Builder leftBackgroundColor(int leftBackgroundColor) {
            this.leftBackgroundColor = leftBackgroundColor;
            return this;
        }

        public Builder leftIcon(Bitmap leftIcon) {
            this.leftIcon = leftIcon;
            return this;
        }

        public Builder rightBackgroundColor(int rightBackgroundColor) {
            this.rightBackgroundColor = rightBackgroundColor;
            return this;
        }

        public Builder rightIcon(Bitmap rightIcon) {
            this.rightIcon = rightIcon;
            return this;
        }

        public Builder iconSize(float iconSize) {
            this.iconSize = iconSize;
            return this;
        }

        public Builder onSwipeListener(OnSwipeListener onSwipeLister) {
            this.onSwipeListener = onSwipeLister;
            return this;
        }

        public CustomItemTouchHelperCallback build() {
            return new CustomItemTouchHelperCallback(this);
        }

    }

}