package com.abs.apksigninfo.interfaces;

import com.abs.apksigninfo.bean.ApkInfo;

import java.util.List;

public interface ApkInfoChangedListener {

    void onApkInfoChanged(List<ApkInfo> newApkInfoList);
}
