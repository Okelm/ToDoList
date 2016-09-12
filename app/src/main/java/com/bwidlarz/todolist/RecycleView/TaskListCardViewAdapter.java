package com.bwidlarz.todolist.RecycleView;

import android.content.Context;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bwidlarz.todolist.R;

/**
 * Created by Dell on 2016-07-09.
 */
public class TaskListCardViewAdapter extends RecyclerView.Adapter<TaskListCardViewAdapter.ViewHolder> {

    private String[] titles;
    private Long[] timeEnds;

    private CursorAdapter mCursorAdapter;
    private Context mContext;
    private ViewHolder holder;

    public TaskListCardViewAdapter(String[] titles, Long[] timeEnds) {
        this.titles = titles;
        this.timeEnds = timeEnds;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @Override
    public TaskListCardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view,parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(TaskListCardViewAdapter.ViewHolder holder, int position) {

}

    @Override
    public int getItemCount() {
        return titles.length;
    }


}
