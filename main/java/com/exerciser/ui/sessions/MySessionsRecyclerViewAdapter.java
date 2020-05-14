package com.exerciser.ui.sessions;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exerciser.R;
import com.exerciser.ui.sessions.SessionsFragment.OnListFragmentInteractionListener;
import com.exerciser.ui.sessions.session.SessionContent.Session;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Session} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MySessionsRecyclerViewAdapter extends RecyclerView.Adapter<MySessionsRecyclerViewAdapter.ViewHolder> {

    private final List<Session> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MySessionsRecyclerViewAdapter(List<Session> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_sessions, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.dayLabel.setText("Day " + Integer.toString(position + 1));

        String name = holder.mItem.exerciseCount + " exercises";
        holder.programName.setText(name);

        String time = new SimpleDateFormat("mm:ss").format(new Date(((long) holder.mItem.seconds) * 1000));
        String description = "Total Time: " + time;
        holder.programDescription.setText(description);
        holder.cardLayout.setBackgroundResource(R.drawable.bg_session_card);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public Session mItem;

        CardView card_view;
        TextView programName;
        TextView programDescription;
        TextView dayLabel;
        RelativeLayout cardLayout;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            card_view = (CardView) view.findViewById(R.id.card_view);
            programName = (TextView)itemView.findViewById(R.id.program_name);
            programDescription = (TextView)itemView.findViewById(R.id.program_description);
            dayLabel = (TextView)itemView.findViewById(R.id.day_label);
            cardLayout = (RelativeLayout) view.findViewById(R.id.card_layout);

            /*
            final Button button = itemView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    button.setBackgroundColor(Color.RED);

                    if (null != mListener) {
                        mListener.onListFragmentInteraction(mItem);
                    }
                }
            });
             */
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
