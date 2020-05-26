package com.main.sampleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.main.zoomingrecyclerview.ZoomingRecyclerView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private RecyclerView listView;
    private LinearLayoutManager linearLayoutManager = new ZoomingRecyclerView(this, (float) 0.3, (float) 0.6);
    private  Adapter  horizontalAdapter;
    private SnapHelper snapHelper = new  LinearSnapHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        horizontalAdapter = new Adapter(fillWithData(), getApplication());


        listView = findViewById(R.id.listView);
        listView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        listView.setAdapter(horizontalAdapter);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        listView.smoothScrollToPosition(0);
        snapHelper.attachToRecyclerView(listView);
        listView.setNestedScrollingEnabled(false);

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            boolean flag = true;
            @Override
            public void run() {
                if(count <= horizontalAdapter.getItemCount()){
                    if(count==horizontalAdapter.getItemCount()-1){
                        flag=false;
                    }else if(count == 0){
                        flag = true;
                    }
                    if(flag) count++;
                    else count=0;

                    listView.smoothScrollToPosition(count);
                    handler.postDelayed(this,2000);
                }
            }
        };
        handler.postDelayed(runnable,2000);
    }

    private ArrayList<model> fillWithData() {

        ArrayList<model> imageModelArrayList = new ArrayList<>();

        model model1 = new model("Oblivion",R.drawable.oblivion);
        imageModelArrayList.add(model1);

        model model2 = new model("Wolverine", R.drawable.wolverine);
        imageModelArrayList.add(model2);

        model model3 = new model("The Dark Knight", R.drawable.thedarkknight);
        imageModelArrayList.add(model3);

        model model4 = new model("Deadpool",R.drawable.deadpool);
        imageModelArrayList.add(model4);

        return imageModelArrayList;

    }
}
