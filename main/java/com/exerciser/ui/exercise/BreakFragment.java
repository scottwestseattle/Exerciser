package com.exerciser.ui.exercise;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.exerciser.R;

import java.util.List;

public class BreakFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_break, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sbw NavHostFragment.findNavController(BreakFragment.this)
                //sbw         .navigate(R.id.action_FirstFragment_to_SecondFragment);

                //startTimer();
            }
        });

        loadNext();
    }

    private int secondsRemaining = 0;
    private final int second = 1000; // 1 Second
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable(){
        public void run() {
            
            secondsRemaining--;
            updateTimerDisplay(secondsRemaining);

            if (secondsRemaining >= 1)
                handler.postDelayed(runnable, second); // update in 1 second
            else
                showExerciseFragment();
        }
    };

    private void loadNext() {

        ExerciseActivity activity = (ExerciseActivity) getActivity();
        ExerciseContent.ExerciseItem exerciseItem = activity.getNextExercise();

        if (null != exerciseItem)
        {
            setStaticViews(activity, exerciseItem);
            startTimer(activity.getTimerSeconds());
        }
        else {
            stopTimer();
        }
    }

    private void startTimer(int seconds)
    {
        this.secondsRemaining = seconds;
        updateTimerDisplay(seconds);
        handler.postDelayed(this.runnable, this.second); // update in 1 second
    }

    private void showExerciseFragment() {
        NavHostFragment.findNavController(BreakFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);
    }

    private void stopTimer() {
        handler.removeCallbacks(this.runnable);
    }

    private void updateTimerDisplay(int seconds)
    {
        TextView countDown = this.getView().findViewById(R.id.textview_countdown);
        if (null != countDown)
            countDown.setText(Integer.toString(seconds));
    }
    
    private void setStaticViews(ExerciseActivity activity, ExerciseContent.ExerciseItem exerciseItem)
    {
        //
        // set static values
        //

        TextView tv = this.getView().findViewById(R.id.textview_title);
        if (null != tv)
            tv.setText("Get Ready");

        tv = this.getView().findViewById(R.id.textview_coming_up);
        if (null != tv)
            tv.setText("Coming up " + exerciseItem.order + " of " + activity.getTotalExercises());

        tv = this.getView().findViewById(R.id.textview_exercise_name);
        if (null != tv)
            tv.setText(exerciseItem.name);

        tv = this.getView().findViewById(R.id.textview_exercise_seconds);
        if (null != tv)
            tv.setText(Integer.toString(exerciseItem.runSeconds) + " seconds");

        tv = this.getView().findViewById(R.id.textview_countdown);
        if (null != tv)
            tv.setText(Integer.toString(this.secondsRemaining));

        ImageView iv = this.getView().findViewById(R.id.imageViewCurrent);
        if (null != iv)
            iv.setImageResource(exerciseItem.imageId);

    }
}
