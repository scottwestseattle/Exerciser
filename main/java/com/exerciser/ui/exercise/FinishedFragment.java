package com.exerciser.ui.exercise;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exerciser.R;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FinishedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinishedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String endMsgs[] = {
            "You killed it like a boss!",
            "You made it your bitch!",
            "You did it like a boss!",
            "Boom chocka locka!",
            "Well done!",
            "Whose your daddy!",
            "You've got that Boom Boom Pow!"
    };

    public FinishedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FinishedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FinishedFragment newInstance(String param1, String param2) {
        FinishedFragment fragment = new FinishedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_finished, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ExerciseActivity activity = (ExerciseActivity) getActivity();
        if (null == activity)
            return;

        activity.speak("All exercises completed.", TextToSpeech.QUEUE_ADD);
        activity.speak((endMsgs[new Random().nextInt(endMsgs.length)]), TextToSpeech.QUEUE_ADD);
    }
}
