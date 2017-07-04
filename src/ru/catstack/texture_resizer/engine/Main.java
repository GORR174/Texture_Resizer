package ru.catstack.texture_resizer.engine;

import ru.catstack.fx_engine.engine.App;
import ru.catstack.fx_engine.impl.GApplication;
import ru.catstack.fx_engine.resources.GApp;
import ru.catstack.texture_resizer.model.FXML_FILES;

public class Main implements GApplication {

    public static void main(String[] args) {

        Main main = new Main();

        config.width = 400;
        config.height = 240;
        config.resizable = false;
        config.title = "Texture Resizer";

        new App(main);
    }

    /**
     * this method runs, when engine has loaded.
     */
    @Override
    public void onStart() {
        try {
            GApp.app.setScene(FXML_FILES.MAIN.get_URL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
