package com.abyssinian.android.ext.font;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.abyssinian.android.ext.ViewManipulator;

public class FontManipulator implements ViewManipulator {

    private static final String ACTION_BAR_TITLE = "action_bar_title";
    private static final String ACTION_BAR_SUBTITLE = "action_bar_subtitle";
    private static final Map<Class<? extends TextView>, Integer> sStyles = new HashMap<Class<? extends TextView>, Integer>() {
        {
            put(TextView.class, android.R.attr.textViewStyle);
            put(Button.class, android.R.attr.buttonStyle);
            put(EditText.class, android.R.attr.editTextStyle);
            put(AutoCompleteTextView.class, android.R.attr.autoCompleteTextViewStyle);
            put(MultiAutoCompleteTextView.class, android.R.attr.autoCompleteTextViewStyle);
            put(CheckBox.class, android.R.attr.checkboxStyle);
            put(RadioButton.class, android.R.attr.radioButtonStyle);
            put(ToggleButton.class, android.R.attr.buttonStyleToggle);
        }
    };


    /**
     * Some styles are in sub styles, such as actionBarTextStyle etc..
     * 
     * @param view
     *            view to check.
     * @return 2 element array, default to -1 unless a style has been found.
     */
    protected static int[] getStyleForTextView(View view) {
        final int[] styleIds = new int[] { -1, -1 };
        // Try to find the specific actionbar styles
        if (matchesResourceIdName(view, ACTION_BAR_TITLE)) {
            styleIds[0] = android.R.attr.actionBarStyle;
            styleIds[1] = android.R.attr.titleTextStyle;
        } else if (matchesResourceIdName(view, ACTION_BAR_SUBTITLE)) {
            styleIds[0] = android.R.attr.actionBarStyle;
            styleIds[1] = android.R.attr.subtitleTextStyle;
        }
        if (styleIds[0] == -1) {
            // Use TextAppearance as default style
            styleIds[0] = sStyles.containsKey(view.getClass()) ? sStyles.get(view.getClass())
                    : android.R.attr.textAppearance;
        }
        return styleIds;
    }


    /**
     * Use to match a view against a potential view id. Such as ActionBar title
     * etc.
     * 
     * @param view
     *            not null view you want to see has resource matching name.
     * @param matches
     *            not null resource name to match against. Its not case
     *            sensitive.
     * @return true if matches false otherwise.
     */
    protected static boolean matchesResourceIdName(View view, String matches) {
        if (view.getId() == View.NO_ID)
            return false;
        final String resourceEntryName = view.getResources().getResourceEntryName(view.getId());
        return resourceEntryName.equalsIgnoreCase(matches);
    }


    @Override
    public void manipulateView(View view, String name, Context context, String customAttributeName, AttributeSet attrs) {
        if (view instanceof TextView) {
            int mAttributeId = context.getResources().getIdentifier(customAttributeName, "attr", context.getPackageName());

            // Try to get typeface attribute value
            // Since we're not using namespace it's a little bit tricky
            // Try view xml attributes
            String textViewFont = CalligraphyUtils.pullFontPathFromView(context, attrs, mAttributeId);

            // Try view style attributes
            if (TextUtils.isEmpty(textViewFont)) {
                textViewFont = CalligraphyUtils.pullFontPathFromStyle(context, attrs, mAttributeId);
            }

            // Try View TextAppearance
            if (TextUtils.isEmpty(textViewFont)) {
                textViewFont = CalligraphyUtils.pullFontPathFromTextAppearance(context, attrs, mAttributeId);
            }

            // Try theme attributes
            if (TextUtils.isEmpty(textViewFont)) {
                final int[] styleForTextView = getStyleForTextView(view);
                if (styleForTextView[1] != -1)
                    textViewFont = CalligraphyUtils.pullFontPathFromTheme(context, styleForTextView[0],
                            styleForTextView[1], mAttributeId);
                else
                    textViewFont = CalligraphyUtils.pullFontPathFromTheme(context, styleForTextView[0], mAttributeId);
            }

            final boolean deferred = matchesResourceIdName(view, ACTION_BAR_TITLE)
                    || matchesResourceIdName(view, ACTION_BAR_SUBTITLE);
            CalligraphyUtils.applyFontToTextView(context, (TextView) view, CalligraphyConfig.get(), textViewFont,
                    deferred);
        }
    }

}
