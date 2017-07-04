package ru.catstack.texture_resizer.controllers;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import ru.catstack.texture_resizer.model.ImageTools;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

public class ResizeController {

    private List<File> files;
    private File saveFolder;
    private Image image;
    private float scaleValue;
    private Alert alert;

    public ResizeController(){

    }

    private ResizeController(List<File> files, File saveFolder, float scaleValue) {
        this.files = files;
        this.saveFolder = saveFolder;
        this.scaleValue = scaleValue;
    }

    public void resize(){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);

        try {
            if (files != null) {
                imageResize();
                showAlert("Resize successful");
            } else {
                showAlertWithAlertType("No files selected", Alert.AlertType.WARNING);
            }
        }catch (OutOfMemoryError ee){
            showAlertWithAlertType("Out of memory", Alert.AlertType.ERROR);
        }catch (Exception e){
            showAlertWithAlertType("Error", Alert.AlertType.ERROR);
        }
    }

    private void imageResize() throws MalformedURLException {
        if (!saveFolder.exists())
            saveFolder.mkdirs();
        for (File file : files) {
            image = new Image(file.toURL().toString());
            image = ImageTools.resizeImage(image, scaleValue);
            File saveFile = new File(saveFolder.getPath() + System.getProperty("file.separator") + file.getName());
            ImageTools.saveImageToFile(image, saveFile);
        }
    }

    private void showAlertWithAlertType(String text, Alert.AlertType alertType){
        alert.setAlertType(alertType);
        showAlert(text);
    }

    private void showAlert(String text){
        alert.setContentText(text);
        alert.showAndWait();
    }

    public class ResizeControllerBuilder {

        private List<File> files;
        private File saveFolder;
        private float scaleValue;

        public ResizeControllerBuilder setFiles(List<File> files) {
            this.files = files;
            return this;
        }

        public ResizeControllerBuilder setSaveFolder(File saveFolder) {
            this.saveFolder = saveFolder;
            return this;
        }

        public ResizeControllerBuilder setScaleValue(float scaleValue) {
            this.scaleValue = scaleValue;
            return this;
        }

        public ResizeController createResizeController() {
            return new ResizeController(files, saveFolder, scaleValue);
        }
    }
}