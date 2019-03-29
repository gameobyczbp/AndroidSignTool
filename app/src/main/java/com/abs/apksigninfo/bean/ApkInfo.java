package com.abs.apksigninfo.bean;

import android.graphics.drawable.Drawable;

public class ApkInfo {


    public ApkInfo(Drawable logo, String appName, String pkgName, String md5, String sha1, String sha256, String versionName, String versionCode) {
        this.logo = logo;
        this.appName = appName;
        this.pkgName = pkgName;
        this.md5 = md5;
        this.sha1 = sha1;
        this.sha256 = sha256;

        this.versionName = versionName;
        this.versionCode = versionCode;


    }

    public Drawable logo;
    public String appName;
    public String pkgName;


    public String md5;
    public String sha1;
    public String sha256;


    public String versionCode;
    public String versionName;


    @Override
    public String toString() {
        return "ApkInfo{" +
                ", appName='" + appName + '\'' +
                ", pkgName='" + pkgName + '\'' +
                '}';
    }
}
