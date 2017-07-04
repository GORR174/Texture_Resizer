package ru.catstack.texture_resizer.model;

import ru.catstack.texture_resizer.engine.Main;

import java.net.URL;

public enum FXML_FILES {
    MAIN("main.fxml");

    private final URL _URL;

    FXML_FILES(String path) {
        _URL = Main.class.getResource("/fxml/" + path);
    }

    public URL get_URL(){
        return _URL;
    }
}
