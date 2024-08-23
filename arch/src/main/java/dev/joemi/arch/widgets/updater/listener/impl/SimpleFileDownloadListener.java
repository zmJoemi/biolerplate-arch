package dev.joemi.arch.widgets.updater.listener.impl;

import dev.joemi.arch.widgets.updater.listener.FileDownloadListener;

/**
 * Created by Joemi on 8/2/22.
 * Do one thing at a time, and do well.
 */

public abstract class SimpleFileDownloadListener implements FileDownloadListener {

    @Override
    public void beforeDownload() {
        //do nothing
    }

}
