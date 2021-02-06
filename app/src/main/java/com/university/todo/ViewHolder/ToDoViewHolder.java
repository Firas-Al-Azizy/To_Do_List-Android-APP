package com.university.todo.ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.university.todo.R;

public class ToDoViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

    public TextView text_task,text_priority;

    public ToDoViewHolder(@NonNull View itemView) {
        super(itemView);
        text_task=itemView.findViewById(R.id.text_task);
        text_priority=itemView.findViewById(R.id.text_priority);

        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select menu");
        contextMenu.add(0,0,getAdapterPosition(),"Update");
        contextMenu.add(0,1,getAdapterPosition(),"Delete");

    }
}
