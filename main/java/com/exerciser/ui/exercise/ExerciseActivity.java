package com.exerciser.ui.exercise;

import android.content.Intent;
import android.os.Bundle;

import com.exerciser.ui.exercise.start.StartContent;
import com.exerciser.ui.sessions.SessionsActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.View;

import com.exerciser.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public class ExerciseActivity extends AppCompatActivity implements StartFragment.OnListFragmentInteractionListener {

    public static ExerciseContent exercises = null;
    public int currentExerciseIndex = -1;
    public TextToSpeech tts = null;
    public boolean isSpeechLoaded = false;
    public int programId = -1;

    @Override
    public void onListFragmentInteraction(ExerciseContent.ExerciseItem exerciseItem) {
        //
        // handle click from any item in Exercise list: start exercise
        //
        Fragment f = getSupportFragmentManager().getPrimaryNavigationFragment();
        if (null != f) {
            speak("Ready to start.", TextToSpeech.QUEUE_ADD);
            NavHostFragment.findNavController(f).navigate(R.id.action_StartFragment_to_BreakFragment);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // get the data
        int exerciseId = getIntent().getIntExtra("sessionId", -1);
        exercises = new ExerciseContent(exerciseId);

        // set the toolbar caption
        String title = getIntent().getStringExtra("sessionName");
        String subTitle = exercises.exerciseList.size() + " exercises, Time: " + exercises.getTotalTime();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setSubtitle(subTitle);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String msg = "TTS not set.";
            if (null != tts)
            {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Set<Voice> voices = tts.getVoices();
                    String name = tts.getVoice().getName();
                    msg = "Voice: " + name;
                    msg += " (" + Integer.toString(voices.size());
                    int count = 0;
                    for (Voice voice : voices) {
                        String lang = voice.getLocale().getLanguage().toString();
                        if (count++ < 5)
                            msg += ", " + lang;
                    }
                    msg += ")";
                }
            }

            Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = tts.setLanguage(Locale.US);

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }

                    Log.i("TTS", "Initialization success.");
                    isSpeechLoaded = true;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        Set<Voice> voices = tts.getVoices();
                        ArrayList<Voice> localVoices = new ArrayList<Voice>();
                        int count = 0;
                        for (Voice voice : voices) {
                            String lang = voice.getLocale().getLanguage().toString();

                            if (false && count++ < 5)
                                speak("Language is " + lang, TextToSpeech.QUEUE_ADD);

                            if (lang == "en" || lang == "eng") {
                                localVoices.add(voice);
                            }
                        }

                        if (localVoices.size() > 1) {
                            // get random localized voice
                            int ix = new Random().nextInt(localVoices.size());
                            Voice voice = localVoices.get(ix);
                            tts.setVoice(voice);

                            //speak("Random voice has been set from " + localVoices.size() + " voices.", TextToSpeech.QUEUE_ADD);
                            Log.i("voice: ", voice.getName() + " set from " + localVoices.size() + " local voices.");
                        }
                        else {
                            //speak(voices.size() + " voices found.", TextToSpeech.QUEUE_ADD);
                        }
                    }
                    else {
                        speak("Speech has been initialized.", TextToSpeech.QUEUE_ADD);
                    }

                    //speak("speech ready", TextToSpeech.QUEUE_ADD);
                } else {
                    Log.i("TTS", "TTS Initialization failed!");
                }
            }
        });

    }

    public ExerciseContent getExercises()
    {
        return exercises;
    }

    public void reset()
    {
        this.currentExerciseIndex = -1;
    }

    public void speak(String text, int queueAction) {
        int speechStatus = tts.speak(text, queueAction, null);
        if (speechStatus == TextToSpeech.ERROR) {
            Log.i("TTS", "Error in converting Text to Speech!");
        }
    }

    public void shutup() {
        if (tts.isSpeaking())
            tts.stop();
    }

    public boolean isLoaded() {
        return null != this.tts && this.isSpeechLoaded && this.exercises.isLoaded();
    }

    public void end() {
        Intent intent = new Intent(this, SessionsActivity.class);
        this.programId = 14;
        intent.putExtra("courseId", programId);
        startActivity(intent);
    }

    public int getTotalExercises() {
        return this.exercises.exerciseList.size();
    }

    public int getTimerSeconds() {

        int seconds = -1;

        if (getCurrentExercise().isFirst()) {
            seconds = this.exercises.startSeconds;
        }
        else {
            // get break seconds from previous item NOT current item
            if (this.currentExerciseIndex > 0)
                seconds = this.exercises.exerciseList.get(this.currentExerciseIndex - 1).breakSeconds;
        }

        return seconds;
    }

    public ExerciseContent.ExerciseItem getNextExercise() {

        ExerciseContent.ExerciseItem ex = null;

        if (this.exercises.isLoaded()) {

            this.currentExerciseIndex++;

            if (this.currentExerciseIndex < this.exercises.exerciseList.size()) {
                ex = this.exercises.exerciseList.get(this.currentExerciseIndex);
            }
        }

        return ex;
    }

    public ExerciseContent.ExerciseItem getCurrentExercise() {
        ExerciseContent.ExerciseItem ex = null;

        if (this.currentExerciseIndex < this.exercises.exerciseList.size())
        {
            ex = this.exercises.exerciseList.get(this.currentExerciseIndex);
        }

        return ex;
    }

}
