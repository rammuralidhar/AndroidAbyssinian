package com.abyssinian.android.ext.databind;
import java.util.Date;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bindroid.BindingMode;
import com.bindroid.converters.ToStringConverter;
import com.bindroid.trackable.TrackableField;
import com.bindroid.ui.BoundUi;
import com.bindroid.ui.HasView;
import com.bindroid.ui.UiBinder;
import com.bindroid.ui.UiProperty;
import com.bindroid.utils.ReflectedProperty;

/**
 * Created by ram.murlidhar on 02-12-2014.
 */
public class BindableTextView extends TextView implements BoundUi<String>, HasView {
    private TrackableField<String> data = new TrackableField<String>();

    public BindableTextView(Context context) {
        super(context);
        UiBinder.bind(UiProperty.make(new ReflectedProperty(this, "Text")), this, "Text",
                BindingMode.ONE_WAY);
    }

    @Override
    public void bind(String dataSource) {
        data.set(dataSource);
    }

    public String getText() {
        return data.get();
    }

    @Override
    public View getView() {
        return this;
    }
}
