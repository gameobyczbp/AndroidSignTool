package com.abs.apksigninfo.interfaces;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

public class KeywordFilterWatcher implements TextWatcher {
    private Handler filterHandler;
    public static final int MSG_WHAT_FILTER = 100;

    private View clearKeywordView;

    public KeywordFilterWatcher(Handler filterHandler, View clearKeywordView) {
        this.filterHandler = filterHandler;
        this.clearKeywordView = clearKeywordView;
    }

    public KeywordFilterWatcher(Handler filterHandler) {
        this.filterHandler = filterHandler;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        filterHandler.removeMessages(MSG_WHAT_FILTER);
    }

    @Override
    public void afterTextChanged(Editable s) {
        String keyword = s.toString();
        Message msg = Message.obtain();
        msg.what = MSG_WHAT_FILTER;
        msg.obj = keyword;
        if (TextUtils.isEmpty(keyword)) {
            filterHandler.sendMessage(msg);
        } else {
            filterHandler.sendMessageDelayed(msg, 1000);
        }

        if (clearKeywordView != null) {
            clearKeywordView.setVisibility(TextUtils.isEmpty(keyword) ? View.GONE : View.VISIBLE);
        }

    }
}
