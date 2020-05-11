package com.exerciser.ui.programs;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exerciser.R;
import com.exerciser.ui.exercise.ExerciseActivity;
import com.exerciser.ui.programs.ProgramsFragment.OnListFragmentInteractionListener;
import com.exerciser.ui.programs.program.ProgramContent.ProgramItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ProgramItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyProgramsRecyclerViewAdapter
        extends RecyclerView.Adapter<MyProgramsRecyclerViewAdapter.ViewHolder>
{
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.programName.setText(mValues.get(position).name);
        holder.programDescription.setText(mValues.get(position).description);
        switch(position)
        {
            case 0:
                holder.programLayout.setBackgroundResource(R.drawable.bg_0);
                break;
            case 1:
                holder.programLayout.setBackgroundResource(R.drawable.bg_1);
                break;
            case 2:
                holder.programLayout.setBackgroundResource(R.drawable.bg_2);
                break;
            case 3:
                holder.programLayout.setBackgroundResource(R.drawable.bg_3);
                break;
            default:
                holder.card_view.setCardBackgroundColor(Color.RED);
                break;
        }

        // show number of sessions
        int cnt = mValues.get(position).sessionCount;
        String sessionCount = Integer.toString(cnt) + ((cnt == 1) ? " session" : " sessions");
        holder.sessionCount.setText(sessionCount);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
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
        public ProgramItem mItem;

        CardView card_view;
        TextView programName;
        TextView programDescription;
        TextView sessionCount;
        RelativeLayout programLayout;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            card_view = (CardView) view.findViewById(R.id.card_view);
            programName = (TextView)view.findViewById(R.id.program_name);
            programDescription = (TextView)view.findViewById(R.id.program_description);
            sessionCount = (TextView)view.findViewById(R.id.session_count);
            programLayout = (RelativeLayout) view.findViewById(R.id.program_layout);

            /*
            programPhoto = (ImageView)itemView.findViewById(R.id.program_photo);

            //
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
             //

             */
        }

        @Override
        public String toString() {
            return super.toString() + " '" + programName.getText() + "'";
        }

    }
}
