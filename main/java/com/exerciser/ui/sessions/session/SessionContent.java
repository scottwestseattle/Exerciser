package com.exerciser.ui.sessions.session;

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
        addSession(createSession(position++, "Day 1", "first day"));
        addSession(createSession(position++, "Day 2", "2nd day"));
        addSession(createSession(position++, "Day 3", "3rd day"));

    }

    private static void addSession(Session item) {
        ITEMS.add(item);
    }

    private static Session createSession(int position, String name, String description) {
        return new Session(position, name, description);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class Session {
        public final int id;
        public final String name;
        public final String description;

        public Session(int id, String content, String description) {
            this.id = id;
            this.name = content;
            this.description = description;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
