/*
 * Copyright (C) 2022 Project Kaleidoscope
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

package ink.kscope.packageinstaller.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.packageinstaller.R;

public class BasePackageInstallerActivity extends AppCompatActivity {

    // Views in layout
    protected ImageView mAppIconView;
    protected TextView mAppLabelView;
    protected TextView mInstallTipView;
    protected Button mCancelBtn;
    protected Button mInstallBtn;
    protected Space mSpace;
    protected ImageView mInstallStatusIconView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kscope_install_main);
        initView();
    }

    private void initView() {
        mAppIconView = findViewById(R.id.app_icon);
        mAppLabelView = findViewById(R.id.app_label);
        mInstallTipView = findViewById(R.id.install_tip);
        mCancelBtn = findViewById(R.id.installer_cancel);
        mInstallBtn = findViewById(R.id.installer_install);
        mSpace = findViewById(R.id.button_space);
        mInstallStatusIconView = findViewById(R.id.install_status_icon);
    }

    protected void hideInstallBtn() {
        ViewGroup.LayoutParams mCancelBtnLayoutParams = mCancelBtn.getLayoutParams();
        mCancelBtnLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mCancelBtn.setLayoutParams(mCancelBtnLayoutParams);
        mInstallBtn.setVisibility(View.GONE);
        mSpace.setVisibility(View.GONE);
    }

    protected void hideCancelBtn() {
        ViewGroup.LayoutParams mInstallBtnLayoutParams = mInstallBtn.getLayoutParams();
        mInstallBtnLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mInstallBtn.setLayoutParams(mInstallBtnLayoutParams);
        mCancelBtn.setVisibility(View.GONE);
        mSpace.setVisibility(View.GONE);
    }
}
