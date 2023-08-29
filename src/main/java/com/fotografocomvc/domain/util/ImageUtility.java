package com.fotografocomvc.domain.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtility {

    public static byte[] compressImage(byte[] data) throws IOException {

        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception e) {
            throw new IOException("Error compressing image");
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) throws IOException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception exception) {
            throw new IOException("Error decompressing image");
        }
        return outputStream.toByteArray();
    }

    public static byte[] addImageWatermark(byte[] watermarkImageBytes, byte[] sourceImageBytes) throws IOException {


        try {

            File watermarkImageFile = imageToFile((watermarkImageBytes));
            File sourceImageFile = imageToFile((sourceImageBytes));
            File destImageFile = File.createTempFile("fotografocomvc-", ".tmp");

            BufferedImage watermarkImage = ImageIO.read(watermarkImageFile);
            BufferedImage sourceImage = ImageIO.read(sourceImageFile);


//            InputStream sourceImageIs = new ByteArrayInputStream(sourceImageBytes);
//            BufferedImage sourceImage = ImageIO.read(sourceImageIs);
//
//            InputStream watermarkIs = new ByteArrayInputStream(watermarkImageBytes);
//            BufferedImage watermarkImage = ImageIO.read(new File("src/resources/teste.jpeg"));

            // initializes necessary graphic properties
            Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();
            AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
            g2d.setComposite(alphaChannel);

            // calculates the coordinate where the image is painted
            int topLeftX = (sourceImage.getWidth() - watermarkImage.getWidth()) / 2;
            int topLeftY = (sourceImage.getHeight() - watermarkImage.getHeight()) / 2;

            // paints the image watermark
            g2d.drawImage(watermarkImage, topLeftX, topLeftY, null);

            ImageIO.write(sourceImage, "png", destImageFile);
            g2d.dispose();

            return Files.readAllBytes(destImageFile.toPath());

        } catch (IOException ex) {
            throw new IOException("Error adding watermark to image");
        }
    }

    public static File imageToFile(byte[] image) throws IOException {
        try {
            File file = File.createTempFile("fotografocomvc-", ".tmp");
            OutputStream os = new FileOutputStream(file);
            os.write(image);
            os.close();
            return file;
        }

        catch (Exception e) {
            throw new IOException("Error writing image");
        }

    }

    public static void waterMarkSecondTry(){

    File origFile = new File("/tmp/teste.jpg");
    ImageIcon icon = new ImageIcon(origFile.getPath());

    // create BufferedImage object of same width and height as of original image
    BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(),
            icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);

    // create graphics object and add original image to it
    Graphics graphics = bufferedImage.getGraphics();
        graphics.drawImage(icon.getImage(), 0, 0, null);

    // set font for the watermark text
        graphics.setFont(new Font("Arial", Font.BOLD, 30));

    //unicode characters for (c) is \u00a9
    String watermark = "\u00a9 JavaXp.com";

    // add the watermark text
        graphics.drawString(watermark, 0, icon.getIconHeight() / 2);
        graphics.dispose();

    File newFile = new File("C:/WatermarkedImage.jpg");
        try {
        ImageIO.write(bufferedImage, "jpg", newFile);
    } catch (IOException e) {
        e.printStackTrace();
    }

        System.out.println(newFile.getPath() + " created successfully!");
}

}
