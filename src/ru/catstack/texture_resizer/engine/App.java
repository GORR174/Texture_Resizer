package ru.catstack.texture_resizer.engine;

import ru.catstack.texture_resizer.private_engine.impl.MainInterface;

public class App extends MainInterface {

    @Override
    protected void onStart() {
        config.width = 400;
        config.height = 240;
        config.resizable = false;
        config.title = "Texture Resizer";
        setScene("main.fxml");
    }
}
