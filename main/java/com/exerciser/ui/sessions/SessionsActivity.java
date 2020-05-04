package com.exerciser.ui.sessions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.exerciser.R;
import com.exerciser.ui.exercise.ExerciseActivity;
import com.exerciser.ui.sessions.session.SessionContent;

public class SessionsActivity extends AppCompatActivity implements SessionsFragment.OnListFragmentInteractionListener
{
    public static SessionContent sessions = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);

        sessions = new SessionContent();
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
