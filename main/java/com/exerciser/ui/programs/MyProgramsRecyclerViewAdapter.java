package com.exerciser.ui.programs;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.exerciser.R;
import com.exerciser.ui.programs.ProgramsFragment.OnListFragmentInteractionListener;
import com.exerciser.ui.programs.program.ProgramContent.ProgramItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ProgramItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyProgramsRecyclerViewAdapter extends RecyclerView.Adapter<MyProgramsRecyclerViewAdapter.ViewHolder> {

    private final List<ProgramItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyProgramsRecyclerViewAdapter(List<ProgramItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_programs, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //sbw holder.mIdView.setText(mValues.get(position).id);
        holder.programName.setText(mValues.get(position).name);
        holder.programDescription.setText(mValues.get(position).description);
        holder.programPhoto.setImageResource(mValues.get(position).imageId);

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
        //orig public final TextView mIdView;
        //orig public final TextView mContentView;
        public ProgramItem mItem;

        CardView card_view;
        TextView programName;
        TextView programDescription;
        ImageView programPhoto;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            //orig mIdView = (TextView) view.findViewById(R.id.item_number);
            //orig mContentView = (TextView) view.findViewById(R.id.content);

            card_view = (CardView) view.findViewById(R.id.card_view);
            programName = (TextView)itemView.findViewById(R.id.program_name);
            programDescription = (TextView)itemView.findViewById(R.id.program_description);
            programPhoto = (ImageView)itemView.findViewById(R.id.program_photo);

            final Button button = itemView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    button.setBackgroundColor(Color.RED);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + programName.getText() + "'";
        }

    }
}
