package com.exerciser.ui.exercise;

import android.util.Log;

import com.exerciser.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExerciseContent {

    public static final int startSeconds = 15;
    public static final int breakEndCountdownSeconds = 5;
    public static final int exerciseEndCountdownSeconds = 10;
    private static HandleXML xml = null;

    /**
     * An array of sample items.
     */
    public static final List<ExerciseItem> ITEMS = new ArrayList<ExerciseItem>();

    /**
     * A map of sample items, by ID.
     */
    public static final Map<String, ExerciseItem> ITEM_MAP = new HashMap<String, ExerciseItem>();

    private static final int COUNT = 3;

    static {
        String url = "https://learnfast.xyz/lessons/rss/840";
        Log.i("parse", "Get Exercises from RSS...");
        xml = new HandleXML(url);
    }

    public void load()
    {
        if (ITEMS.size() == 0) {
            if (null != xml) {
                ArrayList<ExerciseContent.ExerciseItem> exerciseItems = xml.getExerciseItems();
                if (null != exerciseItems) {
                    int order = 0;
                    for (ExerciseItem e : exerciseItems) {
                        addItem(createExerciseItem(e.name, e.description, e.imageName, e.runSeconds, e.breakSeconds, ++order, e.description));
                    }
                }
            }
        }
    }

    private static void addItem(ExerciseItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.name, item);
    }

    private static ExerciseItem createExerciseItem(String name, String description, String imageName, int runSeconds, int breakSeconds, int order, String instructions) {
        return new ExerciseItem(name, description, imageName, runSeconds, breakSeconds, order, instructions);
    }

    //sbw: not used
    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A program item representing a piece of content.
     */
    public static class ExerciseItem {
        public String name;
        public String description;
        public String imageName;
        public int runSeconds;
        public int breakSeconds;
        public int order;
        public String instructions;

        public ExerciseItem(String name, String description, String imageName, int runSeconds, int breakSeconds, int order, String instructions) {
            this.name = name;
            this.imageName = imageName;
            this.description = description;
            this.runSeconds = runSeconds;
            this.breakSeconds = breakSeconds;
            this.order = order;
            this.instructions = instructions;
        }

        public boolean isFirst() {
            return this.order <= 1;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
