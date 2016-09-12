package com.bwidlarz.todolist.RecycleView;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwidlarz.todolist.R;

/**
 * Created by skyfishjy on 10/31/14.
 */
public class MyListCursorAdapter extends CursorRecyclerViewAdapter<MyListCursorAdapter.ViewHolder> {

    public MyListCursorAdapter(Context context,Cursor cursor){
        super(context,cursor);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_complex,parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        MyListItem myListItem = null;
        myListItem = MyListItem.fromCursor(cursor);

        TextView titleView = (TextView)viewHolder.cardView.findViewById(R.id.title_info);
        TextView descView = (TextView)viewHolder.cardView.findViewById(R.id.description_info);
        TextView createdView = (TextView) viewHolder.cardView.findViewById(R.id.created_info);

        titleView.setText(myListItem.getTitle());
        descView.setText(myListItem.getDesc());
        createdView.setText(myListItem.getCreated());
    }
}