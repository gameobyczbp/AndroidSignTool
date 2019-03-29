package com.abs.apksigninfo.interfaces;

import android.text.TextUtils;
import android.view.View;

import com.abs.apksigninfo.R;
import com.abs.apksigninfo.app.App;
import com.abs.apksigninfo.bean.ApkInfo;
import com.abs.apksigninfo.helper.ClipHelper;
import com.abs.apksigninfo.manager.ToastManager;

public class CopyClickListener implements View.OnLongClickListener {
    private ApkInfo apkInfo;

    public CopyClickListener(ApkInfo apkInfo) {
        this.apkInfo = apkInfo;
    }


    @Override
    public boolean onLongClick(View v) {
        int id = v.getId();
        String labelName;
        String content;
        if (id == R.id.apk_md5) {//复制md5
            labelName = "md5";
            content = apkInfo.md5;
        } else if (id == R.id.apk_name) {//复制包名
            labelName = "pkgName";
            content = apkInfo.pkgName;
        } else if (id == R.id.apk_sha1) {//复制sha1
            labelName = "sha1";
            content = apkInfo.sha1;
        } else if (id == R.id.apk_sha256) {//复制sha256
            labelName = "sha256";
            content = apkInfo.sha256;
        } else if (id == R.id.apk_logo) {//复制全部内容
            labelName = "apk全部信息";
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("应用名 = %s", apkInfo.appName));
            sb.append("\n\r");
            sb.append(String.format("包名 = %s", apkInfo.pkgName));
            sb.append("\n\r");
            sb.append(String.format("版本名 = %s", apkInfo.versionName));
            sb.append("\n\r");
            sb.append(String.format("版本号 = %s", apkInfo.versionCode));
            sb.append("\n\r");
            sb.append(String.format("MD5 = %s", apkInfo.md5));
            sb.append("\n\r");
            sb.append(String.format("sha1 = %s", apkInfo.sha1));
            sb.append("\n\r");
            sb.append(String.format("sha256 = %s", apkInfo.sha256));

            content = sb.toString();
        } else {
            labelName = "";
            content = "";
        }
        if (TextUtils.isEmpty(labelName)
                || TextUtils.isEmpty(content)) {
            ToastManager.s("复制内容出错!");
            return false;
        }
        ClipHelper.copy(App.getInstance(), labelName, content);
        ToastManager.s(String.format("%s已经复制到剪切板:%s", labelName, content));
        return true;
    }
}
