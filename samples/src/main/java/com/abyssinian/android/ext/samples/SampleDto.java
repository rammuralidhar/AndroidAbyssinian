package com.abyssinian.android.ext.samples;

import com.bindroid.trackable.TrackableCollection;
import com.bindroid.trackable.TrackableField;

import java.io.Serializable;

/**
 * Created by ram.murlidhar on 13-11-2014.
 */
public class SampleDto implements Serializable {
    private TrackableField<String> test = new TrackableField<String>();

    public TrackableCollection<String> getStringList() {
        return stringList.get();
    }
    public void setStringList(TrackableCollection<String> t) {
        stringList.set(t);
    }


    private TrackableField<TrackableCollection<String>> stringList = new TrackableField<TrackableCollection<String>>();
    public String getTest() {
        return test.get();
    }

    public void setTest(String test) {
        this.test.set(test);
    }
}
