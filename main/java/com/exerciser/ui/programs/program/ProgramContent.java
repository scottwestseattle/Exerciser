package com.exerciser.ui.programs.program;

import com.exerciser.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ProgramContent {

    /**
     * An array of sample items.
     */
    public static final List<ProgramItem> ITEMS = new ArrayList<ProgramItem>();

    /**
     * A map of sample items, by ID.
     */
    public static final Map<String, ProgramItem> ITEM_MAP = new HashMap<String, ProgramItem>();

    private static final int COUNT = 3;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
        }

        addItem(createProgramItem(1, "Beginner", "Strength and endurance building",  R.drawable.dolphin_plank));
        addItem(createProgramItem(2, "Intermediate", "Next level of endurance", R.drawable.downward_dog));
        addItem(createProgramItem(3, "Advanced", "Longer and harder work-outs", R.drawable.plank));
    }

    private static void addItem(ProgramItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static ProgramItem createProgramItem(int position, String name, String description, int imageId) {
        return new ProgramItem(String.valueOf(position), name, description, imageId);
    }

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
    public static class ProgramItem {
        public final String id;
        public final String name;
        public final String description;
        public final int imageId;

        public ProgramItem(String id, String name, String description, int imageId) {
            this.id = id;
            this.name = name;
            this.imageId = imageId;
            this.description = description;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
