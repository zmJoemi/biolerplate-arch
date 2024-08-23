package dev.joemi.arch.widgets.updater.entity;

import java.io.Serializable;

/**
 * Created by Joemi on 8/1/22.
 * Do one thing at a time, and do well.
 */

public class VersionConfig implements Serializable {

    //===========升级的信息=============//
    /**
     * 版本号
     */
    private int versionCode;
    /**
     * 版本名称
     */
    private String versionName;

    /**
     * 更新内容
     */
    private String updateContent;

    //===========下载的信息=============//
    /**
     * 下载地址
     */
    private String downloadUrl;

    /**
     * 下载文件的加密值，用于校验，防止下载的apk文件被替换
     */
    private String md5;

    /**
     * 下载文件的大小【单位：KB】
     */
    private float size;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }
}
