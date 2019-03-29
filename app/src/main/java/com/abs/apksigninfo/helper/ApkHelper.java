package com.abs.apksigninfo.helper;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.abs.apksigninfo.bean.ApkInfo;

import java.util.ArrayList;
import java.util.List;

public class ApkHelper {
    private static final String TAG = "ApkHelper";

    private List<ApkInfo> dataSetCache;
    private static ApkHelper smApkHelper;

    private ApkHelper() {
        dataSetCache = new ArrayList<>();
    }

    public static ApkHelper newInstance() {
        if (smApkHelper == null) {
            smApkHelper = new ApkHelper();
        }
        return smApkHelper;
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


        dataSetCache.clear();
        dataSetCache.addAll(result);
        return result;
    }

    public List<ApkInfo> loadApkInfoByPkgName(String pkgNameOrAppName) {
        if (TextUtils.isEmpty(pkgNameOrAppName)) {
            return dataSetCache;
        }
        pkgNameOrAppName = pkgNameOrAppName.trim();
        List<ApkInfo> result = new ArrayList<>();
        for (ApkInfo each : dataSetCache) {
            if (each.pkgName.toLowerCase().contains(pkgNameOrAppName.toLowerCase())) {//先检测包名忽略大写
                result.add(each);
            } else if (each.appName.contains(pkgNameOrAppName)) {//检测应用名
                result.add(each);
            }
        }
        return result;
    }
}
