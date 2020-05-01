package com.exerciser.ui.exercise;

import com.exerciser.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExerciseContent {

    public static final int startSeconds = 10;

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
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
        }

        int order = 0;
        addItem(createExerciseItem("Dolphin Plank", "", R.drawable.dolphin_plank, 15, 5, ++order));
        addItem(createExerciseItem("Downward Dog", "", R.drawable.downward_dog, 15, 5, ++order));
        addItem(createExerciseItem("Plank", "", R.drawable.plank, 15, 0, ++order));
    }

    private static void addItem(ExerciseItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.name, item);
    }

    private static ExerciseItem createExerciseItem(String name, String description, int imageId, int runSeconds, int breakSeconds, int order) {
        return new ExerciseItem(name, description, imageId, runSeconds, breakSeconds, order);
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
        public final String name;
        public final String description;
        public final int imageId;
        public final int runSeconds;
        public final int breakSeconds;
        public final int order;

        public ExerciseItem(String name, String description, int imageId, int runSeconds, int breakSeconds, int order) {
            this.name = name;
            this.imageId = imageId;
            this.description = description;
            this.runSeconds = runSeconds;
            this.breakSeconds = breakSeconds;
            this.order = order;
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
