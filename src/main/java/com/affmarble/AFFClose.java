package com.affmarble;

import java.io.Closeable;
import java.io.IOException;

public class AFFClose {

    private AFFClose() {
        throw new UnsupportedOperationException(AFFConstant.UNSUPPORTED_OPERATION_EXCEPTION_TIP);
    }

    public static void closeIO(final Closeable closeable) {

        if (closeable == null) {
            return;
        }

        try {
            closeable.close();
        } catch (IOException e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }

    }
}
