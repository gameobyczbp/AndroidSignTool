package com.abs.apksigninfo.interfaces;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;

public class KeywordFilterWatcher implements TextWatcher {
    private Handler filterHandler;
    public static final int MSG_WHAT_FILTER = 100;

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
        Message msg = Message.obtain();
        msg.what = MSG_WHAT_FILTER;
        msg.obj = s.toString();
        filterHandler.sendMessageDelayed(msg, 1000);
    }
}
