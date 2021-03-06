/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.service.autofill;

import android.annotation.IntRange;
import android.annotation.NonNull;
import android.annotation.StringDef;

import com.android.internal.util.DataClass;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A result returned from
 * {@link AutofillService#onSavedDatasetsInfoRequest(SavedDatasetsInfoCallback)}.
 */
@DataClass(
        genToString = true,
        genHiddenConstDefs = true,
        genEqualsHashCode = true)
public final class SavedDatasetsInfo {

    /**
     * Any other type of datasets.
     */
    public static final String TYPE_OTHER = "other";

    /**
     * Datasets such as login credentials.
     */
    public static final String TYPE_PASSWORDS = "passwords";

    /**
     * The type of the datasets that this info is about.
     */
    @NonNull
    @Type
    private final String mType;

    /**
     * The number of datasets of {@link #getType() this type} that the user has saved to the
     * service.
     */
    @IntRange(from = 0)
    private final int mCount;


    // Code below generated by codegen v1.0.22.
    //
    // DO NOT MODIFY!
    // CHECKSTYLE:OFF Generated code
    //
    // To regenerate run:
    // $ codegen $ANDROID_BUILD_TOP/frameworks/base/core/java/android/service/autofill/SavedDatasetsInfo.java
    //
    // To exclude the generated code from IntelliJ auto-formatting enable (one-time):
    //   Settings > Editor > Code Style > Formatter Control
    //@formatter:off


    /** @hide */
    @StringDef(prefix = "TYPE_", value = {
        TYPE_OTHER,
        TYPE_PASSWORDS
    })
    @Retention(RetentionPolicy.SOURCE)
    @DataClass.Generated.Member
    public @interface Type {}

    /**
     * Creates a new SavedDatasetsInfo.
     *
     * @param type
     *   The type of the datasets.
     * @param count
     *   The number of datasets of this type that the user has saved to the service.
     */
    @DataClass.Generated.Member
    public SavedDatasetsInfo(
            @NonNull @Type String type,
            @IntRange(from = 0) int count) {
        this.mType = type;

        if (!(java.util.Objects.equals(mType, TYPE_OTHER))
                && !(java.util.Objects.equals(mType, TYPE_PASSWORDS))) {
            throw new java.lang.IllegalArgumentException(
                    "type was " + mType + " but must be one of: "
                            + "TYPE_OTHER(" + TYPE_OTHER + "), "
                            + "TYPE_PASSWORDS(" + TYPE_PASSWORDS + ")");
        }

        com.android.internal.util.AnnotationValidations.validate(
                NonNull.class, null, mType);
        this.mCount = count;
        com.android.internal.util.AnnotationValidations.validate(
                IntRange.class, null, mCount,
                "from", 0);

        // onConstructed(); // You can define this method to get a callback
    }

    /**
     * The type of the datasets.
     */
    @DataClass.Generated.Member
    public @NonNull @Type String getType() {
        return mType;
    }

    /**
     * The number of datasets of this type that the user has saved to the service.
     */
    @DataClass.Generated.Member
    public @IntRange(from = 0) int getCount() {
        return mCount;
    }

    @Override
    @DataClass.Generated.Member
    public String toString() {
        // You can override field toString logic by defining methods like:
        // String fieldNameToString() { ... }

        return "SavedDatasetsInfo { " +
                "type = " + mType + ", " +
                "count = " + mCount +
        " }";
    }

    @Override
    @DataClass.Generated.Member
    public boolean equals(@android.annotation.Nullable Object o) {
        // You can override field equality logic by defining either of the methods like:
        // boolean fieldNameEquals(SavedDatasetsInfo other) { ... }
        // boolean fieldNameEquals(FieldType otherValue) { ... }

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        @SuppressWarnings("unchecked")
        SavedDatasetsInfo that = (SavedDatasetsInfo) o;
        //noinspection PointlessBooleanExpression
        return true
                && java.util.Objects.equals(mType, that.mType)
                && mCount == that.mCount;
    }

    @Override
    @DataClass.Generated.Member
    public int hashCode() {
        // You can override field hashCode logic by defining methods like:
        // int fieldNameHashCode() { ... }

        int _hash = 1;
        _hash = 31 * _hash + java.util.Objects.hashCode(mType);
        _hash = 31 * _hash + mCount;
        return _hash;
    }

    @DataClass.Generated(
            time = 1615325704446L,
            codegenVersion = "1.0.22",
            sourceFile = "frameworks/base/core/java/android/service/autofill/SavedDatasetsInfo.java",
            inputSignatures = "public static final  java.lang.String TYPE_OTHER\npublic static final  java.lang.String TYPE_PASSWORDS\nprivate final @android.annotation.NonNull @android.service.autofill.SavedDatasetsInfo.Type java.lang.String mType\nprivate final @android.annotation.IntRange int mCount\nclass SavedDatasetsInfo extends java.lang.Object implements []\n@com.android.internal.util.DataClass(genToString=true, genHiddenConstDefs=true, genEqualsHashCode=true)")
    @Deprecated
    private void __metadata() {}


    //@formatter:on
    // End of generated code

}
