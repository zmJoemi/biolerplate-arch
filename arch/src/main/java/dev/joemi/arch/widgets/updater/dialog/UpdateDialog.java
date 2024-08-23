package dev.joemi.arch.widgets.updater.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import dev.joemi.arch.R;
import dev.joemi.arch.widgets.updater.Download;
import dev.joemi.arch.widgets.updater.entity.VersionConfig;
import dev.joemi.arch.widgets.updater.listener.FileDownloadListener;

/**
 * Created by Joemi on 8/1/22.
 * Do one thing at a time, and do well.
 */

public class UpdateDialog extends Dialog implements View.OnClickListener, FileDownloadListener {

    private TextView tvTitle;
    private TextView tvUpdateContent;
    private Button btnUpdate;
    private ProgressBar progressBar;
    private LinearLayout linearClose;
    private ImageView ivClose;

    private VersionConfig versionConfig;
    private boolean isForce;

    public UpdateDialog(@NonNull Context context, @NonNull VersionConfig versionConfig, boolean isForce) {
        super(context);
        this.versionConfig = versionConfig;
        this.isForce = isForce;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_update);

        tvTitle = findViewById(R.id.tvTitle);
        tvUpdateContent = findViewById(R.id.tvUpdateContent);
        btnUpdate = findViewById(R.id.btnUpdate);
        progressBar = findViewById(R.id.progressBar);
        linearClose = findViewById(R.id.linearClose);
        ivClose = findViewById(R.id.ivClose);
        tvUpdateContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        setCanceledOnTouchOutside(false);

        initData();
    }

    private void initData() {
        //kb -> mb
        String mbSize = String.valueOf(versionConfig.getSize() / 1024f);
        tvTitle.setText(String.format(getContext().getString(R.string.update_title), versionConfig.getVersionName(), mbSize));
        tvUpdateContent.setText(versionConfig.getUpdateContent());
        if (isForce) {
            linearClose.setVisibility(View.GONE);
        } else {
            ivClose.setOnClickListener(this);
        }
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnUpdate) {
            btnUpdate.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            Download.with(getContext()).listener(this).start(versionConfig.getDownloadUrl());
        } else if (v.getId() == R.id.ivClose) {
            dismiss();
        }
    }

    @Override
    public void beforeDownload() {

    }

    @Override
    public void downloading(int cur, int total) {
        if (cur == -1 || total == -1)
            return;
        float progress = cur / (total * 1.0f) * 100;
        if (progress > 100) {
            progress = 100;
        }
        progressBar.setProgress((int) progress);
    }

    @Override
    public void downloadSuccess() {
        dismiss();
    }

    @Override
    public void downloadFailed() {

    }
}
