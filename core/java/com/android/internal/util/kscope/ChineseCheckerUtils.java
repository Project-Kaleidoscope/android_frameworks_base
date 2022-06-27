package com.android.internal.util.kscope;

import java.util.Locale;

public class ChineseCheckerUtils {

    public static boolean isSupportLanguage(boolean excludeSAR) {
        Locale locale = Locale.getDefault();
        if (locale.getLanguage().startsWith(Locale.CHINESE.getLanguage())) {
            if (excludeSAR) {
                return locale.getCountry().equals("CN");
            } else {
                return !locale.getCountry().equals("SG");
            }
        } else {
            return false;
        }
    }
}
