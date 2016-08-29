package com.iturchenko.dragdropdemo.gui;

import android.view.View;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;
import com.iturchenko.dragdropdemo.R;
import com.iturchenko.dragdropdemo.data.DataElement;

class ItemViewHolder extends AbstractDraggableItemViewHolder {
    private final TextView tvContent;

    public ItemViewHolder(View itemView) {
        super(itemView);

        tvContent = (TextView) itemView.findViewById(R.id.tv_content);
    }

    public void bind(DataElement dataElement) {
        tvContent.setText(dataElement.value);
    }
}
