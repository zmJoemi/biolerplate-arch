package dev.joemi.arch.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import dev.joemi.arch.vm.BaseViewModel;

public abstract class BaseVMViewBindingActivity<VM extends BaseViewModel, VB extends ViewBinding> extends AppCompatActivity {

    // ViewModel
    protected @Nullable VM viewModel;

    // ViewBinding
    protected VB bindingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        beforeSetContentView();

        bindingView = getViewBinding();
        setContentView(bindingView.getRoot());

        initViewModel();
        //view的设置
        initView(savedInstanceState);
        //观察者的设置
        initObserver();
    }

    /**
     * 初始化ViewModel
     */
    private void initViewModel() {
        Class<VM> viewModelClass = getViewModelClass();
        if (viewModelClass != null) {
            ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication());
            viewModel = new ViewModelProvider(this, factory).get(viewModelClass);
        }
    }

    protected void beforeSetContentView() {
        //do nothing
    }

    protected abstract @NonNull VB getViewBinding();

    protected abstract @Nullable Class<VM> getViewModelClass();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initObserver();
}
