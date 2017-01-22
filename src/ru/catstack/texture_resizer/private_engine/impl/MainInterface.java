package ru.catstack.texture_resizer.private_engine.impl;

import ru.catstack.texture_resizer.private_engine.EngineConfig;
import ru.catstack.texture_resizer.private_engine.Main;

import java.io.IOException;

public abstract class MainInterface {

    public EngineConfig config;
    public Main main;

    public MainInterface() {
        config = new EngineConfig();
        onStart();
    }

    protected abstract void onStart();

    public void setScene(String fxmlFile){
        try {
            Main.setScene(fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateSettings(){
        Main.updateDefaultSettings();
    }
}
