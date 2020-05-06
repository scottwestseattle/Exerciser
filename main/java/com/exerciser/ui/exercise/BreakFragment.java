package com.exerciser.ui.exercise;

import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.exerciser.R;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class BreakFragment extends Fragment {

    static private boolean autoStart = false;
    static private boolean started = false;
    private int secondsRemaining = 0;
    private final int second = 1000; // 1 Second
    private final int countdownSeconds = 5;
    private final int getReadySeconds = countdownSeconds + 1;
    private Handler handler = new Handler();
    private boolean timerPaused = false;

    String startMsgs[] = {
            "Okay fat boy, Here we go!",
            "Okay Lard Ass, Giddy up!",
            "Okay fatty, It's Go Go Time!",
            "Get your fat ass off of that couch!",
            "Okay Fatty, Let's get jiggy with it!",
            "Okay Fatty, Get ready to Shake your money maker!",
            "Okay Fatty, Your last easy day was yesterday!",
            "Let's go jelly belly!",
            "It's go time, butter-ball!",
            "Okay fatty, Put down that big plate of food!",
            "Okay fatty, Take that chicken leg out of your fat face!",
            "Get your fat face out of that bucket of ice-cream!",
            "Okay fatty, Get ready for an avalanche of pain!",
            "Okay fatty, it's about to get really real!",
            "Okay, don't be a little bitch!"
    };

    String endMsgs[] = {
            "You killed it like a boss!",
            "You made it your bitch!",
            "You did it like a boss!",
            "Boom chocka locka!",
            "Feel the heat, in your meat!",
            "Whose your daddy!",
            "You've got that Boom Boom Pow!"
    };

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

    private Runnable startUp = new Runnable(){
        public void run() {

            ExerciseActivity activity = (ExerciseActivity) getActivity();
            if (activity.isLoaded()) {
                start();
            }
            else {
                Log.i("startup", "waiting one second");
                handler.postDelayed(startUp, second); // update in 1 second
            }
        }
    };

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
            if (started) {
                speak("Stopping...", TextToSpeech.QUEUE_FLUSH);
                stop();
                ((ExerciseActivity) getActivity()).end();
            }
            else
                start();
            }
        });

        view.findViewById(R.id.button_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (started) {
                    shutup();
                    stopTimer();
                    showExerciseFragment();
                }
                else {
                    start();
                }

            }
        });

        view.findViewById(R.id.button_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timerPaused) {
                    setButtonText("Pause", R.id.button_pause);
                    speak("Continued.  ", TextToSpeech.QUEUE_FLUSH);
                    startTimer(secondsRemaining); // restart timer
                }
                else {
                    setButtonText("Continue", R.id.button_pause);
                    speak("paused.  ", TextToSpeech.QUEUE_FLUSH);
                    stopTimer();
                }

                timerPaused = !timerPaused;
            }
        });

        if (this.started)
            loadNext();
        else if (this.autoStart)
            handler.postDelayed(this.startUp, this.second * 2);
    }

    private String getRandomMessage(String[] msgs) {

        int ix = new Random().nextInt(msgs.length);

        return msgs[ix];
    }

    private void start() {
        ExerciseActivity activity = (ExerciseActivity) getActivity();

        if (activity.isLoaded()) {
            activity.speak(getRandomMessage(startMsgs), TextToSpeech.QUEUE_ADD);
            this.started = true;
            activity.reset();
            loadNext();
            setButtonText("Stop", R.id.button_start);
        }
        else {
            activity.speak("Wait for exercises to finish loading...", TextToSpeech.QUEUE_ADD);
            handler.postDelayed(this.startUp, this.second); // wait 1 second
        }
    }

    private void stop() {
        this.started = false;
        stopTimer();
        setButtonText("Start", R.id.button_start);
    }

    private void setButtonText(String text, int buttonId) {
        Button button = this.getView().findViewById(buttonId);
        if (null != button)
            button.setText(text);
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
                title = getResources().getString(R.string.exercise_get_ready);
                text = title + " to start in " + seconds + " seconds.";
                text += "  The first exercise is, " + exerciseItem.name + ", for " + exerciseItem.runSeconds + " seconds.";
            }
            else
            {
                title = getResources().getString(R.string.exercise_take_a_break);
                text = title + " for " + seconds + " seconds.";
                text += "  The next exercise is, " + exerciseItem.name + ", for " + exerciseItem.runSeconds + " seconds.";
            }

            //String title = (exerciseItem.order == 1) ? "Get ready" : "Take a break";
            //speak(title + ".  " + exerciseItem.name + ", starts in " + seconds + " seconds");

            speak(text, TextToSpeech.QUEUE_ADD);

            // start
            setStaticViews(activity, exerciseItem, title);
            startTimer(seconds);
            setButtonText("Stop", R.id.button_start);
        }
        else {
            // end
            activity.speak("All exercises completed.", TextToSpeech.QUEUE_ADD);
            activity.speak((this.getRandomMessage(endMsgs)), TextToSpeech.QUEUE_ADD);
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
        if (null != iv) {
            int id = getResources().getIdentifier(exerciseItem.imageName, "drawable", getContext().getPackageName());
            iv.setImageResource(id);
        }
    }

    private void updateTimerAudio(int seconds) {
        if (seconds == this.getReadySeconds) {
            speak("Starting in: ", TextToSpeech.QUEUE_FLUSH);
        }
        else if (seconds <= this.countdownSeconds && seconds > 0) {
            speak(Integer.toString(seconds), TextToSpeech.QUEUE_FLUSH);
        }
    }

    private void speak(String text, int queueAction) {
        ExerciseActivity activity = (ExerciseActivity) getActivity();
        activity.speak(text, queueAction);
    }

    private void shutup() {
        ExerciseActivity activity = (ExerciseActivity) getActivity();
        activity.shutup();
    }

}
