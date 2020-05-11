package com.exerciser.ui.exercise;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
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
    public String sessionName = "";

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
        this.programId = getIntent().getIntExtra("courseId", -1);
        int exerciseId = getIntent().getIntExtra("sessionId", -1);
        this.sessionName = getIntent().getStringExtra("sessionName");

        // get the data
        exercises = new ExerciseContent(exerciseId);

        // set up the main view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        // set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(this.sessionName);
        String subTitle = exercises.exerciseList.size() + " exercises, Time: " + exercises.getTotalTime();
        toolbar.setSubtitle(subTitle);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "TTS not set.";
                if (null != tts) {
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

        FloatingActionButton fabPlay = findViewById(R.id.fabPlay);
        fabPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get the active fragment so we know which action to perform
                Fragment fragment = getActiveFragment();

                if (fragment instanceof StartFragment) {
                    NavHostFragment.findNavController(fragment).navigate(R.id.action_StartFragment_to_BreakFragment);
                } else if (fragment instanceof BreakFragment) {
                    boolean paused = ((BreakFragment) fragment).onFabPlayPauseClicked();
                    setFabPlayIcon(paused);
                } else if (fragment instanceof ExerciseFragment) {
                    boolean paused = ((ExerciseFragment) fragment).onFabPlayPauseClicked();
                    setFabPlayIcon(paused);
                } else if (fragment instanceof FinishedFragment) {
                    reset();
                    NavHostFragment.findNavController(fragment).navigate(R.id.action_finishedFragment_to_StartFragment);
                }
            }
        });

        FloatingActionButton fabNext = findViewById(R.id.fabNext);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get the active fragment so we know which action to perform
                Fragment fragment = getActiveFragment();

                if (fragment instanceof StartFragment) {
                    NavHostFragment.findNavController(fragment).navigate(R.id.action_StartFragment_to_BreakFragment);
                } else if (fragment instanceof BreakFragment) {
                    boolean paused = ((BreakFragment) fragment).onFabNextClicked();
                    setFabPlayIcon(paused);
                } else if (fragment instanceof ExerciseFragment) {
                    boolean paused = ((ExerciseFragment) fragment).onFabNextClicked();
                    setFabPlayIcon(paused);
                } else if (fragment instanceof FinishedFragment) {
                    reset();
                    NavHostFragment.findNavController(fragment).navigate(R.id.action_finishedFragment_to_StartFragment);
                }
            }
        });

        FloatingActionButton fabEnd = findViewById(R.id.fabEnd);
        fabEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get the active fragment so we know which action to perform
                reset();
                boolean showPlayIcon = true;
                Fragment fragment = getActiveFragment();

                if (fragment instanceof StartFragment) {
                    end();
                } else if (fragment instanceof BreakFragment) {
                    speak("Stopping...", TextToSpeech.QUEUE_FLUSH);
                    setFabPlayIcon(showPlayIcon);
                    ((BreakFragment) fragment).onHardStop();
                } else if (fragment instanceof ExerciseFragment) {
                    speak("Stopping...", TextToSpeech.QUEUE_FLUSH);
                    setFabPlayIcon(showPlayIcon);
                    ((ExerciseFragment) fragment).onHardStop();
                } else if (fragment instanceof FinishedFragment) {
                    setFabPlayIcon(showPlayIcon);
                    NavHostFragment.findNavController(fragment).navigate(R.id.action_finishedFragment_to_StartFragment);
                }
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
                        } else {
                            //speak(voices.size() + " voices found.", TextToSpeech.QUEUE_ADD);
                        }
                    } else {
                        speak("Speech has been initialized.", TextToSpeech.QUEUE_ADD);
                    }

                    //speak("speech ready", TextToSpeech.QUEUE_ADD);
                } else {
                    Log.i("TTS", "TTS Initialization failed!");
                }
            }
        });

    }

    private Fragment getActiveFragment()
    {
        Fragment nav = getSupportFragmentManager().getPrimaryNavigationFragment();
        List<Fragment> fragments = (null != nav) ? nav.getChildFragmentManager().getFragments() : null;
        Fragment fragment = (null != fragments && fragments.size() > 0) ? fragments.get(0) : null;

        return fragment;
    }

    public void setFabPlayIcon(boolean paused) {
        setFabButtonIcon(R.id.fabPlay,
                paused  ? android.R.drawable.ic_media_play
                        : android.R.drawable.ic_media_pause);
    }

    public void setFabButtonIcon(int buttonId, int buttonIcon) {
        FloatingActionButton fabPlay = findViewById(buttonId);
        fabPlay.setImageResource(buttonIcon);
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
        finish();
        return;
    }

    public boolean isLastExercise() {
        return (this.currentExerciseIndex == this.exercises.exerciseList.size() - 1);
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
