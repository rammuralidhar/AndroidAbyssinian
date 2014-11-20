package com.bindroid.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.bindroid.trackable.Trackable;
import com.bindroid.utils.Action;
import com.bindroid.utils.Function;
import com.bindroid.utils.ObjectUtilities;
import com.bindroid.utils.Property;

import java.lang.ref.WeakReference;

/**
 * Represents the "Text" property of an {@link android.widget.EditText}, allowing for two-way bindings.
 */
public class SpinnerTextProperty extends Property<String> {
  private Trackable notifier = new Trackable();
  private String lastValue = null;

  /**
   * Constructs an EditTextTextProperty for an {@link android.widget.EditText}.
   *
   * @param target
   *          the text box being bound.
   */
  public SpinnerTextProperty(Spinner target) {
    final WeakReference<Spinner> weakTarget = new WeakReference<Spinner>(target);
    this.propertyType = String.class;
    target.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            SpinnerTextProperty.this.notifier.updateTrackers();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    });
    this.getter = new Function<String>() {
      @Override
      public String evaluate() {
          Spinner target = weakTarget.get();
        if (target != null) {
          SpinnerTextProperty.this.notifier.track();
          return SpinnerTextProperty.this.lastValue = target.getSelectedItem().toString();
        } else {
          return SpinnerTextProperty.this.lastValue;
        }
      }
    };
    this.setter = new Action<String>() {
      @Override
      public void invoke(String parameter) {
          Spinner target = weakTarget.get();
        if (target != null) {
            int index =  getIndexFromElement(target.getAdapter(), parameter);
            target.setSelection(index);
            SpinnerTextProperty.this.lastValue = parameter;
          }
        }
    };
  }
    public int getIndexFromElement(SpinnerAdapter adapter, String element) {
        for(int i = 0; i < adapter.getCount(); i++) {
            if(adapter.getItem(i).equals(element)) {
                return i;
            }
        }
        return 0;
    }

}
