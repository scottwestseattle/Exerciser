package com.exerciser.ui.exercise;

import android.util.Log;

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
    public static List<ExerciseItem> exerciseList = new ArrayList<ExerciseItem>();

    ExerciseContent(int exerciseId)
    {
        String url = "https://learnfast.xyz/lessons/rss/" + exerciseId;
        Log.i("parse", "Get Exercises from RSS...");
        xml = new HandleXML(url, exerciseList);
    }
    static {
    }

    private static void addItem(ExerciseItem item) {
        exerciseList.add(item);
    }

    public boolean isLoaded() {
        return (null != xml && xml.isLoaded());
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
