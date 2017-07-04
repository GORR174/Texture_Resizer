package ru.catstack.texture_resizer.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import ru.catstack.fx_engine.impl.GController;
import ru.catstack.fx_engine.resources.GApp;
import ru.catstack.texture_resizer.model.ImageTools;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

public class MainController implements GController {

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

    /**
     * this method runs, when the {@link ru.catstack.texture_resizer.model.FXML_FILES#MAIN} starts.
     */
    @Override
    public void onShow() {
        fileChooser.setTitle("Select textures");
        directoryChooser.setTitle("Select save directory");
        filesCount.setText("Select files");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg"));
        folder.setText(saveFolder.getPath());
    }

    /**
     * this method runs, when user clicks to choose button.
     */
    public void onChooseClick(ActionEvent actionEvent) {
        files = fileChooser.showOpenMultipleDialog(GApp.app.getStage());
        if(files != null) {
            filesCount.setText("You have selected " + files.size() + " files");
        }else {
            filesCount.setText("Select files");
        }
    }

    /**
     * this method runs, when user clicks to set folder button.
     */
    public void onSetFolderClick(ActionEvent actionEvent) {
        saveFolder = directoryChooser.showDialog(GApp.app.getStage());
        if(saveFolder == null)
            saveFolder = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Documents"
                    + System.getProperty("file.separator") + "Texture Resizer");
        folder.setText(saveFolder.getPath());
    }

    /**
     * this method runs, when user clicks to resize button.
     */
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

    /**
     * this method runs, when user clicks to info button.
     */
    public void onInfoClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Texture Resizer by CatStack Games\n\n" +
                "Do you want to open a VK page? (Ru)\n\n");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            GApp.app.getHostServices().showDocument("https://vk.com/catstack");
        }
    }

    /**
     * this method runs, when user clicks to scale help buttons.
     */
    public void onScaleButtonsClick(ActionEvent actionEvent) {
        Button sourceButton = (Button)actionEvent.getSource();
        String scaleValue = sourceButton.getText();

        if(scaleValue.equals("1/4"))
            scaleValue = "0.25";
        else if(scaleValue.equals("1/2"))
            scaleValue = "0.5";

        scaleValueField.setText(scaleValue);
    }
}
