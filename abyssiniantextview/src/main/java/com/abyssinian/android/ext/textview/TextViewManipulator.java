package com.abyssinian.android.ext.textview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.abyssinian.android.ext.ViewManipulator;

/**
 * Created by ram.murlidhar on 07-11-2014.
 */
public class TextViewManipulator implements ViewManipulator {
    @Override
    public void manipulateView(View view, String name, Context context,
                               String customAttributes, AttributeSet attrs) {
        if (view instanceof TextView) {

        }
    }
}
