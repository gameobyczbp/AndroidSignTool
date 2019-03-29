package com.abs.apksigninfo.helper;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class ClipHelper {

    public static void copy(Context context, String label, String content) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(label, content);
        clipboardManager.setPrimaryClip(clipData);
    }


}
