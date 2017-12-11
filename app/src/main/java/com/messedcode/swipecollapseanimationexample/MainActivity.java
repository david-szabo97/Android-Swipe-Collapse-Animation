package com.messedcode.swipecollapseanimationexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

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
        CustomItemTouchHelperCallback helperCb = new CustomItemTouchHelperCallback();
        ItemTouchHelper helper = new ItemTouchHelper(helperCb);
        helper.attachToRecyclerView(recyclerView);
    }

}
