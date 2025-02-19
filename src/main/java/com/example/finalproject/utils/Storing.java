package com.example.finalproject.utils;

import com.example.finalproject.Main;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

public class Storing {
    static public void putValueToPreferences(String key, String value){
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        prefs.put(key, value);
    }
    static public String getValueToPreferences(String key) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        return prefs.get(key, "");
    }
}
