package dev.joemi.arch.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import dev.joemi.arch.vm.BaseViewModel;

public abstract class BaseVMViewBindingFragment<VM extends BaseViewModel, VB extends ViewBinding> extends Fragment {
    // ViewModel
    protected VM viewModel;
    // ViewBinding
    protected VB bindingView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bindingView = getViewBinding(container);
        return bindingView.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initView(savedInstanceState);
        initObserver();
    }

    protected abstract @NonNull VB getViewBinding(ViewGroup container);

    /**
     * 初始化ViewModel
     */
    private void initViewModel() {
        Class<VM> viewModelClass = getViewModelClass();
        if (viewModelClass != null) {
            ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication());
            viewModel = new ViewModelProvider(this, factory).get(viewModelClass);
        }
    }

    protected abstract @Nullable Class<VM> getViewModelClass();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initObserver();
}
