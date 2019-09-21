package com.affmarble;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AFFConstant {

    private AFFConstant() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_TIP);
    }

    public static final String UNSUPPORTED_OPERATION_EXCEPTION_TIP = "AFF class can't init";

    public static final int MSEC = 1;
    public static final int SEC = 1000;
    public static final int MIN = 60000;
    public static final int HOUR = 3600000;
    public static final int DAY = 86400000;

    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }

}
