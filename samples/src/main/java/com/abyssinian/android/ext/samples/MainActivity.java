package com.abyssinian.android.ext.samples;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

import com.abyssinian.android.ext.PluginExtensionContextWrapper;
import com.bindroid.trackable.TrackableCollection;

/**
 *
 */
public class MainActivity extends Activity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new PluginExtensionContextWrapper(newBase, R.array.view_manipulators));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SampleDto dto = new SampleDto();
        dto.setTest("data3");
        dto.setStringList(new TrackableCollection<String>());
        dto.getStringList().add("test");
        dto.getStringList().add("test");
        dto.getStringList().add("test");
        dto.getStringList().add("test");
        dto.getStringList().add("test");
        System.out.println(dto.getTest());

        // set the dto
        getIntent().putExtra("data", dto);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
