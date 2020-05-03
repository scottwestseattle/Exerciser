package com.exerciser.ui.exercise;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;

import com.exerciser.R;

import java.util.ArrayList;
import java.util.Locale;

public class ExerciseActivity extends AppCompatActivity {

    public static final ExerciseContent exerciseList = new ExerciseContent();
    public int currentExerciseIndex = -1;
    public TextToSpeech tts;

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
                } else {
                    Log.i("TTS", "TTS Initialization failed!");
                }
            }
        });
    }

    public void reset()
    {
        this.currentExerciseIndex = -1;
    }

    public void speak(String text) {

        int speechStatus = tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        if (speechStatus == TextToSpeech.ERROR) {
            Log.i("TTS", "Error in converting Text to Speech!");
        }
    }

    public int getTotalExercises() {
        return this.exerciseList.ITEMS.size();
    }

    public int getTimerSeconds() {

        int seconds = -1;

        if (getCurrentExercise().isFirst()) {
            seconds = this.exerciseList.startSeconds;
        }
        else {
            // get break seconds from previous item NOT current item
            if (this.currentExerciseIndex > 0)
                seconds = this.exerciseList.ITEMS.get(this.currentExerciseIndex - 1).breakSeconds;
        }

        return seconds;
    }

    public ExerciseContent.ExerciseItem getNextExercise() {

        this.exerciseList.load();

        ExerciseContent.ExerciseItem ex = null;

        this.currentExerciseIndex++;

        if (this.currentExerciseIndex < this.exerciseList.ITEMS.size())
        {
            ex = this.exerciseList.ITEMS.get(this.currentExerciseIndex);
        }

        return ex;
    }

    public ExerciseContent.ExerciseItem getCurrentExercise() {
        ExerciseContent.ExerciseItem ex = null;

        if (this.currentExerciseIndex < this.exerciseList.ITEMS.size())
        {
            ex = this.exerciseList.ITEMS.get(this.currentExerciseIndex);
        }

        return ex;
    }

}
