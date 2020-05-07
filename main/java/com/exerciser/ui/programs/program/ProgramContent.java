package com.exerciser.ui.programs.program;

import android.util.Log;

import com.exerciser.RssReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Program data class
 * Handles reading of data from xml rss
 */
public class ProgramContent {

    private static RssReader rss = null;

    /**
     * The array of items from the rss feed
     */
    public static List<ProgramContent.ProgramItem> programList = new ArrayList<ProgramContent.ProgramItem>();

    ProgramContent()
    {
    }

    static {
        String url = "https://learnfast.xyz/courses/rss";
        Log.i("parse", "Getting program list from rss...");
        rss = new RssReader(url);
        rss.fetchProgramList(programList);
    }

    private static void addItem(ProgramItem item) {
        programList.add(item);
    }

    private static ProgramItem createProgramItem(int id, String name, String description, int imageId, int sessionCount) {
        return new ProgramItem(id, name, description, imageId, sessionCount);
    }

    public boolean isLoaded() {
        return (null != rss && rss.isLoaded());
    }

    /**
     * A program item representing a piece of content.
     */
    public static class ProgramItem {
        public final int id;
        public final String name;
        public final String description;
        public final int imageId;
        public final int sessionCount;

        public ProgramItem(int id, String name, String description, int imageId, int sessionCount) {
            this.id = id;
            this.name = name;
            this.imageId = imageId;
            this.description = description;
            this.sessionCount = sessionCount;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
