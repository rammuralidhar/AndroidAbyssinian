package com.abyssinian.android.ext;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

public class ChainedLayoutFactoryWrapper implements LayoutInflater.Factory {
    private static final String[] sClassPrefixList = { "android.widget.", "android.webkit." };
    private final LayoutInflater.Factory factory;
    private List<ViewManipulator> viewManipulators = new ArrayList<ViewManipulator>();
    private List<String> attributes = new ArrayList<String>();


    public ChainedLayoutFactoryWrapper(Context context, LayoutInflater.Factory factory, int attributeId) {
        this.factory = factory;
        String[] viewManipulatorStr = context.getResources().getStringArray(attributeId);

        for (String viewManipulatorClass : viewManipulatorStr) {
            try {
                String[] viewManipulatorClassArr = viewManipulatorClass.split(":");
                viewManipulators.add((ViewManipulator) Class.forName(viewManipulatorClassArr[0]).newInstance());
                attributes.add(viewManipulatorClassArr[1]);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;

        if (context instanceof LayoutInflater.Factory) {
            view = ((LayoutInflater.Factory) context).onCreateView(name, context, attrs);
        }

        if (factory != null && view == null) {
            view = factory.onCreateView(name, context, attrs);
        }

        if (view == null) {
            view = createViewOrFailQuietly(name, context, attrs);
        }

        if (view != null) {
            onViewCreated(view, name, context, attrs);
        }

        return view;
    }


    private View createViewOrFailQuietly(String name, Context context, AttributeSet attrs) {
        if (name.contains(".")) {
            return createViewOrFailQuietly(name, null, context, attrs);
        }

        for (final String prefix : sClassPrefixList) {
            final View view = createViewOrFailQuietly(name, prefix, context, attrs);

            if (view != null) {
                return view;
            }
        }

        return null;
    }


    private View createViewOrFailQuietly(String name, String prefix, Context context, AttributeSet attrs) {
        try {
            return LayoutInflater.from(context).createView(name, prefix, attrs);
        } catch (Exception ignore) {
            return null;
        }
    }


    private void onViewCreated(View view, String name, Context context, AttributeSet attrs) {
        int i = 0;
        for (ViewManipulator viewManipulator : viewManipulators) {
            viewManipulator.manipulateView(view, name, context, attributes.get(i), attrs);
            i++;
        }
    }
}
