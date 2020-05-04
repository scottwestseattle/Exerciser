package com.exerciser.ui.sessions;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exerciser.R;
import com.exerciser.ui.sessions.SessionsFragment.OnListFragmentInteractionListener;
import com.exerciser.ui.sessions.session.SessionContent.Session;

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
        holder.programName.setText(mValues.get(position).name);
        holder.programDescription.setText(mValues.get(position).description);

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
        //orig: public final TextView mIdView;
        //Orig: public final TextView mContentView;
        public Session mItem;

        CardView card_view;
        TextView programName;
        TextView programDescription;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            //orig: mIdView = (TextView) view.findViewById(R.id.item_number);
            //orig: mContentView = (TextView) view.findViewById(R.id.content);

            card_view = (CardView) view.findViewById(R.id.card_view);
            programName = (TextView)itemView.findViewById(R.id.program_name);
            programDescription = (TextView)itemView.findViewById(R.id.program_description);

        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
