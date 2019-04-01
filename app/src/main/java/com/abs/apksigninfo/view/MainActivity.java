package com.abs.apksigninfo.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.abs.apksigninfo.R;
import com.abs.apksigninfo.bean.ApkInfo;
import com.abs.apksigninfo.helper.ApkHelper;
import com.abs.apksigninfo.interfaces.ApkInfoChangedListener;
import com.abs.apksigninfo.interfaces.KeywordFilterWatcher;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener, ApkInfoChangedListener {

    private static final String TAG = "MainActivity";

    private ApkHelper smApkHelper;
    private WeakReference<FilterHandler> weakReference;

    private ApkInfoListAdapter adapter;
    private List<ApkInfo> dataSet;

    private EditText pkgNameFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initEnv();
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        smApkHelper.unRegisterListener(this);
    }

    private void initEnv() {
        smApkHelper = ApkHelper.newInstance();
        smApkHelper.registerListener(this);
        dataSet = new ArrayList<>();
        weakReference = new WeakReference<>(new FilterHandler());
    }

    private void initData() {
        ApkHelper.newInstance().loadApkInfo(this);
    }

    private void initView() {
        RecyclerView apkInfoList = findViewById(R.id.apk_info_list);
        apkInfoList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        apkInfoList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ApkInfoListAdapter(dataSet);
        apkInfoList.setAdapter(adapter);

        View clearKeywordView = findViewById(R.id.apk_clear_keyword);
        clearKeywordView.setOnClickListener(this);

        pkgNameFilter = findViewById(R.id.apk_pkg_name_filter);
        pkgNameFilter.addTextChangedListener(new KeywordFilterWatcher(weakReference.get(), clearKeywordView));
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.apk_clear_keyword:
                pkgNameFilter.setText("");
                break;


        }
    }

    @Override
    public void onApkInfoChanged(List<ApkInfo> newApkInfoList) {

        //检测提前输入的keyword
        String keyword = pkgNameFilter.getText().toString();

        dataSet.clear();
        if (TextUtils.isEmpty(keyword)) {
            dataSet.addAll(newApkInfoList);
        } else {
            List<ApkInfo> filterResult = smApkHelper.loadApkInfoByPkgName(keyword);
            dataSet.addAll(filterResult);
        }
        adapter.notifyDataSetChanged();


    }


    class FilterHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case KeywordFilterWatcher.MSG_WHAT_FILTER:
                    if (smApkHelper.getApkInfoDataFromCache().isEmpty())
                        return;
                    String pkgName = (String) msg.obj;
                    List<ApkInfo> filterDataSet = smApkHelper.loadApkInfoByPkgName(pkgName);
                    dataSet.clear();
                    dataSet.addAll(filterDataSet);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
