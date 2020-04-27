package com.exerciser.ui.programs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProgramsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProgramsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Programs");
    }

    public LiveData<String> getText() {
        return mText;
    }
}