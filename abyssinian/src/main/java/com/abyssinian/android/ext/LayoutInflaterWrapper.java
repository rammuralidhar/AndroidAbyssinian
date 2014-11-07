package com.abyssinian.android.ext;

import android.content.Context;
import android.view.LayoutInflater;

public class LayoutInflaterWrapper extends LayoutInflater {
    private final int attributeId;
    protected LayoutInflaterWrapper(Context context, int attributeId) {
        super(context);
        this.attributeId = attributeId;
        setUpLayoutFactory();
    }

    protected LayoutInflaterWrapper(LayoutInflater original, Context newContext, int attributeId) {
        super(original, newContext);
        this.attributeId = attributeId;
        setUpLayoutFactory();
    }

    private void setUpLayoutFactory() {
        if (!(getFactory() instanceof ChainedLayoutFactoryWrapper)) {
            setFactory(new ChainedLayoutFactoryWrapper(this.getContext(), getFactory(), attributeId));
        }
    }


    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new LayoutInflaterWrapper(this, newContext, attributeId);
    }    
}
