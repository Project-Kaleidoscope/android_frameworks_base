/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.android.packageinstaller;

import android.annotation.Nullable;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.internal.app.AlertActivity;

import java.io.File;
import java.util.List;

import ink.kscope.packageinstaller.activity.BasePackageInstallerActivity;

/**
 * Finish installation: Return status code to the caller or display "success" UI to user
 */
public class InstallSuccess extends BasePackageInstallerActivity {
    private static final String LOG_TAG = InstallSuccess.class.getSimpleName();

    @Nullable
    private PackageUtil.AppSnippet mAppSnippet;

    @Nullable
    private String mAppPackageName;

    @Nullable
    private Intent mLaunchIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getBooleanExtra(Intent.EXTRA_RETURN_RESULT, false)) {
            // Return result if requested
            Intent result = new Intent();
            result.putExtra(Intent.EXTRA_INSTALL_RESULT, PackageManager.INSTALL_SUCCEEDED);
            setResult(Activity.RESULT_OK, result);
            finish();
        } else {
            Intent intent = getIntent();
            ApplicationInfo appInfo =
                    intent.getParcelableExtra(PackageUtil.INTENT_ATTR_APPLICATION_INFO);
            mAppPackageName = appInfo.packageName;
            Uri packageURI = intent.getData();

            // Set header icon and title
            PackageManager pm = getPackageManager();

            if ("package".equals(packageURI.getScheme())) {
                mAppSnippet = new PackageUtil.AppSnippet(pm.getApplicationLabel(appInfo),
                        pm.getApplicationIcon(appInfo));
            } else {
                File sourceFile = new File(packageURI.getPath());
                mAppSnippet = PackageUtil.getAppSnippet(this, appInfo, sourceFile);
            }

            mLaunchIntent = getPackageManager().getLaunchIntentForPackage(mAppPackageName);

            bindUi();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindUi();
    }

    private void bindUi() {
        if (mAppSnippet == null) {
            return;
        }

        mAppIconView.setImageDrawable(mAppSnippet.icon);
        mAppLabelView.setText(mAppSnippet.label);
        mInstallBtn.setText(R.string.launch);
        mCancelBtn.setText(R.string.done);
        mCancelBtn.setOnClickListener(view -> {
            if (mAppPackageName != null) {
                Log.i(LOG_TAG, "Finished installing " + mAppPackageName);
            }
            finish();
        });
        mInstallTipView.setText(R.string.kscope_install_done);
        mInstallStatusIconView.setImageResource(R.drawable.ic_install_done);
        mInstallStatusIconView.setVisibility(View.VISIBLE);

        // Enable or disable "launch" button
        boolean enabled = false;
        if (mLaunchIntent != null) {
            List<ResolveInfo> list = getPackageManager().queryIntentActivities(mLaunchIntent,
                    0);
            if (list != null && list.size() > 0) {
                enabled = true;
            }
        }

        if (enabled) {
            mInstallBtn.setOnClickListener(view -> {
                try {
                    startActivity(mLaunchIntent);
                } catch (ActivityNotFoundException | SecurityException e) {
                    Log.e(LOG_TAG, "Could not start activity", e);
                }
                finish();
            });
        } else {
            hideInstallBtn();
        }
    }
}
