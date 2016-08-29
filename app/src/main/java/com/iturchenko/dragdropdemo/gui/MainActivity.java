package com.iturchenko.dragdropdemo.gui;

import android.graphics.drawable.NinePatchDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.ItemShadowDecorator;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.iturchenko.dragdropdemo.R;
import com.iturchenko.dragdropdemo.data.DataProvider;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataProvider dataProvider = new DataProvider(this);

        initRecyclerView(dataProvider);
    }

    private void initRecyclerView(DataProvider dataProvider) {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);

        // drag & drop manager
        RecyclerViewDragDropManager mRecyclerViewDragDropManager = new RecyclerViewDragDropManager();
        // Start dragging after long press
        mRecyclerViewDragDropManager.setInitiateOnLongPress(true);
        mRecyclerViewDragDropManager.setInitiateOnMove(false);

        //adapter
        final DragDropDataAdapter myItemAdapter = new DragDropDataAdapter(dataProvider);

        RecyclerView.Adapter mWrappedAdapter = mRecyclerViewDragDropManager.createWrappedAdapter(myItemAdapter);

        final GeneralItemAnimator animator = new RefactoredDefaultItemAnimator();

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mWrappedAdapter);  // requires *wrapped* adapter
        mRecyclerView.setItemAnimator(animator);

        mRecyclerViewDragDropManager.attachRecyclerView(mRecyclerView);
    }
}
