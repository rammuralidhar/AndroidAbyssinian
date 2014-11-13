package com.abyssinian.android.ext.databind;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.abyssinian.android.ext.AttributeHelperUtils;
import com.abyssinian.android.ext.ViewManipulator;
import com.bindroid.ui.UiBinder;

/**
 * Created by ram.murlidhar on 13-11-2014.
 */
public class DataBindViewManipulator implements ViewManipulator {
    @Override
    public void manipulateView(View view, String name, Context context, String customAttributes, AttributeSet attrs) {
        if (view instanceof TextView) {
            String[] customAttributesArr = customAttributes.split(",");

            for (String customAttributeName : customAttributesArr) {
                int mAttributeId = context.getResources().getIdentifier(customAttributeName,
                        "attr", context.getPackageName());
                String value = AttributeHelperUtils.getAttributeValue(context, attrs, mAttributeId);
                if (value != null) {
                    // simple
                    switch (customAttributeName) {
                        case "databind":
                            String intentStr = value.substring(0, value.indexOf('.'));
                            String objPath = value.substring(value.indexOf('.') + 1);
                            Object model = ((Activity) context).getIntent().getSerializableExtra(intentStr);
                            UiBinder.bind(view, "Text", model, objPath);
                        break;
                    }
                }
            }
        }
    }
}
