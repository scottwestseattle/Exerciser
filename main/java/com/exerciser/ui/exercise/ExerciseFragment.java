package com.exerciser.ui.exercise;

import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.exerciser.R;

import java.util.Random;

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
                showBreakFragment();
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
                updateTimerAudio(secondsRemaining);
            } else {
                stopTimer();
                showBreakFragment();
            }
        }
    };

    private void updateTimerAudio(int seconds) {
        if (seconds == 11)
        {
            ExerciseActivity activity = (ExerciseActivity) getActivity();
            ExerciseContent.ExerciseItem exerciseItem = activity.getCurrentExercise();
            if (exerciseItem.instructions.length() > 0)
            {
                speak(exerciseItem.instructions, TextToSpeech.QUEUE_ADD);
            }
        }
        else if (seconds <= 10 && seconds > 0) {
            speak(Integer.toString(seconds), TextToSpeech.QUEUE_FLUSH);
        } else if ((seconds % 10) == 0) {

            ExerciseActivity activity = (ExerciseActivity) getActivity();
            ExerciseContent.ExerciseItem exerciseItem = activity.getCurrentExercise();

            String msg = "";

            if (exerciseItem.instructions.length() > 0)
            {
                msg += exerciseItem.instructions;
                msg += msg.endsWith(".") ? "  " : ".  ";
            }

            msg += getSecondsRemainingMessage(seconds);

            speak(msg, TextToSpeech.QUEUE_ADD);
        }
    }

    private String getSecondsRemainingMessage(int seconds) {
        String msg = "";
        int option = new Random().nextInt(7);

        switch(option) {
            case 0:
                msg = Integer.toString(seconds) + " seconds to go";
                break;
            case 1:
                msg = Integer.toString(seconds) + " seconds remaining";
                break;
            case 2:
                msg = Integer.toString(seconds) + " more seconds";
                break;
            case 3:
                msg = "Only " + Integer.toString(seconds) + " more seconds";
                break;
            case 4:
                msg = "Keep it up for " + Integer.toString(seconds) + " more seconds";
                break;
            case 5:
                msg = "Keep going for " + Integer.toString(seconds) + " more seconds";
                break;
            case 6:
                msg = "Continue for " + Integer.toString(seconds) + " seconds longer";
                break;
            default:
                msg = Integer.toString(seconds) + " seconds (random error)";
        }

        return msg + ".";
    }

    private void speak(String text, int queueAction)
    {
        ExerciseActivity activity = (ExerciseActivity) getActivity();
        activity.speak(text, queueAction);
    }

    private void loadCurrent() {

        ExerciseActivity activity = (ExerciseActivity) getActivity();
        ExerciseContent.ExerciseItem exerciseItem = activity.getCurrentExercise();
        if (null != exerciseItem) {
            setStaticViews(exerciseItem, activity.getTotalExercises());
            speak("Begin.  Do " + exerciseItem.name + " -- for " + exerciseItem.runSeconds + " seconds", TextToSpeech.QUEUE_ADD);
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
        if (null != iv) {
            int id = getResources().getIdentifier(exerciseItem.imageName, "drawable", getContext().getPackageName());
            iv.setImageResource(id);
        }

    }

    private void updateTimerDisplay(int seconds)
    {
        TextView countDown = this.getView().findViewById(R.id.textview_countdown);
        if (null != countDown)
            countDown.setText(Integer.toString(seconds));
    }
}