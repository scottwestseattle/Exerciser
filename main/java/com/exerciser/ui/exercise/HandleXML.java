package com.exerciser.ui.exercise;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class HandleXML {

    private String name = "";
    private int order = -1;
    private int runSeconds = -1;
    private int breakSeconds = -1;
    private String description = "";
    private String imageName = "";
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;
    ArrayList<ExerciseContent.ExerciseItem> exerciseItems = null;

    public HandleXML(String url){
        this.urlString = url;
        this.fetchXML();
    }

    public void fetchXML(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {

                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);

                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);

                    parse(myparser);

                    stream.close();
                }

                catch (Exception e) {
                    Log.i("parse: fetchXML", "exception: " + e.getMessage());
                }
            }
        });

        thread.start();
    }

    public void parse(XmlPullParser myParser) {
        int event;
        String text = null;

        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {

                String name = myParser.getName();
                //Log.i("parse", "name tag: " + name);

                switch (event) {

                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:

                        if(name.equals("record")){
                            Log.i("parse", "save the record");
                            if (null == this.exerciseItems)
                                this.exerciseItems = new ArrayList<ExerciseContent.ExerciseItem>();

                            ExerciseContent.ExerciseItem ei = new ExerciseContent.ExerciseItem(
                                    this.name,
                                    this.description,
                                    this.imageName,
                                    this.runSeconds,
                                    this.breakSeconds,
                                    this.order,
                                    this.description);

                            this.exerciseItems.add(ei);
                        }
                        
                        else if (name.equals("name")){
                            this.name = text;
                            Log.i("parse", "name value: " + text.trim());
                        }

                        else if(name.equals("runSeconds")){
                            try {
                                this.runSeconds = Integer.parseInt(text);
                            } catch(NumberFormatException nfe){}
                        }

                        else if(name.equals("breakSeconds")){
                            try {
                                this.breakSeconds = Integer.parseInt(text);
                            } catch(NumberFormatException nfe){}
                        }

                        else if(name.equals("description")){
                            this.description = text.trim();
                        }

                        else if(name.equals("imageName")){
                            // remove file extension so we can find the matching Android resource
                            this.imageName = text.substring(0, text.lastIndexOf("."));
                        }

                        else{
                        }

                        break;
                }

                event = myParser.next();
            }

            parsingComplete = true;
        }

        catch (Exception e) {
            Log.i("parse and store", "exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<ExerciseContent.ExerciseItem> getExerciseItems()
    {
        return (this.parsingComplete) ? this.exerciseItems : null;
    }
}