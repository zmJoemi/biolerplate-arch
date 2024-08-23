package dev.joemi.arch.widgets.updater.listener;

/**
 * Created by Joemi on 8/2/22.
 * Do one thing at a time, and do well.
 */

public interface FileDownloadListener {

    void beforeDownload();

    void downloading(int cur, int total);

    void downloadSuccess();

    void downloadFailed();

}
