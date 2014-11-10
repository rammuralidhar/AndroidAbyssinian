package com.abyssinian.android.ext;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * Created by ram.murlidhar on 07-11-2014.
 */
public class AttributeHelperUtils {
    /**
     * Tries to pull the Custom Attribute directly from the View.
     *
     * @param context     Activity Context
     * @param attrs       View Attributes
     * @param attributeId if -1 returns null.
     * @return null if attribute is not defined or added to View
     */
    public static String getAttributeValue(Context context, AttributeSet attrs, int attributeId) {
        if (attributeId == -1)
            return null;
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, new int[]{attributeId});
        if (typedArray != null) {
            try {
                // First defined attribute
                String fontFromAttribute = typedArray.getString(0);
                if (!TextUtils.isEmpty(fontFromAttribute)) {
                    return fontFromAttribute;
                }
            } catch (Exception ignore) {
                // Failed for some reason.
            } finally {
                typedArray.recycle();
            }
        }
        return null;
    }
}
