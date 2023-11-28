package com.leszekt00;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;



public class ImageParser {// given a loaded image parse its contents into variables
    private BufferedImage image;
    private String filePath;
    public ImageParser(String filePath) { // save filepath and load the image from it
        this.filePath = filePath;
        try {
            this.image = loadImage(filePath);
        } catch (RuntimeException e) {
            throw e;
        }
        readTextFromImage(image);

    }
    private BufferedImage loadImage (String filePath) { // loading image into BufferedImage
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            // Load the image from the FileInputStream
            return ImageIO.read(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot find file with the path:"+filePath,e);
        }
    }

    private String readTextFromImage(BufferedImage im) { // reads text from image using Tess4j
        StringBuilder result = new StringBuilder("image content:")
                .append(System.lineSeparator());
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping
        instance.setDatapath("resources/tessdata"); // path to tessdata directory

        try {
            result.append(instance.doOCR(im));
            System.out.println(result);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return result.toString();
    }


}
