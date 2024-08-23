package dev.joemi.arch.widgets;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * 多数情况下，只关心文字的变化。但是实现TextWatcher要重写3个方法，
 * 于是就有了这个简单的类
 */
public abstract class SimpleTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        changed(s.toString());
    }

    protected abstract void changed(String s);
}
