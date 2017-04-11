package com.example.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Marius on 4/11/2017.
 */
@Service
public class QRCreator {

    private QRCodeWriter qrCodeWriter;
    private final int width = 400;
    private final int height = width;
    private final String imageType = "jpg";

    public QRCreator() {
        qrCodeWriter = new QRCodeWriter();
    }

    public ByteArrayOutputStream createQR(String toEncode) {

        BitMatrix bitMatrix = null;

        try {
            bitMatrix = qrCodeWriter.encode(toEncode, BarcodeFormat.QR_CODE, width, height);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        BufferedImage image = new BufferedImage(bitMatrix.getWidth(), bitMatrix.getWidth(), BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics2D = (Graphics2D) image.getGraphics();
        graphics2D.setBackground(Color.WHITE);
        graphics2D.fillRect(0, 0, bitMatrix.getWidth(), bitMatrix.getWidth());
        graphics2D.setColor(Color.BLACK);

        for (int i = 0; i < bitMatrix.getWidth(); i++) {
            for (int j = 0; j < bitMatrix.getWidth(); j++) {
                if (bitMatrix.get(i, j)) {
                    graphics2D.fillRect(i, j, 1, 1);
                }
            }
        }

        ByteArrayOutputStream imageAsByte = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, imageType, imageAsByte);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageAsByte;
    }
}
