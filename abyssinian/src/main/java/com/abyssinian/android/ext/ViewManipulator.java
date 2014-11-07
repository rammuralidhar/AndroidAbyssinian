package com.abyssinian.android.ext;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public interface ViewManipulator {
    void manipulateView(View view, String name, Context context, String customAttributes, AttributeSet attrs);
}
