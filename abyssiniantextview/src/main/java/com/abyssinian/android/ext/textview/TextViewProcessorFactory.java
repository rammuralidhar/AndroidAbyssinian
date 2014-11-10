package com.abyssinian.android.ext.textview;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ram.murlidhar on 07-11-2014.
 */
public class TextViewProcessorFactory {
    private static Map<String, TextViewProcessor> processors = new HashMap<>();

    public static void registerProcessor(String uniqueId, TextViewProcessor processor) {
        processors.put(uniqueId, processor);
    }

    public TextViewProcessor getProcessor(String uniqueId) {
        return processors.get(uniqueId);
    }
}
