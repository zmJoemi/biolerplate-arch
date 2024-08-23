package dev.joemi.arch.widgets.updater;

import android.app.DownloadManager;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import androidx.annotation.NonNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import dev.joemi.arch.widgets.updater.listener.FileDownloadListener;
import dev.joemi.arch.widgets.updater.receiver.DownloadCompleteReceiver;

/**
 * Created by Joemi on 8/2/22.
 * Do one thing at a time, and do well.
 */

public class Download {
    private volatile static Download INSTANCE;

    private Context mContext;

    private DownloadManager mDownloadManager;
    private long mDownloadId = -1;
    private DownloadCompleteReceiver mDownloadCompleteReceiver;

    private FileDownloadListener mFileDownloadListener;

    private Download(Context context) {
        this.mContext = context;
        mDownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public static Download with(@NonNull Context context) {
        return new Download(context);
    }

    public Download listener(@NonNull FileDownloadListener listener) {
        this.mFileDownloadListener = listener;
        return this;
    }

    public void start(String url) {
        Uri apkUri = Uri.parse(url);
        DownloadManager.Request downloadRequest = new DownloadManager.Request(apkUri);
        String fileName = System.currentTimeMillis() + ".apk";
        downloadRequest.setDestinationInExternalFilesDir(mContext, Environment.DIRECTORY_DOWNLOADS, fileName);
        //downloadRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        mDownloadId = mDownloadManager.enqueue(downloadRequest);

        if (null != mFileDownloadListener) {
            mFileDownloadListener.beforeDownload();
        }
        registerDownloadCompleteReceiver();
        querying();
    }

    private void querying() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(mDownloadId);
                Cursor cursor = null;
                try {
                    cursor = mDownloadManager.query(query);
                    if (null != cursor && cursor.moveToFirst()) {
                        int downloaded = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        int total = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                        int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        if (null != mFileDownloadListener) {
                            mFileDownloadListener.downloading(downloaded, total);
                            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                mFileDownloadListener.downloadSuccess();
                            } else if (status == DownloadManager.STATUS_FAILED) {
                                mFileDownloadListener.downloadFailed();
                            }
                        }
                        if (status == DownloadManager.STATUS_SUCCESSFUL || status == DownloadManager.STATUS_FAILED) {
                            scheduledExecutorService.shutdownNow();
                        }
                    }
                } finally {
                    if (cursor != null)
                        cursor.close();
                }
            }
        };
        scheduledExecutorService.scheduleWithFixedDelay(runnable, 0, 1, TimeUnit.SECONDS);
    }

    private void registerDownloadCompleteReceiver() {
        if (mDownloadId == -1) {
            return;
        }
        mDownloadCompleteReceiver = new DownloadCompleteReceiver(mDownloadId);
        mContext.registerReceiver(mDownloadCompleteReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    //DownloadCompleteReceiver内部自己进行了unregister
    private void unregisterDownloadCompleteReceiver() {
        if (null == mDownloadCompleteReceiver)
            return;
        mContext.unregisterReceiver(mDownloadCompleteReceiver);
    }
}
