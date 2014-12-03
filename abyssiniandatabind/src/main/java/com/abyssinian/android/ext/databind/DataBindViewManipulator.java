package com.abyssinian.android.ext.databind;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.abyssinian.android.ext.AttributeHelperUtils;
import com.abyssinian.android.ext.ViewManipulator;
import com.abyssinian.android.ext.font.mymodule.abyssiniandatabind.R;
import com.bindroid.BindingMode;
import com.bindroid.converters.AdapterConverter;
import com.bindroid.ui.EditTextTextProperty;
import com.bindroid.ui.SpinnerTextProperty;
import com.bindroid.ui.UiBinder;

/**
 * Created by ram.murlidhar on 13-11-2014.
 */
public class DataBindViewManipulator implements ViewManipulator {
    @Override
    public void manipulateView(View view, String name, Context context, String customAttributes, AttributeSet attrs) {
        String[] customAttributesArr = customAttributes.split(",");

        for (String customAttributeName : customAttributesArr) {
            int mAttributeId = context.getResources().getIdentifier(customAttributeName,
                    "attr", context.getPackageName());
            String value = AttributeHelperUtils.getAttributeValue(context, attrs, mAttributeId);
            if (value != null) {
                // simple
                switch (customAttributeName) {
                    case "databindtext": {
                        String intentStr = value.substring(0, value.indexOf('.'));
                        String objPath = value.substring(value.indexOf('.') + 1);
                        Object model = ((Activity) context).getIntent().getSerializableExtra(intentStr);
                        if (view instanceof EditText) {
                            UiBinder.bind(new EditTextTextProperty((EditText) view), model,
                                    objPath, BindingMode.TWO_WAY);
                        } else if (view instanceof TextView) {
                            UiBinder.bind(view, "Text", model, objPath);
                        } else if (view instanceof Spinner) {
                            UiBinder.bind(new SpinnerTextProperty((Spinner) view), model,
                                    objPath, BindingMode.TWO_WAY);
                        }  else if (view instanceof ListView) {
                            UiBinder.bind(view, "Adapter", model, objPath,
                                    new AdapterConverter(BindableTextView.class));
                        }
                        break;
                    }
                    case "databindtvisibility": {
                        String intentStr = value.substring(0, value.indexOf('.'));
                        String objPath = value.substring(value.indexOf('.') + 1);
                        Object model = ((Activity) context).getIntent().getSerializableExtra(intentStr);
                        UiBinder.bind(view, "Visibility", model, objPath);
                        break;
                    }
                }
            }
        }
    }
}
