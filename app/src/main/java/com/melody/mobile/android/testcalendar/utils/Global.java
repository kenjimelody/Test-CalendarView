package com.melody.mobile.android.testcalendar.utils;

import android.content.Context;

/**
 * Created by Thanisak Piyasaksiri on 9/17/15 AD.
 */
public class Global {

    public static int getStatusBarHeight(Context context) {

        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
