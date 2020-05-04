package com.exerciser.ui.programs.program;

import com.exerciser.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for holding list data item
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ProgramContent {

    /**
     * An array of list items.
     */
    public static final List<ProgramItem> ITEMS = new ArrayList<ProgramItem>();

    static {
        addItem(createProgramItem(14, "Plancha", "Una excelente mezcla de ejercicios clásicos de yoga, pilates, plancha y mucho más.",  R.drawable.dolphin_plank));
        addItem(createProgramItem(17, "Intermediate", "Next level of endurance", R.drawable.downward_dog));
        addItem(createProgramItem(18, "Respiración Profunda", "Breathing exercises.", R.drawable.plank));
    }

    private static void addItem(ProgramItem item) {
        ITEMS.add(item);
    }

    private static ProgramItem createProgramItem(int id, String name, String description, int imageId) {
        return new ProgramItem(id, name, description, imageId);
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
