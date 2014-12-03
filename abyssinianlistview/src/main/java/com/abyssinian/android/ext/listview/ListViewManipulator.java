package com.abyssinian.android.ext.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.abyssinian.android.ext.AttributeHelperUtils;
import com.abyssinian.android.ext.ViewManipulator;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by ram.murlidhar on 01-12-2014.
 */
public class ListViewManipulator implements ViewManipulator {
    @Override
    public void manipulateView(View view, String name, final Context context, String customAttributes, AttributeSet attrs) {
        if (view instanceof ListView) {
            ListView listView = (ListView) view;
            String[] customAttributesArr = customAttributes.split(",");

            for (String customAttributeName : customAttributesArr) {
                int mAttributeId = context.getResources().getIdentifier(customAttributeName,
                        "attr", context.getPackageName());
                final String value = AttributeHelperUtils.getAttributeValue(context, attrs, mAttributeId);
                // do post processing
                if (value != null) {
                    switch (customAttributeName) {
                        case "onloadMore":
                            listView.setOnScrollListener(new EndlessScrollListener() {
                                @Override
                                public void onLoadMore(int page, int totalItemsCount) {
                                    try {
                                        context.getClass().getMethod(value, int.class, int.class).
                                                invoke(context, page, totalItemsCount);
                                    } catch (IllegalAccessException e) {
                                        throw new RuntimeException(e);
                                    } catch (InvocationTargetException e) {
                                        throw new RuntimeException(e);
                                    } catch (NoSuchMethodException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });

                            break;
                    }
                }
            }
        }
    }
}
