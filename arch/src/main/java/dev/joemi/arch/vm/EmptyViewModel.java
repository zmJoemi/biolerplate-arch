package dev.joemi.arch.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

//一个空的ViewModel
public class EmptyViewModel extends BaseViewModel {
    public EmptyViewModel(@NonNull Application application) {
        super(application);
    }
}
