package com.abyssinian.android.ext.ninepatch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.abyssinian.android.ext.AttributeHelperUtils;
import com.abyssinian.android.ext.ViewManipulator;

import java.io.IOException;
import java.io.InputStream;

import ua.anatolii.graphics.ninepatch.NinePatchChunk;

/**
 * Created by ram.murlidhar on 05-01-2015.
 */
public class NinePatchViewManipulator implements ViewManipulator {
    private static final int DENSITY_XHDPI = 320;
    private static final int DENSITY_XXHIGH = 480;
    private static final int DENSITY_XXXHIGH = 640;

    @Override
    public void manipulateView(View view, String name, final Context context, String customAttributes, AttributeSet attrs) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            String[] customAttributesArr = customAttributes.split(",");

            for (String customAttributeName : customAttributesArr) {
                int mAttributeId = context.getResources().getIdentifier(customAttributeName,
                        "attr", context.getPackageName());
                final String value = AttributeHelperUtils.getAttributeValue(context, attrs, mAttributeId);

                // do post processing
                if (value != null) {
                    int densityHint = getDensityHint(context, attrs, mAttributeId);
                    switch (customAttributeName) {
                        case "src_assets":
                            displayImage(context, imageView, value, densityHint);
                            break;
                        case "background_assets":
                            displayImageBg(context, imageView, value, densityHint);
                            break;
                    }
                }
            }
        }
    }

    private void displayImageBg(Context context, ImageView imageView, String value, int densityHint) {
        InputStream is = null;
        try {
            is = context.getAssets().open(value);
            Rect padding = new Rect();
            Bitmap bitmap = BitmapFactory.decodeStream(is);

            if (NinePatchChunk.isRawNinePatchBitmap(bitmap)) {
                Drawable drawable = NinePatchChunk.create9PatchDrawable(
                        imageView.getContext().getApplicationContext(),
                        is, DENSITY_XHDPI, value);
                imageView.setBackgroundDrawable(drawable);
            } else {
                imageView.setBackgroundDrawable(new BitmapDrawable(context.getResources(), bitmap));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
        }
    }

    private void displayImage(Context context, ImageView imageView, String value, int densityHint) {
        InputStream is = null;
        try {
            is = context.getAssets().open(value);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            if (densityHint != -1) {
                opts.inScaled = true;
                opts.inDensity = densityHint;
                opts.inTargetDensity = context.getResources().getDisplayMetrics().densityDpi;
            } else {
                opts = null;
            }
            Rect padding = new Rect();
            Bitmap bitmap = BitmapFactory.decodeStream(is, padding, opts);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }

    private int getDensityHint(Context context, AttributeSet attrs, int mAttributeId) {
        // whether ldpi/hdpi/xhdpi
        int densityHintId = context.getResources().getIdentifier("density_hint",
                "attr", context.getPackageName());
        final String densityHint = AttributeHelperUtils.getAttributeValue(context, attrs, mAttributeId);
        switch (densityHint) {
            case "ldpi":
                return DisplayMetrics.DENSITY_LOW;
            case "mdpi":
                return DisplayMetrics.DENSITY_MEDIUM;
            case "hdpi":
                return DisplayMetrics.DENSITY_HIGH;
            case "xdpi":
                return DENSITY_XHDPI;
            case "xxdpi":
                return DENSITY_XXHIGH;
            case "xxxdpi":
                return DENSITY_XXXHIGH;
            default:
                return -1;
        }
    }
}