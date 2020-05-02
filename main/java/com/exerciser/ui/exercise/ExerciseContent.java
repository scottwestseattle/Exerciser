package com.exerciser.ui.exercise;

import com.exerciser.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExerciseContent {

    public static final int startSeconds = 15;
    public static final int breakEndCountdownSeconds = 5;
    public static final int exerciseEndCountdownSeconds = 10;

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

        if (true)
        {
            // quick test program
            addItem(createExerciseItem("The Basic Plank", "", R.drawable.plank, 25, 10, ++order, "Keep stright."));
            addItem(createExerciseItem("Fire Hydrant", "", R.drawable.fire_hydrant, 30, 20, ++order, "Switch sides."));
            addItem(createExerciseItem("Reverse Plank Table with leg lift", "", R.drawable.reverse_table_with_leg_lift, 30, 20, ++order, "Switch legs!"));
            addItem(createExerciseItem("Lord of the Dance", "", R.drawable.lord_of_the_dance, 40, 20, ++order, "Change Sides."));
        }
        else
        {
            // full program
            /*
            addItem(createExerciseItem("Dolphin Plank", "", R.drawable.dolphin_plank, 40, 20, ++order, ""));
            addItem(createExerciseItem("Push-ups", "", R.drawable.push_ups, 60, 20, ++order));
            addItem(createExerciseItem("Cobra Stretch", "", R.drawable.cobra, 40, 0, ++order));
            addItem(createExerciseItem("Runner's Lunge", "", R.drawable.runners_lunge, 60, 20, ++order));

            addItem(createExerciseItem("Plank", "", R.drawable.plank, 60, 20, ++order));
            addItem(createExerciseItem("Abb Scissors", "", R.drawable.ab_scissors, 50, 20, ++order));
            addItem(createExerciseItem("Reverse Table with leg lift", "", R.drawable.reverse_table_with_leg_lift, 60, 20, ++order));
            addItem(createExerciseItem("Squatting Buddha", "", R.drawable.squatting_buddha, 50, 20, ++order));

            addItem(createExerciseItem("Downward Dog", "", R.drawable.downward_dog, 60, 20, ++order));
            addItem(createExerciseItem("Curls", "", R.drawable.none, 60, 20, ++order));
            addItem(createExerciseItem("Side Plank with Left Elbow", "", R.drawable.side_plank_elbow_left, 40, 20, ++order));
            addItem(createExerciseItem("Side Plank with Right Elbow", "", R.drawable.side_plank_elbow_right, 40, 20, ++order));

             */
        }
    }

    private static void addItem(ExerciseItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.name, item);
    }

    private static ExerciseItem createExerciseItem(String name, String description, int imageId, int runSeconds, int breakSeconds, int order, String instructions) {
        return new ExerciseItem(name, description, imageId, runSeconds, breakSeconds, order, instructions);
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
        public final String instructions;

        public ExerciseItem(String name, String description, int imageId, int runSeconds, int breakSeconds, int order, String instructions) {
            this.name = name;
            this.imageId = imageId;
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
