package com.abyssinian.android.ext.textview;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.abyssinian.android.ext.AttributeHelperUtils;
import com.abyssinian.android.ext.ViewManipulator;
import com.abyssinian.android.ext.utils.TextJustifyUtils;

/**
 * Created by ram.murlidhar on 07-11-2014.
 */
public class TextViewManipulator implements ViewManipulator {
    @Override
    public void manipulateView(View view, String name, Context context,
                               String customAttributes, AttributeSet attrs) {
        if (view instanceof TextView) {
            String[] customAttributesArr = customAttributes.split(",");

            for (String customAttributeName : customAttributesArr) {
                int mAttributeId = context.getResources().getIdentifier(customAttributeName,
                        "attr", context.getPackageName());
                String value = AttributeHelperUtils.getAttributeValue(context, attrs, mAttributeId);
                // do post processing
                if (value != null) {
                    // simple
                    switch (customAttributeName) {
                        case "underline":
                            if (value.equals("true")) {
                                TextView view1 = (TextView) view;
                                view1.setPaintFlags(view1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                            }
                        break;
                        case "strikethrough":
                            if (value.equals("true")) {
                                TextView view1 = (TextView) view;
                                view1.setPaintFlags(view1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            }
                            break;
                        case "justify":
                            if (value.equals("true")) {
                                final TextView txtView = (TextView) view;
                                txtView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                                    boolean isJustified = false;
                                    @Override
                                    public boolean onPreDraw() {
                                        if(!isJustified) {
                                            TextJustifyUtils.run(txtView, txtView.getWidth());
                                            isJustified = true;
                                        }

                                        return true;
                                    }

                                });
                            }
                            break;
                    }
                }
            }
        }
    }
}
