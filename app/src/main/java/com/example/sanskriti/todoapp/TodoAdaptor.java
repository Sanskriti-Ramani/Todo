package com.example.sanskriti.todoapp;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static java.lang.Boolean.FALSE;

public class TodoAdaptor extends ArrayAdapter {
    ArrayList<Todo> items;
    LayoutInflater inflator;

    public TodoAdaptor(@NonNull Context context, ArrayList<Todo> items) {
        super(context, 0, items);

        inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
    }

    public int getCount(){
        return items.size();
    }


    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View output = convertView;
        if(output == null){
            output = inflator.inflate(R.layout.row_layout, parent, FALSE);
            TextView nameTextView = output.findViewById(R.id.nameTextView);
           // TextView itemTextView = output.findViewById(R.id.itemTextView);
            //TextView dateTextView = output.findViewById(R.id.dateTextView);
            TodoViewHolder viewHolder = new TodoViewHolder();
            viewHolder.name = nameTextView;
          //  viewHolder.item = itemTextView;
           // viewHolder.date = dateTextView;

            output.setTag(viewHolder);

        }
        TodoViewHolder viewHolder = (TodoViewHolder)output.getTag();
//        View output = inflator.inflate(R.layout.row_layout, parent, FALSE);
//        TextView nameTextView = output.findViewById(R.id.tv1);
//        TextView amountTextView = output.findViewById(R.id.tv2);
        Todo todo = items.get(position);
        viewHolder.name.setText(todo.getName());
       // viewHolder.item.setText(todo.getItem_no()+ " ");
        //viewHolder.date.setText(todo.getDue_date());

        return output;
    }
}
