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

    static {
        addItem(createProgramItem(797, "Quick Six", "Strength and endurance building",  R.drawable.dolphin_plank));
        addItem(createProgramItem(842, "May 4", "Next level of endurance", R.drawable.downward_dog));
        addItem(createProgramItem(854, "May 5", "Longer and harder work-outs", R.drawable.plank));
        addItem(createProgramItem(436, "May 6", "Strong work-out", R.drawable.dolphin_plank));
        addItem(createProgramItem(448, "May 7", "Another good one", R.drawable.scissor));
        addItem(createProgramItem(460, "May 8", "Midweek Heat", R.drawable.plank_cross_tap));
    }

    private static void addItem(ProgramItem item) {
        ITEMS.add(item);
    }

    private static ProgramItem createProgramItem(int id, String name, String description, int imageId) {
        return new ProgramItem(id, name, description, imageId);
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
    public static class ProgramItem {
        public final int id;
        public final String name;
        public final String description;
        public final int imageId;

        public ProgramItem(int id, String name, String description, int imageId) {
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
