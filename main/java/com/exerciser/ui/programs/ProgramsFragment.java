package com.exerciser.ui.programs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.exerciser.R;

public class ProgramsFragment extends Fragment {

    private ProgramsViewModel programsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        programsViewModel =
                ViewModelProviders.of(this).get(ProgramsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_programs, container, false);
        final TextView textView = root.findViewById(R.id.text_programs);
        programsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
