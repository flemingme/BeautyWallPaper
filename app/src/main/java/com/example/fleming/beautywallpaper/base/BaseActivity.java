package com.example.fleming.beautywallpaper.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseActivity
 * Created by fleming on 17-4-11.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final int PERMISSION_REQ_CODE = 100;

    //是否跳转过应用程序信息详情页
    private boolean mIsJump2Settings = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(addLayout());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsJump2Settings) {
            onRecheckPermission();
            mIsJump2Settings = false;
        }
    }
    //单个权限的检查
    public void checkPermission(@NonNull final String permission, @Nullable String reason) {
        if (Build.VERSION.SDK_INT < 23) return;
        int permissionCheck = ContextCompat.checkSelfPermission(this, permission);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            //权限已经申请
            onPermissionGranted(permission);

        } else {
            if (!TextUtils.isEmpty(reason)) {
                //判断用户先前是否拒绝过该权限申请，如果为true，我们可以向用户解释为什么使用该权限
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    //这里的dialog可以自定义
                    new AlertDialog.Builder(this).setCancelable(false).setTitle("温馨提示").setMessage(reason).
                            setNegativeButton("我知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermission(new String[]{permission});
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    requestPermission(new String[]{permission});
                }
            } else {
                requestPermission(new String[]{permission});
            }

        }
    }
    //多个权限的检查
    public void checkPermissions(@NonNull String... permissions) {
        if (Build.VERSION.SDK_INT < 23) return;
        //用于记录权限申请被拒绝的权限集合
        List<String> permissionDeniedList = new ArrayList<>();
        for (String permission : permissions) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, permission);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted(permission);
            } else {
                permissionDeniedList.add(permission);
            }
        }
        if (!permissionDeniedList.isEmpty()) {
            String[] deniedPermissions = permissionDeniedList.toArray(new String[permissionDeniedList.size()]);
            requestPermission(deniedPermissions);
        }
    }

    //调用系统API完成权限申请
    private void requestPermission(String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQ_CODE);
    }

    //申请权限被允许的回调
    public void onPermissionGranted(String permission) {

    }
    //申请权限被拒绝的回调
    public void onPermissionDenied(String permission) {

    }
    //申请权限的失败的回调
    public void onPermissionFailure() {

    }

    //如果从设置界面返回，则重新申请权限
    public void onRecheckPermission() {

    }

    //弹出系统权限询问对话框，用户交互后的结果回调
    @Override
    public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQ_CODE:
                if (grantResults.length > 0) {
                    //用于记录是否有权限申请被拒绝的标记
                    boolean isDenied = false;
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            onPermissionGranted(permissions[i]);
                        } else {
                            isDenied = true;
                            onPermissionDenied(permissions[i]);
                        }
                    }
                    if (isDenied) {
                        isDenied = false;
                        //如果有权限申请被拒绝，则弹出对话框提示用户去修改权限设置。
                        showPermissionSettingsDialog();
                    }

                } else {
                    onPermissionFailure();
                }
                break;
        }
    }

    private void showPermissionSettingsDialog() {
        new AlertDialog.Builder(this).setCancelable(false).setTitle("温馨提示").
                setMessage("缺少必要权限\n不然将导致部分功能无法正常使用").setNegativeButton("下次吧", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                jump2PermissionSettings();
            }
        }).show();
    }

    /**
     * 跳转到应用程序信息详情页面
     */
    private void jump2PermissionSettings() {
        mIsJump2Settings = true;
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    protected abstract int addLayout();

    public void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void toast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }
}
