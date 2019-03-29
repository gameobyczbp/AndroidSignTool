package com.abs.apksigninfo.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abs.apksigninfo.R;
import com.abs.apksigninfo.bean.ApkInfo;
import com.abs.apksigninfo.interfaces.CopyClickListener;

import java.util.List;

public class ApkInfoListAdapter extends RecyclerView.Adapter<ApkInfoListAdapter.ApkInfoViewHolder> {

    private List<ApkInfo> dataSet;

    public ApkInfoListAdapter(List<ApkInfo> dataSet) {
        this.dataSet = dataSet;
    }


    @NonNull
    @Override
    public ApkInfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_apk_info_layout, viewGroup, false);
        return new ApkInfoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ApkInfoViewHolder apkInfoViewHolder, int position) {
        ApkInfo apkInfo = dataSet.get(position);
        CopyClickListener listener = new CopyClickListener(apkInfo);
        apkInfoViewHolder.apkLogo.setImageDrawable(apkInfo.logo);
        apkInfoViewHolder.apkLogo.setOnLongClickListener(listener);
        apkInfoViewHolder.apkName.setText(String.format("%s(%s)", apkInfo.appName, apkInfo.pkgName));
        apkInfoViewHolder.apkName.setOnLongClickListener(listener);
        apkInfoViewHolder.apkPkgCode.setText(String.format("versionName:%s,versionCode:%s", apkInfo.versionName, apkInfo.versionCode));
        apkInfoViewHolder.apkMD5.setText(String.format("MD5->%s", apkInfo.md5));
        apkInfoViewHolder.apkMD5.setOnLongClickListener(listener);
        apkInfoViewHolder.apkSHA1.setText(String.format("sha1->%s", apkInfo.sha1));
        apkInfoViewHolder.apkSHA1.setOnLongClickListener(listener);
        apkInfoViewHolder.apkSHA256.setText(String.format("sha256->%s", apkInfo.sha256));
        apkInfoViewHolder.apkSHA256.setOnLongClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }


    class ApkInfoViewHolder extends RecyclerView.ViewHolder {

        ImageView apkLogo;
        TextView apkName;
        TextView apkPkgCode;
        TextView apkMD5;
        TextView apkSHA1;
        TextView apkSHA256;

        public ApkInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            apkLogo = itemView.findViewById(R.id.apk_logo);
            apkName = itemView.findViewById(R.id.apk_name);
            apkPkgCode = itemView.findViewById(R.id.apk_pkg_code);
            apkMD5 = itemView.findViewById(R.id.apk_md5);
            apkSHA1 = itemView.findViewById(R.id.apk_sha1);
            apkSHA256 = itemView.findViewById(R.id.apk_sha256);
        }


    }
}
