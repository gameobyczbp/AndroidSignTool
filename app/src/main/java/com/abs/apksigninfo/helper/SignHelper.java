package com.abs.apksigninfo.helper;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class SignHelper {


    public static String getSHA1Signature(Context context, String pkgName, String algorithm) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(pkgName, PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] publicKey = md.digest(cert);
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            hexString.deleteCharAt(hexString.length() - 1);
            return hexString.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
