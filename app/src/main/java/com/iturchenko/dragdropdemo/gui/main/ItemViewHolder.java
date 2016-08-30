package com.iturchenko.dragdropdemo.gui.main;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;
import com.iturchenko.dragdropdemo.R;
import com.iturchenko.dragdropdemo.data.model.DataElement;

class ItemViewHolder extends AbstractDraggableItemViewHolder {
    private final TextView tvContent;
    private AsyncTask asyncTask;

    public ItemViewHolder(View itemView) {
        super(itemView);

        tvContent = (TextView) itemView.findViewById(R.id.tv_content);
    }

    public void bind(DataElement dataElement) {
        tvContent.setText(dataElement != null ? dataElement.value : "...");
    }

    public void setAsyncTask(AsyncTask asyncTask) {
        if (this.asyncTask != null) { // we are currently load something, so cancel it
            this.asyncTask.cancel(true);
        }

        this.asyncTask = asyncTask;
    }
}
