package com.exerciser.ui.exercise;

import android.content.Intent;
import android.os.Bundle;

import com.exerciser.ui.programs.ProgramsFragment;
import com.exerciser.ui.sessions.SessionsFragment;
import com.exerciser.ui.sessions.session.SessionContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;

import com.exerciser.R;

import java.util.Locale;

public class ExerciseActivity extends AppCompatActivity  {

    public static ExerciseContent exercises = null;
    public int currentExerciseIndex = -1;
    public TextToSpeech tts = null;
    public boolean isSpeechLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
                    //speak("speech ready", TextToSpeech.QUEUE_ADD);
                } else {
                    Log.i("TTS", "TTS Initialization failed!");
                }
            }
        });

        int exerciseId = getIntent().getFlags();
        exercises = new ExerciseContent(exerciseId);
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

    public boolean isLoaded() {
        return null != this.tts && this.isSpeechLoaded && this.exercises.isLoaded();
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
