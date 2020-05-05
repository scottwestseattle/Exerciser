package com.exerciser.ui.sessions;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.exerciser.R;
import com.exerciser.ui.exercise.ExerciseActivity;
import com.exerciser.ui.exercise.ExerciseContent;
import com.exerciser.ui.programs.ProgramsFragment;
import com.exerciser.ui.programs.program.ProgramContent;
import com.exerciser.ui.sessions.session.SessionContent;

import java.util.List;

public class SessionsActivity extends AppCompatActivity implements SessionsFragment.OnListFragmentInteractionListener
{
    public static SessionContent sessions = null;
    public static int courseId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //
        // save the courseId from the MainActivity list item click
        // the fragment will call up and get it during creation
        // todo: where should this be done?
        //
        this.courseId = getIntent().getIntExtra("courseId", -1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);
    }

    @Override
    public void onListFragmentInteraction(SessionContent.Session item) {
        //
        // start the selected exercise
        //
        Intent intent = new Intent(this, ExerciseActivity.class);
        intent.addFlags(item.id);
        startActivity(intent);
    }
}
