package com.messedcode.swipecollapseanimationexample;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Adapter
        final CustomAdapter adapter = new CustomAdapter(new String[]{"Bread", "Egg", "Salmon", "Bacon", "Apple"});
        recyclerView.setAdapter(adapter);

        // Layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Add swipe
        int red = Color.parseColor("#F44336");
        int green = Color.parseColor("#4CAF50");

        Drawable deleteIconWhite = getResources().getDrawable(R.drawable.ic_delete_black_24dp).mutate();
        deleteIconWhite.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        Drawable checkIconWhite = getResources().getDrawable(R.drawable.ic_check_black_24dp).mutate();
        checkIconWhite.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        float iconSizeInDp = 24;
        CustomItemTouchHelperCallback helperCb = new CustomItemTouchHelperCallback.Builder()
                .iconSize(dpToPx(iconSizeInDp))
                .leftBackgroundColor(red)
                .leftIcon(convertDrawableToBitmap(deleteIconWhite))
                .rightBackgroundColor(green)
                .rightIcon(convertDrawableToBitmap(checkIconWhite))
                .onSwipeListener(new CustomItemTouchHelperCallback.OnSwipeListener() {
                    @Override
                    public void onSwipeRight(RecyclerView.ViewHolder vh) {
                        int position = vh.getAdapterPosition();
                        Item item = adapter.getItemAt(position);
                        item.status = Item.Status.CHECKED;
                        adapter.notifyItemChanged(position);
                    }

                    @Override
                    public void onSwipeLeft(RecyclerView.ViewHolder vh) {
                        int position = vh.getAdapterPosition();
                        Item item = adapter.getItemAt(position);
                        item.status = Item.Status.DELETED;
                        adapter.notifyItemChanged(position);
                    }
                })
                .build();
        ItemTouchHelper helper = new ItemTouchHelper(helperCb);
        helper.attachToRecyclerView(recyclerView);

        // Add animation
        CustomItemAnimator animator = new CustomItemAnimator(new CustomItemAnimator.onAnimationEndListener() {
            @Override
            public void onChangeEnd(RecyclerView.ViewHolder newHolder) {
                adapter.remove(newHolder.getAdapterPosition());
            }
        });
        recyclerView.setItemAnimator(animator);
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private Bitmap convertDrawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

}
