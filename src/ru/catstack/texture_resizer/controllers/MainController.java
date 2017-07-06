package ru.catstack.texture_resizer.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import ru.catstack.fx_engine.impl.GController;
import ru.catstack.fx_engine.resources.GApp;
import ru.catstack.texture_resizer.engine.Main;
import ru.catstack.texture_resizer.model.ImageTools;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainController implements GController {

    @FXML
    public Label filesCount;
    public TextField scaleValueField;
    public Label folder;
    public Button resizeButton;
    public Label resizeMode;

    private FileChooser fileChooser = new FileChooser();
    private DirectoryChooser directoryChooser = new DirectoryChooser();
    private List<File> files;

    private String defaultFolder = System.getProperty("user.home") + System.getProperty("file.separator") + "Documents"
            + System.getProperty("file.separator") + "Texture Resizer";

    private File saveFolder = new File(defaultFolder);
    private File filesFolder = new File(defaultFolder);

    private Image image;

    /**
     * this method runs, when the {@link ru.catstack.texture_resizer.model.FXML_FILES#MAIN} starts.
     */
    @Override
    public void onShow() {
        fileChooser.setTitle("Select textures");
        directoryChooser.setTitle("Select save directory");
        filesCount.setText("Select files or folder");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg"));
        folder.setText(saveFolder.getPath());
    }

    /**
     * this method runs, when user clicks to choose files button.
     */
    public void onChooseFilesClick(ActionEvent actionEvent) {
        List<File> _files = fileChooser.showOpenMultipleDialog(GApp.app.getStage());
        if(_files != null) {
            filesFolder = null;
            files = _files;
            filesCount.setText("You have selected " + files.size() + " files");
            resizeMode.setText("Mode: files");
        }else {
            if(files == null || files.size() == 0) {
                filesCount.setText("Select files or folder");
                resizeMode.setText("Mode: NONE");
            }
        }
    }

    /**
     * this method runs, when user clicks to choose folder button.
     */
    public void onChooseFolderClick(ActionEvent actionEvent) {
        filesFolder = directoryChooser.showDialog(GApp.app.getStage());

        if(filesFolder != null) {
            files = new ArrayList<>();
            resizeMode.setText("Mode: folder");

            try {
                checkDirectory(filesFolder);
            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);

                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Error");
                e.printStackTrace();
                alert.showAndWait();

                files = new ArrayList<>();
            }

            filesCount.setText("You have selected " + files.size() + " files");
        } else {
            if(files == null || files.size() == 0) {
                filesCount.setText("Select files or folder");
                resizeMode.setText("Mode: NONE");
                filesFolder = null;
            }
        }

    }

    private void checkDirectory(File directory){
        for (File file : directory.listFiles()) {
            if(file.isDirectory()){
                checkDirectory(file);
            } else {
                if(file != null)
                    files.add(file);
            }
        }
    }

    /**
     * this method runs, when user clicks to set save folder button.
     */
    public void onSetSaveFolderClick(ActionEvent actionEvent) {
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
            if (files != null && files.size() != 0) {
                if (!saveFolder.exists())
                    saveFolder.mkdirs();

                resizeFiles();

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
            e.printStackTrace();
            alert.showAndWait();
        }
    }

    private void resizeFiles() throws MalformedURLException {
        String root = "";
        if (filesFolder != null){
            root = filesFolder.getPath().substring(filesFolder.getPath().lastIndexOf("\\"));
        }
        for (File file : files) {
            image = new Image(file.toURL().toString());
            image = ImageTools.resizeImage(image, Float.valueOf(scaleValueField.getText()));
            File saveFile;
            if(filesFolder == null) {
                saveFile = new File(saveFolder.getPath() + System.getProperty("file.separator") + file.getName());
            } else {
                String filePath = file.getPath().replace(filesFolder.getPath(), System.getProperty("file.separator")
                        + root + System.getProperty("file.separator"));
                saveFile = new File(saveFolder.getPath() + filePath + System.getProperty("file.separator"));
                saveFile.mkdirs();
            }
            ImageTools.saveImageToFile(image, saveFile);
        }
    }

    /**
     * this method runs, when user clicks to info button.
     */
    public void onInfoClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Texture Resizer by CatStack Games " + Main.VERSION + "\n\n" +
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
