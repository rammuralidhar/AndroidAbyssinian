package com.abyssinian.android.ext.databind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.bindroid.ui.BoundUi;
import com.bindroid.ui.HasView;

/**
 * Created by ram.murlidhar on 04-12-2014.
 */
public class CustomLayoutView<Object> implements HasView, BoundUi<Object> {
    private String rowItem;
    private Context ctx;
    private Object dataSource;
    private View layout;
    public CustomLayoutView(String rowItem, Context ctx) {
        this.rowItem = rowItem;
        this.ctx = ctx;
    }
    @Override
    public View getView() {
        this.layout = LayoutInflater.from(ctx).inflate(
                ctx.getResources().getIdentifier(rowItem, "layout", ctx.getPackageName()), null);

        return layout;
    }

    @Override
    public void bind(Object dataSource) {
        this.dataSource = dataSource;
    }
}
