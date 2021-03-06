package com.exerciser.ui.sessions.session;

import android.util.Log;

import com.exerciser.RssReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class SessionContent {

    private static RssReader rss = null;
    public static int courseId = -1;
    public static List<SessionContent.Session> sessionList = new ArrayList<SessionContent.Session>();

    public SessionContent(int courseId)
    {
        //
        // todo: the rss is read again here because I couldn't figure out how to pass in the data from the programs read
        //
        sessionList.clear();
        this.courseId = courseId;
        String url = "https://learnfast.xyz/courses/rss";
        Log.i("parse", "Getting Session list from rss...");
        rss = new RssReader(url);
        rss.fetchSessionList(sessionList, courseId);
    }

    static {
    }

    private static void addItem(Session item) {
        sessionList.add(item);
    }

    /**
     * A Session Item
     */
    public static class Session {
        public final int id;
        public final int number;
        public final String name;
        public final String description;
        public final String parent;
        public final int seconds;
        public final int exerciseCount;

        public Session(int id, String name, String description, int number, String parent, int seconds, int exerciseCount) {
            this.id = id;
            this.number = number;
            this.name = name;
            this.description = description;
            this.parent = parent;
            this.seconds = seconds;
            this.exerciseCount = exerciseCount;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
