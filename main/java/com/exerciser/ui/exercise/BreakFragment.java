package com.exerciser.ui.exercise;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.exerciser.R;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

public class BreakFragment extends Fragment {

    private final int countdownSeconds = 5;
    private final int getReadySeconds = countdownSeconds + 1;

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

            start();
            }
        });

        if (this.started)
            loadNext();
    }

    static private boolean started = false;
    private int secondsRemaining = 0;
    private final int second = 1000; // 1 Second
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable(){
        public void run() {
            
            secondsRemaining--;
            updateTimerDisplay(secondsRemaining);

            if (secondsRemaining >= 1) {
                handler.postDelayed(runnable, second); // update in 1 second
                updateTimerAudio(secondsRemaining);
            }
            else
                showExerciseFragment();
        }
    };

    private void start() {
            this.started = true;
            ExerciseActivity activity = (ExerciseActivity) getActivity();
            activity.reset();
            loadNext();
    }

    private void updateTimerAudio(int seconds) {
        if (seconds == this.getReadySeconds) {
            speak("Starting in: ");
        }
        else if (seconds <= this.countdownSeconds && seconds > 0) {
            speak(Integer.toString(seconds));
        }
    }

    private void speak(String text) {
        ExerciseActivity activity = (ExerciseActivity) getActivity();
        activity.speak(text);
    }

    private void loadNext() {
        ExerciseActivity activity = (ExerciseActivity) getActivity();
        ExerciseContent.ExerciseItem exerciseItem = activity.getNextExercise();

        if (null != exerciseItem)
        {
            int seconds = activity.getTimerSeconds();
            String title = "";
            String text = "";

            if (exerciseItem.order == 1) // first exercise
            {
                title = "Get ready";
                text = title + " to start in " + seconds + " seconds.";
                text += "  The first exercise is, " + exerciseItem.name + ", for " + exerciseItem.runSeconds + " seconds.";
            }
            else
            {
                title = "Take a break";;
                text = title + " for " + seconds + " seconds.";
                text += "  The next exercise is, " + exerciseItem.name + ", for " + exerciseItem.runSeconds + " seconds.";
            }

            //String title = (exerciseItem.order == 1) ? "Get ready" : "Take a break";
            //speak(title + ".  " + exerciseItem.name + ", starts in " + seconds + " seconds");

            speak(text);

            // start
            setStaticViews(activity, exerciseItem, title);
            startTimer(seconds);
        }
        else {
            // end
            activity.speak("All exercises completed.  Good Work!");
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
        if (seconds > 0) {
            TextView countDown = this.getView().findViewById(R.id.textview_countdown);
            if (null != countDown)
                countDown.setText(Integer.toString(seconds));
        }
    }
    
    private void setStaticViews(ExerciseActivity activity, ExerciseContent.ExerciseItem exerciseItem, String title)
    {
        //
        // set static values
        //

        TextView tv = this.getView().findViewById(R.id.textview_title);
        if (null != tv)
            tv.setText(title);

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
