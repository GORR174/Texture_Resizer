package ru.catstack.texture_resizer.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import ru.catstack.texture_resizer.image.ImageTools;
import ru.catstack.texture_resizer.private_engine.GApp;
import ru.catstack.texture_resizer.private_engine.Main;
import ru.catstack.texture_resizer.private_engine.impl.GController;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

public class MainController extends GController{

    @FXML
    public Label filesCount;
    @FXML
    public TextField scaleValueField;
    @FXML
    public Label folder;
    @FXML
    public Button resizeButton;

    FileChooser fileChooser = new FileChooser();
    DirectoryChooser directoryChooser = new DirectoryChooser();
    List<File> files;

    String defaultFolder = System.getProperty("user.home") + System.getProperty("file.separator") + "Documents"
            + System.getProperty("file.separator") + "Texture Resizer";

    File saveFolder = new File(defaultFolder);

    Image image;

    @Override
    public void onShow() {
        fileChooser.setTitle("Select textures");
        directoryChooser.setTitle("Select save directory");
        filesCount.setText("Select files");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg"));
        folder.setText(saveFolder.getPath());
    }

    public void onChooseClick(ActionEvent actionEvent) {
        files = fileChooser.showOpenMultipleDialog(Main.pStage);
        if(files != null) {
            filesCount.setText("You have selected " + files.size() + " files");
        }else {
            filesCount.setText("Select files");
        }
    }

    public void onSetFolderClick(ActionEvent actionEvent) {
        saveFolder = directoryChooser.showDialog(Main.pStage);
        if(saveFolder == null)
            saveFolder = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Documents"
                    + System.getProperty("file.separator") + "Texture Resizer");
        folder.setText(saveFolder.getPath());
    }

    public void onResizeClick(ActionEvent actionEvent) throws MalformedURLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);

        try {
            if (files != null) {
                if (!saveFolder.exists())
                    saveFolder.mkdirs();
                for (File file : files) {
                    image = new Image(file.toURL().toString());
                    image = ImageTools.resizeImage(image, Float.valueOf(scaleValueField.getText()));
                    File saveFile = new File(saveFolder.getPath() + System.getProperty("file.separator") + file.getName());
                    ImageTools.saveImageToFile(image, saveFile);
                }
                alert.setContentText("Resize successful");
                alert.showAndWait();
            } else {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText("No files selected");
                alert.showAndWait();
            }
        }catch (OutOfMemoryError ee){
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Out of memory");
            alert.showAndWait();
        }catch (Exception e){
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Error");
            alert.showAndWait();
        }
    }

    public void on0_25Click(ActionEvent actionEvent) {
        scaleValueField.setText("0.25");
    }

    public void on0_5Click(ActionEvent actionEvent) {
        scaleValueField.setText("0.5");
    }

    public void on1Click(ActionEvent actionEvent) {
        scaleValueField.setText("1");
    }

    public void on2Cick(ActionEvent actionEvent) {
        scaleValueField.setText("2");
    }

    public void on4Click(ActionEvent actionEvent) {
        scaleValueField.setText("4");
    }

    public void onInfoClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Texture Resizer by CatStack Games\n\n" +
                "Do you want to open a VK page? (Ru)\n\n");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            GApp.app.main.getHostServices().showDocument("https://vk.com/catstack");
        }
    }
}
