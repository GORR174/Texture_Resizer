package ru.catstack.texture_resizer.private_engine;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.catstack.texture_resizer.engine.App;
import ru.catstack.texture_resizer.private_engine.impl.GController;

import java.io.IOException;

public class Main extends Application {

    public static Stage pStage;

    @Override
    public void start(Stage primaryStage) throws IOException {

        pStage = primaryStage;

        GApp.app = new App();
        GApp.app.main = this;

        updateDefaultSettings();

    }

    public static void setScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/"+fxmlFile));
        pStage.setScene(new Scene(loader.load()));
        GController controller = loader.getController();
        controller.onShow();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void updateDefaultSettings(){

        pStage.setWidth(GApp.app.config.width);
        pStage.setHeight(GApp.app.config.height);
        pStage.setTitle(GApp.app.config.title);
        pStage.setResizable(GApp.app.config.resizable);
        pStage.setFullScreen(GApp.app.config.fullscreen);
        pStage.setMaximized(GApp.app.config.maximized);
        pStage.setAlwaysOnTop(GApp.app.config.alwaysOnTop);
        pStage.setMaxWidth(GApp.app.config.maxWidth);
        pStage.setMaxHeight(GApp.app.config.maxHeight);
        pStage.setMinWidth(GApp.app.config.minWidth);
        pStage.setMinHeight(GApp.app.config.minHeight);
        pStage.setOpacity(GApp.app.config.opacity);
        if(GApp.app.config.x != -777) pStage.setX(GApp.app.config.x);
        if(GApp.app.config.y != -777) pStage.setY(GApp.app.config.y);

        pStage.show();
    }

    public HostServices getHostServicec(){
        return getHostServices();
    }
}
