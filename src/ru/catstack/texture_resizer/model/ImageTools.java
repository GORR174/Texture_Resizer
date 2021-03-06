package ru.catstack.texture_resizer.model;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageTools {

    /**
     * This method resize the image with some value.
     *
     * @param imageToResize - default image.
     * @param resizeValue - scale value.
     * @return resized {@link Image}.
     */
    public static Image resizeImage(Image imageToResize, float resizeValue){
        Image image = new Image(imageToResize.impl_getUrl(), imageToResize.getWidth()*resizeValue,
                imageToResize.getHeight()*resizeValue, false, false);
        return image;
    }

    /**
     * This method saving image to file.
     *
     * @param image - image to save.
     * @param file - file to save.
     */
    public static void saveImageToFile(Image image, File file){
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
