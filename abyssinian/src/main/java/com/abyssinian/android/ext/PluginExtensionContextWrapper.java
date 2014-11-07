package com.abyssinian.android.ext;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;

public class PluginExtensionContextWrapper extends ContextWrapper {
    private LayoutInflater mInflater;
    private final int attributeId;

    /**
     * @param base        ContextBase to Wrap
     * @param attributeId Attribute to lookup.
     */
    public PluginExtensionContextWrapper(Context base, int attributeId) {
        super(base);
        this.attributeId = attributeId;
    }

    @Override
    public Object getSystemService(String name) {
        if (attributeId != 0) {
            if (LAYOUT_INFLATER_SERVICE.equals(name)) {
                if (mInflater == null) {
                    mInflater = new LayoutInflaterWrapper(LayoutInflater.from(getBaseContext()), this,
                            attributeId);
                }
                return mInflater;
            }
        }
        return super.getSystemService(name);
    }

}
