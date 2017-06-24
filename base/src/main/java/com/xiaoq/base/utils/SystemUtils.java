package com.xiaoq.base.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Locale;

public class SystemUtils {
    public static String getPackName(Context context) {
        try {
            String pkName = context.getPackageName();
            String versionName = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            return pkName + "-" + versionName;
        } catch (Exception e) {
        }
        return null;
    }

    public static int getVersionCode(Context context) {
        try {
            String pkName = context.getPackageName();

            int versionCode = context.getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            return versionCode;
        } catch (Exception e) {
        }
        return -1;
    }

    public static String getVersionName(Context context) {
        try {
            String pkName = context.getPackageName();

            String versionName = context.getPackageManager()
                    .getPackageInfo(pkName, 0).versionName;
            return versionName;
        } catch (Exception e) {
        }
        return null;
    }


    /**
     * 隐藏软键盘
     *
     * @param context
     * @param v
     */
    public static void hideKeyBoard(Context context, View v) {
        if (context == null || v == null) {
            return;
        }
        /*隐藏软键盘*/
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    /**
     * 检测GPS是否开启
     *
     * @param context
     * @return true 打开状态;  false 关闭状态
     */
    public static boolean isOpenGps(final Activity context) {
        LocationManager alm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (alm.isProviderEnabled(LocationManager.GPS_PROVIDER)) { // GPS模块正常
            return true;
        } else {
            return false;
        }
    }

    /**
     * GPS为关闭状态的时候,打开它
     *
     * @param context
     */
    public static void openGps(final Activity context) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setMessage("为了你的正常使用,自助平台需要你开启GPS定位功能");
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToastUtils.show(context, "请开启GPS");
            }
        });
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "去开启", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 跳转到定位的设置界面
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivityForResult(intent, 0); // 此为设置完成后返回到获取界面
            }
        });
        dialog.show();

        /*可以自定义按钮的颜色,大小*/
//        Button btnPositive = dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE);
//        Button btnNegative = dialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE);
//        btnNegative.setTextColor(Color.GREEN);
//        btnPositive.setTextColor(Color.GREEN);
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }


}
