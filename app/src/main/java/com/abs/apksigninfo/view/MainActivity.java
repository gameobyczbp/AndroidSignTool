package com.abs.apksigninfo.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.EditText;

import com.abs.apksigninfo.R;
import com.abs.apksigninfo.bean.ApkInfo;
import com.abs.apksigninfo.helper.ApkHelper;
import com.abs.apksigninfo.interfaces.KeywordFilterWatcher;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";


    private ApkHelper smApkHelper;

    private RecyclerView apkInfoList;
    private ApkInfoListAdapter adapter;
    private List<ApkInfo> dataSet;


    private EditText pkgNameFilter;

    private WeakReference<FilterHandler> weakReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initEnv();
        initView();
        initData();
    }

    private void initEnv() {
        smApkHelper = ApkHelper.newInstance();
        dataSet = new ArrayList<>();
        weakReference = new WeakReference<>(new FilterHandler());
    }

    private void initData() {
        dataSet.clear();
        dataSet.addAll(smApkHelper.loadApkInfo(this));
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        apkInfoList = findViewById(R.id.apk_info_list);
        apkInfoList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        apkInfoList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ApkInfoListAdapter(dataSet);
        apkInfoList.setAdapter(adapter);

        pkgNameFilter = findViewById(R.id.apk_pkg_name_filter);
        pkgNameFilter.addTextChangedListener(new KeywordFilterWatcher(weakReference.get()));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    class FilterHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case KeywordFilterWatcher.MSG_WHAT_FILTER:
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
