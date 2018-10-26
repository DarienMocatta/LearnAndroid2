package com.example.soco.learnandroid;

public class YouTubeConfiguration {
    //Used from YouTube API in class tutorial, with new specific api generated from Google Console.

    public YouTubeConfiguration() {
    }
    //Declares required API as well as API return method.
    private static final String API_KEY = "AIzaSyAMbB4gAZm8JCZNmIOGF8JwUyHKrTdgfOA";

    public static String getApiKey() {
        return API_KEY;
    }

}
