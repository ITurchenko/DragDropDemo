package com.iturchenko.dragdropdemo.gui;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.iturchenko.dragdropdemo.R;
import com.iturchenko.dragdropdemo.data.DataElement;
import com.iturchenko.dragdropdemo.data.DataProvider;

class DragDropDataAdapter extends RecyclerView.Adapter<ItemViewHolder> implements DraggableItemAdapter<ItemViewHolder> {
    private DataProvider dataProvider;

    public DragDropDataAdapter(DataProvider dataProvider) {
        this.dataProvider = dataProvider;

        setHasStableIds(true);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.element_list, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        DataElement dataElement = dataProvider.get(position);
        holder.bind(dataElement);
    }

    @Override
    public int getItemCount() {
        return dataProvider.getItemCount();
    }

    @Override
    public long getItemId(int position) {
        return dataProvider.getItemId(position);
    }

    @Override
    public boolean onCheckCanStartDrag(ItemViewHolder holder, int position, int x, int y) {
        return true;
    }

    @Override
    public ItemDraggableRange onGetItemDraggableRange(ItemViewHolder holder, int position) {
        return null;
    }

    @Override
    public void onMoveItem(int fromPosition, int toPosition) {
        if (fromPosition == toPosition) return;

        dataProvider.moveItem(fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public boolean onCheckCanDrop(int draggingPosition, int dropPosition) {
        return true;
    }
}