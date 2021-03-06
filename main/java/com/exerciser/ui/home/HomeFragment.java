package com.exerciser.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.exerciser.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Button button = root.findViewById(R.id.button_beginner);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                textView.setText("Beginner Program");
            }
        });

        button = root.findViewById(R.id.button_intermediate);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                textView.setText("Intermediate Program");
            }
        });

        button = root.findViewById(R.id.button_advanced);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                textView.setText("Advanced Program");
            }
        });

        return root;
    }
}
