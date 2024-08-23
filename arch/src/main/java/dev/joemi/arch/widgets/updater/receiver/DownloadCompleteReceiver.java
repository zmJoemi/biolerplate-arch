package dev.joemi.arch.widgets.updater.receiver;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * Created by Joemi on 8/2/22.
 * Do one thing at a time, and do well.
 */

public class DownloadCompleteReceiver extends BroadcastReceiver {

    private long downloadId;

    public DownloadCompleteReceiver(long downloadId) {
        this.downloadId = downloadId;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        if (completeDownloadId != downloadId) {
            return;
        }
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = null;
        try {
            cursor = downloadManager.query(query);
            if (null != cursor && cursor.moveToFirst()) {
                int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                if (DownloadManager.STATUS_SUCCESSFUL == status) {
                    String uriStr = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    installApp(context, uriStr);

                    context.unregisterReceiver(this);

                }

            }
        } finally {
            if (null != cursor)
                cursor.close();
        }
    }

    //跳转安装
    @SuppressLint("ObsoleteSdkInt")
    private void installApp(Context context, String fileUriPath) {
        //manifest 中记得添加 REQUEST_INSTALL_PACKAGES权限
        Uri uri;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.parse(fileUriPath);
        } else {
            File file = new File(fileUriPath.replace("file://", ""));
            if (!file.exists())
                return;
            String authority = "dev.joemi.arch.FileProvider";
            uri = FileProvider.getUriForFile(context, authority, file);
        }
        //intent跳转
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
