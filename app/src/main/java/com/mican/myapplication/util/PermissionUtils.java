package com.mican.myapplication.util;

import android.Manifest;
import android.app.Activity;
import android.os.StrictMode;


import com.mican.myapplication.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @name lzq
 * @class name：com.mican.baselibrary.util
 * @class describe
 * @time 2020/7/1 2:54 PM
 * @change
 * @chang
 * @class describe
 */
public class PermissionUtils {

    public static final int
            W = 10,
            CAMERA = 11,
            CALL_PHONE = 12,
            V = 13,
            MV = 14,
            VID = 15,
            LOCATION = 16,
            SMS = 17;
    Activity context;
    String[] perms_phone = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE};
    PermissionCallBack permissionCallBack;
    private String[] perms_w = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private String[] perms_camera = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private String[] perms_mv = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    private String[] perms_v = {
            Manifest.permission.RECORD_AUDIO,
    };
    private String[] perms_vid = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private String[] perms_loacation = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
    };
    private String[] perms_sms = {
            Manifest.permission.READ_SMS,
    };
    private Fragment fragment;
    private int AskCarrier = 0;
    private int permissionType;
    public PermissionUtils(Activity context) {
        this.context = context;
        AskCarrier = 0;

    }

    public PermissionUtils(Fragment fragment) {
        this.context = fragment.getActivity();
        this.fragment = fragment;
        AskCarrier = 1;

    }

    public void askPermission(int permisType, PermissionCallBack permissionCallBack) {
        String tip = null;
        this.permissionType = permisType;
        this.permissionCallBack = permissionCallBack;
        if (hasPermissions(permisType)) {
            permissionCallBack.callback(permisType);
        } else {
            switch (permisType) {
                case W:
                    initPhotoError();
                    tip = context.getString(R.string.call_phone_permission);
                    if (AskCarrier == 1 && fragment != null) {
                        EasyPermissions.requestPermissions(fragment, tip, permisType, perms_w);
                    } else {
                        EasyPermissions.requestPermissions(context, tip, permisType, perms_w);
                    }
                    break;
                case CAMERA:
                    initPhotoError();
                    tip = context.getString(R.string.camera_permission);
                    if (AskCarrier == 1 && fragment != null) {
                        EasyPermissions.requestPermissions(fragment, tip, permisType, perms_camera);
                    } else {
                        EasyPermissions.requestPermissions(context, tip, permisType, perms_camera);
                    }

                    break;
                case CALL_PHONE:
                    tip = context.getString(R.string.call_phone_permission);
                    if (AskCarrier == 1 && fragment != null) {
                        EasyPermissions.requestPermissions(fragment, tip, permisType, perms_phone);
                    } else {
                        EasyPermissions.requestPermissions(context, tip, permisType, perms_phone);
                    }
                    break;
                case V:
                    tip = context.getString(R.string.call_phone_permission);
                    if (AskCarrier == 1 && fragment != null) {
                        EasyPermissions.requestPermissions(fragment, tip, permisType, perms_v);
                    } else {
                        EasyPermissions.requestPermissions(context, tip, permisType, perms_v);
                    }
                    break;
                case MV:
                    tip = context.getString(R.string.call_phone_permission);
                    if (AskCarrier == 1 && fragment != null) {
                        EasyPermissions.requestPermissions(fragment, tip, permisType, perms_mv);
                    } else {
                        EasyPermissions.requestPermissions(context, tip, permisType, perms_mv);
                    }
                    break;
                case VID:
                    tip = context.getString(R.string.call_phone_permission);
                    if (AskCarrier == 1 && fragment != null) {
                        EasyPermissions.requestPermissions(fragment, tip, permisType, perms_vid);
                    } else {
                        EasyPermissions.requestPermissions(context, tip, permisType, perms_vid);
                    }
                    break;
                case LOCATION:
                    tip = context.getString(R.string.location_permission);
                    if (AskCarrier == 1 && fragment != null) {
                        EasyPermissions.requestPermissions(fragment, tip, permisType, perms_loacation);
                    } else {
                        EasyPermissions.requestPermissions(context, tip, permisType, perms_loacation);
                    }

                case SMS:
                    tip = context.getString(R.string.location_permission);
                    if (AskCarrier == 1 && fragment != null) {
                        EasyPermissions.requestPermissions(fragment, tip, permisType, perms_sms);
                    } else {
                        EasyPermissions.requestPermissions(context, tip, permisType, perms_sms);
                    }
            }

        }
        ;
    }


    private boolean hasPermissions(int type) {
        switch (type) {
            case W:
                return EasyPermissions.hasPermissions(context, perms_w);
            case CAMERA:
                return EasyPermissions.hasPermissions(context, perms_camera);
            case CALL_PHONE:
                return EasyPermissions.hasPermissions(context, perms_phone);
            case V:
                return EasyPermissions.hasPermissions(context, perms_v);
            case MV:
                return EasyPermissions.hasPermissions(context, perms_mv);
            case VID:
                return EasyPermissions.hasPermissions(context, perms_vid);
            case LOCATION:
                return EasyPermissions.hasPermissions(context, perms_loacation);
            case SMS:
                return EasyPermissions.hasPermissions(context, perms_sms);
        }
        return false;
    }

    /**
     * 申请成功
     * @param requestCode
     * @param perms
     */
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (permissionCallBack == null) return;
        if(hasPermissions(requestCode)){
            LogUtils.e("onPermissionsGranted"+requestCode);
            permissionCallBack.callback(permissionType);
        }
    }

    public void onPermissionDenied(int requestCode, @NonNull List<String> perms) {
        LogUtils.e("onPermissionDenied"+requestCode);
        /**
         * 若是在权限弹窗中，用户勾选了'NEVER ASK AGAIN.'或者'不在提示'，且拒绝权限。
         * 这时候，需要跳转到设置界面去，让用户手动开启。
         *
         * */
        if (EasyPermissions.somePermissionPermanentlyDenied(context, perms)) {
            new AppSettingsDialog.Builder(context)
                    .setTitle("权限通知")
                    .setRationale("需要开启相关权限，去开启权限？")
                    .build().show();
        }
    }

    private void initPhotoError() {
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    ;

    public interface PermissionCallBack {
        void callback(int type);
    }


}
