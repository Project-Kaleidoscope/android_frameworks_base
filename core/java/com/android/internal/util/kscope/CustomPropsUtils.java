/*
 * Copyright (C) 2020 The Pixel Experience Project
 *               2022 Project Kaleidoscope
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.internal.util.kscope;

import android.os.Build;
import android.os.SystemProperties;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CustomPropsUtils {

    public static final String PACKAGE_GMS = "com.google.android.gms";

    private static final String SPOOF_MUSIC_APPS = "persist.sys.disguise_props_for_music_app";

    private static final String TAG = CustomPropsUtils.class.getSimpleName();
    private static final boolean DEBUG = false;

    private static final Map<String, Object> propsToChangePixel6;
    private static final String[] packagesToChangePixel6 = {
            "com.google.android.gms",
            "com.google.android.gsf",
            "com.google.android.inputmethod.latin"
    };

    private static final Map<String, Object> propsToChangePixelXL;
    private static final String[] packagesToChangePixelXL = {
            "com.google.android.apps.photos"
    };

    private static final Map<String, Object> propsToChangeMeizu;
    private static final String[] packagesToChangeMeizu = {
            "com.netease.cloudmusic",
            "com.tencent.qqmusic",
            "com.kugou.android",
            "com.kugou.android.lite",
            "cmccwm.mobilemusic",
            "cn.kuwo.player",
            "com.meizu.media.music"
    };

    private static volatile boolean sIsGms = false;

    static {
        propsToChangePixel6 = new HashMap<>();
        propsToChangePixel6.put("BRAND", "google");
        propsToChangePixel6.put("MANUFACTURER", "Google");
        propsToChangePixel6.put("DEVICE", "raven");
        propsToChangePixel6.put("PRODUCT", "raven");
        propsToChangePixel6.put("MODEL", "Pixel 6 Pro");
        propsToChangePixel6.put(
            "FINGERPRINT", "google/raven/raven:13/TP1A.220905.004/8927612:user/release-keys");
        propsToChangePixelXL = new HashMap<>();
        propsToChangePixelXL.put("BRAND", "google");
        propsToChangePixelXL.put("MANUFACTURER", "Google");
        propsToChangePixelXL.put("DEVICE", "marlin");
        propsToChangePixelXL.put("PRODUCT", "marlin");
        propsToChangePixelXL.put("MODEL", "Pixel XL");
        propsToChangePixelXL.put(
            "FINGERPRINT", "google/marlin/marlin:10/QP1A.191005.007.A3/5972272:user/release-keys");
        propsToChangeMeizu = new HashMap<>();
        propsToChangeMeizu.put("BRAND", "meizu");
        propsToChangeMeizu.put("MANUFACTURER", "Meizu");
        propsToChangeMeizu.put("DEVICE", "m1892");
        propsToChangeMeizu.put("DISPLAY", "Flyme");
        propsToChangeMeizu.put("PRODUCT", "meizu_16thPlus_CN");
        propsToChangeMeizu.put("MODEL", "meizu 16th Plus");
    }

    public static void setProps(String packageName) {
        if (packageName == null) {
            return;
        }
        if (packageName.equals(PACKAGE_GMS)) {
            sIsGms = true;
        }
        if (Arrays.asList(packagesToChangePixel6).contains(packageName)) {
            if (DEBUG) Log.d(TAG, "Defining props for: " + packageName);
            for (Map.Entry<String, Object> prop : propsToChangePixel6.entrySet()) {
                setPropValue(prop.getKey(), prop.getValue());
            }
        }
        if (Arrays.asList(packagesToChangePixelXL).contains(packageName)) {
            if (DEBUG) Log.d(TAG, "Defining props for: " + packageName);
            for (Map.Entry<String, Object> prop : propsToChangePixelXL.entrySet()) {
                setPropValue(prop.getKey(), prop.getValue());
            }
        }
        if ((SystemProperties.getBoolean(SPOOF_MUSIC_APPS, false)) &&
            (Arrays.asList(packagesToChangeMeizu).contains(packageName))) {
            if (DEBUG) Log.d(TAG, "Defining props for: " + packageName);
            for (Map.Entry<String, Object> prop : propsToChangeMeizu.entrySet()) {
                setPropValue(prop.getKey(), prop.getValue());
            }
        }
    }

    private static void setPropValue(String key, Object value) {
        try {
            if (DEBUG) Log.d(TAG, "Defining prop " + key + " to " + value.toString());
            Field field = Build.class.getDeclaredField(key);
            field.setAccessible(true);
            field.set(null, value);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Log.e(TAG, "Failed to set prop " + key, e);
        }
    }

    private static boolean isCallerSafetyNet() {
        return Arrays.stream(Thread.currentThread().getStackTrace())
                .anyMatch(elem -> elem.getClassName().contains("DroidGuard"));
    }

    public static void onEngineGetCertificateChain() {
        // Check stack for SafetyNet
        if (sIsGms && isCallerSafetyNet()) {
            throw new UnsupportedOperationException();
        }
    }
}
