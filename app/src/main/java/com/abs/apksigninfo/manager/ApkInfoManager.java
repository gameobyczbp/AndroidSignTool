package com.abs.apksigninfo.manager;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.abs.apksigninfo.bean.ApkInfo;
import com.abs.apksigninfo.helper.SignHelper;

import java.util.ArrayList;
import java.util.List;

public class ApkInfoManager {
    private static ApkInfoManager smApkInfoManager;

    private List<ApkInfo> apkInfoCache;

    private ApkInfoManager() {
        apkInfoCache = new ArrayList<>();
    }

    public static ApkInfoManager newInstance() {
        if (smApkInfoManager == null)
            smApkInfoManager = new ApkInfoManager();
        return smApkInfoManager;
    }


    public List<ApkInfo> loadApkInfo(Context context) {
        PackageManager manager = context.getPackageManager();
        List<PackageInfo> packageInfos = manager.getInstalledPackages(0);
        List<ApkInfo> result = new ArrayList<>();

        for (PackageInfo packageInfo : packageInfos) {
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {//非系统应用
                String pkgName = packageInfo.packageName;
                String versionName = packageInfo.versionName;
                String versionCode;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                    versionCode = "" + packageInfo.getLongVersionCode();
                } else {
                    versionCode = "" + packageInfo.versionCode;
                }
                String appName = packageInfo.applicationInfo.loadLabel(manager).toString();
                Drawable icon = packageInfo.applicationInfo.loadIcon(manager);

                String sha1 = SignHelper.getSHA1Signature(context, pkgName, "sha1");
                String md5 = SignHelper.getSHA1Signature(context, pkgName, "md5");
                String sha256 = SignHelper.getSHA1Signature(context, pkgName, "sha256");


                ApkInfo item = new ApkInfo(icon, appName, pkgName, md5, sha1, sha256, versionName, versionCode);
                result.add(item);
            }
        }


        apkInfoCache.clear();
        apkInfoCache.addAll(result);

        return apkInfoCache;

    }


    public List<ApkInfo> loadDataFromCache() {
        return apkInfoCache;
    }
}
