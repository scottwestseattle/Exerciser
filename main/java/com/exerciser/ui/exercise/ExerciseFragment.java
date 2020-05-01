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

public class ExerciseFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_end).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTimer();
            }
        });

        loadCurrent();
    }

    private int secondsRemaining = -1;
    private final int second = 1000; // 1 Second
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable(){
        public void run() {

            secondsRemaining--;
            updateTimerDisplay(secondsRemaining);

            if (secondsRemaining >= 1) {
                handler.postDelayed(runnable, second); // update in 1 second
            } else {
                stopTimer();
                showBreakFragment();
            }
        }
    };

    private void loadCurrent() {

        ExerciseActivity activity = (ExerciseActivity) getActivity();
        ExerciseContent.ExerciseItem exerciseItem = activity.getCurrentExercise();
        if (null != exerciseItem) {
            setStaticViews(exerciseItem, activity.getTotalExercises());
            startTimer(exerciseItem.runSeconds);
        }
    }

    private void showBreakFragment() {
        NavHostFragment.findNavController(ExerciseFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
    }

    private void startTimer(int seconds)
    {
        // start the exercise timer
        this.secondsRemaining = seconds;
        updateTimerDisplay(seconds);
        handler.postDelayed(runnable, second); // update in 1 second
    }

    private void stopTimer() {
        handler.removeCallbacks(runnable);
    }

    private void setStaticViews(ExerciseContent.ExerciseItem exerciseItem, int totalExercises)
    {
        //
        // set static field values
        //

        TextView exerciseCount = this.getView().findViewById(R.id.textview_exercise_count);
        if (null != exerciseCount)
            exerciseCount.setText(exerciseItem.order + " of " + totalExercises);

        TextView exerciseName = this.getView().findViewById(R.id.textview_exercise_name);
        if (null != exerciseName)
            exerciseName.setText(exerciseItem.name);

        TextView exerciseSeconds = this.getView().findViewById(R.id.textview_exercise_seconds);
        if (null != exerciseSeconds)
            exerciseSeconds.setText(Integer.toString(exerciseItem.runSeconds) + " seconds");

        ImageView iv = this.getView().findViewById(R.id.imageViewCurrent);
        if (null != iv)
            iv.setImageResource(exerciseItem.imageId);

    }

    private void updateTimerDisplay(int seconds)
    {
        TextView countDown = this.getView().findViewById(R.id.textview_countdown);
        if (null != countDown)
            countDown.setText(Integer.toString(seconds));
    }
}
