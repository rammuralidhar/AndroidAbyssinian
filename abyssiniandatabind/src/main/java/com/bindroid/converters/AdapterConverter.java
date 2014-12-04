package com.bindroid.converters;

import java.util.List;

import android.view.View;

import com.bindroid.ValueConverter;
import com.bindroid.trackable.TrackableCollection;
import com.bindroid.ui.BoundCollectionAdapter;
import com.bindroid.ui.HasView;

/**
 * A {@link ValueConverter} that converts a {@link java.util.List} or {@link com.bindroid.trackable.TrackableCollection} into an
 * {@link android.widget.Adapter} that can be used for {@link android.widget.ListView}s and other UI widgets.
 */
public class AdapterConverter extends ValueConverter {
  private HasView viewType;
  private HasView dropDownViewType;
  private boolean recycleViews;
  private boolean cacheViews;

  /**
   * Constructs an AdapterConverter that generates the given views for each object in the bound
   * list.
   * 
   * @param viewType
   *          The type of view to construct for each object in the list.
   */
  public AdapterConverter(HasView viewType) {
    this(viewType, true);
  }

  /**
   * Constructs an AdapterConverter that generates the given views for each object in the bound
   * list.
   * 
   * @param viewType
   *          The type of view to construct for each object in the list.
   * @param recycleViews
   *          Whether views should be recycled by the adapter.
   */
  public AdapterConverter(HasView viewType, boolean recycleViews) {
    this(viewType, recycleViews, false);
  }

  /**
   * Constructs an AdapterConverter that generates the given views for each object in the bound
   * list.
   * 
   * @param viewType
   *          The type of view to construct for each object in the list.
   * @param recycleViews
   *          Whether views should be recycled by the adapter.
   * @param cacheViews
   *          Whether views should be cached by the adapter.
   */
  public AdapterConverter(HasView viewType, boolean recycleViews, boolean cacheViews) {
    this(viewType, recycleViews, cacheViews, viewType);
  }

  /**
   * Constructs an AdapterConverter that generates the given views for each object in the bound
   * list.
   * 
   * @param viewType
   *          The type of view to construct for each object in the list.
   * @param recycleViews
   *          Whether views should be recycled by the adapter.
   * @param cacheViews
   *          Whether views should be cached by the adapter.
   * @param dropDownViewType
   *          The type of view to construct for drop-downs.
   */
  public AdapterConverter(HasView viewType, boolean recycleViews, boolean cacheViews,
      HasView dropDownViewType) {
    this.setViewType(viewType);
    this.setDropDownViewType(dropDownViewType);
    this.recycleViews = recycleViews;
    this.cacheViews = cacheViews;
  }

    public AdapterConverter(HasView viewType, String rowItem) {
        this.setViewType(viewType);
        this.setDropDownViewType(dropDownViewType);
        this.recycleViews = true;
        this.cacheViews = true;
    }

    @SuppressWarnings("unchecked")
  @Override
  public Object convertToTarget(Object sourceValue, Class<?> targetType) {
    TrackableCollection<Object> source;
    if (sourceValue instanceof TrackableCollection) {
      source = (TrackableCollection<Object>) sourceValue;
    } else {
      source = new TrackableCollection<Object>((List<Object>) sourceValue);
    }
    return new BoundCollectionAdapter<Object>(source, this.getViewType(), this.recycleViews,
        this.cacheViews, this.getDropDownViewType());
  }

  private HasView getDropDownViewType() {
    return this.dropDownViewType;
  }

  private HasView getViewType() {
    return this.viewType;
  }

  private void setDropDownViewType(HasView value) {
    this.dropDownViewType = value;
  }

  private void setViewType(HasView value) {
    this.viewType = value;
  }
}
