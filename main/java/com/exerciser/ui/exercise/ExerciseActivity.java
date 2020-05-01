package com.exerciser.ui.exercise;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.exerciser.R;

public class ExerciseActivity extends AppCompatActivity {

    public static final ExerciseContent exerciseList = new ExerciseContent();
    public int currentExerciseIndex = -1;

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
