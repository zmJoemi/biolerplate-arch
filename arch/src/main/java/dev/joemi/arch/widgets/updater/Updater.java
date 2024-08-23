package dev.joemi.arch.widgets.updater;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import dev.joemi.arch.widgets.updater.dialog.UpdateDialog;
import dev.joemi.arch.widgets.updater.entity.VersionConfig;

/**
 * Created by Joemi on 8/1/22.
 * Do one thing at a time, and do well.
 */

public class Updater {
    private Context mContext;
    private VersionConfig mVersionConfig;

    //是否强制安装：不安装无法使用app
    private boolean mIsForce;


    private Updater(Builder builder) {
        mContext = builder.context;
        mVersionConfig = builder.verConfig;
        mIsForce = builder.isForce;

    }

    public void update() {
        //显示UI
        UpdateDialog updateDialog = new UpdateDialog(mContext, mVersionConfig, mIsForce);
        updateDialog.show();
    }

    public static class Builder {
        Context context;
        VersionConfig verConfig;

        //是否强制安装：不安装无法使用app
        boolean isForce;

        /**
         * 构建者
         *
         * @param context 上下文
         */
        public Builder(@NonNull Context context) {
            this.context = context;
            isForce = false;

        }

        public Updater build() {
            if (null == verConfig || TextUtils.isEmpty(verConfig.getDownloadUrl()))
                throw new NullPointerException("VersionConfig未正确配置");

            return new Updater(this);
        }

        public Builder version(@NonNull VersionConfig config) {
            this.verConfig = config;
            return this;
        }

        public Builder forceUpdate() {
            this.isForce = true;
            return this;
        }

    }

}
