package com.exerciser.ui.sessions.session;

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
public class SessionContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Session> ITEMS = new ArrayList<Session>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.

        int position = 0;
        addItem(createItem(797, "Quick Six", "Strength and endurance building", "dolphin_plank.png"));
        addItem(createItem(842, "May 4", "Next level of endurance", "downward_dog.png"));
        addItem(createItem(854, "May 5", "Longer and harder work-outs", "plank.png"));
        addItem(createItem(436, "May 6", "Strong work-out", "dolphin_plank.png"));
    }

    private static void addItem(Session item) {
        ITEMS.add(item);
    }

    private static Session createItem(int position, String name, String description, String imageName) {
        return new Session(position, name, description, imageName);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class Session {
        public final int id;
        public final String name;
        public final String description;
        public final String imageName;

        public Session(int id, String content, String description, String imageName) {
            this.id = id;
            this.name = content;
            this.description = description;
            this.imageName = imageName;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
